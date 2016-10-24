package xiaoyuz.com.readingassistant.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Subscriber;
import xiaoyuz.com.readingassistant.contract.NoteListContract;
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
        mNoteRepository.getNoteList().subscribe(new Subscriber<List<NoteRecord>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<NoteRecord> noteRecords) {
                mView.showNoteList(noteRecords);
            }
        });
    }

}
