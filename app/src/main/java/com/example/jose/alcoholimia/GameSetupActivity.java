package com.example.jose.alcoholimia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Game setup activity
 * Created by Jose on 3/27/2016.
 */

public class GameSetupActivity extends AppCompatActivity {

    public static int numPlayers;
    public static int numRounds;
    public static ArrayList<String> players;
    public static ArrayList<Double> scores;
    public static ArrayList<Double> drinks;
    public static String drinkType;
    public static String drinkTypePlural;
    public static double drinkLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);

        ArrayList<String> drinkTypes = new ArrayList<>(Arrays.asList(
                "Rum", "Vodka", "Whiskey", "Pisco", "Tequila"));

        Spinner spDrinkTypes = (Spinner) findViewById(R.id.spDrinkTypes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, drinkTypes);
        spDrinkTypes.setAdapter(adapter);

        final TextView tvServingSize = (TextView) findViewById(R.id.tvServingSize);

        spDrinkTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        tvServingSize.setText("Drinks will be served in glasses of rum");
                        drinkType = "1 glass of rum";
                        drinkTypePlural = "glasses of rum";
                        drinkLevel = 20.7;
                        break;
                    case 1:
                        tvServingSize.setText("Drinks will be served in shots of vodka");
                        drinkType = "1 shot of vodka";
                        drinkTypePlural = "shots of vodka";
                        drinkLevel = 18.7;
                        break;
                    case 2:
                        tvServingSize.setText("Drinks will be served in glasses of whiskey");
                        drinkType = "1 glass of whiskey";
                        drinkTypePlural = "glasses of whiskey";
                        drinkLevel = 17.6;
                        break;
                    case 3:
                        tvServingSize.setText("Drinks will be served in shots of pisco");
                        drinkType = "1 shot of pisco";
                        drinkTypePlural = "shots of pisco";
                        drinkLevel = 20.12;
                        break;
                    case 4:
                        tvServingSize.setText("Drinks will be served in shots of tequila");
                        drinkType = "1 shot of tequila";
                        drinkTypePlural = "shots of tequila";
                        drinkLevel = 21.01;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.bStartGame).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                getInfo();

                if (validateInfo()) {
                    setupGame();
                    launchGame();
                }
            }

        });
    }

    private void setupGame() {
        players = new ArrayList<>(Collections.nCopies(numPlayers, ""));
        scores = new ArrayList<>(Collections.nCopies(numPlayers, 0D));
        drinks = new ArrayList<>(Collections.nCopies(numPlayers, 0D));
    }

    private void launchGame() {
        Intent intent = new Intent(GameSetupActivity.this, GameActivity.class);
        startActivity(intent);
    }

    private boolean validateInfo() {
        if (numPlayers < 1) {
            Toast.makeText(this, "Not enough players", Toast.LENGTH_LONG).show();
            return false;
        }

        else if (numRounds < 1) {
            Toast.makeText(this, "Not enough rounds", Toast.LENGTH_LONG).show();
            return false;
        }

        else {
            return true;
        }
    }

    private void getInfo() {
        numPlayers = Integer.parseInt(((EditText) findViewById(R.id.etNumPlayers)).getText().toString());
        numRounds = Integer.parseInt(((EditText) findViewById(R.id.etNumRounds)).getText().toString());
    }
}

