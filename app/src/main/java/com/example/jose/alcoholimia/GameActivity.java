package com.example.jose.alcoholimia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Game activity
 * Created by Jose on 3/27/2016.
 */

public class GameActivity extends AppCompatActivity {

    // todo: que estos array se pueblen automaticamente
    ArrayList<String> easy = new ArrayList<>();
    ArrayList<String> medium = new ArrayList<>(Arrays.asList("medium1", "medium2", "medium3"));
    ArrayList<String> hard = new ArrayList<>(Arrays.asList("hard1", "hard2", "hard3"));
    ArrayList<String> extreme = new ArrayList<>(Arrays.asList("extreme1", "extreme2", "extreme3"));

    private ArrayList<Double> difficultyAverage;
    private int currentPlayer = 0;
    private int round = 1;
    int difficulty = 1;
    boolean didDare = false;
    boolean didDrink = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        DaresDataSource datasource = new DaresDataSource(this);

        try {
            datasource.open();

            easy = datasource.getEasyDares();


        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            datasource.close();
        }

        difficultyAverage = new ArrayList<>(Collections.nCopies(GameSetupActivity.numPlayers, 0D));

        final ToggleButton tbQuestion = (ToggleButton) findViewById(R.id.tbQuestion);
        final ToggleButton tbDrinks = (ToggleButton) findViewById(R.id.tbDrinks);
        final Button bNextQuestion = (Button) findViewById(R.id.bNextQuestion);
        final Button bFinishGame = (Button) findViewById(R.id.bFinishGame);

        // Initial round
        ((TextView) findViewById(R.id.tvRound)).setText("Round " + String.valueOf(round));
        ((TextView) findViewById(R.id.tvPlayer)).setText("Player " + String.valueOf(currentPlayer + 1));
        generateRandomQuestion();

        // todo: boton change dare, -puntos (% of something)

        bNextQuestion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                didDare = tbQuestion.isChecked();
                didDrink = tbDrinks.isChecked();

                // go to next player
                if (currentPlayer + 1 <= GameSetupActivity.numPlayers) {
                    addPoints(difficulty, currentPlayer, didDare, didDrink);
                    currentPlayer++;
                }

                // go to next round
                if (currentPlayer == GameSetupActivity.numPlayers) {
                    currentPlayer = 0;
                    round++;
                }

                // update name of round and player
                ((TextView) findViewById(R.id.tvRound)).setText("Round " + String.valueOf(round));
                ((TextView) findViewById(R.id.tvPlayer)).setText("Player " + String.valueOf(currentPlayer + 1));

                // generate a new random question
                generateRandomQuestion();

                // if last turn of the game, show finish button
                if (currentPlayer + 1 == GameSetupActivity.numPlayers && round == GameSetupActivity.numRounds) {
                    bNextQuestion.setVisibility(View.GONE);
                    bFinishGame.setVisibility(View.VISIBLE);
                }

                tbQuestion.setChecked(false);
                tbDrinks.setChecked(false);

                didDare = false;
                didDrink = false;

            }
        });

        bFinishGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                didDare = tbQuestion.isChecked();
                didDrink = tbDrinks.isChecked();

                addPoints(difficulty, currentPlayer, didDare, didDrink);

                launchResults();
            }
        });

    }

    private void generateRandomQuestion() {

        TextView tvDrinks = (TextView) findViewById(R.id.tvDrinks);
        ArrayList<String> currentDifficulty = new ArrayList<>();

        Random random = new Random();

        if (round == 1) {
            difficulty = random.nextInt(4) + 1;

        } else {
            double currentAverage = Math.floor(difficultyAverage.get(currentPlayer));
            if (currentAverage == 1) {
                difficulty = random.nextInt(2) + 3;
            }
            if (currentAverage == 2) {
                difficultyAverage.set(currentPlayer, 1D);
                difficulty = random.nextInt(4) + 1;
            }
            if (currentAverage == 3) {
                difficultyAverage.set(currentPlayer, 1D);
                difficulty = random.nextInt(4) + 1;
            }
            if (currentAverage == 4) {
                difficulty = random.nextInt(2) + 1;
            }
        }

        switch (difficulty) {
            case 1:
                currentDifficulty = easy;
                tvDrinks.setText("1 drink");
                break;
            case 2:
                currentDifficulty = medium;
                tvDrinks.setText("2 drinks");
                break;
            case 3:
                currentDifficulty = hard;
                tvDrinks.setText("3 drinks");
                break;
            case 4:
                currentDifficulty = extreme;
                tvDrinks.setText("4 drinks");
                break;
        }

        int questionIndex = random.nextInt(currentDifficulty.size());

        String randomString = currentDifficulty.get(questionIndex);
        ((TextView) findViewById(R.id.tvRandText)).setText(randomString);

        difficultyAverage.set(currentPlayer,
                (difficultyAverage.get(currentPlayer) + difficulty) / Math.ceil((double) round / (double) GameSetupActivity.numPlayers)
        );
    }

    private void addPoints(int difficulty, int currentPlayer, boolean didDare, boolean didDrink) {
        switch (difficulty) {
            case 1:
                if (didDare || didDrink) {
                    if (didDare && didDrink) {
                        GameSetupActivity.scores.set(currentPlayer, 2D);
                    } else {
                        GameSetupActivity.scores.set(currentPlayer, 1D);
                    }
                }
                break;
            case 2:
                if (didDare || didDrink) {
                    if (didDare && didDrink) {
                        GameSetupActivity.scores.set(currentPlayer, 4D);
                    } else {
                        GameSetupActivity.scores.set(currentPlayer, 2D);
                    }
                }
                break;
            case 3:
                if (didDare || didDrink) {
                    if (didDare && didDrink) {
                        GameSetupActivity.scores.set(currentPlayer, 6D);
                    } else {
                        GameSetupActivity.scores.set(currentPlayer, 3D);
                    }
                }
                break;
            case 4:
                if (didDare || didDrink) {
                    if (didDare && didDrink) {
                        GameSetupActivity.scores.set(currentPlayer, 8D);
                    } else {
                        GameSetupActivity.scores.set(currentPlayer, 4D);
                    }
                }
                break;
        }
    }

    private void launchResults() {
        Intent intent = new Intent(GameActivity.this, ResultsActivity.class);
        startActivity(intent);
        finish();
    }

}


