package androidx.appcompat.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.widget.C0064i;
import b.a.d;
import b.a.f;
import b.a.g;
import b.a.h;
import b.a.j;
import b.e.g.AbstractC0112b;

public class ActivityChooserView extends ViewGroup implements C0064i.a {

    /* renamed from: a  reason: collision with root package name */
    final a f298a;

    /* renamed from: b  reason: collision with root package name */
    private final b f299b;

    /* renamed from: c  reason: collision with root package name */
    private final View f300c;

    /* renamed from: d  reason: collision with root package name */
    private final Drawable f301d;
    final FrameLayout e;
    private final ImageView f;
    final FrameLayout g;
    private final ImageView h;
    private final int i;
    AbstractC0112b j;
    final DataSetObserver k;
    private final ViewTreeObserver.OnGlobalLayoutListener l;
    private ListPopupWindow m;
    PopupWindow.OnDismissListener n;
    boolean o;
    int p;
    private boolean q;
    private int r;

    public static class InnerLayout extends LinearLayout {

        /* renamed from: a  reason: collision with root package name */
        private static final int[] f302a = {16842964};

        public InnerLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            ia a2 = ia.a(context, attributeSet, f302a);
            setBackgroundDrawable(a2.b(0));
            a2.a();
        }
    }

    /* access modifiers changed from: private */
    public class a extends BaseAdapter {

        /* renamed from: a  reason: collision with root package name */
        private C0064i f303a;

        /* renamed from: b  reason: collision with root package name */
        private int f304b = 4;

        /* renamed from: c  reason: collision with root package name */
        private boolean f305c;

        /* renamed from: d  reason: collision with root package name */
        private boolean f306d;
        private boolean e;

        a() {
        }

        public int a() {
            return this.f303a.a();
        }

        public void a(int i) {
            if (this.f304b != i) {
                this.f304b = i;
                notifyDataSetChanged();
            }
        }

        public void a(C0064i iVar) {
            C0064i b2 = ActivityChooserView.this.f298a.b();
            if (b2 != null && ActivityChooserView.this.isShown()) {
                b2.unregisterObserver(ActivityChooserView.this.k);
            }
            this.f303a = iVar;
            if (iVar != null && ActivityChooserView.this.isShown()) {
                iVar.registerObserver(ActivityChooserView.this.k);
            }
            notifyDataSetChanged();
        }

        public void a(boolean z) {
            if (this.e != z) {
                this.e = z;
                notifyDataSetChanged();
            }
        }

        public void a(boolean z, boolean z2) {
            if (this.f305c != z || this.f306d != z2) {
                this.f305c = z;
                this.f306d = z2;
                notifyDataSetChanged();
            }
        }

        public C0064i b() {
            return this.f303a;
        }

        public ResolveInfo c() {
            return this.f303a.b();
        }

        public int d() {
            return this.f303a.c();
        }

        public boolean e() {
            return this.f305c;
        }

        public int f() {
            int i = this.f304b;
            this.f304b = Integer.MAX_VALUE;
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
            int count = getCount();
            View view = null;
            int i2 = 0;
            for (int i3 = 0; i3 < count; i3++) {
                view = getView(i3, view, null);
                view.measure(makeMeasureSpec, makeMeasureSpec2);
                i2 = Math.max(i2, view.getMeasuredWidth());
            }
            this.f304b = i;
            return i2;
        }

        public int getCount() {
            int a2 = this.f303a.a();
            if (!this.f305c && this.f303a.b() != null) {
                a2--;
            }
            int min = Math.min(a2, this.f304b);
            return this.e ? min + 1 : min;
        }

        public Object getItem(int i) {
            int itemViewType = getItemViewType(i);
            if (itemViewType == 0) {
                if (!this.f305c && this.f303a.b() != null) {
                    i++;
                }
                return this.f303a.b(i);
            } else if (itemViewType == 1) {
                return null;
            } else {
                throw new IllegalArgumentException();
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public int getItemViewType(int i) {
            return (!this.e || i != getCount() - 1) ? 0 : 1;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            int itemViewType = getItemViewType(i);
            if (itemViewType == 0) {
                if (view == null || view.getId() != f.list_item) {
                    view = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(g.abc_activity_chooser_view_list_item, viewGroup, false);
                }
                PackageManager packageManager = ActivityChooserView.this.getContext().getPackageManager();
                ResolveInfo resolveInfo = (ResolveInfo) getItem(i);
                ((ImageView) view.findViewById(f.icon)).setImageDrawable(resolveInfo.loadIcon(packageManager));
                ((TextView) view.findViewById(f.title)).setText(resolveInfo.loadLabel(packageManager));
                if (!this.f305c || i != 0 || !this.f306d) {
                    view.setActivated(false);
                } else {
                    view.setActivated(true);
                }
                return view;
            } else if (itemViewType != 1) {
                throw new IllegalArgumentException();
            } else if (view != null && view.getId() == 1) {
                return view;
            } else {
                View inflate = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(g.abc_activity_chooser_view_list_item, viewGroup, false);
                inflate.setId(1);
                ((TextView) inflate.findViewById(f.title)).setText(ActivityChooserView.this.getContext().getString(h.abc_activity_chooser_view_see_all));
                return inflate;
            }
        }

        public int getViewTypeCount() {
            return 3;
        }
    }

    /* access modifiers changed from: private */
    public class b implements AdapterView.OnItemClickListener, View.OnClickListener, View.OnLongClickListener, PopupWindow.OnDismissListener {
        b() {
        }

        private void a() {
            PopupWindow.OnDismissListener onDismissListener = ActivityChooserView.this.n;
            if (onDismissListener != null) {
                onDismissListener.onDismiss();
            }
        }

        public void onClick(View view) {
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            if (view == activityChooserView.g) {
                activityChooserView.a();
                Intent a2 = ActivityChooserView.this.f298a.b().a(ActivityChooserView.this.f298a.b().a(ActivityChooserView.this.f298a.c()));
                if (a2 != null) {
                    a2.addFlags(524288);
                    ActivityChooserView.this.getContext().startActivity(a2);
                }
            } else if (view == activityChooserView.e) {
                activityChooserView.o = false;
                activityChooserView.a(activityChooserView.p);
            } else {
                throw new IllegalArgumentException();
            }
        }

        public void onDismiss() {
            a();
            AbstractC0112b bVar = ActivityChooserView.this.j;
            if (bVar != null) {
                bVar.a(false);
            }
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            int itemViewType = ((a) adapterView.getAdapter()).getItemViewType(i);
            if (itemViewType == 0) {
                ActivityChooserView.this.a();
                ActivityChooserView activityChooserView = ActivityChooserView.this;
                if (!activityChooserView.o) {
                    if (!activityChooserView.f298a.e()) {
                        i++;
                    }
                    Intent a2 = ActivityChooserView.this.f298a.b().a(i);
                    if (a2 != null) {
                        a2.addFlags(524288);
                        ActivityChooserView.this.getContext().startActivity(a2);
                    }
                } else if (i > 0) {
                    activityChooserView.f298a.b().c(i);
                }
            } else if (itemViewType == 1) {
                ActivityChooserView.this.a(Integer.MAX_VALUE);
            } else {
                throw new IllegalArgumentException();
            }
        }

        public boolean onLongClick(View view) {
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            if (view == activityChooserView.g) {
                if (activityChooserView.f298a.getCount() > 0) {
                    ActivityChooserView activityChooserView2 = ActivityChooserView.this;
                    activityChooserView2.o = true;
                    activityChooserView2.a(activityChooserView2.p);
                }
                return true;
            }
            throw new IllegalArgumentException();
        }
    }

    public ActivityChooserView(Context context) {
        this(context, null);
    }

    public ActivityChooserView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActivityChooserView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.k = new C0065j(this);
        this.l = new ViewTreeObserver$OnGlobalLayoutListenerC0066k(this);
        this.p = 4;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, j.ActivityChooserView, i2, 0);
        this.p = obtainStyledAttributes.getInt(j.ActivityChooserView_initialActivityCount, 4);
        Drawable drawable = obtainStyledAttributes.getDrawable(j.ActivityChooserView_expandActivityOverflowButtonDrawable);
        obtainStyledAttributes.recycle();
        LayoutInflater.from(getContext()).inflate(g.abc_activity_chooser_view, (ViewGroup) this, true);
        this.f299b = new b();
        this.f300c = findViewById(f.activity_chooser_view_content);
        this.f301d = this.f300c.getBackground();
        this.g = (FrameLayout) findViewById(f.default_activity_button);
        this.g.setOnClickListener(this.f299b);
        this.g.setOnLongClickListener(this.f299b);
        this.h = (ImageView) this.g.findViewById(f.image);
        FrameLayout frameLayout = (FrameLayout) findViewById(f.expand_activities_button);
        frameLayout.setOnClickListener(this.f299b);
        frameLayout.setAccessibilityDelegate(new C0067l(this));
        frameLayout.setOnTouchListener(new C0068m(this, frameLayout));
        this.e = frameLayout;
        this.f = (ImageView) frameLayout.findViewById(f.image);
        this.f.setImageDrawable(drawable);
        this.f298a = new a();
        this.f298a.registerDataSetObserver(new C0069n(this));
        Resources resources = context.getResources();
        this.i = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(d.abc_config_prefDialogWidth));
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(int r6) {
        /*
        // Method dump skipped, instructions count: 155
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActivityChooserView.a(int):void");
    }

    public boolean a() {
        if (!b()) {
            return true;
        }
        getListPopupWindow().dismiss();
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (!viewTreeObserver.isAlive()) {
            return true;
        }
        viewTreeObserver.removeGlobalOnLayoutListener(this.l);
        return true;
    }

    public boolean b() {
        return getListPopupWindow().b();
    }

    public boolean c() {
        if (b() || !this.q) {
            return false;
        }
        this.o = false;
        a(this.p);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void d() {
        Drawable drawable;
        View view;
        if (this.f298a.getCount() > 0) {
            this.e.setEnabled(true);
        } else {
            this.e.setEnabled(false);
        }
        int a2 = this.f298a.a();
        int d2 = this.f298a.d();
        if (a2 == 1 || (a2 > 1 && d2 > 0)) {
            this.g.setVisibility(0);
            ResolveInfo c2 = this.f298a.c();
            PackageManager packageManager = getContext().getPackageManager();
            this.h.setImageDrawable(c2.loadIcon(packageManager));
            if (this.r != 0) {
                CharSequence loadLabel = c2.loadLabel(packageManager);
                this.g.setContentDescription(getContext().getString(this.r, loadLabel));
            }
        } else {
            this.g.setVisibility(8);
        }
        if (this.g.getVisibility() == 0) {
            view = this.f300c;
            drawable = this.f301d;
        } else {
            view = this.f300c;
            drawable = null;
        }
        view.setBackgroundDrawable(drawable);
    }

    public C0064i getDataModel() {
        return this.f298a.b();
    }

    /* access modifiers changed from: package-private */
    public ListPopupWindow getListPopupWindow() {
        if (this.m == null) {
            this.m = new ListPopupWindow(getContext());
            this.m.a(this.f298a);
            this.m.a(this);
            this.m.a(true);
            this.m.a((AdapterView.OnItemClickListener) this.f299b);
            this.m.a((PopupWindow.OnDismissListener) this.f299b);
        }
        return this.m;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        C0064i b2 = this.f298a.b();
        if (b2 != null) {
            b2.registerObserver(this.k);
        }
        this.q = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        C0064i b2 = this.f298a.b();
        if (b2 != null) {
            b2.unregisterObserver(this.k);
        }
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.removeGlobalOnLayoutListener(this.l);
        }
        if (b()) {
            a();
        }
        this.q = false;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        this.f300c.layout(0, 0, i4 - i2, i5 - i3);
        if (!b()) {
            a();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        View view = this.f300c;
        if (this.g.getVisibility() != 0) {
            i3 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i3), 1073741824);
        }
        measureChild(view, i2, i3);
        setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public void setActivityChooserModel(C0064i iVar) {
        this.f298a.a(iVar);
        if (b()) {
            a();
            c();
        }
    }

    public void setDefaultActionButtonContentDescription(int i2) {
        this.r = i2;
    }

    public void setExpandActivityOverflowButtonContentDescription(int i2) {
        this.f.setContentDescription(getContext().getString(i2));
    }

    public void setExpandActivityOverflowButtonDrawable(Drawable drawable) {
        this.f.setImageDrawable(drawable);
    }

    public void setInitialActivityCount(int i2) {
        this.p = i2;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.n = onDismissListener;
    }

    public void setProvider(AbstractC0112b bVar) {
        this.j = bVar;
    }
}
