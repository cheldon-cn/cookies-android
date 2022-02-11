package com.chn.cookies.flexbox.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.chn.cookies.R;
import com.chn.cookies.databinding.FragmentMineBinding;
import com.chn.cookies.flexbox.utils.ToastUtil;
import com.chn.cookies.ui.MineFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class SettingActivity extends BaseActivity {


    @Override
    protected void initView() {

    }
    @Override
    protected void bindEvent() {

    }
    @Override
    protected void initData() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
    }

    public void onViewClicked(View view)
    {
        switch (view.getId()) {
            case R.id.item_us:
            {
                MineFragment fragment = MineFragment.newInstance();


               // fragment.onCreate(true);
//                finish();
            }
                break;
            case R.id.item_check_update:
                break;
            case R.id.item_entrench:
                break;
            case R.id.btn_logout:
                finish();
                break;
        }
    }
}
