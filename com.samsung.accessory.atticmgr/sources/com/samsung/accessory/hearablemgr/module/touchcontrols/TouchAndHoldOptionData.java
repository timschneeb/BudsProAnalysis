package com.samsung.accessory.hearablemgr.module.touchcontrols;

public class TouchAndHoldOptionData {
    private int count;
    private boolean isConnected;
    private boolean isSelected;
    private String optionName;
    private int viewType;

    public TouchAndHoldOptionData(String str, boolean z, int i, boolean z2) {
        this.optionName = str;
        this.isSelected = z;
        this.viewType = i;
        this.isConnected = z2;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int i) {
        this.count = i;
    }

    public String getOptionName() {
        return this.optionName;
    }

    public void setOptionName(String str) {
        this.optionName = str;
    }

    public boolean getSelectedItem() {
        return this.isSelected;
    }

    public void setSelectedItem(boolean z) {
        this.isSelected = z;
    }

    public int getViewType() {
        return this.viewType;
    }

    public void setIsConnected(boolean z) {
        this.isConnected = z;
    }

    public boolean getIsConnected() {
        return this.isConnected;
    }
}
