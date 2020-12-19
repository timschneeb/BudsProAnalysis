package com.accessorydm.ui.downloadconfirm;

import android.os.Bundle;
import android.view.ViewStub;
import com.accessorydm.ui.downloadconfirm.XUIDownloadConfirmContract;
import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity;
import com.accessorydm.ui.fullscreen.content.BottomContentView;
import com.accessorydm.ui.fullscreen.content.MiddleContentView;
import com.accessorydm.ui.fullscreen.content.TopContentView;
import com.samsung.android.fotaprovider.log.Log;

public class XUIDownloadConfirmActivity extends XUIBaseFullscreenActivity implements XUIDownloadConfirmContract.View {
    private BottomContentView.TwoButtons bottomContentView = null;
    private MiddleContentView.WithoutCaution middleContentView = null;
    private XUIDownloadConfirmContract.Presenter presenter;
    private TopContentView.Guide topContentView = null;

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public XUIDownloadConfirmContract.Presenter getPresenter() {
        return this.presenter;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void onCreate(Bundle bundle) {
        Log.I("");
        this.presenter = new XUIDownloadConfirmPresenter(this, XUIDownloadConfirmModel.getInstance());
        super.onCreate(bundle);
        this.presenter.onCreate();
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void xuiGenerateTopContentLayout(ViewStub viewStub) {
        this.topContentView = new TopContentView.Guide(this, viewStub);
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void xuiGenerateMiddleContentLayout(ViewStub viewStub) {
        this.middleContentView = new MiddleContentView.WithoutCaution(this, viewStub);
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void xuiGenerateBottomLayout(ViewStub viewStub) {
        this.bottomContentView = new BottomContentView.TwoButtons(this, viewStub);
    }

    @Override // com.accessorydm.ui.downloadconfirm.XUIDownloadConfirmContract.View
    public TopContentView.Guide getTopContentView() {
        return this.topContentView;
    }

    @Override // com.accessorydm.ui.downloadconfirm.XUIDownloadConfirmContract.View
    public MiddleContentView.WithoutCaution getMiddleContentView() {
        return this.middleContentView;
    }

    @Override // com.accessorydm.ui.downloadconfirm.XUIDownloadConfirmContract.View
    public BottomContentView.TwoButtons getBottomContentView() {
        return this.bottomContentView;
    }

    @Override // com.accessorydm.ui.downloadconfirm.XUIDownloadConfirmContract.View
    public void xuiSetBottomButtonsClickListeners() {
        this.bottomContentView.setBottomButtonClickListeners(new BottomContentView.TwoButtons.BottomButtonAction() {
            /* class com.accessorydm.ui.downloadconfirm.XUIDownloadConfirmActivity.AnonymousClass1 */

            @Override // com.accessorydm.ui.fullscreen.content.BottomContentView.TwoButtons.BottomButtonAction
            public void firstButtonAction() {
                XUIDownloadConfirmActivity.this.presenter.doFirstButtonAction();
            }

            @Override // com.accessorydm.ui.fullscreen.content.BottomContentView.TwoButtons.BottomButtonAction
            public void secondButtonAction() {
                XUIDownloadConfirmActivity.this.presenter.doSecondButtonAction();
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
