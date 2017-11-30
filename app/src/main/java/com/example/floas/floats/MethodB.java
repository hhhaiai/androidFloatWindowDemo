package com.example.floas.floats;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import com.example.floas.R;
import com.example.floas.utils.L;

/**
 * @Copyright © 2017 sanbo Inc. All rights reserved.
 * @Description: toast实现, 拖动有问题. 4.4~7.0 无需权限可点击
 * @Version: 1.0
 * @Create: 2017/11/30 16:28
 * @author: safei
 * @mail: yongxu.xyf@alibaba-inc.com
 */
public class MethodB {

    private static Toast toast = null;

    private static Object mTN = null;
    private static Method show = null;
    private static Method hide = null;

    public static boolean show(Context context) {
        try {
            if (show == null || mTN == null) {
                initView(context);
            } else {
                initTN();
            }
            show.invoke(mTN);
        } catch (Throwable e) {
            L.e(e);
            return false;
        }
        return true;
    }

    public static boolean hide(Context context) {
        try {
            if (hide == null || mTN == null) {
                initView(context);
            }
            hide.invoke(mTN);
        } catch (Throwable e) {
            L.e(e);
            return false;
        }
        return true;
    }

    private static WindowManager.LayoutParams params = null;
    private static WindowManager mWindowManager = null;
    private static View mWindowView = null;
    private static int mStartX = 0;
    private static int mStartY = 0;
    private static int mEndX = 0;
    private static int mEndY = 0;

    private static void initView(Context context) {
        if (toast == null) {
            toast = new Toast(context);
        }
        if (context == null) {
            L.e("MethodA.initView Context is null");
            return;
        }
        mWindowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        //params = new WindowManager.LayoutParams();
        //// 更多type：https://developer.android.com/reference/android/view/WindowManager.LayoutParams.html#TYPE_PHONE
        //params.type = WindowManager.LayoutParams.TYPE_PHONE;
        //params.format = PixelFormat.TRANSLUCENT;
        //// 更多falgs:https://developer.android.com/reference/android/view/WindowManager.LayoutParams
        //// .html#FLAG_NOT_FOCUSABLE
        //params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //params.gravity = Gravity.LEFT | Gravity.TOP;
        //params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mWindowView = LayoutInflater.from(context).inflate(R.layout.layout_window, null);
        //ImageView iv = (ImageView)mWindowView.findViewById(R.id.imFloat);
        addLisener(mWindowView);

        toast.setView(mWindowView);
        initTN();
    }

    /**
     * 利用反射设置 toast 参数
     */
    private static void initTN() {
        try {
            Field tnField = toast.getClass().getDeclaredField("mTN");
            tnField.setAccessible(true);
            mTN = tnField.get(toast);
            show = mTN.getClass().getMethod("show");
            hide = mTN.getClass().getMethod("hide");

            Field tnParamsField = mTN.getClass().getDeclaredField("mParams");
            tnParamsField.setAccessible(true);
            //移动会出错
            //moveError(tnParamsField);
            //不能移动
            cannotMove();

            Field tnNextViewField = mTN.getClass().getDeclaredField("mNextView");
            tnNextViewField.setAccessible(true);
            tnNextViewField.set(mTN, toast.getView());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void moveError(Field tnParamsField) throws IllegalAccessException {
        params = (WindowManager.LayoutParams)tnParamsField.get(mTN);
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 更多type：https://developer.android.com/reference/android/view/WindowManager.LayoutParams.html#TYPE_PHONE
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.TRANSLUCENT;
        // 更多falgs:https://developer.android.com/reference/android/view/WindowManager.LayoutParams
        // .html#FLAG_NOT_FOCUSABLE
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    private static void cannotMove() {
        params = new WindowManager.LayoutParams();
        // 更多type：https://developer.android.com/reference/android/view/WindowManager.LayoutParams.html#TYPE_PHONE
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.TRANSLUCENT;
        // 更多falgs:https://developer.android.com/reference/android/view/WindowManager.LayoutParams
        // .html#FLAG_NOT_FOCUSABLE
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    static void addLisener(View v) {
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        L.i("MethodB action down");
                        mStartX = (int)event.getRawX();
                        mStartY = (int)event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        L.i("MethodB action move");
                        mEndX = (int)event.getRawX();
                        mEndY = (int)event.getRawY();
                        if (needIntercept()) {
                            // getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                            params.x = (int)event.getRawX() - mWindowView.getMeasuredWidth() / 2;
                            params.y = (int)event.getRawY() - mWindowView.getMeasuredHeight() / 2;
                            mWindowManager.updateViewLayout(mWindowView, params);
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        L.i("MethodB action up");
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
