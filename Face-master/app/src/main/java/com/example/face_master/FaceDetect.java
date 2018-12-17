package com.example.face_master;

import android.content.Intent;

import com.facepp.error.FaceppParseException;

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


}
