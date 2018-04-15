package uk.ac.reading.vn013442.spaceshooter;

import uk.ac.reading.vn013442.spaceshooter.drawable.Entity;

/**
 * collision get x and y
 * inheritance for entities that can collide
 */
public interface ICollision {
    boolean isCollision(Entity otherItem);

    int getX();

    int getY();
}
