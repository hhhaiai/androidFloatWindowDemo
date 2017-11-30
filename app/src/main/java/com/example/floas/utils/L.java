package com.example.floas.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Formatter;
import java.util.Locale;

import android.text.TextUtils;
import android.util.Log;

//import com.umeng.analytics.AnalyticsConstants;

/**
 * @Copyright © 2015 Umeng Inc. All rights reserved.
 * @Description: 支持更多种方式输出log
 * </pre>
 * 操作boolean变量<code>DEBUG</code>可以控制是否输出
 * </pre>
 * 操作boolean变量<code>TAG</code>可以控制打印的TAG,默认是MobclickAgent
 * @Version: 5.6.3
 * @Create: 2015年11月26日 下午4:13:48
 * @Author: sanbo
 */
public class L {

    private L() {
        /* cannot be instantiated */
    }

    /* 用户使用级别的log开关 */
    public static boolean DEBUG = true;
    private static String TAG = "sanbo";

    /**********************************************************************/
    @SuppressWarnings("resource")
    public static void i(Locale l, String format, Object... args) {
        try {
            String msg = new Formatter(l).format(format, args).toString();
            i(TAG, msg, null);
        } catch (Throwable e) {
            e(e);
        }
    }

    @SuppressWarnings("resource")
    public static void d(Locale l, String format, Object... args) {
        try {
            String msg = new Formatter(l).format(format, args).toString();
            d(TAG, msg, null);
        } catch (Throwable e) {
            e(e);
        }
    }

    @SuppressWarnings("resource")
    public static void e(Locale l, String format, Object... args) {
        try {
            String msg = new Formatter(l).format(format, args).toString();
            e(TAG, msg, null);
        } catch (Throwable e) {
            e(e);
        }
    }

    @SuppressWarnings("resource")
    public static void v(Locale l, String format, Object... args) {
        try {
            String msg = new Formatter(l).format(format, args).toString();
            v(TAG, msg, null);
        } catch (Throwable e) {
            e(e);
        }
    }

    @SuppressWarnings("resource")
    public static void w(Locale l, String format, Object... args) {
        try {
            String msg = new Formatter(l).format(format, args).toString();
            w(TAG, msg, null);
        } catch (Throwable e) {
            e(e);
        }
    }

    /**********************************************************************/
    public static void i(String format, Object... args) {
        try {
            String msg = "";
            if (format.contains("%")) {
                msg = new Formatter().format(format, args).toString();
                i(TAG, msg, null);
            } else {
                if (args != null) {
                    msg = (String)args[0];
                }
                i(format, msg, null);
            }
        } catch (Throwable e) {
            e(e);
        }
    }

    public static void d(String format, Object... args) {
        try {
            String msg = "";
            if (format.contains("%")) {
                msg = new Formatter().format(format, args).toString();
                d(TAG, msg, null);
            } else {
                if (args != null) {
                    msg = (String)args[0];
                }
                d(format, msg, null);
            }

        } catch (Throwable e) {
            e(e);
        }
    }

    public static void e(String format, Object... args) {
        try {
            String msg = "";
            if (format.contains("%")) {
                msg = new Formatter().format(format, args).toString();
                e(TAG, msg, null);
            } else {
                if (args != null) {
                    msg = (String)args[0];
                }
                e(format, msg, null);
            }
        } catch (Throwable e) {
            e(e);
        }
    }

    public static void v(String format, Object... args) {
        try {
            String msg = "";
            if (format.contains("%")) {
                msg = new Formatter().format(format, args).toString();
                v(TAG, msg, null);
            } else {
                if (args != null) {
                    msg = (String)args[0];
                }
                v(format, msg, null);
            }
        } catch (Throwable e) {
            e(e);
        }
    }

    public static void w(String format, Object... args) {
        try {
            String msg = "";
            if (format.contains("%")) {
                msg = new Formatter().format(format, args).toString();
                w(TAG, msg, null);
            } else {
                if (args != null) {
                    msg = (String)args[0];
                }
                w(format, msg, null);
            }

        } catch (Throwable e) {
            e(e);
        }
    }

    /************************************************************************/
    public static void i(Throwable e) {
        i(TAG, null, e);
    }

    public static void v(Throwable e) {
        v(TAG, null, e);
    }

    public static void w(Throwable e) {
        w(TAG, null, e);
    }

    public static void d(Throwable e) {
        d(TAG, null, e);
    }

    public static void e(Throwable e) {
        e(TAG, null, e);

    }

    /************************************************************************/
    public static void i(String msg, Throwable e) {
        i(TAG, msg, e);
    }

    public static void v(String msg, Throwable e) {
        v(TAG, msg, e);
    }

    public static void w(String msg, Throwable e) {
        w(TAG, msg, e);
    }

    public static void d(String msg, Throwable e) {
        d(TAG, msg, e);
    }

    public static void e(String msg, Throwable e) {
        e(TAG, msg, e);

    }

    /************************************************************************/

    public static void v(String msg) {
        v(TAG, msg, null);
    }

    public static void d(String msg) {
        d(TAG, msg, null);
    }

    public static void i(String msg) {
        i(TAG, msg, null);
    }

    public static void w(String msg) {
        w(TAG, msg, null);
    }

    public static void e(String msg) {
        e(TAG, msg, null);
    }

    /************************************************************************/

    public static void v(String tag, String msg, Throwable e) {
        if (DEBUG) {
            print(LEVEL_VERBOSE, tag, msg, e);
        }
    }

    public static void d(String tag, String msg, Throwable e) {
        if (DEBUG) {
            print(LEVEL_DEBUG, tag, msg, e);
        }
    }

    public static void i(String tag, String msg, Throwable e) {
        if (DEBUG) {
            print(LEVEL_INFO, tag, msg, e);
        }
    }

    public static void w(String tag, String msg, Throwable e) {
        if (DEBUG) {
            print(LEVEL_WARN, tag, msg, e);
        }
    }

    public static void e(String tag, String msg, Throwable e) {
        if (DEBUG) {
            print(LEVEL_ERROR, tag, msg, e);
        }
    }

    private static final int LEVEL_VERBOSE = 0x1;
    private static final int LEVEL_DEBUG = 0x2;
    private static final int LEVEL_INFO = 0x3;
    private static final int LEVEL_WARN = 0x4;
    private static final int LEVEL_ERROR = 0x5;
    private static int LOG_MAXLENGTH = 2000;

    private static void print(int level, String tag, String msg, Throwable e) {

        if (!TextUtils.isEmpty(msg)) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                // 剩下的文本还是大于规定长度则继续重复截取并输出
                if (strLength > end) {
                    switch (level) {
                        case LEVEL_DEBUG:
                            Log.d(tag, msg.substring(start, end));
                            break;
                        case LEVEL_INFO:
                            Log.i(tag, msg.substring(start, end));
                            break;
                        case LEVEL_ERROR:
                            Log.e(tag, msg.substring(start, end));
                            break;
                        case LEVEL_VERBOSE:
                            Log.v(tag, msg.substring(start, end));
                            break;
                        case LEVEL_WARN:
                            Log.w(tag, msg.substring(start, end));
                            break;

                        default:
                            break;
                    }
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    switch (level) {
                        case LEVEL_DEBUG:
                            Log.d(tag, msg.substring(start, strLength));
                            break;
                        case LEVEL_INFO:
                            Log.i(tag, msg.substring(start, strLength));
                            break;
                        case LEVEL_ERROR:
                            Log.e(tag, msg.substring(start, strLength));
                            break;
                        case LEVEL_VERBOSE:
                            Log.v(tag, msg.substring(start, strLength));
                            break;
                        case LEVEL_WARN:
                            Log.w(tag, msg.substring(start, strLength));
                            break;

                        default:
                            break;
                    }
                    break;
                }
            }
        }
        if (e != null) {

            String res = getStackTrace(e);
            if (!TextUtils.isEmpty(res)) {
                switch (level) {
                    case LEVEL_DEBUG:
                        Log.d(tag, res);
                        break;
                    case LEVEL_INFO:
                        Log.i(tag, res);
                        break;
                    case LEVEL_ERROR:
                        Log.e(tag, res);
                        break;
                    case LEVEL_VERBOSE:
                        Log.v(tag, res);
                        break;
                    case LEVEL_WARN:
                        Log.w(tag, res);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 将error转换成字符串
     */

    public static String getStackTrace(Throwable e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        String result = "";
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            result = sw.toString();
        } catch (Throwable error) {
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (Throwable e1) {
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return result;
    }
}
