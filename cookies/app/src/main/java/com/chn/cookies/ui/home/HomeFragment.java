package com.chn.cookies.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.chn.cookies.MainActivity;
import com.chn.cookies.R;
import com.chn.cookies.databinding.FragmentHomeBinding;
import com.chn.cookies.flexbox.activity.CenterGridActivity;
import com.chn.cookies.flexbox.activity.LabelActivity;
import com.chn.cookies.flexbox.activity.PayActivity;
import com.chn.cookies.flexbox.activity.SettingActivity;
import com.chn.cookies.login.LoginActivity;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private LinkedHashMap map = new LinkedHashMap<Integer,String>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        initCookies(root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    protected void initCookies(View root)
    {
        map.clear();
        map.put( R.drawable.weinxin_2x,"微信");
        map.put( R.drawable.zhifubao_2x,"支付宝");
        map.put( R.drawable.yinlian_2x,"银联");
        map.put( R.mipmap.dingdan,"订单");
        map.put( R.mipmap.temai,"特卖");
        map.put( R.mipmap.gouwuche,"购物车");
        map.put( R.mipmap.yinhangka,"银行卡");
        map.put( R.mipmap.licai,"理财");
        map.put( R.mipmap.dae,"大额");
        map.put( R.mipmap.renzheng,"认证");
        map.put( R.mipmap.zhiwen,"指纹");
        map.put( R.mipmap.renlian,"人脸");
        map.put( R.mipmap.check_updata,"更新");
        map.put( R.mipmap.dizhi,"地址");
        map.put( R.mipmap.setting,"设置");

        if(root == null) return;
        View main = root.findViewById(R.id.cookies_main);
        if(main == null) return;
        View column = main.findViewById(R.id.item_column);
        if(column == null) return;

        View row1 = column.findViewById(R.id.item_row1);
        View row2 = column.findViewById(R.id.item_row2);
        View row3 = column.findViewById(R.id.item_row3);
        View row4 = column.findViewById(R.id.item_row4);
        View row5 = column.findViewById(R.id.item_row5);
        setRow(row1,1);
        setRow(row2,2);
        setRow(row3,3);
        setRow(row4,4);
        setRow(row5,5);
    }
    protected void setRow(View row,int nRow)
    {
        if(row == null) return;
        View single1 = row.findViewById(R.id.item_single1);
        View single2 = row.findViewById(R.id.item_single2);
        View single3 = row.findViewById(R.id.item_single3);
//        setItem(single1,"Wechat",R.drawable.weinxin_2x);

        int m = 0;
        Iterator<LinkedHashMap.Entry<Integer, String>> iterator1 = map.entrySet().iterator();
        LinkedHashMap.Entry<Integer, String> entry;
        while (iterator1.hasNext()) {
            entry = iterator1.next();

            if(m > 3*nRow) break;
            if(m >= 3*(nRow-1) && m < 3*nRow)
            {
                int id = entry.getKey().intValue();
                String text = entry.getValue();

                View view = null;
                if(m%3 == 0)
                    view = single1;
                else if(m%3 == 1)
                    view = single2;
                else
                    view = single3;

                setItem(view,text,id);
            }

            m++;
        }

    }

    protected void setItem(View item, CharSequence text, int idImg)
    {
        if(item == null) return;

        ImageView imgView = item.findViewById(R.id.item_img);
        TextView txtView = item.findViewById(R.id.item_text);
        if(imgView != null)
            imgView.setImageDrawable(getResources().getDrawable(idImg));
        //imgView.setBackground(getResources().getDrawable(R.drawable.weinxin_2x));
        if(txtView != null)
            txtView.setText(text);

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int id = v.getId();
                ViewParent parent = v.getParent();
                String str = parent.getClass().toString();
                View focus = (View)parent;
                int iRoot = focus.getId();
                process(iRoot,id);
            }
        });
    }


    protected int toRow(int idRoot)
    {
        int row = 0;
        switch (idRoot)
        {
            case R.id.item_row1: { row = 1;break; }
            case R.id.item_row2: { row = 2;break; }
            case R.id.item_row3: { row = 3;break; }
            case R.id.item_row4: { row = 4;break; }
            case R.id.item_row5: { row = 5;break; }
        }
        return row;
    }
    protected int toColumn(int id)
    {
        int column = 0;
        switch (id)
        {
            case R.id.item_single1: { column = 1;break; }
            case R.id.item_single2: { column = 2;break; }
            case R.id.item_single3: { column = 3;break; }
        }
        return column;
    }

    protected void process(int idRoot,int id)
    {
        int column = toColumn(id) - 1;
        int row = toRow(idRoot) - 1;
        int index = (row)*3+column;
        int m = 0;
        Iterator<LinkedHashMap.Entry<Integer, String>> iterator1 = map.entrySet().iterator();
        LinkedHashMap.Entry<Integer, String> entry;
        while (iterator1.hasNext()) {
            entry = iterator1.next();
            if(m == index){
                int i = entry.getKey().intValue();
                String text = entry.getValue();
                distribute(i);
                break;
            }
            m++;
        }
    }

    protected void distribute(int id)
    {
        FragmentActivity activity = HomeFragment.this.getActivity();
        int m = 0;
        switch (id) {
            case R.drawable.weinxin_2x:
                startActivity(new Intent(activity, PayActivity.class));
                break;
            case R.drawable.zhifubao_2x:
                startActivity(new Intent(activity, CenterGridActivity.class));
                break;
            case R.drawable.yinlian_2x:
                startActivity(new Intent(activity, LabelActivity.class));
                break;
            case R.mipmap.dingdan: m++;
                break;
            case R.mipmap.temai: m--;
                break;
            case R.mipmap.gouwuche: ++m;
                break;
            case R.mipmap.yinhangka: m++;
                break;
            case R.mipmap.licai: m--;
                break;
            case R.mipmap.dae: ++m;
                break;
            case R.mipmap.renzheng:
                startActivity(new Intent(activity, LoginActivity.class));
                break;
            case R.mipmap.zhiwen: m--;
                break;
            case R.mipmap.renlian: ++m;
                break;
            case R.mipmap.check_updata: m++;
                break;
            case R.mipmap.dizhi: m--;
                break;
            case R.mipmap.setting:
                startActivity(new Intent(activity, SettingActivity.class));
                break;
        }
    }


}