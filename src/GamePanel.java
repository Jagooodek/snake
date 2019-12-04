import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel{

    private final int BOARD_WIDTH = 300;
    private final  int BOARD_HEIGHT = 300;

    private Image ball;
    private Image apple;
    private Image head;


    private int[] positionX = new int[900];
    private int[] positionY = new int[900];

    private int bodySize;
    private int appleX;
    private int appleY;

    GamePanel() {
        initBoard();
    }

    private void initBoard() {
        setBackground(Color.BLACK);
        setFocusable(true);
        this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        loadImages();
    }

    private void loadImages() {
        apple = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/apple.png"));
        ball = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/dot.png"));
        head = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/head.png"));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
            g.drawImage(head, positionX [0], positionY[0], this);
            for (int i = 1; i <= bodySize ; i++) {
                g.drawImage(ball, positionX [i], positionY[i], this);
            }
            g.drawImage(apple, appleX, appleY, this);
    }

    public void setPositions(int[] x, int[] y, int bodySize, int appleX, int appleY) {
        this.bodySize = bodySize;
        this.appleX = appleX;
        this.appleY = appleY;
        positionX = x;
        positionY = y;
    }

}
