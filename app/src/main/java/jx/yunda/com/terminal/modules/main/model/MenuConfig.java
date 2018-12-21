package jx.yunda.com.terminal.modules.main.model;

public class MenuConfig {
    private int iconId;
    private int bg_colorId;
    private Class refClass;

    public MenuConfig(int iconId, int bg_colorId, Class refClass) {
        this.iconId = iconId;
        this.bg_colorId = bg_colorId;
        this.refClass = refClass;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getBg_colorId() {
        return bg_colorId;
    }

    public void setBg_colorId(int bg_colorId) {
        this.bg_colorId = bg_colorId;
    }

    public Class getRefClass() {
        return refClass;
    }

    public void setRefClass(Class refClass) {
        this.refClass = refClass;
    }
}
