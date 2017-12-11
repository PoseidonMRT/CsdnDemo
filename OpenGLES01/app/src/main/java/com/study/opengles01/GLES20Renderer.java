package com.study.opengles01;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;

/**
 * Created by tuozhaobing on 17/12/10.
 */

class GLES20Renderer extends GLRenderer {

  @Override
  public void onCreate(int width, int height,
      boolean contextLost) {
    GLES20.glClearColor(1f, 0f, 0f, 1f);
  }

  @Override
  public void onDrawFrame(boolean firstDraw) {
    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT
        | GLES20.GL_DEPTH_BUFFER_BIT);
  }
}
