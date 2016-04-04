package com.zhuwm.androidproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;
import com.zhuwm.androidproject.adapter.RoleListAdapter;
import com.zhuwm.androidproject.po.RoleInfo;
import com.zhuwm.androidproject.po.ServerSetting;
import com.zhuwm.androidproject.util.EditTextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VideoSettingActivity extends Activity implements AnyChatBaseEvent {

    private String TAG = "VideoSettingActivity";

    private ServerSetting setting = new ServerSetting();

    public AnyChatCoreSDK anyChatSDK;


    private final int LOCALVIDEOAUTOROTATION = 1; // 本地视频自动旋转控制

    private Button btnStartVideo;
    private ListView mRoleList;

    private List<RoleInfo> mRoleInfoList = new ArrayList<RoleInfo>();

    private int userSelfID;
    private RoleListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "==========进入了VideoSettingActivity");
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
        mRoleList = (ListView) this.findViewById(R.id.roleListView);

        findViewById(R.id.btnConnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSetting();
                connAnyChat();
                Log.i(TAG, "============连接服务器成功");


            }
        });

        findViewById(R.id.btnEnterRoom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSetting();
                anyChatSDK.EnterRoom(Integer.parseInt(setting.getRoomID()), "");
                Log.i(TAG, "============进入房间成功,房间号为：" + setting.getRoomID());

            }
        });

    }

    private void connAnyChat() {
        anyChatSDK.Connect(setting.getServerAddr(), setting.getServerPort());
        anyChatSDK.Login("admin", "");
        Log.i(TAG, "============V" + anyChatSDK.GetSDKMainVersion() + "."
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

        Log.i(TAG, "============V" + anyChatSDK.GetSDKMainVersion() + "."
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
        //登录后，返回的事件。从这个方法，能够获得dwUserId.
        //如果要实现登录后自动进入房间，可以在这里实现。
        Log.i(TAG, "%%%%%%  自己登录成功，userid：" + dwUserId);
        //setting.setUserID(Integer.toString(dwUserId));
        userSelfID=dwUserId;
    }

    @Override
    public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
        Log.i(TAG, "&&&&&&&&&  有人进入了房间(包括自己)，房间号是：" + dwRoomId);

    }

    @Override
    public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
        Log.i(TAG, "=========本地保存的ID是：" + userSelfID + ",事件回调的ID是：" + dwUserNum);
        Log.i(TAG, "=========有用户进入房间，用户号（包括自己）为：" + dwUserNum);
/*        Intent intent = new Intent();
        intent.putExtra("userID","-10");
        intent.setClass(VideoSettingActivity.this, VideoActivity.class);
        startActivityForResult(intent,1);*/
        updateUserList();


    }

    @Override
    public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {


    }

    @Override
    public void OnAnyChatLinkCloseMessage(int dwErrorCode) {

    }


    private void updateUserList() {
        mRoleInfoList.clear();
        int[] userID = anyChatSDK.GetOnlineUser();

        Log.i(TAG,"有用户进入房间，当前取到在线用户数为："+userID.length);
        RoleInfo userselfInfo = new RoleInfo();
        userselfInfo.setName(anyChatSDK.GetUserName(userSelfID)
                + "(自己) 【点击可设置】");
        userselfInfo.setUserID(String.valueOf(userSelfID));
        userselfInfo.setRoleIconID(getRoleRandomIconID());
        mRoleInfoList.add(userselfInfo);

        for (int index = 0; index < userID.length; ++index) {
            RoleInfo info = new RoleInfo();
            info.setName(anyChatSDK.GetUserName(userID[index]));
            info.setUserID(String.valueOf(userID[index]));
            info.setRoleIconID(getRoleRandomIconID());
            mRoleInfoList.add(info);
        }

        mAdapter = new RoleListAdapter(VideoSettingActivity.this, mRoleInfoList);
        mRoleList.setAdapter(mAdapter);
        mRoleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Log.i("TAG","已经进入列表，当前点击的是列表第"+arg2+"条，选择的用户ID是："+mRoleInfoList.get(arg2).getUserID());
                if (arg2 == 0) {//自己
                    Intent intent = new Intent();
                    intent.setClass(VideoSettingActivity.this, VideoActivity.class);
                    startActivityForResult(intent, 1);
                    return;
                }else {
                    String strUserID = mRoleInfoList.get(arg2).getUserID();
                    Intent intent = new Intent();
                    intent.putExtra("userID", strUserID);
                    intent.setClass(VideoSettingActivity.this, VideoActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private int getRoleRandomIconID() {
        int number = new Random().nextInt(5) + 1;
        if (number == 1) {
            return R.drawable.role_1;
        } else if (number == 2) {
            return R.drawable.role_2;
        } else if (number == 3) {
            return R.drawable.role_3;
        } else if (number == 4) {
            return R.drawable.role_4;
        } else if (number == 5) {
            return R.drawable.role_5;
        }

        return R.drawable.role_1;
    }
}
