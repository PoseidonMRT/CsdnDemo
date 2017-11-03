package com.example.code.floatingwindowdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaCas;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2017/11/3
 */
public class ScreenShotHelper {

    public static final String TAG = "ScreenShotHelper";
    private ImageReader mImageReader = null;
    private Handler handler = new Handler();

    private int mWindowWidth;
    private int mWindowHeight;
    private Context mContext;
    private DisplayMetrics mDisplayMetrics;
    private VirtualDisplay mVirtualDisplay;

    private static ScreenShotHelper mScreenShotHelper;

    private ScreenShotHelper(Context context){
        mWindowWidth = Utils.getDeviceWidth(context);
        mWindowHeight = Utils.getAbsoluteDeviceHeight(context);

        mDisplayMetrics = new DisplayMetrics();
        Utils.getScreenMetrics(context, mDisplayMetrics);

        mContext = context;
    }

    public synchronized static ScreenShotHelper getInstance(Context context){
        if (mScreenShotHelper == null){
            mScreenShotHelper = new ScreenShotHelper(context);
        }
        return mScreenShotHelper;
    }

    public void takeScreenShot(String filePath,String fileName){
            mImageReader = ImageReader.newInstance(mWindowWidth, mWindowHeight, PixelFormat.RGBA_8888, 1);
            mImageReader.setOnImageAvailableListener(new ImageAvailableListener(), handler);
            mVirtualDisplay = FloatingWindowApplication.getInstance().getMediaProjection().createVirtualDisplay("capture_screen",
                    mWindowWidth, mWindowHeight, mDisplayMetrics.densityDpi,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_PRESENTATION, mImageReader.getSurface
                            (), null, null);
    }

    class ImageAvailableListener implements ImageReader.OnImageAvailableListener {
        @Override
        public void onImageAvailable(ImageReader reader) {
            Log.e(TAG,"Image Available");
            Image image = reader.acquireLatestImage();
            if (image == null) {
                return;
            }

            int width = image.getWidth();
            int height = image.getHeight();
            //delete soft key height from page if needed
            if (Utils.hasNavBar(mContext)) {
                Log.e(TAG,"has navigation bar");
                height -= Utils.getNavigationBarHeight(mContext);
            }

            final Image.Plane[] planes = image.getPlanes();
            final ByteBuffer buffer = planes[0].getBuffer();
            int pixelStride = planes[0].getPixelStride();
            int rowStride = planes[0].getRowStride();
            int rowPadding = rowStride - pixelStride * width;

            Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap
                    .Config.ARGB_8888);
            bitmap.copyPixelsFromBuffer(buffer);
            image.close();

            Log.e(TAG,"bitmap  create success ");
            try {
                mScreenShotCallBack.screenShotSuccess(mImagePath + mImageName);
            } catch (IOException e) {
                Log.e(TAG,"file save failure ");
                Log.e(TAG,e.toString());
                mScreenShotCallBack.screenShotFailure(e);
            } finally {
                if (mImageReader != null) {
                    mImageReader.close();
                    mImageReader = null;
                }
            }
        }
    }
}
