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
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.message.MsgDebugData;
import com.samsung.accessory.hearablemgr.core.service.message.MsgDebugSerialNumber;
import com.samsung.accessory.hearablemgr.module.base.ConnectionActivity;
import seccompat.android.util.Log;

public class VerificationDeviceInfoActivity extends ConnectionActivity {
    private static final String TAG = (Application.TAG_ + VerificationDeviceInfoActivity.class.getSimpleName());
    private String debugMessage = "no data";
    private AppCompatButton mButton;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.module.aboutgalaxywearable.VerificationDeviceInfoActivity.AnonymousClass2 */

        public void onReceive(Context context, Intent intent) {
            String str = VerificationDeviceInfoActivity.TAG;
            Log.d(str, "onReceive() : " + intent.getAction());
            String action = intent.getAction();
            if (((action.hashCode() == 1215575359 && action.equals(CoreService.ACTION_MSG_ID_DEBUG_GET_ALL_DATA)) ? (char) 0 : 65535) == 0) {
                ((TextView) VerificationDeviceInfoActivity.this.findViewById(R.id.layout_device_serial_number)).setText(VerificationDeviceInfoConvert.serialNumberConvert(new StringBuilder()));
                ((TextView) VerificationDeviceInfoActivity.this.findViewById(R.id.layout_device_info_data)).setText(Application.getCoreService().getEarBudsInfo().debugInfo);
                VerificationDeviceInfoActivity.this.mButton.setAlpha(1.0f);
                VerificationDeviceInfoActivity.this.mButton.setEnabled(true);
            }
        }
    };

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_verification_device_info);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Device Info");
        registerReceiver();
        this.mButton = (AppCompatButton) findViewById(R.id.button_request);
        this.mButton.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.aboutgalaxywearable.VerificationDeviceInfoActivity.AnonymousClass1 */

            public void onClick(View view) {
                Application.getCoreService().sendSppMessage(new MsgDebugSerialNumber());
                Application.getCoreService().sendSppMessage(new MsgDebugData());
                VerificationDeviceInfoActivity.this.mButton.setAlpha(0.4f);
                VerificationDeviceInfoActivity.this.mButton.setEnabled(false);
            }
        });
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onDestroy() {
        unregisterReceiver(this.mReceiver);
        super.onDestroy();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CoreService.ACTION_MSG_ID_DEBUG_GET_ALL_DATA);
        registerReceiver(this.mReceiver, intentFilter);
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
