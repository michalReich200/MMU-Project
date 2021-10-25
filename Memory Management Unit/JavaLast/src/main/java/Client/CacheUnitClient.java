package Client;


import com.google.gson.Gson;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class CacheUnitClient {
    public CacheUnitClient() {}

    /**
     *
     * @param request a request to the server from client
     * @return the response from the server.
     */
   public java.lang.String send(java.lang.String request) {
       String response = null;
        try {
            InetAddress localaddr = InetAddress.getLocalHost();
            Socket myServer = new Socket(localaddr.getHostAddress(), 12345);
            DataOutputStream output = new DataOutputStream(new BufferedOutputStream(myServer.getOutputStream()));//
            DataInputStream input = new DataInputStream(myServer.getInputStream());//
            output.writeUTF(request);
            output.flush();

            response = input.readUTF();
            output.close();
            input.close();
            myServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
