package xiaoyuz.com.readingassistant.contract;

import java.util.List;

import xiaoyuz.com.readingassistant.base.BasePresenter;
import xiaoyuz.com.readingassistant.base.BaseView;
import xiaoyuz.com.readingassistant.entity.NoteRecord;

/**
 * Created by zhangxiaoyu on 16-10-22.
 */
public interface NoteListContract {

    interface View extends BaseView<Presenter> {

        void showNoteCropPage(String path);

    }

    interface Presenter extends BasePresenter {

        void loadNoteList();

        void addNote(NoteRecord noteRecord);

        void deleteNote(NoteRecord noteRecord);

        List<NoteRecord> getNoteList();

    }

}
