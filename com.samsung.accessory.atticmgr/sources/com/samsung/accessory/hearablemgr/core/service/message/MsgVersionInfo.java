package com.samsung.accessory.hearablemgr.core.service.message;

import com.accessorydm.interfaces.XDMInterface;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.fmm.utils.FmmConstants;
import com.samsung.accessory.hearablemgr.core.service.BudsLogManager;
import com.sec.android.diagmonagent.log.provider.utils.DiagMonUtil;
import java.nio.ByteBuffer;
import seccompat.android.util.Log;

public class MsgVersionInfo extends Msg {
    private static final String TAG = "Attic_MsgVersionInfo";
    static final String[] str_SWMonth = {"A", "B", "C", DiagMonUtil.AGREE_TYPE_DIAGNOSTIC, "E", "F", "G", "H", "I", "J", "K", "L"};
    static final String[] str_SWRelVer = {"G", "H", "I", "J", "K", "L", "M", FmmConstants.NOT_SUPPORT, "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    static final String[] str_SWVer = {"E", "U"};
    static final String[] str_SWYear = {"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public String Left_HW_version;
    public String Left_SW_version;
    public String Left_Touch_FW_Version;
    public String Right_HW_version;
    public String Right_SW_version;
    public String Right_Touch_FW_Version;

    MsgVersionInfo(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        byte b = recvDataByteBuffer.get();
        this.Left_HW_version = "rev" + String.format("%X", Integer.valueOf((b & 240) >> 4)) + XDMInterface.XDM_BASE_PATH + String.format("%X", Integer.valueOf(b & 15));
        byte b2 = recvDataByteBuffer.get();
        this.Right_HW_version = "rev" + String.format("%X", Integer.valueOf((b2 & 240) >> 4)) + XDMInterface.XDM_BASE_PATH + String.format("%X", Integer.valueOf(b2 & 15));
        StringBuilder sb = new StringBuilder();
        byte b3 = recvDataByteBuffer.get();
        sb.append(Application.S_MODEL_NAME);
        sb.append("XX");
        int i = b3 & 1;
        String str = "E";
        sb.append(i == 0 ? str : "U");
        sb.append("0A");
        Log.d(TAG, "DEBUG = ENG : USER = " + i);
        Log.d(TAG, "DEBUG = BASE : DM : ETC = " + ((b3 & 240) >> 4));
        byte b4 = recvDataByteBuffer.get();
        byte b5 = recvDataByteBuffer.get();
        String format = b5 <= 15 ? String.format("%X", Byte.valueOf(b5)) : str_SWRelVer[b5 - 16];
        sb.append(str_SWYear[(b4 & 240) >> 4]);
        sb.append(str_SWMonth[b4 & 15]);
        sb.append(format);
        this.Left_SW_version = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        byte b6 = recvDataByteBuffer.get();
        sb2.append(Application.S_MODEL_NAME);
        sb2.append("XX");
        int i2 = b6 & 1;
        sb2.append(i2 != 0 ? "U" : str);
        sb2.append("0A");
        Log.d(TAG, "DEBUG = ENG : USER = " + i2);
        Log.d(TAG, "DEBUG = BASE : DM : ETC = " + ((b6 & 240) >> 4));
        byte b7 = recvDataByteBuffer.get();
        byte b8 = recvDataByteBuffer.get();
        String format2 = b8 <= 15 ? String.format("%X", Byte.valueOf(b8)) : str_SWRelVer[b8 - 16];
        sb2.append(str_SWYear[(b7 & 240) >> 4]);
        sb2.append(str_SWMonth[b7 & 15]);
        sb2.append(format2);
        this.Right_SW_version = sb2.toString();
        this.Left_Touch_FW_Version = String.format("%x", Byte.valueOf(recvDataByteBuffer.get()));
        this.Right_Touch_FW_Version = String.format("%x", Byte.valueOf(recvDataByteBuffer.get()));
        String str2 = "\nLEFT [HW version] :" + this.Left_HW_version + "\nRIGHT [HW version] :" + this.Right_HW_version + "\nLEFT [SW version] :" + this.Left_SW_version + "\nRIGHT [SW version] :" + this.Right_SW_version + "\nLEFT [TOUCH FW version] :" + this.Left_Touch_FW_Version + "\nRIGHT [TOUCH FW version] :" + this.Right_Touch_FW_Version;
        Log.d(TAG, str2);
        BudsLogManager.sendLog(6, str2);
    }
}
