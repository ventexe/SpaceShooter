package uk.ac.reading.vn013442.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import uk.ac.reading.vn013442.spaceshooter.drawable.Bullet;
import uk.ac.reading.vn013442.spaceshooter.drawable.Enemy;
import uk.ac.reading.vn013442.spaceshooter.drawable.Entity;
import uk.ac.reading.vn013442.spaceshooter.drawable.Player;
import uk.ac.reading.vn013442.spaceshooter.level.Level;

public class GameView extends SurfaceView implements Runnable {

    private List<Entity> entities = new ArrayList<>();
    private List<Entity> enemies = new ArrayList<>();

    private Context context;
    private GameEngine engine;
    private SurfaceHolder holder;
    private int height = 0;

    private boolean pressIsHeld = false;
    private Player.Direction playerDirection;
    private TapSide lastTapSide;
    private long lastBulletSpawn;
    private int score = 0;

    private volatile boolean running;
    private Thread drawThread = null;
    private DisplayMetrics displayMetrics = new DisplayMetrics();

    private SoundEffects sound;

    private Player player;

    private Level currentLevel;
    private List<Level> allLevels = new ArrayList<>();

    private static final Random RANDOM = new Random();

    public GameView(final Context context) {
        super(context);

        this.context = context;
        sound = new SoundEffects(context);
        engine = ((GameEngine) context);
        displayMetrics = getResources().getDisplayMetrics();

        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                lastTapSide = event.getX() >= displayMetrics.widthPixels / 2 ? TapSide.RIGHT : TapSide.LEFT;

                if (lastTapSide == TapSide.LEFT) {
                    if (event.getY() >= displayMetrics.heightPixels / 2) {
                        playerDirection = Player.Direction.North;
                    } else {
                        playerDirection = Player.Direction.South;
                    }
                }

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    pressIsHeld = true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    pressIsHeld = false;
                }

                return true;
            }
        });

        this.holder = getHolder();

        player = new Player(context, 20, 200);
        entities.add(player);

        allLevels.add(new Level(3, 1));
        allLevels.add(new Level(4, 2));
        allLevels.add(new Level(4, 3));
    }


    public void pause() {
        running = false;
        if (drawThread != null) {
            try {
                drawThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void resume() {
        running = true;
        drawThread = new Thread(this);
        drawThread.start();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void run() {
        while (running) {
            //Update first
            update();

            //Draw updates
            draw();

            List<Entity> clonedEntities = new ArrayList<>(entities);
            clonedEntities.addAll(new ArrayList<>(enemies));
            for (Entity entity : clonedEntities) {
                if (entity instanceof Enemy) {
                    if (entity.getX() <= 0) {
                        running = false;
                    }
                } else if (entity instanceof Bullet) {
                    if (entity.getX() + entity.getImage().getWidth() >= displayMetrics.widthPixels) {
                        entities.remove(entity);
                    }
                }
            }

        }

        finishGame();
    }

    //update the data from the game
    private void update() {
        if (height == 0) {
            height = getHeight();
        }

        if (currentLevel == null && height > 0) {
            Level firstLevel = allLevels.get(0);
            currentLevel = firstLevel;
            startLevel(firstLevel);
        }

        List<Entity> clonedEntities = new ArrayList<>(entities);
        clonedEntities.addAll(new ArrayList<>(enemies));
        for (Entity entity : clonedEntities) {
            if (entity instanceof IMoving) {
                ((IMoving) entity).move();
            }

            if (entity instanceof ICollision) {
                Entity collidingEntity = getCollisionWithAnyEnemy(((ICollision) entity));
                if (collidingEntity != null) {
                    //Remove the bullet from the game
                    entities.remove(entity);

                    Enemy collidingEnemy = (Enemy) collidingEntity;
                    collidingEnemy.takeDamage();

                    if (collidingEnemy.getHealth() <= 0) {
                        //The entity has no remaining health, remove it from the game
                        enemies.remove(collidingEnemy);
                        score++;
                    }
                }
            }

            if (entity instanceof Player) {
                if (pressIsHeld) {
                    if (lastTapSide == TapSide.RIGHT && System.currentTimeMillis() - lastBulletSpawn > 1000) {
                        //Player wants to shoot a bullet
                        entities.add(new Bullet(context, entity.getX() + entity.getImage().getWidth(), entity.getY() + (entity.getImage().getHeight() / 2)));
                        lastBulletSpawn = System.currentTimeMillis();
                        sound.playBulletHitSound();
                    } else if (lastTapSide == TapSide.LEFT) {
                        if (playerDirection == Player.Direction.South
                                && entity.getY() + entity.getImage().getHeight() >= getHeight()) {
                            return;
                        }

                        if (playerDirection == Player.Direction.North
                                && entity.getY() <= 0) {
                            return;
                        }

                        ((Player) entity).move(playerDirection);
                    }
                }
            }
        }

        if (currentLevel != null && enemies.isEmpty()) {
            int currentLevelIndex = allLevels.indexOf(currentLevel);
            int nextLevelIndex = currentLevelIndex + 1;
            if (nextLevelIndex < allLevels.size()) {
                Level nextLevel = allLevels.get(nextLevelIndex);
                startLevel(nextLevel);
                currentLevel = nextLevel;
            } else {
                int amtOfEnemies = currentLevel.amountOfEnemies > 5 ? 5 : currentLevel.amountOfEnemies + 1;
                int health = currentLevel.enemyHealth + 1;

                Level newLevel = new Level(amtOfEnemies, health);
                currentLevel = newLevel;
                startLevel(newLevel);
            }
        }
    }

    private void finishGame() {
        engine.openEndScreen(score);
    }

    private void startLevel(Level level) {
        Bitmap enemyBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        for (int i = 0; i < level.amountOfEnemies; i++) {
            int randomX;
            int randomY;

            do {
                int min = displayMetrics.widthPixels / 2;
                int max = displayMetrics.widthPixels;

                randomX = RANDOM.nextInt(max - min) + min;
                randomY = RANDOM.nextInt(height - enemyBitmap.getHeight());
            } while (!isPositionFree(randomX, randomY));

            enemies.add(new Enemy(context, randomX, randomY, level.enemyHealth));
        }
    }

    private boolean isPositionFree(int x, int y) {
        for (Entity enemy : enemies) {
            int minX = enemy.getX();
            int minY = enemy.getY();
            int maxX = enemy.getX() + enemy.getImage().getWidth();
            int maxY = enemy.getY() + enemy.getImage().getHeight();

            if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                return false;
            }
        }

        return true;
    }

    private Entity getCollisionWithAnyEnemy(final ICollision collidingEntity) {
        List<Entity> enemyEntities = new ArrayList<>();
        for (Entity entity : enemies) {
            enemyEntities.add(entity);
        }

        Collections.sort(enemyEntities, new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                int enemy1CenterY = o1.getY() + (o1.getImage().getHeight() / 2);
                int enemy2CenterY = o2.getY() + (o2.getImage().getHeight() / 2);
                return enemy1CenterY == enemy2CenterY ? 0
                        : enemy1CenterY > enemy2CenterY ? -1
                        : 1;
            }
        });

        for (Entity otherEntity : enemyEntities) {
            if (collidingEntity.isCollision(otherEntity)) {
                return otherEntity;
            }
        }

        return null;
    }

    //draw here
    private void draw() {
        if (holder.getSurface().isValid()) {
            final Paint paint = new Paint();
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            for (Entity entity : entities) {
                canvas.drawBitmap(entity.getImage(), entity.getX(), entity.getY(), paint);
            }

            for (Entity entity : enemies) {
                canvas.drawBitmap(entity.getImage(), entity.getX(), entity.getY(), paint);
            }

            paint.setColor(Color.YELLOW);

            int fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, displayMetrics);
            paint.setTextSize(fontSize);
            canvas.drawText(String.format("Score: %d", score), displayMetrics.widthPixels / 2, 120, paint);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    public enum TapSide {
        LEFT, RIGHT
    }
}