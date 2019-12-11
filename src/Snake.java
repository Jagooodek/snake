import javafx.scene.layout.BorderRepeat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Snake implements ActionListener {

    private JFrame jFrame;
    private Timer timer;
    private Timer pauseTimer;
    private Direction direction;
    private DirectionAdapter directionAdapter;

    private int add;
    private int appleX;
    private int appleY;
    private int speed;
    private int points;

    private final int[] POSITION_X = new int[900];
    private final int[] POSITION_Y = new int[900];

    private ArrayList<PositionData> positionData;

    private BeforeGameJPanel beforeGameJPanel;
    private AfterGameJPanel afterGameJPanel;
    private KeyListener keyListener;

    private GamePanel gamePanel;
    private ScorePanel scorePanel;
    private boolean isDirectionChangeable;
    private int nextDirection;

    Snake(JFrame jFrame) {
        this.jFrame = jFrame;
        initBeforeGamePanel();
    }

    Snake(JFrame jFrame, int score) {
        this.jFrame = jFrame;
        initAfterGamePanel(score);
    }

    private void initBeforeGamePanel() {
        beforeGameJPanel = new BeforeGameJPanel();
        keyListener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                beforeGameJPanel.removeKeyListener(this);
                jFrame.remove(beforeGameJPanel);
                initGame();
            }
        };
        jFrame.add(beforeGameJPanel);
        jFrame.pack();
        beforeGameJPanel.addKeyListener(keyListener);
        beforeGameJPanel.requestFocus();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    private void initAfterGamePanel(int score) {
        afterGameJPanel = new AfterGameJPanel(score);
        keyListener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                afterGameJPanel.removeKeyListener(this);
                jFrame.remove(afterGameJPanel);
                initGame();
            }
        };

        jFrame.add(afterGameJPanel);
        jFrame.pack();

        afterGameJPanel.setPaused(true);

        pauseTimer = new Timer(1000, actionEvent -> {
            afterGameJPanel.addKeyListener(keyListener);
            afterGameJPanel.requestFocus();
            afterGameJPanel.setPaused(false);
            pauseTimer.stop();
        });

        pauseTimer.start();


    }

    private void initGamePanels() {
        gamePanel = new GamePanel();
        scorePanel = new ScorePanel();
        jFrame.add(gamePanel, BorderLayout.PAGE_START);
        jFrame.add(scorePanel, BorderLayout.PAGE_END);
        jFrame.pack();
    }

    private void initGame() {
        initGamePanels();
        initBody();
        placeApple();
        gamePanel.setPositions(positionData, appleX, appleY);
        gamePanel.repaint();

        directionAdapter = new DirectionAdapter();
        gamePanel.addKeyListener(directionAdapter);
        gamePanel.requestFocus();

        points = 0;
        add = 0;

        scorePanel.start();
        nextDirection = Direction.LEFT;
        initTimer();
    }

    private void initBody() {

        positionData = new ArrayList<>();

        positionData.add(new PositionData(150, 150, Direction.LEFT));

        for (int i = 0; i < 3; i++) {
            positionData.add(new PositionData(160 + (i*10), 150, Direction.LEFT ));
        }
    }

    private void initTimer() {
        speed = 15;
        timer = new Timer(speed, this);
        timer.start();
    }

    private void placeApple() {
        Random random = new Random();
        appleX = (Math.abs(random.nextInt()%29) + 1) * 10;
        appleY = (Math.abs(random.nextInt()%29) + 1) * 10;

        for (int i = 0; i < positionData.size() ; i++) {
            if(positionData.get(i).checkCollision(appleX, appleY)) {
                placeApple();
                break;
            }
        }
    }

    private void checkApple() {
        int x = positionData.get(0).getX();
        int y = positionData.get(0).getY();
        int direction = positionData.get(0).getDirection();
        boolean eat = false;

        if(direction == Direction.LEFT) {
            if(appleX == x - 9 && appleY == y){
                eat = true;
            }
        }
        if(direction == Direction.RIGHT) {
            if(appleX == x + 9 && appleY == y){
                eat = true;
            }
        }
        if(direction == Direction.UP) {
            if(appleX == x && appleY == y - 9){
                eat = true;
            }
        }
        if(direction == Direction.DOWN) {
            if(appleX == x && appleY == y + 9){
                eat = true;
            }
        }
        if(eat) {

            points += 1000;
            add = 3;
            speedUp();
            placeApple();
        }
    }

    private void speedUp() {
        timer.stop();
        if(speed > 5)
        {
            speed *= 0.98;
        }
        timer = new Timer(speed, this);
        timer.start();
    }

    private void move() {

        if(positionData.get(0).getX() % 10 == 0 && positionData.get(0).getY() % 10 == 0){
            for (int i = positionData.size() - 1; i > 0 ; i--) {
                positionData.get(i).setDirection(positionData.get(i - 1).getDirection());
            }
            positionData.get(0).setDirection(nextDirection);
            if(add-->0) {
                positionData.add(positionData.get(positionData.size()-1).copy());
                positionData.get(positionData.size() - 1).setDirection(0);
            }
        }

        for (int i = 0; i < positionData.size(); i++) {
            if(positionData.get(i).getDirection() == direction.LEFT)
                positionData.get(i).addToX(-1);

            if(positionData.get(i).getDirection() == direction.RIGHT)
                positionData.get(i).addToX(1);

            if(positionData.get(i).getDirection() == direction.UP)
                positionData.get(i).addToY(-1);

            if(positionData.get(i).getDirection() == direction.DOWN)
                positionData.get(i).addToY(1);

        }

        if(checkCollision()) {
            endGame();
        }
    }

    private boolean checkCollision()  {

        int headX = positionData.get(0).getX();
        int headY = positionData.get(0).getY();

        if(headX < 0 || headX >= 300 || headY < 0 || headY >= 300)
            return true;

        for (int i = 1; i < positionData.size(); i++) {
            if(positionData.get(i).checkCollision(headX, headY))
                return true;
        }

        return false;
    }

    private void endGame() {
        timer.stop();
        jFrame.remove(gamePanel);
        jFrame.remove(scorePanel);
        new Snake(jFrame, points);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(points > 0) {
            points -= 1;
            scorePanel.setPoints(points);
        }
        move();
        checkApple();
        gamePanel.setPositions(positionData, appleX, appleY);
        gamePanel.repaint();
        scorePanel.setPoints(points);
    }

    private class DirectionAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT)
            {
                if(positionData.get(0).getDirection() != Direction.RIGHT && positionData.get(0).getDirection() != Direction.LEFT)
                    nextDirection = Direction.LEFT;
            }

            if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT)
            {
                if(positionData.get(0).getDirection() != Direction.LEFT && positionData.get(0).getDirection() != Direction.RIGHT)
                    nextDirection = Direction.RIGHT;
            }

            if(keyEvent.getKeyCode() == KeyEvent.VK_UP)
            {
                if(positionData.get(0).getDirection() != Direction.DOWN && positionData.get(0).getDirection() != Direction.UP)
                    nextDirection = Direction.UP;
            }

            if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN)
            {
                if(positionData.get(0).getDirection() != Direction.UP && positionData.get(0).getDirection() != Direction.DOWN )
                    nextDirection = Direction.DOWN;
            }
        }
    }

    class AfterGameJPanel extends JPanel {

        private final int PANEL_WIDTH = 300;
        private final  int PANEL_HEIGHT = 300;
        private boolean isPaused;
        private int lastGameScore;

        AfterGameJPanel(int score) {
            super();
            initPanel();
            lastGameScore = score;
        }


        private void initPanel() {
            setBackground(Color.BLACK);
            setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
            setFocusable(true);
        }

        public void setPaused(boolean isPaused) {
            this.isPaused = isPaused;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            doDrawing(graphics);
        }

        private void doDrawing(Graphics g) {
            String string = "Press any key to start...";
            String gameOver = "GAME OVER";
            //String score = "You lose with a snake length of " + (bodySize + 1);
            String score = lastGameScore + " points.";

            int gameOverHeight = 30;
            int scoreHeight = 18;
            int fontHeight = 18;

            Font gameOverFont = new Font("Helvetica", Font.BOLD, gameOverHeight);
            Font font = new Font("Helvetica", Font.PLAIN, fontHeight);
            Font scoreFont = new Font ("Helvetica", Font.ITALIC, scoreHeight);

            FontMetrics fontMetrics = getFontMetrics(font);
            FontMetrics gameOverFontMetrics = getFontMetrics(gameOverFont);
            FontMetrics scoreFontMetrics = getFontMetrics(scoreFont);


            int gap = 30;
            int bigGap = (PANEL_HEIGHT - (2*gap) - gameOverHeight - scoreHeight - fontHeight)/2;

            int gameOverX = ((PANEL_WIDTH - gameOverFontMetrics.stringWidth(gameOver))/2);
            int gameOverY = bigGap + gameOverHeight;

            int scoreX = ((PANEL_WIDTH - scoreFontMetrics.stringWidth(score))/2);
            int scoreY = bigGap + gameOverHeight + gap + scoreHeight;

            int startX = ((PANEL_WIDTH - fontMetrics.stringWidth(string))/2);
            int startY =  PANEL_HEIGHT - bigGap;

            g.setColor(Color.white);

            g.setFont(gameOverFont);
            g.drawString(gameOver, gameOverX, gameOverY);

            g.setFont(scoreFont);
            g.drawString(score, scoreX, scoreY);

            g.setFont(font);
            if(!isPaused)
                g.drawString(string, startX, startY);
        }
    }

    class BeforeGameJPanel extends JPanel {

        private final int PANEL_WIDTH = 300;
        private final  int PANEL_HEIGHT = 300;

        BeforeGameJPanel() {
            super();
            initPanel();
        }

        private void initPanel() {
            setBackground(Color.BLACK);
            setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
            setFocusable(true);
            setVisible(true);
        }


        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            doDrawing(graphics);
        }

        private void doDrawing(Graphics g) {
            String string = "Press any key to start...";
            Font font = new Font("Helvetica", Font.PLAIN, 18);
            FontMetrics fontMetrics = getFontMetrics(font);

            g.setColor(Color.white);
            g.setFont(font);
            g.drawString(string, (PANEL_WIDTH - fontMetrics.stringWidth(string))/2, PANEL_HEIGHT/2);
        }
    }

}
