package com.tt.mvpcircualrectview.utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;

/**
 * Created by TuoZhaoBing on 2016/4/14 0014.
 */
public class MeasureUtils {
    public static final String TAG = "MeasureUtils";
    public static Rect getRect(int centerx,int centy,int width,int height){
        return new Rect(centerx-width/2,centy-height/2,centerx+width/2,centy+height/2);
    }

    public static int getScreenWidth(Context context){
        DisplayMetrics dm =context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context){
        DisplayMetrics dm =context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
}
