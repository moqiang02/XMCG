package com.example.rex.xmcg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rex.xmcg.R;
import com.example.rex.xmcg.model.DepartmentGroup;

import java.util.ArrayList;

/**
 * Created by Rex on 2016/10/13.
 */
public class DeptGroupAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<DepartmentGroup> list;
    private int selectItem = -1;

    public DeptGroupAdapter(Context mContext, ArrayList<DepartmentGroup> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.item_dept_group, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position).deptGroupName);
        if (position == selectItem) {// 如果当前的行就是ListView中选中的一行，就更改显示样式
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.white));// 更改整行的背景色
            holder.img.setVisibility(View.VISIBLE);
        }else{
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.gray));// 更改整行的背景色
            holder.img.setVisibility(View.GONE);
        }

        return convertView;
    }

    static class ViewHolder {
        public ImageView img;
        public TextView title;
    }
}
