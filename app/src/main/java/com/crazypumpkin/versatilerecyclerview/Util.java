package com.crazypumpkin.versatilerecyclerview;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by CrazyPumPkin on 2016/12/13.
 */

public class Util {

    /**
     * 从assets下读取文本
     * @param context
     * @param fileName
     * @return
     */
    public static String getTextFromAssets(Context context, String fileName){
        String result = "";
        try {
            InputStream is = context.getAssets().open(fileName);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            result = new String(buffer,"utf-8");
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
