package com.zhuwm.androidproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalVideoExpandActivity extends Activity {


    ExpandableListView mainlistview = null;
    List<String> parent = null;
    Map<String, List<String>> map = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_video_expand);

        mainlistview = (ExpandableListView) this
                .findViewById(R.id.custumers_expandablelistview);

        initData();
        mainlistview.setAdapter(new MyAdapter());
    }

    public final class ViewHolder{

        public TextView title;

        public Button viewBtn;
    }

    class MyAdapter extends BaseExpandableListAdapter {

        //得到子item需要关联的数据
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            String key = parent.get(groupPosition);
            return (map.get(key).get(childPosition));
        }

        //得到子item的ID
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        //设置子item的组件
        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            String key = LocalVideoExpandActivity.this.parent.get(groupPosition);
            String info = map.get(key).get(childPosition);
/*            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) LocalVideoExpandActivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.activity_local_video_expand_child, null);
            }*/


            ViewHolder holder = null;
            if (convertView == null) {
                holder=new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) LocalVideoExpandActivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.activity_local_video_expand_child, null);

                holder.title = (TextView)convertView.findViewById(R.id.second_textview);

                holder.viewBtn = (Button)convertView.findViewById(R.id.second_textview_btn);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.title.setText(info);
            holder.viewBtn.setText("开始上传");
/*            TextView tv = (TextView) convertView
                    .findViewById(R.id.second_textview);
            tv.setText(info);

            Button btn=(Button)convertView.findViewById(R.id.second_textview_btn);
            btn.setText("上传");*/
            return convertView;
        }

        //获取当前父item下的子item的个数
        @Override
        public int getChildrenCount(int groupPosition) {
            String key = parent.get(groupPosition);
            int size=map.get(key).size();
            return size;
        }
        //获取当前父item的数据
        @Override
        public Object getGroup(int groupPosition) {
            return parent.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return parent.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }
        //设置父item组件
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) LocalVideoExpandActivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.activity_local_video_expand_parent, null);
            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.parent_textview);
            tv.setText(LocalVideoExpandActivity.this.parent.get(groupPosition));
            return tv;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

    public void initData() {
        parent = new ArrayList<String>();
        parent.add("朱巍明");
        parent.add("黄瑞庆");
        parent.add("刘天亮");

        map = new HashMap<String, List<String>>();

        List<String> list1 = new ArrayList<String>();
        list1.add("视频长度13秒");
        list1.add("视频长度13秒");
        map.put("朱巍明", list1);

        List<String> list2 = new ArrayList<String>();
        list2.add("视频长度23秒");
        list2.add("视频长度23秒");
        map.put("黄瑞庆", list2);

        List<String> list3 = new ArrayList<String>();
        list3.add("视频长度33秒");
        map.put("刘天亮", list3);

    }
}
