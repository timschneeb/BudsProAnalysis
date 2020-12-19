package com.samsung.android.app.twatchmanager.model;

import android.text.TextUtils;

public class GearInfo {
    public boolean connectAsAudio = false;
    public String containerPackage;
    public String deviceName;
    public GearGroup group;
    public int hostMinMemory = 1024;
    public String iconDrawableName;
    public String pairingImageName;
    public String pluginAppName;
    public String pluginPackage;
    public boolean requiresPairing = true;
    public boolean supportMultiConnection = false;
    public boolean supportNonSamsung = false;
    public boolean supportTablet = true;
    public boolean supportsBLEOnly = false;
    public String switchGearTitleIcon;
    public String viClass;

    public GearInfo(String str, String str2, String str3) {
        if (str != null) {
            this.deviceName = str.trim();
        }
        if (str2 != null) {
            this.containerPackage = str2.trim();
        }
        if (str3 != null) {
            this.pluginPackage = str3.trim();
        }
    }

    public String getContainerPackageName() {
        return TextUtils.isEmpty(this.containerPackage) ? this.pluginPackage : this.containerPackage;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("deviceName: ");
        stringBuffer.append(this.deviceName);
        stringBuffer.append("\ncontainerPackage: ");
        stringBuffer.append(this.containerPackage);
        stringBuffer.append("\npluginPackage: ");
        stringBuffer.append(this.pluginPackage);
        stringBuffer.append("\nsupportTablet: ");
        stringBuffer.append(this.supportTablet);
        stringBuffer.append("\nsupportNonSamsung: ");
        stringBuffer.append(this.supportNonSamsung);
        stringBuffer.append("\nsupportMultiConnection: ");
        stringBuffer.append(this.supportMultiConnection);
        stringBuffer.append("\nconnectAsAudio: ");
        stringBuffer.append(this.connectAsAudio);
        stringBuffer.append("\nhostMinMemory: ");
        stringBuffer.append(this.hostMinMemory);
        stringBuffer.append("MB");
        stringBuffer.append("\nrequiresPairing: ");
        stringBuffer.append(this.requiresPairing);
        stringBuffer.append("\nsupportsBLEOnly: ");
        stringBuffer.append(this.supportsBLEOnly);
        stringBuffer.append("\nswitchGearTitleIcon: ");
        stringBuffer.append(this.switchGearTitleIcon);
        stringBuffer.append("\npluginAppName: ");
        stringBuffer.append(this.pluginAppName);
        return stringBuffer.toString();
    }
}
