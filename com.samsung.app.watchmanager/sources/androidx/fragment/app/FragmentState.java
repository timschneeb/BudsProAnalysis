package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import androidx.lifecycle.t;

/* access modifiers changed from: package-private */
public final class FragmentState implements Parcelable {
    public static final Parcelable.Creator<FragmentState> CREATOR = new u();

    /* renamed from: a  reason: collision with root package name */
    final String f728a;

    /* renamed from: b  reason: collision with root package name */
    final int f729b;

    /* renamed from: c  reason: collision with root package name */
    final boolean f730c;

    /* renamed from: d  reason: collision with root package name */
    final int f731d;
    final int e;
    final String f;
    final boolean g;
    final boolean h;
    final Bundle i;
    final boolean j;
    Bundle k;
    Fragment l;

    FragmentState(Parcel parcel) {
        this.f728a = parcel.readString();
        this.f729b = parcel.readInt();
        boolean z = true;
        this.f730c = parcel.readInt() != 0;
        this.f731d = parcel.readInt();
        this.e = parcel.readInt();
        this.f = parcel.readString();
        this.g = parcel.readInt() != 0;
        this.h = parcel.readInt() != 0;
        this.i = parcel.readBundle();
        this.j = parcel.readInt() == 0 ? false : z;
        this.k = parcel.readBundle();
    }

    FragmentState(Fragment fragment) {
        this.f728a = fragment.getClass().getName();
        this.f729b = fragment.g;
        this.f730c = fragment.o;
        this.f731d = fragment.z;
        this.e = fragment.A;
        this.f = fragment.B;
        this.g = fragment.E;
        this.h = fragment.D;
        this.i = fragment.i;
        this.j = fragment.C;
    }

    public Fragment a(AbstractC0090j jVar, AbstractC0088h hVar, Fragment fragment, s sVar, t tVar) {
        if (this.l == null) {
            Context c2 = jVar.c();
            Bundle bundle = this.i;
            if (bundle != null) {
                bundle.setClassLoader(c2.getClassLoader());
            }
            this.l = hVar != null ? hVar.a(c2, this.f728a, this.i) : Fragment.a(c2, this.f728a, this.i);
            Bundle bundle2 = this.k;
            if (bundle2 != null) {
                bundle2.setClassLoader(c2.getClassLoader());
                this.l.f713d = this.k;
            }
            this.l.a(this.f729b, fragment);
            Fragment fragment2 = this.l;
            fragment2.o = this.f730c;
            fragment2.q = true;
            fragment2.z = this.f731d;
            fragment2.A = this.e;
            fragment2.B = this.f;
            fragment2.E = this.g;
            fragment2.D = this.h;
            fragment2.C = this.j;
            fragment2.t = jVar.e;
            if (r.f785a) {
                Log.v("FragmentManager", "Instantiated fragment " + this.l);
            }
        }
        Fragment fragment3 = this.l;
        fragment3.w = sVar;
        fragment3.x = tVar;
        return fragment3;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.f728a);
        parcel.writeInt(this.f729b);
        parcel.writeInt(this.f730c ? 1 : 0);
        parcel.writeInt(this.f731d);
        parcel.writeInt(this.e);
        parcel.writeString(this.f);
        parcel.writeInt(this.g ? 1 : 0);
        parcel.writeInt(this.h ? 1 : 0);
        parcel.writeBundle(this.i);
        parcel.writeInt(this.j ? 1 : 0);
        parcel.writeBundle(this.k);
    }
}
