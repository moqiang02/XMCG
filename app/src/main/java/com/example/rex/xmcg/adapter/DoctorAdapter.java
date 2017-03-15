package com.example.rex.xmcg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rex.xmcg.R;
import com.example.rex.xmcg.model.DoctorInfo;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rex on 2016/10/14.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> implements View.OnClickListener {

    private List<DoctorInfo> list;

    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public DoctorAdapter(Context context, List<DoctorInfo> list) {
        this.mContext = context;
        this.list = list;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, DoctorInfo data);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (DoctorInfo) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public DoctorAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_doctor_info, viewGroup, false);//将创建的View注册点击事件
        v.setOnClickListener(this);
        return new DoctorAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DoctorAdapter.ViewHolder viewHolder, int i) {
        // 给ViewHolder设置元素
        DoctorInfo p = list.get(i);
        viewHolder.name.setText(p.name);
        viewHolder.adept.setText(p.adept);
        if (p.image != null) {
            Glide.with(mContext).load(p.image).fitCenter().into(viewHolder.avatar);
        } else {
            viewHolder.avatar.setImageResource(R.mipmap.doctor);
        }

    }


    @Override
    public int getItemCount() {
        // 返回数据总数
        return list == null ? 0 : list.size();
    }

    // 重写的自定义ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.adept)
        TextView adept;
        @BindView(R.id.avatar)
        RoundedImageView avatar;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}