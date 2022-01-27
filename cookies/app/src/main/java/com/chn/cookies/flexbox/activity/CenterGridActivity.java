package com.chn.cookies.flexbox.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.chn.cookies.R;
import com.chn.cookies.flexbox.adapter.CenterGridAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by cheldon on 2021/10/9.
 * 中心展开的网格布局
 */

public class CenterGridActivity extends BaseActivity {

    TextView tvTitle;
    RecyclerView recycleView;
    Button btnAdd;
    Button btnSub;
    LinearLayout llOperator;

    private int count = 0;

    private List<String> items = new ArrayList<>();
    private CenterGridAdapter centerGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.layout_common_recycleview);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnSub = (Button) findViewById(R.id.btn_sub);
        llOperator = (LinearLayout) findViewById(R.id.ll_operator);

        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        llOperator.setVisibility(View.VISIBLE);
        tvTitle.setText("中心展开网格布局");

        //设置主轴方向为横轴
        FlexboxLayoutManager manager = new FlexboxLayoutManager(this, FlexDirection.ROW);
        //设置可以换行
        manager.setFlexWrap(FlexWrap.WRAP);
        //设置item沿主轴方向的位置
        manager.setJustifyContent(JustifyContent.CENTER);
        //设置item 沿次轴方向的位置
        manager.setAlignItems(AlignItems.CENTER);

        recycleView.setLayoutManager(manager);
        centerGridAdapter = new CenterGridAdapter(items, this);
        recycleView.setAdapter(centerGridAdapter);


    }

    @Override
    protected void bindEvent() {
        centerGridAdapter.setOnItemClickListener(new CenterGridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(CenterGridActivity.this, items.get(position), Toast.LENGTH_LONG).show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               count ++ ;
               items.add(count+"");
               centerGridAdapter.notifyDataSetChanged();

            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (items.size() <=1){
                    return;
                }
                items.remove(items.size() -1);
                centerGridAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initData() {

        items.add(count+"");
        centerGridAdapter.notifyDataSetChanged();


    }

}
