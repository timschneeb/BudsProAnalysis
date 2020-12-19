package androidx.appcompat.app;

import android.view.View;
import android.widget.AdapterView;
import androidx.appcompat.app.AlertController;

class j implements AdapterView.OnItemClickListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ AlertController.RecycleListView f185a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ AlertController f186b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ AlertController.a f187c;

    j(AlertController.a aVar, AlertController.RecycleListView recycleListView, AlertController alertController) {
        this.f187c = aVar;
        this.f185a = recycleListView;
        this.f186b = alertController;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        boolean[] zArr = this.f187c.F;
        if (zArr != null) {
            zArr[i] = this.f185a.isItemChecked(i);
        }
        this.f187c.J.onClick(this.f186b.f106b, i, this.f185a.isItemChecked(i));
    }
}
