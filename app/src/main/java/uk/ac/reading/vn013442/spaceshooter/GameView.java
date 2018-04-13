package uk.ac.reading.vn013442.spaceshooter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
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

import uk.ac.reading.vn013442.spaceshooter.drawable.Bullet;
import uk.ac.reading.vn013442.spaceshooter.drawable.Enemy;
import uk.ac.reading.vn013442.spaceshooter.drawable.Entity;
import uk.ac.reading.vn013442.spaceshooter.drawable.Player;

public class GameView extends SurfaceView implements Runnable {

    private List<Entity> entities = new ArrayList<>();
    private Context context;
    private GameEngine engine;
    private SurfaceHolder holder;

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

    public GameView(final Context context) {
        super(context);

        this.context = context;
        sound = new SoundEffects(this);

        engine = ((GameEngine) context);
//        ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayMetrics = getResources().getDisplayMetrics();


        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                lastTapSide = event.getX() >= displayMetrics.widthPixels / 2 ? TapSide.RIGHT : TapSide.LEFT;
                System.out.println(lastTapSide.name());

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


        //add objects to screen

        player = new Player(context, 20, 200);
        entities.add(player);
        entities.add(new Enemy(context, 1000, 0));
        entities.add(new Enemy(context, 1000, 200));
        entities.add(new Enemy(context, 1000, 400));

        //entities.add(new Player(bmAsteroid, 500, 200));
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

    public void spawnBullet(int x, int y) {
        entities.add(new Bullet(context, x, y));

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

        engine.openEndScreen(score);

    }

    //update the data from the game
    private void update() {
        List<Entity> entitiesToSpawn = new ArrayList<>();
        List<Entity> clonedEntities = new ArrayList<>(entities);
        for (Entity entity : clonedEntities) {
            if (entity instanceof IMoving) {
                ((IMoving) entity).move();
            }

            if (entity instanceof ICollision) {
                Entity collidingEntity = getCollisionWithAnyEnemy(((ICollision) entity));
                if (collidingEntity != null) {
                    entities.remove(collidingEntity);
                    entities.remove(entity);

                    score++;
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
                        ((Player) entity).move(playerDirection);

                    }
                }
            }
        }

        entities.addAll(entitiesToSpawn);
    }

    private Entity getCollisionWithAnyEnemy(final ICollision collidingEntity) {
        List<Entity> enemyEntities = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Enemy) {
                enemyEntities.add(entity);
            }
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

            paint.setColor(Color.YELLOW);

            int fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, displayMetrics);
            paint.setTextSize(fontSize);
            System.out.println(displayMetrics.heightPixels);
            canvas.drawText(String.format("Score: %d", score), displayMetrics.widthPixels / 2, 120, paint);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    public enum TapSide {
        LEFT, RIGHT
    }
}