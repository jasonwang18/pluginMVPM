package com.pluginmvpm.sample.presenter;

import android.os.Message;

import com.pluginmvpm.base.BaseLog;
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
    protected void initASynMethod() {
        mASynMethodMap.put(Constant.Method.METHOD_TEST_PRESENTER_ASYN_METHOD,
                Constant.MessageWhat.MESSAGE_WHAT_PRESENTER_ASYN_METHOD);

    }

    @Override
    protected TestModel1 createModel() {
        return new TestModel1();
    }

    @Override
    protected void handleMsg(Message msg) {

        switch (msg.what){

            case Constant.MessageWhat.MESSAGE_WHAT_PRESENTER_ASYN_METHOD:

                //do something
                    List<String> result = aSynPresenterMethod();

                getChannel().replyToMessage(
                        msg,
                        Constant.MessageWhat.MESSAGE_WHAT_CALLBACK,
                        Constant.MessageWhat.MESSAGE_WHAT_PRESENTER_ASYN_METHOD,
                        0,
                        result);

                break;


        }

    }

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
