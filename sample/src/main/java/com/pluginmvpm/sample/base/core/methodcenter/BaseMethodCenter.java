package com.pluginmvpm.sample.base.core.methodcenter;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;

import com.pluginmvpm.annotation.Presenter;
import com.pluginmvpm.sample.base.BaseConstant;
import com.pluginmvpm.sample.base.BaseLog;
import com.pluginmvpm.sample.base.api.IMethodCenter;
import com.pluginmvpm.sample.base.core.AsyncChannel;
import com.pluginmvpm.sample.base.core.MethodHelper;
import com.pluginmvpm.sample.base.core.SynResult;
import com.pluginmvpm.sample.base.presenter.BasePresenter;
import com.pluginmvpm.sample.util.InstanceUtil;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangshizhan on 17/3/21.
 */

public class BaseMethodCenter implements IMethodCenter {

    private Context mContext;

    private Map<String, Integer> mASynMethodMap;//method what map
    private Map<String, Integer> mMethodChannelMap;// method channelId map

    private MethodHandler mHandler;
    private Looper mLooper;
    private SparseArray<AsyncChannel> mChannels;
    private Map<String, BasePresenter> mPresenterMap;

    private Callback callback;

    public Context context(){
        return mContext;
    }

    public BaseMethodCenter(Context context){

        mContext = context;
        init();
    }
    
    protected void init() {

        HandlerThread handlerThread = new HandlerThread("BaseMethodCenter");
        handlerThread.start();

        mLooper = handlerThread.getLooper();

        mHandler = new MethodHandler(mLooper);
        mASynMethodMap = new HashMap<>();
        mMethodChannelMap = new HashMap<>();

    }

    protected void initPresenter() {
        mPresenterMap = new HashMap<>();

        Annotation[] annotations = getClass().getAnnotations();
        for (Annotation annotation:annotations){
            if(annotation instanceof Presenter){
                Class[] presenters = ((Presenter) annotation).value();

                for(Class presenter : presenters){
                    InstanceUtil.getInstance(presenter);
                    BaseLog.d("presenter "+presenter.getName()+" added!");
                }

            }
        }

    }


    @Override
    public void register(BasePresenter basePresenter){

        if(mChannels == null){
            mChannels = new SparseArray<>();
        }

        mPresenterMap.put(basePresenter.getClass().getSimpleName(), basePresenter);

        if(mChannels.get(basePresenter.getId()) != null){

            BaseLog.e("This Controller has already registerd");
            return;
        }
        AsyncChannel channel = new AsyncChannel();
        channel.connect(mContext, mHandler, basePresenter.getHandler());
        basePresenter.setChannel(channel);
        mChannels.put(basePresenter.getId(), channel);
    }

    @Override
    public void registerMethod(Map<String, Integer> map, int id) {

        for (String key : map.keySet()) {
            BaseLog.i("key:"+key+" id:"+id);
            mMethodChannelMap.put(key, id);
        }
        mASynMethodMap.putAll(map);

    }

    @Override
    public Looper getLooper() {
        return mLooper;
    }



    protected class MethodHandler extends Handler{


        public MethodHandler(Looper looper){
            super(looper);
        }


        @Override
        public void handleMessage(Message msg) {


            if (msg.what == BaseConstant.MESSAGE_WHAT_CALLBACK) {

                onCallback(msg);

            }
        }
    }

    protected void callASynMethod(String method, Object... arg){

        BaseLog.i("callASynMethod");

        if(mASynMethodMap == null){
            return;
        }

        int what = mASynMethodMap.get(method);

        int id = mMethodChannelMap.get(method);

        if(arg != null){

            mChannels.get(id).sendMessage(what, arg);
        }
        else {
            mChannels.get(id).sendMessage(what);
        }

    }

    public boolean isASynMethod(String methodName){


        if(mASynMethodMap.containsKey(methodName) && mMethodChannelMap.containsKey(methodName)) {
            return true;
        }

        return false;

    }

    public SynResult<?> callSynMethod(String methodName, Object... arg){

        for(String key :mPresenterMap.keySet()){

            BasePresenter presenter = mPresenterMap.get(key);
            if(presenter.hasSynMethod(methodName)){
                return MethodHelper.callSynMethod(presenter, methodName, arg);
            }
            else{
                continue;
            }
        }


        return new SynResult<>("error");


    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface  Callback{

        void onCallback(Message msg);

    }


    protected void onCallback(Message msg) {
        if(callback != null){
            callback.onCallback(msg);
        }
    }
}
