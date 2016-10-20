package xiaoyuz.com.readingassistant.db;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import xiaoyuz.com.readingassistant.entity.NoteRecord;
import xiaoyuz.com.readingassistant.utils.FileUtils;
import xiaoyuz.com.readingassistant.utils.SharePreferenceUtils;

/**
 * Created by zhangxiaoyu on 16-10-19.
 */
public class SharePreferenceDB {

    private static final String NOTES_LIST_DB_KEY = "notes_list";

    private static List<NoteRecord> sNoteRecords;
    private static Map<String, NoteRecord> sNoteRecordMap;

    public static void loadNoteRecords() {
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

    public static List<NoteRecord> getNotesList() {
        return sNoteRecords;
    }

    public static void addNoteRecord2List(NoteRecord noteRecord) {
        sNoteRecords.add(noteRecord);
        syncNoteRecordsDB();
        sNoteRecordMap.put(noteRecord.getFilePath(), noteRecord);
    }

    public static void removeNoteRecord(int index) {
        NoteRecord noteRecord = sNoteRecords.remove(index);
        syncNoteRecordsDB();
        sNoteRecordMap.remove(noteRecord.getFilePath());
    }

    public static void removeNoteRecord(NoteRecord noteRecord) {
        sNoteRecords.remove(noteRecord);
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
