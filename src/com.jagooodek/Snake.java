package com.jagooodek;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Snake implements ActionListener {

    private JFrame jFrame;
    private Timer timer;
    private Timer pauseTimer;
    private DirectionAdapter directionAdapter;

    private int speed;

    private GameData gameData;

    private BeforeGameJPanel beforeGameJPanel;
    private AfterGameJPanel afterGameJPanel;
    private KeyListener keyListener;

    private GamePanel gamePanel;
    private ScorePanel scorePanel;
    private Direction nextDirection;

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

    private void initOtherThings() {
        gamePanel.setPositions(gameData);
        gamePanel.repaint();

        directionAdapter = new DirectionAdapter();
        gamePanel.addKeyListener(directionAdapter);
        gamePanel.requestFocus();

        scorePanel.start();
        nextDirection = Direction.Left;
    }

    private void initGame() {
        initGamePanels();
        initData();
        initOtherThings();

        initTimer();
    }

    private void initData() {
        gameData = new GameData();
    }

    private void initTimer() {
        speed = 15;
        timer = new Timer(speed, this);
        timer.start();
    }

    private void newSpeed() {
        timer.stop();
        speed = gameData.getSpeed();
        timer = new Timer(speed, this);
        timer.start();
    }

    private void endGame() {
        timer.stop();
        jFrame.remove(gamePanel);
        jFrame.remove(scorePanel);
        new Snake(jFrame, gameData.getPoints());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        gameData.addPoints(-1);
        scorePanel.setPoints(gameData.getPoints());

        gameData.move(nextDirection);

        if(gameData.checkCollision())
            endGame();

        if(gameData.getSpeed() != speed) {
            newSpeed();
        }
        gamePanel.setPositions(gameData);
        scorePanel.setPoints(gameData.getPoints());
    }

    private class DirectionAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            ArrayList<PositionData> positionData = gameData.getPositionData();
            if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT)
            {
                if(positionData.get(0).getDirection() != Direction.Right && positionData.get(0).getDirection() != Direction.Left)
                    nextDirection = Direction.Left;
            }

            if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT)
            {
                if(positionData.get(0).getDirection() != Direction.Left && positionData.get(0).getDirection() != Direction.Right)
                    nextDirection = Direction.Right;
            }

            if(keyEvent.getKeyCode() == KeyEvent.VK_UP)
            {
                if(positionData.get(0).getDirection() != Direction.Down && positionData.get(0).getDirection() != Direction.Up)
                    nextDirection = Direction.Up;
            }

            if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN)
            {
                if(positionData.get(0).getDirection() != Direction.Up && positionData.get(0).getDirection() != Direction.Up )
                    nextDirection = Direction.Down;
            }
        }
    }
}
