package uk.ac.reading.vn013442.spaceshooter;

import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.View;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.app.Fragment;
import android.widget.Button;
import android.content.Intent;
import android.hardware.SensorEventListener;

public class GameEngine extends AppCompatActivity {

    GameView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new GameView(this);
        setContentView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.pause();
    }

    public void openEndScreen(int score) {
        Intent intent = new Intent(this, EndScreen.class);
        intent.putExtra("SCORE", score);
        startActivity(intent);
    }

}

        /**
        ImageView.setOnTouchListener(new onTouchListener()) {

        @Override

        public boolean onTouch(View v, MotionEvent event){
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                return true;
            }
            return false;
        }
        **/


        /**
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.spacebackground));

        }

        public void update() {
            Background.update();
        }
        **/




