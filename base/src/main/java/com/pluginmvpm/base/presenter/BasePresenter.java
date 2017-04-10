package com.pluginmvpm.base.presenter;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.pluginmvpm.base.BaseConstant;
import com.pluginmvpm.base.BaseLog;
import com.pluginmvpm.base.api.IContract;
import com.pluginmvpm.base.api.IMethodCenter;
import com.pluginmvpm.base.core.AsyncChannel;
import com.pluginmvpm.base.core.MethodHelper;
import com.pluginmvpm.base.core.methodcenter.BaseMethodCenter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangshizhan on 17/3/17.
 */

public abstract class BasePresenter<Model extends IContract.IModel> implements IContract.IPresenter {

    protected Model model;
    private Handler mHandler;
    private AsyncChannel mChannel;
    protected Context mContext;

    protected IMethodCenter mMethodCenter;

    protected Map<String, Integer> mASynMethodMap;

    public BasePresenter(BaseMethodCenter methodCenter){

        mMethodCenter = methodCenter;
        mContext = methodCenter.context();
        model = createModel();

        init();

    }

    public void setChannel(AsyncChannel channel) {
        this.mChannel = channel;
    }

    public AsyncChannel getChannel() {
        return mChannel;
    }

    public void setModel(Model model){
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    protected void init(){
        mHandler = createHandler(mMethodCenter.getLooper());
        mMethodCenter.register(this);

        initASynMethod();
        registerASynMethod();
    }

    private void registerASynMethod() {

        mMethodCenter.registerMethod(mASynMethodMap, getId());

    }

    protected void initASynMethod(){
        mASynMethodMap = MethodHelper.initASynMethod(this);
    }

    protected abstract Model createModel();

    protected Handler createHandler(Looper looper){
        return new ControllerHandler(looper){

            @Override
            public void handleMessage(Message msg) {

//                BaseLog.i("handleMessage");
//                super.handleMessage(msg);

                for(String key:mASynMethodMap.keySet()){

                    if (mASynMethodMap.get(key) == msg.what) {

                        Object result = MethodHelper.callASynMethod(BasePresenter.this, key, (Object[]) msg.obj);

                        if(result != null){// have return
                            returnMethodResult(msg, result, onHandleResult(result));
                        }
                    }
                }

//                handleMsg(msg);
            }
        };
    }

    protected abstract boolean onHandleResult(Object result);

    protected void returnMethodResult(Message msg, Object result, boolean isHandled){

        if (!isHandled) {

            getChannel().replyToMessage(
                    msg,
                    BaseConstant.MESSAGE_WHAT_CALLBACK,
                    msg.what,
                    0,
                    result);
        }

    }


    public Handler getHandler() {
        return mHandler;
    }

    public class ControllerHandler extends Handler{

        public ControllerHandler(Looper looper){
            super(looper);
        }

    }

    public int getId(){

        return hashCode();

    }



}
