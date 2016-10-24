package xiaoyuz.com.readingassistant.db.repository;

import java.util.List;

import rx.Observable;
import xiaoyuz.com.readingassistant.entity.NoteRecord;

/**
 * Created by zhangxiaoyu on 16-10-24.
 */
public interface NoteDataSource {

    interface GetNoteListCallback {

        void onNoteListGet(List<NoteRecord> noteRecords);

        void onDataNotAvailable();
    }

    void initNoteRecords();

    Observable<List<NoteRecord>> getNoteList();

    void addNoteRecord(NoteRecord noteRecord);

    void removeNoteRecord(NoteRecord noteRecord);
}
