package com.simple;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by simple on 16/11/8.
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface AttrInt {
    int id();

    int defValue();
}
