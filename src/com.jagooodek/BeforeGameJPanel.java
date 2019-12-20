package com.jagooodek;

import javax.swing.*;
import java.awt.*;

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