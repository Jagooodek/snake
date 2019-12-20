package com.jagooodek;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Apple extends Buff {

    Apple(ArrayList<PositionData> positionData, ArrayList<Buff> buffsData) {
        super(positionData, buffsData);
    }

    @Override
    public void applyBuff(GameData gameData) {
        gameData.addBody(3);
        gameData.addBuff(new Apple(gameData.getPositionData(), gameData.getBuffsData()));
        if(gameData.getSpeed() > 5)
            gameData.setSpeed((int)(gameData.getSpeed() * 0.98));
        gameData.addPoints(1000);
    }

    @Override
    public void paint(Graphics g, JPanel jPanel) {
        Image apple = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/apple.png"));
        g.drawImage(apple, getX(), getY(), jPanel);
    }
}
