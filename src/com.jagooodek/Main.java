package com.jagooodek;
import javax.swing.*;

public class Main {

    JFrame jFrame;

    public Main() {
        initFrame();
        new Snake(jFrame);
    }

    private void initFrame()  {
        jFrame = new JFrame();
        jFrame.setResizable(false);
        jFrame.setTitle("Snake");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Main main = new Main();

    }


}
