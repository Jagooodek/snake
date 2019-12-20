package com.jagooodek;

import javax.swing.*;
import java.awt.*;

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
