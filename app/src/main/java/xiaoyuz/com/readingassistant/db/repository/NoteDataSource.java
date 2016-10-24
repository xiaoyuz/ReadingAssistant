package xiaoyuz.com.readingassistant.db.repository;

import android.support.annotation.NonNull;

import java.util.List;

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

    void getNoteList(@NonNull GetNoteListCallback getNoteListCallback);

    void addNoteRecord(NoteRecord noteRecord);

    void removeNoteRecord(NoteRecord noteRecord);
}
