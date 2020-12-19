package com.samsung.android.app.twatchmanager.util;

import android.graphics.drawable.Drawable;

public class PermissionItem {
    Drawable image;
    String text;

    PermissionItem(Drawable drawable, String str) {
        this.image = drawable;
        this.text = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PermissionItem)) {
            return false;
        }
        return this.text.equals(((PermissionItem) obj).text);
    }

    public int hashCode() {
        return this.text.hashCode();
    }
}
