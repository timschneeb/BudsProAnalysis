package com.google.firebase.platforminfo;

import com.google.firebase.components.Component;

/* compiled from: com.google.firebase:firebase-common@@17.1.0 */
public class LibraryVersionComponent {
    private LibraryVersionComponent() {
    }

    public static Component<?> create(String str, String str2) {
        return Component.intoSet(LibraryVersion.create(str, str2), LibraryVersion.class);
    }
}
