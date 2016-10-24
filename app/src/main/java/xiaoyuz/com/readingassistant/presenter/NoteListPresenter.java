package xiaoyuz.com.readingassistant.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import xiaoyuz.com.readingassistant.contract.NoteListContract;
import xiaoyuz.com.readingassistant.db.repository.NoteDataSource;
import xiaoyuz.com.readingassistant.db.repository.NoteRepository;
import xiaoyuz.com.readingassistant.entity.NoteRecord;

/**
 * Created by zhangxiaoyu on 16-10-22.
 */
public class NoteListPresenter implements NoteListContract.Presenter {

    private NoteListContract.View mView;
    private NoteRepository mNoteRepository;

    public NoteListPresenter(@NonNull NoteListContract.View view,
                             @NonNull NoteRepository noteRepository) {
        mView = view;
        mNoteRepository = noteRepository;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        initNoteList();
    }

    @Override
    public void initNoteList() {
        mNoteRepository.initNoteRecords();
    }

    @Override
    public void addNote(NoteRecord noteRecord) {
        mNoteRepository.addNoteRecord(noteRecord);
    }

    @Override
    public void deleteNote(NoteRecord noteRecord) {
        mNoteRepository.removeNoteRecord(noteRecord);
    }

    @Override
    public void loadNoteList() {
        mNoteRepository.getNoteList(new NoteDataSource.GetNoteListCallback() {
            @Override
            public void onNoteListGet(List<NoteRecord> noteRecords) {
                mView.showNoteList(noteRecords);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

}
