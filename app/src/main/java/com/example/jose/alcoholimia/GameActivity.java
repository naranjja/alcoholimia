package com.example.jose.alcoholimia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Jose on 3/27/2016.
 */

public class GameActivity extends AppCompatActivity {

    private ArrayList<Double> difficultyAverage;
    private int currentPlayer = 1;
    private int round = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        difficultyAverage = new ArrayList<>(Collections.nCopies(GameSetupActivity.numPlayers, 0D));

        findViewById(R.id.bRandom).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                randomText();
            }
        });

    }

    private void randomText() {

        // todo: que estos array se pueblen automaticamente
        ArrayList<String> easy = new ArrayList<>(Arrays.asList("easy1", "easy2", "easy3"));
        ArrayList<String> medium = new ArrayList<>(Arrays.asList("medium1", "medium2", "medium3"));
        ArrayList<String> hard = new ArrayList<>(Arrays.asList("hard1", "hard2", "hard3"));
        ArrayList<String> extreme = new ArrayList<>(Arrays.asList("extreme1", "extreme2", "extreme3"));

        ArrayList<String> currentDifficulty = new ArrayList<>();

        Random random = new Random();

        int difficulty = 1;

        if (round == 1) {
            difficulty = random.nextInt(4) + 1; // entre 1 y 4

        } else {
            double difavg = Math.floor(difficultyAverage.get(round));
            if (difavg == 1) {
                difficulty = random.nextInt(2) + 3;
            } // entre 3 y 4
            if (difavg == 2) {
                difficulty = random.nextInt(4) + 1;
            } // entre 1 y 4
            if (difavg == 3) {
                difficulty = random.nextInt(4) + 1;
            } // entre 1 y 4
            if (difavg == 4) {
                difficulty = random.nextInt(2) + 1;
            } // entre 1 y 2
        }

        switch (difficulty) {
            case 1:
                currentDifficulty = easy;
                break;
            case 2:
                currentDifficulty = medium;
                break;
            case 3:
                currentDifficulty = hard;
                break;
            case 4:
                currentDifficulty = extreme;
                break;
        }

        int questionIndex = random.nextInt(currentDifficulty.size());

        String randomString = currentDifficulty.get(questionIndex);
        ((TextView) findViewById(R.id.tvRandText)).setText(randomString);

        // todo: fix logic
        difficultyAverage.set(
                currentPlayer,
                (difficultyAverage.get(currentPlayer) + difficulty) / Math.ceil(round / GameSetupActivity.numPlayers)
        );

    }
}


