package jx.yunda.com.terminal.modules.tpprocess.model;

import java.io.Serializable;

public class ImageResponsBean implements Serializable {

    /**
     * filePath : /weblogic/JXUpload/\tpImgAtt\2018\5\tmp\20180521115152393.jpg
     * success : true
     */

    private String filePath;
    private boolean success;
    private String error;
    private String fileIdx;

    public String getFileIdx() {
        return fileIdx;
    }

    public void setFileIdx(String fileIdx) {
        this.fileIdx = fileIdx;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
