package uk.ac.reading.vn013442.spaceshooter.drawable;

import android.graphics.Bitmap;


public abstract class Entity {

    private Bitmap image;
    protected int x;
    protected int y;

    public Entity(Bitmap image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }


    /**
     * protected Bitmap createSubImageAt(Bitmap image, int x, int y)  {
     * <p>
     * //Bitmap subImage = Bitmap.createBitmap(image, col* width, row* height ,width, height);
     * //Bitmap subImage = Bitmap.createBitmap(image, col* width, row* height ,width, height);
     * Bitmap subImage = Bitmap.createBitmap(x, y, image);
     * <p>
     * return subImage;
     */

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