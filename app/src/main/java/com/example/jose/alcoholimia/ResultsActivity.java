package com.example.jose.alcoholimia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Results activity
 * Created by Jose on 3/27/2016.
 */
public class ResultsActivity extends AppCompatActivity {

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
            tableResults.addView(createTableRow(i+1, GameSetupActivity.scores.get(i)));
        }
    }

    public TableRow createTableRow(int playerNumber, double playerScore) {
        TableRow row = new TableRow(this);

        TextView tvPlayerNumber = new TextView(this);
        tvPlayerNumber.setText(Integer.toString(playerNumber));
        formatCell(tvPlayerNumber);
        row.addView(tvPlayerNumber);

        TextView tvPlayerScore = new TextView(this);
        tvPlayerScore.setText(Double.toString(playerScore));
        formatCell(tvPlayerScore);
        row.addView(tvPlayerScore);

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
