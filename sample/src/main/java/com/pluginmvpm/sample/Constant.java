package com.pluginmvpm.sample;

/**
 * Created by wangshizhan on 17/4/7.
 */

public interface Constant {

    String PRESENTER_PATH = "com.pluginmvpm.sample.presenter";

    interface MessageWhat{

        int MESSAGE_WHAT_CALLBACK = 1118; //callback

        int MESSAGE_WHAT_PRESENTER_ASYN_METHOD = MESSAGE_WHAT_CALLBACK+1;
    }


    interface Method{

        String METHOD_TEST_PRESENTER_ASYN_METHOD = "aSynPresenterMethod";

        String METHOD_TEST_PRESENTER_SYN_METHOD = "synPresenterMethod";


    }


}
