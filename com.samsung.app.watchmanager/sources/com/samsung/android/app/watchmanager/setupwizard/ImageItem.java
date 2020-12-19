package com.samsung.android.app.watchmanager.setupwizard;

import com.samsung.android.app.twatchmanager.model.GroupInfo;

public class ImageItem {
    private GroupInfo.ImageInfo imageInfo;
    private String title;

    public ImageItem(GroupInfo.ImageInfo imageInfo2, String str) {
        this.imageInfo = imageInfo2;
        this.title = str;
    }

    public GroupInfo.ImageInfo getImageInfo() {
        return this.imageInfo;
    }

    public String getTitle() {
        return this.title;
    }

    public void setImageInfo(GroupInfo.ImageInfo imageInfo2) {
        this.imageInfo = imageInfo2;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String toString() {
        return "ImageItem{imageInfo=" + this.imageInfo + ", title='" + this.title + '\'' + '}';
    }
}
