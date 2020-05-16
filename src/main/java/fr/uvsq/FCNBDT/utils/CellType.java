package fr.uvsq.FCNBDT.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public enum CellType {
    SQUARE(0, new Dimension(26,26), 0.4, 5) {
        @Override
        public void paintBoard(Graphics2D g2d,
                Dimension d, double zoom, Point selection,
                int[] matrice, int width, int length, int[] colors, BufferedImage[] icones) {
            if(matrice != null && matrice.length == width*length && colors != null)
            {
                int scale = defDimension.height;
                int xP = 0;
                int yP = 0;
                for(int j = 0; j < length; j++) {
                    for(int i = 0; i < width; i++) {
                        xP = i*scale;
                        yP = j*scale;
                        g2d.setColor(new Color(colors[matrice[j*width+i]]));
                        if(zoom>0.5 && icones[matrice[j*width+i]]!=null)
                            g2d.drawImage(icones[matrice[j*width+i]],xP,yP,scale,scale,null);
                        else
                            g2d.fillRect(xP, yP, scale, scale);
                        g2d.setColor(Color.black);
                        g2d.drawRect(xP, yP, scale, scale);
                    }
                }
                //selection
                if (selection.x>=0 && selection.y>=0) {
                    xP = selection.x*scale;
                    yP = selection.y*scale;
                    g2d.setColor(Color.red);
                    g2d.drawRect(xP, yP, scale, scale);
                }
            }
        }

        @Override
        public Dimension getBoardDimension(int width, int length, double zoom) {
            Dimension boardDim = new Dimension(
                    (int) ((width+1)*defDimension.width*zoom) + 1,
                    (int) ((length+1)*defDimension.height*zoom) + 1);
            return boardDim;
        }

        @Override
        public double getPositionInBoardWidth(int width, int i, int j) {
            double pos = (j+0.5)*defDimension.height;
            double board = (width+1)*defDimension.height;
            return pos/board;
        }

        @Override
        public double getPositionInBoardLength(int length, int i, int j) {
            double pos = (i+0.5)*defDimension.height;
            double board = (length+1)*defDimension.height;
            return pos/board;
        }

        @Override
        public Point getCurrentPosition(double zoom, Point p) {
            int taille = (int) (defDimension.height*zoom);
            int i = p.y/taille;
            int j = p.x/taille;
            return new Point(i,j);
        }
    },
    HEXAGONE(1, new Dimension(24,28), 0.4, 5) {
        @Override
        public void paintBoard(Graphics2D g2d,
                Dimension d, double zoom, Point selection,
                int[] matrice, int width, int length, int[] colors, BufferedImage[] icones) {
            int xP[] = new int[6];
            int yP[] = new int[6];
            int xScale = defDimension.width/2;
            int yScale = defDimension.height/4;
            yP[1]=(0*3)*yScale;
            yP[2]=(0*3+1)*yScale;
            //System.out.println(4*yScale);
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
                    if(zoom>0.5 && icones[matrice[j*width+i]]!=null)
                        g2d.drawImage(icones[matrice[j*width+i]],xP[0],yP[5],2*xScale,4*yScale,null);
                    else
                        g2d.fillPolygon(xP,yP,6);
                    g2d.setColor(Color.black);
                    g2d.drawPolygon(xP, yP, 6);
                }
            }
            //selection
            if (selection.x>=0 && selection.y>=0) {
                xP[0]=xP[1]=(selection.x*2+1-selection.y%2)*xScale;
                xP[2]=xP[5]=(selection.x*2+1-selection.y%2+1)*xScale;
                xP[3]=xP[4]=(selection.x*2+1-selection.y%2+2)*xScale;
                yP[1]=yP[3]=(selection.y*3+3)*yScale;
                yP[2]=(selection.y*3+4)*yScale;
                yP[0]=yP[4]=(selection.y*3+1)*yScale;
                yP[5]=(selection.y*3)*yScale;
                g2d.setColor(Color.red);
                g2d.drawPolygon(xP, yP, 6);
            }
        }

        @Override
        public Dimension getBoardDimension(int width, int length, double zoom) {
            Dimension boardDim = new Dimension(
                    (int) ((width+0.5)*defDimension.width*zoom)+1,
                    (int) (length*(defDimension.height*zoom*3./4)+defDimension.height*zoom*1./4)+1);
            return boardDim;
        }

        @Override
        public double getPositionInBoardWidth(int width, int i, int j) {
            int xScale = defDimension.width/2;
            double pos = (j*2+1-i%2+1)*xScale;
            double board = (width+0.5)*defDimension.width;
            return pos/board;
        }

        @Override
        public double getPositionInBoardLength(int length, int i, int j) {
            int yScale = defDimension.height/4;
            double pos = (i*3+2)*yScale;
            double board = (length*(defDimension.height*3./4)+defDimension.height*1./4);
            return pos/board;
        }

        @Override
        public Point getCurrentPosition(double zoom, Point p) {
            int xScale = (int) ((defDimension.width/2)*zoom);
            int demiJ = p.x/xScale;
            int yScale = (int) ((defDimension.height/4)*zoom);
            int troisQuartI = p.y/(3*yScale);
            int part3I = (p.y/yScale)%3;
            int i;
            if(part3I==1 || part3I==2) {
                i = troisQuartI;
            } else {
                int pariteI = demiJ%2;
                int pariteJ = troisQuartI%2;
                
                Polygon triangle;
                int[] xP = new int[3];
                int[] yP = new int[3];
                xP[0] = yP[0] = 0;
                yP[2] = 0;
                xP[2] = xScale;
                yP[1] = yScale;
                if(pariteI + pariteJ == 1) {
                    xP[1] = 0;
                } else {
                    xP[1] = xScale;
                }
                triangle = new Polygon(xP, yP, 3);
                Point pInScale = new Point(p.x%xScale, p.y%yScale);
                boolean isTriangle = false;
                if (triangle.contains(pInScale)) {
                    isTriangle = true;
                }
                i = isTriangle?troisQuartI-1:troisQuartI;
            }
            int j = (demiJ-1+i%2);
            if(j>=0)
                j = j/2;
            return new Point(i, j);
        }
    };

    public final int i;
    public final Dimension defDimension;
    public final double minZoomOut;
    public final double maxZoomIn;
    
    public static final double ZOOM_COEFF = 0.15;

    CellType(int i, Dimension def, double minZoomOut, double maxZoomIn) {
        this.i = i;
        this.defDimension = def;
        this.minZoomOut = minZoomOut;
        this.maxZoomIn = maxZoomIn;
    }


    public abstract void paintBoard(Graphics2D g2d,
            Dimension panelDimension, double zoom, Point selection,
            int[] matrice, int width, int length, int[] colors, BufferedImage[] icones);

    public abstract Dimension getBoardDimension(int width, int length, double zoom);
    
    public abstract double getPositionInBoardWidth(int width, int i, int j);
    public abstract double getPositionInBoardLength(int length, int i, int j);
    public abstract Point getCurrentPosition(double zoom, Point p);
    
    public double zoomIn(double zoom) {
        double newZoom = zoom + 0.05;
        if(newZoom > maxZoomIn)
            return maxZoomIn;
        else
            return newZoom;
    }

    public double zoomOut(double zoom) {
        double newZoom = zoom - 0.05;
        if(newZoom < minZoomOut)
            return minZoomOut;
        else
            return newZoom;
    }
}
