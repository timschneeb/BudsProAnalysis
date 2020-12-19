package com.samsung.android.sdk.mobileservice.social.buddy;

import java.util.ArrayList;

public class PublicBuddyInfo {
    private ArrayList<Capability> mCapabilityList;
    private byte[] mImage;
    private String mImageType;
    private String mName;
    private String mPhoneNumber;
    private String mStatusMessage;

    public PublicBuddyInfo(String str, String str2, String str3, byte[] bArr, String str4, ArrayList<Capability> arrayList) {
        this.mPhoneNumber = str;
        this.mName = str2;
        this.mStatusMessage = str3;
        this.mImage = bArr;
        this.mImageType = str4;
        this.mCapabilityList = arrayList;
    }

    public String getPhoneNumber() {
        return this.mPhoneNumber;
    }

    public String getName() {
        return this.mName;
    }

    public String getStatusMessage() {
        return this.mStatusMessage;
    }

    public byte[] getImage() {
        return this.mImage;
    }

    public String getImageType() {
        return this.mImageType;
    }

    public ArrayList<Capability> getCapabilityList() {
        return this.mCapabilityList;
    }

    public static class Capability {
        private String mAppId;
        private String mServiceId;

        public Capability(String str, String str2) {
            this.mAppId = str;
            this.mServiceId = str2;
        }

        public String getAppId() {
            return this.mAppId;
        }

        public String getServiceId() {
            return this.mServiceId;
        }
    }
}
