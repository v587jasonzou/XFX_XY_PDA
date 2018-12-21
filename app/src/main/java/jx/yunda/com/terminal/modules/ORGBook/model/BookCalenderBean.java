package jx.yunda.com.terminal.modules.ORGBook.model;

import java.io.Serializable;

public class BookCalenderBean implements Serializable {

    /**
     * defInfo : {"idx":"ff8080815b096999015b096e5a070002","calendarName":"7*8小时","period1Begin":"08:00:00","period1End":"12:00:00","period2Begin":"13:20:00","period2End":"17:20:00","period3Begin":"00:00:00","period3End":"00:00:00","period4Begin":"00:00:00","period4End":"00:00:00","isDefault":"1","remark":null,"recordStatus":0,"siteId":null,"creator":581,"createTime":1490511878000,"updator":581,"updateTime":1490511886000}
     * entity : {"idx":"ff8080815b09c90b015b0dc8913e028d","infoIdx":"ff8080815b096999015b096e5a070002","calDate":"20180730","calDateType":"0","period1Begin":"08:00:00","period1End":"12:00:00","period2Begin":"13:20:00","period2End":"17:20:00","period3Begin":"00:00:00","period3End":"00:00:00","period4Begin":"00:00:00","period4End":"00:00:00","remark":null,"recordStatus":0,"siteId":null,"creator":581,"createTime":1490584899000,"updator":581,"updateTime":1490585010000}
     * success : true
     */

    private DefInfoBean defInfo;
    private EntityBean entity;
    private boolean success;

    public DefInfoBean getDefInfo() {
        return defInfo;
    }

    public void setDefInfo(DefInfoBean defInfo) {
        this.defInfo = defInfo;
    }

    public EntityBean getEntity() {
        return entity;
    }

    public void setEntity(EntityBean entity) {
        this.entity = entity;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DefInfoBean {
        /**
         * idx : ff8080815b096999015b096e5a070002
         * calendarName : 7*8小时
         * period1Begin : 08:00:00
         * period1End : 12:00:00
         * period2Begin : 13:20:00
         * period2End : 17:20:00
         * period3Begin : 00:00:00
         * period3End : 00:00:00
         * period4Begin : 00:00:00
         * period4End : 00:00:00
         * isDefault : 1
         * remark : null
         * recordStatus : 0
         * siteId : null
         * creator : 581
         * createTime : 1490511878000
         * updator : 581
         * updateTime : 1490511886000
         */

        private String idx;
        private String calendarName;
        private String period1Begin;
        private String period1End;
        private String period2Begin;
        private String period2End;
        private String period3Begin;
        private String period3End;
        private String period4Begin;
        private String period4End;
        private String isDefault;
        private Object remark;
        private int recordStatus;
        private Object siteId;
        private int creator;
        private long createTime;
        private int updator;
        private long updateTime;

        public String getIdx() {
            return idx;
        }

        public void setIdx(String idx) {
            this.idx = idx;
        }

        public String getCalendarName() {
            return calendarName;
        }

        public void setCalendarName(String calendarName) {
            this.calendarName = calendarName;
        }

        public String getPeriod1Begin() {
            return period1Begin;
        }

        public void setPeriod1Begin(String period1Begin) {
            this.period1Begin = period1Begin;
        }

        public String getPeriod1End() {
            return period1End;
        }

        public void setPeriod1End(String period1End) {
            this.period1End = period1End;
        }

        public String getPeriod2Begin() {
            return period2Begin;
        }

        public void setPeriod2Begin(String period2Begin) {
            this.period2Begin = period2Begin;
        }

        public String getPeriod2End() {
            return period2End;
        }

        public void setPeriod2End(String period2End) {
            this.period2End = period2End;
        }

        public String getPeriod3Begin() {
            return period3Begin;
        }

        public void setPeriod3Begin(String period3Begin) {
            this.period3Begin = period3Begin;
        }

        public String getPeriod3End() {
            return period3End;
        }

        public void setPeriod3End(String period3End) {
            this.period3End = period3End;
        }

        public String getPeriod4Begin() {
            return period4Begin;
        }

        public void setPeriod4Begin(String period4Begin) {
            this.period4Begin = period4Begin;
        }

        public String getPeriod4End() {
            return period4End;
        }

        public void setPeriod4End(String period4End) {
            this.period4End = period4End;
        }

        public String getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getRecordStatus() {
            return recordStatus;
        }

        public void setRecordStatus(int recordStatus) {
            this.recordStatus = recordStatus;
        }

        public Object getSiteId() {
            return siteId;
        }

        public void setSiteId(Object siteId) {
            this.siteId = siteId;
        }

        public int getCreator() {
            return creator;
        }

        public void setCreator(int creator) {
            this.creator = creator;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getUpdator() {
            return updator;
        }

        public void setUpdator(int updator) {
            this.updator = updator;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class EntityBean {
        /**
         * idx : ff8080815b09c90b015b0dc8913e028d
         * infoIdx : ff8080815b096999015b096e5a070002
         * calDate : 20180730
         * calDateType : 0
         * period1Begin : 08:00:00
         * period1End : 12:00:00
         * period2Begin : 13:20:00
         * period2End : 17:20:00
         * period3Begin : 00:00:00
         * period3End : 00:00:00
         * period4Begin : 00:00:00
         * period4End : 00:00:00
         * remark : null
         * recordStatus : 0
         * siteId : null
         * creator : 581
         * createTime : 1490584899000
         * updator : 581
         * updateTime : 1490585010000
         */

        private String idx;
        private String infoIdx;
        private String calDate;
        private String calDateType;
        private String period1Begin;
        private String period1End;
        private String period2Begin;
        private String period2End;
        private String period3Begin;
        private String period3End;
        private String period4Begin;
        private String period4End;
        private Object remark;
        private int recordStatus;
        private Object siteId;
        private int creator;
        private long createTime;
        private int updator;
        private long updateTime;

        public String getIdx() {
            return idx;
        }

        public void setIdx(String idx) {
            this.idx = idx;
        }

        public String getInfoIdx() {
            return infoIdx;
        }

        public void setInfoIdx(String infoIdx) {
            this.infoIdx = infoIdx;
        }

        public String getCalDate() {
            return calDate;
        }

        public void setCalDate(String calDate) {
            this.calDate = calDate;
        }

        public String getCalDateType() {
            return calDateType;
        }

        public void setCalDateType(String calDateType) {
            this.calDateType = calDateType;
        }

        public String getPeriod1Begin() {
            return period1Begin;
        }

        public void setPeriod1Begin(String period1Begin) {
            this.period1Begin = period1Begin;
        }

        public String getPeriod1End() {
            return period1End;
        }

        public void setPeriod1End(String period1End) {
            this.period1End = period1End;
        }

        public String getPeriod2Begin() {
            return period2Begin;
        }

        public void setPeriod2Begin(String period2Begin) {
            this.period2Begin = period2Begin;
        }

        public String getPeriod2End() {
            return period2End;
        }

        public void setPeriod2End(String period2End) {
            this.period2End = period2End;
        }

        public String getPeriod3Begin() {
            return period3Begin;
        }

        public void setPeriod3Begin(String period3Begin) {
            this.period3Begin = period3Begin;
        }

        public String getPeriod3End() {
            return period3End;
        }

        public void setPeriod3End(String period3End) {
            this.period3End = period3End;
        }

        public String getPeriod4Begin() {
            return period4Begin;
        }

        public void setPeriod4Begin(String period4Begin) {
            this.period4Begin = period4Begin;
        }

        public String getPeriod4End() {
            return period4End;
        }

        public void setPeriod4End(String period4End) {
            this.period4End = period4End;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getRecordStatus() {
            return recordStatus;
        }

        public void setRecordStatus(int recordStatus) {
            this.recordStatus = recordStatus;
        }

        public Object getSiteId() {
            return siteId;
        }

        public void setSiteId(Object siteId) {
            this.siteId = siteId;
        }

        public int getCreator() {
            return creator;
        }

        public void setCreator(int creator) {
            this.creator = creator;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getUpdator() {
            return updator;
        }

        public void setUpdator(int updator) {
            this.updator = updator;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
