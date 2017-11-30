package com.example.floas.floats;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import com.example.floas.R;
import com.example.floas.utils.L;

/**
 * @Copyright © 2017 Umeng Inc. All rights reserved.
 * @Description: 兼容性不强
 * @Version: 1.0
 * @Create: 2017/11/24 17:51
 * @author: safei
 * @mail: yongxu.xyf@alibaba-inc.com
 */
public class MethodA {

    private static WindowManager.LayoutParams wmParams = null;
    private static WindowManager mWindowManager = null;
    private static View mWindowView = null;

    private static int mStartX = 0;
    private static int mStartY = 0;
    private static int mEndX = 0;
    private static int mEndY = 0;

    //update   https://github.com/yhaolpz/FixedFloatWindow
    static void initView(Context context) {
        if (context == null) {
            L.e("MethodA.initView Context is null");
            return;
        }
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
        //ImageView iv = (ImageView)mWindowView.findViewById(R.id.imFloat);
        addLisener(mWindowView);

        mWindowManager.addView(mWindowView, wmParams);
    }

    public static boolean show(Context context) {
        initView(context);
        return true;
    }

    public static boolean hide(Context context) {
        if (mWindowView != null) {
            // 移除悬浮窗口
            L.i("MethodA hide");
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