package com.simple.attrbutterknife;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.simple.AttrBindBoolean;
import com.simple.AttrBindColor;
import com.simple.AttrBindDimen;
import com.simple.AttrBindFloat;
import com.simple.AttrBindInt;
import com.simple.AttrBindString;
import com.simple.attrbutter_library.AttrButterKnife;

/**
 * Created by simple on 16/11/7.
 */

public class CustomView2 extends View {

    @AttrBindString(R.styleable.CustomView_test_string)
    String testString;
    @AttrBindString(R.styleable.CustomView_test_string2)
    String testString2;
    @AttrBindBoolean(id = R.styleable.CustomView_test_boolean, defValue = true)
    boolean testBoolean;
    @AttrBindDimen(id = R.styleable.CustomView_test_dimension, defValue = 19.0f)
    float testDimension;
    @AttrBindColor(id = R.styleable.CustomView_test_color, defValue = Color.BLACK)
    int testColor;
    @AttrBindFloat(id = R.styleable.CustomView_test_float, defValue = 2.3f)
    float testFloat;
    @AttrBindInt(id = R.styleable.CustomView_test_integer, defValue = 1)
    int testInteger;

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

        AttrButterKnife.bind(this, ta);
        Log.w("CustomView2","testString=="+testString);
        Log.w("CustomView2","testString2=="+testString2);
        Log.w("CustomView2","testBoolean=="+testBoolean);
        Log.w("CustomView2", "testDimension==" + testDimension);
        Log.w("CustomView2", "testColor==" + testColor);
        Log.w("CustomView2", "testFloat==" + testFloat);
        Log.w("CustomView2","testInteger=="+testInteger);
    }

}
