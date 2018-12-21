package jx.yunda.com.terminal.modules.receiveTrain.model;

import java.io.Serializable;

public class TrainEntity implements Serializable {

    /**
     * typeID : 231
     * typeName : 和谐D1
     * shortName : HXD1
     * spell : null
     * alias : null
     * kmlightRepair : null
     * powerQuotiety : null
     * trainNo : null
     * powerType : null
     * repairType : C1C6
     */

    private String typeID;
    private String typeName;
    private String shortName;
    private Object spell;
    private Object alias;
    private Object kmlightRepair;
    private Object powerQuotiety;
    private Object trainNo;
    private Object powerType;
    private String repairType;

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Object getSpell() {
        return spell;
    }

    public void setSpell(Object spell) {
        this.spell = spell;
    }

    public Object getAlias() {
        return alias;
    }

    public void setAlias(Object alias) {
        this.alias = alias;
    }

    public Object getKmlightRepair() {
        return kmlightRepair;
    }

    public void setKmlightRepair(Object kmlightRepair) {
        this.kmlightRepair = kmlightRepair;
    }

    public Object getPowerQuotiety() {
        return powerQuotiety;
    }

    public void setPowerQuotiety(Object powerQuotiety) {
        this.powerQuotiety = powerQuotiety;
    }

    public Object getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(Object trainNo) {
        this.trainNo = trainNo;
    }

    public Object getPowerType() {
        return powerType;
    }

    public void setPowerType(Object powerType) {
        this.powerType = powerType;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }
}
