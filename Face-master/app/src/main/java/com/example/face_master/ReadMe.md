### 开发中应该注意的点

  - 我们进行解耦的时候,detect()方法需要返回多个返回值，
 如果检测成功就返回一个JSON字符串，如果检测失败就返回一个提示信息。
  想完成这样返回多个返回值，可以考虑接口

  <br>
     public interface CallBack{
     <br>
          void success(JSONObject result);
          <br>
          void error(FaceppParseException exception);
          <br>s
     }

  - 其次在主函数解析face对象的属性，因为要围着人脸画一个矩形，因此需要获得
  人脸左上角的起始坐标和人脸的宽度和高度,确定一个矩形

  - face++返回的那些JSON数据都是百分比，需要转换为实际像素点位置后才能使用

  <br>
      x= x /100 * bitmap.getWidth();
                  y = y/100 * bitmap.getHeight();
                  width = width / 100 * bitmap.getWidth();
                  height = height / 100 * bitmap.getHeight();

  <br>

  - 获得图像中的人物的性别和年龄

  <br>
    JSONObject attributeObj = face.getJSONObject("attribute");
    String sex = attributeObj.getJSONObject("gender").getString("value");
    int age  = attributeObj.getJSONObject("age").getInt("value");
  <br>

  - 把一个TextView转化成Bitmap，绘制在当前的画布上

<br>
   int ageWidth = ageBitmap.getWidth();
   int ageHeight = ageBitmap.getHeight();
   if (bitmap.getWidth()<mPhoto.getWidth() && bitmap.getHeight() <mPhoto.getHeight()){
       float ratio = Math.max(bitmap.getWidth() * 1.0f / mPhoto.getWidth() , bitmap.getHeight() * 1.0f / mPhoto.getHeight());
       ageBitmap = Bitmap.createScaledBitmap(ageBitmap, (int)(ageWidth * ratio), (int)(ageHeight * ratio) , false);
       canvas.drawBitmap(ageBitmap , x - ageBitmap.getWidth() / 2 , y - height / 2 - ageBitmap.getHeight() , null);
      }
 <br>

 - 需要避免子线程控制UI的异步处理

   private Handler myHandler = new Handler(){

           @Override
           public void handleMessage(Message msg) {
               switch (msg.what){
                   case MSG_SUCCESS:
                       mWaitting.setVisibility(View.GONE);
                       JSONObject rs  = (JSONObject) msg.obj;
                       prepareRsBitmap(rs);
                       mPhoto.setImageBitmap(mPhotoImg);
                       break;
                   case MSG_ERROR:
                       mWaitting.setVisibility(View.GONE);
                       String errorMsg = (String) msg.obj;
                       if (TextUtils.isEmpty(errorMsg)){
                           mTip.setText("确认是否联网");
                       }
                       else{
                           mTip.setText(errorMsg);
                       }
                       break;
               }
               super.handleMessage(msg);
           }
       };

   <br>
