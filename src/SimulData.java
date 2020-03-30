import java.io.IOException;

import display.Board;
import reader.Reader;
import utils.CellType;

public class SimulData {
    private final Reader reader;
    private final int[] matrice;
    private final int[] colors;
    private final int width;
    private final int length;
    private final CellType cellType;
    
    public SimulData() throws IOException {
        reader = new Reader("traces/incendie/trace3.log");
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
    
    public SimulData(String path) throws IOException {
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
