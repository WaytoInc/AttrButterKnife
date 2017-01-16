package com.simple.attrbutterknife;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.simple.AttrBool;
import com.simple.AttrColor;
import com.simple.AttrDimen;
import com.simple.AttrFloat;
import com.simple.AttrInt;
import com.simple.AttrString;
import com.simple.attrbutter_library.AttrButterKnife;

/**
 * Created by simple on 16/11/7.
 */

public class CustomView extends View {

    @AttrString(R.styleable.CustomView_test_string)
    String testString;
    @AttrString(R.styleable.CustomView_test_string2)
    String testString2;
    @AttrBool(id = R.styleable.CustomView_test_boolean, defValue = true)
    boolean testBoolean;
    @AttrDimen(id = R.styleable.CustomView_test_dimension, defValue = 19.0f)
    float testDimension;
    @AttrColor(id = R.styleable.CustomView_test_color, defValue = Color.BLACK)
    int testColor;
    @AttrFloat(id = R.styleable.CustomView_test_float, defValue = 2.3f)
    float testFloat;
    @AttrInt(id = R.styleable.CustomView_test_integer, defValue = 1)
    int testInteger;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
        AttrButterKnife.bind(this, ta);
        Log.e("CustomView", "testString==" + testString);
        Log.e("CustomView", "testString2==" + testString2);
        Log.e("CustomView", "testBoolean==" + testBoolean);
        Log.e("CustomView", "testDimension==" + testDimension);
        Log.e("CustomView", "testColor==" + testColor);
        Log.e("CustomView", "testFloat==" + testFloat);
        Log.e("CustomView", "testInteger==" + testInteger);
    }

//            try {
//        testString = ta.getString(R.styleable.CustomView_test_string);
//        testString2 = ta.getString(R.styleable.CustomView_test_string2);
//        testBoolean = ta.getBoolean(R.styleable.CustomView_test_boolean, false);
//        testDimension = ta.getDimension(R.styleable.CustomView_test_dimension, 19);
//        testColor = ta.getColor(R.styleable.CustomView_test_color, Color.BLACK);
//        testFloat = ta.getFloat(R.styleable.CustomView_test_float, 10);
//        testInteger = ta.getInteger(R.styleable.CustomView_test_integer, 10);
//        }finally {
//            ta.recycle();
//        }


    }
