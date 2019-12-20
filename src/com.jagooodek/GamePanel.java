package com.jagooodek;
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
    private ArrayList<Buff> buffsData;


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

        for (int i = 0; i < buffsData.size() ; i++) {
            buffsData.get(i).paint(g, this);
        }
    }

    public void setPositions(GameData gameData) {
        this.positionData = gameData.getPositionData();
        this.buffsData = gameData.getBuffsData();
        this.repaint();
    }

}
