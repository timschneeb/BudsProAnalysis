package androidx.fragment.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import java.util.ArrayList;

public class FragmentTabHost extends TabHost implements TabHost.OnTabChangeListener {

    /* renamed from: a  reason: collision with root package name */
    private final ArrayList<a> f732a = new ArrayList<>();

    /* renamed from: b  reason: collision with root package name */
    private FrameLayout f733b;

    /* renamed from: c  reason: collision with root package name */
    private Context f734c;

    /* renamed from: d  reason: collision with root package name */
    private AbstractC0091k f735d;
    private int e;
    private TabHost.OnTabChangeListener f;
    private a g;
    private boolean h;

    /* access modifiers changed from: package-private */
    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new v();

        /* renamed from: a  reason: collision with root package name */
        String f736a;

        SavedState(Parcel parcel) {
            super(parcel);
            this.f736a = parcel.readString();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab=" + this.f736a + "}";
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.f736a);
        }
    }

    /* access modifiers changed from: package-private */
    public static final class a {

        /* renamed from: a  reason: collision with root package name */
        final String f737a;

        /* renamed from: b  reason: collision with root package name */
        final Class<?> f738b;

        /* renamed from: c  reason: collision with root package name */
        final Bundle f739c;

        /* renamed from: d  reason: collision with root package name */
        Fragment f740d;
    }

    public FragmentTabHost(Context context) {
        super(context, null);
        a(context, (AttributeSet) null);
    }

    public FragmentTabHost(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    private a a(String str) {
        int size = this.f732a.size();
        for (int i = 0; i < size; i++) {
            a aVar = this.f732a.get(i);
            if (aVar.f737a.equals(str)) {
                return aVar;
            }
        }
        return null;
    }

    private w a(String str, w wVar) {
        Fragment fragment;
        a a2 = a(str);
        if (this.g != a2) {
            if (wVar == null) {
                wVar = this.f735d.a();
            }
            a aVar = this.g;
            if (!(aVar == null || (fragment = aVar.f740d) == null)) {
                wVar.b(fragment);
            }
            if (a2 != null) {
                Fragment fragment2 = a2.f740d;
                if (fragment2 == null) {
                    a2.f740d = Fragment.a(this.f734c, a2.f738b.getName(), a2.f739c);
                    wVar.a(this.e, a2.f740d, a2.f737a);
                } else {
                    wVar.a(fragment2);
                }
            }
            this.g = a2;
        }
        return wVar;
    }

    private void a() {
        if (this.f733b == null) {
            this.f733b = (FrameLayout) findViewById(this.e);
            if (this.f733b == null) {
                throw new IllegalStateException("No tab content FrameLayout found for id " + this.e);
            }
        }
    }

    private void a(Context context) {
        if (findViewById(16908307) == null) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(1);
            addView(linearLayout, new FrameLayout.LayoutParams(-1, -1));
            TabWidget tabWidget = new TabWidget(context);
            tabWidget.setId(16908307);
            tabWidget.setOrientation(0);
            linearLayout.addView(tabWidget, new LinearLayout.LayoutParams(-1, -2, 0.0f));
            FrameLayout frameLayout = new FrameLayout(context);
            frameLayout.setId(16908305);
            linearLayout.addView(frameLayout, new LinearLayout.LayoutParams(0, 0, 0.0f));
            FrameLayout frameLayout2 = new FrameLayout(context);
            this.f733b = frameLayout2;
            this.f733b.setId(this.e);
            linearLayout.addView(frameLayout2, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        }
    }

    private void a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{16842995}, 0, 0);
        this.e = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
        super.setOnTabChangedListener(this);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        String currentTabTag = getCurrentTabTag();
        int size = this.f732a.size();
        w wVar = null;
        for (int i = 0; i < size; i++) {
            a aVar = this.f732a.get(i);
            aVar.f740d = this.f735d.a(aVar.f737a);
            Fragment fragment = aVar.f740d;
            if (fragment != null && !fragment.B()) {
                if (aVar.f737a.equals(currentTabTag)) {
                    this.g = aVar;
                } else {
                    if (wVar == null) {
                        wVar = this.f735d.a();
                    }
                    wVar.b(aVar.f740d);
                }
            }
        }
        this.h = true;
        w a2 = a(currentTabTag, wVar);
        if (a2 != null) {
            a2.a();
            this.f735d.b();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.h = false;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setCurrentTabByTag(savedState.f736a);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.f736a = getCurrentTabTag();
        return savedState;
    }

    public void onTabChanged(String str) {
        w a2;
        if (this.h && (a2 = a(str, (w) null)) != null) {
            a2.a();
        }
        TabHost.OnTabChangeListener onTabChangeListener = this.f;
        if (onTabChangeListener != null) {
            onTabChangeListener.onTabChanged(str);
        }
    }

    public void setOnTabChangedListener(TabHost.OnTabChangeListener onTabChangeListener) {
        this.f = onTabChangeListener;
    }

    @Deprecated
    public void setup() {
        throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
    }

    public void setup(Context context, AbstractC0091k kVar) {
        a(context);
        super.setup();
        this.f734c = context;
        this.f735d = kVar;
        a();
    }

    public void setup(Context context, AbstractC0091k kVar, int i) {
        a(context);
        super.setup();
        this.f734c = context;
        this.f735d = kVar;
        this.e = i;
        a();
        this.f733b.setId(i);
        if (getId() == -1) {
            setId(16908306);
        }
    }
}
