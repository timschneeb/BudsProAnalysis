package com.accessorydm.ui.fullscreen.basefullscreen;

import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenContract;
import com.samsung.android.fotaprovider.log.Log;

public abstract class XUIBaseFullscreenPresenter implements XUIBaseFullscreenContract.Presenter {
    /* access modifiers changed from: protected */
    public abstract XUIBaseFullscreenModel getModel();

    /* access modifiers changed from: protected */
    public abstract XUIBaseFullscreenContract.View getView();

    /* access modifiers changed from: protected */
    public abstract void initializeBottomContentUI();

    /* access modifiers changed from: protected */
    public abstract void initializeMiddleContentUI();

    /* access modifiers changed from: protected */
    public abstract void initializeTopContentUI();

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenContract.Presenter
    public abstract void onDestroy();

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenContract.Presenter
    public void onCreate() {
        Log.I("");
        initializeUI();
        initializeListenersAfterCreatedUI();
    }

    private void initializeUI() {
        initializeActionBarUI();
        initializeTopContentUI();
        initializeMiddleContentUI();
        initializeBottomContentUI();
    }

    private void initializeActionBarUI() {
        if (getView() != null && getModel() != null) {
            getView().xuiSetAppBarWithTitleText(getModel().getActionBarTitleText());
        }
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenContract.Presenter
    public boolean onOptionsItemSelected(int i) {
        if (i != 16908332) {
            return false;
        }
        actionForUpButton();
        return true;
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenContract.Presenter
    public boolean onKeyDown(int i) {
        if (i != 4) {
            return false;
        }
        actionForBackKey();
        return true;
    }

    /* access modifiers changed from: protected */
    public void actionForUpButton() {
        Log.D("");
        if (getView() != null) {
            getView().xuiOnBackPressed();
        }
    }

    /* access modifiers changed from: protected */
    public void actionForBackKey() {
        Log.D("");
        if (getView() != null) {
            getView().xuiOnBackPressed();
        }
    }
}
