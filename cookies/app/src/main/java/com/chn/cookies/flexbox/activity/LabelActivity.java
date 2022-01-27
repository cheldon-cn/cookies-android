package com.chn.cookies.flexbox.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.chn.cookies.flexbox.adapter.LabelAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import com.chn.cookies.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cheldon on 2021/10/9.
 * 标签布局
 */

public class LabelActivity extends BaseActivity {

    TextView tvTitle;
    RecyclerView recycleView;

    private List<String> labels = new ArrayList<>();
    private LabelAdapter labelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.layout_common_recycleview);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);

        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tvTitle.setText("Lable标签布局");

        //设置主轴方向为横轴
        FlexboxLayoutManager manager = new FlexboxLayoutManager(this, FlexDirection.ROW);
        //设置item沿主轴方向的位置
        manager.setJustifyContent(JustifyContent.FLEX_START);
        //设置item 沿次轴方向的位置
        manager.setAlignItems(AlignItems.CENTER);

        recycleView.setLayoutManager(manager);
        labelAdapter = new LabelAdapter(labels,this);
        recycleView.setAdapter(labelAdapter);


    }

    @Override
    protected void bindEvent() {
     labelAdapter.setOnItemClickListener(new LabelAdapter.OnItemClickListener() {
         @Override
         public void onItemClick(View view,int position) {
             Toast.makeText(LabelActivity.this,labels.get(position),Toast.LENGTH_LONG).show();
         }
     });
    }

    @Override
    protected void initData() {

        labels.add("自由行走的花");
        labels.add("不染");
        labels.add("十七");
        labels.add("借我");
        labels.add("左手指月");
        labels.add("Letter");
        labels.add(" My Heart Will Go On");
        labels.add("望");
        labels.add("当所有的星星落在我的头上");
        labels.add("可恋不可说");
        labels.add("等");
        labels.add(" My Prayer");
        labels.add("我也可以是流浪诗人");
        labels.add("园游会");

        labelAdapter.notifyDataSetChanged();


    }
}
