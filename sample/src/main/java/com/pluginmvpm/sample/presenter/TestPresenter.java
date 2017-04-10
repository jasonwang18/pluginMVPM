package com.pluginmvpm.sample.presenter;

import com.pluginmvpm.base.BaseLog;
import com.pluginmvpm.base.annotation.MessageWhat;
import com.pluginmvpm.base.annotation.SynMethod;
import com.pluginmvpm.base.core.methodcenter.BaseMethodCenter;
import com.pluginmvpm.base.presenter.BasePresenter;
import com.pluginmvpm.sample.Constant;
import com.pluginmvpm.sample.contract.TestContract1;
import com.pluginmvpm.sample.model.TestModel1;

import java.util.List;

/**
 * Created by wangshizhan on 17/4/7.
 */

@SynMethod(value = {"synPresenterMethod"})
public class TestPresenter extends BasePresenter<TestModel1> implements TestContract1.ITestPresenter1{

    public TestPresenter(BaseMethodCenter methodCenter) {
        super(methodCenter);
    }

    @Override
    protected TestModel1 createModel() {
        return new TestModel1();
    }

    @Override
    protected boolean onHandleResult(Object result) {
        return false;
    }

    @MessageWhat(Constant.MessageWhat.MESSAGE_WHAT_PRESENTER_ASYN_METHOD)
    @Override
    public List<String> aSynPresenterMethod() {

        BaseLog.i("aSynPresenterMethod");

        return getModel().aSynModelMethod();

    }

    @Override
    public boolean synPresenterMethod() {

        BaseLog.i("synPresenterMethod");

        return getModel().synModelMethod();
    }
}
