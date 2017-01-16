# 自定义属性的butterknife

学习butterknife然后自己写一个获取自定义属性的项目，让获取自定义属性更快捷方便，一个注解就搞定。

# 使用示例

```java
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
```

```xml
<com.simple.attrbutterknife.CustomView
        android:layout_width="match_parent"
        android:layout_height="100dp"
        app:test_boolean="false"
        app:test_color="@android:color/holo_blue_bright"
        app:test_dimension="@dimen/activity_horizontal_margin"
        app:test_float="22"
        app:test_integer="13"
        app:test_string="测试string"
        app:test_string2="测试string2" />
```

# 输出结果
```
E/CustomView: testString==测试string
E/CustomView: testString2==测试string2
E/CustomView: testBoolean==false
E/CustomView: testDimension==32.0
E/CustomView: testColor==-16720385
E/CustomView: testFloat==22.0
E/CustomView: testInteger==13
```

# 暂时支持的注解

* AttrString
* AttrBool
* AttrDimen
* AttrFloat
* AttrColor
* AttrInt

# 如何使用

* app build.gradle
```
...
dependencies {
     compile 'com.simplepeng:attrbutter_library:1.1.0'
     annotationProcessor 'com.simplepeng:attrbutter_compiler:1.1.0'
}
```

* 源码用java7编译，可能你还需要
```
// 设置java 版本
    compileOptions {
      //>= jdk7
        sourceCompatibility JavaVersion.VERSION_1_7
        targetCompatibility JavaVersion.VERSION_1_7
    }
```


# License

Copyright 2016 Simple Peng

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
