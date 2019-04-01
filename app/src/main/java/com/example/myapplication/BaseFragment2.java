package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.entity.itemdata;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.List;
/**
 * Created by ding on 2019/4/1.
 * BaseFragment2
 * 主要功能：
 * 在fragment中使用recyclerview的适配器，显示列表，允许加载、刷新功能
 * 显示网络图片，点击可全屏显示
 * 从网路中获取数据，list.add后带相关参数（或者在MyREcyclerViewAdapter中获取网络数据）
 */

public class BaseFragment2 extends Fragment {
    private View view;//定义view用来设置fragment的layout
    public RecyclerView recyclerView;//定义RecyclerView
    //定义以itemdata实体类为对象的数据集合
    private ArrayList<itemdata> list = new ArrayList<itemdata>();
    //自定义recyclerveiw的适配器
    private MyRecyclerViewAdapter adapter;



    public static BaseFragment2 newInstance(String info) {
        Bundle args = new Bundle();
        BaseFragment2 fragment = new BaseFragment2();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//
//    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_show, container,false);
        Fresco.initialize(getActivity());
        initData();

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);

        //RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this.getActivity(),LinearLayoutManager.VERTICAL,false);








        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));//控制布局为LinearLayout或者是GridView或者是瀑布流布局
        adapter = new MyRecyclerViewAdapter(list,getActivity());
        recyclerView.setAdapter(adapter);
        // 设置item及item中控件的点击事件
        adapter.setOnItemClickListener(MyItemClickListener);



        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()));
        refreshLayout.setFooterHeight(30);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh();//传入false表示刷新失败
                Toast.makeText(getActivity(),"已刷新",Toast.LENGTH_SHORT).show();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                adapter.addData(list.size());
                adapter.addData(list.size());
                adapter.addData(list.size());
                adapter.addData(list.size());
                adapter.addData(list.size());
                Toast.makeText(getActivity(),"已加载",Toast.LENGTH_SHORT).show();
                refreshlayout.finishLoadMore();//传入false表示加载失败


            }
        });







        //模拟数据

        return view;
    }

    private void initData() {
        //这里应该先从网上获取数据，然后list.add
        list = new ArrayList<>();
        list.add(new itemdata("username01", "让我们成为好友吧1！","http://www.pptbz.com/pptpic/UploadFiles_6909/201203/2012031220134655.jpg"));
        list.add(new itemdata("username02", "让我们成为好友吧2！","http://img3.imgtn.bdimg.com/it/u=3388806012,4159429573&fm=26&gp=0.jpg"));
        list.add(new itemdata("username03", "让我们成为好友吧3！","http://img5.imgtn.bdimg.com/it/u=3866142909,987137952&fm=26&gp=0.jpg"));
        list.add(new itemdata("username04", "让我们成为好友吧4！","http://img2.imgtn.bdimg.com/it/u=1181110699,1748240089&fm=26&gp=0.jpg"));
        list.add(new itemdata("username05", "让我们成为好友吧5！","http://img3.imgtn.bdimg.com/it/u=3751826293,3990589182&fm=26&gp=0.jpg"));

        list.add(new itemdata("username01", "让我们成为好友吧1！","http://www.pptbz.com/pptpic/UploadFiles_6909/201203/2012031220134655.jpg"));
        list.add(new itemdata("username02", "让我们成为好友吧2！","http://img3.imgtn.bdimg.com/it/u=3388806012,4159429573&fm=26&gp=0.jpg"));
        list.add(new itemdata("username03", "让我们成为好友吧3！","http://img5.imgtn.bdimg.com/it/u=3866142909,987137952&fm=26&gp=0.jpg"));
        list.add(new itemdata("username04", "让我们成为好友吧4！","http://img2.imgtn.bdimg.com/it/u=1181110699,1748240089&fm=26&gp=0.jpg"));
        list.add(new itemdata("username05", "让我们成为好友吧5！","http://img3.imgtn.bdimg.com/it/u=3751826293,3990589182&fm=26&gp=0.jpg"));

    }


        private MyRecyclerViewAdapter.OnItemClickListener MyItemClickListener = new MyRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, MyRecyclerViewAdapter.ViewName viewName, int position) {
//viewName可区分item及item内部控件
                switch (v.getId()) {
                    case R.id.btn_agree:
                        Toast.makeText(getActivity(), "你点击了同意按钮" + (position + 1), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_refuse:
                        Toast.makeText(getActivity(), "你点击了拒绝按钮" + (position + 1), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.iv_icon:

                        //TextView tv= (TextView)v.findViewById(R.id.tv_username);
                        //String a = tv.getText().toString();
                        Intent intent = new Intent(getActivity(), showImg.class);
                        intent.putExtra("imgsrc",adapter.getItemData(position).imgsrc);
                        startActivity(intent);
                        //Toast.makeText(getActivity(), adapter.getItemData(position).username , Toast.LENGTH_SHORT).show();
                        //recyclerView.getChildAt(position).findViewById(R.id.tv_username);
                        //iv.setImageResource(R.drawable.ic_dashboard_black_24dp);
                        break;
                    default:
                        Toast.makeText(getActivity(), "你点击了item按钮" + (position + 1), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onItemLongClick(View v) {

            }
        };

}