package com.samsung.sht.audio;

import android.content.Context;
import android.database.Cursor;
import android.media.audiofx.SemDolbyAudioEffect;
import android.net.Uri;
import com.samsung.sht.log.ShtLog;
import java.util.UUID;

public class DolbyAtmosHelperImpl implements DolbyAtmosHelper {
    private static final int AUDIO_SESSION = -3;
    private static final UUID DOLBY_ATMOS_UUID = SemDolbyAudioEffect.EFFECT_TYPE_DOLBY_AUDIO_PROCESSING;
    private static final String[] KEY_DOLBY = {"DOLBY_ATMOS_LEVEL"};
    private static final int PRIORITY = 0;
    private static final int PROFILE_AUTO = 0;
    private static final int PROFILE_MOVIE = 1;
    private static final int PROFILE_MUSIC = 2;
    private static final int PROFILE_OFF = 5;
    private static final int PROFILE_SPATIAL_AUDIO = 8;
    private static final int PROFILE_VOICE = 3;
    private Context mContext;
    private SemDolbyAudioEffect mDolby;
    private int profileFromDB;

    public DolbyAtmosHelperImpl(Context context) {
        this.mContext = context;
        createDolbyInstance();
    }

    @Override // com.samsung.sht.audio.DolbyAtmosHelper
    public void onMediaServerReboot(boolean z) {
        createDolbyInstance();
        if (z) {
            setDolbyProfile(8);
        } else {
            setDolbyProfile(this.profileFromDB);
        }
    }

    private void createDolbyInstance() {
        try {
            if (SemDolbyAudioEffect.isSupported(DOLBY_ATMOS_UUID)) {
                this.mDolby = new SemDolbyAudioEffect(DOLBY_ATMOS_UUID, 0, -3);
                ShtLog.v("Dolby ATMOS instance created");
                return;
            }
            this.mDolby = null;
            ShtLog.e("Dolby ATMOS not supported");
        } catch (Exception e) {
            this.mDolby = null;
            ShtLog.e("Exception) Couldn't create Dolby instance :" + e.getMessage());
        }
    }

    @Override // com.samsung.sht.audio.DolbyAtmosHelper
    public void setHeadTrackingEnabled(boolean z) {
        ShtLog.v("DolbyAtmosHelperImpl.setHeadTrackingEnabled:" + z);
        if (this.mDolby == null) {
            ShtLog.e("setHeadTrackingEnabled) Dolby instance null");
        } else if (z) {
            this.profileFromDB = getProfileFromDB();
            setDolbyProfile(8);
        } else {
            setDolbyProfile(this.profileFromDB);
        }
    }

    private void setDolbyProfile(int i) {
        SemDolbyAudioEffect semDolbyAudioEffect = this.mDolby;
        if (semDolbyAudioEffect == null) {
            ShtLog.e("setDolbyProfile() Dolby instance null");
            return;
        }
        try {
            semDolbyAudioEffect.setProfileEnabled(i != 5);
            this.mDolby.setProfile(i);
            ShtLog.v("setDolbyProfile) Profile updated to " + i);
        } catch (Exception e) {
            ShtLog.e("setDolbyProfile) Exception occured while updating Dolby profile :" + e.getMessage());
        }
    }

    private int getProfileFromDB() {
        Cursor query = this.mContext.getContentResolver().query(Uri.parse("content://com.sec.android.app.soundalive.compatibility.SAContentProvider"), KEY_DOLBY, null, null, null);
        query.moveToFirst();
        int parseInt = Integer.parseInt(query.getString(0));
        ShtLog.i("Read Dolby Profile From DB : " + parseInt);
        return parseInt;
    }
}
