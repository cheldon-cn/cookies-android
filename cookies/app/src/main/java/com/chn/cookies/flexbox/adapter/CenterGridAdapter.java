package com.chn.cookies.flexbox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chn.cookies.R;

import java.util.List;


/**
 * Created by cheldon on 2021/10/9.
 */

public class CenterGridAdapter extends RecyclerView.Adapter<CenterGridAdapter.FlexViewHolder> {


    private List<String> items;
    private Context mContext;
    private OnItemClickListener listener;

    public CenterGridAdapter(List<String> lables, Context mContext) {
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

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_center_grid, parent, false);

        return new FlexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FlexViewHolder holder, final int position) {
        holder.itemTv.setText(items.get(position));

//        ViewGroup.LayoutParams lp =  holder.itemLL.getLayoutParams();
//        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
//            FlexboxLayoutManager.LayoutParams flexboxLp =
//                    (FlexboxLayoutManager.LayoutParams)  holder.itemLL.getLayoutParams();
//            flexboxLp.setFlexGrow(1.0f);
//        }




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

        TextView itemTv;
        LinearLayout itemLL;
        public FlexViewHolder(View itemView) {
            super(itemView);

            itemTv = (TextView) itemView.findViewById(R.id.tv_item);
            itemLL = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }
}
