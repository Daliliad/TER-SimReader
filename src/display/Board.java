package display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
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
	private int[] matrice;
	private int[] colors;
	private BufferedImage[] icones;
	private Dimension caseDimension;
	
	public Board() {
		super();
		caseDimension = type.defDimension;
        this.setPreferredSize();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Dimension d = new Dimension();
		this.getSize(d);
		/*if(caseDimension == null) {
            caseDimension = type.defautDimension(d, width, length);
        }*/
		g2d.setStroke((Stroke) new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
		type.paintBoard(g2d, d, caseDimension, matrice, width, length, colors, icones);
	}
	
	/*
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
	*/
	
	public void setWidth(int width) {
		this.width = width;
        this.setPreferredSize();
	}

	public void setLength(int length) {
		this.length = length;
        this.setPreferredSize();
	}

	public void setMatrice(int[] matrice) {
		this.matrice = matrice;
	}

	public void setColors(int[] colors) {
		this.colors = colors;
	}
	
	public void setCellType(CellType type) {
		this.type=type;
        this.setPreferredSize();
	}
	
	public void setIcones(String dir) {
		icones = new BufferedImage[colors.length];
		File f;
		for(int i=0;i<icones.length;i++) {
			try {
				f = new File(Paths.get(Paths.get(dir).toAbsolutePath().toString(),
                        i+".png").toString());
				if(f.exists())
					icones[i] = ImageIO.read(f);
				else
					icones[i] = null;
			} catch (IOException e) {
				e.printStackTrace();
				icones[i] = null;
			}
		}
	}
	
	public void zoomIn() {
	    this.type.zoomIn(caseDimension);
        this.setPreferredSize();
	    this.revalidate();
	    this.repaint();
	}
	
	public void zoomOut() {
        this.type.zoomOut(caseDimension);
        this.setPreferredSize();
        this.revalidate();
        this.repaint();
    }
	
	private void setPreferredSize() {
        this.setPreferredSize(type.getBoardDimension(width, length, caseDimension));
	}
}
