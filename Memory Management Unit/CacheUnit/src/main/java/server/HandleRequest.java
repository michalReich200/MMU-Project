package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import service.CacheUnitController;
import dm.DataModel;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Map;



public class HandleRequest<T>
        extends java.lang.Object
        implements java.lang.Runnable
{
    DataOutputStream output;
    private CacheUnitController controller;
    private java.net.Socket socket;
    private Request<DataModel<T>[]> request;

    /**
     *
     * @param s the socket the  request is sent in
     * @param controller the controller that actually does the request .
     */
    public HandleRequest(Socket s,
                         CacheUnitController<T> controller){
        this.socket = s ;
        this.controller = controller;

    }

    /**
     * calls to getRequest -when done closes the socket
     */
    @Override
    public void run() {
        try {
            getRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reads the request from the socket and call the right function according to the request
     * @throws IOException when doesn't manage to read and/or write.
     */
    private void getRequest() throws IOException {
        Gson gson = new Gson();
        String content = null;
        StringBuilder strBuilder = new StringBuilder();
        DataInputStream reader = null;

        try {
            reader = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        do {
            try {
                content = reader.readUTF();
            } catch (Exception e) {
                e.printStackTrace();
            }
            strBuilder.append(content);
        } while (reader.available() != 0);
        content = strBuilder.toString();
        try {
            output = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        Type ref = new TypeToken<Request<DataModel<T>[]>>() {
        }.getType();
        if("getStatistics".equals(content)){
            getStatistics();
        }else{
        request = new Gson().fromJson((String) content, ref);
        String action = request.getHeaders().get("action");
        switch (action){
            case "GET":
            try {
                DataModel [] datas = controller.get(request.getBody());
                output.writeUTF(gson.toJson(datas));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            break;
            case "UPDATE":
            try {
                boolean success = controller.update(request.getBody());
                output.writeUTF(gson.toJson(success));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            break;
            case "DELETE":
            try {
                boolean success = controller.delete(request.getBody());
                output.writeUTF(gson.toJson(success));
                output.flush();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            break;
        }
        }
    }

    /**
     * writes to the socket the statistics from the controller
     */
    private void getStatistics() {
        try {
            output=new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String statistics=controller.getStatistic();
        try {
            output.writeUTF(statistics);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


