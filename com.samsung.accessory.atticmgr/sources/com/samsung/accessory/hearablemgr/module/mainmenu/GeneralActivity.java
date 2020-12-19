package com.samsung.accessory.hearablemgr.module.mainmenu;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.work.WorkRequest;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.uhm.DeviceRegistryData;
import com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.message.MsgReset;
import com.samsung.accessory.hearablemgr.core.uhmdb.UhmDatabase;
import com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity;
import com.samsung.accessory.hearablemgr.module.tipsmanual.TipsAndUserManualActivity;
import seccompat.android.util.Log;

public class GeneralActivity extends PermissionCheckActivity {
    private static final int MIN_BATTERY_GAUGE = 15;
    private static final String TAG = (Application.TAG_ + GeneralActivity.class.getSimpleName());
    private boolean isWorkingResetProcess;
    private Handler mProgressDialogTimer = new Handler();
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.module.mainmenu.GeneralActivity.AnonymousClass8 */

        /* JADX WARNING: Removed duplicated region for block: B:18:0x0059  */
        /* JADX WARNING: Removed duplicated region for block: B:46:0x0133  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r5, android.content.Intent r6) {
            /*
            // Method dump skipped, instructions count: 391
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.module.mainmenu.GeneralActivity.AnonymousClass8.onReceive(android.content.Context, android.content.Intent):void");
        }
    };
    private ProgressDialog progressDialog;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_general);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(getString(R.string.general));
        findViewById(R.id.text_user_manaual).setOnClickListener(new OnSingleClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.GeneralActivity.AnonymousClass1 */

            @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
            public void onSingleClick(View view) {
                SamsungAnalyticsUtil.sendEvent(SA.Event.ELSE_USER_MANUAL, SA.Screen.GENERAL);
                TipsAndUserManualActivity.startUserManual(GeneralActivity.this);
            }
        });
        findViewById(R.id.reset_earbuds).setOnClickListener(new OnSingleClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.GeneralActivity.AnonymousClass2 */

            @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
            public void onSingleClick(View view) {
                SamsungAnalyticsUtil.sendEvent(SA.Event.RESET_EARBUDS, SA.Screen.GENERAL);
                GeneralActivity.this.alertDialog(Application.getCoreService().getEarBudsInfo());
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void alertDialog(EarBudsInfo earBudsInfo) {
        Integer valueOf = earBudsInfo.batteryI == -1 ? null : Integer.valueOf(earBudsInfo.batteryI);
        if ((valueOf != null || (earBudsInfo.batteryL < 15 && earBudsInfo.batteryR < 15)) && (valueOf == null || valueOf.intValue() < 15)) {
            batteryLowDialog();
        } else {
            resetDialog();
        }
    }

    private void resetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getApplicationContext().getString(R.string.reset_earbuds_dialog_title));
        builder.setMessage(getApplicationContext().getString(R.string.reset_earbuds_dialog_message));
        builder.setNegativeButton(getApplicationContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.GeneralActivity.AnonymousClass3 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton(getApplicationContext().getString(R.string.button_reset), new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.GeneralActivity.AnonymousClass4 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                Application.getCoreService().sendSppMessage(new MsgReset());
                GeneralActivity.this.resetProgressDialog();
            }
        });
        builder.show();
    }

    private void batteryLowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getApplicationContext().getString(R.string.battery_low_dialog_title));
        builder.setMessage(getApplicationContext().getString(R.string.battery_low_dialog_message, 15));
        builder.setPositiveButton(getApplicationContext().getString(R.string.ok), new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.GeneralActivity.AnonymousClass5 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void resetFailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getApplicationContext().getString(R.string.reset_fail_dialog_title));
        builder.setMessage(getApplicationContext().getString(R.string.reset_fail_dialog_message));
        builder.setPositiveButton(getApplicationContext().getString(R.string.ok), new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.GeneralActivity.AnonymousClass6 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void resetProgressDialog() {
        Log.d(TAG, "resetProgressDialog");
        this.isWorkingResetProcess = true;
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage(getApplicationContext().getString(R.string.reset_progress_dialog_message));
        this.progressDialog.setCancelable(false);
        this.progressDialog.setProgressStyle(16973855);
        this.progressDialog.show();
        this.mProgressDialogTimer.postDelayed(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.module.mainmenu.GeneralActivity.AnonymousClass7 */

            public void run() {
                Log.i(GeneralActivity.TAG, "resetProgressDialogTimeOut run");
                GeneralActivity.this.isWorkingResetProcess = false;
                if (GeneralActivity.this.progressDialog != null && GeneralActivity.this.progressDialog.isShowing()) {
                    GeneralActivity.this.progressDialog.cancel();
                    GeneralActivity.this.progressDialog = null;
                }
            }
        }, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void unpairDevice(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "unpairDevice");
        try {
            bluetoothDevice.getClass().getMethod("removeBond", null).invoke(bluetoothDevice, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CoreService.ACTION_MSG_ID_RESET);
        intentFilter.addAction(CoreService.ACTION_DEVICE_DISCONNECTED);
        intentFilter.addAction(UhmDatabase.ACTION_DB_UPDATED);
        registerReceiver(this.mReceiver, intentFilter);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onPause() {
        ProgressDialog progressDialog2 = this.progressDialog;
        if (progressDialog2 != null && progressDialog2.isShowing()) {
            this.progressDialog.cancel();
            this.progressDialog = null;
        }
        super.onPause();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onResume() {
        SamsungAnalyticsUtil.sendPage(SA.Screen.GENERAL);
        registerReceiver();
        super.onResume();
        if (!Application.getCoreService().isConnected()) {
            Log.w(TAG, "isConnected() == false -> finish()");
            finish();
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onStop() {
        super.onStop();
        unregisterReceiver(this.mReceiver);
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        SamsungAnalyticsUtil.sendEvent(SA.Event.UP_BUTTON, SA.Screen.GENERAL);
        finish();
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private DeviceRegistryData getDeviceByStatus(int i) {
        for (DeviceRegistryData deviceRegistryData : Application.getUhmDatabase().getDeviceList()) {
            if (deviceRegistryData.connected.intValue() == i) {
                return deviceRegistryData;
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private DeviceRegistryData getLastLaunchedDevice() {
        for (DeviceRegistryData deviceRegistryData : Application.getUhmDatabase().getDeviceList()) {
            if (deviceRegistryData.lastLaunch.intValue() == 1) {
                return deviceRegistryData;
            }
        }
        return null;
    }
}
