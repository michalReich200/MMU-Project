package View;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GUI {

    private JFrame mainFrame;
    private JTextArea statisticsFrame;
    private JScrollPane scroll;

    PropertyChangeSupport support;

    /**
     *
     * @param support to tell the observer that was a change
     */
    public GUI(PropertyChangeSupport support) {
        this.support=support;
    }

    /**
     * create the GUI
     * @throws FontFormatException
     */
    void prepareGUI() throws FontFormatException {
        mainFrame = new JFrame("Java SWING ");
        mainFrame.setSize(400, 400);
        mainFrame.setLayout(new GridLayout(3, 1));

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

          statisticsFrame = new JTextArea("Statistics:");
          scroll = new JScrollPane(statisticsFrame, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JButton requestButton = new JButton("Load a request");
        JButton statisticsButton = new JButton("Show statistics");
        requestButton.setActionCommand("Load a request");
        statisticsButton.setActionCommand("Show statistics");

        statisticsButton.addActionListener(new GUI.ButtonClickListener(this.support));///todo
        requestButton.addActionListener(new GUI.ButtonClickListener(this.support));

        mainFrame.add(requestButton);
        mainFrame.add(statisticsButton);
        
        mainFrame.add(scroll);
        mainFrame.setVisible(true);
        Color myColor1 = new Color(102, 0, 102);
        requestButton.setBackground(myColor1);
        requestButton.setForeground(Color.white);
        Font buttonFont =new Font("Arial",Font.BOLD,17);
        requestButton.setFont(buttonFont);
        statisticsButton.setBackground(myColor1);
        statisticsButton.setForeground(Color.white);
        statisticsButton.setFont(buttonFont);
        Font textFont=new Font("Arial",Font.CENTER_BASELINE,15);
        statisticsFrame.setFont(textFont);
        statisticsFrame.setForeground(Color.blue);



    }

    void showEventDemo() {


        mainFrame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        PropertyChangeSupport support;
        public String action = null;
        String filePath = null;
        /**
         *
         * @param support to tell the observer that was a change
         */
        public ButtonClickListener(PropertyChangeSupport support) {
            this.support=support;
        }

        /**
         * according to the action -update the observer
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            this.action = "getStatistics";

            if (command.equals("Load a request")) {
                JFileChooser fileChooser=new JFileChooser();
                fileChooser.setCurrentDirectory(new File("src/main/resources"));
                FileNameExtensionFilter filesFilter = new FileNameExtensionFilter("json files","json");
                fileChooser.setFileFilter(filesFilter);
                int i = fileChooser.showOpenDialog(mainFrame);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    filePath = file.getPath();
                    action=fileToString(file);
                    support.firePropertyChange("request",null,action);
                }
            }
            else  {

                support.firePropertyChange("statistics",null,action);



            }
    }

        /**
         * open the selected file,read content to string
         * @param file which the user select
         * @return string of the content from the file.
         */
        private String fileToString(File file) {
            StringBuilder data = new StringBuilder();
            try {
                Scanner input = new Scanner(file);
                while (input.hasNext()) {
                    data.append(input.next());
                }

            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            return data.toString();
        }
        }

        public JTextArea getStatisticsFrame(){
            return statisticsFrame;
        }
    }

