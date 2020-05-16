package fr.uvsq.FCNBDT.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import fr.uvsq.FCNBDT.SimulData;
import fr.uvsq.FCNBDT.utils.CellType;

public class Board extends JPanel{

    private static final long serialVersionUID = 1L;

    private boolean isFilled;
    private CellType type = CellType.HEXAGONE;
    private int width = 3;
    private int length = 3;
    private int x = 0;
    private int y = 0;
    private int[] matrice;
    private int[] colors;
    private BufferedImage[] icones;
    private double zoom;
    private Point selection;

    public Board() {
        super();
        this.setPreferredSize();
        zoom = 1;
        selection = new Point(-1, -1);
        isFilled = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        AffineTransform affineTrans = g2d.getTransform();
        affineTrans.scale(zoom, zoom);
        g2d.setTransform(affineTrans);
        Dimension d = new Dimension();
        this.getSize(d);
        g2d.setStroke((Stroke) new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        if (isFilled)
            type.paintBoard(g2d, d, zoom, selection, matrice, width, length, colors, icones);
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
    /*
    public void setWidth(int width) {
        this.width = width;
        this.setPreferredSize();
    }

    public void setLength(int length) {
        this.length = length;
        this.setPreferredSize();
    }*/

    public void setMatrice(int[] matrice) {
        this.matrice = matrice;
    }
/*
    public void setColors(int[] colors) {
        this.colors = colors;
    }

    public void setCellType(CellType type) {
        this.type = type;
        this.setPreferredSize();
    }*/

    public void filled(SimulData data) {
        this.isFilled = true;
        this.zoom = 1;
        this.width = data.getWidth();
        this.length = data.getLength();
        this.type = data.getCellType();
        this.setPreferredSize();
        this.colors = data.getColors();
        this.matrice = data.getMatrice();
        this.setIcones(Paths.get(Paths.get(data.getReader().getPath()).toAbsolutePath().getParent().toString(),
                "icones").toString());
        this.revalidate();
        this.repaint();
    }

    public void unfilled() {
        this.isFilled = false;
        this.selection = new Point(-1,-1);
        this.zoom = 1;
        this.width = 0;
        this.length = 0;
        this.type = null;
        this.setPreferredSize();
        this.colors = null;
        this.matrice = null;
        this.icones = null;
        this.revalidate();
        this.repaint();
    }

    /*public void rebootZoom() {
        this.zoom = 1;
    }*/

    private void setIcones(String dir) {
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

    public double getPourcentagePositionI(int i, int j) {
        return isFilled?type.getPositionInBoardLength(length, i, j):0;
    }
    public double getPourcentagePositionJ(int i, int j) {
        return isFilled?type.getPositionInBoardWidth(width, i, j):0;
    }
    
    public Point getCurrentPosition(Point p) {
        if (!isFilled)
            return new Point(-1, -1);
        Point result = type.getCurrentPosition(zoom, p);
        if (result.x >= width || result.y >= length)
            return new Point(-1, -1);
        return result;
    }

    public void zoomIn() {
        this.zoom = this.type.zoomIn(zoom);
        this.setPreferredSize();
        this.revalidate();
        this.repaint();
    }

    public void zoomOut() {
        this.zoom = this.type.zoomOut(zoom);
        this.setPreferredSize();
        this.revalidate();
        this.repaint();
    }

    public void select(int i, int j) {
        if(isFilled) {
            this.selection = new Point(j,i);
            this.revalidate();
            this.repaint();
        }
    }

    public void deselect() {
        this.selection = new Point(-1,-1);
        this.revalidate();
        this.repaint();
    }

    private void setPreferredSize() {
        if(!isFilled) {
            this.setPreferredSize(new Dimension(0,0));
            return;
        }
        Dimension result = type.getBoardDimension(width, length, zoom);
        result.width += 80;
        this.setPreferredSize(result);
    }
}
