package com.simple.attrbutterknife;

import java.lang.reflect.Method;

/**
 * Created by simple on 16/11/1.
 */

public final class AttrButterKnify {

//    public static void bind(Object view) {
//        try {
//            String cla = view.getClass().getName() + "$ViewBinder";
//            Class clazz = Class.forName(cla);
//            Object instance = clazz.newInstance();
//            Method bind = clazz.getMethod("bind", Object.class);
//            bind.invoke(instance, view);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void bind(Object target, Object attrs) {
        try {
            String cla = target.getClass().getName() + "$ViewBinder";
            Class clazz = Class.forName(cla);
            Object instance = clazz.newInstance();
            Method bind = clazz.getMethod("bind", Object.class, Object.class);
            bind.invoke(instance, target,attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
