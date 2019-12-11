import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Random;

public class Apple {

    private int appleX;
    private int appleY;

    Apple(ArrayList<PositionData> positionData) {
        placeApple(positionData);
    }

    public void placeApple(ArrayList<PositionData> positionData) {
        Random random = new Random();
        appleX = (Math.abs(random.nextInt()%29) + 1) * 10;
        appleY = (Math.abs(random.nextInt()%29) + 1) * 10;

        for (int i = 0; i < positionData.size() ; i++) {
            if(positionData.get(i).checkCollision(appleX, appleY)) {
                placeApple(positionData);
                break;
            }
        }
    }

    public boolean checkApple(ArrayList<PositionData> positionData) {

        int x = positionData.get(0).getX();
        int y = positionData.get(0).getY();
        int direction = positionData.get(0).getDirection();


        if(direction == Direction.LEFT) {
            if(appleX == x - 9 && appleY == y){
                return true;
            }
        }
        if(direction == Direction.RIGHT) {
            if(appleX == x + 9 && appleY == y){
                return true;
            }
        }
        if(direction == Direction.UP) {
            if(appleX == x && appleY == y - 9){
                return true;
            }
        }
        if(direction == Direction.DOWN) {
            if(appleX == x && appleY == y + 9){
                return true;
            }
        }
        return false;
    }

    public int getX() {
        return appleX;
    }

    public int getY() {
        return appleY;
    }
}
