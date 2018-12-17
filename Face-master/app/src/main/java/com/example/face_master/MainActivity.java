package com.example.face_master;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //声明用于startActivityForResult()的请求码，随便设置一个常量0x110
    private static final int PIC_CODE = 0x110;


    //声明UI控件的变量
    private ImageView mPhoto = null;
    private Button mGetImage = null;
    private Button mDetect = null;
    private TextView mTip = null;
    private View mWaitting = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        initViews();

        //设置监听器的初始化
        initEvents();
    }



    //初始化各个控件
    private void initViews() {
        mPhoto = (ImageView) findViewById(R.id.id_photo);
        mGetImage = (Button) findViewById(R.id.id_getImage);
        mDetect = (Button) findViewById(R.id.id_detect);
        mTip = (TextView) findViewById(R.id.id_tip);
        mWaitting = findViewById(R.id.id_watting);

    }


    //初始化各个控件的监听器
    private void initEvents() {
        mGetImage.setOnClickListener(this);
        mDetect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        //接下来实现获取图片的方法
       switch (view.getId()){
           case R.id.id_getImage:
               Intent intent = new Intent(Intent.ACTION_PICK);
               intent.setType("image/*");
               startActivityForResult(intent,PIC_CODE);
               break;
       }

    }
}
