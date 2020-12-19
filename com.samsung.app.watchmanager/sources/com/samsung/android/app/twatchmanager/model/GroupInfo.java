package com.samsung.android.app.twatchmanager.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class GroupInfo extends GroupBase {
    public String aliasName;
    public Integer displayOrder = Integer.MAX_VALUE;
    public HashMap<String, ArrayList<ImageInfo>> images;

    public static class ImageInfo {
        public static final String ATTR_HEIGHT = "height";
        public static final String ATTR_SWDP = "swdp";
        public static final String ATTR_TYPE = "type";
        public static final String ATTR_UPDATED = "updated";
        public static final String ATTR_YSLIDE_VALUE = "ySlideValue";
        public HashMap<String, String> attributes;
        public String name;

        public String toString() {
            return "ImageInfo{name='" + this.name + '\'' + ", attributes=" + this.attributes + '}';
        }
    }

    public enum InfoType {
        BACKGROUND(0),
        ICON(1),
        HEADER(2);
        
        private int index;

        private InfoType(int i) {
            this.index = i;
        }

        public int toValue() {
            return this.index;
        }
    }

    public ImageInfo getGroupImageInfo(InfoType infoType) {
        return getGroupImageInfo(infoType, 0);
    }

    public ImageInfo getGroupImageInfo(InfoType infoType, int i) {
        if (this.images == null) {
            return null;
        }
        try {
            ArrayList<ImageInfo> arrayList = this.images.get(infoType.toString().toLowerCase(Locale.ENGLISH));
            if (arrayList != null) {
                return arrayList.get(i);
            }
            return null;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTitle() {
        String str = this.aliasName;
        return str != null ? str : this.name;
    }

    public String toString() {
        return "GroupInfo{name='" + this.name + '\'' + ", aliasName='" + this.aliasName + '\'' + ", displayOrder=" + this.displayOrder + ", images=" + this.images + '}';
    }
}
