package com.pluginmvpm.sample.base.core.methodcenter;

import android.content.Context;

import com.pluginmvpm.sample.base.BaseLog;
import com.pluginmvpm.sample.base.api.IMethodCenter;
import com.pluginmvpm.sample.base.core.SynResult;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangshizhan on 17/4/7.
 */

public class MethodCenter extends BaseMethodCenter {

    private static final String INSTANCE = "instance";

    private static Map<String, IMethodCenter> map = new ConcurrentHashMap<>();

    protected MethodCenter(Context context) {
        super(context);

        setInstance();
        initPresenter();

    }

    private void setInstance(){
        if(map.get(INSTANCE) != null){
            BaseLog.d("-->name对应的值已存在");
        }
        else {
            map.put(INSTANCE, this);
        }
    }

    public static IMethodCenter getInstance() {

        if (map.get(INSTANCE) == null) {
            BaseLog.d("-->name对应的值不存在");

        }else {
            BaseLog.d("-->name对应的值存在");
        }

        BaseLog.d("class:"+map.get(INSTANCE).getClass().getName());

        return map.get(INSTANCE);
    }

    /**
     * 调用同步方法
     * @param method 方法名
     * @param arg 可变参数
     */
    public void callMethodVoid(String method, Object... arg){

        if(isASynMethod(method)){
            callASynMethod(method, arg);
        }
        else{
            callSynMethodVoid(method, arg);
        }

    }

    /**
     * 调用同步方法
     * @param method 方法名
     * @param arg 可变参数
     */
    protected void callSynMethodVoid(String method, Object... arg){

        BaseLog.i("callSynMethodVoid");

        SynResult<String> result = (SynResult<String>) callSynMethod(method, arg);

        if("null".equals(result.value())){
            //success
        }
        else if("error".equals(result.value())){
            BaseLog.e("callSynMethodVoid  error!");
        }

    }


    /**
     * 调用同步方法
     * @param method 方法名
     * @param arg 可变参数
     * @return bool
     */
    public  boolean callMethodBoolean(String method, Object... arg){

        BaseLog.i("callSynMethodBoolean");

        SynResult<Boolean> result = (SynResult<Boolean>)callSynMethod(method, arg);

        if(result == null || "error".equals(result.value())){
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
    public int callMethodInt(String method, Object... arg){

        SynResult<Integer> result = (SynResult<Integer>) callSynMethod(method, arg);

        if(result == null || "error".equals(result.value())){
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
    public  String callMethodString(String method, Object... arg){

        SynResult<String> result = (SynResult<String>) callSynMethod(method, arg);

        if(result == null || "error".equals(result.value())){
            return "";
        }
        return result.value();
    }

}
