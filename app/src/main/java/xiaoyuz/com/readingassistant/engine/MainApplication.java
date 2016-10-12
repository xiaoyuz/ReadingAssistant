package xiaoyuz.com.readingassistant.engine;

import android.app.Application;
import android.view.WindowManager;

import xiaoyuz.com.readingassistant.utils.App;

/**
 * Created by zhangxiaoyu on 16-10-11.
 */
public class MainApplication extends Application {

    private WindowManager.LayoutParams mWindowParams = new WindowManager.LayoutParams();

    @Override
    public void onCreate() {
        super.onCreate();
        App.initialize(this);
    }

    public WindowManager.LayoutParams getWindowParams() {
        return mWindowParams;
    }
}
