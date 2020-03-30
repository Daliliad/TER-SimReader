import java.nio.file.Paths;

import display.Board;

public class SimulBoard extends Board {
    public SimulBoard() {
        super();
    }
    
    public void resetBoard(SimulData data) {
        this.setWidth(data.getWidth());
        this.setLength(data.getLength());
        this.setCellType(data.getCellType());
        this.setColors(data.getColors());
        this.setMatrice(data.getMatrice());
        this.revalidate();
        this.repaint();
        this.setIcones(Paths.get(Paths.get(data.getReader().getPath()).toAbsolutePath().getParent().toString(),
                "icones").toString());
        System.out.println(Paths.get(Paths.get(data.getReader().getPath()).toAbsolutePath().getParent().toString(),
                "icones").toString());
    }

}
