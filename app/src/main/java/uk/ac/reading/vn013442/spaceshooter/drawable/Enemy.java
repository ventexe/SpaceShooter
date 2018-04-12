package uk.ac.reading.vn013442.spaceshooter.drawable;

import android.content.Context;
import android.graphics.BitmapFactory;

import uk.ac.reading.vn013442.spaceshooter.IMoving;
import uk.ac.reading.vn013442.spaceshooter.R;


public class Enemy extends Entity implements IMoving {

    private long lastMove;

    public Enemy(Context context, int x, int y) {
        super(BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy), x, y);
        lastMove = System.currentTimeMillis();
    }

    @Override
    public void move() {
        if (System.currentTimeMillis() - this.lastMove < 100) {
            return;
        }
        this.x -= 10;
        this.lastMove = System.currentTimeMillis();
    }
}