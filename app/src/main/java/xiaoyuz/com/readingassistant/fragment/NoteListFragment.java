package xiaoyuz.com.readingassistant.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xiaoyuz.com.readingassistant.R;
import xiaoyuz.com.readingassistant.base.BaseFragment;
import xiaoyuz.com.readingassistant.entity.NoteRecord;
import xiaoyuz.com.readingassistant.ui.adapter.NoteListAdapter;
import xiaoyuz.com.readingassistant.utils.App;

/**
 * Created by zhangxiaoyu on 16-10-17.
 */
public class NoteListFragment extends BaseFragment {

    private RecyclerView mNoteListView;
    private List<NoteRecord> mNoteRecordList;
    private LinearLayoutManager mLayoutManager;
    private NoteListAdapter mAdapter;

    @Override
    protected void initVariables() {
        mNoteRecordList = new ArrayList<>();
    }

    @Override
    protected View initView(LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_list_fragment, container, false);
        mNoteListView = (RecyclerView) view.findViewById(R.id.notes_list);
        mLayoutManager = new LinearLayoutManager(App.getContext());
        mNoteListView.setLayoutManager(mLayoutManager);
        mNoteListView.setHasFixedSize(true);
        mAdapter = new NoteListAdapter(mNoteRecordList);
        mNoteListView.setAdapter(mAdapter);
        return view;
    }

    @Override
    protected void loadData() {
        testData();
    }

    /**
     * Data set for test.
     */
    private void testData() {
        for (int i = 0; i < 10; i++) {
            NoteRecord noteRecord = new NoteRecord();
            noteRecord.setDate(new Date());
            noteRecord.setFilePath("abc");
            mNoteRecordList.add(noteRecord);
        }
    }
}
