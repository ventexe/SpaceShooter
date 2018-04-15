package uk.ac.reading.vn013442.spaceshooter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GameEngine extends AppCompatActivity {

    private GameView view;


    /**
     * contains the main game
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new GameView(this);
        setContentView(view);
    }

    /**
     * user returns to activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        view.resume();
    }

    /**
     * activity paused while another is used
     */
    @Override
    protected void onPause() {
        super.onPause();
        view.pause();
    }

    /**
     * opens end screen
     *
     * @param score
     */
    public void openEndScreen(int score) {
        Intent intent = new Intent(this, EndScreen.class);
        intent.putExtra("SCORE", score);
        startActivity(intent);
    }
}