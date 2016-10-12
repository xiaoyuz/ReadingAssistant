package xiaoyuz.com.readingassistant.ui;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import xiaoyuz.com.readingassistant.R;
import xiaoyuz.com.readingassistant.utils.App;

/**
 * Created by zhangxiaoyu on 16-10-12.
 */
public class DraggableFrameLayout extends FrameLayout {

    private float mTouchX;
    private float mTouchY;
    private float x;
    private float y;
    private float mStartX;
    private float mStartY;
    private OnClickListener mClickListener;
    private WindowManager windowManager = (WindowManager) getContext()
            .getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    private WindowManager.LayoutParams windowManagerParams = App.getWindowParams();

    public DraggableFrameLayout(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.assistant_float_window, null);
        this.addView(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Rect frame = new Rect();
        getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        x = event.getRawX();
        y = event.getRawY() - statusBarHeight;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchX = event.getX();
                mTouchY = event.getY();
                mStartX = x;
                mStartY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                updateViewPosition();
                mTouchX = mTouchY = 0;
                if ((x - mStartX) < 5 && (y - mStartY) < 5) {
                    if (mClickListener != null) {
                        mClickListener.onClick(this);
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        this.mClickListener = l;
    }

    private void updateViewPosition() {
        windowManagerParams.x = (int) (x - mTouchX);
        windowManagerParams.y = (int) (y - mTouchY);
        windowManager.updateViewLayout(this, windowManagerParams);
    }
}
