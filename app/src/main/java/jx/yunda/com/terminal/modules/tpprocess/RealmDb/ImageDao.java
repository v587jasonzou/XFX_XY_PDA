package jx.yunda.com.terminal.modules.tpprocess.RealmDb;

import java.io.Serializable;

import io.realm.RealmObject;

public class ImageDao extends RealmObject implements Serializable {
    private String filename;
    private String filePath;
    private boolean isUpLoad;
    private String planIdx;
    private String fileBackPath;
    private int idx;
    private boolean isToup;

    public boolean isToup() {
        return isToup;
    }

    public void setToup(boolean toup) {
        isToup = toup;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isUpLoad() {
        return isUpLoad;
    }

    public void setUpLoad(boolean upLoad) {
        isUpLoad = upLoad;
    }

    public String getPlanIdx() {
        return planIdx;
    }

    public void setPlanIdx(String planIdx) {
        this.planIdx = planIdx;
    }

    public String getFileBackPath() {
        return fileBackPath;
    }

    public void setFileBackPath(String fileBackPath) {
        this.fileBackPath = fileBackPath;
    }
}
