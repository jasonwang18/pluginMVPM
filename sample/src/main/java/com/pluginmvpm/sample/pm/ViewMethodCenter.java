package com.pluginmvpm.sample.pm;

import com.pluginmvpm.base.BaseLog;
import com.pluginmvpm.base.annotation.Presenter;
import com.pluginmvpm.base.core.SynResult;
import com.pluginmvpm.base.core.methodcenter.MethodCenter;
import com.pluginmvpm.sample.SampleApplication;

/**
 * Created by wangshizhan on 17/4/7.
 *
 * use Annotation @Instance plugin presenters
 *
 * the asynMethod in each presenter need init with initASynMethod
 *
 * the synmethod need declared with Annotation @SynMethod
 *
 */

@Presenter(value = {"TestPresenter"})
public class ViewMethodCenter extends MethodCenter {

    private static ViewMethodCenter instance ;

    public ViewMethodCenter(){
        super(SampleApplication.context());
    }

    public static ViewMethodCenter getInstance(){
        if (instance == null) {
            synchronized (ViewMethodCenter.class){
                if (instance == null) {
                    instance = new ViewMethodCenter() ;
                }
            }
        }
        return instance ;
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

        BaseLog.i( "callSynMethodBoolean");

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
