package uk.ac.reading.vn013442.spaceshooter;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public abstract class Drawable {

    protected Bitmap image;

    //protected final int rowCount;
    //protected final int colCount;

    //protected final int WIDTH;
   // protected final int HEIGHT;

    //protected final int width;
    //protected final int height;

    protected int x;
    protected int y;

    //public Drawable(Bitmap image, int rowCount, int colCount, int x, int y)  {
      public Drawable(Bitmap image, int x, int y) {

        this.image = image;
        //this.rowCount= rowCount;
        //this.colCount= colCount;

        this.x = x;
        this.y = y;

        //this.WIDTH = image.getWidth();
        //this.HEIGHT = image.getHeight();

        //this.width = this.WIDTH/ colCount;
        //this.height= this.HEIGHT/ rowCount;
    }


    /**
    protected Bitmap createSubImageAt(Bitmap image, int x, int y)  {

        //Bitmap subImage = Bitmap.createBitmap(image, col* width, row* height ,width, height);
        //Bitmap subImage = Bitmap.createBitmap(image, col* width, row* height ,width, height);
        Bitmap subImage = Bitmap.createBitmap(x, y, image);

        return subImage;
    }
    */

    public int getX()  {
        return this.x;
    }

    public int getY()  {
        return this.y;
    }


    public Bitmap getImage() { return this.image; }

    /**
     public int getHeight() {
     return height;
     }

     public int getWidth() {
     return width;
     }
     *
     */

}