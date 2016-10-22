package xiaoyuz.com.readingassistant.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import xiaoyuz.com.readingassistant.utils.BitmapUtils;

/**
 * Created by zhangxiaoyu on 16-10-20.
 */
public class DoodleView extends View {

    private Paint mPaint;
    private Bitmap mOriginalBitmap;
    private Bitmap mNew1Bitmap;
    private Bitmap mNew2Bitmap;
    private float mClickX;
    private float mClickY;
    private float mStartX;
    private float mStartY;
    private boolean mIsMove = true;
    private boolean mIsClear;
    private int mColor = Color.RED;
    private float mStrokeWidth = 2.0f;

    public DoodleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setImage(String path) {
        mOriginalBitmap = BitmapUtils.getBitmapFromPath(path).copy(Bitmap.Config.ARGB_8888, true);
        mNew1Bitmap = Bitmap.createBitmap(mOriginalBitmap);
    }

    public void clear(){
        mIsClear = true;
        mNew2Bitmap = Bitmap.createBitmap(mOriginalBitmap);
        invalidate();
    }
    public void setstyle(float strokeWidth){
        this.mStrokeWidth = strokeWidth;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawBitmap(handWriting(mNew1Bitmap), 0, 0,null);

    }

    public Bitmap handWriting(Bitmap originalBitmap)
    {
        Canvas canvas = new Canvas(mIsClear ? mNew2Bitmap : originalBitmap);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(mStrokeWidth);
        if(mIsMove){
            canvas.drawLine(mStartX, mStartY, mClickX, mClickY, mPaint);
        }
        mStartX = mClickX;
        mStartY = mClickY;
        return mIsClear ? mNew2Bitmap : originalBitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        mClickX = event.getX();
        mClickY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsMove = false;
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                mIsMove = true;
                invalidate();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

}
