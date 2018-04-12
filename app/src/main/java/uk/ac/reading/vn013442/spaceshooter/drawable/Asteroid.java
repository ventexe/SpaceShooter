package uk.ac.reading.vn013442.spaceshooter.drawable;

import android.content.Context;
import android.graphics.BitmapFactory;

import uk.ac.reading.vn013442.spaceshooter.R;

public class Asteroid extends Entity {
    public Asteroid(Context context, int x, int y) {
        super(BitmapFactory.decodeResource(context.getResources(), R.drawable.asteroid), x, y);
    }
}