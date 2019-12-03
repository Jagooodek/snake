import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Snake implements ActionListener {
    JFrame jFrame;
    private Timer timer;
    Direction direction;
    private DirectionAdapter directionAdapter;

    private int bodySize;
    private int appleX;
    private int appleY;
    private int speed;
    private int points;

    private boolean inGame;

    private final int[] POSITION_X = new int[900];
    private final int[] POSITION_Y = new int[900];

    private GamePanel gamePanel;
    //private ScorePanel scorePanel;

    Snake(JFrame jFrame) {
        this.jFrame = jFrame;
        initPanels();
        initGame();
    }

    public int getScore() {

        System.out.println("here");
        while(inGame)
        {

        }
        return points;
    }

    private void initPanels() {
        gamePanel = new GamePanel();
        jFrame.add(gamePanel, BorderLayout.PAGE_START);
        jFrame.pack();
    }

    private void initGame() {
        inGame = true;

        directionAdapter = new DirectionAdapter();
        gamePanel.addKeyListener(directionAdapter);
        gamePanel.requestFocus();

        direction = new Direction();

        points = 0;

        placeApple();
        initBody();
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
        jFrame.remove(gamePanel);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        points -= 10;
        move();
        checkApple();
        gamePanel.setPositions(POSITION_X, POSITION_Y, bodySize, appleX, appleY);
        gamePanel.repaint();
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
