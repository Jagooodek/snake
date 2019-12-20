package com.jagooodek;
public class PositionData {
    private int x;
    private int y;
    private int direction;

    PositionData(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirection() {
        return direction;
    }

    public void addToX (int number) {
        x += number;
    }

    public void addToY (int number) {
        y += number;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean checkCollision (int x, int y) {
        if(this.x == x && this.y == y)
            return true;
        return false;
    }

    public PositionData copy() {
        return new PositionData(x, y ,direction);
    }

}
