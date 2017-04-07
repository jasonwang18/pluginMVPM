package com.pluginmvpm.base.api;

import android.os.Looper;

import com.pluginmvpm.base.presenter.BasePresenter;

import java.util.Map;

/**
 * Created by wangshizhan on 17/3/26.
 */

public interface IMethodCenter {

    void register(BasePresenter basePresenter);

    void registerMethod(Map<String, Integer> map,  int id);

    Looper getLooper();


}
