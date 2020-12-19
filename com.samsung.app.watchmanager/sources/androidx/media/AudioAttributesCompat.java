package androidx.media;

import android.util.SparseIntArray;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.versionedparcelable.d;

public class AudioAttributesCompat implements d {

    /* renamed from: a  reason: collision with root package name */
    private static final SparseIntArray f860a = new SparseIntArray();

    /* renamed from: b  reason: collision with root package name */
    private static final int[] f861b = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16};

    /* renamed from: c  reason: collision with root package name */
    AbstractC0094a f862c;

    static {
        f860a.put(5, 1);
        f860a.put(6, 2);
        f860a.put(7, 2);
        f860a.put(8, 1);
        f860a.put(9, 1);
        f860a.put(10, 1);
    }

    AudioAttributesCompat() {
    }

    static int a(boolean z, int i, int i2) {
        if ((i & 1) == 1) {
            return z ? 1 : 7;
        }
        if ((i & 4) == 4) {
            return z ? 0 : 6;
        }
        switch (i2) {
            case 0:
                if (z) {
                    return LinearLayoutManager.INVALID_OFFSET;
                }
                return 3;
            case 1:
            case 12:
            case 14:
            case 16:
                return 3;
            case 2:
                return 0;
            case 3:
                return z ? 0 : 8;
            case 4:
                return 4;
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
                return 5;
            case 6:
                return 2;
            case 11:
                return 10;
            case 13:
                return 1;
            case 15:
            default:
                if (!z) {
                    return 3;
                }
                throw new IllegalArgumentException("Unknown usage value " + i2 + " in audio attributes");
        }
    }

    static String a(int i) {
        switch (i) {
            case 0:
                return "USAGE_UNKNOWN";
            case 1:
                return "USAGE_MEDIA";
            case 2:
                return "USAGE_VOICE_COMMUNICATION";
            case 3:
                return "USAGE_VOICE_COMMUNICATION_SIGNALLING";
            case 4:
                return "USAGE_ALARM";
            case 5:
                return "USAGE_NOTIFICATION";
            case 6:
                return "USAGE_NOTIFICATION_RINGTONE";
            case 7:
                return "USAGE_NOTIFICATION_COMMUNICATION_REQUEST";
            case 8:
                return "USAGE_NOTIFICATION_COMMUNICATION_INSTANT";
            case 9:
                return "USAGE_NOTIFICATION_COMMUNICATION_DELAYED";
            case 10:
                return "USAGE_NOTIFICATION_EVENT";
            case 11:
                return "USAGE_ASSISTANCE_ACCESSIBILITY";
            case 12:
                return "USAGE_ASSISTANCE_NAVIGATION_GUIDANCE";
            case 13:
                return "USAGE_ASSISTANCE_SONIFICATION";
            case 14:
                return "USAGE_GAME";
            case 15:
            default:
                return "unknown usage " + i;
            case 16:
                return "USAGE_ASSISTANT";
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AudioAttributesCompat)) {
            return false;
        }
        AudioAttributesCompat audioAttributesCompat = (AudioAttributesCompat) obj;
        AbstractC0094a aVar = this.f862c;
        return aVar == null ? audioAttributesCompat.f862c == null : aVar.equals(audioAttributesCompat.f862c);
    }

    public int hashCode() {
        return this.f862c.hashCode();
    }

    public String toString() {
        return this.f862c.toString();
    }
}
