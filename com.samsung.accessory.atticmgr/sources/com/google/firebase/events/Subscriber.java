package com.google.firebase.events;

import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-common@@17.1.0 */
public interface Subscriber {
    <T> void subscribe(Class<T> cls, EventHandler<? super T> eventHandler);

    <T> void subscribe(Class<T> cls, Executor executor, EventHandler<? super T> eventHandler);

    <T> void unsubscribe(Class<T> cls, EventHandler<? super T> eventHandler);
}
