package androidx.versionedparcelable;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;

class c extends b {

    /* renamed from: a  reason: collision with root package name */
    private final SparseIntArray f1173a;

    /* renamed from: b  reason: collision with root package name */
    private final Parcel f1174b;

    /* renamed from: c  reason: collision with root package name */
    private final int f1175c;

    /* renamed from: d  reason: collision with root package name */
    private final int f1176d;
    private final String e;
    private int f;
    private int g;

    c(Parcel parcel) {
        this(parcel, parcel.dataPosition(), parcel.dataSize(), "");
    }

    c(Parcel parcel, int i, int i2, String str) {
        this.f1173a = new SparseIntArray();
        this.f = -1;
        this.g = 0;
        this.f1174b = parcel;
        this.f1175c = i;
        this.f1176d = i2;
        this.g = this.f1175c;
        this.e = str;
    }

    private int d(int i) {
        int readInt;
        do {
            int i2 = this.g;
            if (i2 >= this.f1176d) {
                return -1;
            }
            this.f1174b.setDataPosition(i2);
            int readInt2 = this.f1174b.readInt();
            readInt = this.f1174b.readInt();
            this.g += readInt2;
        } while (readInt != i);
        return this.f1174b.dataPosition();
    }

    @Override // androidx.versionedparcelable.b
    public void a() {
        int i = this.f;
        if (i >= 0) {
            int i2 = this.f1173a.get(i);
            int dataPosition = this.f1174b.dataPosition();
            this.f1174b.setDataPosition(i2);
            this.f1174b.writeInt(dataPosition - i2);
            this.f1174b.setDataPosition(dataPosition);
        }
    }

    @Override // androidx.versionedparcelable.b
    public void a(Parcelable parcelable) {
        this.f1174b.writeParcelable(parcelable, 0);
    }

    @Override // androidx.versionedparcelable.b
    public void a(String str) {
        this.f1174b.writeString(str);
    }

    @Override // androidx.versionedparcelable.b
    public void a(byte[] bArr) {
        if (bArr != null) {
            this.f1174b.writeInt(bArr.length);
            this.f1174b.writeByteArray(bArr);
            return;
        }
        this.f1174b.writeInt(-1);
    }

    @Override // androidx.versionedparcelable.b
    public boolean a(int i) {
        int d2 = d(i);
        if (d2 == -1) {
            return false;
        }
        this.f1174b.setDataPosition(d2);
        return true;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.versionedparcelable.b
    public b b() {
        Parcel parcel = this.f1174b;
        int dataPosition = parcel.dataPosition();
        int i = this.g;
        if (i == this.f1175c) {
            i = this.f1176d;
        }
        return new c(parcel, dataPosition, i, this.e + "  ");
    }

    @Override // androidx.versionedparcelable.b
    public void b(int i) {
        a();
        this.f = i;
        this.f1173a.put(i, this.f1174b.dataPosition());
        c(0);
        c(i);
    }

    @Override // androidx.versionedparcelable.b
    public void c(int i) {
        this.f1174b.writeInt(i);
    }

    @Override // androidx.versionedparcelable.b
    public byte[] d() {
        int readInt = this.f1174b.readInt();
        if (readInt < 0) {
            return null;
        }
        byte[] bArr = new byte[readInt];
        this.f1174b.readByteArray(bArr);
        return bArr;
    }

    @Override // androidx.versionedparcelable.b
    public int e() {
        return this.f1174b.readInt();
    }

    @Override // androidx.versionedparcelable.b
    public <T extends Parcelable> T f() {
        return (T) this.f1174b.readParcelable(c.class.getClassLoader());
    }

    @Override // androidx.versionedparcelable.b
    public String g() {
        return this.f1174b.readString();
    }
}
