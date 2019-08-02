package com.example.myapplication;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.entity.event;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.List;

public class MyRecyclerViewAdapter2 extends RecyclerView.Adapter<MyRecyclerViewAdapter2.MyViewHolder> implements View.OnClickListener {



    private List<event> list;//数据源
    private Context context;//上下文

    public MyRecyclerViewAdapter2(List<event> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item2,parent,false);
        Fresco.initialize(context);
        return new MyViewHolder(view);
    }

    //绑定
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        event data = list.get(position);
//        holder.ivIcon.setBackgroundResource(data.getIcon());
        holder.tv_event.setText(data.getName());
        holder.tv_time.setText(data.getDate());

        holder.itemView.setTag(position);
//        holder.btnAgree.setTag(position);
//        holder.btnRefuse.setTag(position);
        holder.ivIcon.setTag(position);
//        Uri uri = Uri.parse(data.getImgsrc());
        if(data.getState()==1){
            holder.ivIcon.setImageResource(R.mipmap.done);
        }else{
            holder.ivIcon.setImageResource(R.mipmap.todo);
        }

    }

    //有多少个item？
    @Override
    public int getItemCount() {
        return list.size();
    }


    public event getItemData(int position){
        return list.get(position);
    }



    public void addData(int position,event data) {
//      在list中添加数据，并通知条目加入一条,这里要改
        //这里应该先从数据库中分页获取event

        list.add(position,data);

        //添加动画

        notifyItemInserted(position);
    }

    public void delAll() {
        int count=getItemCount();
        list.clear();
        notifyItemRangeRemoved(0,count);
    }

    public void delData(int position) {
//      在list中添加数据，并通知条目加入一条,这里要改
        //这里应该先从网络中获取event，然后将event放入recyclerview_item中
        list.remove(position);
        //添加动画
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,list.size());
    }




    //创建MyViewHolder继承RecyclerView.ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder{
        //private SimpleDraweeView ivIcon;
        private ImageView ivIcon;
        private TextView tv_event,tv_time;
        //private Button btnAgree,btnRefuse;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.iv_icon);

            tv_event = itemView.findViewById(R.id.tv_event);
            tv_time = itemView.findViewById(R.id.tv_time);
//            btnAgree = itemView.findViewById(R.id.btn_agree);
//            btnRefuse = itemView.findViewById(R.id.btn_refuse);

            // 为ItemView添加点击事件
            itemView.setOnClickListener(MyRecyclerViewAdapter2.this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = (int) v.getTag();
                    mOnItemClickListener.onItemLongClick(v, ViewName.PRACTISE, position);
                    return false;
                }
            });
//            btnAgree.setOnClickListener(MyRecyclerViewAdapter2.this);
//            btnRefuse.setOnClickListener(MyRecyclerViewAdapter2.this);
            ivIcon.setOnClickListener(MyRecyclerViewAdapter2.this);
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
        void onItemLongClick(View v, ViewName viewName, int position);
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



