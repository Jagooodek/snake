import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {

    JFrame jFrame;
    KeyListener keyListener;
    KeyListener keyListener2;
    BeforeGameJPanel beforeGameJPanel;
    AfterGameJPanel afterGameJPanel;

    public Main() throws InterruptedException {
        initFrame();
        initPanels();
        //beforeGameScreen();
        new Snake(jFrame);
    }

    private void initFrame()  {
        jFrame = new JFrame();
        jFrame.setResizable(false);
        jFrame.setTitle("Snake");
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

    private void initPanels() {
        beforeGameJPanel = new BeforeGameJPanel();
        keyListener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                beforeGameJPanel.removeKeyListener(this);
                jFrame.remove(beforeGameJPanel);
                initGame();
            }
        };


        afterGameJPanel = new AfterGameJPanel();
        keyListener2 = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                afterGameJPanel.removeKeyListener(this);
                jFrame.remove(afterGameJPanel);
                initGame();
            }
        };
    }

    private void beforeGameScreen() {
        jFrame.add(beforeGameJPanel);
        jFrame.pack();
        beforeGameJPanel.addKeyListener(keyListener);
        beforeGameJPanel.requestFocus();
    }

    private void afterGameScreen(int score)  {
        afterGameJPanel.setPoints(score);
        jFrame.add(afterGameJPanel);
        jFrame.pack();
        afterGameJPanel.addKeyListener(keyListener2);
        afterGameJPanel.requestFocus();
    }

    private void initGame() {
        jFrame.remove(0);
        afterGameScreen(new Snake(jFrame).getScore());
    }


    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();

    }

    class AfterGameJPanel extends JPanel {

        private final int PANEL_WIDTH = 300;
        private final  int PANEL_HEIGHT = 300;
        private int points;

        AfterGameJPanel() {
            super();
            initPanel();
        }

        public void setPoints(int points) {
            this.points = points;
        }

        private void initPanel() {
            setBackground(Color.BLACK);
            setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
            setFocusable(true);
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
