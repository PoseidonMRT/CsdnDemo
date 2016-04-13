package com.tt.colormatrixdemo;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by TuoZhaoBing on 2016/4/13 0013.
 */
public class MeasureUtils {
    public static final String TAG = "MeasureUtils";
    public static int getScreenWidth(Context context){
        DisplayMetrics dm =context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context){
        DisplayMetrics dm =context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
}
