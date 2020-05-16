package fr.uvsq.FCNBDT;

import java.io.IOException;

import fr.uvsq.FCNBDT.reader.Reader;
import fr.uvsq.FCNBDT.utils.CellType;

public class SimulData {
    private boolean isFilled;
    private Reader reader;
    private int[] matrice;
    private int[] colors;
    private int width;
    private int length;
    private CellType cellType;
    
    public SimulData() throws IOException {
        isFilled = false;
        this.noData();
        /*reader = new Reader("traces/incendie/trace3.log");
        matrice = new int[reader.getLength()*reader.getWidth()];
        colors = reader.getColors();
        for(int i=0;i<colors.length;i++) {
            System.out.println(i+" : "+colors[i]);
        }
        width = reader.getWidth();
        length = reader.getLength();
        cellType = reader.getCellType();

        reader.readNext(matrice);*/
    }
    
    public void resetData(String path) throws IOException {
        isFilled = true;
        reader = new Reader(path);
        matrice = new int[reader.getLength()*reader.getWidth()];
        colors = reader.getColors();
        for(int i=0;i<colors.length;i++) {
            System.out.println(i+" : "+colors[i]);
        }
        width = reader.getWidth();
        length = reader.getLength();
        cellType = reader.getCellType();

        reader.readNext(matrice);
    }
    
    public void noData() {
        reader = null;
        matrice = null;
        colors = null;
        width = 0;
        length = 0;
        cellType = null;
        isFilled = false;
    }
    
    public boolean isFilled() {
        return isFilled;
    }

    public int[] getMatrice() {
        return matrice;
    }
    
    public int[] getColors() {
        return colors;
    }
    
    public Reader getReader() {
        return reader;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public CellType getCellType() {
        return cellType;
    }
    
}
