import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.AbstractButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import display.Board;
import reader.Reader;

public class Main {

	public static void main(String[] args) throws IOException {
<<<<<<< Updated upstream
		Reader r = new Reader("traces/incendie/trace3.log");
=======
		Reader r = new Reader("b");
>>>>>>> Stashed changes
		//Reader r = new Reader("traces/jeuvie/b");
		int matrice[] = new int[r.getLength()*r.getWidth()];
		r.readNext(matrice);
		int colors[] = r.getColors();
		for(int i=0;i<colors.length;i++) {
			System.out.println(i+" : "+colors[i]);
		}
		JFrame frame = new JFrame();
		Board b = new Board();
		b.setWidth(r.getWidth());
		b.setLength(r.getLength());
		b.setCellType(r.getCellType());
		b.setColors(colors);
		b.setMatrice(matrice);
		b.setIcones("traces/incendie/");
		//b.setIcones("traces/jeuvie/");

		JPanel texts = new JPanel();
		JTextPane logs = new JTextPane();
<<<<<<< Updated upstream
		logs.setMaximumSize(new Dimension(1000, 50));
		logs.setText("test test test test \n"
=======
		logs.setMaximumSize(new Dimension(1000, 500));
		logs.setPreferredSize(new Dimension(1000, 500));
		/*logs.setText("test test test test \n"
>>>>>>> Stashed changes
				+ "test test test test \n"
				+ "test \n");
		logs.setEditable(false);
		JScrollPane scrlogs = new JScrollPane(logs);
		scrlogs.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		texts.setLayout(new BorderLayout());
		texts.setMaximumSize(new Dimension(1000, 150));
		texts.setPreferredSize(new Dimension(300, 150));
		texts.add(scrlogs, BorderLayout.NORTH);
		scrlogs.setMaximumSize(new Dimension(1000, 500));

		JPanel buttons = new JPanel();
		buttons.setSize(200, 50);

		JButton play = new JButton("â–¶");

		play.addActionListener(new ActionListener() {
			Timer timer = new Timer();
			@Override
			public void actionPerformed(ActionEvent e) {
				int begin = 0;
				int timeInterval = 1000;
				Object source = e.getSource();
				if (source instanceof JButton) {
					if (play.getText().equals("â–¶")) {
						play.setText("â�š â�š ");
						timer.schedule(new TimerTask() {

							@Override
							public void run() {
								try {
									r.readNext(matrice);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								b.setMatrice(matrice);
								//System.err.println(matrice.toString());
								b.revalidate();
								b.repaint();
							}
						}, begin, timeInterval);
					} else if (play.getText().equals("â�š â�š ")) {
						play.setText("â–¶");
						timer.cancel();
						timer= new Timer();
					}
				}
			}

		});

		JButton prec = new JButton("prec.");
		prec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					r.readPrevious(matrice);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				b.setMatrice(matrice);
				b.revalidate();
				b.repaint();
			}
		});

		JButton suiv = new JButton("suiv.");
		suiv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(r.readNext(matrice) != -1) {
						if(r.logExist(r.getT()))
							logs.setText(logs.getText() + r.getLog(r.getT()) + "\n");
						b.setMatrice(matrice);
						b.revalidate();
						b.repaint();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				b.setMatrice(matrice);
				//System.err.println(matrice.toString());
				b.revalidate();
				b.repaint();
			}
		});

		buttons.add(prec);
		buttons.add(play);
		buttons.add(suiv);
		
		//MenuBar//
		JMenuBar menu = new JMenuBar();
		JMenu fileMenu = new JMenu("Fichier");
		menu.add(fileMenu);
		JMenuItem openFileMenu = new JMenuItem("Ouvrir Fichier");
		fileMenu.add(openFileMenu);
		openFileMenu.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            //Ajouter Ici l'action de selection d'un fichier
          }
      });
		frame.setJMenuBar(menu);
		
		
		//Second champ pour les infos//
		JTextPane infos = new JTextPane();
		infos.setMaximumSize(new Dimension(1000, 500));
		infos.setPreferredSize(new Dimension(1000, 100));
		infos.setEditable(false);
        JScrollPane scrinfo = new JScrollPane(infos);
        scrinfo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        texts.add(scrinfo, BorderLayout.SOUTH);
        scrinfo.setMaximumSize(new Dimension(1000, 500));
        
              //-------- A executer a chaque chargement de fichier --------//
        if(r.logExist(-1))
          infos.setText(r.getLog(-1));
		

		frame.add(b);
		frame.add(buttons,BorderLayout.SOUTH);
		frame.add(texts, BorderLayout.EAST);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(1100, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo( null );
		frame.setVisible(true);

	}

}
