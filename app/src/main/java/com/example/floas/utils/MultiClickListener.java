package com.example.floas.utils;

import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @Copyright © 2017 Umeng Inc. All rights reserved.
 * @Description: 多次点击事件.
 * @Version: 1.0
 * @Create: 2017/11/21 10:38
 * @author: safei
 * @mail: yongxu.xyf@alibaba-inc.com
 */
public class MultiClickListener {

    /**
     * 三击
     */
    private static long[] mHits = new long[3];

    public static final OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
            mHits[mHits.length - 1] = SystemClock.uptimeMillis();//获取手机开机时间
            if (mHits[mHits.length - 1] - mHits[0] < 500) {
                /**双击的业务逻辑*/
                L.e("三击");
                //Intent i = new Intent(MyApplication.getContext(), SettingActivity.class);
                ////非activity启动页面需要添加这个flug
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ////i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //MyApplication.getContext().startActivity(i);
            }
        }
    };
}
