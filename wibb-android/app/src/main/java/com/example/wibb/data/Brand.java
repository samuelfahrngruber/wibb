package com.example.wibb.data;

public class Brand implements GridDisplayable {
    private String name;
    private int icon;

    public Brand(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    @Override
    public int getDrawable() {
        return getIcon();
    }

    @Override
    public String getText() {
        return getName();
    }
}
