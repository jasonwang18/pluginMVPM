package com.pluginmvpm.sample.model;

import com.pluginmvpm.base.BaseLog;
import com.pluginmvpm.sample.contract.TestContract1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshizhan on 17/4/7.
 */

public class TestModel1 implements TestContract1.ITestModel1 {

    @Override
    public List<String> aSynModelMethod() {
        BaseLog.i("aSynModelMethod");

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");

        return list;
    }

    @Override
    public boolean synModelMethod() {
        BaseLog.i("synModelMethod");
        return true;
    }

    @Override
    public void init() {

    }

    @Override
    public void release() {

    }
}
