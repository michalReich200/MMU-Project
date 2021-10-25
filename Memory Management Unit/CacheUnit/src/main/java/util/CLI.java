package util;

import java.io.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Scanner;

public class CLI
        extends java.lang.Object
        implements java.lang.Runnable{
    private Scanner input;
    private PrintWriter output;
    private  String value  = null;
    public PropertyChangeSupport changeListener=new  PropertyChangeSupport(this);

    public CLI(java.io.InputStream in, java.io.OutputStream out) throws FileNotFoundException {
        input = new Scanner(in);
        output = new PrintWriter(out);
    }

    public void removePropertyChangeListener(java.beans.PropertyChangeListener pcl){
        this.changeListener.removePropertyChangeListener(pcl);

    }
    public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl){
        this.changeListener.addPropertyChangeListener(pcl);

    }
    public void write(java.lang.String string) throws IOException {
        output.write(string+ '\n');
        output.flush();
    }




    @Override
    public void run() {
        String clientCommand = null;
        System.out.println("enter your command: (start/stop)");
        while (true){
            clientCommand = input.nextLine().toLowerCase();
            //if()
            changeListener.firePropertyChange( "value",value, clientCommand);
            value=clientCommand;
            System.out.println("enter your command: (start/stop)");
        }
    }
}

