package xiaoyuz.com.readingassistant.entity;

import java.util.Date;

/**
 * Created by zhangxiaoyu on 16-10-18.
 */
public class NoteRecord {

    private String filePath;
    private Date date;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
