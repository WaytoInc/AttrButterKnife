# 自定义属性的butterknife

学习butterknife的练手项目，只是为了让获取自定义属性更方便。

```
public class CustomView extends View {

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
    
  }
```

```
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

# 暂时支持的注解

* AttrBindString
* AttrBindBoolean
* AttrBindDimen
* AttrBindFloat
* AttrBindColor
* AttrBindInt

# License

Copyright 2016 Simplepeng

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
