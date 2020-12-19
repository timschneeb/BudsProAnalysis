package com.accessorydm.dmstarter;

import java.util.concurrent.ThreadFactory;

/* renamed from: com.accessorydm.dmstarter.-$$Lambda$XDMInitExecutor$16VicOkiceFMYSJCnMqJi8K5hAQ  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$XDMInitExecutor$16VicOkiceFMYSJCnMqJi8K5hAQ implements ThreadFactory {
    public static final /* synthetic */ $$Lambda$XDMInitExecutor$16VicOkiceFMYSJCnMqJi8K5hAQ INSTANCE = new $$Lambda$XDMInitExecutor$16VicOkiceFMYSJCnMqJi8K5hAQ();

    private /* synthetic */ $$Lambda$XDMInitExecutor$16VicOkiceFMYSJCnMqJi8K5hAQ() {
    }

    public final Thread newThread(Runnable runnable) {
        return XDMInitExecutor.lambda$new$0(runnable);
    }
}
