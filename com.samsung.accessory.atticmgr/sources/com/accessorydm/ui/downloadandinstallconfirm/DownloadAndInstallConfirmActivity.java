package com.accessorydm.ui.downloadandinstallconfirm;

import android.os.Bundle;
import android.view.ViewStub;
import com.accessorydm.ui.downloadandinstallconfirm.DownloadAndInstallConfirmContract;
import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity;
import com.accessorydm.ui.fullscreen.content.BottomContentView;
import com.accessorydm.ui.fullscreen.content.MiddleContentView;
import com.accessorydm.ui.fullscreen.content.TopContentView;
import com.samsung.android.fotaprovider.log.Log;

public class DownloadAndInstallConfirmActivity extends XUIBaseFullscreenActivity implements DownloadAndInstallConfirmContract.View {
    private BottomContentView.TwoButtons bottomContentView = null;
    private MiddleContentView.WithCaution middleContentView = null;
    private DownloadAndInstallConfirmContract.Presenter presenter;
    private TopContentView.Guide topContentView = null;

    /* access modifiers changed from: protected */
    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public DownloadAndInstallConfirmContract.Presenter getPresenter() {
        return this.presenter;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void onCreate(Bundle bundle) {
        Log.I("");
        this.presenter = new DownloadAndInstallConfirmPresenter(this, DownloadAndInstallConfirmModel.getInstance());
        super.onCreate(bundle);
        this.presenter.onCreate();
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void xuiGenerateTopContentLayout(ViewStub viewStub) {
        this.topContentView = new TopContentView.Guide(this, viewStub);
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void xuiGenerateMiddleContentLayout(ViewStub viewStub) {
        this.middleContentView = new MiddleContentView.WithCaution(this, viewStub);
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void xuiGenerateBottomLayout(ViewStub viewStub) {
        this.bottomContentView = new BottomContentView.TwoButtons(this, viewStub);
    }

    @Override // com.accessorydm.ui.downloadandinstallconfirm.DownloadAndInstallConfirmContract.View
    public TopContentView.Guide getTopContentView() {
        return this.topContentView;
    }

    @Override // com.accessorydm.ui.downloadandinstallconfirm.DownloadAndInstallConfirmContract.View
    public MiddleContentView.WithCaution getMiddleContentView() {
        return this.middleContentView;
    }

    @Override // com.accessorydm.ui.downloadandinstallconfirm.DownloadAndInstallConfirmContract.View
    public BottomContentView.TwoButtons getBottomContentView() {
        return this.bottomContentView;
    }

    @Override // com.accessorydm.ui.downloadandinstallconfirm.DownloadAndInstallConfirmContract.View
    public void xuiSetBottomButtonsClickListeners() {
        this.bottomContentView.setBottomButtonClickListeners(new BottomContentView.TwoButtons.BottomButtonAction() {
            /* class com.accessorydm.ui.downloadandinstallconfirm.DownloadAndInstallConfirmActivity.AnonymousClass1 */

            @Override // com.accessorydm.ui.fullscreen.content.BottomContentView.TwoButtons.BottomButtonAction
            public void firstButtonAction() {
                DownloadAndInstallConfirmActivity.this.presenter.doFirstButtonAction();
            }

            @Override // com.accessorydm.ui.fullscreen.content.BottomContentView.TwoButtons.BottomButtonAction
            public void secondButtonAction() {
                DownloadAndInstallConfirmActivity.this.presenter.doSecondButtonAction();
            }
        });
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void onDestroy() {
        Log.D("");
        super.onDestroy();
    }
}
