package com.pluginmvpm.sample.base.core;

/**
 * Created by wangshizhan on 17/3/27.
 */

public class SynResult<T> {

    T result;

    public T value(){

        return result;

    }


    public SynResult(T t){
        this.result = t;
    }


}
