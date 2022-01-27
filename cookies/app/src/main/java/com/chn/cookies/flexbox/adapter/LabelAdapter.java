package com.chn.cookies.flexbox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chn.cookies.R;

import java.util.List;

/**
 * Created by cheldon on 2021/10/9.
 */

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.FlexViewHolder> {


    private List<String> lables;
    private Context mContext;
    private OnItemClickListener listener;

    public LabelAdapter(List<String> lables, Context mContext) {
        this.lables = lables;
        this.mContext = mContext;
    }

    public void setOnItemClickListener( OnItemClickListener listener){
        this.listener = listener;
    }
    public  interface  OnItemClickListener{
        void onItemClick(View view,int position);
    }
    @Override
    public FlexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_label, parent, false);

        return new FlexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FlexViewHolder holder, final int position) {
        holder.itemTvLabel.setText(lables.get(position));

        holder.itemTvLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener !=null){
                    listener.onItemClick(holder.itemTvLabel,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lables.size();
    }

    static class FlexViewHolder extends RecyclerView.ViewHolder {
        TextView itemTvLabel;
        public FlexViewHolder(View itemView) {
            super(itemView);

            itemTvLabel = (TextView) itemView.findViewById(R.id.item_tv_label);
        }
    }
}
