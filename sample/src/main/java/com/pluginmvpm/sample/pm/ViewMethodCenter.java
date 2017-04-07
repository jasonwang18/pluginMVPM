package com.pluginmvpm.sample.pm;

import com.pluginmvpm.base.BaseLog;
import com.pluginmvpm.base.annotation.Presenter;
import com.pluginmvpm.base.core.SynResult;
import com.pluginmvpm.base.core.methodcenter.MethodCenter;
import com.pluginmvpm.sample.SampleApplication;

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

@Presenter(value = {"TestPresenter"})
public class ViewMethodCenter extends MethodCenter {

    private static ViewMethodCenter instance ;

    public ViewMethodCenter(){
        super(SampleApplication.context());
    }

    public static ViewMethodCenter getInstance(){
        if (instance == null) {
            synchronized (ViewMethodCenter.class){
                if (instance == null) {
                    instance = new ViewMethodCenter() ;
                }
            }
        }
        return instance ;
    }

}
