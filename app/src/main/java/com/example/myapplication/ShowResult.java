package com.example.myapplication;

import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.view.View;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

import android.hardware.Camera;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;



import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.Toast;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.myapplication.entity.itemdata;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.client.android.camera.CameraConfigurationUtils;
import com.journeyapps.barcodescanner.camera.CameraManager;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


public class ShowResult extends AppCompatActivity {
    private TextView textView;

    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<itemdata> list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_show);
        initData();
//        textView=this.findViewById(R.id.textView);
//        Intent intent = getIntent();
//        String result = intent.getStringExtra("result");
//        textView.setText(result);

//        Uri uri = Uri.parse(result);
//        Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent2);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));//控制布局为LinearLayout或者是GridView或者是瀑布流布局
        adapter = new MyRecyclerViewAdapter(list,this);
        recyclerView.setAdapter(adapter);
        // 设置item及item中控件的点击事件
        adapter.setOnItemClickListener(MyItemClickListener);


        RefreshLayout refreshLayout = this.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new MaterialHeader(this));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh();//传入false表示刷新失败
                Toast.makeText(ShowResult.this,"已刷新",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ShowResult.this,"已加载",Toast.LENGTH_SHORT).show();
                refreshlayout.finishLoadMore();//传入false表示加载失败


            }
        });

    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new itemdata("username1", "让我们成为好友吧1！","http://www.pptbz.com/pptpic/UploadFiles_6909/201203/2012031220134655.jpg"));
        list.add(new itemdata("username2", "让我们成为好友吧2！","http://www.pptbz.com/pptpic/UploadFiles_6909/201203/2012031220134655.jpg"));
        list.add(new itemdata("username3", "让我们成为好友吧3！","http://www.pptbz.com/pptpic/UploadFiles_6909/201203/2012031220134655.jpg"));

    }

    private MyRecyclerViewAdapter.OnItemClickListener MyItemClickListener = new MyRecyclerViewAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, MyRecyclerViewAdapter.ViewName viewName, int position) {
//viewName可区分item及item内部控件
            switch (v.getId()){
                case R.id.btn_agree:
                    Toast.makeText(ShowResult.this,"你点击了同意按钮"+(position+1),Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_refuse:
                    Toast.makeText(ShowResult.this,"你点击了拒绝按钮"+(position+1),Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(ShowResult.this,"你点击了item按钮"+(position+1),Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onItemLongClick(View v) {

        }
    };



}
