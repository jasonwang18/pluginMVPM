package com.pluginmvpm.sample.pm;

import com.pluginmvpm.annotation.Presenter;
import com.pluginmvpm.sample.SampleApplication;
import com.pluginmvpm.sample.base.core.methodcenter.MethodCenter;
import com.pluginmvpm.sample.presenter.TestPresenter;

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

@Presenter(value = {TestPresenter.class})
public class ViewMethodCenter extends MethodCenter {


    public ViewMethodCenter(){
        super(SampleApplication.context());
    }



}
