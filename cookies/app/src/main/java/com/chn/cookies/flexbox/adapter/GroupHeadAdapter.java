package com.chn.cookies.flexbox.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chn.cookies.R;
import com.chn.cookies.flexbox.utils.ScreenUtil;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;


/**
 * Created by cheldon on 2021/10/9.
 */

public class GroupHeadAdapter extends RecyclerView.Adapter<GroupHeadAdapter.FlexViewHolder> {


    private List<Integer> items;
    private Context mContext;
    private OnItemClickListener listener;

    public GroupHeadAdapter(List<Integer> lables, Context mContext) {
        this.items = lables;
        this.mContext = mContext;
    }

    public void setOnItemClickListener( OnItemClickListener listener){
        this.listener = listener;
    }
    public  interface  OnItemClickListener{
        void onItemClick(View view, int position);
    }
    @Override
    public FlexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_group_head, parent, false);

        return new FlexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FlexViewHolder holder, final int position) {

        setItem(holder.itemTv,position);

        holder.itemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener !=null){
                    listener.onItemClick(holder.itemTv,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class FlexViewHolder extends RecyclerView.ViewHolder {
        FlexboxLayout itemTv;
        public FlexViewHolder(View itemView) {
            super(itemView);

            itemTv = (FlexboxLayout) itemView.findViewById(R.id.tv_item);
        }
    }


    public void setItem(FlexboxLayout view,int position){
        for (int i = 0; i < items.get(position) ; i++) {
            TextView textView = new TextView(mContext);
            textView.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
            textView.setText("1");
            textView.setGravity(Gravity.CENTER);
            setItemSize(textView,items.get(position));
            view.addView(textView);
        }
    }
    /**
     * 设置item的宽和高
     */
    public void setItemSize(View itemView,int num){
        int parentWidth = ScreenUtil.dp2px(mContext,96);
        int parentHeight = parentWidth;
        int itemWidth = 0,itemHeight = 0;
      switch (num){
          case 1:
               itemHeight = parentHeight;
               itemWidth = parentWidth;
              break;
          case 2:
          case 3:
          case 4:
              itemHeight = parentHeight/2;
              itemWidth = parentWidth/2;
              break;
          case 5:
          case 6:
          case 7:
          case 8:
          case 9:
              itemHeight = parentHeight/3;
              itemWidth = parentWidth/3;
              break;
          default:
              break;
      }

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(itemWidth,itemHeight);
        itemView.setLayoutParams(layoutParams);
    }
}
