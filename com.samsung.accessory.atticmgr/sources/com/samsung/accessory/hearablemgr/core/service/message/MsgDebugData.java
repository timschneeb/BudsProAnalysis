package com.samsung.accessory.hearablemgr.core.service.message;

import com.accessorydm.interfaces.XDMInterface;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.util.ByteUtil;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.fmm.utils.FmmConstants;
import com.samsung.android.sdk.mobileservice.social.buddy.provider.BuddyContract;
import com.sec.android.diagmonagent.log.provider.utils.DiagMonUtil;
import java.nio.ByteBuffer;
import seccompat.android.util.Log;

public class MsgDebugData extends Msg {
    static final String SW_VERSION_FORMAT_ENG = "FV00_R190XX000";
    static final String SW_VERSION_FORMAT_USR = "R190XXU0A000";
    private static final String TAG = "Attic_MsgDebugData";
    static final String[] str_SWMonth = {"A", "B", "C", DiagMonUtil.AGREE_TYPE_DIAGNOSTIC, "E", "F", "G", "H", "I", "J", "K", "L"};
    static final String[] str_SWRelVer = {"0", "1", "2", "3", BuddyContract.Email.Type.MOBILE, "5", "6", "7", "8", "9", "A", "B", "C", DiagMonUtil.AGREE_TYPE_DIAGNOSTIC, "E", "F", "G", "H", "I", "J", "K", "L", "M", FmmConstants.NOT_SUPPORT, "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    static final String[] str_SWVer = {"E", "U"};
    static final String[] str_SWYear = {"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public String debugdata = "None";

    public MsgDebugData() {
        super((byte) MsgID.DEBUG_GET_ALL_DATA);
    }

    public MsgDebugData(byte[] bArr) {
        super(bArr);
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        String str10;
        String str11;
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        byte b = recvDataByteBuffer.get();
        byte b2 = recvDataByteBuffer.get();
        StringBuilder sb = new StringBuilder();
        sb.append("rev");
        sb.append(String.format("%X", Integer.valueOf((b2 & 240) >> 4)));
        sb.append(XDMInterface.XDM_BASE_PATH);
        sb.append(String.format("%X", Integer.valueOf(b2 & 15)));
        StringBuilder sb2 = new StringBuilder();
        byte b3 = recvDataByteBuffer.get();
        sb2.append(Application.S_MODEL_NAME);
        sb2.append("XX");
        int i = b3 & 1;
        sb2.append(i == 0 ? "E" : "U");
        sb2.append("0A");
        Log.d(TAG, "DEBUG = ENG : USER = " + i);
        int i2 = (b3 & 240) >> 4;
        Application.getCoreService().getEarBudsFotaInfo().isFotaDM = i2;
        Log.d(TAG, "DEBUG = BASE : DM : ETC = " + i2);
        byte b4 = recvDataByteBuffer.get();
        String str12 = str_SWRelVer[recvDataByteBuffer.get()];
        sb2.append(str_SWYear[(b4 & 240) >> 4]);
        sb2.append(str_SWMonth[b4 & 15]);
        sb2.append(str12);
        Application.getCoreService().getEarBudsInfo().deviceSWVer = sb2.toString();
        Preferences.putString(PreferenceKey.PREFERENCE_DEVICE_INFO_FW_VERSION, sb2.toString());
        Preferences.putString(PreferenceKey.PREFERENCE_DEVICE_INFO_LAST_DEVICE_SW_VERSION, sb2.toString());
        if (Application.getCoreService().getEarBudsFotaInfo().isFotaDM == 0) {
            Application.getCoreService().getEarBudsFotaInfo().firmwareVersion = sb2.toString() + "/" + sb2.toString() + "/";
        } else {
            Application.getCoreService().getEarBudsFotaInfo().firmwareVersion = sb2.toString() + ".DM/" + sb2.toString() + "/";
        }
        SamsungAnalyticsUtil.setStatusString(SA.Status.EARBUDS_SW_VERSION, sb2.toString());
        String format = String.format("%x", Byte.valueOf(recvDataByteBuffer.get()));
        StringBuilder sb3 = new StringBuilder();
        for (int i3 = 0; i3 < 6; i3++) {
            sb3.append(":");
            sb3.append(ByteUtil.toHexString(recvDataByteBuffer.get()));
        }
        StringBuilder sb4 = new StringBuilder();
        for (int i4 = 0; i4 < 6; i4++) {
            sb4.append(":");
            sb4.append(ByteUtil.toHexString(recvDataByteBuffer.get()));
        }
        String valueOf = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf2 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf3 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf4 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf5 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf6 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf7 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf8 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf9 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf10 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf11 = String.valueOf(((double) recvDataByteBuffer.getShort()) * 0.1d);
        String valueOf12 = String.valueOf(((double) recvDataByteBuffer.getShort()) * 0.1d);
        String valueOf13 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf14 = String.valueOf(((double) recvDataByteBuffer.getShort()) * 0.01d);
        String valueOf15 = String.valueOf(((double) recvDataByteBuffer.getShort()) * 1.0E-4d);
        valueOf15 = valueOf15.length() > 6 ? valueOf15.substring(0, 6) : valueOf15;
        String valueOf16 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf17 = String.valueOf(((double) recvDataByteBuffer.getShort()) * 0.01d);
        String valueOf18 = String.valueOf(((double) recvDataByteBuffer.getShort()) * 1.0E-4d);
        valueOf18 = valueOf18.length() > 6 ? valueOf18.substring(0, 6) : valueOf18;
        String valueOf19 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf20 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf21 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf22 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf23 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf24 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf25 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf26 = String.valueOf((int) recvDataByteBuffer.getShort());
        String format2 = String.format("%X", Byte.valueOf(recvDataByteBuffer.get()));
        String format3 = String.format("%X", Byte.valueOf(recvDataByteBuffer.get()));
        byte[] bArr2 = new byte[2];
        recvDataByteBuffer.get(bArr2);
        String str13 = new String(bArr2, 0, 2);
        byte[] bArr3 = new byte[2];
        recvDataByteBuffer.get(bArr3);
        String str14 = new String(bArr3, 0, 2);
        String valueOf27 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf28 = String.valueOf((int) recvDataByteBuffer.getShort());
        String valueOf29 = String.valueOf((int) recvDataByteBuffer.get());
        String valueOf30 = String.valueOf((int) recvDataByteBuffer.get());
        String valueOf31 = String.valueOf((int) recvDataByteBuffer.get());
        String valueOf32 = String.valueOf((int) recvDataByteBuffer.get());
        String str15 = "";
        if (b >= 0) {
            str6 = String.valueOf((int) recvDataByteBuffer.getShort());
            str5 = String.valueOf((int) recvDataByteBuffer.getShort());
            str4 = String.valueOf((int) recvDataByteBuffer.getShort());
            str3 = String.valueOf((int) recvDataByteBuffer.getShort());
            str2 = String.valueOf((int) recvDataByteBuffer.getShort());
            str = String.valueOf((int) recvDataByteBuffer.getShort());
        } else {
            str6 = str15;
            str5 = str6;
            str4 = str5;
            str3 = str4;
            str2 = str3;
            str = str2;
        }
        if (b > 0) {
            str15 = String.valueOf((int) recvDataByteBuffer.getShort());
            String valueOf33 = String.valueOf((int) recvDataByteBuffer.getShort());
            String valueOf34 = String.valueOf((int) recvDataByteBuffer.getShort());
            String valueOf35 = String.valueOf((int) recvDataByteBuffer.getShort());
            String valueOf36 = String.valueOf((int) recvDataByteBuffer.getShort());
            str7 = String.valueOf((int) recvDataByteBuffer.getShort());
            str11 = valueOf33;
            str10 = valueOf34;
            str9 = valueOf35;
            str8 = valueOf36;
        } else {
            str11 = str15;
            str10 = str11;
            str9 = str10;
            str8 = str9;
            str7 = str8;
        }
        StringBuilder sb5 = new StringBuilder();
        sb5.append("\n===============\n");
        sb5.append("[MSG_VERSION]: ");
        sb5.append((int) b);
        sb5.append("\n");
        sb5.append("[HW version]: ");
        sb5.append((CharSequence) sb);
        sb5.append("\n");
        sb5.append("[SW version]: ");
        sb5.append((CharSequence) sb2);
        sb5.append("\n");
        sb5.append("[TOUCH FW version]: ");
        sb5.append(format);
        sb5.append("\n");
        sb5.append("[L>BT address] ");
        sb5.append(sb3.toString().toUpperCase());
        sb5.append("\n");
        sb5.append("[R>BT address] ");
        sb5.append(sb4.toString().toUpperCase());
        sb5.append("\n");
        sb5.append("[L>Acc 0]: ");
        sb5.append(valueOf);
        sb5.append("\n");
        sb5.append("[L>Acc 1]: ");
        sb5.append(valueOf2);
        sb5.append("\n");
        sb5.append("[L>Acc 2]: ");
        sb5.append(valueOf3);
        sb5.append("\n");
        sb5.append("[R>Acc 0]: ");
        sb5.append(valueOf4);
        sb5.append("\n");
        sb5.append("[R>Acc 1]: ");
        sb5.append(valueOf5);
        sb5.append("\n");
        sb5.append("[R>Acc 2]: ");
        sb5.append(valueOf6);
        sb5.append("\n");
        sb5.append("[L>Proxymity]: ");
        sb5.append(valueOf7);
        sb5.append("\n");
        sb5.append("[L>ProxymityOffset 0]: ");
        sb5.append(valueOf8);
        sb5.append("\n");
        sb5.append("[R>Proxymity]: ");
        sb5.append(valueOf9);
        sb5.append("\n");
        sb5.append("[R>ProxymityOffset 0]: ");
        sb5.append(valueOf10);
        sb5.append("\n");
        sb5.append("[L>Thermistor]: ");
        sb5.append(valueOf11);
        sb5.append("\n");
        sb5.append("[R>Thermistor]: ");
        sb5.append(valueOf12);
        sb5.append("\n");
        sb5.append("[L>Batt 0]: ");
        sb5.append(valueOf13);
        sb5.append("\n");
        sb5.append("[L>Batt 1]: ");
        sb5.append(valueOf14);
        sb5.append("\n");
        sb5.append("[L>Batt 2]: ");
        sb5.append(valueOf15);
        sb5.append("\n");
        sb5.append("[R>Batt 0]: ");
        sb5.append(valueOf16);
        sb5.append("\n");
        sb5.append("[R>Batt 1]: ");
        sb5.append(valueOf17);
        sb5.append("\n");
        sb5.append("[R>Batt 2]: ");
        sb5.append(valueOf18);
        sb5.append("\n");
        sb5.append("[L>TspAbs]: ");
        sb5.append(valueOf19);
        sb5.append("\n");
        sb5.append("[R>TspAbs]: ");
        sb5.append(valueOf20);
        sb5.append("\n");
        sb5.append("[L>TspDiff 0]: ");
        sb5.append(valueOf21);
        sb5.append("\n");
        sb5.append("[L>TspDiff 1]: ");
        sb5.append(valueOf22);
        sb5.append("\n");
        sb5.append("[L>TspDiff 2]: ");
        sb5.append(valueOf23);
        sb5.append("\n");
        sb5.append("[R>TspDiff 0]: ");
        sb5.append(valueOf24);
        sb5.append("\n");
        sb5.append("[R>TspDiff 1]: ");
        sb5.append(valueOf25);
        sb5.append("\n");
        sb5.append("[R>TspDiff 2]: ");
        sb5.append(valueOf26);
        sb5.append("\n");
        sb5.append("[L>Hall]: ");
        sb5.append(format2);
        sb5.append("\n");
        sb5.append("[R>Hall]: ");
        sb5.append(format3);
        sb5.append("\n");
        sb5.append("[L>PR]: ");
        sb5.append(str13);
        sb5.append("\n");
        sb5.append("[R>PR]: ");
        sb5.append(str14);
        sb5.append("\n");
        sb5.append("[L>WD]: ");
        sb5.append(valueOf27);
        sb5.append("\n");
        sb5.append("[R>WD]: ");
        sb5.append(valueOf28);
        sb5.append("\n");
        sb5.append("[L>Cradle Flag]: ");
        sb5.append(valueOf29);
        sb5.append("\n");
        sb5.append("[R>Cradle Flag]: ");
        sb5.append(valueOf30);
        sb5.append("\n");
        sb5.append("[L>Cradle Batt]: ");
        sb5.append(valueOf31);
        sb5.append("\n");
        sb5.append("[R>Cradle Batt]: ");
        sb5.append(valueOf32);
        sb5.append("\n");
        if (b >= 0) {
            sb5.append("[L>Gyro 0]: ");
            sb5.append(str6);
            sb5.append("\n");
            sb5.append("[L>Gyro 1]: ");
            sb5.append(str5);
            sb5.append("\n");
            sb5.append("[L>Gyro 2]: ");
            sb5.append(str4);
            sb5.append("\n");
            sb5.append("[R>Gyro 0]: ");
            sb5.append(str3);
            sb5.append("\n");
            sb5.append("[R>Gyro 1]: ");
            sb5.append(str2);
            sb5.append("\n");
            sb5.append("[R>Gyro 2]: ");
            sb5.append(str);
            sb5.append("\n");
        }
        if (b > 0) {
            sb5.append("[L>Vpu 0]: ");
            sb5.append(str15);
            sb5.append("\n");
            sb5.append("[L>Vpu 1]: ");
            sb5.append(str11);
            sb5.append("\n");
            sb5.append("[L>Vpu 2]: ");
            sb5.append(str10);
            sb5.append("\n");
            sb5.append("[R>Vpu 0]: ");
            sb5.append(str9);
            sb5.append("\n");
            sb5.append("[R>Vpu 1]: ");
            sb5.append(str8);
            sb5.append("\n");
            sb5.append("[R>Vpu 2]: ");
            sb5.append(str7);
            sb5.append("\n");
        }
        sb5.append("===============\n");
        this.debugdata = sb5.toString();
        Log.d(TAG, sb5.toString());
    }
}
