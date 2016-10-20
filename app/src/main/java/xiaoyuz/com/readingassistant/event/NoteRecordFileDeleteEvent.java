package xiaoyuz.com.readingassistant.event;

import xiaoyuz.com.readingassistant.entity.NoteRecord;

/**
 * Created by zhangxiaoyu on 16-10-20.
 */
public class NoteRecordFileDeleteEvent {

    private NoteRecord mNoteRecord;

    public NoteRecordFileDeleteEvent(NoteRecord noteRecord) {
        mNoteRecord = noteRecord;
    }

    public NoteRecord getNoteRecord() {
        return mNoteRecord;
    }

}
