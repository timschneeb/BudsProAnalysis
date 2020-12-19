package com.samsung.accessory.hearablemgr.core.fota.util;

import com.samsung.accessory.hearablemgr.common.util.BufferBuilder;
import com.samsung.accessory.hearablemgr.core.service.message.MsgFotaSession;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import seccompat.android.util.Log;

public class FotaBinaryFile implements MsgFotaSession.FotaBinaryFileGetData {
    private static final int MAGIC_NUMBER = -889271554;
    private static final String TAG = "Attic_FotaBinaryFile";
    private long file_crc32 = 0;
    private final File mBinaryFile;
    private final List<Entry> mEntryList = new ArrayList();

    public FotaBinaryFile(File file) {
        this.mBinaryFile = file;
    }

    public boolean open() {
        BufferedInputStream bufferedInputStream;
        FileInputStream fileInputStream;
        Throwable th;
        BufferedInputStream bufferedInputStream2;
        IOException e;
        RandomAccessFile randomAccessFile;
        Throwable th2;
        IOException e2;
        if (!this.mBinaryFile.exists()) {
            Log.e(TAG, "open() : mBinaryFile.exists() == false");
            return false;
        }
        this.mEntryList.clear();
        try {
            fileInputStream = new FileInputStream(this.mBinaryFile);
            try {
                bufferedInputStream = new BufferedInputStream(fileInputStream);
            } catch (IOException e3) {
                e = e3;
                bufferedInputStream2 = null;
                try {
                    e.printStackTrace();
                    safeClose(bufferedInputStream2);
                    safeClose(fileInputStream);
                    return false;
                } catch (Throwable th3) {
                    th = th3;
                    bufferedInputStream = bufferedInputStream2;
                    safeClose(bufferedInputStream);
                    safeClose(fileInputStream);
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                bufferedInputStream = null;
                safeClose(bufferedInputStream);
                safeClose(fileInputStream);
                throw th;
            }
            try {
                if (((int) read4Byte(bufferedInputStream)) == MAGIC_NUMBER) {
                    read4Byte(bufferedInputStream);
                    int read4Byte = (int) read4Byte(bufferedInputStream);
                    for (int i = 0; i < read4Byte; i++) {
                        int read4Byte2 = (int) read4Byte(bufferedInputStream);
                        int read4Byte3 = (int) read4Byte(bufferedInputStream);
                        long read4Byte4 = read4Byte(bufferedInputStream);
                        long read4Byte5 = read4Byte(bufferedInputStream);
                        this.mEntryList.add(new Entry(read4Byte2, read4Byte3, read4Byte4, read4Byte5));
                        Log.d(TAG, String.format("id=%d, crc=%x, offset=%d, size=%d", Integer.valueOf(read4Byte2), Integer.valueOf(read4Byte3), Long.valueOf(read4Byte4), Long.valueOf(read4Byte5)));
                    }
                    byte[] bArr = new byte[4];
                    try {
                        randomAccessFile = getRandomAccessFile();
                        try {
                            randomAccessFile.seek(randomAccessFile.length() - 4);
                            randomAccessFile.read(bArr, 0, 4);
                        } catch (IOException e4) {
                            e2 = e4;
                            try {
                                e2.printStackTrace();
                                safeClose(randomAccessFile);
                                this.file_crc32 = bufferToLong(bArr);
                                safeClose(bufferedInputStream);
                                safeClose(fileInputStream);
                                Log.d(TAG, "open() : return true");
                                return true;
                            } catch (Throwable th5) {
                                th2 = th5;
                            }
                        }
                    } catch (IOException e5) {
                        e2 = e5;
                        randomAccessFile = null;
                        e2.printStackTrace();
                        safeClose(randomAccessFile);
                        this.file_crc32 = bufferToLong(bArr);
                        safeClose(bufferedInputStream);
                        safeClose(fileInputStream);
                        Log.d(TAG, "open() : return true");
                        return true;
                    } catch (Throwable th6) {
                        th2 = th6;
                        randomAccessFile = null;
                        safeClose(randomAccessFile);
                        throw th2;
                    }
                    safeClose(randomAccessFile);
                    this.file_crc32 = bufferToLong(bArr);
                    safeClose(bufferedInputStream);
                    safeClose(fileInputStream);
                    Log.d(TAG, "open() : return true");
                    return true;
                }
                throw new IOException("wrong MAGIC_NUMBER");
            } catch (IOException e6) {
                e = e6;
                bufferedInputStream2 = bufferedInputStream;
                e.printStackTrace();
                safeClose(bufferedInputStream2);
                safeClose(fileInputStream);
                return false;
            } catch (Throwable th7) {
                th = th7;
                safeClose(bufferedInputStream);
                safeClose(fileInputStream);
                throw th;
            }
        } catch (IOException e7) {
            e = e7;
            bufferedInputStream2 = null;
            fileInputStream = null;
            e.printStackTrace();
            safeClose(bufferedInputStream2);
            safeClose(fileInputStream);
            return false;
        } catch (Throwable th8) {
            th = th8;
            fileInputStream = null;
            bufferedInputStream = null;
            safeClose(bufferedInputStream);
            safeClose(fileInputStream);
            throw th;
        }
    }

    public List<Entry> getEntryList() {
        return this.mEntryList;
    }

    public long getFile_crc32() {
        Log.d(TAG, "getFile_crc32 :" + this.file_crc32);
        return this.file_crc32;
    }

    public RandomAccessFile getRandomAccessFile() throws FileNotFoundException {
        return new RandomAccessFile(this.mBinaryFile, "r");
    }

    public static class Entry {
        public final int crc32;
        public final int id;
        public final long offset;
        public final long size;

        Entry(int i, int i2, long j, long j2) {
            this.id = i;
            this.crc32 = i2;
            this.offset = j;
            this.size = j2;
        }
    }

    private long read4Byte(BufferedInputStream bufferedInputStream) throws IOException {
        byte[] bArr = new byte[4];
        if (bufferedInputStream.read(bArr) != -1) {
            return bufferToLong(bArr);
        }
        throw new IOException();
    }

    private long bufferToLong(byte[] bArr) {
        return ((((long) bArr[3]) & 255) << 24) | ((((long) bArr[2]) & 255) << 16) | ((((long) bArr[1]) & 255) << 8) | (255 & ((long) bArr[0]));
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

    @Override // com.samsung.accessory.hearablemgr.core.service.message.MsgFotaSession.FotaBinaryFileGetData
    public byte[] getDataForMsgFotaSession() {
        BufferBuilder bufferBuilder = new BufferBuilder();
        List<Entry> entryList = getEntryList();
        bufferBuilder.putInt((int) getFile_crc32());
        bufferBuilder.put((byte) entryList.size());
        for (Entry entry : entryList) {
            bufferBuilder.put((byte) entry.id);
            bufferBuilder.putInt((int) entry.size);
            bufferBuilder.putInt(entry.crc32);
        }
        return bufferBuilder.array();
    }
}
