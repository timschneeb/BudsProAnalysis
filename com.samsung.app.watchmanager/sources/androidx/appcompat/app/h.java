package androidx.appcompat.app;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import androidx.appcompat.app.AlertController;

class h extends CursorAdapter {

    /* renamed from: a  reason: collision with root package name */
    private final int f179a;

    /* renamed from: b  reason: collision with root package name */
    private final int f180b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ AlertController.RecycleListView f181c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ AlertController f182d;
    final /* synthetic */ AlertController.a e;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    h(AlertController.a aVar, Context context, Cursor cursor, boolean z, AlertController.RecycleListView recycleListView, AlertController alertController) {
        super(context, cursor, z);
        this.e = aVar;
        this.f181c = recycleListView;
        this.f182d = alertController;
        Cursor cursor2 = getCursor();
        this.f179a = cursor2.getColumnIndexOrThrow(this.e.L);
        this.f180b = cursor2.getColumnIndexOrThrow(this.e.M);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        ((CheckedTextView) view.findViewById(16908308)).setText(cursor.getString(this.f179a));
        AlertController.RecycleListView recycleListView = this.f181c;
        int position = cursor.getPosition();
        boolean z = true;
        if (cursor.getInt(this.f180b) != 1) {
            z = false;
        }
        recycleListView.setItemChecked(position, z);
    }

    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.e.f112b.inflate(this.f182d.M, viewGroup, false);
    }
}
