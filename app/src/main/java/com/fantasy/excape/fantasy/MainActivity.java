package com.fantasy.excape.fantasy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences key;
    private int level;
    Button start, newgame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        key = getSharedPreferences("DATA", 0);
        level = key.getInt("LEVEL", 0);


        start = (Button)findViewById(R.id.start);
        newgame = (Button)findViewById(R.id.newgame);

        if (level==0) {
            start.setEnabled(false);
            start.setVisibility(View.INVISIBLE);
        }
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("log", "continue");
                Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                startActivity(intent);
            }
        });

        newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("log", "newgame");
                key.edit().clear();
                key.edit().putInt("LEVEL", 0).commit();
                Intent intent = new Intent(MainActivity.this, DramaActivity.class);
                startActivity(intent);
            }
        });
    }
}
