package com.a4sc11production.krlassist.util;

public class DrawerItem {
    String ItemName;
    int imgResID;

    public DrawerItem(String itemName, int imgResID){
        super();
        itemName = ItemName;
        this.imgResID = imgResID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getImgResID() {
        return imgResID;
    }

    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }
}
