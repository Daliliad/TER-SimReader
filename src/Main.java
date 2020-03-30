import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import display.Board;
import display.Slider;
import reader.Reader;

public enum Main {

    MAIN;

    public void run() throws IOException {
        SimulData data = new SimulData();
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(1100,600));
        Dimension frameDimension = frame.getPreferredSize();
        int widthBoard = (int) (frameDimension.width*0.7);

        SimulBoard b = new SimulBoard();
        b.resetBoard(data);
        //b.setIcones("traces/jeuvie/");

        /* LOGS */
        JPanel texts = new JPanel();
        JTextPane logs = new JTextPane();
        //logs.setMaximumSize(new Dimension(1000, 50));
        /*logs.setText("test test test test \n"
                + "test test test test \n"
                + "test \n");*/
        logs.setEditable(false);
        JScrollPane scrlogs = new JScrollPane(logs);
        scrlogs.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        texts.setLayout(new BorderLayout());
        texts.setMaximumSize(new Dimension(1000, 150));
        //texts.setPreferredSize(new Dimension(frameDimension.width-widthBoard, 490));
        texts.add(scrlogs);
        scrlogs.setMaximumSize(new Dimension(1000, 150));
        scrlogs.setPreferredSize(new Dimension(frameDimension.width-widthBoard, 490));

        /* Buttons and Slider */
        VideoCommands videoCommands = new VideoCommands(data, b, logs, frameDimension.width);
        videoCommands.traitement();


        JPanel fileTextPanel = new JPanel();
        fileTextPanel.setLayout(new BoxLayout(fileTextPanel, BoxLayout.PAGE_AXIS));

        JPanel filePanel = new JPanel();
        filePanel.add(new JButton("Select"), BorderLayout.EAST);

        fileTextPanel.add(filePanel);
        fileTextPanel.add(texts);

        b.setPreferredSize(new Dimension(widthBoard,490));

        frame.add(fileTextPanel,BorderLayout.EAST);
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
