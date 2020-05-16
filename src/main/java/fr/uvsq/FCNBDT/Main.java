package fr.uvsq.FCNBDT;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public enum Main {
    MAIN;

    public void run() throws IOException {
        SimulData data = new SimulData();
        JFrame frame = new JFrame();
        //frame.setTitle(data.getReader().getPath());
        frame.setTitle("Visualisateur d'Automates cellulaires");
        
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(1100,600));
        Dimension frameDimension = frame.getPreferredSize();
        int widthBoard = (int) (frameDimension.width*0.7);

        SimulBoard b = new SimulBoard(widthBoard);
        //b.resetBoard(data);
        //b.setIcones("traces/jeuvie/");
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(frameDimension.width-widthBoard-20, 460));
        /* LOGS */
        LogsPanel texts = new LogsPanel(frameDimension.width-widthBoard-20);
        rightPanel.add(texts, BorderLayout.SOUTH);
        
        /* Buttons and Slider */
        JPanel infoPosition = new JPanel();
        infoPosition.setPreferredSize(new Dimension(frameDimension.width-widthBoard-20, 40));
        VideoCommands videoCommands = new VideoCommands(data, b, texts, infoPosition, frameDimension.width);
        videoCommands.traitement();
        rightPanel.add(infoPosition, BorderLayout.NORTH);

        /* FILE */
        JMenuBar menu = new MenuBar(videoCommands);
        
        frame.setJMenuBar(menu);


        frame.add(rightPanel,BorderLayout.EAST);
        frame.add(b, BorderLayout.WEST);
        //frame.add(buttons,BorderLayout.SOUTH);
        frame.add(videoCommands,BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        //frame.setSize(1100, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);


        System.out.println("Frame Dimension : "+frame.getSize().toString());
    }

    public static void main(String[] args) throws IOException {
        MAIN.run();
    }
}
