package xiaoyuz.com.readingassistant.db.repository.local;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import xiaoyuz.com.readingassistant.db.repository.NoteDataSource;
import xiaoyuz.com.readingassistant.entity.NoteRecord;
import xiaoyuz.com.readingassistant.utils.FileUtils;
import xiaoyuz.com.readingassistant.utils.SharePreferenceUtils;

/**
 * Created by zhangxiaoyu on 16-10-19.
 */
public class NoteLocalDataSource implements NoteDataSource {

    private static final String NOTES_LIST_DB_KEY = "notes_list";

    private static List<NoteRecord> sNoteRecords;
    private static Map<String, NoteRecord> sNoteRecordMap;

    private static NoteLocalDataSource sIntance;

    private NoteLocalDataSource() {
    }

    public static NoteLocalDataSource getInstance() {
        if (sIntance == null) {
            sIntance = new NoteLocalDataSource();
        }
        return sIntance;
    }

    @Override
    public void initNoteRecords() {
        String listStr = SharePreferenceUtils.getString(NOTES_LIST_DB_KEY, null);
        if (TextUtils.isEmpty(listStr)) {
            sNoteRecords = new ArrayList<>();
        } else {
            sNoteRecords = new Gson().fromJson(listStr,
                    new TypeToken<List<NoteRecord>>(){}.getType());
        }

        // Check if some files are deleted.
        sNoteRecordMap = new HashMap<>();
        Iterator iterator = sNoteRecords.iterator();
        while (iterator.hasNext()) {
            NoteRecord noteRecord = (NoteRecord) iterator.next();
            if (!FileUtils.isFileExists(noteRecord.getFilePath())) {
                iterator.remove();
                syncNoteRecordsDB();
            } else {
                sNoteRecordMap.put(noteRecord.getFilePath(), noteRecord);
            }
        }
    }

    @Override
    public void addNoteRecord(NoteRecord noteRecord) {
        sNoteRecords.add(noteRecord);
        syncNoteRecordsDB();
        sNoteRecordMap.put(noteRecord.getFilePath(), noteRecord);
    }

    @Override
    public void getNoteList(@NonNull GetNoteListCallback getNoteListCallback) {
        getNoteListCallback.onNoteListGet(sNoteRecords);
    }

    @Override
    public void removeNoteRecord(NoteRecord noteRecord) {
        sNoteRecords.remove(noteRecord);
        syncNoteRecordsDB();
        sNoteRecordMap.remove(noteRecord.getFilePath());
    }

    public static void removeNoteRecord(int index) {
        NoteRecord noteRecord = sNoteRecords.remove(index);
        syncNoteRecordsDB();
        sNoteRecordMap.remove(noteRecord.getFilePath());
    }

    public static Map<String, NoteRecord> getNoteRecordMap() {
        return sNoteRecordMap;
    }

    private static void syncNoteRecordsDB() {
        String listStr = new Gson().toJson(sNoteRecords);
        SharePreferenceUtils.putString(NOTES_LIST_DB_KEY, listStr);
    }
}
