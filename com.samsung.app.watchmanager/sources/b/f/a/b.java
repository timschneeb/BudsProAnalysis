package b.f.a;

import android.database.Cursor;
import android.widget.Filter;

class b extends Filter {

    /* renamed from: a  reason: collision with root package name */
    a f1454a;

    interface a {
        Cursor a();

        Cursor a(CharSequence charSequence);

        void a(Cursor cursor);

        CharSequence convertToString(Cursor cursor);
    }

    b(a aVar) {
        this.f1454a = aVar;
    }

    public CharSequence convertResultToString(Object obj) {
        return this.f1454a.convertToString((Cursor) obj);
    }

    /* access modifiers changed from: protected */
    public Filter.FilterResults performFiltering(CharSequence charSequence) {
        Cursor a2 = this.f1454a.a(charSequence);
        Filter.FilterResults filterResults = new Filter.FilterResults();
        if (a2 != null) {
            filterResults.count = a2.getCount();
        } else {
            filterResults.count = 0;
            a2 = null;
        }
        filterResults.values = a2;
        return filterResults;
    }

    /* access modifiers changed from: protected */
    public void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
        Cursor a2 = this.f1454a.a();
        Object obj = filterResults.values;
        if (obj != null && obj != a2) {
            this.f1454a.a((Cursor) obj);
        }
    }
}
