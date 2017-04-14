package com.pluginmvpm.base.core;

import com.pluginmvpm.base.BaseLog;
import com.pluginmvpm.base.annotation.Instance;
import com.pluginmvpm.base.annotation.MessageWhat;
import com.pluginmvpm.base.annotation.Presenter;
import com.pluginmvpm.base.annotation.SynMethod;
import com.pluginmvpm.base.core.methodcenter.BaseMethodCenter;
import com.pluginmvpm.base.core.methodcenter.MethodCenter;
import com.pluginmvpm.base.presenter.BasePresenter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by wangshizhan on 17/3/27.
 *
 * MethodHelper is designed for BaseMethodCenter, it works as a constructer for presenters ,
 *
 * it also provide a way to call synchonize functions for those presenters
 *
 */

public class MethodHelper {

    /**
     *  create instance for controllers by annotation "Instance"
     *
     *  see @Instance
     * @param clazz
     */
    public static Map<String, BasePresenter> createPresenters(BaseMethodCenter methodCenter, Class<?> clazz){
        Map<String, BasePresenter> map = new HashMap<>();

        try {

            Annotation[] classAnnotations = clazz.getAnnotations();

            for(Annotation annotation : classAnnotations){

                if(annotation instanceof Presenter){
                    String[] classes = ((Presenter) annotation).value();

                    for(String className:classes){

                        Class controllerClazz = Class.forName(MethodCenter.getPresenterPath()+"."+className);

                        Constructor<BasePresenter> constructor = controllerClazz.getConstructor(BaseMethodCenter.class);

                        BasePresenter controller = constructor.newInstance(methodCenter);

                        map.put(className, controller);

                        BaseLog.i(""+className+" added");
                    }

                }
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            BaseLog.e("createPresenters NoSuchMethodException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            BaseLog.e("createPresenters ClassNotFoundException");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            BaseLog.e("createPresenters InvocationTargetException");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            BaseLog.e("createPresenters IllegalAccessException");
        } catch (InstantiationException e) {
            e.printStackTrace();
            BaseLog.e("createPresenters InstantiationException");

        }

        return map;
    }
    /**
     *  create singlton instance for presenters by annotation "Instance"
     *
     *  see @Instance
     * @param clazz
     */

    public static Map<String, BasePresenter> createInstances(BaseMethodCenter methodCenter, Class<?> clazz){
        Map<String, BasePresenter> map = new HashMap<>();

        try {

            Annotation[] classAnnotations = clazz.getAnnotations();

            for(Annotation annotation : classAnnotations){

                if(annotation instanceof Instance){
                    String[] classes = ((Instance) annotation).value();

                    for(String className:classes){

                        Class presenterClazz = Class.forName(MethodCenter.getPresenterPath()+"."+className);

                        Method getInstance = presenterClazz.getDeclaredMethod("getInstance", BaseMethodCenter.class);

                        BasePresenter presenter = (BasePresenter) getInstance.invoke(presenterClazz, methodCenter);

                        map.put(className, presenter);
                    }

                }
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            BaseLog.e("createInstances NoSuchMethodException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            BaseLog.e("createInstances ClassNotFoundException");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            BaseLog.e("createInstances InvocationTargetException");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            BaseLog.e("createInstances IllegalAccessException");
        }

        return map;
    }


    /**
     * call synchonize method function to controller that who annotated with "@SynMethod"
     *
     * see @SynMethod
     */
    public static SynResult<?> callSynMethod(Method method, BasePresenter presenter, Object[] arg){

        SynResult result = null;


        try {
            result = new SynResult<>(method.invoke(presenter, arg));
            BaseLog.i(""+result.value());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            BaseLog.e("callSynMethod IllegalAccessException");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            BaseLog.e("callSynMethod InvocationTargetException");

        }

        if(result == null){
            return null;
        }

        return result;

    }

    /**
     * call synchonize function to controllers those who annotated with "@SynMethod"
     *
     * @param arg
     * @param presenterMap
     * @param methodName
     *
     * see @SynMethod
     */
    public static SynResult<?> callSynMethod(Map<String, BasePresenter> presenterMap, String methodName, Object... arg){

        BasePresenter presenter = null;
        Method method = null;

        for (String key : presenterMap.keySet()) {
            presenter = presenterMap.get(key);
/*
            Class clazz = presenterMap.get(key).getClass();
            Method[] methods = clazz.getDeclaredMethods();
            BaseLog.i("methods:"+methods.length);
            for(Method m: methods){
                BaseLog.i("method:"+m.getName());
                if(m.getName().equals(methodName)){
                    method = m;
                    presenter = presenterMap.get(key);
                    break outer;
                }
            }
*/

            try {
                if(arg == null) {

                    method = presenter.getClass().getDeclaredMethod(methodName);
                }
                else{

                    method = presenter.getClass().getDeclaredMethod(methodName, getClasses(arg));

                }

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            if(method != null){
                break ;
            }

        }


        if(method == null){
            BaseLog.e("cannot find the presenter");

            return null;

        }

        return  callSynMethod(method, presenter, arg);


    }

    private static Class<?>[] getClasses(Object... arg){

        int length = arg.length;
        Class<?>[] classes = new Class<?>[length];

        for(int i =0; i < length; i++){
            classes[i] = arg[i].getClass();
        }

        return classes;

    }


    /**
     * call asynchonize function to presenter that who annotated with "@MessageWhat"
     *
     * @param presenter
     *
     * see @MessageWhat
     */
    public static Map<String, Integer> initASynMethod(BasePresenter presenter){

        Map<String, Integer> methodMap = new HashMap<>();

        Class<? extends BasePresenter> clazz = presenter.getClass();

        Method[] publicMethods = clazz.getMethods();

        for(Method publicMethod: publicMethods){

            Annotation[] methodAnnotations = publicMethod.getAnnotations();

            for(Annotation annotation: methodAnnotations){

                if(annotation instanceof MessageWhat){

                    methodMap.put(publicMethod.getName(), ((MessageWhat) annotation).value());

                }
            }

        }


       return methodMap;


    }

    /**
     * call asynchonize function to presenter
     *
     * @param arg
     * @param presenter
     * @param methodName
     *
     * see @SynMethod
     */
    public static Object callASynMethod(BasePresenter presenter, String methodName, Object[] arg){


       Method[] methods = presenter.getClass().getDeclaredMethods();

        for(Method method : methods){
            if(method.getName().equals(methodName)){
                try {
                    return method.invoke(presenter, arg);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;

    }


}
