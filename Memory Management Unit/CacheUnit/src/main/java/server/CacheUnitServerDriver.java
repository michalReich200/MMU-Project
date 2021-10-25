package server;

import util.CLI;


import java.io.FileNotFoundException;

import static java.lang.Thread.sleep;

public class CacheUnitServerDriver
        extends java.lang.Object {


    public CacheUnitServerDriver(){

    }
    public static void main(java.lang.String[] args) throws FileNotFoundException, InterruptedException {
        CLI cli = new CLI(System.in, System.out);
        Server server = new Server();
        cli.addPropertyChangeListener(server);
        new Thread(cli).start();



    }
}

