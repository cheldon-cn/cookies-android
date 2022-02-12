package com.chn.cookies.flexbox.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.chn.cookies.AppManager;
import com.chn.cookies.MainActivity;
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


    private int currentIndex = 0;
    private Fragment currentFragment = null;

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
//                MineFragment fragment = MineFragment.newInstance();

                // 由于 activity不能直接跳转到 fragment，需要先去跳转到 MainActivity 中，
                // 根据在 MainActivity 中显示的方法去显示需要的fragment即可
//                Intent intent=new Intent(SettingActivity.this, SettingActivity.class);
//                intent.putExtra("juanFlag","1");    // 1表示领券成功后 需要跳转到劵包fragment
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_right_in,
//                        R.anim.slide_left_out);
//
//                AppManager.getAppManager().finishAllActivity();
//
//                showFragment();


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

//    @Override
//    public void initView() {
//
//        // 这个 waitPayFlag 就是从 第一个AActivity中跳转过来传递过来的参数
//        // 从购物车页面，如果提交的店铺个数是多个，点击叉号跳转 我的 - 待付款fragment列表
//        // 取出从PayActivity中购物车的标识waitPayFlag
//        String waitPayFlag = getIntent().getStringExtra("waitPayFlag") ;
//
//        indicator = (TabPageIndicator)findViewById(R.id.indicator);
//        viewPager = (ViewPager)findViewById(R.id.viewPager);
//        BasePagerAdapter adapter = new     BasePagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(adapter);
//        indicator.setViewPager(viewPager);
//        setTabPagerIndicator();
//
//        // TODO:  这里一定要注意：获取waitPayFlag千万不要写到 onResume()中，就写到 initView()中就可以，
//        // 我一开始就写到 onResume()中，然后设置viewPager.setCurrentItem(1)，界面可以出来，但是就是
//        // 不能加载数据
//
//        // 第一：获取waitPayFlag一定要在initView()方法中，不要写到onResume()中；
//        // 第二：下边设置viewPager.setCurrentItem(1)这句代码一定要写到 上边viewPager的 findViewById的下边，
//        // 否则跳转到的目标fragment显示不出来
//
//
//        // 从购物车页面，如果提交的店铺个数是多个，点击叉号跳转 我的 - 待付款fragment列表
//        // 1表示：用于从PayActivity中点击叉号跳转待付款的fragment
//        if(!TextUtils.isEmpty(waitPayFlag)){
//            if ("1".equals(waitPayFlag)) {
//                // 这里设置要跳转到第几个fragment
//                viewPager.setCurrentItem(1);
//            }
//        }
//    }

    /**
     * 在onNewIntent方法中进行的判断，然后显示MainActivity中对应的fragment即可，
     * 只要执行这个方法就不会执行onCreate()方法，
     *
     * 执行顺序如下：
     *      onNewIntent()---->onResart()------>onStart()----->onResume()生命周期就发生了更改
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String juanFlag = intent.getStringExtra("juanFlag");
        if (!TextUtils.isEmpty(juanFlag)){
            if ("1".equals(juanFlag)) {
                goFragmentIndex();
            }
        }
    }

    /**
     * 用于接收从商品详情 跳转到购物车fragment的id值
     */
    private void goFragmentIndex() {

        // 这里直接给currentIndex角标设置数字，然后调用showFragment()及对应图标和文字切换，
        // 就可以正常从任意界面切换到MainActivity中所在的fragment
        currentIndex = 0;
        showFragment();
    }

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment(){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(currentFragment != null)
            transaction.hide(currentFragment);
        else
            currentFragment = MineFragment.newInstance();

        transaction.add(R.id.nav_host_fragment_content_main,currentFragment,""+currentIndex);
        transaction.show(currentFragment);

//        //如果之前没有添加过
//        if(!fragments.get(currentIndex).isAdded()){
//
//            // 第三个参数为添加当前的fragment时绑定一个tag
//            transaction
//                    .hide(currentFragment)
//                    .add(R.id.layout_main_content,fragments.get(currentIndex),""+currentIndex);
//
//        }else{
//            transaction
//                    .hide(currentFragment)
//                    .show(fragments.get(currentIndex));
//        }
//
//        currentFragment = fragments.get(currentIndex);
        transaction.commit();

    }


}
