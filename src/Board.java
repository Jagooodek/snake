import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class Board extends JPanel implements ActionListener {

    private final int BOARD_WIDTH = 300;
    private final  int BOARD_HEIGHT = 300;

    private Image ball;
    private Image apple;
    private Image head;
    private Timer timer;
    private Timer pauseTimer;

    private final int[] POSITION_X = new int[900];
    private final int[] POSITION_Y = new int[900];

    private int bodySize;
    private int appleX;
    private int appleY;
    private int speed;
    private byte gameStatus;
    private int points;

    private Main main;
    private ScorePanel scorePanel;
    private KeyListener keyListener;
    private DirectionAdapter directionAdapter;

    Direction direction;

    Board(Main main) {
        this.main = main;
        initBoard();
        areYouReady();
    }

    private void initBoard() {
        gameStatus = 0;
        setBackground(Color.BLACK);
        setFocusable(true);
        this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        loadImages();
        directionAdapter = new DirectionAdapter();
    }
// to jest zmiana żeby sprawdzić jak działa git
    private void initGame() {
        main.getScorePanel().initTimer();
        points = 0;
        this.removeKeyListener(keyListener);
        this.addKeyListener(directionAdapter);
        direction = new Direction();
        initBody();
        placeApple();
        initTimer();
        gameStatus = 1;
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

    private void loadImages() {
        apple = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/apple.png"));
        ball = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/dot.png"));
        head = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/head.png"));
    }

    private void areYouReady() {
        keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if(gameStatus != 2) {
                    initGame();
                }
            }
        };
        this.addKeyListener(keyListener);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(gameStatus == 1) {
            doDrawing(g);
        }
        else if (gameStatus == 0) {
            beforeGameDrawing(g);
        }
        else {
            afterGameDrawing(g);
        }

    }

    private void afterGameDrawing(Graphics g) {

        String string = "Press any key to start...";
        String gameOver = "GAME OVER";
        //String score = "You lose with a snake length of " + (bodySize + 1);
        String score = points + " points.";

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
        int bigGap = (BOARD_HEIGHT - (2*gap) - gameOverHeight - scoreHeight - fontHeight)/2;

        int gameOverX = ((BOARD_WIDTH - gameOverFontMetrics.stringWidth(gameOver))/2);
        int gameOverY = bigGap + gameOverHeight;

        int scoreX = ((BOARD_WIDTH - scoreFontMetrics.stringWidth(score))/2);
        int scoreY = bigGap + gameOverHeight + gap + scoreHeight;

        int startX = ((BOARD_WIDTH - fontMetrics.stringWidth(string))/2);
        int startY =  BOARD_HEIGHT - bigGap;

        g.setColor(Color.white);

        g.setFont(gameOverFont);
        g.drawString(gameOver, gameOverX, gameOverY);

        g.setFont(scoreFont);
        g.drawString(score, scoreX, scoreY);

        g.setFont(font);
        if(gameStatus == 3) {
            g.drawString(string, startX, startY);
        }
    }

    private void beforeGameDrawing(Graphics g) {
        String string = "Press any key to start...";
        Font font = new Font("Helvetica", Font.PLAIN, 18);
        FontMetrics fontMetrics = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(string, (BOARD_WIDTH - fontMetrics.stringWidth(string))/2, BOARD_HEIGHT/2);
    }

    private void doDrawing(Graphics g) {
            g.drawImage(head, POSITION_X[0], POSITION_Y[0], this);
            for (int i = 1; i <= bodySize ; i++) {
                g.drawImage(ball, POSITION_X[i], POSITION_Y[i], this);
            }
            g.drawImage(apple, appleX, appleY, this);
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

    private void endGame() {
        timer.stop();
        gameStatus = 2;
        this.removeKeyListener(directionAdapter);
        pauseTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gameStatus = 3;
                pauseTimer.stop();
                repaint();
            }
        });
        pauseTimer.start();
        areYouReady();
    }

    public int getPoints()  {
        return points;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        points -= 10;
        move();
        checkApple();
        repaint();
        direction.setChangeable(true);

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
            Random r = new Random();
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

}
