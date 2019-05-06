package com.digitaldestino.modelClass.menu;

public class MenuItem
{
    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    private String menu_name;
    private int image;

    public MenuItem(String menu_name, int image) {
        this.menu_name = menu_name;
        this.image = image;
    }

}
