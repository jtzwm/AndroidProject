package com.zhuwm.androidproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;

public class VideoActivity extends Activity implements AnyChatBaseEvent {

    private int userID;
    private AnyChatCoreSDK anychatSDK;
    private String TAG = "VideoActivity";

    private SurfaceView mMyView;
    private SurfaceView mOtherView;
    private Button mEndCallBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();

        userID = Integer.parseInt(intent.getStringExtra("userID"));
        Log.i(TAG, "进入VideoActivity，取到用户ID:" + userID);

        initSDK();
        initLayout();
    }

    private void initSDK() {
        anychatSDK = new AnyChatCoreSDK();
        anychatSDK.SetBaseEvent(this);
        anychatSDK.mSensorHelper.InitSensor(this);
        AnyChatCoreSDK.mCameraHelper.SetContext(this);
    }

    private void initLayout() {
        setContentView(R.layout.activity_video);

        mMyView = (SurfaceView) findViewById(R.id.surface_local);
        mOtherView = (SurfaceView) findViewById(R.id.surface_remote);
        mEndCallBtn = (Button) findViewById(R.id.endCall);
        mEndCallBtn.setOnClickListener(onClickListener);


        // 如果是采用Java视频采集，则需要设置Surface的CallBack
        if (AnyChatCoreSDK
                .GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER) == AnyChatDefine.VIDEOCAP_DRIVER_JAVA) {
            mMyView.getHolder().addCallback(AnyChatCoreSDK.mCameraHelper);
        }

        // 如果是采用Java视频显示，则需要设置Surface的CallBack
        if (AnyChatCoreSDK
                .GetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL) == AnyChatDefine.VIDEOSHOW_DRIVER_JAVA) {
            int index = anychatSDK.mVideoHelper.bindVideo(mOtherView
                    .getHolder());
            anychatSDK.mVideoHelper.SetVideoUser(index, userID);
        }

        anychatSDK.UserCameraControl(userID, 1);
        anychatSDK.UserSpeakControl(userID, 1);


        anychatSDK.UserCameraControl(-1, 1);// -1表示对本地视频进行控制，打开本地视频
        anychatSDK.UserSpeakControl(-1, 1);// -1表示对本地音频进行控制，打开本地音频


    }




    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case (R.id.ImgSwichVideo): {

                    // 如果是采用Java视频采集，则在Java层进行摄像头切换
                    if (AnyChatCoreSDK
                            .GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER) == AnyChatDefine.VIDEOCAP_DRIVER_JAVA) {
                        AnyChatCoreSDK.mCameraHelper.SwitchCamera();
                        return;
                    }

                    String strVideoCaptures[] = anychatSDK.EnumVideoCapture();
                    String temp = anychatSDK.GetCurVideoCapture();
                    for (int i = 0; i < strVideoCaptures.length; i++) {
                        if (!temp.equals(strVideoCaptures[i])) {
                            anychatSDK.UserCameraControl(-1, 0);
                            //bSelfVideoOpened = false;
                            anychatSDK.SelectVideoCapture(strVideoCaptures[i]);
                            anychatSDK.UserCameraControl(-1, 1);
                            break;
                        }
                    }
                }
                break;
                case (R.id.endCall): {
                    exitVideoDialog();
                }

                default:
                    break;
            }
        }
    };


    private void exitVideoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                destroyCurActivity();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }


    private void destroyCurActivity() {
        onPause();
        onDestroy();
    }

    @Override
    public void OnAnyChatConnectMessage(boolean bSuccess) {

    }

    @Override
    public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {

    }

    @Override
    public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {

    }

    @Override
    public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {

    }

    @Override
    public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {

    }

    @Override
    public void OnAnyChatLinkCloseMessage(int dwErrorCode) {

    }
}
