package xiaoyuz.com.readingassistant.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by zhangxiaoyu on 16-10-14.
 */
public class CornerNumberTextView extends TextView {

    private int mNum;

    public CornerNumberTextView(Context context) {
        super(context);
    }

    public CornerNumberTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (mNum == 0) {
            setVisibility(GONE);
        }
    }

    public void increaseNum() {
        mNum++;
        setText(String.valueOf(mNum));
        setVisibility(VISIBLE);
    }

    public int getNum() {
        return mNum;
    }

    public void clearNum() {
        mNum = 0;
        setVisibility(GONE);
    }

}
