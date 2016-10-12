package xiaoyuz.com.readingassistant.engine;

import android.app.Application;

import xiaoyuz.com.readingassistant.utils.App;

/**
 * Created by zhangxiaoyu on 16-10-11.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        App.initialize(this);
    }
}
