package com.fantasy.excape.fantasy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by song on 2016/1/9.
 */
public class ChooseActivity extends Activity {
    private SharedPreferences key;
    private Button monster, monk;
    // 返回鍵退出的參數
    private long lastBackTime = 0;
    private long currentBackTime = 0;

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
                        key.edit().putBoolean("ISWARRIOR",false).commit();
                        key.edit().putInt("FLAG3", 2).commit();
                        break;
                    case R.id.monk:
                        key.edit().putBoolean("ISWARRIOR",true).commit();
                        key.edit().putInt("FLAG3", 1).commit();
                        break;
                }
                Intent intent = new Intent(ChooseActivity.this, DramaActivity.class);
                startActivity(intent);
                ChooseActivity.this.finish();

            }
        };
        monster.setOnClickListener(clickListener);
        monk.setOnClickListener(clickListener);
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
