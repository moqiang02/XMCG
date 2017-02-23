package com.example.rex.xmcg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rex.xmcg.R;
import com.example.rex.xmcg.model.Doctor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rex on 2016/10/14.
 */

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.ViewHolder> implements View.OnClickListener {

    private List<Doctor> list;

    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public RegisterAdapter(Context context, List<Doctor> list) {
        this.mContext = context;
        this.list = list;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Doctor data);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Doctor) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public RegisterAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_register, viewGroup, false);//将创建的View注册点击事件
        v.setOnClickListener(this);
        return new RegisterAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RegisterAdapter.ViewHolder viewHolder, int i) {
        // 给ViewHolder设置元素
        Doctor p = list.get(i);
        viewHolder.name.setText(p.doctorName);
        viewHolder.deptname.setText(p.deptName);
        Glide.with(mContext).load(p.doctorBean.image).override(180, 160).fitCenter().into(viewHolder.avatar);
        viewHolder.introduce.setText(p.doctorBean.adept.substring(0,20)+"...");
        viewHolder.title.setText(p.doctorBean.title);
        if (p.opdTimeID.equals("1")){
            viewHolder.time.setText("上午诊");
        }else if(p.opdTimeID.equals("2")){
            viewHolder.time.setText("下午诊");
        }else if(p.opdTimeID.equals("3")){
            viewHolder.time.setText("晚间诊");
        }
        //将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(p);
        if (p.canReg.equals("N")) {
            viewHolder.status.setText("停诊");
            viewHolder.heart.setImageResource(R.mipmap.gray_heart);
        } else if (p.isFull.equals("Y")) {
            viewHolder.status.setText("已满");
            viewHolder.heart.setImageResource(R.mipmap.gray_heart);
        } else if (p.canReg.equals("Y")) {
            viewHolder.status.setText("有号");
            viewHolder.heart.setImageResource(R.mipmap.red_heart);
        } else {
            viewHolder.status.setText("未满");
            viewHolder.heart.setImageResource(R.mipmap.gray_heart);
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
        @BindView(R.id.deptname)
        TextView deptname;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.avatar)
        CircleImageView avatar;
        @BindView(R.id.heart)
        ImageView heart;
        @BindView(R.id.introduce)
        TextView introduce;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}