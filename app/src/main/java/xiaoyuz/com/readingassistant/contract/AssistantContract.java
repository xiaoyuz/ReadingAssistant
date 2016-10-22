package xiaoyuz.com.readingassistant.contract;

import xiaoyuz.com.readingassistant.base.BasePresenter;
import xiaoyuz.com.readingassistant.base.BaseView;
import xiaoyuz.com.readingassistant.entity.NoteRecord;

/**
 * Created by zhangxiaoyu on 16-10-22.
 */
public interface AssistantContract {

    interface View extends BaseView<Presenter> {

        void increaseNotifyNum();

        void showFlowWindow();

        void removeAssistant();

        void showNoteList();

    }

    interface Presenter extends BasePresenter {

        void loadNoteList();

        void addNote(NoteRecord noteRecord);

    }

}
