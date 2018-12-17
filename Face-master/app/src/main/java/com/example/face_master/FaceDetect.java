package com.example.face_master;

import android.content.Intent;
import android.graphics.Bitmap;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;

import org.json.JSONObject;

public class FaceDetect {

    //考虑个接口
    /**
     * 下面的那个detect()方法可能需要返回多个返回值：
     * 	如果检测成功就返回一个JSON字符串；如果检测失败就返回一个提示信息；
     * 想完成这样返回多个返回值，可以考虑“接口”
     */
    public interface CallBack{
        void success(JSONObject result);
        void error(FaceppParseException exception);
    }

    //用于分析图片的方法
    public static void detect(final Bitmap bitmap,final CallBack callBack){
        //分析师很耗时间的，所以要开一个子线程进行
        new Thread(new Runnable() {
            @Override
            public void run() {
                //创建request请求
                //构造器的第三个参数是问是否是中国地区。经过测试，发现第三个参数和第四个参数都为true，能获得更可靠的网络连接

            }
        });
    }


}
