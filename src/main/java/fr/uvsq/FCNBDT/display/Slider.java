package fr.uvsq.FCNBDT.display;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;


//CTRL + SHIFT + O pour générer les imports nécessaires

public class Slider extends JSlider {

    private JLabel contentValue;
    private Popup valueHover = null;

    public Slider(int Maximum) {
        super(0, Maximum);

        this.setValue(0);

        this.initContentPopup();
        //Cursor cursor = new Cursor(Cursor.HAND_CURSOR);

        this.setUI(new VideoSliderUI(this));
        //this.setOrientation(SwingConstants.VERTICAL);

        this.addClickListener();
    }

    private void initContentPopup() {
        contentValue = new JLabel();
        contentValue.setBackground(Color.LIGHT_GRAY);
        contentValue.setBorder(new TextBubbleBorder(Color.black, 1, 4, 3));
    }

    private void showValueHover(int xMouse) {
        if(this.valueHover!=null)
            this.valueHover.hide();
        VideoSliderUI v = (VideoSliderUI) this.getUI();
        
        int value = v.valueForXPosition(xMouse);
        this.contentValue.setText(String.valueOf(value));
        
        Point pos = this.getLocationOnScreen();
        int x = pos.x + xMouse - this.contentValue.getWidth()/2 - 1;
        int y = pos.y + v.getYTrackTop() - 25;
        
        this.valueHover = PopupFactory.getSharedInstance()
                .getPopup(this, contentValue, x, y);
        this.valueHover.show();
    }
    
    private void hideValueHover() {
        if(this.valueHover!=null)
            this.valueHover.hide();
        this.valueHover = null;
    }

    private void addClickListener() {
        SliderMouse mouse = new SliderMouse();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
    }
    
    private class SliderMouse extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            Slider s = (Slider) e.getSource();
            s.showValueHover(e.getX());
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            Slider s = (Slider) e.getSource();
            s.hideValueHover();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            Slider s = (Slider) e.getSource();
            VideoSliderUI v = (VideoSliderUI) s.getUI();
            int value = v.valueForXPosition(e.getX());
            s.setValue(value);
        }
    }

    private class VideoSliderUI extends BasicSliderUI {
        Color thumbColor;
        Color rangeColor;

        public VideoSliderUI(JSlider s) {
            super(s);
            thumbColor=Color.RED;
            rangeColor=Color.RED;
        }

        private Shape createThumbShape(int x, int y, int width, int height) {
            Ellipse2D shape = new Ellipse2D.Double(x, y, width, height);
            return shape;
        }

        @Override
        protected void calculateThumbSize() {
            super.calculateThumbSize();
            int thumbSize = (thumbRect.width<thumbRect.height)?thumbRect.width:thumbRect.height;
            thumbRect.setSize(thumbSize, thumbSize);
        }

        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            Stroke old = g2d.getStroke();
            g2d.setStroke(new BasicStroke(5f));
            Paint oldColor = g2d.getPaint();
            g2d.setPaint(Color.GRAY);

            if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
                //background track
                int mid = trackRect.y + trackRect.height / 2;
                g2d.drawLine(trackRect.x, mid, 
                        trackRect.x + trackRect.width, mid);

                //foreground track with range
                int ythumbMid = (int) thumbRect.getCenterY();
                int lowerXRange = trackRect.x ;
                int upperXRange = (int) thumbRect.getCenterX();
                g2d.setColor(rangeColor);
                g2d.drawLine(lowerXRange, ythumbMid, upperXRange, ythumbMid);
            }
            else if (slider.getOrientation() == SwingConstants.VERTICAL) {
                //background track
                int mid = trackRect.x + trackRect.width / 2;
                g2d.drawLine(mid, trackRect.y, 
                        mid, trackRect.y + trackRect.height);

                //foreground track with range
                int xthumbMid = (int) thumbRect.getCenterX();
                int lowerYRange = (int) thumbRect.getCenterY();
                int upperYRange = trackRect.y + trackRect.height;
                g2d.setColor(rangeColor);
                g2d.drawLine(xthumbMid, lowerYRange, xthumbMid, upperYRange);
            }
            g2d.setPaint(oldColor);
            g2d.setStroke(old);
        }

        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();

            Shape thumbShape = createThumbShape(thumbRect.x,
                    thumbRect.y, thumbRect.width-1, thumbRect.height-1);

            Object oldKeyValue = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(thumbColor);
            g2d.fill(thumbShape);

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    oldKeyValue);
        }

        /*public int getXPositionForValue(int value) {
            return this.xPositionForValue(value);
        }*/

        public int getYTrackTop() {
            return trackRect.y;
        }
    }

}