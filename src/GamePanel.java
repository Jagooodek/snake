import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel{

    private final int BOARD_WIDTH = 300;
    private final  int BOARD_HEIGHT = 300;

    private Image ball;
    private Image apple;
    private Image head;

    private ArrayList<PositionData> positionData;
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
            g.drawImage(head, positionData.get(0).getX(), positionData.get(0).getY(), this);
            for (int i = 1; i <= positionData.size() - 1 ; i++) {
                g.drawImage(ball, positionData.get(i).getX(), positionData.get(i).getY(), this);
            }
            g.drawImage(apple, appleX, appleY, this);
    }

    public void setPositions(ArrayList<PositionData> positionData, Apple apple) {
        this.positionData = positionData;
        this.appleX = apple.getX();
        this.appleY = apple.getY();
    }

}
