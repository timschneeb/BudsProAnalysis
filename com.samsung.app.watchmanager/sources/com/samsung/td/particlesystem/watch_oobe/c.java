package com.samsung.td.particlesystem.watch_oobe;

import android.os.Handler;
import android.os.Message;

/* access modifiers changed from: package-private */
public class c implements Handler.Callback {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ParticleView_Watch_OOBE_Base f1931a;

    c(ParticleView_Watch_OOBE_Base particleView_Watch_OOBE_Base) {
        this.f1931a = particleView_Watch_OOBE_Base;
    }

    public boolean handleMessage(Message message) {
        ParticleView_Watch_OOBE_Base particleView_Watch_OOBE_Base = this.f1931a;
        int i = message.what;
        particleView_Watch_OOBE_Base.p = i;
        particleView_Watch_OOBE_Base.b(i);
        this.f1931a.invalidate();
        ParticleView_Watch_OOBE_Base particleView_Watch_OOBE_Base2 = this.f1931a;
        if (particleView_Watch_OOBE_Base2.p == 0) {
            particleView_Watch_OOBE_Base2.m.cancel();
            return true;
        } else if (particleView_Watch_OOBE_Base2.m.isRunning()) {
            return true;
        } else {
            this.f1931a.m.start();
            return true;
        }
    }
}
