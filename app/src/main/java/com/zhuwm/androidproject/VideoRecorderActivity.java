package com.zhuwm.androidproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class VideoRecorderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_recorder);

        //如果有摄像头
        if (checkCameraHardware(this) == true) {

        } else {
            showDialog();
        }
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

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(VideoRecorderActivity.this);
        builder.setTitle("出错了");
        builder.setMessage("找不到摄像头");
        builder.setPositiveButton("确定", null);
        builder.show();
    }
}
