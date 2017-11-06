package com.example.code.floatingwindowdemo.callback;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2017/11/4
 */
public interface ScreenShotCallBack {
    void screenShotSuccess(byte[] image);
    void screenShotSuccess(String imagePath);
    void screenShotError(Exception e);
}
