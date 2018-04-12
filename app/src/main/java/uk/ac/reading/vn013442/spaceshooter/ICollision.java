package uk.ac.reading.vn013442.spaceshooter;

import uk.ac.reading.vn013442.spaceshooter.drawable.Entity;

public interface ICollision {
    boolean isCollision(Entity otherItem);
    int getX();
    int getY();
}
