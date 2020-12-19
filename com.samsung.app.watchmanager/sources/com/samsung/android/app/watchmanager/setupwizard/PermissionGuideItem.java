package com.samsung.android.app.watchmanager.setupwizard;

import android.graphics.drawable.Drawable;

public class PermissionGuideItem {
    String description;
    Drawable image;
    String title;

    PermissionGuideItem(Drawable drawable, String str, String str2) {
        this.image = drawable;
        this.title = str;
        this.description = str2;
    }

    public String toString() {
        return "PermissionGuideItem{image=" + this.image + ", title='" + this.title + '\'' + ", description='" + this.description + '\'' + '}';
    }
}
