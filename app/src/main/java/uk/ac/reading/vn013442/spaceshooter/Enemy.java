package uk.ac.reading.vn013442.spaceshooter;

import android.graphics.Bitmap;


public class Enemy extends Drawable {

    public Enemy(Bitmap image, int x, int y) {
        super(image, x, y);
        lastMove = System.currentTimeMillis();

    }

    public void move() {
        if (System.currentTimeMillis() - this.lastMove < 100) {
            return;
        }
        this.x -= 10;
        this.lastMove = System.currentTimeMillis();
    }
    private long lastMove;


}

