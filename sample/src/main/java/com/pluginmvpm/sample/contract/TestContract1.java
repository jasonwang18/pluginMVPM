package com.pluginmvpm.sample.contract;

import com.pluginmvpm.base.api.IContract;

import java.util.List;

/**
 * Created by wangshizhan on 17/4/7.
 */

public interface TestContract1 {

    interface ITestPresenter1 extends IContract.IPresenter{

        List<String> aSynPresenterMethod(String s);
        boolean synPresenterMethod();

        boolean synPresenterMethod2(String s, int i, boolean b);

        boolean synPresenterMethod3(String s, String s2, String s3);
    }


    interface ITestModel1 extends IContract.IModel{

        List<String> aSynModelMethod();
        boolean synModelMethod();
    }


}
