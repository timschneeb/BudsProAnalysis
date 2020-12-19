package com.samsung.accessory.hearablemgr.core.fmm;

import com.google.gson.JsonObject;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.fmm.base.FmmBaseModel;
import com.samsung.accessory.hearablemgr.core.fmm.utils.FmmConfig;
import com.samsung.accessory.hearablemgr.core.fmm.utils.FmmConstants;
import com.samsung.accessory.hearablemgr.core.fmm.utils.FmmUtils;
import seccompat.android.util.Log;

public class FmmModels {
    private static final String TAG = (Application.TAG_ + FmmModels.class.getSimpleName());
    private static FmmConfig mFmmConfigValue;

    public static class DeviceInfoModel extends FmmBaseModel {
        String mUid;

        public DeviceInfoModel(String str, String str2) {
            super(str, str2);
            this.mUid = str2;
        }

        /* access modifiers changed from: protected */
        @Override // com.samsung.accessory.hearablemgr.core.fmm.base.FmmBaseModel
        public JsonObject makeJsonData() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(FmmConstants.fmmConfig.sn, FmmUtils.getLeftSerialNumber());
            jsonObject.addProperty("isConnected", FmmUtils.getLeftIsConnected());
            jsonObject.addProperty("battery", Integer.valueOf(FmmUtils.getLeftBattery()));
            jsonObject.addProperty("status", Integer.valueOf(FmmUtils.getLeftDeviceStatus()));
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty(FmmConstants.fmmConfig.sn, FmmUtils.getRightSerialNumber());
            jsonObject2.addProperty("isConnected", FmmUtils.getRightIsConnected());
            jsonObject2.addProperty("battery", Integer.valueOf(FmmUtils.getRightBattery()));
            jsonObject2.addProperty("status", Integer.valueOf(FmmUtils.getRightDeviceStatus()));
            JsonObject jsonObject3 = new JsonObject();
            if (FmmUtils.getLeftIsConnected().booleanValue()) {
                jsonObject3.add("left", jsonObject);
            }
            if (FmmUtils.getRightIsConnected().booleanValue()) {
                jsonObject3.add("right", jsonObject2);
            }
            JsonObject jsonObject4 = new JsonObject();
            jsonObject4.addProperty("model_name", "Galaxy Buds Pro");
            jsonObject4.addProperty("nickname", FmmUtils.getNickName(this.mUid));
            jsonObject4.addProperty("model_number", "SM-R190");
            jsonObject4.addProperty("fwVer", FmmUtils.getFwVersion());
            jsonObject4.add("supportOprtList", FmmUtils.getSupportOprtList());
            jsonObject4.add("detail", jsonObject3);
            return jsonObject4;
        }
    }

    public static class SetDeviceInfoModel extends FmmBaseModel {
        private int resultCodeLeft;
        private int resultCodeRight;

        public SetDeviceInfoModel(String str) {
            super("SET_DEVICE_INFO", str);
            init();
        }

        private void init() {
            this.resultCodeLeft = !FmmUtils.getLeftIsConnected().booleanValue();
            this.resultCodeRight = !FmmUtils.getRightIsConnected().booleanValue();
        }

        /* access modifiers changed from: protected */
        @Override // com.samsung.accessory.hearablemgr.core.fmm.base.FmmBaseModel
        public JsonObject makeJsonData() {
            JsonObject jsonObject = new JsonObject();
            String str = "success";
            jsonObject.addProperty("result", this.resultCodeLeft == 0 ? str : FmmConstants.Result.FAIL);
            jsonObject.addProperty("result_code", Integer.valueOf(this.resultCodeLeft));
            JsonObject jsonObject2 = new JsonObject();
            if (this.resultCodeRight != 0) {
                str = FmmConstants.Result.FAIL;
            }
            jsonObject2.addProperty("result", str);
            jsonObject2.addProperty("result_code", Integer.valueOf(this.resultCodeRight));
            JsonObject jsonObject3 = new JsonObject();
            jsonObject3.add("left", jsonObject);
            jsonObject3.add("right", jsonObject2);
            JsonObject jsonObject4 = new JsonObject();
            jsonObject4.add("detail", jsonObject3);
            return jsonObject4;
        }
    }

    public static class getDeviceInfoModel extends FmmBaseModel {
        String mUid;

        public getDeviceInfoModel(String str, String str2, FmmConfig fmmConfig) {
            super(str, str2);
            this.mUid = str2;
            FmmConfig unused = FmmModels.mFmmConfigValue = fmmConfig;
        }

        /* access modifiers changed from: protected */
        @Override // com.samsung.accessory.hearablemgr.core.fmm.base.FmmBaseModel
        public JsonObject makeJsonData() {
            JsonObject jsonObject = new JsonObject();
            FmmConfig unused = FmmModels.mFmmConfigValue;
            jsonObject.addProperty(FmmConstants.fmmConfig.sn, FmmConfig.left_sn);
            FmmConfig unused2 = FmmModels.mFmmConfigValue;
            jsonObject.addProperty(FmmConstants.fmmConfig.fmmToken, FmmConfig.left_fmmToken);
            FmmConfig unused3 = FmmModels.mFmmConfigValue;
            jsonObject.addProperty(FmmConstants.fmmConfig.findingSupport, FmmConfig.left_findingSupport);
            FmmConfig unused4 = FmmModels.mFmmConfigValue;
            jsonObject.addProperty(FmmConstants.fmmConfig.e2e, FmmConfig.left_e2e);
            FmmConfig unused5 = FmmModels.mFmmConfigValue;
            jsonObject.addProperty(FmmConstants.fmmConfig.secretKey, FmmConfig.left_secretKey);
            FmmConfig unused6 = FmmModels.mFmmConfigValue;
            jsonObject.addProperty(FmmConstants.fmmConfig.iv, FmmConfig.left_iv);
            FmmConfig unused7 = FmmModels.mFmmConfigValue;
            jsonObject.addProperty(FmmConstants.fmmConfig.maxN, Integer.valueOf(FmmConfig.left_maxN));
            FmmConfig unused8 = FmmModels.mFmmConfigValue;
            jsonObject.addProperty("region", Integer.valueOf(FmmConfig.left_region));
            JsonObject jsonObject2 = new JsonObject();
            FmmConfig unused9 = FmmModels.mFmmConfigValue;
            jsonObject2.addProperty(FmmConstants.fmmConfig.sn, FmmConfig.right_sn);
            FmmConfig unused10 = FmmModels.mFmmConfigValue;
            jsonObject2.addProperty(FmmConstants.fmmConfig.fmmToken, FmmConfig.right_fmmToken);
            FmmConfig unused11 = FmmModels.mFmmConfigValue;
            jsonObject2.addProperty(FmmConstants.fmmConfig.findingSupport, FmmConfig.right_findingSupport);
            FmmConfig unused12 = FmmModels.mFmmConfigValue;
            jsonObject2.addProperty(FmmConstants.fmmConfig.e2e, FmmConfig.right_e2e);
            FmmConfig unused13 = FmmModels.mFmmConfigValue;
            jsonObject2.addProperty(FmmConstants.fmmConfig.secretKey, FmmConfig.right_secretKey);
            FmmConfig unused14 = FmmModels.mFmmConfigValue;
            jsonObject2.addProperty(FmmConstants.fmmConfig.iv, FmmConfig.right_iv);
            FmmConfig unused15 = FmmModels.mFmmConfigValue;
            jsonObject2.addProperty(FmmConstants.fmmConfig.maxN, Integer.valueOf(FmmConfig.right_maxN));
            FmmConfig unused16 = FmmModels.mFmmConfigValue;
            jsonObject2.addProperty("region", Integer.valueOf(FmmConfig.right_region));
            JsonObject jsonObject3 = new JsonObject();
            jsonObject3.addProperty(FmmConstants.fmmConfig.sn, FmmUtils.getLeftSerialNumber());
            jsonObject3.addProperty("isConnected", FmmUtils.getLeftIsConnected());
            jsonObject3.addProperty("battery", Integer.valueOf(FmmUtils.getLeftBattery()));
            jsonObject3.addProperty("status", Integer.valueOf(FmmUtils.getLeftDeviceStatus()));
            jsonObject3.add("fmmConfig", jsonObject);
            JsonObject jsonObject4 = new JsonObject();
            jsonObject4.addProperty(FmmConstants.fmmConfig.sn, FmmUtils.getRightSerialNumber());
            jsonObject4.addProperty("isConnected", FmmUtils.getRightIsConnected());
            jsonObject4.addProperty("battery", Integer.valueOf(FmmUtils.getRightBattery()));
            jsonObject4.addProperty("status", Integer.valueOf(FmmUtils.getRightDeviceStatus()));
            jsonObject4.add("fmmConfig", jsonObject2);
            JsonObject jsonObject5 = new JsonObject();
            if (FmmUtils.getLeftIsConnected().booleanValue()) {
                jsonObject5.add("left", jsonObject3);
            }
            if (FmmUtils.getRightIsConnected().booleanValue()) {
                jsonObject5.add("right", jsonObject4);
            }
            JsonObject jsonObject6 = new JsonObject();
            jsonObject6.addProperty("model_name", "Galaxy Buds Pro");
            jsonObject6.addProperty("nickname", FmmUtils.getNickName(this.mUid));
            jsonObject6.addProperty("model_number", "SM-R190");
            jsonObject6.addProperty("fwVer", FmmUtils.getFwVersion());
            jsonObject6.add("supportOprtList", FmmUtils.getSupportOprtList());
            jsonObject6.add("detail", jsonObject5);
            return jsonObject6;
        }
    }

    public static class ConnectionModel extends FmmBaseModel {
        boolean mIsConnected;

        public ConnectionModel(String str, boolean z) {
            super(FmmConstants.Operation.CONNECTION, str);
            this.mIsConnected = z;
        }

        /* access modifiers changed from: protected */
        @Override // com.samsung.accessory.hearablemgr.core.fmm.base.FmmBaseModel
        public JsonObject makeJsonData() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(FmmConstants.fmmConfig.sn, FmmUtils.getLeftSerialNumber());
            jsonObject.addProperty("isConnected", FmmUtils.getLeftIsConnected());
            jsonObject.addProperty("battery", Integer.valueOf(FmmUtils.getLeftBattery()));
            jsonObject.addProperty("status", Integer.valueOf(FmmUtils.getLeftDeviceStatus()));
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty(FmmConstants.fmmConfig.sn, FmmUtils.getRightSerialNumber());
            jsonObject2.addProperty("isConnected", FmmUtils.getRightIsConnected());
            jsonObject2.addProperty("battery", Integer.valueOf(FmmUtils.getRightBattery()));
            jsonObject2.addProperty("status", Integer.valueOf(FmmUtils.getRightDeviceStatus()));
            JsonObject jsonObject3 = new JsonObject();
            if (FmmUtils.getLeftIsConnected().booleanValue()) {
                jsonObject3.add("left", jsonObject);
            }
            if (FmmUtils.getRightIsConnected().booleanValue()) {
                jsonObject3.add("right", jsonObject2);
            }
            JsonObject jsonObject4 = new JsonObject();
            jsonObject4.addProperty("isConnected", Boolean.valueOf(this.mIsConnected));
            jsonObject4.add("detail", jsonObject3);
            return jsonObject4;
        }
    }

    public static class ChangeStatusModel extends FmmBaseModel {
        public ChangeStatusModel(String str, String str2, int i) {
            super(str, str2);
        }

        /* access modifiers changed from: protected */
        @Override // com.samsung.accessory.hearablemgr.core.fmm.base.FmmBaseModel
        public JsonObject makeJsonData() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(FmmConstants.fmmConfig.sn, FmmUtils.getLeftSerialNumber());
            jsonObject.addProperty("isConnected", FmmUtils.getLeftIsConnected());
            jsonObject.addProperty("battery", Integer.valueOf(FmmUtils.getLeftBattery()));
            jsonObject.addProperty("status", Integer.valueOf(FmmUtils.getLeftDeviceStatus()));
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty(FmmConstants.fmmConfig.sn, FmmUtils.getRightSerialNumber());
            jsonObject2.addProperty("isConnected", FmmUtils.getRightIsConnected());
            jsonObject2.addProperty("battery", Integer.valueOf(FmmUtils.getRightBattery()));
            jsonObject2.addProperty("status", Integer.valueOf(FmmUtils.getRightDeviceStatus()));
            JsonObject jsonObject3 = new JsonObject();
            if (FmmUtils.getLeftIsConnected().booleanValue()) {
                jsonObject3.add("left", jsonObject);
            }
            if (FmmUtils.getRightIsConnected().booleanValue()) {
                jsonObject3.add("right", jsonObject2);
            }
            JsonObject jsonObject4 = new JsonObject();
            jsonObject4.add("detail", jsonObject3);
            return jsonObject4;
        }
    }

    public static class ConnectionCheckModel extends FmmBaseModel {
        public ConnectionCheckModel(String str) {
            super(FmmConstants.Operation.CONNECTION_CHECK, str);
        }

        /* access modifiers changed from: protected */
        @Override // com.samsung.accessory.hearablemgr.core.fmm.base.FmmBaseModel
        public JsonObject makeJsonData() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(FmmConstants.fmmConfig.sn, FmmUtils.getLeftSerialNumber());
            jsonObject.addProperty("isConnected", FmmUtils.getLeftIsConnected());
            jsonObject.addProperty("battery", Integer.valueOf(FmmUtils.getLeftBattery()));
            jsonObject.addProperty("status", Integer.valueOf(FmmUtils.getLeftDeviceStatus()));
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty(FmmConstants.fmmConfig.sn, FmmUtils.getRightSerialNumber());
            jsonObject2.addProperty("isConnected", FmmUtils.getRightIsConnected());
            jsonObject2.addProperty("battery", Integer.valueOf(FmmUtils.getRightBattery()));
            jsonObject2.addProperty("status", Integer.valueOf(FmmUtils.getRightDeviceStatus()));
            JsonObject jsonObject3 = new JsonObject();
            if (FmmUtils.getLeftIsConnected().booleanValue()) {
                jsonObject3.add("left", jsonObject);
            }
            if (FmmUtils.getRightIsConnected().booleanValue()) {
                jsonObject3.add("right", jsonObject2);
            }
            JsonObject jsonObject4 = new JsonObject();
            jsonObject4.addProperty("isConnected", FmmUtils.getIsConnected());
            jsonObject4.add("detail", jsonObject3);
            return jsonObject4;
        }
    }

    public static class RingCheckModel extends FmmBaseModel {
        private String resultCode = FmmConstants.Result.FAIL;
        private int resultCodeLeft;
        private int resultCodeRight;

        public RingCheckModel(String str) {
            super(FmmConstants.Action.ACTION_OPERATION_RESPONSE, "RING", str);
        }

        public void startRing() {
            Log.d(FmmModels.TAG, "startRing()");
            int startFindMyEarbuds = FmmUtils.startFindMyEarbuds();
            if (startFindMyEarbuds != 0) {
                this.resultCodeRight = startFindMyEarbuds;
                this.resultCodeLeft = startFindMyEarbuds;
                return;
            }
            this.resultCode = "success";
            this.resultCodeRight = 0;
            this.resultCodeLeft = 0;
            if (!FmmUtils.getLeftIsConnected().booleanValue()) {
                this.resultCodeLeft = 1;
            }
            if (!FmmUtils.getRightIsConnected().booleanValue()) {
                this.resultCodeRight = 1;
            }
        }

        public void stopRing() {
            Log.d(FmmModels.TAG, "stopRing()");
            stopCheck();
            if ("success".equals(this.resultCode)) {
                FmmUtils.readyFindMyEarbuds();
            }
        }

        private void stopCheck() {
            if (!FmmUtils.isFindingEarbuds()) {
                this.resultCodeRight = 8;
                this.resultCodeLeft = 8;
                return;
            }
            this.resultCode = "success";
            this.resultCodeRight = 0;
            this.resultCodeLeft = 0;
        }

        /* access modifiers changed from: protected */
        @Override // com.samsung.accessory.hearablemgr.core.fmm.base.FmmBaseModel
        public JsonObject makeJsonData() {
            JsonObject jsonObject = new JsonObject();
            String str = "success";
            jsonObject.addProperty("result", this.resultCodeLeft == 0 ? str : FmmConstants.Result.FAIL);
            jsonObject.addProperty("result_code", Integer.valueOf(this.resultCodeLeft));
            jsonObject.addProperty("status", Integer.valueOf(FmmUtils.getLeftDeviceStatus()));
            JsonObject jsonObject2 = new JsonObject();
            if (this.resultCodeRight != 0) {
                str = FmmConstants.Result.FAIL;
            }
            jsonObject2.addProperty("result", str);
            jsonObject2.addProperty("result_code", Integer.valueOf(this.resultCodeRight));
            jsonObject2.addProperty("status", Integer.valueOf(FmmUtils.getRightDeviceStatus()));
            JsonObject jsonObject3 = new JsonObject();
            jsonObject3.add("left", jsonObject);
            jsonObject3.add("right", jsonObject2);
            JsonObject jsonObject4 = new JsonObject();
            jsonObject4.add("detail", jsonObject3);
            return jsonObject4;
        }
    }

    public static class RingStatusModel extends FmmBaseModel {
        public RingStatusModel(String str) {
            super(FmmConstants.Operation.RING_STATUS, str);
        }

        /* access modifiers changed from: protected */
        @Override // com.samsung.accessory.hearablemgr.core.fmm.base.FmmBaseModel
        public JsonObject makeJsonData() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(FmmConstants.fmmConfig.sn, FmmUtils.getLeftSerialNumber());
            jsonObject.addProperty("isConnected", FmmUtils.getLeftIsConnected());
            jsonObject.addProperty("battery", Integer.valueOf(FmmUtils.getLeftBattery()));
            jsonObject.addProperty("status", Integer.valueOf(FmmUtils.getLeftDeviceStatus()));
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty(FmmConstants.fmmConfig.sn, FmmUtils.getRightSerialNumber());
            jsonObject2.addProperty("isConnected", FmmUtils.getRightIsConnected());
            jsonObject2.addProperty("battery", Integer.valueOf(FmmUtils.getRightBattery()));
            jsonObject2.addProperty("status", Integer.valueOf(FmmUtils.getRightDeviceStatus()));
            JsonObject jsonObject3 = new JsonObject();
            if (FmmUtils.getLeftIsConnected().booleanValue()) {
                jsonObject3.add("left", jsonObject);
            }
            if (FmmUtils.getRightIsConnected().booleanValue()) {
                jsonObject3.add("right", jsonObject2);
            }
            JsonObject jsonObject4 = new JsonObject();
            jsonObject4.addProperty("isConnected", FmmUtils.getIsConnected());
            jsonObject4.add("detail", jsonObject3);
            return jsonObject4;
        }
    }

    public static class MuteModel extends FmmBaseModel {
        private int resultCodeLeft = 4;
        private int resultCodeRight = 4;
        private String resultLeft = FmmConstants.Result.FAIL;
        private String resultRight = FmmConstants.Result.FAIL;

        public MuteModel(String str, String str2) {
            super(str2, str);
        }

        public void setMuteLeft(boolean z) {
            this.resultCodeLeft = FmmUtils.setLeftMute(z);
            if (this.resultCodeLeft == 0) {
                this.resultLeft = "success";
            } else {
                this.resultLeft = FmmConstants.Result.FAIL;
            }
        }

        public void setMuteRight(boolean z) {
            this.resultCodeRight = FmmUtils.setRightMute(z);
            if (this.resultCodeRight == 0) {
                this.resultRight = "success";
            } else {
                this.resultRight = FmmConstants.Result.FAIL;
            }
        }

        /* access modifiers changed from: protected */
        @Override // com.samsung.accessory.hearablemgr.core.fmm.base.FmmBaseModel
        public JsonObject makeJsonData() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("result", this.resultLeft);
            jsonObject.addProperty("result_code", Integer.valueOf(this.resultCodeLeft));
            jsonObject.addProperty("status", Integer.valueOf(FmmUtils.getLeftDeviceStatus()));
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("result", this.resultRight);
            jsonObject2.addProperty("result_code", Integer.valueOf(this.resultCodeRight));
            jsonObject2.addProperty("status", Integer.valueOf(FmmUtils.getRightDeviceStatus()));
            JsonObject jsonObject3 = new JsonObject();
            if (FmmUtils.getLeftIsConnected().booleanValue()) {
                jsonObject3.add("left", jsonObject);
            }
            if (FmmUtils.getRightIsConnected().booleanValue()) {
                jsonObject3.add("right", jsonObject2);
            }
            JsonObject jsonObject4 = new JsonObject();
            jsonObject4.add("detail", jsonObject3);
            return jsonObject4;
        }
    }

    public static class FmmSimpleModel extends FmmBaseModel {
        /* access modifiers changed from: protected */
        @Override // com.samsung.accessory.hearablemgr.core.fmm.base.FmmBaseModel
        public JsonObject makeJsonData() {
            return null;
        }

        public FmmSimpleModel(String str, String str2, String str3) {
            super(str, str2, str3);
        }
    }

    public static class StatusChangeModel extends FmmBaseModel {
        public StatusChangeModel(String str) {
            super(FmmConstants.Operation.STATUS_CHANGE, str);
        }

        /* access modifiers changed from: protected */
        @Override // com.samsung.accessory.hearablemgr.core.fmm.base.FmmBaseModel
        public JsonObject makeJsonData() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(FmmConstants.fmmConfig.sn, FmmUtils.getLeftSerialNumber());
            jsonObject.addProperty("isConnected", FmmUtils.getLeftIsConnected());
            jsonObject.addProperty("battery", Integer.valueOf(FmmUtils.getLeftBattery()));
            jsonObject.addProperty("status", Integer.valueOf(FmmUtils.getLeftDeviceStatus()));
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty(FmmConstants.fmmConfig.sn, FmmUtils.getRightSerialNumber());
            jsonObject2.addProperty("isConnected", FmmUtils.getRightIsConnected());
            jsonObject2.addProperty("battery", Integer.valueOf(FmmUtils.getRightBattery()));
            jsonObject2.addProperty("status", Integer.valueOf(FmmUtils.getRightDeviceStatus()));
            JsonObject jsonObject3 = new JsonObject();
            if (FmmUtils.getLeftIsConnected().booleanValue()) {
                jsonObject3.add("left", jsonObject);
            }
            if (FmmUtils.getRightIsConnected().booleanValue()) {
                jsonObject3.add("right", jsonObject2);
            }
            JsonObject jsonObject4 = new JsonObject();
            jsonObject4.addProperty("isConnected", FmmUtils.getIsConnected());
            jsonObject4.add("detail", jsonObject3);
            return jsonObject4;
        }
    }
}
