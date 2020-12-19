package com.samsung.accessory.hearablemgr.core.service.message;

public class MsgFotaSession extends Msg {
    public static final int FOTA_SESSION_ABORTING = 6;
    public static final int FOTA_SESSION_ABORTING_AND_WAITING_WRITE_EVT = 7;
    public static final int FOTA_SESSION_CLOSE = 0;
    public static final int FOTA_SESSION_CLOSING = 5;
    public static final int FOTA_SESSION_OPENED = 3;
    public static final int FOTA_SESSION_OPENED_AND_DOWNLOAD_COMPLETE = 4;
    public static final int FOTA_SESSION_OPENED_AND_PENDING = 2;
    public static final int FOTA_SESSION_OPENING = 1;
    private FotaBinaryFileGetData mBinaryFile;
    public int mErrorCode;

    public interface FotaBinaryFileGetData {
        byte[] getDataForMsgFotaSession();
    }

    public MsgFotaSession(FotaBinaryFileGetData fotaBinaryFileGetData) {
        super((byte) MsgID.FOTA_OPEN);
        this.mBinaryFile = fotaBinaryFileGetData;
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        return this.mBinaryFile.getDataForMsgFotaSession();
    }

    public MsgFotaSession(byte[] bArr) {
        super(bArr);
        this.mErrorCode = getRecvDataByteBuffer().get();
    }
}
