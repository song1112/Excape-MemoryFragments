package com.fantasy.excape.fantasy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by song on 2016/1/9.
 */
public class ChooseActivity extends Activity {
    private SharedPreferences key;
    private Button monster, monk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_layout);
        monster = (Button)findViewById(R.id.monster);
        monk = (Button)findViewById(R.id.monk);

        key = getSharedPreferences("DATA", 0);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.monster:
                        key.edit().putBoolean("WARRIOR",false).commit();
                        break;
                    case R.id.monk:
                        key.edit().putBoolean("WARRIOR",true).commit();
                        break;
                }
                startActivity(new Intent(ChooseActivity.this, DramaActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)); // 按下返回鍵不會返回上一頁
            }
        };
        monster.setOnClickListener(clickListener);
        monk.setOnClickListener(clickListener);
    }
}
