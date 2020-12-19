package com.samsung.td.particlesystem.watch_oobe;

import android.app.ActivityManager;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

public class ParticleViewManager_Watch_OOBE extends FrameLayout {

    /* renamed from: a  reason: collision with root package name */
    final String f1915a = "Watch_OOBE";

    /* renamed from: b  reason: collision with root package name */
    ParticleView_Watch_OOBE_HighPerformance f1916b = null;

    public enum a {
        PERFORMANCE_LOW,
        PERFORMANCE_MEDIUM,
        PERFORMANCE_HIGH
    }

    public ParticleViewManager_Watch_OOBE(Context context) {
        super(context);
    }

    public ParticleViewManager_Watch_OOBE(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ParticleViewManager_Watch_OOBE(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: package-private */
    public long a(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(memoryInfo);
        return memoryInfo.totalMem / 1048576;
    }

    public void a(int i, int i2) {
        String str;
        Context context = getContext();
        a aVar = a(context) > 2500 ? a.PERFORMANCE_HIGH : a.PERFORMANCE_LOW;
        this.f1916b = new ParticleView_Watch_OOBE_HighPerformance(context);
        this.f1916b.b(i, i2);
        int i3 = a.f1929a[aVar.ordinal()];
        if (i3 == 2) {
            str = "Device = Medium performance";
        } else if (i3 != 3) {
            Log.i("Watch_OOBE", "Device = Low performance");
            this.f1916b.setPerformance(false);
            addView(this.f1916b);
        } else {
            str = "Device = High performance";
        }
        Log.i("Watch_OOBE", str);
        this.f1916b.setPerformance(true);
        addView(this.f1916b);
    }

    public ParticleView_Watch_OOBE_HighPerformance getParticleView() {
        return this.f1916b;
    }
}
