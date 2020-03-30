import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import reader.Reader;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import display.Board;

public class Bouton extends JPanel {
   //final private JPanel buttons;
   private JButton playOrPause;
   private Reader reader;
   private int[] matrice;
   private Board board;
   private JButton previous;
   private JButton next;
   private JTextPane logs;
   
   public Bouton(Reader r, int[] mat, Board b, JTextPane l) {
       this.reader = r;
       this.matrice = mat;
       board = new Board();
       logs = new JTextPane();
       //buttons = new JPanel();
       playOrPause = new JButton("▶");
       next = new JButton("suiv.");
       previous = new JButton("prec.");
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
                                   reader.readNext(matrice);
                               } catch (IOException e1) {
                                   // TODO Auto-generated catch block
                                   e1.printStackTrace();
                               }
                               board.setMatrice(matrice);
                               //System.err.println(matrice.toString());
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
   
   public void suivClick() {
       next.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               try {
                   if(reader.readNext(matrice) != -1) {
                       if(reader.logExist(reader.getT()))
                           logs.setText(logs.getText() + reader.getLog(reader.getT()) + "\n");
                       board.setMatrice(matrice);
                       board.revalidate();
                       board.repaint();
                   }
               } catch (IOException e1) {
                   e1.printStackTrace();
               }
               board.setMatrice(matrice);
               //System.err.println(matrice.toString());
               board.revalidate();
               board.repaint();
           }
       });
   }
   
   public void precClick() {
       previous.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               try {
                   reader.readPrevious(matrice);
               } catch (IOException e1) {
                   // TODO Auto-generated catch block
                   e1.printStackTrace();
               }
               board.setMatrice(matrice);
               board.revalidate();
               board.repaint();
           }
       });
   }
   
   public void traitement() {
       this.playOrPauseClick();
       this.precClick();
       this.suivClick();
       
       this.add(previous);
       this.add(playOrPause);
       this.add(next);
   }

}
