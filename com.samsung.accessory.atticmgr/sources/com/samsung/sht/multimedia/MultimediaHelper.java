package com.samsung.sht.multimedia;

import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.samsung.android.media.SemMediaResourceHelper;
import com.samsung.sht.log.ShtLog;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class MultimediaHelper {
    private static final int MSG_AUDIO_PLAYBACK_INFO_CHANGED = 5;
    private static final int MSG_MEDIA_CODEC_INFO_CHANGED = 4;
    private static final int MSG_MEDIA_RESOURCE_INFO_CHANGED = 3;
    private static final int MSG_START = 1;
    private static final int MSG_STOP = 2;
    private static final String TAG = MultimediaHelper.class.getSimpleName();
    private boolean isDecoderAdded = false;
    private boolean isRunning = false;
    private AudioManager mAudioManager = null;
    private AudioManager.AudioPlaybackCallback mAudioPlaybackCallback = new AudioManager.AudioPlaybackCallback() {
        /* class com.samsung.sht.multimedia.MultimediaHelper.AnonymousClass3 */

        @Override // android.media.AudioManager.AudioPlaybackCallback
        public void onPlaybackConfigChanged(List<AudioPlaybackConfiguration> list) {
            HashSet hashSet = new HashSet();
            for (AudioPlaybackConfiguration audioPlaybackConfiguration : list) {
                if (audioPlaybackConfiguration.semGetPlayerState() == 2) {
                    hashSet.add(Integer.valueOf(audioPlaybackConfiguration.semGetClientPid()));
                }
            }
            if (MultimediaHelper.this.mHandler != null) {
                MultimediaHelper.this.mHandler.obtainMessage(5, hashSet).sendToTarget();
            }
        }
    };
    private HashSet<Integer> mAudioPlayingPIDs = new HashSet<>();
    private Callback mCallback = null;
    private HashSet<Integer> mCodecRunningPIDs = new HashSet<>();
    private SemMediaResourceHelper.CodecStateChangedListener mCodecStateChangedListener = new SemMediaResourceHelper.CodecStateChangedListener() {
        /* class com.samsung.sht.multimedia.MultimediaHelper.AnonymousClass2 */

        public void onStateChanged(ArrayList<SemMediaResourceHelper.MediaResourceInfo> arrayList) {
            if (!MultimediaHelper.this.isRunning) {
                ShtLog.e(MultimediaHelper.TAG, "(Codec)updateStateChanged called, but monitor is not running");
                return;
            }
            HashSet hashSet = new HashSet();
            ShtLog.i("CodecStateChangedListener.onStateChanged");
            Iterator<SemMediaResourceHelper.MediaResourceInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                SemMediaResourceHelper.MediaResourceInfo next = it.next();
                ShtLog.i("--status:" + next.isEncoder() + "," + next.getCodecState());
                if (!next.isEncoder() && next.getCodecState() == 1) {
                    hashSet.add(Integer.valueOf(next.getPid()));
                }
            }
            if (MultimediaHelper.this.mHandler != null) {
                MultimediaHelper.this.mHandler.obtainMessage(4, hashSet).sendToTarget();
            }
        }
    };
    private Handler mHandler = null;
    private SemMediaResourceHelper.ResourceInfoChangedListener mMediaResourceChangedListener = new SemMediaResourceHelper.ResourceInfoChangedListener() {
        /* class com.samsung.sht.multimedia.MultimediaHelper.AnonymousClass1 */

        public void onError(SemMediaResourceHelper semMediaResourceHelper) {
        }

        public void onAdd(ArrayList<SemMediaResourceHelper.MediaResourceInfo> arrayList) {
            updateVideoStatus(arrayList);
        }

        public void onRemove(ArrayList<SemMediaResourceHelper.MediaResourceInfo> arrayList) {
            updateVideoStatus(arrayList);
        }

        private void updateVideoStatus(ArrayList<SemMediaResourceHelper.MediaResourceInfo> arrayList) {
            if (!MultimediaHelper.this.isRunning) {
                ShtLog.e(MultimediaHelper.TAG, "updateVideoStatus called, but monitor is not running");
                return;
            }
            boolean z = false;
            ShtLog.i("updateVideoStatus");
            Iterator<SemMediaResourceHelper.MediaResourceInfo> it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                SemMediaResourceHelper.MediaResourceInfo next = it.next();
                ShtLog.i("--status:" + next.isEncoder());
                if (!next.isEncoder()) {
                    z = true;
                    break;
                }
            }
            if (MultimediaHelper.this.mHandler != null) {
                MultimediaHelper.this.mHandler.obtainMessage(3, new Boolean(z)).sendToTarget();
            }
        }
    };
    private SemMediaResourceHelper mMediaResourceHelper;

    public interface Callback {
        void onMediaStarted();

        void onMediaStopped();
    }

    public MultimediaHelper(Callback callback, AudioManager audioManager, Looper looper) {
        this.mCallback = callback;
        this.mAudioManager = audioManager;
        this.mMediaResourceHelper = SemMediaResourceHelper.createInstance(2, true);
        this.mHandler = new MyHandler(looper);
    }

    public void startMonitoring() {
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    public void stopMonitoring() {
        this.mHandler.obtainMessage(2).sendToTarget();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleStart() {
        if (this.isRunning) {
            ShtLog.e(TAG, "Start requested, but already running");
            return;
        }
        this.isDecoderAdded = false;
        this.mCodecRunningPIDs.clear();
        this.mAudioPlayingPIDs.clear();
        this.mMediaResourceHelper.setResourceInfoChangedListener(this.mMediaResourceChangedListener);
        this.mMediaResourceHelper.setCodecStateChangedListener(this.mCodecStateChangedListener);
        this.mAudioManager.registerAudioPlaybackCallback(this.mAudioPlaybackCallback, this.mHandler);
        this.isRunning = true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleStop() {
        if (!this.isRunning) {
            ShtLog.e(TAG, "Stop requested, but is not running");
            return;
        }
        this.isDecoderAdded = false;
        this.mCodecRunningPIDs.clear();
        this.mAudioPlayingPIDs.clear();
        this.mMediaResourceHelper.setResourceInfoChangedListener((SemMediaResourceHelper.ResourceInfoChangedListener) null);
        this.mMediaResourceHelper.setCodecStateChangedListener((SemMediaResourceHelper.CodecStateChangedListener) null);
        this.mAudioManager.unregisterAudioPlaybackCallback(this.mAudioPlaybackCallback);
        this.isRunning = false;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleDecoderStatusUpdated(boolean z) {
        if (!this.isRunning) {
            ShtLog.e(TAG, "decoder status updated, but not running");
            return;
        }
        String str = TAG;
        ShtLog.d(str, "handleDecoderStatusUpdated :" + z);
        this.isDecoderAdded = z;
        if (!this.isDecoderAdded) {
            this.mCodecRunningPIDs.clear();
        }
        updateMediaRunning();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleCodecStatusUpdated(HashSet<Integer> hashSet) {
        if (!this.isRunning) {
            ShtLog.e(TAG, "codec status updated, but monitor is not running");
            return;
        }
        ShtLog.d(TAG, "handleCodecStatusUpdated");
        this.mCodecRunningPIDs.clear();
        this.mCodecRunningPIDs.addAll(hashSet);
        updateMediaRunning();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleAudioPlaybackUpdated(HashSet<Integer> hashSet) {
        if (!this.isRunning) {
            ShtLog.e(TAG, "audio playback status updated, but monitor is not running");
            return;
        }
        ShtLog.d(TAG, "handleAudioPlaybackStatusUpdated");
        this.mAudioPlayingPIDs.clear();
        this.mAudioPlayingPIDs.addAll(hashSet);
        updateMediaRunning();
    }

    private void updateMediaRunning() {
        if (this.mCallback == null) {
            return;
        }
        if (!this.isDecoderAdded || !checkPIDMatches()) {
            this.mCallback.onMediaStopped();
        } else {
            this.mCallback.onMediaStarted();
        }
    }

    private boolean checkPIDMatches() {
        if (this.mAudioPlayingPIDs.size() <= 0) {
            ShtLog.e(TAG, "Audio Playback list empty");
            return false;
        } else if (this.mCodecRunningPIDs.size() <= 0) {
            ShtLog.e(TAG, "Codec running list empty");
            return false;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Audio PID list :");
            Iterator<Integer> it = this.mAudioPlayingPIDs.iterator();
            while (it.hasNext()) {
                sb.append(it.next());
                sb.append(",");
            }
            sb.append("Codec running PID : ");
            Iterator<Integer> it2 = this.mCodecRunningPIDs.iterator();
            while (it2.hasNext()) {
                sb.append(it2.next());
                sb.append(",");
            }
            ShtLog.d(TAG, sb.toString());
            Iterator<Integer> it3 = this.mAudioPlayingPIDs.iterator();
            while (it3.hasNext()) {
                Integer next = it3.next();
                if (this.mCodecRunningPIDs.contains(next)) {
                    return true;
                }
                String str = TAG;
                ShtLog.e(str, "No Match:" + next);
            }
            return false;
        }
    }

    private class MyHandler extends Handler {
        MyHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == 1) {
                MultimediaHelper.this.handleStart();
            } else if (i == 2) {
                MultimediaHelper.this.handleStop();
            } else if (i == 3) {
                MultimediaHelper.this.handleDecoderStatusUpdated(((Boolean) message.obj).booleanValue());
            } else if (i == 4) {
                MultimediaHelper.this.handleCodecStatusUpdated((HashSet) message.obj);
            } else if (i != 5) {
                ShtLog.e("Invalid msg");
            } else {
                MultimediaHelper.this.handleAudioPlaybackUpdated((HashSet) message.obj);
            }
        }
    }
}
