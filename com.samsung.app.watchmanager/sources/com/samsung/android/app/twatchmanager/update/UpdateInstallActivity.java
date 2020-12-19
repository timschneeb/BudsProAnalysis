package com.samsung.android.app.twatchmanager.update;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.content.FileProvider;
import b.j.a.b;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import java.io.File;

public class UpdateInstallActivity extends Activity {
    public static final String ACTION_CALL_PACKAGE_INSTALLER = "com.samsung.android.app.watchmanager.CALL_PACKAGE_INSTALLER";
    public static final String ACTION_CALL_PLAYSTORE = "com.samsung.android.app.watchmanager.CALL_PLAYSTORE";
    private static final String AUTHORITY = "com.samsung.android.app.watchmanager.fileprovider";
    public static final String INTENT_KEY_FILE_PATH = "file_path";
    public static final String INTENT_KEY_PACKAGE_NAME = "package_name";
    public static final String TAG = ("tUHM:[Update]" + UpdateInstallActivity.class.getSimpleName());
    private String mAction = "";
    private String mFilePath = "";
    private String mPackageName = "";

    private Intent makeInstallRequestIntent() {
        String str = TAG;
        Log.d(str, "makeInstallRequestIntent() filePath : " + this.mFilePath);
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT < 24) {
            intent.setDataAndType(Uri.fromFile(new File(this.mFilePath)), "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(FileProvider.a(this, AUTHORITY, new File(this.mFilePath)), "application/vnd.android.package-archive");
            intent.setFlags(1);
        }
        return intent;
    }

    private Intent makePlayStoreDeepLinkIntent() {
        String str = TAG;
        Log.d(str, "makePlayStoreDeepLinkIntent() packageName:" + this.mPackageName);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("market://details?id=" + this.mPackageName));
        intent.setPackage(GlobalConst.PACKAGE_NAME_PLAYSTORE);
        return intent;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        String str = TAG;
        Log.d(str, "onActivityResult requestCode [" + i + "], resultCode [" + i2 + "], intent [" + intent + "]");
        UpdateInstallData.setNonSamsungInstallRequested(false);
        new Handler().postDelayed(new Runnable() {
            /* class com.samsung.android.app.twatchmanager.update.UpdateInstallActivity.AnonymousClass1 */

            public void run() {
                Log.d(UpdateInstallActivity.TAG, "onActivityResult() send broadcast...");
                b.a(UpdateInstallActivity.this).a(new Intent(UpdateInstallActivity.this.mAction));
            }
        }, 1000);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        Log.d(TAG, "onCreate() starts...");
        super.onCreate(bundle);
        if (!UpdateInstallData.isNonSamsungInstallRequested()) {
            Intent intent = getIntent();
            if (intent != null) {
                this.mAction = intent.getAction();
            }
            Intent intent2 = null;
            if (ACTION_CALL_PACKAGE_INSTALLER.equals(this.mAction)) {
                this.mFilePath = intent.getStringExtra(INTENT_KEY_FILE_PATH);
                intent2 = makeInstallRequestIntent();
            } else if (ACTION_CALL_PLAYSTORE.equals(this.mAction)) {
                this.mPackageName = intent.getStringExtra("package_name");
                intent2 = makePlayStoreDeepLinkIntent();
            }
            String str = TAG;
            Log.d(str, "onCreate() mAction : " + this.mAction + " installRequestIntent : " + intent2);
            if (intent2 != null) {
                try {
                    startActivityForResult(intent2, 0);
                    UpdateInstallData.setNonSamsungInstallRequested(true);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            UpdateInstallData.setNonSamsungInstallRequested(false);
        }
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy() starts..");
        super.onDestroy();
    }
}
