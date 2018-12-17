package com.example.face_master;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
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

    //标示当前所使用的图片的路径
    private String mCurrentPhotoStr = null;

    //用于存储经过压缩后的Bitmap对象
    private Bitmap mPhotoImg =  null;



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

    /*
     * 由于用到了startActivityForResult()，那么肯定得有一个方法要对此作出回应
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
       //如果是代表获取图片的那个请求码，就进一步执行下一步操作
        if (requestCode == PIC_CODE){
            //看看Intent中的内容是不是空的
            if (intent != null){
                Uri uri = intent.getData();
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                //这样我们就拿到了图片的路径
                mCurrentPhotoStr = cursor.getString(idx);
                cursor.close();
                //有了这个路径之后，就可以去这个路径去获取图片
                //不过由于通常一个照片好一点的动不动就是好几十MB，而Face++的SDK对此有限制，要求照片转换为二进制数据
                //最大不能超过3M，因此需要对要获取的图片进行一个压缩。所以自定义了一个resizePhoto()方法去压缩照片

                resizePhoto();

                //压缩图片完之后需要再屏幕上显示出来
                mPhoto.setImageBitmap(mPhotoImg);
                mTip.setText("可以开始分析了===>");
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 压缩需要注意的问题是，最终压缩后的图片不能超过3M
     */
    private void resizePhoto() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoStr,options);

        double ratio = Math.max(options.outWidth * 1.0d / 1024f , options.outHeight * 1.0d / 1024f);
        options.inSampleSize = (int) Math.ceil(ratio);
        options.inJustDecodeBounds = false;
        mPhotoImg = BitmapFactory.decodeFile(mCurrentPhotoStr,options);
    }
}
