package xiaoyuz.com.readingassistant.utils;

import android.content.Context;
import android.view.WindowManager;

import xiaoyuz.com.readingassistant.cache.ACache;

/**
 * Created by zhangxiaoyu on 16-10-11.
 */
public class App {

    private static Context sAppContext;
    private static ACache sACache;
    private static WindowManager.LayoutParams sWindowParams;

    public static void initialize(Context context) {
        sAppContext = context;
    }

    public static boolean isInitialized() {
        return sAppContext != null;
    }

    public static Context getContext() {
        return sAppContext;
    }

    public static ACache getACache() {
        if (sACache == null) {
            sACache = ACache.get(getContext());
        }
        return sACache;
    }

    public static WindowManager.LayoutParams getWindowParams() {
        if (sWindowParams == null) {
            sWindowParams = new WindowManager.LayoutParams();
        }
        return sWindowParams;
    }
}