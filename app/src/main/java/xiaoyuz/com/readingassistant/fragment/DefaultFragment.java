package xiaoyuz.com.readingassistant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xiaoyuz.com.readingassistant.R;
import xiaoyuz.com.readingassistant.base.BaseFragment;

/**
 * Created by zhangxiaoyu on 16-10-18.
 */
public class DefaultFragment extends BaseFragment {

    @Override
    protected View initView(LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.default_fragment, container, false);
        return view;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void loadData() {

    }
}
