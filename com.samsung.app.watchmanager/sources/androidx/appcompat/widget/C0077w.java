package androidx.appcompat.widget;

import android.view.View;
import android.widget.AdapterView;
import androidx.appcompat.widget.AppCompatSpinner;

/* access modifiers changed from: package-private */
/* renamed from: androidx.appcompat.widget.w  reason: case insensitive filesystem */
public class C0077w implements AdapterView.OnItemClickListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ AppCompatSpinner f525a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ AppCompatSpinner.b f526b;

    C0077w(AppCompatSpinner.b bVar, AppCompatSpinner appCompatSpinner) {
        this.f526b = bVar;
        this.f525a = appCompatSpinner;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        AppCompatSpinner.this.setSelection(i);
        if (AppCompatSpinner.this.getOnItemClickListener() != null) {
            AppCompatSpinner.b bVar = this.f526b;
            AppCompatSpinner.this.performItemClick(view, i, bVar.L.getItemId(i));
        }
        this.f526b.dismiss();
    }
}
