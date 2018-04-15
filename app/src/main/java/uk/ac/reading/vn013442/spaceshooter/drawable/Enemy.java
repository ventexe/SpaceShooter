package uk.ac.reading.vn013442.spaceshooter.drawable;

import android.content.Context;
import android.graphics.BitmapFactory;

import uk.ac.reading.vn013442.spaceshooter.IMoving;
import uk.ac.reading.vn013442.spaceshooter.R;


/**
 * holds data for the enemy: x, y, health, movement
 */
public class Enemy extends Entity implements IMoving {

    private long lastMove;
    private int health;

    /**
     * @param context
     * @param x
     * @param y
     * @param health
     */
    public Enemy(Context context, int x, int y, int health) {
        super(BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy), x, y);
        lastMove = System.currentTimeMillis();
        this.health = health;
    }


    /**
     * removes 1 health if hit by bullet
     */
    public void takeDamage() {
        this.health -= 1;
    }

    /**
     * returns health
     *
     * @return
     */
    public int getHealth() {
        return health;
    }

    /**
     * move 10 pixels right every 100 milliseconds
     */
    @Override
    public void move() {
        if (System.currentTimeMillis() - this.lastMove < 100) {
            return;
        }
        this.x -= 10;
        this.lastMove = System.currentTimeMillis();
    }
}