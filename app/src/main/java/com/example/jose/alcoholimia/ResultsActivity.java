package com.example.jose.alcoholimia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Collections;

/**
 * Results activity
 * Created by Jose on 3/27/2016.
 */
public class ResultsActivity extends AppCompatActivity {

    double maxScore = Collections.max(GameSetupActivity.scores);
    double minScore = Collections.min(GameSetupActivity.scores);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        LinearLayout layoutResults = (LinearLayout) findViewById(R.id.layoutResults);
        TableLayout tableResults = (TableLayout) findViewById(R.id.tableResults);

        ScrollView mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mScrollView.smoothScrollTo(0, layoutResults.getHeight());

        tableResults.addView(createTableHeaders());


        for (int i = 0; i < GameSetupActivity.numPlayers; i++) {
            tableResults.addView(createTableRow(i+1, GameSetupActivity.scores.get(i), GameSetupActivity.drinks.get(i)));
        }
    }

    public TableRow createTableRow(int playerNumber, double playerScore, double playerDrinks) {
        DecimalFormat formatter = new DecimalFormat("#,###,###,##0.00");
        TableRow row = new TableRow(this);

        TextView tvPlayerNumber = new TextView(this);
        tvPlayerNumber.setText(Integer.toString(playerNumber));
        formatCell(tvPlayerNumber);
        row.addView(tvPlayerNumber);

        TextView tvPlayerScore = new TextView(this);
        tvPlayerScore.setText(formatter.format(playerScore));
        formatCell(tvPlayerScore);
        row.addView(tvPlayerScore);

        TextView tvPlayerDrinks = new TextView(this);
        tvPlayerDrinks.setText(formatter.format(playerDrinks));
        formatCell(tvPlayerDrinks);
        row.addView(tvPlayerDrinks);

        TextView Result = new TextView(this);

        if (playerScore == maxScore) {
            Result.setText("Winner");
            formatCell(Result);
            row.addView(Result);
        }

        else if (playerScore == minScore) {
            Result.setText("Loser");
            formatCell(Result);
            row.addView(Result);
        }

        return row;
    }

    public TableRow createTableHeaders() {
        TableRow row = new TableRow(this);

        TextView tvHeaderPlayerNumber = new TextView(this);
        tvHeaderPlayerNumber.setText("Player");
        formatCell(tvHeaderPlayerNumber);
        row.addView(tvHeaderPlayerNumber);

        TextView tvHeaderPlayerScore = new TextView(this);
        tvHeaderPlayerScore.setText("Score");
        formatCell(tvHeaderPlayerScore);
        row.addView(tvHeaderPlayerScore);

        TextView tvHeaderPlayerDrinks = new TextView(this);
        tvHeaderPlayerDrinks.setText("mL");
        formatCell(tvHeaderPlayerDrinks);
        row.addView(tvHeaderPlayerDrinks);

        return row;
    }

    public void formatCell(TextView cell) {
        cell.setTextSize(20);
        cell.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        cell.setPadding(30,20,30,20);
        cell.setHeight(150);
        cell.setWidth(300);
    }

}
