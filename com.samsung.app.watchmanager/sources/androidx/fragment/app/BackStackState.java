package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.fragment.app.C0081a;
import java.util.ArrayList;

/* access modifiers changed from: package-private */
public final class BackStackState implements Parcelable {
    public static final Parcelable.Creator<BackStackState> CREATOR = new C0082b();

    /* renamed from: a  reason: collision with root package name */
    final int[] f695a;

    /* renamed from: b  reason: collision with root package name */
    final int f696b;

    /* renamed from: c  reason: collision with root package name */
    final int f697c;

    /* renamed from: d  reason: collision with root package name */
    final String f698d;
    final int e;
    final int f;
    final CharSequence g;
    final int h;
    final CharSequence i;
    final ArrayList<String> j;
    final ArrayList<String> k;
    final boolean l;

    public BackStackState(Parcel parcel) {
        this.f695a = parcel.createIntArray();
        this.f696b = parcel.readInt();
        this.f697c = parcel.readInt();
        this.f698d = parcel.readString();
        this.e = parcel.readInt();
        this.f = parcel.readInt();
        this.g = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.h = parcel.readInt();
        this.i = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.j = parcel.createStringArrayList();
        this.k = parcel.createStringArrayList();
        this.l = parcel.readInt() != 0;
    }

    public BackStackState(C0081a aVar) {
        int size = aVar.f755b.size();
        this.f695a = new int[(size * 6)];
        if (aVar.i) {
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                C0081a.C0013a aVar2 = aVar.f755b.get(i3);
                int[] iArr = this.f695a;
                int i4 = i2 + 1;
                iArr[i2] = aVar2.f758a;
                int i5 = i4 + 1;
                Fragment fragment = aVar2.f759b;
                iArr[i4] = fragment != null ? fragment.g : -1;
                int[] iArr2 = this.f695a;
                int i6 = i5 + 1;
                iArr2[i5] = aVar2.f760c;
                int i7 = i6 + 1;
                iArr2[i6] = aVar2.f761d;
                int i8 = i7 + 1;
                iArr2[i7] = aVar2.e;
                i2 = i8 + 1;
                iArr2[i8] = aVar2.f;
            }
            this.f696b = aVar.g;
            this.f697c = aVar.h;
            this.f698d = aVar.k;
            this.e = aVar.m;
            this.f = aVar.n;
            this.g = aVar.o;
            this.h = aVar.p;
            this.i = aVar.q;
            this.j = aVar.r;
            this.k = aVar.s;
            this.l = aVar.t;
            return;
        }
        throw new IllegalStateException("Not on back stack");
    }

    public C0081a a(r rVar) {
        C0081a aVar = new C0081a(rVar);
        int i2 = 0;
        int i3 = 0;
        while (i2 < this.f695a.length) {
            C0081a.C0013a aVar2 = new C0081a.C0013a();
            int i4 = i2 + 1;
            aVar2.f758a = this.f695a[i2];
            if (r.f785a) {
                Log.v("FragmentManager", "Instantiate " + aVar + " op #" + i3 + " base fragment #" + this.f695a[i4]);
            }
            int i5 = i4 + 1;
            int i6 = this.f695a[i4];
            aVar2.f759b = i6 >= 0 ? rVar.k.get(i6) : null;
            int[] iArr = this.f695a;
            int i7 = i5 + 1;
            aVar2.f760c = iArr[i5];
            int i8 = i7 + 1;
            aVar2.f761d = iArr[i7];
            int i9 = i8 + 1;
            aVar2.e = iArr[i8];
            aVar2.f = iArr[i9];
            aVar.f756c = aVar2.f760c;
            aVar.f757d = aVar2.f761d;
            aVar.e = aVar2.e;
            aVar.f = aVar2.f;
            aVar.a(aVar2);
            i3++;
            i2 = i9 + 1;
        }
        aVar.g = this.f696b;
        aVar.h = this.f697c;
        aVar.k = this.f698d;
        aVar.m = this.e;
        aVar.i = true;
        aVar.n = this.f;
        aVar.o = this.g;
        aVar.p = this.h;
        aVar.q = this.i;
        aVar.r = this.j;
        aVar.s = this.k;
        aVar.t = this.l;
        aVar.a(1);
        return aVar;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeIntArray(this.f695a);
        parcel.writeInt(this.f696b);
        parcel.writeInt(this.f697c);
        parcel.writeString(this.f698d);
        parcel.writeInt(this.e);
        parcel.writeInt(this.f);
        TextUtils.writeToParcel(this.g, parcel, 0);
        parcel.writeInt(this.h);
        TextUtils.writeToParcel(this.i, parcel, 0);
        parcel.writeStringList(this.j);
        parcel.writeStringList(this.k);
        parcel.writeInt(this.l ? 1 : 0);
    }
}
