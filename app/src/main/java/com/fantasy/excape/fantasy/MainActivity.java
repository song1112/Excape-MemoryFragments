package com.fantasy.excape.fantasy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.widget.Toast;

public class MainActivity extends Activity {

    private SharedPreferences key;
    private int level;
    Button start, newgame, explain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        key = getSharedPreferences("DATA", 0);
        level = key.getInt("LEVEL", 0);


        start = (Button)findViewById(R.id.start);
        newgame = (Button)findViewById(R.id.newgame);
        explain = (Button)findViewById(R.id.explain);

        if (level==0) {
            start.setEnabled(false);
            start.setVisibility(View.INVISIBLE);
        }

        // 按下繼續
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("log", "continue");
                Intent intent = new Intent(MainActivity.this, LevelActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // 按下新遊戲
        newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("log", "newgame");
                // 清除紀錄
                key.edit().clear();key.edit().clear();
                key.edit().putInt("LEVEL", 0).commit();
                key.edit().putInt("WARRIOR", 0).commit();
                key.edit().putInt("WIZARD", 0).commit();
                // 先跳轉到開頭劇情
                Intent intent = new Intent(MainActivity.this, DramaActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExplainActivity.class));
            }
        });
    }


}
