package xiaoyuz.com.readingassistant.entity;

/**
 * Created by zhangxiaoyu on 16-10-18.
 */
public class NoteRecord {

    private String filePath;
    private String time;

    public NoteRecord() {

    }

    public NoteRecord(String filePath, String time) {
        this.filePath = filePath;
        this.time = time;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        return filePath.equals(((NoteRecord) o).getFilePath())
                && time.equals(((NoteRecord) o).getTime());
    }
}
