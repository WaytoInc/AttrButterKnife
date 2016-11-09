package com.example;

/**
 * Created by simple on 16/11/9.
 */

public class BindingSet {

    public String className;
    public String pageName;
    public String annotationName;

    public BindingSet(String className, String pageName) {
        this.className = className;
        this.pageName = pageName;
    }

    public static BindingSet create(String className, String pageName) {
        return new BindingSet(className, pageName);
    }
}
