import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private final int WIDTH = 300;
    private final int HEIGHT = 50;

    private Timer timer;

    private int time;
    private int points;

    ScorePanel() {
        initPanel();
    }

    private void initPanel(){

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setVisible(true);
        this.setFocusable(false);
        this.setBackground(Color.pink);
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

    private void initTimer() {

        timer = new Timer(1, actionEvent -> {
            if(points < 0){
                points = 0;
            }
            time ++;
            repaint();
        });
        timer.start();
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void start(){
        points = 0;
        initTimer();
    }

}

