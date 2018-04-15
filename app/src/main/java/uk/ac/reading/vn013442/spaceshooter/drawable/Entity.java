package uk.ac.reading.vn013442.spaceshooter.drawable;

import android.graphics.Bitmap;


/**
 * getters/setters
 * holds all of the data for images and their x/y coordinates
 */
public abstract class Entity {

    protected int x;
    protected int y;
    private Bitmap image;

    public Entity(Bitmap image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }


    public Bitmap getImage() {
        return this.image;
    }
}