package xiaoyuz.com.readingassistant.activity;

import android.os.Bundle;
import android.widget.Button;

import xiaoyuz.com.readingassistant.R;
import xiaoyuz.com.readingassistant.base.BaseActivity;
import xiaoyuz.com.readingassistant.ui.widget.DoodleView;
import xiaoyuz.com.readingassistant.utils.Constants;

/**
 * Created by zhangxiaoyu on 16-10-20.
 */
public class DoodleActivity extends BaseActivity {

    private DoodleView mDoodleView;
    private Button mClearButton;
    private Button mSaveButton;

    private String mImagePath;

    @Override
    protected void initVariables() {
        Bundle bundle = this.getIntent().getExtras();
        mImagePath = bundle.getString(Constants.DOODLE_IMAGE_PATH);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.doodle_activity);

        mDoodleView = (DoodleView) findViewById(R.id.doodle_view);
        mClearButton = (Button) findViewById(R.id.clear);
        mSaveButton = (Button) findViewById(R.id.save);
    }

    @Override
    protected void loadData() {
        mDoodleView.setImage(mImagePath);
    }
}
