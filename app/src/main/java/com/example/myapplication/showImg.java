package com.example.myapplication;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;


import android.content.Intent;

import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.client.android.camera.CameraConfigurationUtils;
import com.journeyapps.barcodescanner.camera.CameraManager;
import android.support.v7.app.AppCompatActivity;

public class showImg extends AppCompatActivity {
    private Activity activity;
    private String imgsrc;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        Fresco.initialize(showImg.this);
        setContentView(R.layout.dialog_photo_entry);
        activity = this;
        Bundle bundle = this.getIntent().getExtras();
        if(bundle!=null){
            imgsrc = bundle.getString("imgsrc"); //图片路径
        }
        //ImageView img = (ImageView)this.findViewById(R.id.large_image );
        Uri uri = Uri.parse(imgsrc);
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.draweeView);
        GenericDraweeHierarchy hierarchy =draweeView.getHierarchy();
        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);//设置图片保持原比例居中显示
        draweeView.setImageURI(uri);
        Toast toast = Toast.makeText(this, "点击图片即可返回",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 10);
        toast.show();
        draweeView.setOnClickListener(new View.OnClickListener() { // 点击返回
            public void onClick(View paramView) {
                activity.finish();
            }
        });
    }

}
