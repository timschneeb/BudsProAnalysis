package com.accessorydm.ui.progress;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewStub;
import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity;
import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenContract;
import com.accessorydm.ui.fullscreen.content.MiddleContentView;
import com.accessorydm.ui.fullscreen.content.TopContentView;
import com.accessorydm.ui.progress.XUIProgressContract;
import com.samsung.android.fotaprovider.log.Log;

public class XUIProgressActivity extends XUIBaseFullscreenActivity implements XUIProgressContract.View {
    private MiddleContentView.WithoutCaution middleContentView = null;
    private XUIProgressPresenter presenter;
    private TopContentView.Progress topContentView = null;

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void xuiGenerateBottomLayout(ViewStub viewStub) {
    }

    /* access modifiers changed from: protected */
    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public XUIBaseFullscreenContract.Presenter getPresenter() {
        return this.presenter;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void onCreate(Bundle bundle) {
        Log.I("");
        xuiCreatePresenter(getIntent().getIntExtra("progressMode", 0));
        super.onCreate(bundle);
        this.presenter.onCreate();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onNewIntent(Intent intent) {
        Log.I("");
        xuiCreatePresenter(intent.getIntExtra("progressMode", 0));
        super.onNewIntent(intent);
        this.presenter.onCreate();
    }

    private void xuiCreatePresenter(int i) {
        if (i == 1) {
            Log.I("Current progress: Download");
            this.presenter = new XUIDownloadProgressPresenter(this, XUIProgressModel.getInstance());
        } else if (i != 2) {
            Log.W("Current progress: Not available");
            finish();
        } else {
            Log.I("Current progress: Copy");
            this.presenter = new XUICopyProgressPresenter(this, XUIProgressModel.getInstance());
        }
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void xuiGenerateTopContentLayout(ViewStub viewStub) {
        this.topContentView = new TopContentView.Progress(this, viewStub);
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void xuiGenerateMiddleContentLayout(ViewStub viewStub) {
        this.middleContentView = new MiddleContentView.WithoutCaution(this, viewStub);
    }

    @Override // com.accessorydm.ui.progress.XUIProgressContract.View
    public TopContentView.Progress getTopContentView() {
        return this.topContentView;
    }

    @Override // com.accessorydm.ui.progress.XUIProgressContract.View
    public MiddleContentView.WithoutCaution getMiddleContentView() {
        return this.middleContentView;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void onDestroy() {
        Log.D("");
        super.onDestroy();
    }
}
