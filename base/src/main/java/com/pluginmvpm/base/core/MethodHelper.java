package com.pluginmvpm.base.core;

import com.pluginmvpm.base.BaseLog;
import com.pluginmvpm.base.annotation.Instance;
import com.pluginmvpm.base.annotation.MessageWhat;
import com.pluginmvpm.base.annotation.Presenter;
import com.pluginmvpm.base.core.methodcenter.BaseMethodCenter;
import com.pluginmvpm.base.core.methodcenter.MethodCenter;
import com.pluginmvpm.base.presenter.BasePresenter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
     *  create instance for presneters by annotation "Presenter"
     *
     *  see @Instance
     * @param clazz
     */
    public static Map<String, BasePresenter> createPresenters(BaseMethodCenter methodCenter, Class<?> clazz) {
        Map<String, BasePresenter> map = new HashMap<>();

        try {

            Annotation[] classAnnotations = clazz.getAnnotations();

            for(Annotation annotation : classAnnotations){

                if(annotation instanceof Presenter){
                    String[] classes = ((Presenter) annotation).value();

                    for(String className:classes){

                        Class presenterClass = Class.forName(MethodCenter.getPresenterPath()+"."+className);
                        Constructor<BasePresenter> constructor = presenterClass.getConstructor(BaseMethodCenter.class);
                        constructor.setAccessible(true);
                        BasePresenter presenter = constructor.newInstance(methodCenter);
                        map.put(className, presenter);

                        BaseLog.d(""+className+" added");
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
     *  create instance for presenters by annotation "Instance"
     *
     *  see @Instance
     * @param clazz
     */
    public static Map<String, BasePresenter> createInstances(BaseMethodCenter methodCenter, Class<?> clazz) {
        Map<String, BasePresenter> map = new HashMap<>();

        try {

            Annotation[] classAnnotations = clazz.getAnnotations();

            for(Annotation annotation : classAnnotations){

                if(annotation instanceof Instance){
                    String[] classes = ((Instance) annotation).value();

                    for(String className:classes){

                        Class presenterClass = Class.forName(MethodCenter.getPresenterPath()+"."+className);
                        Constructor<BasePresenter> constructor = presenterClass.getConstructor(BaseMethodCenter.class);
                        constructor.setAccessible(true);
                        BasePresenter presenter = constructor.newInstance(methodCenter);
                        map.put(className, presenter);

                        BaseLog.d(""+className+" added");
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
        } catch (InstantiationException e) {
            e.printStackTrace();
            BaseLog.e("createInstances InstantiationException");
        }

        return map;
    }


    /**
     * call synchonize function to presenters
     */
    public static SynResult<?> callSynMethod(Method method, BasePresenter presenter,  Object[] arg){

        SynResult result = null;

        try {
            result = new SynResult<>(method.invoke(presenter, arg));
            BaseLog.i("callSynMethod result:"+result.value());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            BaseLog.e("callSynMethod IllegalAccessException");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            BaseLog.e("callSynMethod InvocationTargetException");

        }

        if(result == null){
            return new SynResult<>("error");
        }

        if(result.value() == null){
            return new SynResult<>("null");
        }

        return result;

    }

    /**
     * call synchonize function
     *
     * @param arg
     * @param methodName
     *
     */
    public static SynResult<?> callSynMethod(BasePresenter presenter, String methodName, Object[] arg){

        Method method = null;


//            BaseLog.i("loop");

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


        if(method == null){
            BaseLog.e("未找到对应presenter");

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
    public static Map<String, Integer>[] initAllMethod(BasePresenter presenter){

        Map<String, Integer>[] maps = new Map[2];
        Map<String, Integer> asynMethodMap = new HashMap<>();
        Map<String, Integer> synMethodMap = new HashMap<>();

        Class<? extends BasePresenter> clazz = presenter.getClass();

        Method[] publicMethods = clazz.getMethods();

        for(Method publicMethod: publicMethods){

            Annotation[] methodAnnotations = publicMethod.getAnnotations();

            for(Annotation annotation: methodAnnotations){

                if(annotation instanceof MessageWhat){

                    asynMethodMap.put(publicMethod.getName(), ((MessageWhat) annotation).value());
                    continue;

                }
            }

            synMethodMap.put(publicMethod.getName(), 0);

        }

        maps[0] = asynMethodMap;
        maps[1] = synMethodMap;

        return maps;


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

//        BaseLog.i("callASynMethod method:"+methodName);

        Method method = null;

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
        if(method == null){
            return null ;
        }
       /* Method[] methods = presenter.getClass().getDeclaredMethods();

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
        }*/

        try {
            return method.invoke(presenter, arg);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }

    }

}
