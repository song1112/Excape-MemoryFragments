package com.fantasy.excape.fantasy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by song on 2016/1/7.
 */
public class DramaActivity extends Activity {

    private SharedPreferences key;
    private int level; // 目前關卡
    private int count=0; // 對話數
    private int end=6; // 結局關卡號
    private boolean isWarrior; // 是否是戰士
    TextView dramaTView;
    // 返回鍵退出的參數
    private long lastBackTime = 0;
    private long currentBackTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drama_layout);

        key = getSharedPreferences("DATA", 0);
        // 取得目前所在的關卡
        level = key.getInt("LEVEL", 0);
        // 取得獲得的寶箱
        isWarrior = key.getBoolean("ISWARRIOR", true);

        dramaTView = (TextView)findViewById(R.id.drama);
        // 先進行第一個對話
        nextDialogue();

        // 按下對話內容進行下一個對話
        dramaTView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 當對話結尾時
                if (!nextDialogue()) {
                    // 如果現在關卡不是END
                    if(level!=end) {
                        // 如果選擇是戰士寶箱，戰士的FAGE+1
                        if (level!=0) {
                            if (isWarrior) {
                                int warrior = key.getInt("WARRIOR", 0) + 1;
                                key.edit().putInt("WARRIOR", warrior).commit();
                            } else {
                                int wizard = key.getInt("WIZARD", 0) + 1;
                                key.edit().putInt("WIZARD", wizard).commit();
                            }
                            Log.i("log", "WARRIOR:" + key.getInt("WARRIOR", 0)+"，WIZARD"+key.getInt("WIZARD:", 0));
                        }


                        // 存下一關
                        level++;
                        key.edit().putInt("LEVEL", level).commit();

                        // 跳轉到關卡畫面
                        Intent intent = new Intent(DramaActivity.this, LevelActivity.class);
                        startActivity(intent);
                        DramaActivity.this.finish();

                    } else {
                        endingDrama();
                    }
                }
            }
        });
    }

    private boolean nextDialogue() {
        // 取得劇情內容
        String[] drama = getDrama();
        // 如果是劇情結尾時傳回false
        if (count == drama.length)
            return false;
        else
            dramaTView.setText(drama[count] + "  >>>");
        count++;
        return true;
    }

    private String[] getDrama() {
        // 依照對應的關卡取得劇情內容
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
            case 6:
                return  res.getStringArray(R.array.drama_level6);
        }
        return null;
    }

    private void endingDrama() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //捕获返回键按下的事件
        if(keyCode == KeyEvent.KEYCODE_BACK){
            //获取当前系统时间的毫秒数
            currentBackTime = System.currentTimeMillis();
            //比较上次按下返回键和当前按下返回键的时间差，如果大于2秒，则提示再按一次退出
            if(currentBackTime - lastBackTime > 2 * 1000){
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                lastBackTime = currentBackTime;
            }else{ //如果两次按下的时间差小于2秒，则退出程序
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
