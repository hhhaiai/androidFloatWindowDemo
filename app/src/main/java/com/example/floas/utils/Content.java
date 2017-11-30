package com.example.floas.utils;

import android.content.Context;

/**
 * @Copyright © 2017 Umeng Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2017/11/23 15:25
 * @author: safei
 * @mail: yongxu.xyf@alibaba-inc.com
 */
public class Content {
    public static final String INOUT_IN = "0";
    public static final String INOUT_OUT = "1";
    public static final String SP_IP = "sp_ip";
    public static Context CONTEXT;

    public static void init(Context context) {
        CONTEXT = context.getApplicationContext();
    }

}
