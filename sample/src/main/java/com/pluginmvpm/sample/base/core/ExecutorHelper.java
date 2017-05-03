package com.pluginmvpm.sample.base.core;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by wangshizhan on 17/4/11.
 */

public class ExecutorHelper {

    private static ExecutorHelper instance ;

    private ScheduledThreadPoolExecutor executor;

    private ExecutorHelper(){
        executor = new ScheduledThreadPoolExecutor(20);
    }

    public static ExecutorHelper getInstance(){
        if (instance == null) {
            synchronized (ExecutorHelper.class){
                if (instance == null) {
                    instance = new ExecutorHelper() ;
                }
            }
        }
        return instance ;
    }


    public void execute(Runnable runnable){
        executor.execute(runnable);
    }


}
