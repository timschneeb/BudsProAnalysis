package com.accessorydm.tp.downloadInfo;

import com.accessorydm.db.file.XDB;
import com.accessorydm.exception.file.ExceptionFileIO;
import com.accessorydm.exception.file.ExceptionFileNotFound;
import com.samsung.android.fotaprovider.log.Log;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class DownloadFileAdapter {
    private FileOutputStream File;

    public void openDeltaFileInputStream() {
        try {
            this.File = new FileOutputStream(XDB.xdbFileGetNameFromCallerID(XDB.xdbGetFileIdFirmwareData()), true);
        } catch (FileNotFoundException unused) {
            throw new ExceptionFileNotFound();
        }
    }

    public void writeDeltaFile(byte[] bArr, int i) {
        try {
            this.File.write(bArr, 0, i);
            this.File.flush();
            this.File.getFD().sync();
        } catch (FileNotFoundException unused) {
            throw new ExceptionFileNotFound("Write Delta FileOutputStream Not Found");
        } catch (IOException unused2) {
            throw new ExceptionFileIO("Write Delta IOException");
        }
    }

    public void closeDeltaFileInputStream() {
        try {
            if (this.File != null) {
                this.File.close();
            }
        } catch (IOException e) {
            Log.printStackTrace(e);
        }
    }
}
