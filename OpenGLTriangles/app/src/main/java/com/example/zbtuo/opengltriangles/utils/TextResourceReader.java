package com.example.zbtuo.opengltriangles.utils;

import android.content.Context;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zbtuo on 17-1-24.
 *
 * Function Declaration :read GLSL file from raw resource
 */

public class TextResourceReader {

  /**
   * read glsl file from resource
   * @param context
   * @param resourceId example R.raw.vertex_shader
   * @return
   */
  public static String readTextFileFromResource(Context context,int resourceId){
    StringBuilder body = new StringBuilder();
    try {
      InputStream inputStream = context.getResources().openRawResource(resourceId);
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      String nextLine;
      while ((nextLine = bufferedReader.readLine()) != null){
        body.append(nextLine);
        body.append('\n');
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return body.toString();
  }
}
