package com.pluginmvpm.base.util;

import com.pluginmvpm.annotation.TimeLog;

/**
 * Created by baixiaokang on 16/4/30.
 */
public class InstanceUtil {

    /**
     * 通过实例工厂去实例化相应类
     *
     * @param <T> 返回实例的泛型类型
     * @return
     */
    @TimeLog
    public static <T> T getInstance(Class clazz) {
        try {
            return (T) InstanceFactory.create(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
