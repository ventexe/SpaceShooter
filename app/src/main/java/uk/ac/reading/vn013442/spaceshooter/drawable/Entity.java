package uk.ac.reading.vn013442.spaceshooter.drawable;

import android.graphics.Bitmap;

/*
Holds all of the data for images and their x/y coordinates
 */

public abstract class Entity {

    private Bitmap image;
    protected int x;
    protected int y;

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