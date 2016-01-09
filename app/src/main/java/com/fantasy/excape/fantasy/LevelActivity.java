package com.fantasy.excape.fantasy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by song on 2016/1/5.
 */
public class LevelActivity extends Activity {
    private long lastBackTime = 0;
    //当前按下返回键的系统时间
    private long currentBackTime = 0;
    private SharedPreferences key;
    private int level;
    Button treasure;
    private RelativeLayout level_layout;
    private float tmpx1=0, tmpx2=0; // 設定手勢
    private int role;
    private String passwd;
    private int side=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化
        level_layout = new RelativeLayout(this);
        treasure = new Button(this);

        // 取得螢幕大小
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        // 取得目前所在關卡
        key = getSharedPreferences("DATA", 0);
        level = key.getInt("LEVEL", 1);

        // 用來裝寶箱的layout
        RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams (height/6, height/6);
        btParams.leftMargin = width/3;
        btParams.topMargin = height/7 * 4;
        Builder builder = new Builder(this);

        // 設定畫面
        setLevelLayout();
        level_layout.addView(treasure, btParams);
        this.setContentView(level_layout);

        treasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passworkDialog();
            }
        });

    }

    public void passworkDialog() {

        // 寶箱輸入密碼的畫面
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.passwork_dialog, (ViewGroup) findViewById(R.id.password_dialog));

        // 對話框
        final Builder  builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        final AlertDialog dialog = builder.show();

        final EditText passwdEdit = (EditText) layout.findViewById(R.id.password);

        // 監聽文字變化
        passwdEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {

                String input = passwdEdit.getText().toString();
                if (input.length() == passwd.length()) {
                    // 當密碼相同時
                    if (input.equals(passwd)) {
                        dialog.hide();
                        // 跳轉到劇情
                        Intent intent = new Intent(LevelActivity.this, DramaActivity.class);
                        startActivity(intent);
                        LevelActivity.this.finish();
                    }
                    else {
                        dialog.hide();
                        Toast.makeText(LevelActivity.this, "密碼不對啊", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    // 設定手勢
    public boolean onTouchEvent(MotionEvent event) {
        // 取得現在座標
        float x = event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                tmpx1 = x;
                break;
            case MotionEvent.ACTION_UP:
                tmpx2 = x;
                if(tmpx1 != 0){
                    if(tmpx1 - tmpx2 > 8)  // 右滑
                        side++;
                    if(tmpx2 - tmpx1 > 8) // 左滑
                        side--;
                    setLevelLayout(); // 設定畫面
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    // 設定關卡畫面
    public void setLevelLayout() {

        // 面
        if (side>4)
            side = 1;
        else if (side<1)
            side = 4;

        // 關卡
        switch (level) {
            case 2:
                switch (side) {
                    case 1:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level2_1));
                        passwd = "135";
                        key.edit().putBoolean("ISWARRIOR",false).commit();
                        key.edit().putInt("FLAG1", 2).commit();
                        setTreasure(true);
                        break;
                    case 2:
                        passwd = "313";
                        key.edit().putBoolean("ISWARRIOR",true).commit();
                        key.edit().putInt("FLAG1", 1).commit();
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level2_2));
                        setTreasure(true);
                        break;
                    case 3:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level2_3));
                        setTreasure(false);
                        break;
                    case 4:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level2_4));
                        setTreasure(false);
                        break;
                }
                break;
            case 3:
                switch (side) {
                    case 1:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level3_1));
                        setTreasure(false);
                        break;
                    case 2:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level3_2));
                        passwd = "759";
                        key.edit().putBoolean("ISWARRIOR",true).commit();
                        setTreasure(true);
                        break;
                    case 3:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level3_3));
                        setTreasure(false);
                        break;
                    case 4:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level3_4));
                        passwd = "779";
                        key.edit().putBoolean("ISWARRIOR",false).commit();
                        setTreasure(true);
                        break;
                }
                break;
            case 4:
                passwd = "7620";
                switch (side) {

                    case 1:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level4_1));
                        setTreasure(false);
                        break;
                    case 2:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level4_2));
                        key.edit().putBoolean("ISWARRIOR",false).commit();
                        key.edit().putInt("FLAG2", 2).commit();
                        setTreasure(true);
                        break;
                    case 3:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level4_3));
                        setTreasure(false);
                        break;
                    case 4:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level4_4));
                        key.edit().putBoolean("ISWARRIOR",true).commit();
                        key.edit().putInt("FLAG2", 1).commit();
                        setTreasure(true);
                        break;
                }
                break;
            case 5:
                passwd = "100100100100";
                switch (side) {
                    case 1:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level5_1));
                        setTreasure(true);
                        key.edit().putBoolean("ISWARRIOR",false).commit();
                        break;
                    case 2:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level5_2));
                        setTreasure(false);
                        break;
                    case 3:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level5_3));
                        setTreasure(false);
                        break;
                    case 4:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level5_4));
                        setTreasure(true);
                        key.edit().putBoolean("ISWARRIOR",true).commit();
                        break;
                }
                break;
            case 6:
                startActivity(new Intent(LevelActivity.this, ChooseActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)); // 按下返回鍵不會返回上一頁
                LevelActivity.this.finish();
                break;
            case 1:
            default:
                switch (side) {
                    case 1:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level1_1));
                        passwd = "154";
                        key.edit().putBoolean("ISWARRIOR",true).commit();
                        setTreasure(true);
                        break;
                    case 2:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level1_2));
                        setTreasure(false);
                        break;
                    case 3:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level1_3));
                        passwd = "143";
                        key.edit().putBoolean("ISWARRIOR",false).commit();
                        setTreasure(true);
                        break;
                    case 4:
                        level_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.level1_4));
                        setTreasure(false);
                        break;
                }
        }
    }

    public void setTreasure(boolean visible) {
        treasure.setBackgroundDrawable(getResources().getDrawable(R.drawable.treasure));
        // 設定寶箱不可互動
        treasure.setEnabled(visible);
        // 不可見
        if(visible)
            treasure.setVisibility(View.VISIBLE);
        else
            treasure.setVisibility(View.INVISIBLE);
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
