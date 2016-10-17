package xiaoyuz.com.readingassistant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xiaoyuz.com.readingassistant.R;
import xiaoyuz.com.readingassistant.base.BaseFragment;

/**
 * Created by zhangxiaoyu on 16-10-17.
 */
public class NoteListFragment extends BaseFragment {

    @Override
    protected void initVariables() {

    }

    @Override
    protected View initView(LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_list_fragment, container, false);
    }

    @Override
    protected void loadData() {

    }
}
