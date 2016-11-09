package com.simple.attrbutterknife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//    @BindView(R.id.tv_test)
//    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        AttrButterKnify.bind(this);
//        textView.setTextColor(Color.RED);
    }
}
