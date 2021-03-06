package xiaoyuz.com.readingassistant.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import xiaoyuz.com.readingassistant.EventDispatcher;
import xiaoyuz.com.readingassistant.R;
import xiaoyuz.com.readingassistant.base.BaseFragment;
import xiaoyuz.com.readingassistant.cropimage.Crop;
import xiaoyuz.com.readingassistant.db.SharePreferenceDB;
import xiaoyuz.com.readingassistant.entity.NoteRecord;
import xiaoyuz.com.readingassistant.event.NoteRecordFileEvent;
import xiaoyuz.com.readingassistant.ui.adapter.NoteListAdapter;
import xiaoyuz.com.readingassistant.utils.App;

/**
 * Created by zhangxiaoyu on 16-10-17.
 */
public class NoteListFragment extends BaseFragment {

    private class EventHandler {
        @Subscribe
        public void NoteRecordFileDeleteEvent(NoteRecordFileEvent event) {
            if (event.getType() == NoteRecordFileEvent.Type.DELETE) {
                mAdapter.removeNoteRecord(event.getNoteRecord());
                mNoteRecordList = SharePreferenceDB.getNotesList();
                mAdapter.notifyDataSetChanged();
            } else if(event.getType() == NoteRecordFileEvent.Type.ADD) {
                mNoteRecordList = SharePreferenceDB.getNotesList();
                mAdapter.setNoteRecordList(mNoteRecordList);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private RecyclerView mNoteListView;
    private List<NoteRecord> mNoteRecordList;
    private LinearLayoutManager mLayoutManager;
    private NoteListAdapter mAdapter;
    private EventHandler mEventHandler;

    @Override
    protected void initVariables() {
        mNoteRecordList = new ArrayList<>();
        mEventHandler = new EventHandler();
        EventDispatcher.register(mEventHandler, EventDispatcher.Group.Main);
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
        mAdapter.setOnItemClickListener(new NoteListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
//                Intent intent = new Intent(getActivity(), DoodleActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString(Constants.DOODLE_IMAGE_PATH, data);
//                intent.putExtras(bundle);
//                getActivity().startActivity(intent);
                Crop.of(Uri.fromFile(new File(data)), Uri.fromFile(new File(data + "1")))
                        .start(getActivity());
            }
        });
        mNoteListView.setAdapter(mAdapter);
        return view;
    }

    @Override
    protected void loadData() {
        SharePreferenceDB.loadNoteRecords();
        mNoteRecordList = SharePreferenceDB.getNotesList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventDispatcher.unregister(mEventHandler);
    }
}
