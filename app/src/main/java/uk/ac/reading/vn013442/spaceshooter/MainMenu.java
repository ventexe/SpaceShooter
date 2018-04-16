package uk.ac.reading.vn013442.spaceshooter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * main menu containing buttons
 */
public class MainMenu extends AppCompatActivity {

    private Button BtnPlay;
    TextView textView;

    /**
     * creates content on main menu
     * @param savedInstanceState state of current screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);


        BtnPlay = (Button) findViewById(R.id.BtnPlay);
        BtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlay();
            }
        });
    }

    /**
     * starts the game
     */
    public void openPlay() {
        Intent intent = new Intent(this, GameEngine.class);
        startActivity(intent);
    }

    /**
     * displays instructions as text
     */
    public void Instructions() {
        textView = (TextView) findViewById(R.id.instructions);
        textView.setTextColor(Color.YELLOW);
    }


}


