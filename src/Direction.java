public class Direction {
    public final static int UP = 1;
    public final static int DOWN = 2;
    public final static int RIGHT = 3;
    public final static int LEFT = 4;

    private int previousDirection;
    private int direction;
    private boolean isChangeable;


    Direction() {
        direction = LEFT;
        isChangeable = true;
    }

    public int getDirection() {
        return direction;
    }

    public int getPreviousDirection() {
        return previousDirection;
    }

    public void setDirection(int newDirection) {
        if(isChangeable){
            if ((newDirection == 1 || newDirection == 2) && (direction == 3 || direction == 4)) {
                previousDirection = direction;
                direction = newDirection;
                isChangeable = false;
            }
            if((newDirection == 3 || newDirection == 4) && (direction == 1 || direction == 2))
            {
                previousDirection = newDirection;
                direction = newDirection;
                isChangeable = false;
            }
        }
    }

    public void setChangeable(boolean isChangeable)
    {
        this.isChangeable = isChangeable;
    }
}