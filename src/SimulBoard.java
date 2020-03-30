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
    }

}
