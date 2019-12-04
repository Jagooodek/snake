import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
