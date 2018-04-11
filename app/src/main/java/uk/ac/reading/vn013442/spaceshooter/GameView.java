package uk.ac.reading.vn013442.spaceshooter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;


public class GameView extends SurfaceView implements Runnable {

    Canvas canvas;
    Paint paint;

    Thread drawThread = null;
    SurfaceHolder holder;
    volatile boolean running;

    Bitmap bmPlayer;
    Bitmap bmEnemy;
    Bitmap bmBullet;
    Bitmap bmAsteroid;
    Bitmap bmBackground;

    Context CURRENT_CONTEXT;
    Context SENSOR_CONTEXT;
    Context TYPE_ACCELEROMETER;
    Context TYPE_MAGNETIC_FIELD;

    public static int stepSize = 5;

    private int screenWidth;
    private int screenHeight;

    int x;
    int y;

    public GameEngine engine;

    Path enemyPath;

    List<Drawable> entities = new ArrayList<>();
    OrientationData orientationData;



    //private Point playerPoint;

    //private OrientationData orientationData;

    public GameView(Context context) {

        super(context);

        engine = ((GameEngine) context);

        CURRENT_CONTEXT = Constants.CURRENT_CONTEXT = context;
        this.paint = new Paint();

        this.bmPlayer = BitmapFactory.decodeResource(getResources(), R.drawable.player);

        this.bmEnemy = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);

        this.bmBullet = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);

        this.bmAsteroid = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid);

        this.bmBackground = BitmapFactory.decodeResource(getResources(), R.drawable.spacebackground);


        this.holder = getHolder();

        //x_dir = 10;
        //y_dir = 10;

        //bmEnemy = bmEnemy + x_dir;
        //bmEnemy = bmEnemy + y_dir;

        //add objects to screen
        entities.add(new Player(bmPlayer, 20, 200));
        entities.add(new Enemy(bmEnemy, 1000, 0));
        entities.add(new Enemy(bmEnemy, 1000, 200));
        entities.add(new Enemy(bmEnemy, 1000, 400));
        //entities.add(new Player(bmAsteroid, 500, 200));

        //invalidate();

        orientationData = new OrientationData();
        orientationData.register();




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

        canvas.drawColor(Color.BLACK);
        canvas.drawText("1", 50, 50, paint);
    }

    @Override
    public void run() {
        while (running) {

            //Update first
            update();

            //Draw updates
            draw();

            for (Drawable entity : entities) {
                if (entity instanceof Enemy) {
                    if (entity.x <= 0) {
                        running = false;
                    }
                }
            }

            if (orientationData != null) {

                if (orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {

                    float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1]; //pi to -pi
                    float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];    //p/2 to -p/2, account for this

                    float xSpeed = 2 * roll * Constants.SCREEN_WIDTH / 1000f;
                    float ySpeed = pitch * Constants.SCREEN_HEIGHT / 1000f;

                    //Direction.x += Math.abs(xSpeed*elapsedTime) > 5 ? xSpeed*elapsedTime : 0;
                    //Direction.y -= Math.abs(ySpeed*elapsedTime) > 5 ? ySpeed*elapsedTime : 0;
                }

            }
        }

        engine.openEndScreen(10);

    }



/**

            if(Drawable.x < 0)
                Drawable.x = 0;
            else if(Drawable.x > Constants.SCREEN_WIDTH)
                Drawable.x = Constants.SCREEN_WIDTH;

            if(Drawable.y < 0)
                Drawable.y = 0;
            else if(Drawable.y > Constants.SCREEN_HEIGHT)
                Drawable.y = Constants.SCREEN_HEIGHT;
        }
    }
 *
 */




    //update the data from the game
    private void update() {
        for (int i = 0; i < entities.size(); i++) {
            for (Drawable drawable: entities) {
                if (drawable instanceof Enemy) {
                    ((Enemy)drawable).move();

                }
            }
        }
    }

    //draw here
    private void draw() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

            canvas.drawColor(Color.argb(255, 0, 0, 0));

            for (Drawable entity: entities) {
                canvas.drawBitmap(entity.getImage(), entity.getX(), entity.getY(), paint);
            }

            //Player player = new Player;

            //canvas.drawBitmap(bmEnemy, 550, 200, paint);
            //canvas.drawBitmap(bmBullet, 100, 400, paint);
            //canvas.drawBitmap(bmAsteroid, 350, 300, paint);
            //canvas.drawBitmap(bmBackground, 550, 200, paint);



            holder.unlockCanvasAndPost(canvas);
        }
    }





    }


