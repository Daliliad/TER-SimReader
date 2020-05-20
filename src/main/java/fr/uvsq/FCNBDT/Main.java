package fr.uvsq.FCNBDT;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fr.uvsq.FCNBDT.display.SimulBoard;

public enum Main {
    MAIN;

    public void run() throws IOException {
        SimulData data = new SimulData();
        JFrame frame = new JFrame();
        frame.setTitle("Visualisateur d'Automates cellulaires");
        
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(1100,600));
        Dimension frameDimension = frame.getPreferredSize();
        int widthBoard = (int) (frameDimension.width*0.7);

        SimulBoard b = new SimulBoard(widthBoard);
        
        JPanel rightPanel = new JPanel() {
            private Dimension getCustomDimension() {
                if(getParent().getWidth()<700)
                    return new Dimension(300,this.getParent().getHeight()-VideoCommands.BUTTON_PANEL_HEIGHT);
                return new Dimension(350,this.getParent().getHeight()-VideoCommands.BUTTON_PANEL_HEIGHT);
            }
            
            @Override
            public Dimension getPreferredSize() {
                return getCustomDimension();
            }
            @Override
            public Dimension getMinimumSize() {
                return getCustomDimension();
            }
            @Override
            public Dimension getMaximumSize() {
                return getCustomDimension();
            }
        };
        rightPanel.setLayout(new BorderLayout());
        /* LOGS */
        LogsPanel texts = new LogsPanel();
        rightPanel.add(texts, BorderLayout.SOUTH);
        
        /* Buttons and Slider */
        JPanel infoPosition = new JPanel();
        VideoCommands videoCommands = new VideoCommands(data, b, texts, infoPosition);
        videoCommands.traitement();
        rightPanel.add(infoPosition, BorderLayout.NORTH);

        /* FILE */
        JMenuBar menu = new MenuBar(videoCommands);
        
        frame.setJMenuBar(menu);

        frame.add(rightPanel,BorderLayout.EAST);
        frame.add(b, BorderLayout.WEST);
        frame.add(videoCommands,BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(650,300));
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        MAIN.run();
    }
}
