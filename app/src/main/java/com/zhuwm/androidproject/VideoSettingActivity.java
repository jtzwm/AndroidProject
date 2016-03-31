package com.zhuwm.androidproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;
import com.zhuwm.androidproject.po.ServerSetting;
import com.zhuwm.androidproject.util.EditTextUtil;

public class VideoSettingActivity extends Activity implements AnyChatBaseEvent {

    private String TAG = "VideoSettingActivity";

    private ServerSetting setting = new ServerSetting();
    ;
    public AnyChatCoreSDK anyChatSDK;


    private final int LOCALVIDEOAUTOROTATION = 1; // 本地视频自动旋转控制

    private Button btnStartVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "==========进入了VideoSettingActivity");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_setting);

        //获取界面输入的服务器信息
        getSetting();
        //初始化界面、绑定事件
        initLayout();
        //初始化AnyChat
        initSDK();
    }

    private void initLayout() {
        findViewById(R.id.btnConnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connAnyChat();
                Log.d(TAG,"============连接服务器成功");


            }
        });

        findViewById(R.id.btnEnterRoom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anyChatSDK.EnterRoom(Integer.parseInt(setting.getRoomID()), "");
                Log.d(TAG, "============进入房间成功");

            }
        });

    }

    private void connAnyChat() {
        anyChatSDK.Connect(setting.getServerAddr(), setting.getServerPort());
        anyChatSDK.Login("admin", "");
        Log.d(TAG, "============V" + anyChatSDK.GetSDKMainVersion() + "."
                + anyChatSDK.GetSDKSubVersion() + "  Build time: "
                + anyChatSDK.GetSDKBuildTime());
    }

    /**
     * 初始化Anychat
     */
    private void initSDK() {
        if (anyChatSDK == null) {
            anyChatSDK = AnyChatCoreSDK.getInstance(this);
            anyChatSDK.SetBaseEvent(this);
            anyChatSDK.InitSDK(android.os.Build.VERSION.SDK_INT, 0);
            AnyChatCoreSDK.SetSDKOptionInt(
                    AnyChatDefine.BRAC_SO_LOCALVIDEO_AUTOROTATION,
                    LOCALVIDEOAUTOROTATION);
        }

        Log.d(TAG,"============V" + anyChatSDK.GetSDKMainVersion() + "."
                + anyChatSDK.GetSDKSubVersion() + "  Build time: "
                + anyChatSDK.GetSDKBuildTime());
    }

    /**
     * 获取设置
     */
    private void getSetting() {

        setting.setServerAddr(EditTextUtil.getEditTextByID(R.id.editServerAdd, this));

        setting.setServerPort(Integer.parseInt(EditTextUtil.getEditTextByID(R.id.editServerPort, this)));
        setting.setRoomID(EditTextUtil.getEditTextByID(R.id.editRoomID, this));

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
