package fr.uvsq.FCNBDT.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import fr.uvsq.FCNBDT.reader.LegendReader;

public class Legend extends JPanel {
    private Board board;
    private String pathLegend;
    
    private ArrayList<JPanel> etatsCellules;
    private JPanel content;
    private JScrollPane scrContent;
    private GridBagConstraints gridConstraint;
    private Map<Integer, String> nomsEtats;
    
    public Legend(Board board) {
        this.board = board;
        etatsCellules = new ArrayList<>();
        nomsEtats = new HashMap<Integer, String>();
        this.content = new JPanel();
        this.scrContent = new JScrollPane(content);
        scrContent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrContent.setBorder(new EmptyBorder(2, 2, 2, 2));
        gridConstraint = new GridBagConstraints();
        gridConstraint.fill = GridBagConstraints.HORIZONTAL;
        gridConstraint.anchor = GridBagConstraints.FIRST_LINE_START;
        gridConstraint.gridx = 0;
        gridConstraint.gridy = GridBagConstraints.RELATIVE;
        gridConstraint.weightx = 1;
        gridConstraint.weighty = 0;
        gridConstraint.ipadx = 4;
        content.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(300, 300));
        scrContent.setPreferredSize(new Dimension(280, 255));
        this.add(scrContent);
        this.noLegend();
    }
    
    
    public void setLegend(String path) {
        content.removeAll();
        etatsCellules.clear();
        nomsEtats.clear();
        
        this.pathLegend = Paths.get(Paths.get(path).toAbsolutePath().getParent().toString(),
                "legende.txt").toString();
        nomsEtats = LegendReader.readLegend(this.pathLegend);
        System.out.println(nomsEtats.toString());
        
        if(nomsEtats.isEmpty()) {
            content.add(new JLabel("Aucune légende disponible"));
        } else {
            for (int i : nomsEtats.keySet()) {
                etatsCellules.add(this.createEtatCellule(i, nomsEtats.get(i)));
            }
            for (int i = 0; i < etatsCellules.size()-1; i++) {
                content.add(etatsCellules.get(i), gridConstraint);
            }
            gridConstraint.weighty = 1;
            content.add(etatsCellules.get(etatsCellules.size()-1), gridConstraint);
            gridConstraint.weighty = 0;
        }
        this.revalidate();
        this.repaint();
    }
    
    public JPanel createEtatCellule(int indice, String commentaire) {
        JPanel panel = new JPanel(new BorderLayout());
        LegendElementPanel graphic = new LegendElementPanel(indice);
        panel.add(graphic, BorderLayout.WEST);
        JLabel label = new JLabel(commentaire);
        label.setOpaque(true);
        panel.add(label);
        return panel;
    }
    
    public void noLegend() {
        content.removeAll();
        etatsCellules.clear();
        nomsEtats.clear();
        System.out.println("No legend");
        content.add(new JLabel("Aucun fichier ouvert"));
        this.revalidate();
        this.repaint();
    }
    
    private class LegendElementPanel extends JPanel {
        private int indice;
        
        public LegendElementPanel (int indice) {
            this.indice = indice;
            this.setPreferredSize(new Dimension(70,30));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            board.paintLegendElement(g2d, indice);
        }
    }
    
}
