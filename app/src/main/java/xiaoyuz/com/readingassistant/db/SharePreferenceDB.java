package xiaoyuz.com.readingassistant.db;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xiaoyuz.com.readingassistant.entity.NoteRecord;
import xiaoyuz.com.readingassistant.utils.SharePreferenceUtils;

/**
 * Created by zhangxiaoyu on 16-10-19.
 */
public class SharePreferenceDB {

    private static final String NOTES_LIST_DB_KEY = "notes_list";

    public static List<NoteRecord> getNotesList() {
        String listStr = SharePreferenceUtils.getString(NOTES_LIST_DB_KEY, null);
        List<NoteRecord> noteRecords;
        if (TextUtils.isEmpty(listStr)) {
            noteRecords = new ArrayList<>();
        } else {
            noteRecords = new Gson().fromJson(listStr,
                    new TypeToken<List<NoteRecord>>(){}.getType());
        }
        return noteRecords;
    }

    public static void setNotesList(List<NoteRecord> noteRecords) {
        String listStr = new Gson().toJson(noteRecords);
        SharePreferenceUtils.putString(NOTES_LIST_DB_KEY, listStr);
    }

    public static void addNoteRecord2List(NoteRecord noteRecord) {
        List<NoteRecord> noteRecords = getNotesList();
        noteRecords.add(noteRecord);
        setNotesList(noteRecords);
    }

}
