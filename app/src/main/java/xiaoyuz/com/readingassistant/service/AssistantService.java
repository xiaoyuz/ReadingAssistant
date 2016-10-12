package xiaoyuz.com.readingassistant.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.FrameLayout;

import xiaoyuz.com.readingassistant.ui.DraggableFrameLayout;
import xiaoyuz.com.readingassistant.utils.App;

/**
 * Created by zhangxiaoyu on 16-10-12.
 * This service running in background shall handle all reading assistance request.
 */
public class AssistantService extends Service {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;
    private FrameLayout mFloatLayout;

    @Override
    public void onCreate() {
        super.onCreate();
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mParams = App.getWindowParams();
        mFloatLayout = new DraggableFrameLayout(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFlowWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWindowManager.removeView(mFloatLayout);
    }

    public void showFlowWindow() {
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mParams.format = PixelFormat.RGBA_8888;
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.x = 300;
        mParams.y = 300;

        mWindowManager.addView(mFloatLayout, mParams);
    }
}
