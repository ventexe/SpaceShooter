package uk.ac.reading.vn013442.spaceshooter;


import android.graphics.Bitmap;

public interface CollidingItem {
    boolean isCollision(Bitmap image, int x, int y);
}
