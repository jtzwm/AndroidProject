package com.zhuwm.androidproject;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuwm.androidproject.adapter.RoleListAdapter;
import com.zhuwm.androidproject.util.MediaFilesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalVideoActivity extends ListActivity {

    private static String TAG="LocalVideoActivity";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;

    private Uri fileUri;
    private List<Map<String, Object>> mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mData = getData();
        MyAdapter adapter = new MyAdapter(this);
        setListAdapter(adapter);
    }

    private List<Map<String,Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "朱巍明");
        map.put("info", "集合项目计划第1次发行");
        map.put("img", R.drawable.resizeapi);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "黄瑞庆");
        map.put("info", "集合项目计划第1次发行");
        map.put("img", R.drawable.resizeapi);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "刘天亮");
        map.put("info", "集合项目计划第1次发行");
        map.put("img", R.drawable.resizeapi);
        list.add(map);

        return list;
    }

    public final class ViewHolder{
        public ImageView img;
        public TextView title;
        public TextView info;
        public Button viewBtn;
        public Button viewBtnSDK;
        public Button viewBtnSDK2Camera;

    }

    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;


        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
               return mData.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder=new ViewHolder();
                convertView = mInflater.inflate(R.layout.activity_local_video, null);
                holder.img = (ImageView)convertView.findViewById(R.id.img);
                holder.title = (TextView)convertView.findViewById(R.id.title);
                holder.info = (TextView)convertView.findViewById(R.id.info);
                holder.viewBtn = (Button)convertView.findViewById(R.id.view_btn);
                holder.viewBtnSDK = (Button)convertView.findViewById(R.id.view_btn_sdk);
                holder.viewBtnSDK2Camera = (Button)convertView.findViewById(R.id.view_btn_sdk_2camera);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.img.setBackgroundResource((Integer)mData.get(position).get("img"));
            holder.title.setText((String)mData.get(position).get("title"));
            //holder.info.setText((String)mData.get(position).get("info"));

            holder.viewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //通过intent调用activity
                    startVideo();
                }
            });
            holder.viewBtnSDK.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    //通过sdk直接调用摄像头。参数只能传1，如果传2、原生sdk不支持的
                    startVideoBySDK(1);
                }
            });

            holder.viewBtnSDK2Camera.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    //通过sdk直接调用摄像头
                    startSamsungCarema();
                }
            });
            return convertView;
        }

    }

    private void startSamsungCarema(){
        startActivity(new Intent(LocalVideoActivity.this,SamsungCameraActivity.class));
    }

    private void startVideoBySDK(int cameraNumbers){
        Intent intent =new Intent(LocalVideoActivity.this, VideoRecorderActivity.class);
        intent.putExtra("cameraNumbers",cameraNumbers);
        startActivity(intent);
    }

    private void startVideo(){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        fileUri= MediaFilesUtil.getOutputMediaFileUri(MediaFilesUtil.MEDIA_TYPE_VIDEO);
        Log.d(TAG,"onCreate:视频文件为："+fileUri.getPath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,"resultCode="+resultCode);
        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                Log.d(TAG,"视频录制成功"+data.getData());
                // 捕获的视频保存到Intent指定的fileUri
                Toast.makeText(this, "Video saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // 用户取消了视频捕获
            } else {
                // 视频捕获失败，提示用户
            }
        }
    }
}
