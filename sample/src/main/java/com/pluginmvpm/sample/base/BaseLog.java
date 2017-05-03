package com.pluginmvpm.sample.base;

import android.util.Log;

/**
 * Created by wangshizhan on 17/4/6.
 */

public class BaseLog {
    private static boolean isShow = true;

//    private static boolean isShow = BuildConfig.DEBUG;
    private static String TAG = "MVPM";


    public static void e(String msg){
        Log.e(TAG, msg);
    }

    public static void i(String msg){
        if(isShow){
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg){
        if(isShow){
            Log.d(TAG, msg);
        }
    }

    public static void w(String msg){
        if(isShow){
            Log.w(TAG, msg);
        }
    }

    public static void v(String msg){
        if(isShow){
            Log.v(TAG, msg);
        }
    }

    public static void e(String tag, String msg){
        Log.e(tag, msg);
    }

    public static void i(String tag, String msg){
        if(isShow){
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if(isShow){
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg){
        if(isShow){
            Log.w(tag, msg);
        }
    }

    public static void v(String tag, String msg){
        if(isShow){
            Log.v(tag, msg);
        }
    }

}
