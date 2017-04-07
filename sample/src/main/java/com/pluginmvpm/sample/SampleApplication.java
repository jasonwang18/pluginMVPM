package com.pluginmvpm.sample;

import android.app.Application;
import android.content.Context;

/**
 * Created by wangshizhan on 17/4/7.
 */

public class SampleApplication extends Application {

    private static Application context;


    @Override
    public void onCreate() {
        super.onCreate();

        context =this;

    }


    public static Context context(){
        return context;
    }
}
