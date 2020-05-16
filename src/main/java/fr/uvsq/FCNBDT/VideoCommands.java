package fr.uvsq.FCNBDT;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.uvsq.FCNBDT.display.Slider;

public class VideoCommands extends JPanel {
    private SimulData simul;
    private SimulBoard board;
    private LogsPanel logs;

    private JPanel buttons;
    private JButton playOrPause;
    private JButton previous;
    private JButton next;
    private JButton speed;
    private JButton slow;
    private JButton zoomIn;
    private JButton zoomOut;
    private JButton changeSpeed;
    private JButton begin;

    private JTextField enterSpeed;
    private JLabel ms;
    
    private JTextField enterJumpI;
    private JLabel jumpI;
    private JTextField enterJumpJ;
    private JLabel jumpJ;
    private JButton jumpTo;
    private JButton jumpToDeselect;
    
    private JLabel descriptive; 

    private Slider slider;

    private Timer timer;
    private int timeInterval;
    
    private static final String PLAY_STRING = "play";
    private static final String PAUSE_STRING = "pause";

    public VideoCommands(SimulData sd, SimulBoard b, LogsPanel l, int frameWidth) throws IOException {
        super();
        this.simul = sd;
        this.board = b;
        this.logs = l;
        timer = new Timer();

        enterSpeed = new JTextField();
        ms = new JLabel("ms");
        descriptive = new JLabel("Le temps d'intervalle est de: 1000 ms");
        
        enterJumpI = new JTextField();
        jumpI = new JLabel("i :");
        enterJumpJ = new JTextField();
        jumpJ = new JLabel("j :");

        initButtons(frameWidth);
        initSlider(frameWidth);

        this.setPreferredSize(new Dimension(frameWidth,75));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.timeInterval = 1000;

        //logs.rebootLogs(simul.getReader().getLog(-1));
        logs.noLogs();

        /*BufferedImage myPicture = ImageIO.read(new File(""));
       playOrPause = new JButton("Shopping", new ImageIcon(myPicture));*/
    }

    private void initButtons(int frameWidth) {
        playOrPause = new JButton(PLAY_STRING);
        next = new JButton(">");
        previous = new JButton("<");
        speed = new JButton("acc.");
        slow = new JButton("dec.");
        zoomIn = new JButton("+");
        zoomOut = new JButton("-");
        jumpTo = new JButton("saut");
        jumpToDeselect = new JButton("X");
        changeSpeed = new JButton("modif.");
        begin = new JButton("debut");

        previous.setMargin(new Insets(0, 0, 0, 0));
        previous.setPreferredSize(new Dimension(35,35));
        playOrPause.setMargin(new Insets(0, 0, 0, 0));
        playOrPause.setPreferredSize(new Dimension(50,35));
        next.setMargin(new Insets(0, 0, 0, 0));
        next.setPreferredSize(new Dimension(35,35));
        speed.setMargin(new Insets(0, 0, 0, 0));
        speed.setPreferredSize(new Dimension(40,35));
        slow.setMargin(new Insets(0, 0, 0, 0));
        slow.setPreferredSize(new Dimension(40,35));
        zoomIn.setMargin(new Insets(0, 0, 0, 0));
        zoomIn.setPreferredSize(new Dimension(35,35));
        zoomOut.setMargin(new Insets(0, 0, 0, 0));
        zoomOut.setPreferredSize(new Dimension(35,35));
        jumpTo.setMargin(new Insets(0, 0, 0, 0));
        jumpTo.setPreferredSize(new Dimension(50,35));
        jumpToDeselect.setMargin(new Insets(0, 0, 0, 0));
        jumpToDeselect.setPreferredSize(new Dimension(50,35));
        changeSpeed.setMargin(new Insets(0, 0, 0, 0));
        changeSpeed.setPreferredSize(new Dimension(50,35));
        begin.setMargin(new Insets(0, 0, 0, 0));
        begin.setPreferredSize(new Dimension(45,35));

        buttons = new JPanel();
        buttons.setPreferredSize(new Dimension(frameWidth,50));
        buttons.setLayout(new FlowLayout());
        buttons.add(zoomIn);
        buttons.add(zoomOut);
        buttons.add(jumpI);
        buttons.add(enterJumpI);
        buttons.add(jumpJ);
        buttons.add(enterJumpJ);
        buttons.add(jumpTo);
        buttons.add(jumpToDeselect);
        jumpToDeselect.setVisible(false);
        buttons.add(begin);
        buttons.add(slow);
        buttons.add(previous);
        buttons.add(playOrPause);
        buttons.add(next);
        buttons.add(speed);
        buttons.add(enterSpeed);
        buttons.add(ms);
        buttons.add(changeSpeed);
        buttons.add(descriptive);
    }

    private void initSlider(int frameWidth) {
        slider = new Slider(simul.isFilled()?simul.getReader().getTmax():0);
        slider.setPreferredSize(new Dimension((int) (frameWidth*0.8),20));
        slider.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent event){
                try {
                    if (!simul.isFilled()) {
                        String warning = "Aucun fichier ouvert.";
                        JOptionPane.showMessageDialog(new JFrame(), warning);
                        return;
                    }
                    if(simul.getReader().readExactTime(simul.getMatrice(), ((JSlider)event.getSource()).getValue()) != -1) {
                        if(simul.getReader().logExist(simul.getReader().getT()))
                            logs.addTextLogs(simul.getReader().getLog(simul.getReader().getT()));
                        board.getBoard().setMatrice(simul.getMatrice());
                        board.getBoard().revalidate();
                        board.getBoard().repaint();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void playOrPauseClick() {
        playOrPause.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (!simul.isFilled()) {
                    String warning = "Aucun fichier ouvert.";
                    JOptionPane.showMessageDialog(new JFrame(), warning);
                    return;
                }
                int begin = 0;
                Object source = e.getSource();
                if (source instanceof JButton) {
                    if (playOrPause.getText().equals(PLAY_STRING)) {
                        playOrPause.setText(PAUSE_STRING);
                        timer.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                /*try {
                                    simul.getReader().readNext(simul.getMatrice());
                                } catch (IOException e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }
                                board.setMatrice(simul.getMatrice());
                                board.revalidate();
                                board.repaint();*/
                                slider.setValue(slider.getValue()+1);
                            }
                        }, begin, timeInterval);
                    } else if (playOrPause.getText().equals(PAUSE_STRING)) {
                        playOrPause.setText(PLAY_STRING);
                        timer.cancel();
                        timer= new Timer();
                    }
                }
            }
        });
    }

    private void nextClick() {
        next.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                if (!simul.isFilled()) {
                    String warning = "Aucun fichier ouvert.";
                    JOptionPane.showMessageDialog(new JFrame(), warning);
                    return;
                }
                slider.setValue(slider.getValue()+1);
            }
        });
    }



    private void prevClick() {
        previous.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                if (!simul.isFilled()) {
                    String warning = "Aucun fichier ouvert.";
                    JOptionPane.showMessageDialog(new JFrame(), warning);
                    return;
                }
                slider.setValue(slider.getValue()-1);
            }
        });
    }

    private void speedUpClick() {
        speed.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                int test = timeInterval - 100;
                if (test > 0) {
                    timeInterval = timeInterval - 100;
                    descriptive.setText("Le temps d'intervalle est de: " + timeInterval + " ms");
                    if (playOrPause.getText().equals(PAUSE_STRING)) {
                            playOrPause.doClick();
                            playOrPause.doClick();
                    }
                }
                else {
                    String warning = "La vitesse est deja au maximum!";
                    JOptionPane.showMessageDialog(new JFrame(), warning);
                }
            }
        });
    }

    private void slowDownClick() {
        slow.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                int test = timeInterval + 100;
                if (test < 60000) {
                    timeInterval = timeInterval + 100;
                    descriptive.setText("Le temps d'intervalle est de: " + timeInterval + " ms");
                    if (playOrPause.getText().equals(PAUSE_STRING)) {
                            playOrPause.doClick();
                            playOrPause.doClick();
                    }
                }
                else {
                    String warning = "La vitesse est a  1 minutes par etape."
                            + "\n Elle ne peut plus etre diminuee.";
                    JOptionPane.showMessageDialog(new JFrame(), warning);
                }
            }
        });
    }
    
    private void zoomInClick() {
        zoomIn.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                if (!simul.isFilled()) {
                    String warning = "Aucun fichier ouvert.";
                    JOptionPane.showMessageDialog(new JFrame(), warning);
                    return;
                }
                board.getBoard().zoomIn();
            }
        });
    }
    
    private void zoomOutClick() {
        zoomOut.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                if (!simul.isFilled()) {
                    String warning = "Aucun fichier ouvert.";
                    JOptionPane.showMessageDialog(new JFrame(), warning);
                    return;
                }
                board.getBoard().zoomOut();
            }
        });
    }

    public void newFile(File selectedFile) throws IOException {
        simul.resetData(selectedFile.getCanonicalPath());

        if (playOrPause.getText().equals("pause")) {
            playOrPause.doClick();
        }
        board.resetBoard(simul);

        slider.setValue(0);
        slider.setMaximum(simul.getReader().getTmax());

        logs.rebootLogs(simul.getReader().getLog(-1));

        ((Frame) this.getParent().getParent().getParent().getParent())
        .setTitle(simul.getReader().getPath());
        jumpToDeselect.setVisible(false);
        board.jumpToDeselect();
    }
    
    public void noFile() {
        if (playOrPause.getText().equals("pause")) {
            playOrPause.doClick();
        }
        slider.setValue(0);
        slider.setMaximum(0);
        logs.noLogs();
        ((Frame) this.getParent().getParent().getParent().getParent())
            .setTitle("Visualisateur d'Automates cellulaires");
        jumpToDeselect.setVisible(false);
        simul.noData();
        board.noBoard();
    }

    public void enterTime() {
        enterSpeed.setColumns(4);
        enterSpeed.setPreferredSize(new Dimension(35, 35));
        TextPrompt tp = new TextPrompt("1000", enterSpeed);
        tp.setForeground( Color.GRAY );
        tp.changeStyle(Font.ITALIC + Font.BOLD);
    }

    public void changeSpeedClick() {
        changeSpeed.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                if (enterSpeed.getText().isEmpty()) {
                    timeInterval = 1000;
                    descriptive.setText("Le temps d'intervalle est de: " + timeInterval + " ms");
                } else {
                    try {
                        int val = Integer.parseInt(enterSpeed.getText());
                        if (val > 0 && val < 60000) {
                            timeInterval = Integer.parseInt(enterSpeed.getText());
                            descriptive.setText("Le temps d'intervalle est de: " + timeInterval + " ms");
                            if (playOrPause.getText().equals(PAUSE_STRING)) {
                                    playOrPause.doClick();
                                    playOrPause.doClick();
                            }
                        } else if (val <= 0) {
                            String warning = "Le temps d'intervalle ne peut être négatif ou nul!";
                            JOptionPane.showMessageDialog(new JFrame(), warning);
                        } else {
                            String warning = "Le temps d'intervalle maximum est d'1 minute!";
                            JOptionPane.showMessageDialog(new JFrame(), warning);
                        } 
                    } catch (NumberFormatException e1) {
                        String warning = "Le temps d'intervalle doit être un entier!";
                        JOptionPane.showMessageDialog(new JFrame(), warning);
                    }
                }
            }
        });
    }
    
    public void enterJump() {
        enterJumpI.setColumns(4);
        enterJumpJ.setColumns(4);
        enterJumpI.setPreferredSize(new Dimension(35, 35));
        enterJumpJ.setPreferredSize(new Dimension(35, 35));
        TextPrompt tp = new TextPrompt("0", enterJumpI);
        TextPrompt tp2 = new TextPrompt("0", enterJumpJ);
        tp.setForeground( Color.GRAY );
        tp.changeStyle(Font.ITALIC + Font.BOLD);
        tp2.setForeground( Color.GRAY );
        tp2.changeStyle(Font.ITALIC + Font.BOLD);
    }
    
    public void jumpTo() {
        jumpTo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!simul.isFilled()) {
                    String warning = "Aucun fichier ouvert.";
                    JOptionPane.showMessageDialog(new JFrame(), warning);
                    return;
                }
                int i, j;
                if (enterJumpI.getText().isEmpty()) {
                    i = 0;
                } else {
                    try {
                        int val = Integer.parseInt(enterJumpI.getText());
                        if (val >= 0 && val < simul.getWidth()) {
                            i = val;
                        } else {
                            String warning = "Indice i en dehors de la simulation.";
                            JOptionPane.showMessageDialog(new JFrame(), warning);
                            return;
                        } 
                    } catch (NumberFormatException e1) {
                        String warning = "Indice i doit être un entier.";
                        JOptionPane.showMessageDialog(new JFrame(), warning);
                        return;
                    }
                }
                if (enterJumpJ.getText().isEmpty()) {
                    j = 0;
                } else {
                    try {
                        int val = Integer.parseInt(enterJumpJ.getText());
                        if (val >= 0 && val < simul.getLength()) {
                            j = val;
                        } else {
                            String warning = "Indice j en dehors de la simulation.";
                            JOptionPane.showMessageDialog(new JFrame(), warning);
                            return;
                        } 
                    } catch (NumberFormatException e1) {
                        String warning = "Indice j doit être un entier.";
                        JOptionPane.showMessageDialog(new JFrame(), warning);
                        return;
                    }
                }
                board.jumpTo(i, j);
                enterJumpI.setText("");
                enterJumpJ.setText("");
                jumpToDeselect.setVisible(true);
            }
        });
    }
    
    public void jumpToDeselect() {
        jumpToDeselect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.jumpToDeselect();
                jumpToDeselect.setVisible(false);
            }
        });
    }
    
    public void beginClick() {
        begin.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                if (playOrPause.getText().equals("pause")) {
                    playOrPause.doClick();
                }
                slider.setValue(0);
            }
        });
    }

    public void traitement() {
        this.playOrPauseClick();
        this.prevClick();
        this.nextClick();
        this.speedUpClick();
        this.slowDownClick();
        this.zoomInClick();
        this.zoomOutClick();
        this.enterJump();
        this.jumpTo();
        this.jumpToDeselect();
        this.enterTime();
        this.changeSpeedClick();
        this.beginClick();

        this.add(slider);
        this.add(buttons);
       
    }

}
