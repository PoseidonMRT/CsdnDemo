package com.example.zbtuo.opengltriangles.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

/**
 * Created by zbtuo on 17-1-24.
 *
 * Function Declaration :some device infor code in this file,such as whether device support open gl
 * es 2 and so on.
 */

public class DeviceInfor {

  public static final String FINGERPRINT_GENERIC = "generic";
  public static final String FINGERPRINT_UNKNOWN = "unknown";
  public static final String MODEL_GOOGLE = "google_sdk";
  public static final String MODEL_EMULATOR = "Emulator";
  public static final String MODEL_SDK = "Android SDK built for x86";

  /**
   * help to judge whether device support open gl es version 2.0
   * @param context
   * @return true:support 2.0 version,false:do not support 2.0 version
   */
  public static boolean isSupportOpenGLES2(Context context) {
    ActivityManager activityManager = (ActivityManager) context
        .getSystemService(Context.ACTIVITY_SERVICE);
    ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
    return configurationInfo.reqGlEsVersion >= 0x20000 || (
        VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH_MR1 && (
            Build.FINGERPRINT.startsWith(FINGERPRINT_GENERIC) || Build.FINGERPRINT
                .startsWith(FINGERPRINT_UNKNOWN) || Build.MODEL.contains(MODEL_GOOGLE)
                || Build.MODEL.contains(MODEL_EMULATOR) || Build.MODEL.contains(MODEL_SDK)));
  }
}
