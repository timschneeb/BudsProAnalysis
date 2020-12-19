package com.sec.android.diagmonagent.log.provider.v1;

import android.os.Bundle;

public abstract class AbstractSlaveLogProvider extends AbstractLogProvider {
    @Override // com.sec.android.diagmonagent.log.provider.v1.AbstractLogProvider
    public boolean onCreate() {
        if (!super.onCreate()) {
            return false;
        }
        this.f1988a.putBundle("authorityList", Bundle.EMPTY);
        return true;
    }
}
