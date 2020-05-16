package fr.uvsq.FCNBDT;

import java.awt.Dimension;
import java.nio.file.Paths;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import fr.uvsq.FCNBDT.display.Board;

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
        board.filled(data);
        
        System.out.println(Paths.get(Paths.get(data.getReader().getPath()).toAbsolutePath().getParent().toString(),
                "icones").toString());
        
        this.scrBoard.getVerticalScrollBar().setValue(0);
        this.scrBoard.getHorizontalScrollBar().setValue(0);
        scrBoard.revalidate();
    }
    
    public void noBoard() {
        board.unfilled();
        this.scrBoard.getVerticalScrollBar().setValue(0);
        this.scrBoard.getHorizontalScrollBar().setValue(0);
        scrBoard.revalidate();
    }
    
    public Board getBoard() {
        return board;
    }
    
    public void jumpTo(int i, int j) {
        JScrollBar vertical = this.scrBoard.getVerticalScrollBar();
        int value = (int) ((vertical.getMaximum()-vertical.getMinimum())*this.board.getPourcentagePositionI(i, j));
        value -= vertical.getHeight()/2;
        vertical.setValue(value);
        JScrollBar horizontal = this.scrBoard.getHorizontalScrollBar();
        value = (int) ((horizontal.getMaximum()-horizontal.getMinimum())*this.board.getPourcentagePositionJ(i, j));
        value -= horizontal.getWidth()/2;
        horizontal.setValue(value);
        this.board.select(i, j);
    }
    
    public void jumpToDeselect() {
        this.board.deselect();
    }
}
