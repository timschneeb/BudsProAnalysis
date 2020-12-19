package com.samsung.android.app.watchmanager.setupwizard.contactus.connection.RequestResponseClasses;

public class FeedBackRequestBody {
    AccessoryInfo accessory;
    ApplicationInfo application;
    String betaProjectId;
    String communityId;
    DeviceInfo device;
    String parentId;
    Question question;
    FeedBackType type;

    public FeedBackRequestBody(FeedBackType feedBackType, Question question2, DeviceInfo deviceInfo, AccessoryInfo accessoryInfo, ApplicationInfo applicationInfo, String str, String str2, String str3) {
        this.type = feedBackType;
        this.question = question2;
        this.device = deviceInfo;
        this.accessory = accessoryInfo;
        this.application = applicationInfo;
        this.parentId = str;
        this.communityId = str2;
        this.betaProjectId = str3;
    }
}
