package androidx.appcompat.app;

import android.view.View;
import android.widget.AdapterView;
import androidx.appcompat.app.AlertController;

class i implements AdapterView.OnItemClickListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ AlertController f183a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ AlertController.a f184b;

    i(AlertController.a aVar, AlertController alertController) {
        this.f184b = aVar;
        this.f183a = alertController;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.f184b.x.onClick(this.f183a.f106b, i);
        if (!this.f184b.H) {
            this.f183a.f106b.dismiss();
        }
    }
}
