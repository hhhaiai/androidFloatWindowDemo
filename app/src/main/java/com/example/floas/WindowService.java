package com.example.floas;

import java.util.List;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class WindowService extends Service {

    private final String TAG = this.getClass().getSimpleName();

    private WindowManager.LayoutParams wmParams;
    private WindowManager mWindowManager;
    private View mWindowView;
    private ImageView mPercentTv;

    private int mStartX;
    private int mStartY;
    private int mEndX;
    private int mEndY;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        initWindowParams();
        initView();
        addWindowView2Window();
        addLisener();
    }

    private void initWindowParams() {
        mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
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
    }

    private void initView() {
        mWindowView = LayoutInflater.from(getApplication()).inflate(R.layout.layout_window, null);
        mPercentTv = (ImageView)mWindowView.findViewById(R.id.imFloat);
    }

    private void addWindowView2Window() {
        mWindowManager.addView(mWindowView, wmParams);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWindowView != null) {
            // 移除悬浮窗口
            Log.i(TAG, "removeView");
            mWindowManager.removeView(mWindowView);
        }
        Log.i(TAG, "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void addLisener() {
        mPercentTv.setOnTouchListener(new View.OnTouchListener() {

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

        mPercentTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isAppAtBackground(WindowService.this)) {
                    Intent intent = new Intent(WindowService.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 是否拦截
     *
     * @return true:拦截;false:不拦截.
     */
    private boolean needIntercept() {
        if (Math.abs(mStartX - mEndX) > 30 || Math.abs(mStartY - mEndY) > 30) {
            return true;
        }
        return false;
    }

    /**
     * 判断当前应用程序处于前台还是后台
     */
    private boolean isAppAtBackground(final Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

}
