package com.example.floas;

import android.app.Application;
import com.example.floas.utils.Content;

/**
 * @Copyright © 2017 Umeng Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2017/11/30 15:39
 * @author: safei
 * @mail: yongxu.xyf@alibaba-inc.com
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Content.init(this);
    }
}
