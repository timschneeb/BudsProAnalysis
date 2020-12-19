package b.f.a;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import b.f.a.b;

public abstract class a extends BaseAdapter implements Filterable, b.a {

    /* renamed from: a  reason: collision with root package name */
    protected boolean f1448a;

    /* renamed from: b  reason: collision with root package name */
    protected boolean f1449b;

    /* renamed from: c  reason: collision with root package name */
    protected Cursor f1450c;

    /* renamed from: d  reason: collision with root package name */
    protected Context f1451d;
    protected int e;
    protected C0027a f;
    protected DataSetObserver g;
    protected b h;

    /* access modifiers changed from: private */
    /* renamed from: b.f.a.a$a  reason: collision with other inner class name */
    public class C0027a extends ContentObserver {
        C0027a() {
            super(new Handler());
        }

        public boolean deliverSelfNotifications() {
            return true;
        }

        public void onChange(boolean z) {
            a.this.b();
        }
    }

    /* access modifiers changed from: private */
    public class b extends DataSetObserver {
        b() {
        }

        public void onChanged() {
            a aVar = a.this;
            aVar.f1448a = true;
            aVar.notifyDataSetChanged();
        }

        public void onInvalidated() {
            a aVar = a.this;
            aVar.f1448a = false;
            aVar.notifyDataSetInvalidated();
        }
    }

    public a(Context context, Cursor cursor, boolean z) {
        a(context, cursor, z ? 1 : 2);
    }

    @Override // b.f.a.b.a
    public Cursor a() {
        return this.f1450c;
    }

    public abstract View a(Context context, Cursor cursor, ViewGroup viewGroup);

    /* access modifiers changed from: package-private */
    public void a(Context context, Cursor cursor, int i) {
        b bVar;
        boolean z = false;
        if ((i & 1) == 1) {
            i |= 2;
            this.f1449b = true;
        } else {
            this.f1449b = false;
        }
        if (cursor != null) {
            z = true;
        }
        this.f1450c = cursor;
        this.f1448a = z;
        this.f1451d = context;
        this.e = z ? cursor.getColumnIndexOrThrow("_id") : -1;
        if ((i & 2) == 2) {
            this.f = new C0027a();
            bVar = new b();
        } else {
            bVar = null;
            this.f = null;
        }
        this.g = bVar;
        if (z) {
            C0027a aVar = this.f;
            if (aVar != null) {
                cursor.registerContentObserver(aVar);
            }
            DataSetObserver dataSetObserver = this.g;
            if (dataSetObserver != null) {
                cursor.registerDataSetObserver(dataSetObserver);
            }
        }
    }

    @Override // b.f.a.b.a
    public void a(Cursor cursor) {
        Cursor b2 = b(cursor);
        if (b2 != null) {
            b2.close();
        }
    }

    public abstract void a(View view, Context context, Cursor cursor);

    public Cursor b(Cursor cursor) {
        Cursor cursor2 = this.f1450c;
        if (cursor == cursor2) {
            return null;
        }
        if (cursor2 != null) {
            C0027a aVar = this.f;
            if (aVar != null) {
                cursor2.unregisterContentObserver(aVar);
            }
            DataSetObserver dataSetObserver = this.g;
            if (dataSetObserver != null) {
                cursor2.unregisterDataSetObserver(dataSetObserver);
            }
        }
        this.f1450c = cursor;
        if (cursor != null) {
            C0027a aVar2 = this.f;
            if (aVar2 != null) {
                cursor.registerContentObserver(aVar2);
            }
            DataSetObserver dataSetObserver2 = this.g;
            if (dataSetObserver2 != null) {
                cursor.registerDataSetObserver(dataSetObserver2);
            }
            this.e = cursor.getColumnIndexOrThrow("_id");
            this.f1448a = true;
            notifyDataSetChanged();
        } else {
            this.e = -1;
            this.f1448a = false;
            notifyDataSetInvalidated();
        }
        return cursor2;
    }

    public abstract View b(Context context, Cursor cursor, ViewGroup viewGroup);

    /* access modifiers changed from: protected */
    public void b() {
        Cursor cursor;
        if (this.f1449b && (cursor = this.f1450c) != null && !cursor.isClosed()) {
            this.f1448a = this.f1450c.requery();
        }
    }

    @Override // b.f.a.b.a
    public abstract CharSequence convertToString(Cursor cursor);

    public int getCount() {
        Cursor cursor;
        if (!this.f1448a || (cursor = this.f1450c) == null) {
            return 0;
        }
        return cursor.getCount();
    }

    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        if (!this.f1448a) {
            return null;
        }
        this.f1450c.moveToPosition(i);
        if (view == null) {
            view = a(this.f1451d, this.f1450c, viewGroup);
        }
        a(view, this.f1451d, this.f1450c);
        return view;
    }

    public Filter getFilter() {
        if (this.h == null) {
            this.h = new b(this);
        }
        return this.h;
    }

    public Object getItem(int i) {
        Cursor cursor;
        if (!this.f1448a || (cursor = this.f1450c) == null) {
            return null;
        }
        cursor.moveToPosition(i);
        return this.f1450c;
    }

    public long getItemId(int i) {
        Cursor cursor;
        if (!this.f1448a || (cursor = this.f1450c) == null || !cursor.moveToPosition(i)) {
            return 0;
        }
        return this.f1450c.getLong(this.e);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (!this.f1448a) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        } else if (this.f1450c.moveToPosition(i)) {
            if (view == null) {
                view = b(this.f1451d, this.f1450c, viewGroup);
            }
            a(view, this.f1451d, this.f1450c);
            return view;
        } else {
            throw new IllegalStateException("couldn't move cursor to position " + i);
        }
    }
}
