import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

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
    
    VideoCommands videoButton;

    public MenuBar(VideoCommands videoButton) {
        this.videoButton = videoButton;
        
        fileMenu = new JMenu("Fichier");
        initOpenFileMenu();
        fileMenu.add(openFileMenu);

        this.add(fileMenu);
    }
    
    private void initOpenFileMenu() {
        openFileMenu = new JMenuItem("Ouvrir Fichier");
        openFileMenu.addActionListener(new ActionListener() {
            JFileChooser chooser = new JFileChooser();

            @Override
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
                        String warning = "Le fichier spécifié n'existe pas!";
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
}
