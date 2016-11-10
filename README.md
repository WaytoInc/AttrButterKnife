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
```
