package fr.uvsq.FCNBDT;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;

public class LogsPanel extends JPanel {
    JTextPane logs;
    JScrollPane scrlogs;

    JTextPane infos;
    JScrollPane scrinfo;
    
    private final int infoHeight = 70;

    public LogsPanel() {
        super();
        this.setLayout(new BorderLayout());
        //this.setPreferredSize(new Dimension(width, 425));

        initLogs();
        this.add(scrlogs);
        initInfos();
        this.add(scrinfo, BorderLayout.NORTH);

    }

    private void initLogs() {
        logs = new JTextPane();
        logs.setEditable(false);

        scrlogs = new JScrollPane(logs){
            private Dimension getCustomDimension() {
                return new Dimension(this.getParent().getWidth(),this.getParent().getHeight()-infoHeight);
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
        };
        scrlogs.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void initInfos() {
        //Second champ pour les infos//
        infos = new JTextPane();
        infos.setEditable(false);

        scrinfo = new JScrollPane(infos) {
            private Dimension getCustomDimension() {
                return new Dimension(this.getParent().getWidth(), infoHeight);
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
        };
        scrinfo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrinfo.getViewport().putClientProperty("EnableWindowBlit", Boolean.TRUE);
        scrinfo.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
    }
    
    private Dimension getCustomDimension() {
        return new Dimension(this.getParent().getWidth(),this.getParent().getHeight()-VideoCommands.INFO_POSITION_PANEL_HEIGHT);
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

    public JTextPane getLogs() {
        return logs;
    }

    public void rebootLogs(String staticLog) {
        logs.setText("");
        infos.setText(staticLog);
    }
    
    public void noLogs() {
        logs.setText("");
        infos.setText("");
    }

    public void addTextLogs(int tps, String str) {
        logs.setText(logs.getText() 
                + tps + " : " + str + "\n");
    }
}
