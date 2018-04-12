package uk.ac.reading.vn013442.spaceshooter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GameEngine extends AppCompatActivity {

    private GameView view;

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