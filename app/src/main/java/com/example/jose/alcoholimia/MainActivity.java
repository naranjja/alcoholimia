package com.example.jose.alcoholimia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bStart).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchGameSetup();
            }

        });
    }

    private void launchGameSetup() {
        Intent intent = new Intent(MainActivity.this, GameSetupActivity.class);
        startActivity(intent);
        finish();
    }
}
