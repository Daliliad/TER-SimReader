import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import display.Board;
import reader.Reader;

public enum Main {
    
    MAIN;
    
    public void run() throws IOException {
        SimulData data = new SimulData();
        JFrame frame = new JFrame();
        SimulBoard b = new SimulBoard();
        b.resetBoard(data);
        //b.setIcones("traces/jeuvie/");

        JPanel texts = new JPanel();
        JTextPane logs = new JTextPane();
        logs.setMaximumSize(new Dimension(1000, 50));
        logs.setText("test test test test \n"
                + "test test test test \n"
                + "test \n");
        logs.setEditable(false);
        JScrollPane scrlogs = new JScrollPane(logs);
        scrlogs.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        texts.setLayout(new BorderLayout());
        texts.setMaximumSize(new Dimension(1000, 150));
        texts.setPreferredSize(new Dimension(300, 150));
        texts.add(scrlogs);
        scrlogs.setMaximumSize(new Dimension(1000, 150));
        
        Bouton boutons = new Bouton(data, b , logs);
        boutons.traitement();
        
        frame.add(b);
        frame.add(boutons,BorderLayout.SOUTH);
        frame.add(texts, BorderLayout.EAST);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1500, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
    }

	public static void main(String[] args) throws IOException {
		MAIN.run();
	}

}
