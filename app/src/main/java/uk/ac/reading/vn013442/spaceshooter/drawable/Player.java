package uk.ac.reading.vn013442.spaceshooter.drawable;

import android.content.Context;
import android.graphics.BitmapFactory;

import uk.ac.reading.vn013442.spaceshooter.R;

public class Player extends Entity {
    public Player(Context context, int x, int y) {
        super(BitmapFactory.decodeResource(context.getResources(), R.drawable.player), x, y);
    }

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