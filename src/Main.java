import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import display.Board;
import display.Slider;
import reader.Reader;

public class Main {

	public static void main(String[] args) throws IOException {
	    
		Reader r = new Reader("traces/incendie/trace3.log");
		//Reader r = new Reader("traces/jeuvie/b");
		int matrice[] = new int[r.getLength()*r.getWidth()];
		r.readNext(matrice);
		int colors[] = r.getColors();
		for(int i=0;i<colors.length;i++) {
			System.out.println(i+" : "+colors[i]);
		}
		JFrame frame = new JFrame();
		
		frame.setPreferredSize(new Dimension(1100,600));
		Dimension frameDimension = frame.getPreferredSize();
		System.out.println("Frame Dimension : "+frameDimension.toString());
		int widthBoard = (int) (frameDimension.width*0.7);
		
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
		//logs.setMaximumSize(new Dimension(1000, 50));
		/*logs.setText("test test test test \n"
				+ "test test test test \n"
				+ "test \n");*/
		logs.setEditable(false);
		JScrollPane scrlogs = new JScrollPane(logs);
		scrlogs.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		texts.setLayout(new BorderLayout());
		texts.setMaximumSize(new Dimension(1000, 150));
		texts.setPreferredSize(new Dimension(frameDimension.width-widthBoard, 490));
		texts.add(scrlogs);
		scrlogs.setMaximumSize(new Dimension(1000, 150));

		Slider slider = new Slider(r.getTmax());
		slider.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent event){
                try {
                    if(r.readExactTime(matrice, ((JSlider)event.getSource()).getValue()) != -1) {
                        if(r.logExist(r.getT()))
                            logs.setText(logs.getText() + r.getLog(r.getT()) + "\n");
                        b.setMatrice(matrice);
                        b.revalidate();
                        b.repaint();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
		
		JPanel buttons = new JPanel();

		JButton play = new JButton("▶");

		play.addActionListener(new ActionListener() {
			Timer timer = new Timer();
			@Override
			public void actionPerformed(ActionEvent e) {
				int begin = 0;
				int timeInterval = 1000;
				Object source = e.getSource();
				if (source instanceof JButton) {
					if (play.getText().equals("▶")) {
						play.setText("❚ ❚");
						timer.schedule(new TimerTask() {

							@Override
							public void run() {
							    slider.setValue(slider.getValue()+1);
								/*try {
									r.readNext(matrice);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								b.setMatrice(matrice);
								//System.err.println(matrice.toString());
								b.revalidate();
								b.repaint();*/
							}
						}, begin, timeInterval);
					} else if (play.getText().equals("❚ ❚")) {
						play.setText("▶");
						timer.cancel();
						timer= new Timer();
					}
				}
			}

		});

		JButton prec = new JButton("<");
		prec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                slider.setValue(slider.getValue()-1);
				/*try {
					r.readPrevious(matrice);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				b.setMatrice(matrice);
				b.revalidate();
				b.repaint();*/
			}
		});

		JButton suiv = new JButton(">");
		suiv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                slider.setValue(slider.getValue()+1);
				/*try {
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
				b.repaint();*/
			}
		});
		
		frame.setLayout(new BorderLayout());
		
		JPanel fileTextPanel = new JPanel();
		fileTextPanel.setLayout(new BoxLayout(fileTextPanel, BoxLayout.PAGE_AXIS));
		
		JPanel filePanel = new JPanel();
		filePanel.add(new JButton("Select"), BorderLayout.EAST);
		
		fileTextPanel.add(filePanel);
		fileTextPanel.add(texts);
		
		b.setPreferredSize(new Dimension(widthBoard,490));
		
		
		JPanel buttonAndSlider = new JPanel();
		buttonAndSlider.setPreferredSize(new Dimension(frameDimension.width,75));
		buttonAndSlider.setLayout(new BoxLayout(buttonAndSlider, BoxLayout.PAGE_AXIS));

		buttons.setPreferredSize(new Dimension(frameDimension.width,50));
		
		slider.setPreferredSize(new Dimension((int) (frameDimension.width*0.5),20));
		prec.setMargin(new Insets(0, 0, 0, 0));
		prec.setPreferredSize(new Dimension(35,35));
        play.setMargin(new Insets(0, 0, 0, 0));
        play.setPreferredSize(new Dimension(35,35));
        suiv.setMargin(new Insets(0, 0, 0, 0));
        suiv.setPreferredSize(new Dimension(35,35));
        
		buttonAndSlider.add(slider);
		buttons.add(prec);
		buttons.add(play);
		buttons.add(suiv);
        buttonAndSlider.add(buttons);

        frame.add(fileTextPanel,BorderLayout.LINE_END);
		frame.add(b, BorderLayout.LINE_START);
		//frame.add(buttons,BorderLayout.SOUTH);
		frame.add(buttonAndSlider,BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		//frame.setSize(1100, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo( null );
		frame.setVisible(true);
	}
}
