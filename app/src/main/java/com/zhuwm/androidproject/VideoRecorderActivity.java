package com.zhuwm.androidproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.zhuwm.androidproject.camera.CameraPreview;

public class VideoRecorderActivity extends Activity {
    private static String TAG = "VideoRecorderActivity";

    private Camera mCamera1;
    private Camera mCamera2;

    private CameraPreview mPreview1;
    private CameraPreview mPreview2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_recorder);

        //如果有摄像头
        if (checkCameraHardware(this) == true) {
            Log.d(TAG, "有摄像头");
            //检查有几个摄像头
            int cameraNumbers = Camera.getNumberOfCameras();
            Log.d(TAG, "摄像头数:" + cameraNumbers);

            //显示预览画面
            mCamera1 = getCameraInstance(0);
            mPreview1 =new CameraPreview(this, mCamera1);
            FrameLayout preview1 =(FrameLayout) findViewById(R.id.camera_preview1);
            preview1.addView(mPreview1);

            int para_cameranumbers=getIntent().getIntExtra("cameraNumbers",0);
            if(para_cameranumbers==2){
                Log.d(TAG,"启动两个摄像头");
                //显示预览画面
                mCamera2 = getCameraInstance(1);
                mPreview2 =new CameraPreview(this, mCamera2);
                FrameLayout preview2 =(FrameLayout) findViewById(R.id.camera_preview2);
                preview2.addView(mPreview2);
            }

        } else {
            showDialog("出错了", "找不到摄像头");
        }
    }



    //打开摄像头
    public static Camera getCameraInstance(int cameraId) {
        Camera c = null;
        try {
            c = Camera.open(cameraId);//试图获取Camera实例
        } catch (Exception e) {
            //摄像头不可用（正被占用或不存在）
        }
        return c;//不可用则返回null
    }

    //检查设备
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            //摄像头存在
            return true;
        } else {
            //摄像头不存在
            return false;
        }

    }

    private void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(VideoRecorderActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定", null);
        builder.show();
    }

    @Override
    protected void onStop() {

        super.onStop();
        Log.d(TAG,"onStop，释放摄像头");
        if (mCamera1!=null){
            mCamera1.release();
        }
        if(mCamera2!=null){
            mCamera2.release();
        }
    }
}
