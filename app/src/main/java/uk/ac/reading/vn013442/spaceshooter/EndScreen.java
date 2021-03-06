package uk.ac.reading.vn013442.spaceshooter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * game engine for functionality
 * contains the end screen, shows score and calculates highscore
 */
public class EndScreen extends AppCompatActivity {

    private Button btnQuit;


    /**
     * high score calculations
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_screen);

        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        TextView highScoreLabel = (TextView) findViewById(R.id.highScoreLabel);

        int score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText(score + "");

        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE", 0);

        if (score > highScore) {
            highScoreLabel.setText("High Score: " + score);

            SharedPreferences.Editor editor = settings.edit();  //saves score
            editor.putInt("HIGH_SCORE", score);
            editor.commit();
        } else {
            highScoreLabel.setText("High Score: " + highScore);
        }

    }

    /**
     * starts a new game by switching to game activity
     *
     * @param view
     */
    public void playAgain(View view) {
        Intent intent = new Intent(this, GameEngine.class);
        startActivity(intent);
    }

    /**
     * quits by going to main menu activity
     *
     * @param view
     */
    public void btnQuit(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
