package com.chn.cookies.flexbox.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by cheldon on 2021/10/9.
 */

public  abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        bindEvent();
        initData();
    }


    protected abstract  void initView();
    protected abstract void bindEvent();
    protected abstract void initData();
}
