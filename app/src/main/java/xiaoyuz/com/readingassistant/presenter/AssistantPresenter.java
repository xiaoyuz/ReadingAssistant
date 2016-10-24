package xiaoyuz.com.readingassistant.presenter;

import android.support.annotation.NonNull;

import xiaoyuz.com.readingassistant.contract.AssistantContract;
import xiaoyuz.com.readingassistant.db.repository.NoteRepository;
import xiaoyuz.com.readingassistant.entity.NoteRecord;

/**
 * Created by zhangxiaoyu on 16-10-22.
 */
public class AssistantPresenter implements AssistantContract.Presenter {

    private AssistantContract.View mView;
    private NoteRepository mNoteRepository;

    public AssistantPresenter(@NonNull AssistantContract.View view,
                              @NonNull NoteRepository noteRepository) {
        mView = view;
        mNoteRepository = noteRepository;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        loadNoteList();
    }

    @Override
    public void addNote(NoteRecord noteRecord) {
        mNoteRepository.addNoteRecord(noteRecord);
    }

    @Override
    public void loadNoteList() {
        mNoteRepository.initNoteRecords();
    }
}
