package fr.uvsq.FCNBDT.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public enum CellType {
    SQUARE(0, new Dimension(26,26), 0.4, 5) {
        @Override
        public void paintBoard(Graphics2D g2d,
                Dimension d, double zoom,
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
            }
        }

        @Override
        public Dimension getBoardDimension(int width, int length, double zoom) {
            Dimension boardDim = new Dimension(
                    (int) ((width+1)*defDimension.width*zoom) + 1,
                    (int) ((length+1)*defDimension.height*zoom) + 1);
            return boardDim;
        }
    },
    HEXAGONE(1, new Dimension(24,28), 0.4, 5) {
        @Override
        public void paintBoard(Graphics2D g2d,
                Dimension d, double zoom,
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
        }

        @Override
        public Dimension getBoardDimension(int width, int length, double zoom) {
            Dimension boardDim = new Dimension(
                    (int) ((width+0.5)*defDimension.width*zoom)+1,
                    (int) (length*(defDimension.height*zoom*3./4)+defDimension.height*zoom*1./4)+1);
            return boardDim;
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
            Dimension panelDimension, double zoom,
            int[] matrice, int width, int length, int[] colors, BufferedImage[] icones);

    public abstract Dimension getBoardDimension(int width, int length, double zoom);
    
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
