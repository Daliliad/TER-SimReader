package fr.uvsq.FCNBDT;

import java.awt.BorderLayout;
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

    public LogsPanel(int width) {
        super();

        this.setLayout(new BorderLayout());
        this.setMaximumSize(new Dimension(1000, 525));
        this.setPreferredSize(new Dimension(width, 455));

        initLogs(width);
        this.add(scrlogs);

        initInfos(width);
        this.add(scrinfo, BorderLayout.NORTH);

    }

    private void initLogs(int width) {
        logs = new JTextPane();
        logs.setMaximumSize(new Dimension(1000, 50));
        /*logs.setText("test test test test \n"
                + "test test test test \n"
                + "test \n");*/
        logs.setPreferredSize(new Dimension(width, 355));
        logs.setEditable(false);

        scrlogs = new JScrollPane(logs);
        scrlogs.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrlogs.setMaximumSize(new Dimension(1000, 150));
        scrlogs.setPreferredSize(new Dimension(width, 355));
    }

    private void initInfos(int width) {
        //Second champ pour les infos//
        infos = new JTextPane();
        infos.setMaximumSize(new Dimension(1000, 500));
        infos.setPreferredSize(new Dimension(width, 100));
        infos.setEditable(false);

        scrinfo = new JScrollPane(infos);
        scrinfo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrinfo.setMaximumSize(new Dimension(1000, 500));
        scrinfo.setPreferredSize(new Dimension(width, 100));
        scrinfo.getViewport().putClientProperty("EnableWindowBlit", Boolean.TRUE);
        scrinfo.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
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

    public void addTextLogs(String str) {
        logs.setText(logs.getText() 
                + str + "\n");
    }
}
