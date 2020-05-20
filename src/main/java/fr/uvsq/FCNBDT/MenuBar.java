package fr.uvsq.FCNBDT;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import fr.uvsq.FCNBDT.display.Board;

public class MenuBar extends JMenuBar {
    private JMenu fileMenu;
    private JMenuItem openFileMenu;
    private JMenuItem closeFileMenu;
    
    private JMenu legendMenu;
    private JMenuItem legendShow;
    private JFrame legend;
    
    private JMenu saveMenu;
    private JMenuItem saveImage;
    
    private VideoCommands videoButton;
    
    private File currentDirectory; 

    public MenuBar(VideoCommands videoButton) {
        this.videoButton = videoButton;
        this.currentDirectory = new File(".");
        
        fileMenu = new JMenu("Fichier");
        initOpenFileMenu();
        initCloseFileMenu();
        fileMenu.add(openFileMenu);
        fileMenu.add(closeFileMenu);
        this.add(fileMenu);

        saveMenu = new JMenu("Enregistrer");
        initSaveImage();
        this.add(saveMenu);
        
        initLegend();
        this.add(legendMenu);
    }
    
    private void initOpenFileMenu() {
        openFileMenu = new JMenuItem("Ouvrir Fichier");
        openFileMenu.addActionListener(new ActionListener() {
            JFileChooser chooser = new JFileChooser();

            public void actionPerformed(ActionEvent e) {
               
                chooser.setCurrentDirectory(currentDirectory);
                chooser.setDialogTitle("Selectionner la trace à simuler");
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier trace .log", "log");
                chooser.setFileFilter(filter);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = chooser.getSelectedFile();
                    try {
                        videoButton.newFile(selectedFile);
                        currentDirectory = chooser.getCurrentDirectory();
                    } catch (IOException e1) {
                        String warning = "Le fichier specifie n'existe pas!";
                        JOptionPane.showMessageDialog(new JFrame(), warning);
                        //e1.printStackTrace();
                    } catch (ArithmeticException e2) {
                        String warning = "La trace n'est pas compatible!";
                        JOptionPane.showMessageDialog(new JFrame(), warning);
                    }
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
    
    private void initLegend() {
        legendMenu = new JMenu("Légende");
        legendShow = new JMenuItem("Ouvrir Légende");
        legendMenu.add(legendShow);
        legendShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                legend.setVisible(true);
                legend.toFront();
            }
        });
        legend = new JFrame();
        legend.setLayout(new GridLayout(1, 1));
        legend.setPreferredSize(new Dimension(300, 300));
        legend.setMinimumSize(new Dimension(200, 100));
        legend.add(videoButton.getLegend());
        legend.setTitle("Légende");
        legend.pack();
        legend.setLocationRelativeTo( null );
    }
    
    private void initSaveImage() {
        saveImage = new JMenuItem("Enregistrer image actuelle");
        saveImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Board board = videoButton.getBoard();
                if (!board.isFilled()) {
                    String warning = "Aucun fichier ouvert";
                    JOptionPane.showMessageDialog(new JFrame(), warning);
                    return;
                }
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(currentDirectory);
                chooser.setDialogTitle("Enregistrer une image");
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier image .png", "png");
                chooser.setFileFilter(filter);
                if (chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
                    File f = chooser.getSelectedFile();
                    BufferedImage image = new BufferedImage(board.getWidth(), board.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                    Graphics g = image.getGraphics();
                    board.paint(g);
                    if(!f.getPath().endsWith(".png")) {
                        f = new File(f.getPath() + ".png");
                    }
                    try {
                        ImageIO.write(image, "PNG", f);
                    } catch (IOException e) {
                        String warning = "Erreur d'enregistrement";
                        JOptionPane.showMessageDialog(new JFrame(), warning);
                    }
                    String warning = "Image enregistrée";
                    JOptionPane.showMessageDialog(new JFrame(), warning);
                }
            }
        });
        saveMenu.add(saveImage);
    }
}
