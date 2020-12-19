package com.google.firebase.platforminfo;

import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;

/* access modifiers changed from: package-private */
/* compiled from: com.google.firebase:firebase-common@@17.1.0 */
public final /* synthetic */ class DefaultUserAgentPublisher$$Lambda$1 implements ComponentFactory {
    private static final DefaultUserAgentPublisher$$Lambda$1 instance = new DefaultUserAgentPublisher$$Lambda$1();

    private DefaultUserAgentPublisher$$Lambda$1() {
    }

    public static ComponentFactory lambdaFactory$() {
        return instance;
    }

    @Override // com.google.firebase.components.ComponentFactory
    public Object create(ComponentContainer componentContainer) {
        return DefaultUserAgentPublisher.lambda$component$0(componentContainer);
    }
}
