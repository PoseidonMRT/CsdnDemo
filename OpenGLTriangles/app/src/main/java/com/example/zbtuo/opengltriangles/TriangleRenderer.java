package com.example.zbtuo.opengltriangles;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import com.example.zbtuo.opengltriangles.utils.ShaderHelper;
import com.example.zbtuo.opengltriangles.utils.TextResourceReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by zbtuo on 17-1-24.
 *
 * Function Declaration :
 */
public class TriangleRenderer implements Renderer {

  private Context mContext;
  private static final int POSITION_COMPONENT_COUNT = 2;

  float[] vertexTrangles = {
      -1.0f, -1.0f,
      1.0f, -1.0f,
      0f, 1.0f
  };

  private static final int BYTES_PER_FLOAT = 4;

  private FloatBuffer vertexData;
  private int mProgramId;

  private static final String U_COLOR = "u_Color";
  private int uColorLocation;
  private static final String A_POSITION = "a_Position";
  private int aPositionLocation;

  public TriangleRenderer(Context context) {
    mContext = context;
    vertexData = ByteBuffer.allocateDirect(vertexTrangles.length * BYTES_PER_FLOAT)
        .order(ByteOrder.nativeOrder()).asFloatBuffer();
    vertexData.put(vertexTrangles);
  }

  @Override
  public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
    gl10.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    String vertexShaderSource = TextResourceReader
        .readTextFileFromResource(mContext, R.raw.simple_vertex_shader);
    String fragmentShaderSource = TextResourceReader
        .readTextFileFromResource(mContext, R.raw.simple_fragment_shader);
    int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
    int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
    mProgramId = ShaderHelper.linkProgram(vertexShader, fragmentShader);
    ShaderHelper.validateProgram(mProgramId);
    glUseProgram(mProgramId);
    uColorLocation = glGetUniformLocation(mProgramId, U_COLOR);
    aPositionLocation = glGetAttribLocation(mProgramId, A_POSITION);
    vertexData.position(0);
    glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0,
        vertexData);
    glEnableVertexAttribArray(aPositionLocation);
  }

  @Override
  public void onSurfaceChanged(GL10 gl10, int i, int i1) {
    gl10.glViewport(0, 0, i, i1);
  }

  @Override
  public void onDrawFrame(GL10 gl10) {
    gl10.glClear(gl10.GL_COLOR_BUFFER_BIT);
    glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
    glDrawArrays(GL_TRIANGLE_STRIP, 0, 3);
  }
}
