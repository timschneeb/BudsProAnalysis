package com.accessorydm.ui.installconfirm;

import android.os.Bundle;
import android.view.ViewStub;
import androidx.fragment.app.FragmentManager;
import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity;
import com.accessorydm.ui.fullscreen.content.BottomContentView;
import com.accessorydm.ui.fullscreen.content.MiddleContentView;
import com.accessorydm.ui.fullscreen.content.TopContentView;
import com.accessorydm.ui.installconfirm.XUIInstallConfirmContract;
import com.accessorydm.ui.installconfirm.scheduleinstall.ScheduleInstallDialog;
import com.samsung.android.fotaprovider.log.Log;

public class XUIInstallConfirmActivity extends XUIBaseFullscreenActivity implements XUIInstallConfirmContract.View {
    private BottomContentView.TwoButtons bottomContentView = null;
    private MiddleContentView.WithCaution middleContentView = null;
    private XUIInstallConfirmContract.Presenter presenter;
    private TopContentView.Guide topContentView = null;

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public XUIInstallConfirmContract.Presenter getPresenter() {
        return this.presenter;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void onCreate(Bundle bundle) {
        Log.I("");
        this.presenter = new XUIInstallConfirmPresenter(this, XUIInstallConfirmModel.getInstance());
        super.onCreate(bundle);
        this.presenter.onCreate();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onResume() {
        Log.I("");
        super.onResume();
        this.presenter.onResume();
    }

    /* access modifiers changed from: protected */
    public void onUserLeaveHint() {
        Log.D("");
        this.presenter.onUserLeaveHint();
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

    @Override // com.accessorydm.ui.installconfirm.XUIInstallConfirmContract.View
    public TopContentView.Guide getTopContentView() {
        return this.topContentView;
    }

    @Override // com.accessorydm.ui.installconfirm.XUIInstallConfirmContract.View
    public MiddleContentView.WithCaution getMiddleContentView() {
        return this.middleContentView;
    }

    @Override // com.accessorydm.ui.installconfirm.XUIInstallConfirmContract.View
    public BottomContentView.TwoButtons getBottomContentView() {
        return this.bottomContentView;
    }

    @Override // com.accessorydm.ui.installconfirm.XUIInstallConfirmContract.View
    public void xuiSetBottomButtonsClickListeners() {
        this.bottomContentView.setBottomButtonClickListeners(new BottomContentView.TwoButtons.BottomButtonAction() {
            /* class com.accessorydm.ui.installconfirm.XUIInstallConfirmActivity.AnonymousClass1 */

            @Override // com.accessorydm.ui.fullscreen.content.BottomContentView.TwoButtons.BottomButtonAction
            public void firstButtonAction() {
                XUIInstallConfirmActivity.this.presenter.doFirstButtonAction();
            }

            @Override // com.accessorydm.ui.fullscreen.content.BottomContentView.TwoButtons.BottomButtonAction
            public void secondButtonAction() {
                XUIInstallConfirmActivity.this.presenter.doSecondButtonAction();
            }
        });
    }

    @Override // com.accessorydm.ui.installconfirm.XUIInstallConfirmContract.View
    public void xuiShowScheduleInstallDialog() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager.findFragmentByTag(ScheduleInstallDialog.TAG) == null) {
            new ScheduleInstallDialog().show(supportFragmentManager, ScheduleInstallDialog.TAG);
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void onDestroy() {
        Log.D("");
        super.onDestroy();
    }
}
