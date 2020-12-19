package com.samsung.accessory.hearablemgr.module.aboutgalaxywearable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.service.DeviceLogManager;
import com.samsung.accessory.hearablemgr.module.base.ConnectionActivity;
import seccompat.android.util.Log;

public class VerificationDumpLogActivity extends ConnectionActivity {
    private static final String TAG = (Application.TAG_ + VerificationDeviceInfoActivity.class.getSimpleName());
    private String debugMessage = "no data";
    private AppCompatButton mButton;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.module.aboutgalaxywearable.VerificationDumpLogActivity.AnonymousClass2 */

        /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
        public void onReceive(Context context, Intent intent) {
            char c;
            Log.d(VerificationDumpLogActivity.TAG, "onReceive() : " + intent.getAction());
            String action = intent.getAction();
            switch (action.hashCode()) {
                case -1976628078:
                    if (action.equals(DeviceLogManager.ACTION_MSG_ID_LOG_SESSION_CLOSE)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -781828191:
                    if (action.equals(DeviceLogManager.ACTION_MSG_ID_LOG_TRACE_DATA)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 136106087:
                    if (action.equals(DeviceLogManager.ACTION_MSG_ID_LOG_COREDUMP_DATA_SIZE)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 989784089:
                    if (action.equals(DeviceLogManager.ACTION_MSG_ID_LOG_COREDUMP_DATA)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1293228201:
                    if (action.equals(DeviceLogManager.ACTION_MSG_ID_LOG_COREDUMP_COMPLETE)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1322072144:
                    if (action.equals(DeviceLogManager.ACTION_MSG_ID_LOG_SESSION_OPEN)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1350325967:
                    if (action.equals(DeviceLogManager.ACTION_MSG_ID_LOG_TRACE_COMPLETE)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1547531051:
                    if (action.equals(DeviceLogManager.ACTION_MSG_ID_LOG_TRACE_START)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1617425222:
                    if (action.equals(DeviceLogManager.ACTION_MSG_ID_LOG_TRACE_ROLE_SWITCH)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    VerificationDumpLogActivity.this.debugMessage = "open session";
                    ((TextView) VerificationDumpLogActivity.this.findViewById(R.id.layout_device_info_data)).setText(VerificationDumpLogActivity.this.debugMessage);
                    return;
                case 1:
                    VerificationDumpLogActivity.this.debugMessage = "close session";
                    ((TextView) VerificationDumpLogActivity.this.findViewById(R.id.layout_device_info_data)).setText(VerificationDumpLogActivity.this.debugMessage);
                    VerificationDumpLogActivity.this.mButton.setAlpha(1.0f);
                    VerificationDumpLogActivity.this.mButton.setEnabled(true);
                    return;
                case 2:
                    VerificationDumpLogActivity.this.debugMessage = "coredump data size";
                    ((TextView) VerificationDumpLogActivity.this.findViewById(R.id.layout_device_info_data)).setText(VerificationDumpLogActivity.this.debugMessage);
                    return;
                case 3:
                    VerificationDumpLogActivity.this.debugMessage = "core dump data";
                    ((TextView) VerificationDumpLogActivity.this.findViewById(R.id.layout_device_info_data)).setText(VerificationDumpLogActivity.this.debugMessage);
                    return;
                case 4:
                    VerificationDumpLogActivity.this.debugMessage = "coredump complete";
                    ((TextView) VerificationDumpLogActivity.this.findViewById(R.id.layout_device_info_data)).setText(VerificationDumpLogActivity.this.debugMessage);
                    return;
                case 5:
                    VerificationDumpLogActivity.this.debugMessage = "trace start";
                    ((TextView) VerificationDumpLogActivity.this.findViewById(R.id.layout_device_info_data)).setText(VerificationDumpLogActivity.this.debugMessage);
                    return;
                case 6:
                    VerificationDumpLogActivity.this.debugMessage = "trace data";
                    ((TextView) VerificationDumpLogActivity.this.findViewById(R.id.layout_device_info_data)).setText(VerificationDumpLogActivity.this.debugMessage);
                    return;
                case 7:
                    VerificationDumpLogActivity.this.debugMessage = "trace complete";
                    ((TextView) VerificationDumpLogActivity.this.findViewById(R.id.layout_device_info_data)).setText(VerificationDumpLogActivity.this.debugMessage);
                    return;
                case '\b':
                    VerificationDumpLogActivity.this.debugMessage = "role switch";
                    ((TextView) VerificationDumpLogActivity.this.findViewById(R.id.layout_device_info_data)).setText(VerificationDumpLogActivity.this.debugMessage);
                    return;
                default:
                    return;
            }
        }
    };

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_verification_dump_log);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Device Dump Log");
        registerReceiver();
        this.mButton = (AppCompatButton) findViewById(R.id.button_start);
        this.mButton.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutgalaxywearable.VerificationDumpLogActivity.AnonymousClass1 */

            public void onClick(View view) {
                Application.getCoreService().getDeviceLogInfo().sendOpenSession();
                VerificationDumpLogActivity.this.mButton.setAlpha(0.4f);
                VerificationDumpLogActivity.this.mButton.setEnabled(false);
            }
        });
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onDestroy() {
        unregisterReceiver(this.mReceiver);
        Application.getCoreService().getDeviceLogInfo().setDeviceLogExtractionState(0);
        super.onDestroy();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DeviceLogManager.ACTION_MSG_ID_LOG_SESSION_OPEN);
        intentFilter.addAction(DeviceLogManager.ACTION_MSG_ID_LOG_SESSION_CLOSE);
        intentFilter.addAction(DeviceLogManager.ACTION_MSG_ID_LOG_COREDUMP_DATA_SIZE);
        intentFilter.addAction(DeviceLogManager.ACTION_MSG_ID_LOG_COREDUMP_DATA);
        intentFilter.addAction(DeviceLogManager.ACTION_MSG_ID_LOG_COREDUMP_COMPLETE);
        intentFilter.addAction(DeviceLogManager.ACTION_MSG_ID_LOG_TRACE_START);
        intentFilter.addAction(DeviceLogManager.ACTION_MSG_ID_LOG_TRACE_DATA);
        intentFilter.addAction(DeviceLogManager.ACTION_MSG_ID_LOG_TRACE_COMPLETE);
        intentFilter.addAction(DeviceLogManager.ACTION_MSG_ID_LOG_TRACE_ROLE_SWITCH);
        registerReceiver(this.mReceiver, intentFilter);
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
