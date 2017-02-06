package com.example.zbtuo.opengltriangles.utils;

import static android.opengl.GLES20.GL_VERTEX_SHADER;

import static android.opengl.GLES20.*;

/**
 * Created by zbtuo on 17-1-24.
 *
 * Function Declaration :
 */

public class ShaderHelper {

  public static final String TAG = "ShaderHelper";

  public static int compileVertexShader(String shaderCode) {
    return compileShader(GL_VERTEX_SHADER, shaderCode);
  }

  public static int compileFragmentShader(String shaderCode) {
    return compileShader(GL_FRAGMENT_SHADER, shaderCode);
  }

  private static int compileShader(int type, String shaderCode) {
    int shaderObjectId = glCreateShader(type);
    if (shaderObjectId == 0) {
      return 0;
    }
    glShaderSource(shaderObjectId, shaderCode);
    glCompileShader(shaderObjectId);
    final int[] compileStatus = new int[1];
    glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
    if (compileStatus[0] == 0) {
      glDeleteShader(shaderObjectId);
      return 0;
    }
    return shaderObjectId;
  }

  public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
    int programId = glCreateProgram();
    if (programId == 0) {
      return 0;
    }
    glAttachShader(programId, vertexShaderId);
    glAttachShader(programId, fragmentShaderId);
    glLinkProgram(programId);
    final int[] linkStatus = new int[1];
    glGetProgramiv(programId, GL_LINK_STATUS, linkStatus, 0);
    if (linkStatus[0] == 0) {
      glDeleteProgram(programId);
      return 0;
    }
    return programId;
  }

  public static boolean validateProgram(int programId) {
    glValidateProgram(programId);
    final int[] validateStatus = new int[1];
    glGetProgramiv(programId, GL_VALIDATE_STATUS, validateStatus, 0);
    return validateStatus[0] != 0;
  }
}
