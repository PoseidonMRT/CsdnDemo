package com.example.code.permissionsrequestdemo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2017/11/30
 */
public class PermissionHelper {

    private static final String TAG = "PermissionHelper";
    private static PermissionHelper mPermissionHelper;

    private PermissionHelper(){

    }

    public static PermissionHelper newInstance(Activity activity) {
        if (mPermissionHelper == null){
            mPermissionHelper = new PermissionHelper();
        }
        return mPermissionHelper;
    }

    public boolean hasPermission(@NonNull Context context,@Size(min = 1) @NonNull String... perms){
        // Always return true for SDK < M, let the system deal with the permissions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.w(TAG, "hasPermissions: API version < M, returning true by default");

            // DANGER ZONE!!! Changing this will break the library.
            return true;
        }

        // Null context may be passed if we have detected Low API (less than M) so getting
        // to this point with a null context should not be possible.
        if (context == null) {
            throw new IllegalArgumentException("Can't check permissions for null context");
        }

        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(context, perm)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }
}
