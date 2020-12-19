package com.samsung.android.app.twatchmanager.btutil;

import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManufacturerData {
    private static final int MANUFACTURER_SS_LE_ASSOCIATED_SERVICE_DATA_BTMAC = 8;
    private static final int MANUFACTURER_SS_LE_ASSOCIATED_SERVICE_DATA_CRC = 1;
    private static final int MANUFACTURER_SS_LE_ASSOCIATED_SERVICE_DATA_HASH = 4;
    private static final int MANUFACTURER_SS_LE_ASSOCIATED_SERVICE_DATA_LEGNTH_BTMAC = 6;
    private static final int MANUFACTURER_SS_LE_ASSOCIATED_SERVICE_DATA_LEGNTH_CRC = 2;
    private static final int MANUFACTURER_SS_LE_ASSOCIATED_SERVICE_DATA_LEGNTH_HASH = 9;
    private static final int MANUFACTURER_SS_LE_ASSOCIATED_SERVICE_DATA_LEGNTH_P2PMAC = 6;
    private static final int MANUFACTURER_SS_LE_ASSOCIATED_SERVICE_DATA_P2PMAC = 2;
    private static final String TAG = ("tUHM;" + ManufacturerData.class.getSimpleName());
    public final int MANUFACTURER_DEVICE_ID_GEAR = 1;
    public final int MANUFACTURER_DEVICE_ID_GEAR_360 = 254;
    public final int MANUFACTURER_DEVICE_ID_GEAR_VR_CONTROLLER = 251;
    public final int MANUFACTURER_DEVICE_ID_GLOBE = 219;
    public final int MANUFACTURER_DEVICE_ID_WEARABLE_MAX = 255;
    public final int MANUFACTURER_DEVICE_ID_WEARABLE_MIN = 144;
    public final byte MANUFACTURER_DEVICE_TYPE_BLE = 2;
    public final byte MANUFACTURER_DEVICE_TYPE_CLASSIC = 1;
    public final byte MANUFACTURER_DEVICE_TYPE_DEFAULT = 0;
    public final byte MANUFACTURER_DEVICE_TYPE_HF_ROLE_SUPPORTED = 2;
    public final byte MANUFACTURER_DEVICE_TYPE_HIDDEN_CONDITION = 1;
    public final byte MANUFACTURER_DEVICE_TYPE_NONE = 0;
    public final byte MANUFACTURER_DEVICE_TYPE_SLD_L = 3;
    public final byte MANUFACTURER_DEVICE_TYPE_SLD_R = 4;
    private int MANUFACTURER_LENGTH_SS_LE_ASSOCIATED_SERVICE_DATA;
    private int MANUFACTURER_LENGTH_SS_LE_CONNECTIVITY;
    private int MANUFACTURER_LENGTH_SS_LE_DEVICE;
    private int MANUFACTURER_LENGTH_SS_LE_PACKET_NUMBER;
    private int MANUFACTURER_LENGTH_SS_LE_PROXIMITY;
    private int MANUFACTURER_OFFSET_OLD_DEVICE_ID = 7;
    private int MANUFACTURER_OFFSET_OLD_DEVICE_TYPE = 10;
    private int MANUFACTURER_OFFSET_OLD_SERVICE_ID = 5;
    private int MANUFACTURER_OFFSET_SS_ASSOCIATED_SERVICE_ID = 7;
    private int MANUFACTURER_OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA = 31;
    private int MANUFACTURER_OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA_DEVICE_ID = 1;
    private int MANUFACTURER_OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA_DEVICE_TYPE = 3;
    private int MANUFACTURER_OFFSET_SS_BREDR_DEVICE_CONTACT_CRC = 15;
    private int MANUFACTURER_OFFSET_SS_BREDR_DEVICE_CONTACT_HASH = 12;
    private int MANUFACTURER_OFFSET_SS_BREDR_DEVICE_ICON = 11;
    private int MANUFACTURER_OFFSET_SS_BREDR_DEVICE_TYPE = 10;
    private int MANUFACTURER_OFFSET_SS_BREDR_PROXIMITY_INFO = 9;
    private int MANUFACTURER_OFFSET_SS_BREDR_PROXIMITY_TYPE = 8;
    private int MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_CONTACT_CRC;
    private int MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_DEVICE_ID;
    private int MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_DEVICE_TYPE;
    private int MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_EXTRA;
    private int MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH;
    private int MANUFACTURER_OFFSET_SS_LE_DEVICE_CONTACT_HASH;
    private int MANUFACTURER_OFFSET_SS_LE_DEVICE_ICON;
    private int MANUFACTURER_OFFSET_SS_LE_DEVICE_TYPE;
    private int MANUFACTURER_OFFSET_SS_LE_FEATURES = 8;
    private int MANUFACTURER_OFFSET_SS_LE_PROXIMITY_INFO;
    private int MANUFACTURER_OFFSET_SS_LE_PROXIMITY_TYPE;
    private int MANUFACTURER_OFFSET_SS_SERVICE_ID = 5;
    private final byte MANUFACTURER_SS_LE_ASSOCIATED_SERVICE_DATA_FIELD;
    private final byte MANUFACTURER_SS_LE_CONNECTIVITY_FIELD;
    private final byte MANUFACTURER_SS_LE_DEVICE_FIELD;
    private final byte MANUFACTURER_SS_LE_PACKET_NUMBER_FIELD;
    private final byte MANUFACTURER_SS_LE_PROXIMITY_FIELD;
    public final int MANUFACTURER_TYPE_DEFAULT = 0;
    public final int MANUFACTURER_TYPE_OLD = 1;
    public final int MANUFACTURER_TYPE_SS_BREDR = 2;
    public final int MANUFACTURER_TYPE_SS_LE = 3;
    private Data mData;
    private byte[] mManufacturerRawData;
    private int mManufacturerType;
    private SSdevice mSSdevice;

    public class Data {
        private byte mAssociateData;
        private byte[] mBTMacAddress = new byte[6];
        private byte mBluetoothType;
        private byte[] mContactCrc = new byte[2];
        private byte[] mContactHash = new byte[3];
        private byte mDeviceCategory = 0;
        private String mDeviceCategoryPrefix = "";
        private byte mDeviceIconIndex = 0;
        private byte[] mDeviceId = new byte[2];
        private int mTxPower = 0;

        public Data() {
            byte[] bArr = this.mContactHash;
            bArr[0] = 0;
            bArr[1] = 0;
            bArr[2] = 0;
            byte[] bArr2 = this.mContactCrc;
            bArr2[0] = 0;
            bArr2[1] = 0;
            byte[] bArr3 = this.mDeviceId;
            bArr3[0] = 0;
            bArr3[1] = 0;
            this.mBluetoothType = 0;
            for (int i = 0; i < 6; i++) {
                this.mBTMacAddress[i] = 0;
            }
            this.mAssociateData = 0;
        }

        public byte getAssociateData() {
            return this.mAssociateData;
        }

        public byte[] getBTMacAddress() {
            return this.mBTMacAddress;
        }

        public byte getBluetoothType() {
            return this.mBluetoothType;
        }

        public byte[] getContactCrc() {
            return this.mContactCrc;
        }

        public byte[] getContactHash() {
            return this.mContactHash;
        }

        public byte getDeviceCategory() {
            return this.mDeviceCategory;
        }

        public byte getDeviceIconIndex() {
            return this.mDeviceIconIndex;
        }

        public byte[] getDeviceId() {
            return this.mDeviceId;
        }

        public String getPrefixName() {
            return this.mDeviceCategoryPrefix;
        }

        public int getTxPower() {
            return this.mTxPower;
        }

        /* access modifiers changed from: protected */
        public void setAssociateData(byte b2) {
            this.mAssociateData = b2;
        }

        /* access modifiers changed from: protected */
        public void setBTMacAddress(byte[] bArr, int i) {
            System.arraycopy(bArr, i, this.mBTMacAddress, 0, 6);
        }

        /* access modifiers changed from: protected */
        public void setBluetoothType(byte b2) {
            this.mBluetoothType = b2;
        }

        /* access modifiers changed from: protected */
        public void setContactCrc(byte[] bArr, int i) {
            System.arraycopy(bArr, i, this.mContactCrc, 0, 2);
        }

        /* access modifiers changed from: protected */
        public void setContactHash(byte[] bArr, int i) {
            System.arraycopy(bArr, i, this.mContactHash, 0, 3);
        }

        /* access modifiers changed from: protected */
        public void setDeviceCategory(byte b2) {
            this.mDeviceCategory = b2;
        }

        /* access modifiers changed from: protected */
        public void setDeviceIconIndex(byte b2) {
            this.mDeviceIconIndex = b2;
        }

        /* access modifiers changed from: protected */
        public void setDeviceId(byte[] bArr, int i) {
            System.arraycopy(bArr, i, this.mDeviceId, 0, 2);
        }

        /* access modifiers changed from: protected */
        public void setPrefixName(String str) {
            this.mDeviceCategoryPrefix = str;
        }

        /* access modifiers changed from: protected */
        public void setTxPower(int i) {
            this.mTxPower = i;
        }
    }

    public class SSdevice {
        public final byte ACCESSORY = 5;
        public final byte AIRPURIFIER = 15;
        public final byte AV = 7;
        public final byte BAND = 34;
        public final byte BD = 36;
        public final byte CAMCORDER = 27;
        public final byte CAMERA = 26;
        public final byte COOKTOP = 28;
        public final byte DISHWASHER = 29;
        public final byte DRYER = 11;
        public final byte EBOARD = 24;
        public final byte FLOOR_AC = 12;
        public final byte HEADPHONE = 21;
        public final byte HOOD = 31;
        public final byte IOT = 25;
        public final byte KIMCHI_REFRIGERATOR = 32;
        public final byte MICROWAVEOVEN = 30;
        public final byte MONITOR = 23;
        public final byte OVEN = 16;
        public final byte PC = 4;
        public final byte PHONE = 1;
        public final byte PRINTER = 20;
        public final byte RANGE = 17;
        public final byte REFRIGERATOR = 9;
        public final byte ROBOT_VACUUM = 18;
        public final byte ROOM_AC = 13;
        public final byte ROUTER = 35;
        public final byte SIGNAGE = 8;
        public final byte SMART_HOME = 19;
        public final byte SPEAKER = 22;
        public final byte SYSTEM_AC = 14;
        public final byte TABLET = 2;
        public final byte TV = 6;
        public final byte WASHER = 10;
        public final byte WATCH = 33;
        public final byte WEARABLE = 3;
        private final List<String> mCategoryPrefixList = new ArrayList(Arrays.asList("[Phone] ", "[Tablet] ", "[Wearable] ", "[PC] ", "[Accessory] ", "[TV] ", "[AV] ", "[Signage] ", "[Refrigerator] ", "[Washer] ", "[Dryer] ", "[Floor A/C] ", "[Room A/C] ", "[System A/C] ", "[Air Purifier] ", "[Oven] ", "[Range] ", "[Robot Vacuum] ", "[Smart Home] ", "[Printer] ", "[Headphone] ", "[Speaker] ", "[Monitor] ", "[E-Board] ", "[IoT] ", "[Camera] ", "[Camcorder] ", "[Cooktop] ", "[Dish Washer] ", "[Microwave Oven] ", "[Hood] ", "[KimchiRef] ", "[Watch] ", "[Band] ", "[Router] ", "[BD] "));

        public SSdevice() {
        }

        public String getCategoryPrefix(byte b2) {
            int i;
            return (b2 <= 0 || this.mCategoryPrefixList.size() <= (i = b2 + -1)) ? "" : this.mCategoryPrefixList.get(i);
        }
    }

    public ManufacturerData(byte[] bArr) {
        int i = this.MANUFACTURER_OFFSET_SS_LE_FEATURES;
        this.MANUFACTURER_OFFSET_SS_LE_PROXIMITY_TYPE = i;
        this.MANUFACTURER_OFFSET_SS_LE_PROXIMITY_INFO = 1;
        this.MANUFACTURER_OFFSET_SS_LE_DEVICE_TYPE = i;
        this.MANUFACTURER_OFFSET_SS_LE_DEVICE_ICON = 1;
        this.MANUFACTURER_OFFSET_SS_LE_DEVICE_CONTACT_HASH = 3;
        this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH = i;
        this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_DEVICE_ID = 1;
        this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_DEVICE_TYPE = 3;
        this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_EXTRA = 4;
        this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_CONTACT_CRC = 5;
        this.MANUFACTURER_LENGTH_SS_LE_PACKET_NUMBER = 1;
        this.MANUFACTURER_LENGTH_SS_LE_PROXIMITY = 2;
        this.MANUFACTURER_LENGTH_SS_LE_DEVICE = 6;
        this.MANUFACTURER_LENGTH_SS_LE_CONNECTIVITY = 18;
        this.MANUFACTURER_LENGTH_SS_LE_ASSOCIATED_SERVICE_DATA = 5;
        this.MANUFACTURER_SS_LE_PACKET_NUMBER_FIELD = 1;
        this.MANUFACTURER_SS_LE_PROXIMITY_FIELD = 2;
        this.MANUFACTURER_SS_LE_DEVICE_FIELD = 4;
        this.MANUFACTURER_SS_LE_CONNECTIVITY_FIELD = 8;
        this.MANUFACTURER_SS_LE_ASSOCIATED_SERVICE_DATA_FIELD = 16;
        this.mManufacturerRawData = null;
        this.mManufacturerType = 0;
        this.mData = new Data();
        this.mSSdevice = new SSdevice();
        updateDeviceInfo(bArr);
    }

    private void setAssociateData(byte[] bArr) {
        this.mData.setAssociateData(bArr[this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH + this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_EXTRA]);
    }

    private void setBTMacAddress(byte[] bArr) {
        if (this.mManufacturerType != 3) {
            Log.d(TAG, "setBTMacAddress : do nothing");
            return;
        }
        byte associateData = this.mData.getAssociateData();
        int i = ((associateData & 1) > 0 ? 2 : 0) + 0 + ((associateData & 2) > 0 ? 6 : 0) + ((associateData & 4) > 0 ? 9 : 0);
        String str = TAG;
        Log.d(str, "setBTMacAddress : getAssociateData: " + String.format("%H", Integer.valueOf(associateData)));
        String str2 = TAG;
        Log.d(str2, "setBTMacAddress : offset length : " + i);
        this.mData.setBTMacAddress(bArr, this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH + this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_EXTRA + 1 + i);
    }

    private void setBluetoothType(byte[] bArr) {
        int i;
        int i2;
        Data data;
        byte b2;
        int i3 = this.mManufacturerType;
        if (i3 == 1) {
            byte[] deviceId = this.mData.getDeviceId();
            if (deviceId[0] == 0 && (i = deviceId[1] & 255) >= 144 && i <= 255 && this.mManufacturerRawData.length > (i2 = this.MANUFACTURER_OFFSET_OLD_DEVICE_TYPE)) {
                data = this.mData;
                b2 = bArr[i2];
            } else {
                return;
            }
        } else if (i3 == 2) {
            int i4 = this.MANUFACTURER_OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA;
            int i5 = bArr[i4] & 255;
            if (i5 > 0 && bArr.length > i5 + i4) {
                this.mData.setBluetoothType(bArr[i4 + this.MANUFACTURER_OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA_DEVICE_TYPE]);
                return;
            }
            return;
        } else if (i3 == 3 && isSupportFeature((byte) 16)) {
            data = this.mData;
            b2 = bArr[this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH + this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_DEVICE_TYPE];
        } else {
            return;
        }
        data.setBluetoothType(b2);
    }

    private void setContactCrc(byte[] bArr) {
        int i = this.mManufacturerType;
        if (i == 2) {
            this.mData.setContactCrc(bArr, this.MANUFACTURER_OFFSET_SS_BREDR_DEVICE_CONTACT_CRC);
        } else if (i == 3 && isSupportFeature((byte) 16)) {
            int i2 = this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH;
            if ((bArr[this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_EXTRA + i2] & 1) == 1) {
                this.mData.setContactCrc(bArr, i2 + this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_CONTACT_CRC);
            }
        }
    }

    private void setContactHash(byte[] bArr) {
        int i;
        Data data;
        int i2 = this.mManufacturerType;
        if (i2 == 2) {
            data = this.mData;
            i = this.MANUFACTURER_OFFSET_SS_BREDR_DEVICE_CONTACT_HASH;
        } else if (i2 == 3 && isSupportFeature((byte) 4)) {
            data = this.mData;
            i = this.MANUFACTURER_OFFSET_SS_LE_DEVICE_TYPE + this.MANUFACTURER_OFFSET_SS_LE_DEVICE_CONTACT_HASH;
        } else {
            return;
        }
        data.setContactHash(bArr, i);
    }

    private void setDeviceCategory(byte[] bArr) {
        byte b2;
        Data data;
        int i = this.mManufacturerType;
        if (i == 2) {
            data = this.mData;
            b2 = bArr[this.MANUFACTURER_OFFSET_SS_BREDR_DEVICE_TYPE];
        } else if (i == 3 && isSupportFeature((byte) 4)) {
            data = this.mData;
            b2 = bArr[this.MANUFACTURER_OFFSET_SS_LE_DEVICE_TYPE];
        } else {
            return;
        }
        data.setDeviceCategory(b2);
    }

    private void setDeviceCategoryPrefix(byte[] bArr) {
        int i;
        int i2 = this.mManufacturerType;
        if (i2 == 2) {
            int intValue = Integer.valueOf(bArr[this.MANUFACTURER_OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA]).intValue();
            if (intValue >= 0 && bArr.length > (i = this.MANUFACTURER_OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA + intValue + 1) && bArr[i] == 91) {
                byte[] bArr2 = new byte[(bArr.length - i)];
                for (int i3 = 0; i3 < bArr2.length; i3++) {
                    bArr2[i3] = bArr[i3 + i];
                    if (bArr2[i3] == 93) {
                        break;
                    } else if (i3 != bArr2.length - 1) {
                    }
                }
                this.mData.setPrefixName(new String(bArr2).trim() + " ");
                return;
            }
            this.mData.setPrefixName("");
        } else if (i2 == 3) {
            Data data = this.mData;
            data.setPrefixName(this.mSSdevice.getCategoryPrefix(data.mDeviceCategory));
        }
    }

    private void setDeviceIconIndex(byte[] bArr) {
        byte b2;
        Data data;
        int i = this.mManufacturerType;
        if (i == 2) {
            data = this.mData;
            b2 = bArr[this.MANUFACTURER_OFFSET_SS_BREDR_DEVICE_ICON];
        } else if (i == 3 && isSupportFeature((byte) 4)) {
            data = this.mData;
            b2 = bArr[this.MANUFACTURER_OFFSET_SS_LE_DEVICE_TYPE + this.MANUFACTURER_OFFSET_SS_LE_DEVICE_ICON];
        } else {
            return;
        }
        data.setDeviceIconIndex(b2);
    }

    private void setDeviceId(byte[] bArr) {
        int i;
        Data data;
        int i2 = this.mManufacturerType;
        if (i2 == 1) {
            data = this.mData;
            i = this.MANUFACTURER_OFFSET_OLD_DEVICE_ID;
        } else if (i2 == 2) {
            int i3 = this.MANUFACTURER_OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA;
            int i4 = bArr[i3] & 255;
            if (i4 > 0 && bArr.length > i4 + i3) {
                this.mData.setDeviceId(bArr, i3 + this.MANUFACTURER_OFFSET_SS_BREDR_ASSOCIATED_SERVICE_DATA_DEVICE_ID);
                return;
            }
            return;
        } else if (i2 == 3 && isSupportFeature((byte) 16)) {
            data = this.mData;
            i = this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH + this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_DEVICE_ID;
        } else {
            return;
        }
        data.setDeviceId(bArr, i);
    }

    private void setManufacturerRawData(byte[] bArr) {
        this.mManufacturerRawData = bArr;
    }

    private void setManufacturerType(byte[] bArr) {
        int i;
        if (bArr == null || bArr.length < 9) {
            this.mManufacturerType = 0;
            return;
        }
        int i2 = this.MANUFACTURER_OFFSET_OLD_SERVICE_ID;
        if (bArr[i2] == 0 && bArr[i2 + 1] == 2) {
            this.mManufacturerType = 1;
        } else if (bArr[this.MANUFACTURER_OFFSET_SS_SERVICE_ID] == 9 && bArr[this.MANUFACTURER_OFFSET_SS_ASSOCIATED_SERVICE_ID] == 0) {
            this.mManufacturerType = 2;
        } else if (bArr[this.MANUFACTURER_OFFSET_SS_SERVICE_ID] == 9 && bArr[this.MANUFACTURER_OFFSET_SS_ASSOCIATED_SERVICE_ID] == 2) {
            this.mManufacturerType = 3;
            int i3 = this.MANUFACTURER_OFFSET_SS_LE_FEATURES;
            byte b2 = bArr[i3];
            int i4 = i3 + 1;
            for (int i5 = 0; i5 < 5; i5++) {
                byte b3 = (byte) (((byte) (1 << i5)) & b2);
                if (b3 == 1) {
                    i = this.MANUFACTURER_LENGTH_SS_LE_PACKET_NUMBER;
                } else if (b3 == 2) {
                    this.MANUFACTURER_OFFSET_SS_LE_PROXIMITY_TYPE = i4;
                    i = this.MANUFACTURER_LENGTH_SS_LE_PROXIMITY;
                } else if (b3 == 4) {
                    this.MANUFACTURER_OFFSET_SS_LE_DEVICE_TYPE = i4;
                    i = this.MANUFACTURER_LENGTH_SS_LE_DEVICE;
                } else if (b3 == 8) {
                    i = this.MANUFACTURER_LENGTH_SS_LE_CONNECTIVITY;
                } else if (b3 == 16) {
                    this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH = i4;
                    this.MANUFACTURER_LENGTH_SS_LE_ASSOCIATED_SERVICE_DATA = bArr[this.MANUFACTURER_OFFSET_SS_LE_ASSOCIATED_SERVICE_DATA_LENGTH] + 1;
                    i = this.MANUFACTURER_LENGTH_SS_LE_ASSOCIATED_SERVICE_DATA;
                }
                i4 += i;
            }
        } else {
            this.mManufacturerType = 0;
        }
    }

    private void setTxPower(byte[] bArr) {
        int i = this.mManufacturerType;
        if (i != 2) {
            if (i != 3) {
                this.mData.setTxPower(0);
            } else if (isSupportFeature((byte) 2)) {
                int i2 = this.MANUFACTURER_OFFSET_SS_LE_PROXIMITY_TYPE;
                if (bArr[i2] == 1) {
                    this.mData.setTxPower(bArr[i2 + this.MANUFACTURER_OFFSET_SS_LE_PROXIMITY_INFO]);
                }
            }
        } else if ((bArr[this.MANUFACTURER_OFFSET_SS_BREDR_PROXIMITY_TYPE] & 1) == 1) {
            this.mData.setTxPower(bArr[this.MANUFACTURER_OFFSET_SS_BREDR_PROXIMITY_INFO]);
        }
    }

    public String byteToString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b2 : bArr) {
            sb.append("0123456789abcdef".charAt((b2 & 240) >> 4));
            sb.append("0123456789abcdef".charAt(b2 & 15));
        }
        return sb.toString();
    }

    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ManufacturerType] " + this.mManufacturerType);
        sb.append(", [TxPower] " + this.mData.getTxPower());
        sb.append(", [DeviceCategory] " + ((int) this.mData.getDeviceCategory()));
        sb.append(", [DeviceIconIndex] " + ((int) this.mData.getDeviceIconIndex()));
        sb.append(", [DevicePrefix] " + this.mData.getPrefixName());
        sb.append(", [Contact] " + byteToString(this.mData.getContactHash()) + byteToString(this.mData.getContactCrc()));
        StringBuilder sb2 = new StringBuilder();
        sb2.append(", [Device ID] ");
        sb2.append(byteToString(this.mData.getDeviceId()));
        sb.append(sb2.toString());
        sb.append(", [BT Type] " + ((int) this.mData.getBluetoothType()));
        sb.append(", [Associate Data] " + ((int) this.mData.getAssociateData()));
        sb.append(", [BT MAC] " + byteToString(this.mData.getBTMacAddress()));
        return sb.toString();
    }

    public byte getAssociateData() {
        return this.mData.getAssociateData();
    }

    public byte[] getBTMacAddress() {
        return this.mData.getBTMacAddress();
    }

    public byte getBluetoothType() {
        return this.mData.getBluetoothType();
    }

    public byte[] getContactCrc() {
        return this.mData.getContactCrc();
    }

    public byte[] getContactHash() {
        return this.mData.getContactHash();
    }

    public byte getDeviceCategory() {
        return this.mData.getDeviceCategory();
    }

    public byte getDeviceIconIndex() {
        return this.mData.getDeviceIconIndex();
    }

    public byte[] getDeviceId() {
        return this.mData.getDeviceId();
    }

    public byte[] getManufacturerRawData() {
        return this.mManufacturerRawData;
    }

    public int getManufacturerType() {
        return this.mManufacturerType;
    }

    public String getPrefixName() {
        return this.mData.getPrefixName();
    }

    public int getTxPower() {
        return this.mData.getTxPower();
    }

    public boolean haveBRDevice() {
        Data data = this.mData;
        boolean z = false;
        if (data == null) {
            return false;
        }
        if ((data.getAssociateData() & 8) > 0) {
            z = true;
        }
        String str = TAG;
        Log.d(str, "haveBRDevice : " + z);
        return z;
    }

    public boolean isOldManufacturerType() {
        return this.mManufacturerRawData != null && this.mManufacturerType == 1;
    }

    public boolean isSSBLEManufacturerType() {
        return this.mManufacturerRawData != null && this.mManufacturerType == 3;
    }

    public boolean isSSManufacturerType() {
        if (this.mManufacturerRawData == null) {
            return false;
        }
        int i = this.mManufacturerType;
        return i == 2 || i == 3;
    }

    public boolean isSupportFeature(byte b2) {
        byte[] bArr;
        return this.mManufacturerType == 3 && (bArr = this.mManufacturerRawData) != null && (bArr[this.MANUFACTURER_OFFSET_SS_LE_FEATURES] & b2) == b2;
    }

    public void updateDeviceInfo(byte[] bArr) {
        if (bArr != null) {
            setManufacturerRawData(bArr);
            setManufacturerType(bArr);
            setTxPower(bArr);
            setDeviceCategory(bArr);
            setDeviceIconIndex(bArr);
            setDeviceCategoryPrefix(bArr);
            setContactHash(bArr);
            setContactCrc(bArr);
            setDeviceId(bArr);
            setBluetoothType(bArr);
            if (isSSBLEManufacturerType()) {
                setAssociateData(bArr);
                if (haveBRDevice()) {
                    setBTMacAddress(bArr);
                }
            }
        }
    }
}
