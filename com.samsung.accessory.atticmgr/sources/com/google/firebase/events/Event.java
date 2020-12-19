package com.google.firebase.events;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.firebase:firebase-common@@17.1.0 */
public class Event<T> {
    private final T payload;
    private final Class<T> type;

    public Event(Class<T> cls, T t) {
        this.type = (Class) Preconditions.checkNotNull(cls);
        this.payload = (T) Preconditions.checkNotNull(t);
    }

    public Class<T> getType() {
        return this.type;
    }

    public T getPayload() {
        return this.payload;
    }

    public String toString() {
        return String.format("Event{type: %s, payload: %s}", this.type, this.payload);
    }
}
