package fr.uvsq.FCNBDT.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.nio.file.Paths;

import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.OverlayLayout;
import javax.swing.ScrollPaneConstants;

import fr.uvsq.FCNBDT.SimulData;
import fr.uvsq.FCNBDT.VideoCommands;

public class SimulBoard extends JPanel {
    
    private JLayeredPane layeredPane;
    private Board board;
    private JScrollPane scrBoard;
    
    public SimulBoard(int widthBoard) {
        super();
        board = new Board();
        this.setLayout(new GridLayout(1, 1));
        scrBoard = new JScrollPane();
        scrBoard.setViewportView(board);
        scrBoard.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrBoard.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        /*scrBoard.setPreferredSize(new Dimension(widthBoard,450));
        scrBoard.setMaximumSize(new Dimension(widthBoard,525));*/
        
        scrBoard.getViewport().putClientProperty("EnableWindowBlit", Boolean.TRUE);
        scrBoard.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        //scrBoard.setBounds(0, 0, widthBoard, 455);
        
        layeredPane = new JLayeredPane();
        //layeredPane.setPreferredSize(new Dimension(widthBoard,455));
        layeredPane.setLayout(new OverlayLayout(layeredPane));
        layeredPane.add(scrBoard, JLayeredPane.DEFAULT_LAYER);
        
        this.add(layeredPane);
        /*this.setPreferredSize(new Dimension(widthBoard,455));
        this.setMaximumSize(new Dimension(widthBoard,525));*/
    }
    
    private Dimension getCustomDimension() {
        if(this.getParent().getWidth()<700)
            return new Dimension(this.getParent().getWidth()-300,this.getParent().getHeight()-VideoCommands.BUTTON_PANEL_HEIGHT);
        return new Dimension(this.getParent().getWidth()-350,this.getParent().getHeight()-VideoCommands.BUTTON_PANEL_HEIGHT);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return getCustomDimension();
    }
    @Override
    public Dimension getMinimumSize() {
        return getCustomDimension();
    }
    @Override
    public Dimension getMaximumSize() {
        return getCustomDimension();
    }
    
    public void addZoomLayer(JPanel panel) {
        JPanel zoomLayer = new JPanel();
        zoomLayer.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.ipadx = 32;
        zoomLayer.add(panel, c);
        panel.setOpaque(false);
        zoomLayer.setOpaque(false);
        layeredPane.setLayer(zoomLayer, JLayeredPane.PALETTE_LAYER, 0);
        layeredPane.add(zoomLayer, JLayeredPane.PALETTE_LAYER);
    }
    
    public void resetBoard(SimulData data) {
        board.filled(data);
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
    
    public Point getCurrentPosition(Point p) {
        Point result = board.getCurrentPosition(p);
        return result;
    }
}
