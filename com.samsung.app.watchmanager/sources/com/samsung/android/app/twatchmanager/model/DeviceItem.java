package com.samsung.android.app.twatchmanager.model;

public class DeviceItem {
    public boolean connectAsAudio = false;
    public String deviceName;
    public GearGroup gearGroup;
    public int hostMinMemory = 1024;
    public String iconDrawableName = "oobe_drawer_ic_watch";
    public String pairingImageName = "oobe_pairing_img_watch_select_01";
    public boolean requiresPairing = true;
    public boolean supportMultiConnection;
    public boolean supportNonSamsung;
    public boolean supportTablet = true;
    public boolean supportsBLEOnly = false;
    public String switchGearTitleIcon;
}
