package com.study.opengles02;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;

/**
 * Created by tuozhaobing on 17/12/11.
 */

public class Utils {

  public static boolean hasGLES20(Context context) {
    ActivityManager am = (ActivityManager)
        context.getSystemService(Context.ACTIVITY_SERVICE);
    ConfigurationInfo info = am.getDeviceConfigurationInfo();
    return info.reqGlEsVersion >= 0x20000;
  }

}
