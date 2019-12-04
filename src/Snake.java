import javafx.scene.layout.BorderRepeat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Snake implements ActionListener {

    private JFrame jFrame;
    private Timer timer;
    private Timer pauseTimer;
    private Direction direction;
    private DirectionAdapter directionAdapter;

    private int bodySize;
    private int appleX;
    private int appleY;
    private int speed;
    private int points;

    private final int[] POSITION_X = new int[900];
    private final int[] POSITION_Y = new int[900];

    private BeforeGameJPanel beforeGameJPanel;
    private AfterGameJPanel afterGameJPanel;
    private KeyListener keyListener;

    private GamePanel gamePanel;
    private ScorePanel scorePanel;

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
        gamePanel.setPositions(POSITION_X, POSITION_Y, bodySize, appleX, appleY);
        gamePanel.repaint();

        directionAdapter = new DirectionAdapter();
        gamePanel.addKeyListener(directionAdapter);
        gamePanel.requestFocus();

        direction = new Direction();

        points = 0;

        scorePanel.start();
        initTimer();
    }

    private void initBody() {
        bodySize = 3;
        POSITION_Y[0] = 150;
        POSITION_X[0] = 150;

        for (int i = 1; i <= bodySize; i++) {
            POSITION_Y[i] = POSITION_Y[i-1];
            POSITION_X[i] = POSITION_X[i-1] + 10;

        }
    }

    private void initTimer() {
        speed = 150;
        timer = new Timer(speed, this);
        timer.start();
    }

    private void placeApple() {
        Random random = new Random();
        appleX = (Math.abs(random.nextInt()%29) + 1) * 10;
        appleY = (Math.abs(random.nextInt()%29) + 1) * 10;

        for (int i = 0; i <= bodySize; i++) {
            if (appleX == POSITION_X[i] && appleY == POSITION_Y[i]) {
                placeApple();
                break;

            }
        }
    }

    private void checkApple() {

        if  (POSITION_X[0] == appleX && POSITION_Y[0] == appleY) {

            bodySize += 3;
            points += 1000;

            for (int i = 0; i < 3; i++) {
                POSITION_X[bodySize - i] = POSITION_X[bodySize-3];
                POSITION_Y[bodySize - i] = POSITION_Y[bodySize-3];
            }

            speedUp();
            placeApple();
        }
    }

    private void speedUp() {
        timer.stop();
        if(speed > 50)
        {
            speed *= 0.98;
        }
        timer = new Timer(speed, this);
        timer.start();
    }

    private void move() {

        if(!checkCollision(direction)) {
            for (int i = bodySize; i >= 1 ; i--) {
                POSITION_X[i] = POSITION_X[i-1];
                POSITION_Y[i] = POSITION_Y[i-1];
            }
            if(direction.getDirection() == direction.LEFT) {
                POSITION_X[0] -= 10;
            }

            if(direction.getDirection() == direction.RIGHT) {
                POSITION_X[0] += 10;
            }

            if(direction.getDirection() == direction.DOWN) {
                POSITION_Y[0] += 10;
            }

            if(direction.getDirection() == direction.UP) {
                POSITION_Y[0] -= 10;
            }
        }

    }

    private boolean checkCollision(Direction direction)  {
        boolean collision = false;
        if(direction.getDirection() == Direction.UP) {
            if(POSITION_Y[0] - 10 < 0){
                collision = true;
            }
            for (int i = 2; i < bodySize; i++) {
                if(POSITION_X[i] == POSITION_X[0] && POSITION_Y[i] == POSITION_Y[0] - 10 )
                {
                    collision = true;
                }
            }
        }

        if(direction.getDirection() == Direction.DOWN) {
            if(POSITION_Y[0] + 10 >= 300){
                collision = true;
            }
            for (int i = 2; i < bodySize; i++) {
                if(POSITION_X[i] == POSITION_X[0] && POSITION_Y[i] == POSITION_Y[0] + 10 )
                {
                    collision = true;
                }
            }
        }

        if(direction.getDirection() == Direction.LEFT) {
            if(POSITION_X[0] - 10 < 0){
                collision = true;
            }
            for (int i = 2; i < bodySize; i++) {
                if(POSITION_X[i] == POSITION_X[0] - 10 && POSITION_Y[i] == POSITION_Y[0] )
                {
                    collision = true;
                }
            }

        }

        if(direction.getDirection() == Direction.RIGHT) {
            if(POSITION_X[0] + 10 >= 300) {
                collision = true;
            }
            for (int i = 2; i < bodySize; i++) {
                if(POSITION_X[i] == POSITION_X[0] + 10 && POSITION_Y[i] == POSITION_Y[0] )
                {
                    collision = true;
                }
            }

        }

        if(collision) {
            endGame();
        }

        return collision;
    }

    private void endGame() {
        timer.stop();
        jFrame.remove(gamePanel);
        jFrame.remove(scorePanel);
        new Snake(jFrame, points);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(points > 0)
            points -= 10;
        move();
        checkApple();
        gamePanel.setPositions(POSITION_X, POSITION_Y, bodySize, appleX, appleY);
        gamePanel.repaint();
        direction.setChangeable(true);
        scorePanel.setPoints(points);

    }

    private class DirectionAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT)
            {
                direction.setDirection(Direction.LEFT);
            }

            if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT)
            {
                direction.setDirection(Direction.RIGHT);
            }

            if(keyEvent.getKeyCode() == KeyEvent.VK_UP)
            {
                direction.setDirection(Direction.UP);
            }

            if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN)
            {
                direction.setDirection(Direction.DOWN);
            }
        }
    }

    private class Direction {
        public final static int UP = 1;
        public final static int DOWN = 2;
        public final static int RIGHT = 3;
        public final static int LEFT = 4;

        private int previousDirection;
        private int direction;
        private boolean isChangeable;


        Direction() {
            direction = LEFT;
            isChangeable = true;
        }

        public int getDirection() {
            return direction;
        }

        public int getPreviousDirection() {
            return previousDirection;
        }

        public void setDirection(int newDirection) {
            if(isChangeable){
                if ((newDirection == 1 || newDirection == 2) && (direction == 3 || direction == 4)) {
                    previousDirection = direction;
                    direction = newDirection;
                    isChangeable = false;
                }
                if((newDirection == 3 || newDirection == 4) && (direction == 1 || direction == 2))
                {
                    previousDirection = newDirection;
                    direction = newDirection;
                    isChangeable = false;
                }
            }
        }

        public void setChangeable(boolean isChangeable)
        {
            this.isChangeable = isChangeable;
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
