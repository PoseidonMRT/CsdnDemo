package com.example.code.floatingwindowdemo;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2017/11/3
 */

import android.app.Activity;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ScreenProjectionPermissionActivity extends Activity {

    public static final String TAG = "ScreenProjectionService";
    private MediaProjection mMediaProjection;
    private static final int REQUEST_CODE = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "Permission Request Activity");
        Intent captureIntent = FloatingWindowApplication.getInstance().getMediaProjectionManager().createScreenCaptureIntent();
        startActivityForResult(captureIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.e(TAG,"Permission Request Activity Result OK");
                mMediaProjection = FloatingWindowApplication.getInstance().getMediaProjectionManager().getMediaProjection(resultCode, data);
                FloatingWindowApplication.getInstance().setMediaProjection(mMediaProjection);

            } else {
                Log.e(TAG,"Permission Request Activity Result Canceled");
                Toast.makeText(this, getResources().getString(R.string.str_permission_needed), Toast.LENGTH_SHORT)
                        .show();
            }
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, getResources().getString(R.string.str_permission_needed), Toast.LENGTH_SHORT)
                .show();
        finish();
    }
}
