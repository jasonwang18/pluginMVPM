package com.pluginmvpm.sample.presenter;

import com.pluginmvpm.annotation.InstanceFactory;
import com.pluginmvpm.annotation.MessageWhat;
import com.pluginmvpm.sample.Constant;
import com.pluginmvpm.sample.base.BaseLog;
import com.pluginmvpm.sample.base.api.IMethodCenter;
import com.pluginmvpm.sample.base.presenter.BasePresenter;
import com.pluginmvpm.sample.contract.TestContract1;
import com.pluginmvpm.sample.model.TestModel1;

import java.util.List;

/**
 * Created by wangshizhan on 17/4/7.
 */

@InstanceFactory
public class TestPresenter extends BasePresenter<TestModel1> implements TestContract1.ITestPresenter1{

    public TestPresenter() {
        super();
    }

    @Override
    protected boolean onHandleResult(Object result) {
        return false;
    }

    @MessageWhat(Constant.MessageWhat.MESSAGE_WHAT_PRESENTER_ASYN_METHOD)
    @Override
    public List<String> aSynPresenterMethod(String s) {

        BaseLog.i("aSynPresenterMethod s:"+s);

        return getModel().aSynModelMethod();

    }

    @Override
    public boolean synPresenterMethod() {

        BaseLog.i("synPresenterMethod");

        return getModel().synModelMethod();
    }

    @Override
    public boolean synPresenterMethod2(String s, int i, boolean b) {
        BaseLog.i("synPresenterMethod2 s:"+s);

        return false;
    }

    @Override
    public boolean synPresenterMethod3(String s, String s2, String s3) {
        BaseLog.i("synPresenterMethod2 s:"+s);

        return false;
    }
}
