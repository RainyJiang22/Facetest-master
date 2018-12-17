package com.fukaimei.facerecognition.Detect;

import com.facepp.error.FaceppParseException;

import org.json.JSONObject;


public class FaceDetect {



    public interface CallBack{
        void success(JSONObject result);
        void error(FaceppParseException exception);
    }
}
