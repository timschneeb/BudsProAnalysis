package com.samsung.accessory.hearablemgr.core.fmm.receiver;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.core.fmm.FmmManager;
import com.samsung.accessory.hearablemgr.core.fmm.utils.FmmConstants;
import com.samsung.accessory.hearablemgr.core.fmm.utils.FmmUtils;
import com.samsung.accessory.hearablemgr.core.fmm.utils.RingManager;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import seccompat.android.util.Log;

public class FmmDeviceStatusReceiver {
    private static final String TAG = (Application.TAG_ + FmmDeviceStatusReceiver.class.getSimpleName());
    Context mContext;
    private boolean mCurrentCoupledStatus;
    private BroadcastReceiver mDeviceResponseReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.fmm.receiver.FmmDeviceStatusReceiver.AnonymousClass3 */

        /* JADX WARNING: Removed duplicated region for block: B:12:0x002b  */
        /* JADX WARNING: Removed duplicated region for block: B:14:0x004b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r5, android.content.Intent r6) {
            /*
                r4 = this;
                java.lang.String r0 = r6.getAction()
                int r1 = r0.hashCode()
                r2 = -123216535(0xfffffffff8a7dd69, float:-2.7237635E34)
                r3 = 1
                if (r1 == r2) goto L_0x001e
                r2 = 89245058(0x551c582, float:9.863409E-36)
                if (r1 == r2) goto L_0x0014
                goto L_0x0028
            L_0x0014:
                java.lang.String r1 = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_SET_FMM_CONFIG_RESULT"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0028
                r0 = 0
                goto L_0x0029
            L_0x001e:
                java.lang.String r1 = "com.samsung.accessory.hearablemgr.core.service.CoreService.ACTION_MSG_ID_GET_FMM_CONFIG_RESP"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0028
                r0 = r3
                goto L_0x0029
            L_0x0028:
                r0 = -1
            L_0x0029:
                if (r0 == 0) goto L_0x004b
                if (r0 == r3) goto L_0x002e
                goto L_0x0057
            L_0x002e:
                java.lang.String r0 = com.samsung.accessory.hearablemgr.core.fmm.receiver.FmmDeviceStatusReceiver.access$000()
                java.lang.String r1 = "ACTION_MSG_ID_GET_FMM_CONFIG_RESP"
                seccompat.android.util.Log.d(r0, r1)
                java.lang.String r0 = "getFmmConfig"
                byte[] r6 = r6.getByteArrayExtra(r0)
                java.nio.ByteBuffer r6 = java.nio.ByteBuffer.wrap(r6)
                java.nio.ByteOrder r0 = java.nio.ByteOrder.LITTLE_ENDIAN
                java.nio.ByteBuffer r6 = r6.order(r0)
                com.samsung.accessory.hearablemgr.core.fmm.FmmManager.responseGetDeviceInfo(r5, r6)
                goto L_0x0057
            L_0x004b:
                java.lang.String r6 = com.samsung.accessory.hearablemgr.core.fmm.receiver.FmmDeviceStatusReceiver.access$000()
                java.lang.String r0 = "ACTION_MSG_ID_SET_FMM_CONFIG_RESULT"
                seccompat.android.util.Log.d(r6, r0)
                com.samsung.accessory.hearablemgr.core.fmm.FmmManager.responseSetDeviceInfo(r5)
            L_0x0057:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.core.fmm.receiver.FmmDeviceStatusReceiver.AnonymousClass3.onReceive(android.content.Context, android.content.Intent):void");
        }
    };
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.fmm.receiver.FmmDeviceStatusReceiver.AnonymousClass1 */

        public void onReceive(Context context, Intent intent) {
            if (!FmmUtils.isSupportedOfflineFinding().booleanValue() || !FmmUtils.isFmmSupport().booleanValue()) {
                Log.d(FmmDeviceStatusReceiver.TAG, "Not supported fmm");
                return;
            }
            String action = intent.getAction();
            char c = 65535;
            switch (action.hashCode()) {
                case -1854841232:
                    if (action.equals(CoreService.ACTION_MSG_ID_STATUS_UPDATED)) {
                        c = 4;
                        break;
                    }
                    break;
                case -1553437513:
                    if (action.equals(CoreService.ACTION_DEVICE_EXTENDED_STATUS_READY)) {
                        c = 0;
                        break;
                    }
                    break;
                case -1436980304:
                    if (action.equals(CoreService.ACTION_MUTE_EARBUD_STATUS_UPDATED)) {
                        c = 3;
                        break;
                    }
                    break;
                case -1418242124:
                    if (action.equals(CoreService.ACTION_DEVICE_DISCONNECTED_FROM_CONNECT)) {
                        c = 1;
                        break;
                    }
                    break;
                case -302900700:
                    if (action.equals(CoreService.ACTION_MSG_ID_FIND_MY_EARBUDS_STATUS_UPDATED)) {
                        c = 2;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                FmmDeviceStatusReceiver.this.mCurrentCoupledStatus = Application.getCoreService().getEarBudsInfo().coupled;
            } else if (c != 1) {
                if (c == 2 || c == 3) {
                    String stringExtra = intent.getStringExtra(RingManager.KEY_SENDER_ID);
                    if (stringExtra == null || !FmmRequestReceiver.class.getName().equals(stringExtra)) {
                        FmmManager.sendAction(context, FmmConstants.Operation.RING_STATUS);
                        return;
                    }
                    Log.d(FmmDeviceStatusReceiver.TAG, "This intent from Fmm request. Do not send ring status. senderId : " + stringExtra);
                    return;
                } else if (c == 4 && Application.getCoreService().isExtendedStatusReady() && FmmDeviceStatusReceiver.this.mCurrentCoupledStatus != Application.getCoreService().getEarBudsInfo().coupled) {
                    FmmDeviceStatusReceiver.this.mCurrentCoupledStatus = Application.getCoreService().getEarBudsInfo().coupled;
                    FmmManager.sendAction(context, FmmConstants.Operation.STATUS_CHANGE);
                    return;
                } else {
                    return;
                }
            }
            Log.d(FmmDeviceStatusReceiver.TAG, "FMM:CONNECTION");
            FmmManager.sendAction(context, FmmConstants.Operation.CONNECTION);
        }
    };
    private BroadcastReceiver mUnpairReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.fmm.receiver.FmmDeviceStatusReceiver.AnonymousClass2 */

        public void onReceive(Context context, Intent intent) {
            String str = FmmDeviceStatusReceiver.TAG;
            Log.d(str, "onReceive() : " + intent.getAction());
            if (intent.getAction() != null && intent.getAction().equals("android.bluetooth.device.action.BOND_STATE_CHANGED")) {
                int intExtra = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", Integer.MIN_VALUE);
                int intExtra2 = intent.getIntExtra("android.bluetooth.device.extra.PREVIOUS_BOND_STATE", Integer.MIN_VALUE);
                if (((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")).getAddress().equals(UhmFwUtil.getLastLaunchDeviceId()) && intExtra == 10 && intExtra != intExtra2) {
                    FmmManager.sendAction(context, FmmConstants.Operation.REMOVE);
                }
            }
        }
    };

    public FmmDeviceStatusReceiver(Context context) {
        this.mContext = context;
        onCreate();
    }

    private void onCreate() {
        registerReceiver();
    }

    public void onDestroy() {
        unRegisterReceiver();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CoreService.ACTION_DEVICE_EXTENDED_STATUS_READY);
        intentFilter.addAction(CoreService.ACTION_DEVICE_DISCONNECTED_FROM_CONNECT);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_FIND_MY_EARBUDS_STATUS_UPDATED);
        intentFilter.addAction(CoreService.ACTION_MUTE_EARBUD_STATUS_UPDATED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_STATUS_UPDATED);
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
        this.mContext.registerReceiver(this.mUnpairReceiver, intentFilter2);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction(CoreService.ACTION_MSG_ID_SET_FMM_CONFIG_RESULT);
        intentFilter3.addAction(CoreService.ACTION_MSG_ID_GET_FMM_CONFIG_RESP);
        this.mContext.registerReceiver(this.mDeviceResponseReceiver, intentFilter3);
    }

    private void unRegisterReceiver() {
        this.mContext.unregisterReceiver(this.mReceiver);
        this.mContext.unregisterReceiver(this.mUnpairReceiver);
        this.mContext.unregisterReceiver(this.mDeviceResponseReceiver);
    }
}
