package uk.ac.reading.vn013442.spaceshooter.level;

import java.util.ArrayList;
import java.util.List;

import uk.ac.reading.vn013442.spaceshooter.drawable.Entity;

/**
 * holds data for levels, amount of enemies and health
 */
public class Level {

    public int amountOfEnemies;
    public int enemyHealth;

    public Level(int amountOfEnemies, int enemyHealth) {
        this.amountOfEnemies = amountOfEnemies;
        this.enemyHealth = enemyHealth;
    }
}
