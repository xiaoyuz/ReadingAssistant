package xiaoyuz.com.readingassistant.db.repository;

import android.support.annotation.NonNull;

import xiaoyuz.com.readingassistant.entity.NoteRecord;

/**
 * Created by zhangxiaoyu on 16-10-24.
 */
public class NoteRepository implements NoteDataSource {

    private NoteDataSource mNoteDataSource;
    private static NoteRepository sInstance;

    private NoteRepository(NoteDataSource noteDataSource) {
        mNoteDataSource = noteDataSource;
    }

    public static NoteRepository getInstance(NoteDataSource noteDataSource) {
        if (sInstance == null) {
            sInstance = new NoteRepository(noteDataSource);
        }
        return sInstance;
    }

    public void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void initNoteRecords() {
        mNoteDataSource.initNoteRecords();
    }

    @Override
    public void getNoteList(@NonNull GetNoteListCallback getNoteListCallback) {
        mNoteDataSource.getNoteList(getNoteListCallback);
    }

    @Override
    public void addNoteRecord(NoteRecord noteRecord) {
        mNoteDataSource.addNoteRecord(noteRecord);
    }

    @Override
    public void removeNoteRecord(NoteRecord noteRecord) {
        mNoteDataSource.removeNoteRecord(noteRecord);
    }
}
