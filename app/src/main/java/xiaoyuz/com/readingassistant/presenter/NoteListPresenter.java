package xiaoyuz.com.readingassistant.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import xiaoyuz.com.readingassistant.contract.NoteListContract;
import xiaoyuz.com.readingassistant.db.SharePreferenceDB;
import xiaoyuz.com.readingassistant.entity.NoteRecord;

/**
 * Created by zhangxiaoyu on 16-10-22.
 */
public class NoteListPresenter implements NoteListContract.Presenter {

    private NoteListContract.View mView;

    public NoteListPresenter(@NonNull NoteListContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadNoteList();
    }

    @Override
    public void loadNoteList() {
        SharePreferenceDB.loadNoteRecords();
    }

    @Override
    public void addNote(NoteRecord noteRecord) {
        SharePreferenceDB.addNoteRecord2List(noteRecord);
    }

    @Override
    public void deleteNote(NoteRecord noteRecord) {
        SharePreferenceDB.removeNoteRecord(noteRecord);
    }

    @Override
    public List<NoteRecord> getNoteList() {
        return SharePreferenceDB.getNotesList();
    }

}
