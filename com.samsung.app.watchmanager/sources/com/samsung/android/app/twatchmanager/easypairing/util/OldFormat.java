package com.samsung.android.app.twatchmanager.easypairing.util;

import android.bluetooth.le.ScanResult;
import com.samsung.android.app.twatchmanager.log.Log;

public class OldFormat {
    private static final byte MANUFACTURER_FLAG = -1;
    private static final int SAMSUNG_MANUFACTURE_DATA = 117;
    private static final int SAMSUNG_MANUFACTURE_DATA_REVERSE = 29952;
    private static final String TAG = "OldFormat";
    int mDeviceID = 0;
    boolean mIsSamsungCompany = false;
    boolean mIsWearableDeviceID = false;
    int mPurpose = 1;
    int mServiceID = 0;
    int mType = 2;

    interface DeviceType {
        public static final int DEFAULT = 0;
        public static final int SUPPORT_HFP = 2;
    }

    interface Format {
        public static final int DEVICE_ID = 3;
        public static final int DEVICE_TYPE = 6;
        public static final int PURPOSE = 5;
        public static final int SERVICE_ID = 1;
        public static final int VERSION = 0;
    }

    interface Purpose {
        public static final int AUTOCONNECTION_MODE = 2;
        public static final int AUTOCONNECTION_MODE2 = 3;
        public static final int SETUP_MODE = 1;
        public static final int UNKNOWN_MODE = 0;
        public static final int WEARING_MODE = 4;
    }

    public OldFormat(ScanResult scanResult) {
        String str;
        StringBuilder sb;
        if (scanResult != null) {
            byte[] manufacturerSpecificData = scanResult.getScanRecord().getManufacturerSpecificData(SAMSUNG_MANUFACTURE_DATA_REVERSE);
            if (manufacturerSpecificData == null) {
                Log.d(TAG, "OldFormat - manufacture_data is null - get again");
                manufacturerSpecificData = scanResult.getScanRecord().getManufacturerSpecificData(117);
                if (manufacturerSpecificData == null) {
                    Log.d(TAG, "OldFormat - manufacture_data is null");
                    return;
                }
            }
            this.mIsSamsungCompany = true;
            try {
                if (manufacturerSpecificData[0] == 1) {
                    if (manufacturerSpecificData[1] == 0) {
                        this.mServiceID = manufacturerSpecificData[2];
                    }
                    if (manufacturerSpecificData[3] == 0) {
                        this.mDeviceID = manufacturerSpecificData[4];
                    }
                    if (this.mServiceID == 2 && this.mDeviceID == 1) {
                        this.mIsWearableDeviceID = true;
                    }
                    if (this.mIsWearableDeviceID) {
                        this.mPurpose = manufacturerSpecificData[5];
                        this.mType = manufacturerSpecificData[6];
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                sb = new StringBuilder();
                sb.append("ArrayIndexOutOfBoundsException : ");
                str = e.toString();
                sb.append(str);
                Log.d(TAG, sb.toString());
            } catch (Exception e2) {
                sb = new StringBuilder();
                sb.append("Exception : ");
                str = e2.toString();
                sb.append(str);
                Log.d(TAG, sb.toString());
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0042, code lost:
        if (r8[r0] != 117) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0050, code lost:
        if (r8[r3 - 2] == 117) goto L_0x0052;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0052, code lost:
        r7.mIsSamsungCompany = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0058, code lost:
        if (r8[r3 + 0] != 1) goto L_0x008c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005a, code lost:
        r0 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005e, code lost:
        if (r8[r0] != 0) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0060, code lost:
        r7.mServiceID = r8[r0 + 1];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0066, code lost:
        r0 = r3 + 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x006a, code lost:
        if (r8[r0] != 0) goto L_0x0072;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006c, code lost:
        r7.mDeviceID = r8[r0 + 1];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0074, code lost:
        if (r7.mServiceID != 2) goto L_0x007c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0078, code lost:
        if (r7.mDeviceID != 1) goto L_0x007c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x007a, code lost:
        r7.mIsWearableDeviceID = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x007e, code lost:
        if (r7.mIsWearableDeviceID == false) goto L_0x008c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0080, code lost:
        r7.mPurpose = r8[r3 + 5];
        r7.mType = r8[r3 + 6];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x008c, code lost:
        r3 = r3 + 5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public OldFormat(byte[] r8) {
        /*
        // Method dump skipped, instructions count: 145
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.easypairing.util.OldFormat.<init>(byte[]):void");
    }

    public boolean isAutoConnectionMode() {
        return this.mIsWearableDeviceID && this.mPurpose != 1;
    }

    public boolean isSetupMode() {
        return this.mIsWearableDeviceID && this.mPurpose == 1;
    }

    public boolean isSupportHFP() {
        return isAutoConnectionMode() && (this.mType & 2) != 0;
    }

    public String toString() {
        return "mIsWearableDeviceID : " + this.mIsWearableDeviceID + " / mPurpose : " + this.mPurpose + " / mType : " + this.mType;
    }
}
