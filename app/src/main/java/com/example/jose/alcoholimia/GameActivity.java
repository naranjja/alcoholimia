package com.example.jose.alcoholimia;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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
    int difficulty = 1;
    boolean didDare = false;
    boolean didDrink = false;
    private ArrayList<Double> difficultyAverage;
    private int currentPlayer = 0;
    private int round = 1;
    private ToggleButton tbQuestion;
    private ToggleButton tbDrinks;


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Warning!")
                .setMessage("Do you want to end the game?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Dialog dialog = (Dialog) dialogInterface;
                        new AlertDialog.Builder(dialog.getContext())
                                .setTitle("Last warning!")
                                .setMessage("Are you completely, 100% sure you want to end the game?")
                                .setPositiveButton("YES, GAME OVER ALREADY!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }

                                })
                                .setNegativeButton("NO, SORRY! MY FINGERS ARE SLIPPERY", null)
                                .show();
                    }

                })
                .setNegativeButton("NO, IT WAS AN ACCIDENT!", null)
                .show();
    }


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

        tbQuestion = (ToggleButton) findViewById(R.id.tbQuestion);
        tbDrinks = (ToggleButton) findViewById(R.id.tbDrinks);

        final Button bNextQuestion = (Button) findViewById(R.id.bNextQuestion);
        final Button bFinishGame = (Button) findViewById(R.id.bFinishGame);
        final Button bSkip = (Button) findViewById(R.id.bSkip);
        final Button bReRoll = (Button) findViewById(R.id.bReRoll);

        // Initial round
        ((TextView) findViewById(R.id.tvRound)).setText(String.format("Round %s", String.valueOf(round)));
        ((TextView) findViewById(R.id.tvPlayer)).setText(String.format("Player %s", String.valueOf(currentPlayer + 1)));

        generateRandomQuestion(tbQuestion, tbDrinks);

        bReRoll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setMessage("Rerolling removes 3 points. Are you sure you want to reroll?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        removePoints(currentPlayer, 3D);
                        generateRandomQuestion(tbQuestion, tbDrinks);
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        bSkip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                builder.setMessage("Are you sure you want to skip?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        // go to next player
                        if (currentPlayer + 1 <= GameSetupActivity.numPlayers) {
                            currentPlayer++;
                        }

                        // go to next round
                        if (currentPlayer == GameSetupActivity.numPlayers) {
                            currentPlayer = 0;
                            round++;
                        }

                        // update name of round and player
                        ((TextView) findViewById(R.id.tvRound)).setText(String.format("Round %s", String.valueOf(round)));
                        ((TextView) findViewById(R.id.tvPlayer)).setText(String.format("Player %s", String.valueOf(currentPlayer + 1)));

                        // generate a new random question
                        generateRandomQuestion(tbQuestion, tbDrinks);

                        // if last turn of the game, show finish button
                        if (currentPlayer + 1 == GameSetupActivity.numPlayers && round == GameSetupActivity.numRounds) {
                            bNextQuestion.setEnabled(false);
                            bSkip.setVisibility(View.GONE);
                            bFinishGame.setVisibility(View.VISIBLE);
                        }

                        tbQuestion.setChecked(false);
                        tbDrinks.setChecked(false);

                        didDare = false;
                        didDrink = false;
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        bNextQuestion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                didDare = tbQuestion.isChecked();
                didDrink = tbDrinks.isChecked();

                if (didDare || didDrink) {
                    // go to next player
                    if (currentPlayer + 1 <= GameSetupActivity.numPlayers) {
                        addPoints(difficulty, currentPlayer, didDare, didDrink);
                        if (didDrink) addDrink(difficulty, currentPlayer);
                        currentPlayer++;
                    }

                    // go to next round
                    if (currentPlayer == GameSetupActivity.numPlayers) {
                        currentPlayer = 0;
                        round++;
                    }

                    // update name of round and player
                    ((TextView) findViewById(R.id.tvRound)).setText(String.format("Round %s", String.valueOf(round)));
                    ((TextView) findViewById(R.id.tvPlayer)).setText(String.format("Player %s", String.valueOf(currentPlayer + 1)));

                    // generate a new random question
                    generateRandomQuestion(tbQuestion, tbDrinks);

                    // if last turn of the game, show finish button
                    if (currentPlayer + 1 == GameSetupActivity.numPlayers && round == GameSetupActivity.numRounds) {
                        bNextQuestion.setEnabled(false);
                        bSkip.setVisibility(View.GONE);
                        bFinishGame.setVisibility(View.VISIBLE);
                    }

                    tbQuestion.setChecked(false);
                    tbDrinks.setChecked(false);

                    didDare = false;
                    didDrink = false;
                } else {
                    Toast.makeText(view.getContext(), "You must do a dare, drink, skip or reroll!", Toast.LENGTH_LONG).show();
                }
            }
        });

        bFinishGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                didDare = tbQuestion.isChecked();
                didDrink = tbDrinks.isChecked();

                if (didDare || didDrink) {
                    addPoints(difficulty, currentPlayer, didDare, didDrink);
                }

                launchResults();
            }
        });

    }

    private void generateRandomQuestion(ToggleButton tbQuestion, ToggleButton tbDrinks) {

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
                tbDrinks.setTextOn(GameSetupActivity.drinkType);
                tbDrinks.setTextOff(GameSetupActivity.drinkType);
                tbDrinks.setText(GameSetupActivity.drinkType);
                break;
            case 2:
                currentDifficulty = medium;
                tbDrinks.setTextOn("2 " + GameSetupActivity.drinkTypePlural);
                tbDrinks.setTextOff("2 " + GameSetupActivity.drinkTypePlural);
                tbDrinks.setText(String.format("2 %s", GameSetupActivity.drinkTypePlural));
                break;
            case 3:
                currentDifficulty = hard;
                tbDrinks.setTextOn("3 " + GameSetupActivity.drinkTypePlural);
                tbDrinks.setTextOff("3 " + GameSetupActivity.drinkTypePlural);
                tbDrinks.setText(String.format("3 %s", GameSetupActivity.drinkTypePlural));
                break;
            case 4:
                currentDifficulty = extreme;
                tbDrinks.setTextOn("4 " + GameSetupActivity.drinkTypePlural);
                tbDrinks.setTextOff("4 " + GameSetupActivity.drinkTypePlural);
                tbDrinks.setText(String.format("4 %s", GameSetupActivity.drinkTypePlural));
                break;
        }

        int questionIndex = random.nextInt(currentDifficulty.size());

        String randomString = currentDifficulty.get(questionIndex);
        tbQuestion.setTextOn(randomString);
        tbQuestion.setTextOff(randomString);
        tbQuestion.setText(randomString);

        difficultyAverage.set(currentPlayer,
                (difficultyAverage.get(currentPlayer) + difficulty) / Math.ceil((double) round / (double) GameSetupActivity.numPlayers)
        );
    }

    private void addDrink(int difficulty, int currentPlayer) {
        GameSetupActivity.drinks.set(currentPlayer,
                GameSetupActivity.drinks.get(currentPlayer) + GameSetupActivity.drinkLevel * (double) difficulty);
    }

    private void removePoints(int currentPlayer, double points) {
        GameSetupActivity.scores.set(currentPlayer, GameSetupActivity.scores.get(currentPlayer) - points);
    }

    private void addPoints(int difficulty, int currentPlayer, boolean didDare, boolean didDrink) {
        switch (difficulty) {
            case 1:
                if (didDare || didDrink) {
                    if (didDare && didDrink) {
                        GameSetupActivity.scores.set(currentPlayer, GameSetupActivity.scores.get(currentPlayer) + 2D);
                    } else {
                        GameSetupActivity.scores.set(currentPlayer, GameSetupActivity.scores.get(currentPlayer) + 1D);
                    }
                }
                break;
            case 2:
                if (didDare || didDrink) {
                    if (didDare && didDrink) {
                        GameSetupActivity.scores.set(currentPlayer, GameSetupActivity.scores.get(currentPlayer) + 4D);
                    } else {
                        GameSetupActivity.scores.set(currentPlayer, GameSetupActivity.scores.get(currentPlayer) + 2D);
                    }
                }
                break;
            case 3:
                if (didDare || didDrink) {
                    if (didDare && didDrink) {
                        GameSetupActivity.scores.set(currentPlayer, GameSetupActivity.scores.get(currentPlayer) + 6D);
                    } else {
                        GameSetupActivity.scores.set(currentPlayer, GameSetupActivity.scores.get(currentPlayer) + 3D);
                    }
                }
                break;
            case 4:
                if (didDare || didDrink) {
                    if (didDare && didDrink) {
                        GameSetupActivity.scores.set(currentPlayer, GameSetupActivity.scores.get(currentPlayer) + 8D);
                    } else {
                        GameSetupActivity.scores.set(currentPlayer, GameSetupActivity.scores.get(currentPlayer) + 4D);
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


