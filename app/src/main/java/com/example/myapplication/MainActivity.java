package com.example.myapplication;


import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ActivityInfo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import android.content.Intent;


import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.Console;


public class MainActivity extends AppCompatActivity  {

    private ImageView imageView;
    private TextView textView;
    private Context context;
    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;
    private DatabaseHelper dbHelper=new DatabaseHelper(this,"event.db",null,1);
    private long mPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView = this.findViewById(R.id.imageView);
        //textView=this.findViewById(R.id.editText);
        textView=this.findViewById(R.id.textView2);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:

                                viewPager.setCurrentItem(0);
                                return true;
                            case R.id.navigation_dashboard:

                                viewPager.setCurrentItem(1);
                                return true;
                            case R.id.navigation_notifications:

                                viewPager.setCurrentItem(2);
                                return true;
//                            case R.id.item_more:
//                                viewPager.setCurrentItem(3);
//                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        setupViewPager(viewPager);
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        try {
            dbHelper.onCreate(db);
        }catch (Exception e){
            System.out.print("错误信息："+e);

        }
        
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(BaseFragment3.newInstance("第一页"));
        adapter.addFragment(BaseFragment2.newInstance("第二页"));
        adapter.addFragment(BaseFragment.newInstance("第三页"));
//        adapter.addFragment(BaseFragment.newInstance("第四页"));
        viewPager.setAdapter(adapter);
    }


    public void onShowList(View v){

            Intent intent = new Intent(this, ShowResult.class);
            startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "扫码取消！", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "扫描成功，条码值: " + result.getContents(), Toast.LENGTH_LONG).show();


//                Intent intent = new Intent(this, ShowResult.class);
//                intent.putExtra("result",  result.getContents());
//                startActivity(intent);


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if((mNowTime - mPressedTime) > 2000){//比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        }
        else{//退出程序
            this.finish();
            System.exit(0);
        }
    }


}
