package display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JPanel;

import utils.CellType;

public class Board extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CellType type = CellType.SQUARE;
	private int width = 100;
	private int length = 100;
	private int zoom = 1;
	private int x = 0;
	private int y = 0;
	private int[] matrice = {0,1,0,2,3,2,4,5,4};
	private int[] colors = {Color.BLACK.getRGB(),Color.RED.getRGB(),Color.BLUE.getRGB(),Color.GREEN.getRGB(),Color.WHITE.getRGB(),Color.GRAY.getRGB()};
	
	public Board() {
		super();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); 
		Graphics2D g2d = (Graphics2D) g;
		Dimension d = new Dimension();
		this.getSize(d);
		g2d.setStroke((Stroke) new BasicStroke(3f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
		if(type == CellType.SQUARE) {
			if(matrice != null && matrice.length == width*length && colors != null)
			{
				for(int i = 0; i < matrice.length; i++) {
					g2d.setColor(new Color(colors[matrice[i]]));
					g2d.fillRect(i%width*d.height/length+(d.width-d.height)/2, i/length*d.height/length, d.height/length, d.height/length);
				}
			}
			drawCells(g2d);
		}
	}
	
	private void drawCells(Graphics2D g2d) {
		Dimension d = new Dimension();
		g2d.setColor(Color.black);
		this.getSize(d);
		for(int i = 0; i <= width; i++) {
			g2d.drawLine(i*d.height/length+(d.width-d.height)/2, 0, i*d.height/length+(d.width-d.height)/2, d.height);
		}
		for(int i = 0; i <= length; i++) {
			g2d.drawLine((d.width-d.height)/2, i*d.height/length, d.width-(d.width-d.height)/2, i*d.height/length);
		}
	}
	
	public void setWidth(int width) {
		this.width = width;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setMatrice(int[] matrice) {
		this.matrice = matrice;
	}

	public void setColors(int[] colors) {
		this.colors = colors;
	}
	
}
