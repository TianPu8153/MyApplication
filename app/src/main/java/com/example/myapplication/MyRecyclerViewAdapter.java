package com.example.myapplication;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.entity.itemdata;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;


import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {



    private List<itemdata> list;//数据源
    private Context context;//上下文

    public MyRecyclerViewAdapter(List<itemdata> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
        Fresco.initialize(context);
        return new MyViewHolder(view);
    }

    //绑定
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        itemdata data = list.get(position);
//        holder.ivIcon.setBackgroundResource(data.getIcon());
        holder.tvUsername.setText(data.getUsername());
        holder.tvMessage.setText(data.getMessage());

        holder.itemView.setTag(position);
        holder.btnAgree.setTag(position);
        holder.btnRefuse.setTag(position);
        holder.ivIcon.setTag(position);
        Uri uri = Uri.parse(data.getImgsrc());
        holder.ivIcon.setImageURI(uri);
    }

    //有多少个item？
    @Override
    public int getItemCount() {
        return list.size();
    }


    public itemdata getItemData(int position){
        return list.get(position);
    }


    public void addData(int position) {
//      在list中添加数据，并通知条目加入一条,这里要改
        //这里应该先从网络中获取itemdata，然后将itemdata放入recyclerview_item中
        list.add(position,new itemdata("username"+position, "应该先从网上获取数据"+position,"http://www.pptbz.com/pptpic/UploadFiles_6909/201203/2012031220134655.jpg"));
        //添加动画
        notifyItemInserted(position);
    }

    //创建MyViewHolder继承RecyclerView.ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private SimpleDraweeView ivIcon;
        //private ImageView ivIcon;
        private TextView tvUsername,tvMessage;
        private Button btnAgree,btnRefuse;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.iv_icon);
//            Uri uri = Uri.parse("http://www.pptbz.com/pptpic/UploadFiles_6909/201203/2012031220134655.jpg");
//            ivIcon.setImageURI(uri);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvMessage = itemView.findViewById(R.id.tv_message);
            btnAgree = itemView.findViewById(R.id.btn_agree);
            btnRefuse = itemView.findViewById(R.id.btn_refuse);

            // 为ItemView添加点击事件
            itemView.setOnClickListener(MyRecyclerViewAdapter.this);
            btnAgree.setOnClickListener(MyRecyclerViewAdapter.this);
            btnRefuse.setOnClickListener(MyRecyclerViewAdapter.this);
            ivIcon.setOnClickListener(MyRecyclerViewAdapter.this);
        }

    }

    //=======================以下为item中的button控件点击事件处理===================================

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }

    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, ViewName viewName, int position);
        void onItemLongClick(View v);
    }

    private OnItemClickListener mOnItemClickListener;//声明自定义的接口

    //定义方法并传给外面的使用者
    public void setOnItemClickListener(OnItemClickListener  listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                case R.id.recyclerView:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
                    break;
            }
        }
    }
}



