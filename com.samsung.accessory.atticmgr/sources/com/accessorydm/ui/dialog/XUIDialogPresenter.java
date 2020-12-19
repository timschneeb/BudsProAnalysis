package com.accessorydm.ui.dialog;

import com.accessorydm.ui.dialog.XUIDialogContract;
import com.accessorydm.ui.dialog.model.XUIDialogModel;
import com.samsung.android.fotaprovider.log.Log;

class XUIDialogPresenter implements XUIDialogContract.Presenter {
    private XUIDialog dialog;
    private XUIDialogModel model;
    private XUIDialogContract.View view;

    XUIDialogPresenter(XUIDialogContract.View view2, XUIDialog xUIDialog) {
        this.view = view2;
        this.dialog = xUIDialog;
        this.model = xUIDialog.getDialogModel();
    }

    @Override // com.accessorydm.ui.dialog.XUIDialogContract.Presenter
    public void onCreate() {
        Log.I("");
        preExecute();
        setUpConditions();
        initializeUI();
    }

    private void preExecute() {
        this.model.preExecute();
    }

    private void setUpConditions() {
        this.view.setGravity(this.model.getGravity());
        this.view.blockKeyEvents(this.model.getBlockKeyEvents());
        this.view.setCancelable(this.model.isCancelable());
    }

    private void initializeUI() {
        this.view.setDialogTitle(this.model.getTitle());
        this.view.setDialogBody(this.model.getMessage());
        this.view.setButton(this.model.getNeutralButtonStrategy());
        this.view.setButton(this.model.getNegativeButtonStrategy());
        this.view.setButton(this.model.getPositiveButtonStrategy());
    }

    @Override // com.accessorydm.ui.dialog.XUIDialogContract.Presenter
    public void onDismiss() {
        Log.I("dismiss: " + this.dialog);
    }
}
