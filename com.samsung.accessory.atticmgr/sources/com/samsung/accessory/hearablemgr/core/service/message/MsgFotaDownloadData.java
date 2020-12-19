package com.samsung.accessory.hearablemgr.core.service.message;

import com.samsung.accessory.hearablemgr.common.util.ByteUtil;
import com.samsung.accessory.hearablemgr.core.fota.util.FotaBinaryFile;
import java.io.Closeable;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import seccompat.android.util.Log;

public class MsgFotaDownloadData extends Msg {
    private static final String TAG = "Attic_MsgFotaDownloadData";
    private FotaBinaryFile mBinaryFile;
    private int mEntryId;
    private int mMtuSize;
    public boolean mNAK;
    private long mOffset;
    public long mReceivedOffset;
    public int mReqeustPacketNum;

    private int makeFragmentHeader(boolean z, long j) {
        int i = (int) (j & 2147483647L);
        return z ? i : i | Integer.MIN_VALUE;
    }

    public MsgFotaDownloadData(FotaBinaryFile fotaBinaryFile, int i, long j, int i2, boolean z) {
        super(MsgID.FOTA_DOWNLOAD_DATA, z);
        this.fragment = true;
        this.mBinaryFile = fotaBinaryFile;
        this.mEntryId = i;
        this.mOffset = j;
        this.mMtuSize = i2;
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        RandomAccessFile randomAccessFile;
        Throwable th;
        IOException e;
        FotaBinaryFile.Entry entry = getEntry(this.mEntryId);
        if (entry == null) {
            Log.d(TAG, "entry is null");
            return null;
        }
        int i = 0;
        boolean z = this.mOffset + ((long) this.mMtuSize) >= entry.size;
        long j = z ? entry.size - this.mOffset : (long) this.mMtuSize;
        Log.d(TAG, "entry.offset : " + entry.offset);
        Log.d(TAG, "mOffset : " + this.mOffset);
        Log.d(TAG, "rawDataSize : " + j);
        if (j < 0) {
            Log.d(TAG, "rawDataSize - null");
            return null;
        }
        byte[] bArr = new byte[((int) (4 + j))];
        byte[] fromInt = ByteUtil.fromInt(makeFragmentHeader(z, this.mOffset));
        int length = fromInt.length;
        int i2 = 0;
        while (i < length) {
            bArr[i2] = fromInt[i];
            i++;
            i2++;
        }
        int i3 = 5;
        while (true) {
            int i4 = i3 - 1;
            if (i3 <= 0) {
                break;
            }
            try {
                randomAccessFile = this.mBinaryFile.getRandomAccessFile();
                try {
                    randomAccessFile.seek(entry.offset + this.mOffset);
                    randomAccessFile.read(bArr, i2, (int) j);
                    safeClose(randomAccessFile);
                    break;
                } catch (IOException e2) {
                    e = e2;
                }
            } catch (IOException e3) {
                e = e3;
                randomAccessFile = null;
                try {
                    e.printStackTrace();
                    Log.e(TAG, "RandomAccessFile Error !!! (" + i4 + ")");
                    safeClose(randomAccessFile);
                    i3 = i4;
                } catch (Throwable th2) {
                    th = th2;
                    safeClose(randomAccessFile);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                randomAccessFile = null;
                safeClose(randomAccessFile);
                throw th;
            }
            safeClose(randomAccessFile);
            i3 = i4;
        }
        return bArr;
    }

    public boolean isLastFragment() {
        try {
            Log.d(TAG, "mOffset : " + this.mOffset);
            Log.d(TAG, "mMtuSize : " + this.mMtuSize);
            Log.d(TAG, "mEntryId : " + this.mEntryId);
            if (this.mOffset + ((long) this.mMtuSize) >= getEntry(this.mEntryId).size) {
                return true;
            }
            return false;
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e(TAG, "isLastFragment() exception!!!");
            return true;
        }
    }

    public long getOffset() {
        return this.mOffset;
    }

    private FotaBinaryFile.Entry getEntry(int i) {
        FotaBinaryFile fotaBinaryFile = this.mBinaryFile;
        if (fotaBinaryFile == null) {
            return null;
        }
        for (FotaBinaryFile.Entry entry : fotaBinaryFile.getEntryList()) {
            if (entry.id == i) {
                return entry;
            }
        }
        return null;
    }

    private void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public MsgFotaDownloadData(byte[] bArr) {
        super(bArr);
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        this.mNAK = (bArr[getDataStartIndex() + 3] & MsgID.SET_AMBIENT_MODE) != 0;
        this.mReceivedOffset = ((((long) bArr[getDataStartIndex() + 1]) & 255) << 8) | (((long) bArr[getDataStartIndex()]) & 255) | ((((long) bArr[getDataStartIndex() + 2]) & 255) << 16) | ((((long) bArr[getDataStartIndex() + 3]) & 127) << 24);
        this.mReqeustPacketNum = recvDataByteBuffer.get(8);
        Log.d(TAG, "mNAK : " + this.mNAK);
        Log.d(TAG, "mReceivedOffset : " + this.mReceivedOffset);
        Log.d(TAG, "mReqeustPacketNum : " + this.mReqeustPacketNum);
    }
}
