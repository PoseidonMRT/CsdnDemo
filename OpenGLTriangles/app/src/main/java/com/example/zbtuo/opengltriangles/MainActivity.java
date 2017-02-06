package com.example.zbtuo.opengltriangles;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.example.zbtuo.opengltriangles.utils.DeviceInfor;

public class MainActivity extends AppCompatActivity {

  private GLSurfaceView mGLSurfaceView;
  private boolean mRendererSet = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mGLSurfaceView = new GLSurfaceView(this);
    if (DeviceInfor.isSupportOpenGLES2(this)){
      mGLSurfaceView.setEGLContextClientVersion(2);
      mGLSurfaceView.setRenderer(new TriangleRenderer(this));
      mRendererSet = true;
    }else {
      Toast.makeText(this, "This Device does not support open gl es 2.0 version!", Toast.LENGTH_SHORT).show();
      return;
    }
    setContentView(mGLSurfaceView);
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (mRendererSet){
      mGLSurfaceView.onPause();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (mRendererSet){
      mGLSurfaceView.onResume();
    }
  }
}
