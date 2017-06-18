package com.example.mobilesafe2.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mobilesafe2.R;

/**
 * Created by Administrator on 2017/6/18.
 */

public class SettingItemView extends RelativeLayout {

    private TextView tv_title;
    private ImageView iv_switch;
    private boolean flag = false;       //用来记录开关的状态

    public SettingItemView(Context context) {
        super(context);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        String title = a.getString(R.styleable.SettingItemView_title);
        int bgType = a.getInt(R.styleable.SettingItemView_bgType, -1);



        View view = View.inflate(context, R.layout.view_settingitemview, null);
        this.addView(view);
        tv_title = (TextView) view.findViewById(R.id.tv_title);

        tv_title.setText(title);

        switch (bgType){
            case 0:
                this.setBackgroundResource(R.drawable.selector_first_bg);
                break;
            case 1:
                this.setBackgroundResource(R.drawable.selector_middle_bg);
                break;
            case 2:
                this.setBackgroundResource(R.drawable.selector_last_bg);
                break;
            default:

                break;
        }

        iv_switch = (ImageView) view.findViewById(R.id.iv_switch);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//根据开关状态设置对应的图片
    public void setToggle(boolean b){
        if (b) {
            iv_switch.setImageResource(R.drawable.on);
        } else {
            iv_switch.setImageResource(R.drawable.off);
        }
        //记录开关的状态
        this.flag = b;
    }

    public boolean isToggle(){
        return flag;
    }
//开关状态取反操作
    public void toggle() {
        setToggle(!this.flag);
    }

//    public void setTitle(String title){
//        tv_title.setText(title);
//    }
}
