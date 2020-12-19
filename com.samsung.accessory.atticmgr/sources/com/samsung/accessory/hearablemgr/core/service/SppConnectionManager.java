package com.samsung.accessory.hearablemgr.core.service;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.work.WorkRequest;
import com.samsung.accessory.hearablemgr.common.util.BluetoothUtil;
import com.samsung.accessory.hearablemgr.common.util.ByteUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.common.util.WorkerHandler;
import com.samsung.accessory.hearablemgr.common.util.WorkerTask;
import com.samsung.accessory.hearablemgr.core.service.message.Msg;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.UUID;
import seccompat.android.util.Log;

public class SppConnectionManager {
    private static final UUID SPP_UUID = UUID.fromString("00001102-0000-1000-8000-00805f9b34fd");
    private static final UUID STANDARD_SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String TAG = "Attic_SppConnectionManager";
    private Callback mCallback;
    private BluetoothDevice mDevice;
    private Handler mMainHandler;
    private InputStream mRecvStream;
    private OutputStream mSendStream;
    private BluetoothSocket mSocket;
    private int mState = 0;
    private WorkerHandler mWorkerHandler;

    public interface Callback {
        void onConnectionStateChanged(BluetoothDevice bluetoothDevice, int i);

        void onMessage(BluetoothDevice bluetoothDevice, Msg msg);
    }

    public SppConnectionManager(Callback callback) {
        Log.d(TAG, "SppConnectionManager()");
        this.mMainHandler = new CallbackHandler(this);
        this.mCallback = callback;
        this.mWorkerHandler = WorkerHandler.createWorkerHandler("spp_worker@" + this);
        this.mWorkerHandler.setMessageHandler(new Handler.Callback() {
            /* class com.samsung.accessory.hearablemgr.core.service.SppConnectionManager.AnonymousClass1 */

            public boolean handleMessage(Message message) {
                SppConnectionManager.this.onSendBuffer((byte[]) message.obj);
                return true;
            }
        });
    }

    public synchronized void destroy() {
        Log.d(TAG, "destroy()");
        this.mWorkerHandler.quit();
        this.mMainHandler.removeCallbacksAndMessages(null);
    }

    public synchronized void connect(final BluetoothDevice bluetoothDevice) {
        Log.d("CoreService", "connect() : " + BluetoothUtil.privateAddress(bluetoothDevice.getAddress()));
        Log.d(TAG, "connect() : " + BluetoothUtil.privateAddress(bluetoothDevice.getAddress()));
        this.mWorkerHandler.post(new WorkerTask() {
            /* class com.samsung.accessory.hearablemgr.core.service.SppConnectionManager.AnonymousClass1TaskConnect */

            @Override // com.samsung.accessory.hearablemgr.common.util.WorkerTask
            public void execute() {
                String str = this.TAG;
                Log.d(str, "connect() : " + BluetoothUtil.privateAddress(bluetoothDevice.getAddress()));
                if (BluetoothUtil.isConnecting(SppConnectionManager.this.mState)) {
                    if (bluetoothDevice.equals(SppConnectionManager.this.mDevice)) {
                        Log.w(this.TAG, "connect() : Already connecting");
                        return;
                    } else {
                        Log.e(this.TAG, "connect() : Force disconnect");
                        SppConnectionManager.this.close();
                    }
                }
                try {
                    SppConnectionManager.this.mDevice = bluetoothDevice;
                    SppConnectionManager.this.setState(1);
                    Log.d(this.TAG, "connect() : connectSocket()");
                    SppConnectionManager.this.mSocket = SppConnectionManager.this.mDevice.createInsecureRfcommSocketToServiceRecord(SppConnectionManager.STANDARD_SPP_UUID);
                    Log.d(this.TAG, "connect() : createInsecureRfcommSocketToServiceRecord()_end");
                    SppConnectionManager.connectSocketWithTimeout(SppConnectionManager.this.mSocket);
                    Log.d(this.TAG, "connect() : connectSocket()_end");
                    SppConnectionManager.this.mRecvStream = SppConnectionManager.this.mSocket.getInputStream();
                    SppConnectionManager.this.mSendStream = SppConnectionManager.this.mSocket.getOutputStream();
                    SppConnectionManager.this.setState(2);
                    new SppRecvThread().start();
                    Log.d(this.TAG, "connect() : done");
                } catch (Exception e) {
                    e.printStackTrace();
                    String str2 = this.TAG;
                    Log.e(str2, "connect() : Exception : " + e);
                    SppConnectionManager.this.close();
                }
            }
        });
    }

    public synchronized void disconnect() {
        Log.d(TAG, "disconnect()");
        this.mWorkerHandler.post(new WorkerTask() {
            /* class com.samsung.accessory.hearablemgr.core.service.SppConnectionManager.AnonymousClass1TaskClose */

            @Override // com.samsung.accessory.hearablemgr.common.util.WorkerTask
            public void execute() {
                SppConnectionManager.this.close();
            }
        });
    }

    public synchronized void sendBuffer(byte[] bArr) {
        if (this.mWorkerHandler != null) {
            this.mWorkerHandler.obtainMessage(0, bArr).sendToTarget();
        } else {
            Log.e(TAG, "sendBuffer() : mWorkerHandler == null !!!");
        }
    }

    public synchronized void sendMessage(Msg msg) {
        sendBuffer(msg.toByteArray());
    }

    public boolean isConnected() {
        Log.w(TAG, "isConnected() : mState=" + this.mState);
        return this.mState == 2;
    }

    public int getConnectionState() {
        Log.w(TAG, "getConnectionState() : mState=" + this.mState);
        return this.mState;
    }

    public BluetoothDevice getDevice() {
        return this.mDevice;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private synchronized void setState(final int i) {
        Log.e(TAG, "++ setState() " + this.mState + " -> " + i);
        this.mState = i;
        BudsLogManager.sendLog(2, BluetoothUtil.stateToString(this.mState));
        final BluetoothDevice bluetoothDevice = this.mDevice;
        this.mMainHandler.post(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.service.SppConnectionManager.AnonymousClass2 */

            public void run() {
                SppConnectionManager.this.mCallback.onConnectionStateChanged(bluetoothDevice, i);
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private synchronized void close() {
        Log.d(TAG, "close() : mState=" + this.mState);
        if (this.mState == 0) {
            Log.w(TAG, "close() : mState == BluetoothProfile.STATE_DISCONNECTED");
            return;
        }
        Util.safeClose(this.mSendStream);
        Util.safeClose(this.mRecvStream);
        Util.safeClose(this.mSocket);
        setState(3);
        setState(0);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private synchronized void onSendBuffer(byte[] bArr) {
        try {
            if (this.mSendStream == null) {
                Log.e(TAG, "onSendBuffer() : mSendStream == null !!!");
            } else {
                this.mSendStream.write(bArr);
                BudsLogManager.sendLog(0, bArr);
                Log.v(TAG, "SENT-- : " + ByteUtil.toLogString(bArr));
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "onSendBuffer() : IOException !!!");
        }
        return;
    }

    class SppRecvThread extends Thread {
        private static final String TAG = "Attic_SppRecvThread";
        private final int BUFFER_SIZE = 2048;
        private final byte[] mBufRead = new byte[2048];
        private final LinkedList<Byte> mRecvBuffer = new LinkedList<>();

        SppRecvThread() {
        }

        public void run() {
            Log.d(TAG, "run()");
            while (readRecvStream()) {
                parseFrameBuffer();
            }
            SppConnectionManager.this.close();
            Log.d(TAG, "run()_finish");
        }

        private boolean readRecvStream() {
            try {
                int read = SppConnectionManager.this.mRecvStream.read(this.mBufRead);
                byte[] bArr = new byte[read];
                for (int i = 0; i < read; i++) {
                    this.mRecvBuffer.offer(Byte.valueOf(this.mBufRead[i]));
                    bArr[i] = this.mBufRead[i];
                }
                BudsLogManager.sendLog(1, bArr);
                Log.v(TAG, "RECV++ : " + ByteUtil.toLogString(bArr));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "SPP connection was lost");
                return false;
            }
        }

        private void parseFrameBuffer() {
            int payloadLength;
            while (true) {
                if ((this.mRecvBuffer.peek() == null || this.mRecvBuffer.peek().byteValue() != -3) && this.mRecvBuffer.peek() != null) {
                    Log.e(TAG, "lost=" + String.format(ByteUtil.HEX_FORMAT, this.mRecvBuffer.poll()));
                } else if (this.mRecvBuffer.size() >= 3 && this.mRecvBuffer.size() >= (payloadLength = Msg.getPayloadLength((ByteUtil.toU8(this.mRecvBuffer.get(2).byteValue()) << 8) + ByteUtil.toU8(this.mRecvBuffer.get(1).byteValue())) + 3 + 1)) {
                    if (this.mRecvBuffer.get(payloadLength - 1).byteValue() == -35) {
                        byte[] bArr = new byte[payloadLength];
                        for (int i = 0; i < payloadLength; i++) {
                            bArr[i] = this.mRecvBuffer.poll().byteValue();
                        }
                        Msg createMsg = Msg.createMsg(bArr);
                        if (createMsg != null) {
                            SppConnectionManager.this.mMainHandler.obtainMessage(0, createMsg).sendToTarget();
                        }
                    } else {
                        Log.e(TAG, "lost2=" + String.format(ByteUtil.HEX_FORMAT, this.mRecvBuffer.poll()));
                    }
                } else {
                    return;
                }
            }
        }
    }

    static class CallbackHandler extends Handler {
        private final WeakReference<SppConnectionManager> mWeakSppConnection;

        CallbackHandler(SppConnectionManager sppConnectionManager) {
            super(Looper.getMainLooper());
            this.mWeakSppConnection = new WeakReference<>(sppConnectionManager);
        }

        public void handleMessage(Message message) {
            SppConnectionManager sppConnectionManager = this.mWeakSppConnection.get();
            Log.v(SppConnectionManager.TAG, "CallbackHandler() : handleMessage()");
            if (sppConnectionManager != null) {
                sppConnectionManager.mCallback.onMessage(sppConnectionManager.getDevice(), (Msg) message.obj);
            } else {
                Log.w(SppConnectionManager.TAG, "CallbackHandler() : ref == null");
            }
        }
    }

    /* access modifiers changed from: private */
    public static void connectSocketWithTimeout(final BluetoothSocket bluetoothSocket) throws IOException {
        Log.d(TAG, "connectSocketWithTimeout() : " + bluetoothSocket);
        final AnonymousClass1WrapperBoolean r0 = new Object() {
            /* class com.samsung.accessory.hearablemgr.core.service.SppConnectionManager.AnonymousClass1WrapperBoolean */
            boolean value = false;
        };
        Thread thread = new Thread(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.service.SppConnectionManager.AnonymousClass3 */

            public void run() {
                Log.d(SppConnectionManager.TAG, "timeOutThread : run()");
                try {
                    Thread.sleep(WorkRequest.MIN_BACKOFF_MILLIS);
                    if (!r0.value) {
                        Log.e(SppConnectionManager.TAG, "timeOutThread : run() : TIME_OUT !!! " + bluetoothSocket);
                        Util.safeClose(bluetoothSocket);
                    }
                    Log.d(SppConnectionManager.TAG, "timeOutThread : run()_end");
                } catch (InterruptedException unused) {
                    Log.d(SppConnectionManager.TAG, "timeOutThread : run() : Interrupted");
                }
            }
        });
        thread.start();
        try {
            bluetoothSocket.connect();
            r0.value = true;
            thread.interrupt();
            Log.d(TAG, "connectSocketWithTimeout()_end");
        } catch (IOException e) {
            throw e;
        } catch (Throwable th) {
            r0.value = true;
            throw th;
        }
    }
}
