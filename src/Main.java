import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import display.Board;
import reader.Reader;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		  Reader r = new Reader("a");
		  int matrice[] = new int[r.getLength()*r.getWidth()];
		  r.readNext(matrice);
		  int colors[] = r.getColors();
		  JFrame frame = new JFrame();
		  Board b = new Board();
		  b.setWidth(r.getWidth());
		  b.setLength(r.getLength());
		  b.setColors(colors);
		  b.setMatrice(matrice);
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
		  JPanel buttons = new JPanel();
		  buttons.setSize(200, 50);
		  JButton prec = new JButton("prec.");
		  prec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					r.readPrevious(matrice);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				b.setMatrice(matrice);
				b.revalidate();
				b.repaint();
			}
		});
		  JButton suiv = new JButton("suiv.");
		  suiv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(r.readNext(matrice) != -1) {
						if(r.logExist(r.getT()))
							logs.setText(logs.getText() + r.getLog(r.getT()) + "\n");
						b.setMatrice(matrice);
						b.revalidate();
						b.repaint();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		  buttons.add(prec);
		  buttons.add(suiv);
		  frame.add(b);
		  frame.add(buttons,BorderLayout.NORTH);
		  frame.add(texts, BorderLayout.EAST);
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.pack();
		  frame.setSize(1100, 600);
		  frame.setResizable(false);
		  frame.setLocationRelativeTo( null );
		  frame.setVisible(true);
	}

}
