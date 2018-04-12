package uk.ac.reading.vn013442.spaceshooter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndScreen extends AppCompatActivity {

    private Button btnQuit;


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

    public void playAgain(View view) {
        Intent intent = new Intent(this, GameEngine.class);
        startActivity(intent);
    }

    public void btnQuit(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
