package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.List;
/**
 * Created by bruce on 2016/11/1.
 * BaseFragment
 */

public class BaseFragment2 extends Fragment {
    private View view;//定义view用来设置fragment的layout
    public RecyclerView recyclerView;//定义RecyclerView
    //定义以goodsentity实体类为对象的数据集合
    private ArrayList<itemdata> list = new ArrayList<itemdata>();
    //自定义recyclerveiw的适配器
    private MyRecyclerViewAdapter adapter;
    private ImageView iv;

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

        initData();

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        iv= (ImageView)view.findViewById(R.id.img_large);
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
        list = new ArrayList<>();
        list.add(new itemdata("username1", "让我们成为好友吧1！"));
        list.add(new itemdata("username2", "让我们成为好友吧2！"));
        list.add(new itemdata("username3", "让我们成为好友吧3！"));
        list.add(new itemdata("username4", "让我们成为好友吧4！"));
        list.add(new itemdata("username5", "让我们成为好友吧5！"));

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

                        Toast.makeText(getActivity(), adapter.getItemData(position).username , Toast.LENGTH_SHORT).show();
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