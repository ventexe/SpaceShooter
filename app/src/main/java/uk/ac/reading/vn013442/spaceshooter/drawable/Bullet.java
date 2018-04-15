package uk.ac.reading.vn013442.spaceshooter.drawable;

import android.content.Context;
import android.graphics.BitmapFactory;

import uk.ac.reading.vn013442.spaceshooter.ICollision;
import uk.ac.reading.vn013442.spaceshooter.IMoving;
import uk.ac.reading.vn013442.spaceshooter.R;


public class Bullet extends Entity implements IMoving, ICollision {

    private long lastMove;

    /**
     * holds data for moving
     *
     * @param context
     * @param x
     * @param y
     */
    public Bullet(Context context, int x, int y) {
        super(BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet), x, y);
        lastMove = System.currentTimeMillis();
    }

    /**
     * calculates the of the enemies, moves 10 pixels every 100 milliseconds
     */
    @Override
    public void move() {
        if (System.currentTimeMillis() - this.lastMove < 100) {
            return;
        }
        this.x -= -10;
        this.lastMove = System.currentTimeMillis();
    }

    /**
     * checks for collision between bitmaps, calculates bounding boxes
     *
     * @param otherItem
     * @return
     */
    @Override
    public boolean isCollision(Entity otherItem) {
        if (!(otherItem instanceof Enemy)) {
            return false;
        }

        int otherItemMinY = otherItem.getY();
        int otherItemMaxY = otherItem.getY() + otherItem.getImage().getHeight();

        return getX() + getImage().getWidth() >= otherItem.getX() && getY() >= otherItemMinY && getY() <= otherItemMaxY;
    }
}