package uk.ac.reading.vn013442.spaceshooter.drawable;

import android.content.Context;
import android.graphics.BitmapFactory;

import uk.ac.reading.vn013442.spaceshooter.R;

/**
 * player class to hold movement and direction
 */
public class Player extends Entity {
    public Player(Context context, int x, int y) {
        super(BitmapFactory.decodeResource(context.getResources(), R.drawable.player), x, y);
    }

    /**
     * if direction north, go up 10 pixels, if direction south, go down 10 pixels
     *
     * @param direction
     */
    public void move(Direction direction) {
        switch (direction) {
            case North:
                this.y -= 10;
                break;
            case South:
                this.y += 10;
                break;
        }
    }


    public enum Direction {
        North, South
    }
}