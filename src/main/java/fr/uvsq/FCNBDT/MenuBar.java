package fr.uvsq.FCNBDT;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MenuBar extends JMenuBar {
    JMenu fileMenu;
    JMenuItem openFileMenu;
    JMenuItem closeFileMenu;
    
    JMenu menuLegende;
    JMenuItem afficheLegende;
    JFrame legende;
    
    VideoCommands videoButton;

    public MenuBar(VideoCommands videoButton) {
        this.videoButton = videoButton;
        
        fileMenu = new JMenu("Fichier");
        initOpenFileMenu();
        initCloseFileMenu();
        fileMenu.add(openFileMenu);
        fileMenu.add(closeFileMenu);
        this.add(fileMenu);
        
        initLegende();
        this.add(menuLegende);
    }
    
    private void initOpenFileMenu() {
        openFileMenu = new JMenuItem("Ouvrir Fichier");
        openFileMenu.addActionListener(new ActionListener() {
            JFileChooser chooser = new JFileChooser();

            public void actionPerformed(ActionEvent e) {
               
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Selectionner la trace à simuler");
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "log");
                chooser.setFileFilter(filter);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = chooser.getSelectedFile();
                    try {
                        videoButton.newFile(selectedFile);
                    } catch (IOException e1) {
                        String warning = "Le fichier specifie n'existe pas!";
                        JOptionPane.showMessageDialog(new JFrame(), warning);
                        //e1.printStackTrace();
                    } catch (ArithmeticException e2) {
                        String warning = "La trace n'est pas compatible!";
                        JOptionPane.showMessageDialog(new JFrame(), warning);
                    }
                } else {
                    System.out.println("No Selection ");
                }
            }

        });
    }
    
    private void initCloseFileMenu() {
        closeFileMenu = new JMenuItem("Fermer Fichier");
        closeFileMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                videoButton.noFile();
            }

        });
    }
    
    private void initLegende() {
        menuLegende = new JMenu("Légende");
        afficheLegende = new JMenuItem("Ouvrir Légende");
        menuLegende.add(afficheLegende);
        afficheLegende.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                legende.setVisible(true);
                legende.toFront();
            }
        });
        legende = new JFrame();
        legende.setLayout(new GridLayout(1, 1));
        legende.setPreferredSize(new Dimension(300, 300));
        legende.setMinimumSize(new Dimension(200, 100));
        legende.add(videoButton.getLegend());
        legende.setTitle("Légende");
        legende.pack();
        legende.setLocationRelativeTo( null );
    }
}
