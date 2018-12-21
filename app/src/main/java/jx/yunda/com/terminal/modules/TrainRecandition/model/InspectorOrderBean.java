package jx.yunda.com.terminal.modules.TrainRecandition.model;

import java.io.Serializable;

public class InspectorOrderBean implements Serializable {

    /**
     * idx : C3CE9BAE8A3C44A6908194673FA9A438
     * detectItemCode : JCX-000763
     * repairResult : null
     * detectResulttype : 数字
     * maxResult : null
     * itemResultCount : 2
     * isPhotograph : null
     * detectItemContent : 测量排障器距轨面高度
     * workTaskName : 排障器安装牢固，不许有裂损、严重变形；落成后测量排障器距轨面高度。
     * detectResult : 车体23333
     * detectItemStandard : mm
     * taskIdx : A089DFE368054D6EAC7CFF510DF9F00C
     * resultName : 车体23333
     * hjDetectResult : null
     * minResult : null
     * isNotBlank : 0
     */

    private String idx;
    private String detectItemCode;
    private String repairResult;
    private String detectResulttype;
    private String maxResult;
    private int itemResultCount;
    private String isPhotograph;
    private String detectItemContent;
    private String workTaskName;
    private String detectResult;
    private String detectItemStandard;
    private String taskIdx;
    private String resultName;
    private String hjDetectResult;
    private String minResult;
    private int isNotBlank;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getDetectItemCode() {
        return detectItemCode;
    }

    public void setDetectItemCode(String detectItemCode) {
        this.detectItemCode = detectItemCode;
    }

    public String getRepairResult() {
        return repairResult;
    }

    public void setRepairResult(String repairResult) {
        this.repairResult = repairResult;
    }

    public String getDetectResulttype() {
        return detectResulttype;
    }

    public void setDetectResulttype(String detectResulttype) {
        this.detectResulttype = detectResulttype;
    }

    public String getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(String maxResult) {
        this.maxResult = maxResult;
    }

    public int getItemResultCount() {
        return itemResultCount;
    }

    public void setItemResultCount(int itemResultCount) {
        this.itemResultCount = itemResultCount;
    }

    public String getIsPhotograph() {
        return isPhotograph;
    }

    public void setIsPhotograph(String isPhotograph) {
        this.isPhotograph = isPhotograph;
    }

    public String getDetectItemContent() {
        return detectItemContent;
    }

    public void setDetectItemContent(String detectItemContent) {
        this.detectItemContent = detectItemContent;
    }

    public String getWorkTaskName() {
        return workTaskName;
    }

    public void setWorkTaskName(String workTaskName) {
        this.workTaskName = workTaskName;
    }

    public String getDetectResult() {
        return detectResult;
    }

    public void setDetectResult(String detectResult) {
        this.detectResult = detectResult;
    }

    public String getDetectItemStandard() {
        return detectItemStandard;
    }

    public void setDetectItemStandard(String detectItemStandard) {
        this.detectItemStandard = detectItemStandard;
    }

    public String getTaskIdx() {
        return taskIdx;
    }

    public void setTaskIdx(String taskIdx) {
        this.taskIdx = taskIdx;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public Object getHjDetectResult() {
        return hjDetectResult;
    }

    public void setHjDetectResult(String hjDetectResult) {
        this.hjDetectResult = hjDetectResult;
    }

    public String getMinResult() {
        return minResult;
    }

    public void setMinResult(String minResult) {
        this.minResult = minResult;
    }

    public int getIsNotBlank() {
        return isNotBlank;
    }

    public void setIsNotBlank(int isNotBlank) {
        this.isNotBlank = isNotBlank;
    }
}
