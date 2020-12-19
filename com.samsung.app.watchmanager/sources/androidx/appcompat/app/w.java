package androidx.appcompat.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatDelegateImpl;

class w extends BroadcastReceiver {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ AppCompatDelegateImpl.d f201a;

    w(AppCompatDelegateImpl.d dVar) {
        this.f201a = dVar;
    }

    public void onReceive(Context context, Intent intent) {
        this.f201a.b();
    }
}
