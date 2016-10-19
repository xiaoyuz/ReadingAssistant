package xiaoyuz.com.readingassistant.event;

import xiaoyuz.com.readingassistant.entity.NoteRecord;

/**
 * Created by zhangxiaoyu on 16-10-19.
 */
public class ScreenShotEvent {

    private NoteRecord mNoteRecord;

    public ScreenShotEvent(NoteRecord noteRecord) {
        mNoteRecord = noteRecord;
    }

    public NoteRecord getNoteRecord() {
        return mNoteRecord;
    }
}
