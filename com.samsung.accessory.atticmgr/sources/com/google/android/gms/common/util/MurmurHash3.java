package com.google.android.gms.common.util;

public class MurmurHash3 {
    public static int murmurhash3_x86_32(byte[] bArr, int i, int i2, int i3) {
        int i4 = (i2 & -4) + i;
        while (i < i4) {
            int i5 = ((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | (bArr[i + 3] << 24)) * -862048943;
            int i6 = i3 ^ (((i5 << 15) | (i5 >>> 17)) * 461845907);
            i3 = (((i6 >>> 19) | (i6 << 13)) * 5) - 430675100;
            i += 4;
        }
        int i7 = 0;
        int i8 = i2 & 3;
        if (i8 != 1) {
            if (i8 != 2) {
                if (i8 == 3) {
                    i7 = (bArr[i4 + 2] & 255) << 16;
                }
                int i9 = i3 ^ i2;
                int i10 = (i9 ^ (i9 >>> 16)) * -2048144789;
                int i11 = (i10 ^ (i10 >>> 13)) * -1028477387;
                return i11 ^ (i11 >>> 16);
            }
            i7 |= (bArr[i4 + 1] & 255) << 8;
        }
        int i12 = ((bArr[i4] & 255) | i7) * -862048943;
        i3 ^= ((i12 >>> 17) | (i12 << 15)) * 461845907;
        int i92 = i3 ^ i2;
        int i102 = (i92 ^ (i92 >>> 16)) * -2048144789;
        int i112 = (i102 ^ (i102 >>> 13)) * -1028477387;
        return i112 ^ (i112 >>> 16);
    }

    private MurmurHash3() {
    }
}
