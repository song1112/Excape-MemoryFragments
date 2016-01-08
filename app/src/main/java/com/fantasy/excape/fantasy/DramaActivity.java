package com.fantasy.excape.fantasy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by song on 2016/1/7.
 */
public class DramaActivity extends Activity {

    private SharedPreferences key;
    private int level;
    private int count=0;
    private boolean isWarrior;
    TextView dramaTView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drama_layout);

        key = getSharedPreferences("DATA", 0);
        // 取得目前所在的關卡
        level = key.getInt("LEVEL", 0);
        // 取得獲得的寶箱
        isWarrior = key.getBoolean("WARRIOR", true);


        dramaTView = (TextView)findViewById(R.id.drama);
        nextDialogue();
        dramaTView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nextDialogue()) {
                    level++;
                    key.edit().putInt("LEVEL", level).commit();
                    startActivity(new Intent(DramaActivity.this, LevelActivity.class));
                }
            }
        });
    }

    private boolean nextDialogue() {

        String[] drama = getDrama();
        if (count == drama.length)
            return false;
        else
            dramaTView.setText(drama[count] + "  >>>");
        count++;
        return true;
    }

    private String[] getDrama() {
        Resources res = getResources();
        switch (level) {
            case 0:
                return res.getStringArray(R.array.drama_level1_0);
            case 1:
                if (isWarrior)
                    return res.getStringArray(R.array.drama_level1_1);
                else
                    return res.getStringArray(R.array.drama_level1_2);
            case 2:
                if (isWarrior)
                    return res.getStringArray(R.array.drama_level2_1);
                else
                    return res.getStringArray(R.array.drama_level2_2);
            case 3:
                if (isWarrior)
                    return res.getStringArray(R.array.drama_level3_1);
                else
                    return res.getStringArray(R.array.drama_level3_2);
            case 4:
                if (isWarrior)
                    return res.getStringArray(R.array.drama_level4_1);
                else
                    return res.getStringArray(R.array.drama_level4_2);
            case 5:
                if (isWarrior)
                    return res.getStringArray(R.array.drama_level5_1);
                else
                    return res.getStringArray(R.array.drama_level5_2);
        }
        return null;
    }
}
