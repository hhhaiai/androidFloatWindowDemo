package com.example.floas.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.example.floas.R;

/**
 * @Copyright © 2017 Umeng Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2017/11/24 17:51
 * @author: safei
 * @mail: yongxu.xyf@alibaba-inc.com
 */
public class Method1 {

    private static WindowManager.LayoutParams wmParams;
    private static WindowManager mWindowManager;
    private static View mWindowView;

    private static int mStartX = 0;
    private static int mStartY = 0;
    private static int mEndX = 0;
    private static int mEndY = 0;

    //update   https://github.com/yhaolpz/FixedFloatWindow
    public static void initView(Context context) {
        mWindowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();
        // 更多type：https://developer.android.com/reference/android/view/WindowManager.LayoutParams.html#TYPE_PHONE
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.TRANSLUCENT;
        // 更多falgs:https://developer.android.com/reference/android/view/WindowManager.LayoutParams
        // .html#FLAG_NOT_FOCUSABLE
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mWindowView = LayoutInflater.from(context).inflate(R.layout.layout_window, null);
        ImageView iv = (ImageView)mWindowView.findViewById(R.id.imFloat);
        addLisener(iv);
    }

    static void addLisener(View v) {
        v.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartX = (int)event.getRawX();
                        mStartY = (int)event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mEndX = (int)event.getRawX();
                        mEndY = (int)event.getRawY();
                        if (needIntercept()) {
                            // getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                            wmParams.x = (int)event.getRawX() - mWindowView.getMeasuredWidth() / 2;
                            wmParams.y = (int)event.getRawY() - mWindowView.getMeasuredHeight() / 2;
                            mWindowManager.updateViewLayout(mWindowView, wmParams);
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (needIntercept()) {
                            return true;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 是否拦截
     *
     * @return true:拦截;false:不拦截.
     */
    private static boolean needIntercept() {
        if (Math.abs(mStartX - mEndX) > 30 || Math.abs(mStartY - mEndY) > 30) {
            return true;
        }
        return false;
    }

}