package com.jagooodek;

import java.util.ArrayList;

public class GameData {
    private ArrayList<PositionData> positionData;
    private ArrayList<Buff> buffsData;
    private int add;
    private int points;
    private int speed;

    GameData() {
        positionData = new ArrayList<>();
        buffsData = new ArrayList<>();

        positionData.add(new PositionData(150, 150, Direction.LEFT));

        for (int i = 0; i < 3; i++) {
            positionData.add(new PositionData(160 + (i*10), 150, Direction.LEFT ));
        }
        add = 0;

        addBuff(new Apple(positionData, buffsData));

        points = 0;
        speed = 15;
    }

    public void addBuff(Buff buff) {
        buffsData.add(buff);
    }

    public ArrayList<PositionData> getPositionData() {
        return positionData;
    }

    public ArrayList<Buff> getBuffsData() {
        return buffsData;
    }

    public void move(int nextDirection) {

        if(positionData.get(0).getX() % 10 == 0 && positionData.get(0).getY() % 10 == 0){
            for (int i = positionData.size() - 1; i > 0 ; i--) {
                positionData.get(i).setDirection(positionData.get(i - 1).getDirection());
            }
            positionData.get(0).setDirection(nextDirection);
            if(add-->0) {
                positionData.add(positionData.get(positionData.size()-1).copy());
                positionData.get(positionData.size() - 1).setDirection(0);
            }
        }

        for (int i = 0; i < positionData.size(); i++) {
            if(positionData.get(i).getDirection() == Direction.LEFT)
                positionData.get(i).addToX(-1);

            if(positionData.get(i).getDirection() == Direction.RIGHT)
                positionData.get(i).addToX(1);

            if(positionData.get(i).getDirection() == Direction.UP)
                positionData.get(i).addToY(-1);

            if(positionData.get(i).getDirection() == Direction.DOWN)
                positionData.get(i).addToY(1);
        }
        checkBuffs();
    }

    public void checkBuffs() {
        for (int i = 0; i < buffsData.size(); i++) {
            if(buffsData.get(i).checkBuff(positionData)) {
                buffsData.get(i).applyBuff(this);
                buffsData.remove(i);
            }

        }
    }

    public boolean checkCollision() {

        int headX = positionData.get(0).getX();
        int headY = positionData.get(0).getY();

        if(headX < 0 || headX > 290 || headY < 0 || headY > 290)
            return true;

        for (int i = 1; i < positionData.size(); i++) {
            if(positionData.get(i).checkCollision(headX, headY))
                return true;
        }

        return false;
    }

    public void addBody(int add) {
        this.add = add;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
        if(this.points < 0)
            this.points = 0;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
