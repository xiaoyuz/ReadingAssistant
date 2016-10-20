package xiaoyuz.com.readingassistant.event;

import xiaoyuz.com.readingassistant.entity.NoteRecord;

/**
 * Created by zhangxiaoyu on 16-10-20.
 */
public class NoteRecordFileEvent {

    public enum Type {
        ADD(1),
        DELETE(2);

        private int typeNum;

        Type(int typeNum) {
            this.typeNum = typeNum;
        }
    }

    private NoteRecord mNoteRecord;
    private Type mType;

    public NoteRecordFileEvent(NoteRecord noteRecord, Type type) {
        mNoteRecord = noteRecord;
        mType = type;
    }

    public NoteRecord getNoteRecord() {
        return mNoteRecord;
    }

    public Type getType() {
        return mType;
    }
}
