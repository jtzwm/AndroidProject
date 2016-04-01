package com.zhuwm.androidproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhuwm.androidproject.R;
import com.zhuwm.androidproject.VideoSettingActivity;
import com.zhuwm.androidproject.po.RoleInfo;

import java.util.List;

/**
 * Created by zhuweiming on 2016/4/1.
 */
public class RoleListAdapter extends BaseAdapter {
    private Context mContext;
    private List<RoleInfo> mRoleList;

    public RoleListAdapter(Context context, List<RoleInfo> roleInfos) {
        super();
        mContext = context;
        mRoleList = roleInfos;
    }

    @Override
    public int getCount() {
        return mRoleList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRoleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.role_list, null);
            holder.mname = (TextView) convertView.findViewById(R.id.mname);
            holder.mRoleID = (TextView) convertView.findViewById(R.id.mRoleID);
            holder.mRoleIcon = (ImageView) convertView.findViewById(R.id.roleHeaderImg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        RoleInfo info = mRoleList.get(position);
        holder.mname.setText("" + info.getName());
        holder.mRoleID.setText("" + info.getUserID());
        holder.mRoleIcon.setImageResource(info.getRoleIconID());
        return convertView;
    }

    public class ViewHolder {
        TextView mname;
        TextView mRoleID;
        ImageView mRoleIcon;
    }
}
