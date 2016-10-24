package xiaoyuz.com.readingassistant.fragment;

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
import xiaoyuz.com.readingassistant.contract.NoteListContract;
import xiaoyuz.com.readingassistant.cropimage.Crop;
import xiaoyuz.com.readingassistant.entity.NoteRecord;
import xiaoyuz.com.readingassistant.event.NoteRecordFileEvent;
import xiaoyuz.com.readingassistant.ui.adapter.NoteListAdapter;
import xiaoyuz.com.readingassistant.utils.App;

/**
 * Created by zhangxiaoyu on 16-10-17.
 */
public class NoteListFragment extends BaseFragment implements NoteListContract.View {

    private class EventHandler {
        @Subscribe
        public void NoteRecordFileDeleteEvent(NoteRecordFileEvent event) {
            if (event.getType() == NoteRecordFileEvent.Type.DELETE) {
                mAdapter.removeNoteRecord(event.getNoteRecord());
                mNoteListPresenter.loadNoteList();
                mAdapter.notifyDataSetChanged();
            } else if(event.getType() == NoteRecordFileEvent.Type.ADD) {
                mNoteListPresenter.loadNoteList();
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

    private NoteListContract.Presenter mNoteListPresenter;

    @Override
    protected void initVariables() {
        mNoteRecordList = new ArrayList<>();
        mEventHandler = new EventHandler();
        EventDispatcher.register(mEventHandler, EventDispatcher.Group.Main);
        mNoteRecordList = new ArrayList<>();
        mAdapter = new NoteListAdapter(mNoteRecordList, mNoteListPresenter);
    }

    @Override
    protected View initView(LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_list_fragment, container, false);
        mNoteListView = (RecyclerView) view.findViewById(R.id.notes_list);
        mLayoutManager = new LinearLayoutManager(App.getContext());
        mNoteListView.setLayoutManager(mLayoutManager);
        mNoteListView.setHasFixedSize(true);
//        mAdapter = new NoteListAdapter(mNoteRecordList, mNoteListPresenter);
        mAdapter.setOnItemClickListener(new NoteListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
//                Intent intent = new Intent(getActivity(), DoodleActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString(Constants.DOODLE_IMAGE_PATH, data);
//                intent.putExtras(bundle);
//                getActivity().startActivity(intent);
                showNoteCropPage(data);
            }
        });
        mNoteListView.setAdapter(mAdapter);
        return view;
    }

    @Override
    protected void loadData() {
        mNoteListPresenter.start();
        mNoteListPresenter.loadNoteList();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.setNoteRecordList(mNoteRecordList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventDispatcher.unregister(mEventHandler);
    }

    @Override
    public void setPresenter(NoteListContract.Presenter presenter) {
        mNoteListPresenter = presenter;
    }

    @Override
    public void showNoteList(List<NoteRecord> noteRecords) {
        mNoteListPresenter.start();
        mNoteRecordList = noteRecords;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNoteCropPage(String path) {
        Crop.of(Uri.fromFile(new File(path)), Uri.fromFile(new File(path + "1")))
                .start(getActivity());
    }
}
