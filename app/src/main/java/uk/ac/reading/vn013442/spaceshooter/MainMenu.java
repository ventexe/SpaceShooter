package uk.ac.reading.vn013442.spaceshooter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainMenu extends AppCompatActivity {

    private Button BtnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        BtnPlay = (Button) findViewById(R.id.BtnPlay);
        BtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlay();
                //orientationData.newGame();
            }
        });
    }
        public void openPlay() {
            Intent intent = new Intent(this, GameEngine.class);
            startActivity(intent);
    }
}

