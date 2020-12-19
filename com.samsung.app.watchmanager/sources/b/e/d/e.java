package b.e.d;

import java.util.Comparator;

class e implements Comparator<byte[]> {
    e() {
    }

    /* renamed from: a */
    public int compare(byte[] bArr, byte[] bArr2) {
        int i;
        int i2;
        if (bArr.length != bArr2.length) {
            i2 = bArr.length;
            i = bArr2.length;
        } else {
            for (int i3 = 0; i3 < bArr.length; i3++) {
                if (bArr[i3] != bArr2[i3]) {
                    i2 = bArr[i3];
                    i = bArr2[i3];
                }
            }
            return 0;
        }
        return (i2 == 1 ? 1 : 0) - (i == 1 ? 1 : 0);
    }
}
