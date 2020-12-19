package com.accessorydm.ui.fullscreen.basefullscreen;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.ViewStub;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.accessorydm.ui.UIManager;
import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenContract;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.UiUtil;
import com.sec.android.fotaprovider.R;

public abstract class XUIBaseFullscreenActivity extends AppCompatActivity implements XUIBaseFullscreenContract.View {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;

    /* access modifiers changed from: protected */
    public abstract XUIBaseFullscreenContract.Presenter getPresenter();

    public abstract void xuiGenerateBottomLayout(ViewStub viewStub);

    public abstract void xuiGenerateMiddleContentLayout(ViewStub viewStub);

    public abstract void xuiGenerateTopContentLayout(ViewStub viewStub);

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        Log.I("");
        super.onCreate(bundle);
        UiUtil.setOrientationFor(this);
        UIManager.getInstance().put(this);
        setContentView(R.layout.base_activity);
        this.collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.base_activity_collapsing_app_bar);
        this.toolbar = (Toolbar) findViewById(R.id.base_activity_toolbar);
        xuiGenerateTopContentLayout((ViewStub) findViewById(R.id.viewstub_top_content));
        xuiGenerateMiddleContentLayout((ViewStub) findViewById(R.id.viewstub_middle_content));
        xuiGenerateBottomLayout((ViewStub) findViewById(R.id.viewstub_bottom_content));
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenContract.View
    public void xuiSetAppBarWithTitleText(String str) {
        CollapsingToolbarLayout collapsingToolbarLayout2 = this.collapsingToolbarLayout;
        if (collapsingToolbarLayout2 != null) {
            collapsingToolbarLayout2.setTitle(str);
        }
        Toolbar toolbar2 = this.toolbar;
        if (toolbar2 != null) {
            toolbar2.setTitle(str);
        }
        setSupportActionBar(this.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenContract.View
    public void xuiOnBackPressed() {
        onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        Log.D("AsUp pressed");
        return getPresenter() != null && getPresenter().onOptionsItemSelected(menuItem.getItemId());
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        Log.D("BackKey pressed");
        return getPresenter() != null && getPresenter().onKeyDown(i);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onDestroy() {
        if (getPresenter() != null) {
            getPresenter().onDestroy();
        }
        UIManager.getInstance().remove(this);
        super.onDestroy();
    }
}
