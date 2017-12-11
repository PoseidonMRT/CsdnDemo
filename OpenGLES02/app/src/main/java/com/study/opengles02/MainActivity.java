package com.study.opengles02;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

  private GLSurfaceView mGLSurfaceView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (Utils.hasGLES20(this)){
      mGLSurfaceView = new GLSurfaceView(this);
      mGLSurfaceView.setRenderer(new OpenGLRenderer());
    }else{
      finish();
    }
    setContentView(mGLSurfaceView);
  }
}
