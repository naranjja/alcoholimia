package com.example.jose.alcoholimia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Jose on 3/27/2016.
 */

public class GameActivity extends AppCompatActivity {

    // todo: que estos array se pueblen automaticamente
    ArrayList<String> easy = new ArrayList<>(Arrays.asList("easy1", "easy2", "easy3"));
    ArrayList<String> medium = new ArrayList<>(Arrays.asList("medium1", "medium2", "medium3"));
    ArrayList<String> hard = new ArrayList<>(Arrays.asList("hard1", "hard2", "hard3"));
    ArrayList<String> extreme = new ArrayList<>(Arrays.asList("extreme1", "extreme2", "extreme3"));
    private ArrayList<Double> difficultyAverage;
    private int currentPlayer = 0;
    private int round = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        difficultyAverage = new ArrayList<>(Collections.nCopies(GameSetupActivity.numPlayers, 0D));
        Button bNextQuestion = (Button) findViewById(R.id.bNextQuestion);

        // todo: boton change dare, -puntos (% of something)

        bNextQuestion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.tvRound)).setText(String.valueOf(round));
                generateRandomQuestion();

                if (round <= GameSetupActivity.numRounds) {
                    round++;
                } else {
                    ((TextView) findViewById(R.id.tvRound)).setText("done");
                }
            }
        });

    }

    private void generateRandomQuestion() {

        ArrayList<String> currentDifficulty = new ArrayList<>();

        Random random = new Random();

        int difficulty = 1;

        if (round == 1) {
            difficulty = random.nextInt(4) + 1; // completely random

        } else {
            double difavg = Math.floor(difficultyAverage.get(currentPlayer));
            if (difavg == 1) {
                // 3 o 4
                difficulty = random.nextInt(2) + 3;
            }
            if (difavg == 2) {
                // reset average
                difficultyAverage.set(currentPlayer, 1D);
                // completely random
                difficulty = random.nextInt(4) + 1;
            }
            if (difavg == 3) {
                // reset average
                difficultyAverage.set(currentPlayer, 1D);
                // completely random
                difficulty = random.nextInt(4) + 1;
            }
            if (difavg == 4) {
                // 1 o 2
                difficulty = random.nextInt(2) + 1;
            }
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

        difficultyAverage.set(currentPlayer,
                (difficultyAverage.get(currentPlayer) + difficulty) / Math.ceil(round / GameSetupActivity.numPlayers)
        );

    }
}


