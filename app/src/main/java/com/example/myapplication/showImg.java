package com.example.myapplication;
import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

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
        draweeView.setImageURI(uri);//获取到的图片放入占位图中
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
