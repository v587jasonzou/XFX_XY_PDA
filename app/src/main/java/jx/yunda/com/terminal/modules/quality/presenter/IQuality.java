package jx.yunda.com.terminal.modules.quality.presenter;

public interface IQuality {
    void getTrainQualitySuccess(int size);
    void getTrainQualityFaild(String msg);
    void getEquipQualitySuccess(int size);
    void getEquipQualityFaild(String msg);
    void getTicketQualitySuccess(int size);
    void getTicketQualityFaild(String msg);
}
