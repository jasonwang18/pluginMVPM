package com.pluginmvpm.base.core.methodcenter;

import android.content.Context;

import com.pluginmvpm.base.BaseLog;
import com.pluginmvpm.base.core.SynResult;

/**
 * Created by wangshizhan on 17/4/7.
 */

public class MethodCenter extends BaseMethodCenter {

    private static String presenterPath = "";

    public static void setPresenterPath(String path) {
        presenterPath = path;
    }

    public static String getPresenterPath() {
        return presenterPath;
    }


    public MethodCenter(Context context){
        super(context);
    }

    /**
     * 调用异步方法
     * @param method
     * @param arg
     */
    public void callASynMethod(String method, String[] arg){

        super.callASynMethod(method, arg);

    }


    /**
     * 调用同步方法
     * @param method 方法名
     * @param arg 可变参数
     * @return bool
     */
    public  boolean callSynMethodBoolean(String method, String[] arg){

        BaseLog.i("callSynMethodBoolean");

        SynResult<Boolean> result = (SynResult<Boolean>)callSynMethod(method, arg);

        if(result == null ){
            return false;
        }
        return result.value();
    }

    /**
     * 调用同步方法
     * @param method
     * @param arg
     * @return int
     */
    public int callSynMethodInt(String method, String[] arg){

        SynResult<Integer> result = (SynResult<Integer>) callSynMethod(method, arg);

        if(result == null){
            return -1;
        }
        return result.value();
    }

    /**
     * 调用同步方法
     * @param method
     * @param arg
     * @return String
     */
    public  String callSynMethodString(String method, String[] arg){

        SynResult<String> result = (SynResult<String>) callSynMethod(method, arg);

        if(result == null){
            return "";
        }
        return result.value();
    }


}
