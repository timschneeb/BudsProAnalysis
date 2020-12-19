package com.samsung.sht.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.samsung.sht.log.ShtLog;
import java.util.ArrayList;
import java.util.Arrays;

public class RelativeQuat {
    private static final int BUFFSIZE = 10;
    private static final long INITIAL_ALIGN_DURATION_MS = 500;
    private static final int MSG_ALIGN = 3;
    private static final int MSG_START = 1;
    private static final int MSG_STOP = 2;
    private static final int MSG_UPDATE_HEADSET_QUAT = 4;
    private static final int MSG_UPDATE_MOBILE_QUAT = 5;
    private static final int MSG_UPDATE_WEAR_QUAT = 6;
    private static final int SAMPLING_RATE_US = 10000;
    private static final String TAG = "RelativeQuat";
    private Callback callback;
    private MyHandler handler;
    private boolean isAlignCalled;
    private float[] lastHeadsetQuat = null;
    private long lastHeadsetQuatTs = -1;
    private float[] lastWearAttitude = null;
    private double mLastCos = 2.0d;
    private float mLastOutputPitch = 0.0f;
    private float mLastOutputRoll = 0.0f;
    private float mLastOutputYaw = 0.0f;
    private float mLastRelQYaw = 400.0f;
    private double mLastSin = 2.0d;
    private float mMobileQuatVar;
    private ArrayList<Float> mMoblieQuatHistory = new ArrayList<>();
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        /* class com.samsung.sht.sensor.RelativeQuat.AnonymousClass1 */

        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == 15) {
                RelativeQuat.this.updateMobileQuat(new float[]{sensorEvent.values[3], sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]});
            }
        }
    };
    private SensorManager sensorManager;
    private long startTs = -1;

    public interface Callback {
        void onHeadsetGrvNotUpdated();

        void onRelativeEulerUpdated(float f, float f2, float f3);
    }

    private native void getRelativeQuat(float[] fArr);

    private native void initialize();

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private native void setQuatHead2Buds(float[] fArr);

    private native void update(float[] fArr, float[] fArr2);

    static {
        System.loadLibrary(TAG);
    }

    public RelativeQuat(Context context, Callback callback2, Looper looper) {
        this.sensorManager = (SensorManager) context.getSystemService("sensor");
        this.callback = callback2;
        this.handler = new MyHandler(looper);
    }

    public void start() {
        this.handler.sendEmptyMessage(1);
    }

    public void stop() {
        this.handler.sendEmptyMessage(2);
    }

    public void align() {
        this.handler.sendEmptyMessage(3);
    }

    public void updateWearAttitude(float[] fArr) {
        this.handler.obtainMessage(6, fArr).sendToTarget();
    }

    public void updateHeadsetQuat(float[] fArr) {
        this.handler.removeMessages(4);
        MyHandler myHandler = this.handler;
        myHandler.sendMessage(myHandler.obtainMessage(4, fArr));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateMobileQuat(float[] fArr) {
        MyHandler myHandler = this.handler;
        myHandler.sendMessage(myHandler.obtainMessage(5, fArr));
    }

    private float getVariance(ArrayList<Float> arrayList) {
        int size = arrayList.size();
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i = 0; i < size; i++) {
            f2 += arrayList.get(i).floatValue();
        }
        float f3 = f2 / ((float) size);
        for (int i2 = 0; i2 < size; i2++) {
            f += (arrayList.get(i2).floatValue() - f3) * (arrayList.get(i2).floatValue() - f3);
        }
        return f / ((float) (size - 1));
    }

    private void alignIfMobileMoves(float[] fArr) {
        this.mMoblieQuatHistory.add(Float.valueOf(OrientationUtil.convertQuat2Euler(fArr)[2]));
        if (this.mMoblieQuatHistory.size() > 10) {
            this.mMoblieQuatHistory.remove(0);
            this.mMobileQuatVar = getVariance(this.mMoblieQuatHistory);
        }
        if (this.mMobileQuatVar > 1.0f) {
            handleAlign();
            this.isAlignCalled = true;
            return;
        }
        this.isAlignCalled = false;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleStart() {
        Log.d(TAG, "Start requested");
        initialize();
        float[] fArr = this.lastWearAttitude;
        if (fArr != null) {
            setQuatHead2Buds(fArr);
        }
        this.mMoblieQuatHistory.clear();
        this.mMobileQuatVar = 0.0f;
        this.isAlignCalled = false;
        this.mLastRelQYaw = 400.0f;
        this.mLastOutputYaw = 0.0f;
        this.mLastOutputPitch = 0.0f;
        this.mLastOutputRoll = 0.0f;
        this.mLastSin = 2.0d;
        this.mLastCos = 2.0d;
        Sensor defaultSensor = this.sensorManager.getDefaultSensor(15);
        if (defaultSensor != null) {
            this.sensorManager.registerListener(this.sensorEventListener, defaultSensor, SAMPLING_RATE_US);
        } else {
            ShtLog.e("RelQuat : This device doesn't support GRV");
        }
        this.startTs = System.currentTimeMillis();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleStop() {
        this.sensorManager.unregisterListener(this.sensorEventListener);
        this.lastHeadsetQuat = null;
        this.startTs = -1;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleAlign() {
        this.mLastOutputYaw = 0.0f;
        this.mLastOutputPitch = 0.0f;
        this.mLastOutputRoll = 0.0f;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleHeadsetQuat(float[] fArr) {
        this.lastHeadsetQuat = Arrays.copyOf(fArr, 4);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleMobileQuat(float[] fArr) {
        float f;
        update(this.lastHeadsetQuat, fArr);
        float[] fArr2 = new float[4];
        getRelativeQuat(fArr2);
        float[] convertQuat2Euler = OrientationUtil.convertQuat2Euler(fArr2);
        float f2 = this.mLastRelQYaw;
        if (f2 <= 360.0f) {
            f = convertQuat2Euler[2] - f2;
            if (f < -180.0f) {
                f += 360.0f;
            } else if (f > 180.0f) {
                f -= 360.0f;
            }
        } else {
            f = 0.0f;
        }
        this.mLastRelQYaw = convertQuat2Euler[2];
        this.mLastOutputPitch = convertQuat2Euler[1];
        this.mLastOutputRoll = convertQuat2Euler[0];
        this.mLastOutputYaw += f;
        float f3 = this.mLastOutputYaw;
        if (f3 < -180.0f) {
            this.mLastOutputYaw = f3 + 360.0f;
        } else if (f3 > 180.0f) {
            this.mLastOutputYaw = f3 - 360.0f;
        }
        if (System.currentTimeMillis() - this.startTs < INITIAL_ALIGN_DURATION_MS) {
            handleAlign();
        }
        alignIfMobileMoves(fArr);
        double d = this.mLastSin;
        if (d > 1.0d || this.mLastCos > 1.0d) {
            this.mLastSin = Math.sin(Math.toRadians((double) this.mLastOutputYaw));
            this.mLastCos = Math.cos(Math.toRadians((double) this.mLastOutputYaw));
        } else {
            this.mLastSin = (d * 0.5d) + (Math.sin(Math.toRadians((double) this.mLastOutputYaw)) * 0.5d);
            this.mLastCos = (this.mLastCos * 0.5d) + (Math.cos(Math.toRadians((double) this.mLastOutputYaw)) * 0.5d);
        }
        if (this.callback != null || this.isAlignCalled) {
            this.callback.onRelativeEulerUpdated((float) Math.toDegrees(Math.atan2(this.mLastSin, this.mLastCos)), -this.mLastOutputPitch, this.mLastOutputRoll);
        }
    }

    /* access modifiers changed from: private */
    public class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                RelativeQuat.this.handleStart();
            } else if (message.what == 2) {
                RelativeQuat.this.handleStop();
            } else if (message.what == 3) {
                RelativeQuat.this.handleAlign();
            } else if (message.what == 4) {
                RelativeQuat.this.handleHeadsetQuat((float[]) message.obj);
            } else if (message.what == 5) {
                if (RelativeQuat.this.lastHeadsetQuat != null) {
                    RelativeQuat.this.handleMobileQuat((float[]) message.obj);
                }
            } else if (message.what == 6) {
                RelativeQuat.this.lastWearAttitude = Arrays.copyOf((float[]) message.obj, 4);
                RelativeQuat relativeQuat = RelativeQuat.this;
                relativeQuat.setQuatHead2Buds(relativeQuat.lastWearAttitude);
            }
        }
    }
}
