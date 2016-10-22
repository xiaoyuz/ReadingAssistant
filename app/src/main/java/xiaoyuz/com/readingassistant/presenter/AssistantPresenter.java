package xiaoyuz.com.readingassistant.presenter;

import android.support.annotation.NonNull;

import xiaoyuz.com.readingassistant.contract.AssistantContract;
import xiaoyuz.com.readingassistant.db.SharePreferenceDB;
import xiaoyuz.com.readingassistant.entity.NoteRecord;

/**
 * Created by zhangxiaoyu on 16-10-22.
 */
public class AssistantPresenter implements AssistantContract.Presenter {

    private AssistantContract.View mView;

    public AssistantPresenter(@NonNull AssistantContract.View view) {
        mView = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        loadNoteList();
    }

    @Override
    public void addNote(NoteRecord noteRecord) {
        SharePreferenceDB.addNoteRecord2List(noteRecord);
    }

    @Override
    public void loadNoteList() {
        SharePreferenceDB.loadNoteRecords();
    }
}
