package com.zhuwm.androidproject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.hardware.camera2.CameraAccessException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.camera.SCamera;
import com.samsung.android.sdk.camera.SCameraCharacteristics;
import com.samsung.android.sdk.camera.SCameraDevice;
import com.samsung.android.sdk.camera.SCameraManager;
import com.samsung.android.sdk.camera.processor.SCameraProcessorManager;

public class SamsungCameraActivity extends Activity {

    private SCamera mSCamera;
    private SCameraManager mSCameraManager;
    private String mCameraId;
    private Handler mBackgroundHandler;
    private static String TAG = "SamsungCameraActivity";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samsung_camera);

        mSCamera = new SCamera();
        try {
            mSCamera.initialize(this);


        } catch (SsdkUnsupportedException e) {
            Log.d(TAG, "出错了");
        }
        mSCameraManager = mSCamera.getSCameraManager();
        mCameraId = "0";

        try {
            mSCameraManager.openCamera(mCameraId, new SCameraDevice.StateCallback() {
                public void onOpened(SCameraDevice sCameraDevice) {
    /*                mCameraOpenCloseLock.release();
                    if (getState() == CAMERA_STATE.CLOSING)
                        return;
                    mSettingDialog.dismiss();
                    mSCameraDevice = sCameraDevice;
                    createPreviewSession();*/
                }

                @Override
                public void onDisconnected(SCameraDevice sCameraDevice) {
    /*                mCameraOpenCloseLock.release();
                    if (getState() == CAMERA_STATE.CLOSING)
                        return;
                    showAlertDialog("Camera disconnected.", true);*/
                }

                @Override
                public void onError(SCameraDevice sCameraDevice, int i) {
    /*                mCameraOpenCloseLock.release();
                    if (getState() == CAMERA_STATE.CLOSING)
                        return;
                    showAlertDialog("Error while camera open.", true);*/
                }
            }, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }
}