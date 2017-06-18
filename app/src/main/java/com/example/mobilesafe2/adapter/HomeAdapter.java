package com.example.mobilesafe2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobilesafe2.R;
import com.example.mobilesafe2.bean.HomeBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/18.
 */

public class HomeAdapter extends BaseAdapter {

    private final List<HomeBean> mDatas;
    private final Context mContext;

    public HomeAdapter(List<HomeBean> datas, Context context) {
        this.mDatas = datas;
        this.mContext = context;

    }

    @Override
    public int getCount() {

        return 4;
    }

    @Override
    public HomeBean getItem(int position) {
        return mDatas.get(position);
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
            convertView = View.inflate(parent.getContext(), R.layout.view_gridview_item, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HomeBean homeBean = getItem(position);
        holder.iv.setImageResource(homeBean.imageId);
        holder.tv_title.setText(homeBean.title);
        holder.tv_desc.setText(homeBean.desc);

        return convertView;
    }

    class ViewHolder {
        ImageView iv;
        TextView tv_title;
        TextView tv_desc;

    }
}
