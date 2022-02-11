package com.chn.cookies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chn.cookies.R;
import com.chn.cookies.databinding.FragmentHomeBinding;
import com.chn.cookies.databinding.FragmentMineBinding;
import com.chn.cookies.flexbox.activity.SettingActivity;
import com.chn.cookies.flexbox.widget.GroupViewItem;

/**
 * Created by EverGlow on 2018/4/11 11:52
 */
public class MineFragment extends Fragment {

    private FragmentMineBinding binding;

    //@BindView(R.id.nick_name)
    TextView mNickName;
    //@BindView(R.id.tv_money_balance)
    TextView mTvMoneyBalance;
    //@BindView(R.id.tv_integral)
    TextView mTvIntegral;
    //@BindView(R.id.profile_image)
    ImageView mProfileImage;
    //@BindView(R.id.tv_recharge)
    TextView mTvRecharge;
    //@BindView(R.id.tv_withdraw)
    TextView mTvWithdraw;
    //@BindView(R.id.item_card)
    GroupViewItem mItemCard;
    //@BindView(R.id.item_truth)
    GroupViewItem mItemTruth;
    //@BindView(R.id.item_order)
    GroupViewItem mItemOrder;
    //@BindView(R.id.item_shopping)
    GroupViewItem mItemShopping;
    //@BindView(R.id.item_address)
    GroupViewItem mItemAddress;
    //@BindView(R.id.item_setting)
    GroupViewItem mItemSetting;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    public void initData(@Nullable Bundle savedInstanceState) {

    }

    public void setData(@Nullable Object data) {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        binding = FragmentMineBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        init(rootView);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent intent = getIntent();
//        int id = intent.getIntExtra("mine",-1);
//        if(id>0){
//            System.out.println("aaa"+id);
//            if(id==1) {
//                transaction.replace(R.id.zlayout, showFragment);
//            }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    protected void init(View root)
    {

    }

    public void refresh() {
//        CommonPreferences preferences = new CommonPreferences(MyApplication.APP);
//        EntitiyUser user = preferences.getModel(EntitiyUser.class);
//        android.util.Log.d(TAG, "refresh: ");
//        if (user != null)
        {
          //  mNickName.setText(user.getAppNickname());
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    @OnClick({R.id.profile_image, R.id.item_card, R.id.item_truth, R.id.item_order, R.id.item_shopping, R.id.item_address, R.id.item_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profile_image:
//                startActivity(new Intent(getActivity(), AcUserInfo.class));
                break;
            case R.id.item_card:
//                startActivity(new Intent(getActivity(), CardManngeActivity.class));
                break;
            case R.id.item_truth:
//                startActivity(new Intent(getActivity(), CertificationActivity.class));
                break;
            case R.id.item_order:
//                startActivity(new Intent(getActivity(), WebActivity.class));
                
                break;
            case R.id.item_shopping:
                break;
            case R.id.item_address:
//                startActivity(new Intent(getActivity(), AcAddressList.class));
                break;

            case R.id.item_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }

    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            refresh();
        }
    }
}
