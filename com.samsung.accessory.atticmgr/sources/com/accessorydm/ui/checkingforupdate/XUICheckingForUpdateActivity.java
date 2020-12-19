package com.accessorydm.ui.checkingforupdate;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewStub;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity;
import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenContract;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.type.DeviceType;
import com.sec.android.fotaprovider.R;

public class XUICheckingForUpdateActivity extends XUIBaseFullscreenActivity {
    /* access modifiers changed from: protected */
    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public XUIBaseFullscreenContract.Presenter getPresenter() {
        return null;
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void xuiGenerateBottomLayout(ViewStub viewStub) {
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void xuiGenerateMiddleContentLayout(ViewStub viewStub) {
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void xuiGenerateTopContentLayout(ViewStub viewStub) {
    }

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.I("");
        setContentView(R.layout.checking_for_update_activity);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.checking_for_update_activity_collapsing_app_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.checking_for_update_activity_toolbar);
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setTitle(FotaProviderInitializer.getContext().getString(DeviceType.get().getTextType().getTitleId()));
        }
        if (toolbar != null) {
            toolbar.setTitle(FotaProviderInitializer.getContext().getString(DeviceType.get().getTextType().getTitleId()));
        }
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenActivity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            Log.D("AsUp pressed");
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
