package MineSweeper;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import static javax.imageio.ImageIO.read;

/**
 *
 * @author Sam Zaffanella
 */
public class GameStart extends JPanel implements KeyListener {

    static final int SCREEN_WIDTH = 400;
    static final int SCREEN_HEIGHT = 400;
    public boolean isAlwaysOnTop = false;
    private BufferedImage world;
    private Graphics2D buffer;
    static JFrame jf;
    private TileLayout TL;
    private BufferedImage UnclickedTile = null, ClickedTile = null, spikes = null;
    private float frequency = .44f;
    private ClickControl CC;

    public static void main(String[] args) {
        GameStart gamex = new GameStart();
        gamex.init();
        boolean alwaysOnTop = false;
        try {
            while (true) {

                gamex.repaint();
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {

        }

    }

    private void init() {
        jf = new JFrame("Mine Sweeper");
        this.world = new BufferedImage(GameStart.SCREEN_WIDTH, GameStart.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        try {
            System.out.println(System.getProperty("user.dir"));
            /*
             * note class loaders read files from the out folder (build folder in netbeans) and not the
             * current working directory.
             */

            UnclickedTile = read(new File("Resources/UnclickedTile.PNG"));
            ClickedTile = read(new File("Resources/ClickedTile.PNG"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        TL = new TileLayout();
        TL.init(UnclickedTile, ClickedTile);
        CC = new ClickControl();
        CC.init(TL);
        jf.setLayout(new BorderLayout());
        jf.add(this);

        jf.addMouseListener(CC);
        jf.addMouseMotionListener(CC);
        jf.addKeyListener(this);
        jf.setSize(GameStart.SCREEN_WIDTH, GameStart.SCREEN_HEIGHT);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.setUndecorated(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jf.setOpacity(frequency);

    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();

        if (keyPressed == KeyEvent.VK_SPACE) {
            TL = new TileLayout();
            TL.init(UnclickedTile, ClickedTile);
            CC.init(TL);
        }
        if (keyPressed == KeyEvent.VK_UP && frequency < .99) {
            jf.setOpacity(frequency+=.05f);
        }
        if (keyPressed == KeyEvent.VK_DOWN && frequency > .1) {
            jf.setOpacity(frequency-=.05f);
        }
        if (keyPressed == KeyEvent.VK_L) {
            jf.setAlwaysOnTop(!isAlwaysOnTop);
            isAlwaysOnTop = !isAlwaysOnTop;
        }
        if (keyPressed == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }

    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("AR Delaney", Font.PLAIN, 34));
        buffer = world.createGraphics();
        super.paintComponent(g2);
        g2.drawImage(world,0,0,null);
        TL.draw(buffer);

    }

}