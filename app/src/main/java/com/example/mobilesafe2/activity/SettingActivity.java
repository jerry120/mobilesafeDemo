package com.example.mobilesafe2.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mobilesafe2.R;
import com.example.mobilesafe2.utils.Constant;
import com.example.mobilesafe2.utils.SpUtil;
import com.example.mobilesafe2.view.SettingItemView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private SettingItemView siv_autoupdate;
    private SettingItemView siv_location;
    private SettingItemView siv_style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();


    }

    private void initView() {
        siv_autoupdate = (SettingItemView) findViewById(R.id.siv_autoupdate);
        siv_location = (SettingItemView) findViewById(R.id.siv_location);
        siv_style = (SettingItemView) findViewById(R.id.siv_style);

        siv_autoupdate.setOnClickListener(this);
        siv_location.setOnClickListener(this);
        siv_style.setOnClickListener(this);

//        SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
//        boolean flag = sp.getBoolean("autoupdate", true);

        siv_autoupdate.setToggle(SpUtil.getBoolean(SettingActivity.this, Constant.KEY_AUTO_UPDATE,true));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.siv_autoupdate:
                siv_autoupdate.toggle();
                SpUtil.saveBoolean(SettingActivity.this, Constant.KEY_AUTO_UPDATE,siv_autoupdate.isToggle());
                break;
            case R.id.siv_location:
                siv_location.toggle();
                break;
            case R.id.siv_style:
                siv_style.toggle();
                break;
            default:

                break;
        }

    }
}
