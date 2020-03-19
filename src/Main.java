import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import display.Board;
import reader.Reader;

public class Main {

	public static void main(String[] args) throws IOException {
		Reader r = new Reader("traces/incendie/trace3.log");
		//Reader r = new Reader("traces/jeuvie/b");
		int matrice[] = new int[r.getLength()*r.getWidth()];
		r.readNext(matrice);
		int colors[] = r.getColors();
		for(int i=0;i<colors.length;i++) {
			System.out.println(i+" : "+colors[i]);
		}
		JFrame frame = new JFrame();
		Board b = new Board();
		b.setWidth(r.getWidth());
		b.setLength(r.getLength());
		b.setCellType(r.getCellType());
		b.setColors(colors);
		b.setMatrice(matrice);
		b.setIcones("traces/incendie/");
		//b.setIcones("traces/jeuvie/");
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
					r.readNext(matrice);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				b.setMatrice(matrice);
				//System.err.println(matrice.toString());
				b.revalidate();
				b.repaint();
			}
		});
		
		buttons.add(prec);
		buttons.add(suiv);
		frame.add(b);
		frame.add(buttons,BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(600, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo( null );
		frame.setVisible(true);
	}

}
