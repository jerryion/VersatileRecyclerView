package com.crazypumpkin.versatilerecyclerview.library;

import android.content.res.Resources;

/**
 * 常用函数
 * Created by CrazyPumPkin on 2016/12/3.
 */

public class Util {
    /**
     * dp转px
     * @param dp
     */
    public static int dp2px(float dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }

    /**
     * sp转px
     * @param sp
     * @return
     */
    public static int sp2px(float sp){
        return (int) (sp * Resources.getSystem().getDisplayMetrics().scaledDensity + 0.5f);
    }
}
