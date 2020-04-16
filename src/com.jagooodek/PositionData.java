package com.jagooodek;
public class PositionData {
    private int x;
    private int y;
    Direction direction;

    PositionData(int x, int y, Direction direction) {
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

    public Direction getDirection() {
        return direction;
    }

    public void addToX (int number) {
        x += number;
    }

    public void addToY (int number) {
        y += number;
    }

    public void setDirection(Direction direction) {
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
