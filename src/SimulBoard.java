import java.awt.Dimension;
import java.nio.file.Paths;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import display.Board;

public class SimulBoard extends JPanel {
    
    private Board board;
    private JScrollPane scrBoard;
    
    public SimulBoard(int widthBoard) {
        super();
        board = new Board();
        scrBoard = new JScrollPane();
        scrBoard.setViewportView(board);
        scrBoard.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrBoard.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        scrBoard.setPreferredSize(new Dimension(widthBoard,455));
        scrBoard.setMaximumSize(new Dimension(widthBoard,525));
        
        this.add(scrBoard);
        this.setPreferredSize(new Dimension(widthBoard,450));
        this.setMaximumSize(new Dimension(widthBoard,525));
    }
    
    public void resetBoard(SimulData data) {
        board.setWidth(data.getWidth());
        board.setLength(data.getLength());
        board.setCellType(data.getCellType());
        board.setColors(data.getColors());
        board.setMatrice(data.getMatrice());
        board.setIcones(Paths.get(Paths.get(data.getReader().getPath()).toAbsolutePath().getParent().toString(),
                "icones").toString());
        System.out.println(Paths.get(Paths.get(data.getReader().getPath()).toAbsolutePath().getParent().toString(),
                "icones").toString());
        
        board.revalidate();
        board.repaint();
        
        scrBoard.revalidate();
    }
    
    public Board getBoard() {
        return board;
    }
}
