package com.simple.attrbutterknife;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.AttrBindBoolean;
import com.example.AttrBindString;

/**
 * Created by simple on 16/11/7.
 */

public class CustomView2 extends View {

    @AttrBindString(R.styleable.CustomView_test_string)
    String testString;
//    @AttrBindString(R.styleable.CustomView_test_string2)
//    String testString2;
    @AttrBindBoolean(value = R.styleable.CustomView_test_boolean,default_value = true)
    boolean testBoolean;
//    float testDimension;
//    int testColor;
//    float testFloat;
//    int testInteger;

    public CustomView2(Context context) {
        this(context, null);
    }

    public CustomView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
//        try {
//            testString = ta.getString(R.styleable.CustomView_test_string);
//            testString2 = ta.getString(R.styleable.CustomView_test_string2);
//            testBoolean = ta.getBoolean(R.styleable.CustomView_test_boolean,false);
//            testDimension = ta.getDimension(R.styleable.CustomView_test_dimension, 19);
//            testColor = ta.getColor(R.styleable.CustomView_test_color, Color.BLACK);
//            testFloat = ta.getFloat(R.styleable.CustomView_test_float, 10);
//            testInteger = ta.getInteger(R.styleable.CustomView_test_integer, 10);
//        }finally {
//            ta.recycle();
//        }

        AttrButterKnify.bind(this, ta);
//        Log.e("CustomView","testString=="+testString);
//        Log.e("CustomView","testString2=="+testString2);
        Log.e("CustomView","testBoolean=="+testBoolean);
//        Log.e("CustomView","testDimension=="+testDimension);
//        Log.e("CustomView","testColor=="+testColor);
//        Log.e("CustomView","testFloat=="+testFloat);
//        Log.e("CustomView","testInteger=="+testInteger);
    }

}
