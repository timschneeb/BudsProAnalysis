package com.accessorydm.tp.downloadInfo;

import com.accessorydm.adapter.XDMTargetAdapter;
import com.accessorydm.exception.file.ExceptionFileIO;
import com.accessorydm.exception.file.ExceptionFileNotFound;
import com.accessorydm.exception.file.ExceptionStorageNotUsed;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.accessorydm.ui.progress.XUIProgressModel;

public class UpdateDownloadInfo extends DownloadFileAdapter {
    private long downloadProgressSize;
    private long downloadSize = 0;
    private DownloadFlexibleBuffer flexible = new DownloadFlexibleBuffer(65536, HttpNetworkInterface.RECEIVE_DOWNLOAD_BUFFER_SIZE);

    public void updateDownloadInfo(long j, byte[] bArr) {
        this.downloadProgressSize += (long) bArr.length;
        try {
            writeFirmwareObject(bArr, j);
            this.downloadSize += (long) this.flexible.getRealWriteBufferLength();
            if (this.downloadSize > 0 && j > 0) {
                XUIProgressModel.getInstance().updateProgressInfoForDownload(this.downloadSize);
            }
        } catch (ExceptionFileNotFound unused) {
            throw new ExceptionFileNotFound();
        } catch (ExceptionFileIO unused2) {
            throw new ExceptionFileIO();
        } catch (ExceptionStorageNotUsed unused3) {
            throw new ExceptionStorageNotUsed();
        }
    }

    private void writeFirmwareObject(byte[] bArr, long j) {
        int length = bArr.length;
        if (length > 0) {
            this.flexible.addFlexibleBuffer(bArr, length);
        }
        try {
            if (this.flexible.getFlexibleBufferLength() > this.flexible.getAppendSavedBufferSize() || (this.downloadProgressSize >= j && this.flexible.getFlexibleBufferLength() > 0)) {
                writeDeltaFile(this.flexible.getRealWriteBuffer(), this.flexible.getFlexibleBufferLength());
                this.flexible.clearFlexibleBuffer();
                if (!XDMTargetAdapter.xdmGetStorageAvailable()) {
                    throw new ExceptionStorageNotUsed("Storage is not available");
                }
            }
        } catch (ExceptionFileNotFound unused) {
            throw new ExceptionFileNotFound();
        } catch (ExceptionFileIO unused2) {
            throw new ExceptionFileIO();
        }
    }

    public void setRemainDownloadSize(long j) {
        this.downloadProgressSize += j;
        this.downloadSize += j;
    }

    public long getDownloadSize() {
        return this.downloadSize;
    }

    public void resetFlexibleBufferWithDownloadInfo() {
        this.flexible.flexibleBufferAllClear();
        this.downloadSize = 0;
        this.downloadProgressSize = 0;
    }
}
