package com.example.mobilesafe2.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/6/18.
 */

public class SpUtil {
    private static final String SP_NAME = "config";
    private static SharedPreferences sp;
    //保存boolean

    public static void saveBoolean(Context context, String key,boolean value){
        if(sp==null){
            sp = context.getSharedPreferences(SP_NAME,MODE_PRIVATE);
        }

        sp.edit().putBoolean(key,value).commit();
    }
    //获取boolean

    public static boolean getBoolean(Context context, String key, boolean deValue){
        if(sp==null){
            sp = context.getSharedPreferences(SP_NAME,MODE_PRIVATE);
        }

        return sp.getBoolean(key,deValue);
    }

}
