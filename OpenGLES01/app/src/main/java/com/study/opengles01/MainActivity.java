package com.study.opengles01;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  private GLSurfaceView mGLSurfaceView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //全屏显示
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    //判断当前设备是否支持OpenGL ES2.0
    if (hasGLES20()){
      //初始化GLSurfaceView
      mGLSurfaceView = new GLSurfaceView(this);
      mGLSurfaceView.setEGLContextClientVersion(2);
      mGLSurfaceView.setPreserveEGLContextOnPause(true);
      //设置GLSurfaceView的渲染器
      mGLSurfaceView.setRenderer(new GLES20Renderer());
    }else{
      Toast.makeText(this,"Device not support OpenGL ES 2.0 version",Toast.LENGTH_SHORT).show();
      finish();
    }
    setContentView(mGLSurfaceView);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (mGLSurfaceView != null){
      mGLSurfaceView.onResume();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (mGLSurfaceView != null){
      mGLSurfaceView.onPause();
    }
  }


  private boolean hasGLES20() {
    ActivityManager am = (ActivityManager)
        getSystemService(Context.ACTIVITY_SERVICE);
    ConfigurationInfo info = am.getDeviceConfigurationInfo();
    return info.reqGlEsVersion >= 0x20000;
  }
}
