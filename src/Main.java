import javax.swing.JFrame;

import display.Board;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  JFrame frame = new JFrame();
		  Board b = new Board();
		  frame.add(b);
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.pack();
		  frame.setSize(1000, 1000);
		  frame.setResizable(false);
		  frame.setLocationRelativeTo( null );
		  frame.setVisible(true);
	}

}
