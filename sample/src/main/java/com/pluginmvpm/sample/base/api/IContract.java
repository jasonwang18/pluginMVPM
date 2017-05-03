package com.pluginmvpm.sample.base.api;

/**
 * Created by wangshizhan on 17/3/17.
 */

public interface IContract {


    interface IPresenter {

        //Presenter方法由继承接口分别设置

    }


    interface IModel {


        //Model其他方法由继承接口分别设置

        void init();


        void release();
    }

}
