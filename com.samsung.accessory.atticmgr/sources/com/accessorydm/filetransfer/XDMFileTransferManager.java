package com.accessorydm.filetransfer;

import com.accessorydm.filetransfer.action.CheckDeviceInfo;
import com.accessorydm.filetransfer.action.CopyDelta;
import com.accessorydm.filetransfer.action.FileTransferFailure;
import com.accessorydm.filetransfer.action.InstallPackage;
import com.accessorydm.filetransfer.action.ResetDevice;
import com.samsung.android.fotaprovider.log.Log;

public class XDMFileTransferManager {
    public static boolean checkDeviceInfo() {
        Log.I("");
        return new CheckDeviceInfo().doAction();
    }

    public static void copyDelta() {
        Log.I("");
        new CopyDelta().doAction();
    }

    public static void installPackage() {
        Log.I("");
        new InstallPackage().doAction();
    }

    public static void resetDevice() {
        Log.I("");
        new ResetDevice().doAction();
    }

    public static void changeDevice() {
        Log.I("");
        FileTransferFailure.handleChangedDevice();
    }
}
