package com.accessorydm.filetransfer.action;

public abstract class FileTransferAction {
    /* access modifiers changed from: package-private */
    public abstract boolean checkPrecondition();

    /* access modifiers changed from: package-private */
    public abstract void controlAccessory();

    public final boolean doAction() {
        if (!checkPrecondition()) {
            return false;
        }
        controlAccessory();
        return true;
    }
}
