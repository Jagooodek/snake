package com.jagooodek;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Buff {
    private int x;
    private int y;

    Buff(ArrayList<PositionData> positionData, ArrayList<Buff> buffsData) {
        placeBuff(positionData, buffsData);
    }

    public void placeBuff(ArrayList<PositionData> positionData, ArrayList<Buff> buffsData) {
        Random random = new Random();
        x = (Math.abs(random.nextInt()%29) + 1) * 10;
        y = (Math.abs(random.nextInt()%29) + 1) * 10;

        for (int i = 0; i < positionData.size() ; i++) {
            if(positionData.get(i).checkCollision(x, y)) {
                placeBuff(positionData, buffsData);
                break;
            }
            break;
        }

        for (int i = 0; i < buffsData.size() ; i++) {
            if(buffsData.get(i).checkCollision(x, y)) {
                placeBuff(positionData, buffsData);
                break;
            }
            break;
        }
    }

    public boolean checkBuff(ArrayList<PositionData> positionData) {

        int x = positionData.get(0).getX();
        int y = positionData.get(0).getY();
        int direction = positionData.get(0).getDirection();

        if(direction == Direction.LEFT) {
            if(x - 9 == this.x && y == this.y){
                return true;
            }
        }
        if(direction == Direction.RIGHT) {
            if(x + 9 == this.x && y == this.y){
                return true;
            }
        }
        if(direction == Direction.UP) {
            if(x == this.x && y - 9 == this.y){
                return true;
            }
        }
        if(direction == Direction.DOWN) {
            if(x == this.x && y + 9 == this.y){
                return true;
            }
        }
        return false;
    }

    public boolean checkCollision(int x, int y) {
        if(this.x == x || this.y == y)
            return true;
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void applyBuff(GameData gameData);

    public abstract void paint(Graphics g, JPanel jPanel);
}
