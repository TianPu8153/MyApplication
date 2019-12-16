package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
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

import com.example.myapplication.entity.event;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianpu81533 on 2019/8/1.
 * BaseFragment 主要使用的框架及实现的功能
 * recyclerveiw 自定义列表，下拉刷新，上拉加载
 * searchbox 搜索框(配合列表) 需要改project->searchbox/layout/dialog_search,加<requestFocus />
 * database 数据库
 */

public class BaseFragment3 extends Fragment {

    private View view;//定义view用来设置fragment的layout
    public RecyclerView recyclerView;//定义RecyclerView
    //定义以itemdata实体类为对象的数据集合
    private ArrayList<event> list = new ArrayList<event>();
    //自定义recyclerveiw的适配器
    private MyRecyclerViewAdapter2 adapter;
    private int page;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Toolbar mToolbar;
    private EditText etSearchKeyword;
    Activity mActivity;
    AppCompatActivity mAppCompatActivity;

    public static BaseFragment3 newInstance(String info) {
        Bundle args = new Bundle();
        BaseFragment3 fragment = new BaseFragment3();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_db, null);

        //super.onViewCreated(view, savedInstanceState);
        SearchFragment searchFragment = SearchFragment.newInstance();

        mActivity = getActivity();
        mToolbar=view.findViewById(R.id.toolbar);
        AppCompatActivity mAppCompatActivity = (AppCompatActivity) mActivity;
        mAppCompatActivity.setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFragment.showFragment(mAppCompatActivity.getSupportFragmentManager(),SearchFragment.TAG);

            }
        });



//        mToolbar=view.findViewById(R.id.toolbar);
//        mAppCompatActivity.setSupportActionBar(mToolbar);


        dbHelper=new DatabaseHelper(getActivity(),"event.db",null,1);
        db=dbHelper.getReadableDatabase();
        recyclerView =view.findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));//控制布局为LinearLayout或者是GridView或者是瀑布流布局
        adapter = new MyRecyclerViewAdapter2(list,getActivity());
        adapter.delAll();
//        add();
        initData("");

        recyclerView.setAdapter(adapter);//initData()写在这个前面或者后面???
        adapter.setOnItemClickListener(MyItemClickListener);
        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()));
        refreshLayout.setFooterHeight(30);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                adapter.delAll();
                initData("");
                refreshlayout.finishRefresh();//传入false表示刷新失败
                Toast.makeText(getActivity(),"已刷新",Toast.LENGTH_SHORT).show();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                if(loadMore()) {
                    //Toast.makeText(getActivity(),"已加载",Toast.LENGTH_SHORT).show();
                    refreshlayout.finishLoadMore();//传入false表示加载失败
                }else{
                    refreshlayout.finishLoadMoreWithNoMoreData();
                }
            }
        });
        searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
            @Override
            public void OnSearchClick(String keyword) {
                //这里处理逻辑

                initData(keyword);
                //Toast.makeText(getActivity(), keyword, Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }


    private boolean loadMore() {
        page=page+1;
        String page1=page*10+"";
        String page2=(page+1)*10+"";
        Cursor cursor =db.rawQuery("select * from event order by id desc limit ?,?",new String[]{page1,page2});
        if(cursor.getCount()==0){ //返回值为空
            Toast.makeText(getActivity(), "到底了" , Toast.LENGTH_SHORT).show();
            return false;
        }
        while(cursor.moveToNext())
        {
            int id  = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            int state = cursor.getInt(cursor.getColumnIndex("state"));
            int level = cursor.getInt(cursor.getColumnIndex("level"));
            Log.e("id:",id+"");
            event e=new event(id,name,date,state,level,"");
            list.add(e);
            adapter.addData(list.size()-1,e);
        }
        cursor.close();
        return true;
    }

    private void initData(String search) {
        adapter.delAll();
        //这里应该先从数据库获取数据，然后list.add
        list = new ArrayList<>();
        page=0;
        //select * from yourtable where 查询条件 order by id desc limit 0,10;
        String page1=page*10+"";
        String page2=(page+1)*10+"";
        Cursor cursor;
//        if(search!=""){
//            cursor1 =db.rawQuery("select * from event where name="+search+" order by id desc ",new String[]{});
//        }else{
//            cursor1 =db.rawQuery("select * from event order by id desc ",new String[]{});
//        }
//        Log.e("总数为：",cursor1.getCount()+"");
//        cursor1.close();
        if(search!=""){
            cursor =db.rawQuery("select * from event where name like '%"+search+"%' order by id desc limit ?,?",new String[]{page1,page2});
        }else{
            cursor =db.rawQuery("select * from event order by id desc limit ?,? ",new String[]{page1,page2});
        }
        //Cursor cursor =db.rawQuery("select * from event order by id desc limit ?,?",new String[]{page1,page2});
        if (cursor.getCount()== 0){
            Toast.makeText(getActivity(), "没有数据！", Toast.LENGTH_LONG).show();
        }
        while(cursor.moveToNext())
        {
            int id  = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            int state = cursor.getInt(cursor.getColumnIndex("state"));
            int level = cursor.getInt(cursor.getColumnIndex("level"));
            event e=new event(id,name,date,state,level,"");
            list.add(e);
            adapter.addData(list.size()-1,e);
        }
        cursor.close();
    }

    public void check(){
        Cursor cursor =db.rawQuery("select * from event where level=?",new String[]{"1"});
        while(cursor.moveToNext())
        {
            int id  = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            int state = cursor.getInt(cursor.getColumnIndex("state"));
            int level = cursor.getInt(cursor.getColumnIndex("level"));
            Log.d("data:",id+name+date+state+level);
        }
        cursor.close();

    }
    public void add(){
        for (int i=0;i<20;i++) {
            ContentValues values = new ContentValues();
            values.put("name", "事件名称"+i);
            values.put("date", "2019-08-01 10:08:23");
            if(i%3==0){values.put("state", 1);}
            else {values.put("state", 0);}
            values.put("level", 1);
            db.insert("event", null, values);
        }
    }


    private MyRecyclerViewAdapter2.OnItemClickListener MyItemClickListener = new MyRecyclerViewAdapter2.OnItemClickListener() {
        @Override
        public void onItemClick(View v, MyRecyclerViewAdapter2.ViewName viewName, int position) {
            //viewName可区分item及item内部控件
            switch (v.getId()) {
                case R.id.btn_agree:
                    Toast.makeText(getActivity(), "你点击了同意按钮" + (position + 1), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_refuse:
                    Toast.makeText(getActivity(), "你点击了拒绝按钮" + (position + 1), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.iv_icon:
                    //点击查看大图
                    Intent intent = new Intent(getActivity(), showImg.class);
                    intent.putExtra("imgsrc",adapter.getItemData(position).imgsrc);
                    startActivity(intent);
                    break;
                default:
                    Toast.makeText(getActivity(), "你点击了item按钮" + (position + 1)+",id:"+adapter.getItemData(position).getId(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        @Override
        public void onItemLongClick(View v, MyRecyclerViewAdapter2.ViewName viewName, final int position) {
            //长按显示删除按钮，点击删除item
            Toast.makeText(getActivity(), "position:" + (position), Toast.LENGTH_SHORT).show();
            View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_pop, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int popupWidth = popupView.getMeasuredWidth();
            int popupHeight =  popupView.getMeasuredHeight();
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0]+v.getWidth()/2)-popupWidth/2,
                    location[1]-popupHeight+30);
            Button button=popupView.findViewById(R.id.bt_r);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.delData(position);
                    popupWindow.dismiss();
                }
            });
        }
    };

}