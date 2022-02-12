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

                // ���� activity����ֱ����ת�� fragment����Ҫ��ȥ��ת�� MainActivity �У�
                // ������ MainActivity ����ʾ�ķ���ȥ��ʾ��Ҫ��fragment����
//                Intent intent=new Intent(SettingActivity.this, SettingActivity.class);
//                intent.putExtra("juanFlag","1");    // 1��ʾ��ȯ�ɹ��� ��Ҫ��ת������fragment
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
//        // ��� waitPayFlag ���Ǵ� ��һ��AActivity����ת�������ݹ����Ĳ���
//        // �ӹ��ﳵҳ�棬����ύ�ĵ��̸����Ƕ������������ת �ҵ� - ������fragment�б�
//        // ȡ����PayActivity�й��ﳵ�ı�ʶwaitPayFlag
//        String waitPayFlag = getIntent().getStringExtra("waitPayFlag") ;
//
//        indicator = (TabPageIndicator)findViewById(R.id.indicator);
//        viewPager = (ViewPager)findViewById(R.id.viewPager);
//        BasePagerAdapter adapter = new     BasePagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(adapter);
//        indicator.setViewPager(viewPager);
//        setTabPagerIndicator();
//
//        // TODO:  ����һ��Ҫע�⣺��ȡwaitPayFlagǧ��Ҫд�� onResume()�У���д�� initView()�оͿ��ԣ�
//        // ��һ��ʼ��д�� onResume()�У�Ȼ������viewPager.setCurrentItem(1)��������Գ��������Ǿ���
//        // ���ܼ�������
//
//        // ��һ����ȡwaitPayFlagһ��Ҫ��initView()�����У���Ҫд��onResume()�У�
//        // �ڶ����±�����viewPager.setCurrentItem(1)������һ��Ҫд�� �ϱ�viewPager�� findViewById���±ߣ�
//        // ������ת����Ŀ��fragment��ʾ������
//
//
//        // �ӹ��ﳵҳ�棬����ύ�ĵ��̸����Ƕ������������ת �ҵ� - ������fragment�б�
//        // 1��ʾ�����ڴ�PayActivity�е�������ת�������fragment
//        if(!TextUtils.isEmpty(waitPayFlag)){
//            if ("1".equals(waitPayFlag)) {
//                // ��������Ҫ��ת���ڼ���fragment
//                viewPager.setCurrentItem(1);
//            }
//        }
//    }

    /**
     * ��onNewIntent�����н��е��жϣ�Ȼ����ʾMainActivity�ж�Ӧ��fragment���ɣ�
     * ֻҪִ����������Ͳ���ִ��onCreate()������
     *
     * ִ��˳�����£�
     *      onNewIntent()---->onResart()------>onStart()----->onResume()�������ھͷ����˸���
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
     * ���ڽ��մ���Ʒ���� ��ת�����ﳵfragment��idֵ
     */
    private void goFragmentIndex() {

        // ����ֱ�Ӹ�currentIndex�Ǳ��������֣�Ȼ�����showFragment()����Ӧͼ��������л���
        // �Ϳ�����������������л���MainActivity�����ڵ�fragment
        currentIndex = 0;
        showFragment();
    }

    /**
     * ʹ��show() hide()�л�ҳ��
     * ��ʾfragment
     */
    private void showFragment(){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(currentFragment != null)
            transaction.hide(currentFragment);
        else
            currentFragment = MineFragment.newInstance();

        transaction.add(R.id.nav_host_fragment_content_main,currentFragment,""+currentIndex);
        transaction.show(currentFragment);

//        //���֮ǰû����ӹ�
//        if(!fragments.get(currentIndex).isAdded()){
//
//            // ����������Ϊ��ӵ�ǰ��fragmentʱ��һ��tag
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
