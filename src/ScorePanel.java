import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScorePanel extends JPanel {

    private final int WIDTH = 300;
    private final int HEIGHT = 50;

    private Timer timer;

    private int time;
    private int points;

    private Main main;
    private Board board;

    ScorePanel(Main main) {
        this.main = main;
        board = main.getBoard();
        initPanel();
    }

    private void initPanel(){

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setVisible(true);
        this.setFocusable(false);
        this.setBackground(Color.pink);

        System.out.println(this.getGraphics());
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        paintPoints(graphics);
        paintTime(graphics);
    }

    private void paintPoints(Graphics g) {
        g.drawString(points + "", WIDTH/4, HEIGHT/2);
    }
    private void paintTime(Graphics g) {
        g.drawString(timeToString(time), WIDTH/4*3, HEIGHT/2);
    }

    private String timeToString(int time) {
        int timeInSeconds = time / 1000;
        int timeAfterSeconds = time - (timeInSeconds * 1000);
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds - (minutes * 60);

        String timeAfterSecondsString;
        String minutesString;
        String secondsString;

        if(timeAfterSeconds < 100){
            if(timeAfterSeconds < 10) {
                timeAfterSecondsString = "00" + timeAfterSeconds;
            }else{
                timeAfterSecondsString = "0" + timeAfterSeconds;
            }
        } else {
            timeAfterSecondsString = "" + timeAfterSeconds;
        }

        if(seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        String string = minutes + ":" + secondsString + "." + timeAfterSecondsString;

        return string;
    }

    public void initTimer() {

        timer = new Timer(1, actionEvent -> {
            points = board.getPoints();
            if(points < 0){
                points = 0;
            }
            time ++;
            repaint();
        });
        timer.start();
    }

    public void start(){
        initTimer();
    }

}
