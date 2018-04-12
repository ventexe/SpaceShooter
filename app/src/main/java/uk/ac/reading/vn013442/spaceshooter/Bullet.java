package uk.ac.reading.vn013442.spaceshooter;

import android.graphics.Bitmap;
import android.support.constraint.solver.widgets.Rectangle;


public class Bullet extends Drawable {

    Rectangle bounds;


    public Bullet(Bitmap image, int x, int y) {
        super(image, x, y);
        lastMove = System.currentTimeMillis();

        bounds = new Rectangle();

    }

    public void move() {
        if (System.currentTimeMillis() - this.lastMove < 100) {
            return;
        }
        this.x -= -10;
        this.lastMove = System.currentTimeMillis();
    }

    private long lastMove;


}
