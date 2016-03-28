package com.example.jose.alcoholimia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jose on 3/27/2016.
 */

public class GameSetupActivity extends AppCompatActivity {

    public static int numPlayers;
    public static int numRounds;
    public static ArrayList<String> players;
    public static ArrayList<Double> scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);

        findViewById(R.id.bSubmit).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                setupGame();
                launchGame();
            }

        });
    }

    private void setupGame() {

        numPlayers = Integer.parseInt(((EditText) findViewById(R.id.etNumPlayers)).getText().toString());
        numRounds = Integer.parseInt(((EditText) findViewById(R.id.etNumRounds)).getText().toString());

        players = new ArrayList<>(Collections.nCopies(numPlayers, ""));
        scores = new ArrayList<>(Collections.nCopies(numPlayers, 0D));
    }

    private void launchGame() {
        Intent intent = new Intent(GameSetupActivity.this, GameActivity.class);
        startActivity(intent);
        finish();
    }
}

