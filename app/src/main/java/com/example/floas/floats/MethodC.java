package com.example.floas.floats;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import com.example.floas.R;
import com.example.floas.utils.L;
import com.example.floas.utils.PermissionUtils;

/**
 * @Copyright © 2017 Umeng Inc. All rights reserved.
 * @Description: 主要是兼容7.x以上版本(24以上). 其实就是动态申请权限的MethodA
 * @Version: 1.0
 * @Create: 2017/11/30 17:00
 * @author: safei
 * @mail: yongxu.xyf@alibaba-inc.com
 */
public class MethodC {

    public static boolean show(Context context) {
        if (mWindowManager == null
            || mLayoutParams == null
            || mWindowView == null
            ) {
            initView(context);
        }

        //如果没有权限,动态授权
        if (!PermissionUtils.hasPermission(context)) {
            Intent intent = new Intent(context, PermissionUtils.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        if (PermissionUtils.hasPermission(context)) {
            mWindowManager.addView(mWindowView, mLayoutParams);
        }
        return true;
    }

    public static boolean hide(Context context) {

        if (mWindowView != null) {
            // 移除悬浮窗口
            L.i("MethodC hide");
            try {
                mWindowManager.removeView(mWindowView);
            } catch (java.lang.IllegalArgumentException e) {
                L.i("已经隐藏了");
            } catch (Throwable e) {
                L.e(e);
            }
        }
        return true;
    }

    private static WindowManager mWindowManager = null;
    private static WindowManager.LayoutParams mLayoutParams = null;
    private static View mWindowView = null;

    private static void initView(Context context) {
        mWindowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();

        // 更多type：https://developer.android.com/reference/android/view/WindowManager.LayoutParams.html#TYPE_PHONE
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mLayoutParams.format = PixelFormat.TRANSLUCENT;
        // 更多falgs:https://developer.android.com/reference/android/view/WindowManager.LayoutParams
        // .html#FLAG_NOT_FOCUSABLE
        //mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mWindowView = LayoutInflater.from(context).inflate(R.layout.layout_window, null);
    }
}
