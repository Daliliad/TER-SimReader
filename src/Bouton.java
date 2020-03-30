import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import display.Board;

public class Bouton extends JPanel {
    private JButton playOrPause;
    private SimulData simul;
    private SimulBoard board;
    private JButton previous;
    private JButton next;
    private JButton select;
    private JTextPane logs;

    public Bouton(SimulData sd, SimulBoard b, JTextPane l) throws IOException {
        this.simul = sd;
        this.board = b;
        this.logs = l;
        playOrPause = new JButton("▶");
        next = new JButton("suiv.");
        previous = new JButton("prec.");
        select = new JButton("select.");

        /*BufferedImage myPicture = ImageIO.read(new File(""));
       playOrPause = new JButton("Shopping", new ImageIcon(myPicture));*/
    }

    public void playOrPauseClick() {
        playOrPause.addActionListener(new ActionListener() {
            Timer timer = new Timer();
            @Override
            public void actionPerformed(ActionEvent e) {
                int begin = 0;
                int timeInterval = 1000;
                Object source = e.getSource();
                if (source instanceof JButton) {
                    if (playOrPause.getText().equals("▶")) {
                        playOrPause.setText("❚❚");
                        timer.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                try {
                                    simul.getReader().readNext(simul.getMatrice());
                                } catch (IOException e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }
                                board.setMatrice(simul.getMatrice());
                                board.revalidate();
                                board.repaint();
                            }
                        }, begin, timeInterval);
                    } else if (playOrPause.getText().equals("❚❚")) {
                        playOrPause.setText("▶");
                        timer.cancel();
                        timer= new Timer();
                    }
                }
            }
        });
    }

    public void nextClick() {
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(simul.getReader().readNext(simul.getMatrice()) != -1) {
                        if(simul.getReader().logExist(simul.getReader().getT()))
                            logs.setText(logs.getText() 
                                    + simul.getReader().getLog(simul.getReader().getT()) + "\n");
                        board.setMatrice(simul.getMatrice());
                        board.revalidate();
                        board.repaint();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                board.setMatrice(simul.getMatrice());
                board.revalidate();
                board.repaint();
            }
        });
    }

    public void prevClick() {
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    simul.getReader().readPrevious(simul.getMatrice());
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                board.setMatrice(simul.getMatrice());
                board.revalidate();
                board.repaint();
            }
        });
    }

    public void selectClick() {
        select.addActionListener(new ActionListener() {
            JFileChooser chooser = new JFileChooser();

            @Override
            public void actionPerformed(ActionEvent e) {
  
                try {
                    simul.getReader().readPrevious(simul.getMatrice());
                } catch (IOException e3) {
                    // TODO Auto-generated catch block
                    e3.printStackTrace();
                }
               
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Selectionner la trace à simuler");
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "log");
                chooser.setFileFilter(filter);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = chooser.getSelectedFile();
                    try {
                        simul= new SimulData(selectedFile.getCanonicalPath());
                        board.resetBoard(simul);
                        
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
    
    public void resetBoard() {
        
    }

    public void traitement() {
        this.playOrPauseClick();
        this.prevClick();
        this.nextClick();
        this.selectClick();

        this.add(previous);
        this.add(playOrPause);
        this.add(next);
        this.add(select);  
    }

}
