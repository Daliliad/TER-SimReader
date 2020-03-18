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
	
	private CellType type = CellType.HEXAGONE;
	private int width = 3;
	private int length = 3;
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
		g2d.setStroke((Stroke) new BasicStroke(0, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
		if(type == CellType.SQUARE) {
			if(matrice != null && matrice.length == width*length && colors != null)
			{
				for(int i = 0; i < matrice.length; i++) {
					g2d.setColor(new Color(colors[matrice[i]]));
					g2d.fillRect(i%width*d.width/width, i/length*d.height/length, d.width/width, d.height/length);
				}
			}
			drawCells(g2d);
		}
		else if(type == CellType.HEXAGONE) {
			int xP[] = new int[6];
			int yP[] = new int[6];
			int xScale = d.width/(2*width+1);
			int yScale = (int) (xScale/(Math.sqrt(3)));
			if((yScale*3*length+1)>d.height) {
				yScale=d.height/(3*length+1);
				xScale=(int) (yScale*Math.sqrt(3));
			}
			yP[1]=(0*3)*yScale;
			yP[2]=(0*3+1)*yScale;
			for(int j = 0; j < length ; j++) {
				yP[5]=yP[1];
				yP[0]=yP[4]=yP[2];
				yP[1]=yP[3]=(j*3+3)*yScale;
				yP[2]=(j*3+4)*yScale;

				xP[3]=(0*2+1-j%2)*xScale;
				for(int i=0; i<width; i++) {
					xP[0]=xP[1]=xP[3];
					xP[2]=xP[5]=(i*2+1-j%2+1)*xScale;
					xP[3]=xP[4]=(i*2+1-j%2+2)*xScale;

					g2d.setColor(new Color(colors[matrice[j*width+i]]));
					g2d.fillPolygon(xP,yP,6);
					g2d.setColor(Color.black);
					g2d.drawPolygon(xP, yP, 6);
				}
			}
		}
	}
	
	private void drawCells(Graphics2D g2d) {
		Dimension d = new Dimension();
		g2d.setColor(Color.black);
		this.getSize(d);
		for(int i = 0; i < width; i++) {
			g2d.drawLine(i*d.width/width, 0, i*d.width/width, d.height);
		}
		for(int i = 0; i < length; i++) {
			g2d.drawLine(0, i*d.height/length, d.width, i*d.height/length);
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
