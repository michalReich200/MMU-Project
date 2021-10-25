package server;



import service.CacheUnitController;

import java.beans.PropertyChangeEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server
        extends java.lang.Object
        implements java.beans.PropertyChangeListener, java.lang.Runnable {
    //initialize socket and input stream
    private Socket clientSocket ;
    private ServerSocket server;
    private DataInputStream in ;
    private boolean isOn = false;
    private boolean isAlive;
    private Executor executor;
    private Thread thread;
    private CacheUnitController<String> controller;


    /**
     * initilizes  the controller
     */
    public Server() {
        controller=new CacheUnitController<String>();
    }

    /**
     *
     * @param evt -the command that the user inserted
     *  the function checks the command and start or shutdown the server according to it.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String action =(String) evt.getNewValue();
        if (action.equals("start")){
            if(isOn == false) {
                isOn = true;
                thread= new Thread(this);
                thread.start();
            }
        }
        else if(action.equals("stop")){
            if(isOn){
            isOn = false;
            thread.stop();
            shutdown();
            }
        }
        else {
            System.out.println("Not a valid command");
        }
    }

    /**
     * reboot the server and loop while server is on,
     * lunch a thread whom accept the client
     */
    @Override
    public void run(){
        // starts server and waits for a connection
        try {
            server = new ServerSocket(12345);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");
            while (isOn) {
                clientSocket = server.accept();
                System.out.println("Client accepted");
                executor = Executors.newFixedThreadPool(3);
                executor.execute(new HandleRequest<String>(clientSocket, controller));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * call to function shutdown that takes care of saving  the data into the file,
     * then closes the server.
     */
        public void shutdown(){
        System.out.println("Server shutdown");
        controller.shutdown();
        try {
            server.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

