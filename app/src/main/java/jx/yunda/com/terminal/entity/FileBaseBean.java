package jx.yunda.com.terminal.entity;

import java.io.Serializable;

public class FileBaseBean implements Serializable {

    /**
     * fileIdx : 8a82842863d96f2f0163d99922250021
     * fileUrl : upload_file/tpImgAtt/2018/6/8a82842863d96f2f0163d9991fe3001a_20180607173441638.jpg
     */

    private String fileIdx;
    private String fileUrl;

    public String getFileIdx() {
        return fileIdx;
    }

    public void setFileIdx(String fileIdx) {
        this.fileIdx = fileIdx;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
