package com.example.code.floatingwindowdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2017/11/3
 */
public class MediaProjectionPermissionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
//        if (action.equals(Contans.ACTION_REQUEST_MEDIA_PROJECTION)){
            context.startActivity(new Intent(context, ScreenProjectionPermissionActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//        }
    }
}
