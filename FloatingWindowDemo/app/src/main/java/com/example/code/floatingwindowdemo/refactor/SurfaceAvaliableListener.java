package com.dfwd.wlkt.screenbroadcasting.refactor;

import android.view.SurfaceHolder;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/2/9
 */
public interface SurfaceAvaliableListener {
    public void surfaceCreated(SurfaceHolder holder);
    public void surfaceChanged(SurfaceHolder holder,int format,int width,int height);
    public void surfaceDestroyed(SurfaceHolder holder);
}
