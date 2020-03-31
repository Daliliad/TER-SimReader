import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

public enum Main {
    MAIN;

    public void run() throws IOException {
        SimulData data = new SimulData();
        JFrame frame = new JFrame();
        frame.setTitle(data.getReader().getPath());

        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(1280,600));
        Dimension frameDimension = frame.getPreferredSize();
        int widthBoard = (int) (frameDimension.width*0.7);

        SimulBoard b = new SimulBoard(widthBoard);
        b.resetBoard(data);
        //b.setIcones("traces/jeuvie/");

        /* LOGS */
        LogsPanel texts = new LogsPanel(frameDimension.width-widthBoard-20);

        /* Buttons and Slider */
        VideoCommands videoCommands = new VideoCommands(data, b, texts, frameDimension.width);
        videoCommands.traitement();

        /* FILE */
        JMenuBar menu = new MenuBar(videoCommands);
        
        frame.setJMenuBar(menu);



        frame.add(texts,BorderLayout.EAST);
        frame.add(b, BorderLayout.WEST);
        //frame.add(buttons,BorderLayout.SOUTH);
        frame.add(videoCommands,BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        //frame.setSize(1100, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);


        System.out.println("Frame Dimension : "+frame.getSize().toString());
    }

    public static void main(String[] args) throws IOException {
        MAIN.run();
    }
}
