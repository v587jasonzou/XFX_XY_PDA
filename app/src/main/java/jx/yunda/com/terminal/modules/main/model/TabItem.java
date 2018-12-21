package jx.yunda.com.terminal.modules.main.model;

public class TabItem {
    private String name;
    private String title;
    private int imageResouseId;
    private int selectedImageResouseId;
    private Class relFragment;

    public TabItem(String name, String title, int imageResouseId, int selectedImageResouseId, Class relFragment) {
        this.name = name;
        this.title = title;
        this.imageResouseId = imageResouseId;
        this.selectedImageResouseId = selectedImageResouseId;
        this.relFragment = relFragment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResouseId() {
        return imageResouseId;
    }

    public void setImageResouseId(int imageResouseId) {
        this.imageResouseId = imageResouseId;
    }

    public int getSelectedImageResouseId() {
        return selectedImageResouseId;
    }

    public void setSelectedImageResouseId(int selectedImageResouseId) {
        this.selectedImageResouseId = selectedImageResouseId;
    }

    public Class getRelFragment() {
        return relFragment;
    }

    public void setRelFragment(Class relFragment) {
        this.relFragment = relFragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
