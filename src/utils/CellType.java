package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public enum CellType {
    SQUARE(0, new Dimension(6,6), new Dimension(26,26), new Dimension(150,150)) {
        @Override
        public void paintBoard(Graphics2D g2d,
                Dimension d, Dimension caseDimension,
                int[] matrice, int width, int length, int[] colors, BufferedImage[] icones) {
            if(matrice != null && matrice.length == width*length && colors != null)
            {
                int scale = caseDimension.height;
                int xP = 0;
                int yP = 0;
                for(int i = 0; i < length; i++) {
                    for(int j = 0; j < width; j++) {
                        xP = j*scale;
                        yP = i*scale;
                        g2d.setColor(new Color(colors[matrice[j*width+i]]));
                        if(d.width/width>15 && icones[matrice[j*width+i]]!=null)
                            g2d.drawImage(icones[matrice[j*width+i]],xP,yP,scale,scale,null);
                        else
                            g2d.fillRect(xP, yP, scale, scale);
                        g2d.setColor(Color.black);
                        g2d.drawRect(xP, yP, scale, scale);
                    }
                }
            }
        }

        /*
        @Override
        public Dimension defautDimension(Dimension panelDimension, int width, int length) {
            int scale = (panelDimension.width/width<panelDimension.height/length)?
                    panelDimension.width/width:panelDimension.height/length;
            Dimension dim = new Dimension(scale, scale);
            if(dim.height < minDimension.height || dim.width < minDimension.width)
                return minDimension;
            if(dim.height > maxDimension.height || dim.width > maxDimension.width)
                return maxDimension;
            return dim;
        }*/

        /*
        @Override
        public void zoomIn(Dimension dim) {
            Dimension newD = new Dimension(dim.width + ZOOM_SIZE, dim.height + ZOOM_SIZE);
            if(newD.width > maxDimension.width || newD.height > maxDimension.height)
                dim.setSize(maxDimension);
            else
                dim.setSize(newD);
        }

        @Override
        public void zoomOut(Dimension dim) {
            Dimension newD = new Dimension(dim.width - defDimension.width/ZOOM_COEFF_DIV, dim.height - ZOOM_SIZE);
            if(newD.width < minDimension.width || newD.height < minDimension.height)
                dim.setSize(minDimension);
            else
                dim.setSize(newD);
        }*/
    },
    HEXAGONE(1, new Dimension(9,10), new Dimension(24,28), new Dimension(152,175)) {
        @Override
        public void paintBoard(Graphics2D g2d,
                Dimension d, Dimension caseDimension,
                int[] matrice, int width, int length, int[] colors, BufferedImage[] icones) {
            int xP[] = new int[6];
            int yP[] = new int[6];
            int xScale = caseDimension.width/2;
            int yScale = caseDimension.height/4;
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
                    if(4*yScale>15 && icones[matrice[j*width+i]]!=null)
                        g2d.drawImage(icones[matrice[j*width+i]],xP[0],yP[5],2*xScale,4*yScale,null);
                    else
                        g2d.fillPolygon(xP,yP,6);
                    g2d.setColor(Color.black);
                    g2d.drawPolygon(xP, yP, 6);
                }
            }
        }

        /*
        @Override
        public Dimension defautDimension(Dimension panelDimension, int width, int length) {
            int xScale = (int) (((double) panelDimension.width)/(2*width+1));
            xScale = xScale
                    + (minDimension.width % ZOOM_SIZE - (xScale*2) % ZOOM_SIZE)/2;
            int yScale = (int) (xScale/(Math.sqrt(3)));
            if((yScale*3*length+1)>panelDimension.height) {
                yScale=(int) (((double) panelDimension.height)/(3*length+1));
                yScale = yScale
                        + (minDimension.height % ZOOM_SIZE - (yScale*4) % ZOOM_SIZE)/4;
                xScale=(int) (yScale*Math.sqrt(3));
            }
            
            xScale = xScale*2;
            xScale = xScale
                    + (minDimension.width % ZOOM_SIZE - xScale % ZOOM_SIZE);
            yScale = yScale*4;
            yScale = yScale
                    + (minDimension.height % ZOOM_SIZE - yScale % ZOOM_SIZE);
            Dimension dim = new Dimension(xScale, yScale);
            System.out.println("Dimension hexa = "+dim.toString());
            if(dim.height < minDimension.height || dim.width < minDimension.width)
                return minDimension;
            if(dim.height > maxDimension.height || dim.width > maxDimension.width)
                return maxDimension;
            return dim;
        }*/

        /*
        @Override
        public void zoomIn(Dimension dim) {
            Dimension newD = new Dimension(dim.width + defDimension.width/ZOOM_COEFF_DIV, dim.height + defDimension.height/ZOOM_COEFF_DIV);
            System.out.println(newD);
            if(newD.width > maxDimension.width || newD.height > maxDimension.height)
                dim.setSize(maxDimension);
            else
                dim.setSize(newD);
        }

        @Override
        public void zoomOut(Dimension dim) {
            Dimension newD = new Dimension(dim.width - defDimension.width/ZOOM_COEFF_DIV, dim.height - defDimension.height/ZOOM_COEFF_DIV);
            System.out.println(newD);
            if(newD.width < minDimension.width || newD.height < minDimension.height)
                dim.setSize(minDimension);
            else
                dim.setSize(newD);
        }*/
    };

    public final int i;
    public final Dimension minDimension;
    public final Dimension defDimension;
    public final Dimension maxDimension;
    
    public static final double ZOOM_COEFF = 0.15;

    CellType(int i, Dimension min, Dimension def, Dimension max) {
        this.i = i;
        this.minDimension = min;
        this.defDimension = def;
        this.maxDimension = max;
    }

    public abstract void paintBoard(Graphics2D g2d,
            Dimension panelDimension, Dimension caseDimension,
            int[] matrice, int width, int length, int[] colors, BufferedImage[] icones);
    
    //public abstract Dimension defautDimension(Dimension panelDimension, int width, int length);
    
    public void zoomIn(Dimension dim) {
        Dimension newD = new Dimension((int) (dim.width*(1+ZOOM_COEFF)), (int) (dim.height*(1+ZOOM_COEFF)));
        System.out.println(newD);
        if(newD.width > maxDimension.width || newD.height > maxDimension.height)
            dim.setSize(maxDimension);
        else
            dim.setSize(newD);
    }

    public void zoomOut(Dimension dim) {
        Dimension newD = new Dimension((int) (dim.width*(1-ZOOM_COEFF)), (int) (dim.height*(1-ZOOM_COEFF)));
        System.out.println(newD);
        if(newD.width < minDimension.width || newD.height < minDimension.height)
            dim.setSize(minDimension);
        else
            dim.setSize(newD);
    }
    
}
