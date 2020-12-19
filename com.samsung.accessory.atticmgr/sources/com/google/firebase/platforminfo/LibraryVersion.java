package com.google.firebase.platforminfo;

import javax.annotation.Nonnull;

/* access modifiers changed from: package-private */
/* compiled from: com.google.firebase:firebase-common@@17.1.0 */
public abstract class LibraryVersion {
    @Nonnull
    public abstract String getLibraryName();

    @Nonnull
    public abstract String getVersion();

    LibraryVersion() {
    }

    static LibraryVersion create(String str, String str2) {
        return new AutoValue_LibraryVersion(str, str2);
    }
}
