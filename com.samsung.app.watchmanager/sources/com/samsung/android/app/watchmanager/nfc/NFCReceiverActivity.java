package com.samsung.android.app.watchmanager.nfc;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility;
import com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity;
import java.util.Arrays;

public class NFCReceiverActivity extends Activity {
    private static final String EXTRA_ADDR_NFC = "bt_addr";
    private static final String EXTRA_FROM_NFC = "isFromNFC";
    private final String TAG = NFCReceiverActivity.class.getSimpleName();
    private final BroadcastReceiver mBTScanReceiver = new BroadcastReceiver() {
        /* class com.samsung.android.app.watchmanager.nfc.NFCReceiverActivity.AnonymousClass2 */

        public void onReceive(Context context, Intent intent) {
            BluetoothDevice bluetoothDevice;
            String action = intent.getAction();
            String str = NFCReceiverActivity.this.TAG;
            Log.d(str, "onReceive() action:" + action);
            if ("android.bluetooth.device.action.FOUND".equals(action) && (bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")) != null) {
                String str2 = NFCReceiverActivity.this.TAG;
                Log.w(str2, "bt::Device Name = " + bluetoothDevice.getAddress());
                if (bluetoothDevice.getAddress() != null && bluetoothDevice.getAddress().equals(NFCReceiverActivity.this.mTargetBluetoothAdd)) {
                    if (NFCReceiverActivity.this.mBluetoothAdapter != null && NFCReceiverActivity.this.mBluetoothAdapter.isDiscovering()) {
                        NFCReceiverActivity.this.mBluetoothAdapter.cancelDiscovery();
                    }
                    NFCReceiverActivity.this.mHandler.removeCallbacks(NFCReceiverActivity.this.mRunnable);
                    NFCReceiverActivity.this.doRemainConnectProcess();
                }
            }
        }
    };
    private final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        /* class com.samsung.android.app.watchmanager.nfc.NFCReceiverActivity.AnonymousClass1 */

        public void run() {
            String str = NFCReceiverActivity.this.TAG;
            Log.d(str, "There is no FOUND Device. address = " + NFCReceiverActivity.this.mTargetBluetoothAdd);
            NFCReceiverActivity.this.doRemainConnectProcess();
        }
    };
    private String mTargetBluetoothAdd = "";
    private Intent newIntent = null;

    private boolean checkRecord(NdefRecord[] ndefRecordArr) {
        String str;
        String str2;
        if (ndefRecordArr == null) {
            str = this.TAG;
            str2 = "record is null - false";
        } else {
            String str3 = new String(ndefRecordArr[0].getPayload());
            String[] split = str3.split(":");
            String str4 = this.TAG;
            Log.d(str4, "checkRecord payload + " + str3 + "spliteStrings : " + Arrays.toString(split));
            if (split.length == 6 && str3.length() == 17) {
                return true;
            }
            str = this.TAG;
            str2 = "records[0] is not valid bt address - false";
        }
        Log.d(str, str2);
        return false;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void continueSetup() {
        this.mTargetBluetoothAdd = "";
        NdefRecord[] resolveIntent = resolveIntent(getIntent());
        boolean checkRecord = checkRecord(resolveIntent);
        if (checkRecord) {
            this.mTargetBluetoothAdd = getBluetoothAddr(resolveIntent[0]);
            String str = this.TAG;
            Log.d(str, "BT address[" + this.mTargetBluetoothAdd + "] from NFC tag");
            if (!TextUtils.isEmpty(this.mTargetBluetoothAdd)) {
                Log.d(this.TAG, "nfc::send BT address from NFCReceiver to SetupWizardWelcomeactivity");
                this.newIntent = new Intent(this, SetupWizardWelcomeActivity.class);
                this.newIntent.putExtra("isFromNFC", true);
                this.newIntent.putExtra("bt_addr", this.mTargetBluetoothAdd);
                String name = this.mBluetoothAdapter.getRemoteDevice(this.mTargetBluetoothAdd).getName();
                String str2 = this.TAG;
                Log.d(str2, "NFC deviceName = " + name);
                if (name == null) {
                    doDiscovery();
                } else {
                    doRemainConnectProcess();
                }
            }
        } else {
            String str3 = this.TAG;
            Log.d(str3, "checkRecord(records) = " + checkRecord);
            finish();
        }
    }

    private void doDiscovery() {
        Log.d(this.TAG, "doDiscovery");
        this.mBluetoothAdapter.startDiscovery();
        this.mHandler.postDelayed(this.mRunnable, 4000);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void doRemainConnectProcess() {
        Log.d(this.TAG, "doRemainConnectProcess");
        if (this.newIntent == null) {
            this.newIntent = new Intent(this, SetupWizardWelcomeActivity.class);
            this.newIntent.putExtra("isFromNFC", true);
            this.newIntent.putExtra("bt_addr", this.mTargetBluetoothAdd);
        }
        this.newIntent.setFlags(268435456);
        startActivity(this.newIntent);
        finish();
    }

    private String getBluetoothAddr(NdefRecord ndefRecord) {
        return new String(ndefRecord.getPayload());
    }

    private NdefMessage[] getNdefMessages(Intent intent) {
        if (!"android.nfc.action.NDEF_DISCOVERED".equals(intent.getAction())) {
            return null;
        }
        Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra("android.nfc.extra.NDEF_MESSAGES");
        if (parcelableArrayExtra != null) {
            NdefMessage[] ndefMessageArr = new NdefMessage[parcelableArrayExtra.length];
            for (int i = 0; i < parcelableArrayExtra.length; i++) {
                ndefMessageArr[i] = (NdefMessage) parcelableArrayExtra[i];
            }
            return ndefMessageArr;
        }
        byte[] bArr = new byte[0];
        return new NdefMessage[]{new NdefMessage(new NdefRecord[]{new NdefRecord(5, bArr, bArr, bArr)})};
    }

    private NdefRecord[] resolveIntent(Intent intent) {
        NdefMessage[] ndefMessages = getNdefMessages(intent);
        if (ndefMessages != null) {
            return ndefMessages[0].getRecords();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.w(this.TAG, "NFC onCreate()");
        registerReceiver(this.mBTScanReceiver, new IntentFilter("android.bluetooth.device.action.FOUND"));
        BluetoothDiscoveryUtility.turnOnBT(this, new BluetoothDiscoveryUtility.IBTStatusOnListener() {
            /* class com.samsung.android.app.watchmanager.nfc.NFCReceiverActivity.AnonymousClass3 */

            @Override // com.samsung.android.app.watchmanager.setupwizard.BluetoothDiscoveryUtility.IBTStatusOnListener
            public void onStatus(boolean z) {
                if (z) {
                    NFCReceiverActivity.this.continueSetup();
                } else {
                    NFCReceiverActivity.this.finish();
                }
            }
        }, true);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        Log.w(this.TAG, "NFC ondestory()");
        BroadcastReceiver broadcastReceiver = this.mBTScanReceiver;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        Log.saveLog();
        super.onDestroy();
    }
}
