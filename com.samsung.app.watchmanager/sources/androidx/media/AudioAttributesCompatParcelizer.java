package androidx.media;

import androidx.versionedparcelable.b;

public final class AudioAttributesCompatParcelizer {
    public static AudioAttributesCompat read(b bVar) {
        AudioAttributesCompat audioAttributesCompat = new AudioAttributesCompat();
        audioAttributesCompat.f862c = (AbstractC0094a) bVar.a(audioAttributesCompat.f862c, 1);
        return audioAttributesCompat;
    }

    public static void write(AudioAttributesCompat audioAttributesCompat, b bVar) {
        bVar.a(false, false);
        bVar.b(audioAttributesCompat.f862c, 1);
    }
}
