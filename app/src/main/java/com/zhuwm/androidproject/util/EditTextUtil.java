package com.zhuwm.androidproject.util;

import android.app.Activity;
import android.widget.EditText;

import com.zhuwm.androidproject.R;

/**
 * Created by zhuweiming on 2016/3/31.
 */
public  class EditTextUtil {

    /**
     * 获取EditText的文本
     * @param id
     * @param activity
     * @return
     */
    public static  String getEditTextByID(int id,Activity activity){
        EditText temp =(EditText)activity.findViewById(id);
        if(temp != null){
            return temp.getText().toString();
        }
        return null;

    }
}
