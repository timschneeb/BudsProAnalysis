package com.samsung.accessory.hearablemgr.module.aboutgalaxywearable;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import seccompat.android.util.Log;

public class AboutGalaxyWearableActivity extends PermissionCheckActivity {
    private static final String GALAXY_WEARABLE_APP_INFO = "android.settings.APPLICATION_DETAILS_SETTINGS";
    private static final String OPEN_SOURCE_LICENCE_PATH = "Open_source_Announcement.txt";
    private static final String TAG = "Attic_AboutGalaxyWearableActivity";
    private int mClickCount = 0;
    private Button mUpdateButton;
    private TextView mUpdateStateText;

    static /* synthetic */ int access$108(AboutGalaxyWearableActivity aboutGalaxyWearableActivity) {
        int i = aboutGalaxyWearableActivity.mClickCount;
        aboutGalaxyWearableActivity.mClickCount = i + 1;
        return i;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        Log.d(TAG, "onCreate()");
        super.onCreate(bundle);
        setContentView(R.layout.activity_about_galaxy_wearable);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initView();
        updatePluginInfo();
        findViewById(R.id.text_uhm_title).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutgalaxywearable.AboutGalaxyWearableActivity.AnonymousClass1 */

            public void onClick(View view) {
                if (AboutGalaxyWearableActivity.this.checkCondition()) {
                    AboutGalaxyWearableActivity.access$108(AboutGalaxyWearableActivity.this);
                    if (AboutGalaxyWearableActivity.this.mClickCount == 10) {
                        Log.d(AboutGalaxyWearableActivity.TAG, "Show verification Toast Message.");
                        AboutGalaxyWearableActivity aboutGalaxyWearableActivity = AboutGalaxyWearableActivity.this;
                        aboutGalaxyWearableActivity.startActivity(new Intent(aboutGalaxyWearableActivity, VerificationMenuActivity.class));
                        AboutGalaxyWearableActivity.this.mClickCount = 0;
                    }
                }
            }
        });
        this.mUpdateButton.setOnClickListener(new OnSingleClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutgalaxywearable.AboutGalaxyWearableActivity.AnonymousClass2 */

            @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
            public void onSingleClick(View view) {
                Log.i(AboutGalaxyWearableActivity.TAG, "onClick::app update");
                SamsungAnalyticsUtil.sendEvent(SA.Event.UPDATE, SA.Screen.ABOUT_GALAXY_WEARABLE_APP);
                AboutGalaxyWearableActivity.this.installPlugin();
            }
        });
        findViewById(R.id.image_app_info).setOnClickListener(new OnSingleClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutgalaxywearable.AboutGalaxyWearableActivity.AnonymousClass3 */

            @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
            public void onSingleClick(View view) {
                SamsungAnalyticsUtil.sendEvent(SA.Event.APP_INFORMATION, SA.Screen.ABOUT_GALAXY_WEARABLE_APP);
                AboutGalaxyWearableActivity aboutGalaxyWearableActivity = AboutGalaxyWearableActivity.this;
                Intent intent = new Intent(AboutGalaxyWearableActivity.GALAXY_WEARABLE_APP_INFO);
                aboutGalaxyWearableActivity.startActivity(intent.setData(Uri.parse("package:" + AboutGalaxyWearableActivity.this.getPackageName())));
            }
        });
        ((TextView) findViewById(R.id.text_open_source_licenses)).setOnClickListener(new OnSingleClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutgalaxywearable.AboutGalaxyWearableActivity.AnonymousClass4 */

            @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
            public void onSingleClick(View view) {
                SamsungAnalyticsUtil.sendEvent(SA.Event.OPEN_SOURCE_LICENSES, SA.Screen.ABOUT_GALAXY_WEARABLE_APP);
                AboutGalaxyWearableActivity.this.alertDialog();
            }
        });
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onResume() {
        super.onResume();
        SamsungAnalyticsUtil.sendPage(SA.Screen.ABOUT_GALAXY_WEARABLE_APP);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        String str;
        PackageManager.NameNotFoundException e;
        TextView textView = (TextView) findViewById(R.id.text_uhm_version);
        TextView textView2 = (TextView) findViewById(R.id.text_app_version);
        this.mUpdateStateText = (TextView) findViewById(R.id.text_update_state);
        this.mUpdateButton = (Button) findViewById(R.id.button_app_update);
        String str2 = null;
        try {
            str = Application.getContext().getPackageManager().getPackageInfo(UhmFwUtil.getUhmPackageName(), 0).versionName;
            try {
                str2 = Application.getContext().getPackageManager().getPackageInfo(Application.getContext().getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e2) {
                e = e2;
            }
        } catch (PackageManager.NameNotFoundException e3) {
            e = e3;
            str = null;
            e.printStackTrace();
            textView.setText(getString(R.string.download_update_version) + " " + str);
            textView2.setText(getString(R.string.download_update_version) + " " + str2);
        }
        textView.setText(getString(R.string.download_update_version) + " " + str);
        textView2.setText(getString(R.string.download_update_version) + " " + str2);
    }

    private void updatePluginInfo() {
        if (Preferences.getBoolean(PreferenceKey.EXISTED_NEW_VERSION_PLUGIN, false, UhmFwUtil.getLastLaunchDeviceId())) {
            this.mUpdateStateText.setText(R.string.update_available);
            this.mUpdateButton.setVisibility(0);
            return;
        }
        this.mUpdateStateText.setText(R.string.latest_version_installed);
        this.mUpdateButton.setVisibility(8);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void installPlugin() {
        if (Util.getActiveNetworkInfo() >= 0) {
            this.mUpdateStateText.setVisibility(4);
            this.mUpdateButton.setVisibility(4);
            findViewById(R.id.progress_app_update).setVisibility(0);
            startUpdateModuleActivity();
            finish();
            return;
        }
        this.mUpdateButton.setVisibility(0);
        this.mUpdateStateText.setVisibility(0);
        this.mUpdateButton.setText(R.string.retry);
        this.mUpdateStateText.setText(R.string.cannot_check_for_app_update);
        findViewById(R.id.progress_app_update).setVisibility(4);
    }

    private void startUpdateModuleActivity() {
        boolean isConnected = Application.getCoreService().isConnected();
        Intent intent = new Intent();
        intent.setPackage(UhmFwUtil.getUhmPackageName());
        intent.putExtra("isFromUpdateNotification", true);
        intent.putExtra("connstatus", isConnected);
        intent.putExtra("isFromPlugin", true);
        intent.setFlags(65536);
        intent.addFlags(268435456);
        startActivity(intent);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void alertDialog() {
        Log.d(TAG, "alertDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.legal_info_open_source_licenses));
        builder.setMessage(getStringFromAsset(this));
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutgalaxywearable.AboutGalaxyWearableActivity.AnonymousClass5 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    private String getStringFromAsset(Context context) {
        String str;
        IOException e;
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(context.getAssets().open(OPEN_SOURCE_LICENCE_PATH));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int read = bufferedInputStream.read();
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(read);
            }
            str = new String(byteArrayOutputStream.toByteArray());
            try {
                bufferedInputStream.close();
            } catch (IOException e2) {
                e = e2;
            }
        } catch (IOException e3) {
            e = e3;
            str = "";
            e.printStackTrace();
            return str;
        }
        return str;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean checkCondition() {
        return new File(Environment.getExternalStorageDirectory() + "/go_to_cassiopeia.test").exists();
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        SamsungAnalyticsUtil.sendEvent(SA.Event.UP_BUTTON, SA.Screen.ABOUT_GALAXY_WEARABLE_APP);
        finish();
        return true;
    }
}
