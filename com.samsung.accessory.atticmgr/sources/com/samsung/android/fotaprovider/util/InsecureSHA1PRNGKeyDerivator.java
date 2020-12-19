package com.samsung.android.fotaprovider.util;

import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;

public class InsecureSHA1PRNGKeyDerivator {
    private static final int BYTES_OFFSET = 81;
    private static final int COUNTER_BASE = 0;
    private static final int DIGEST_LENGTH = 20;
    private static final int[] END_FLAGS = {Integer.MIN_VALUE, 8388608, 32768, 128};
    private static final int EXTRAFRAME_OFFSET = 5;
    private static final int FRAME_LENGTH = 16;
    private static final int FRAME_OFFSET = 21;
    private static final int H0 = 1732584193;
    private static final int H1 = -271733879;
    private static final int H2 = -1732584194;
    private static final int H3 = 271733878;
    private static final int H4 = -1009589776;
    private static final int HASHBYTES_TO_USE = 20;
    private static final int HASHCOPY_OFFSET = 0;
    private static final int HASH_OFFSET = 82;
    private static final int[] LEFT = {0, 24, 16, 8};
    private static final int[] MASK = {-1, ViewCompat.MEASURED_SIZE_MASK, SupportMenu.USER_MASK, 255};
    private static final int MAX_BYTES = 48;
    private static final int NEXT_BYTES = 2;
    private static final int[] RIGHT1 = {0, 40, 48, 56};
    private static final int[] RIGHT2 = {0, 8, 16, 24};
    private static final int SET_SEED = 1;
    private static final int UNDEFINED = 0;
    private transient int[] copies;
    private transient long counter;
    private transient int nextBIndex;
    private transient byte[] nextBytes;
    private transient int[] seed = new int[87];
    private transient long seedLength;
    private transient int state;

    public static byte[] deriveInsecureKey(byte[] bArr, int i) {
        InsecureSHA1PRNGKeyDerivator insecureSHA1PRNGKeyDerivator = new InsecureSHA1PRNGKeyDerivator();
        insecureSHA1PRNGKeyDerivator.setSeed(bArr);
        byte[] bArr2 = new byte[i];
        insecureSHA1PRNGKeyDerivator.nextBytes(bArr2);
        return bArr2;
    }

    private InsecureSHA1PRNGKeyDerivator() {
        int[] iArr = this.seed;
        iArr[82] = H0;
        iArr[83] = H1;
        iArr[84] = H2;
        iArr[85] = H3;
        iArr[86] = H4;
        this.seedLength = 0;
        this.copies = new int[37];
        this.nextBytes = new byte[20];
        this.nextBIndex = 20;
        this.counter = 0;
        this.state = 0;
    }

    private void updateSeed(byte[] bArr) {
        updateHash(this.seed, bArr, 0, bArr.length - 1);
        this.seedLength += (long) bArr.length;
    }

    private synchronized void setSeed(byte[] bArr) {
        if (bArr != null) {
            if (this.state == 2) {
                System.arraycopy(this.copies, 0, this.seed, 82, 5);
            }
            this.state = 1;
            if (bArr.length != 0) {
                updateSeed(bArr);
            }
        } else {
            throw new IllegalArgumentException("seed == null");
        }
    }

    private synchronized void nextBytes(byte[] bArr) {
        int i;
        int i2;
        if (bArr != null) {
            int i3 = this.seed[81] == 0 ? 0 : (this.seed[81] + 7) >> 2;
            if (this.state != 0) {
                char c = ' ';
                int i4 = 48;
                if (this.state == 1) {
                    System.arraycopy(this.seed, 82, this.copies, 0, 5);
                    for (int i5 = i3 + 3; i5 < 18; i5++) {
                        this.seed[i5] = 0;
                    }
                    long j = (this.seedLength << 3) + 64;
                    if (this.seed[81] < 48) {
                        this.seed[14] = (int) (j >>> 32);
                        this.seed[15] = (int) (j & -1);
                    } else {
                        this.copies[19] = (int) (j >>> 32);
                        this.copies[20] = (int) (j & -1);
                    }
                    this.nextBIndex = 20;
                    i = 2;
                } else {
                    i = 2;
                }
                this.state = i;
                if (bArr.length != 0) {
                    int length = 20 - this.nextBIndex < bArr.length - 0 ? 20 - this.nextBIndex : bArr.length - 0;
                    if (length > 0) {
                        System.arraycopy(this.nextBytes, this.nextBIndex, bArr, 0, length);
                        this.nextBIndex += length;
                        i2 = length + 0;
                    } else {
                        i2 = 0;
                    }
                    if (i2 < bArr.length) {
                        int i6 = this.seed[81] & 3;
                        while (true) {
                            if (i6 == 0) {
                                this.seed[i3] = (int) (this.counter >>> c);
                                this.seed[i3 + 1] = (int) (this.counter & -1);
                                this.seed[i3 + 2] = END_FLAGS[0];
                            } else {
                                int[] iArr = this.seed;
                                iArr[i3] = ((int) (((long) MASK[i6]) & (this.counter >>> RIGHT1[i6]))) | iArr[i3];
                                this.seed[i3 + 1] = (int) ((this.counter >>> RIGHT2[i6]) & -1);
                                this.seed[i3 + 2] = (int) ((this.counter << LEFT[i6]) | ((long) END_FLAGS[i6]));
                            }
                            if (this.seed[81] > i4) {
                                this.copies[5] = this.seed[16];
                                this.copies[6] = this.seed[17];
                            }
                            computeHash(this.seed);
                            if (this.seed[81] > i4) {
                                System.arraycopy(this.seed, 0, this.copies, 21, 16);
                                System.arraycopy(this.copies, 5, this.seed, 0, 16);
                                computeHash(this.seed);
                                System.arraycopy(this.copies, 21, this.seed, 0, 16);
                            }
                            this.counter++;
                            int i7 = 0;
                            for (int i8 = 0; i8 < 5; i8++) {
                                int i9 = this.seed[i8 + 82];
                                this.nextBytes[i7] = (byte) (i9 >>> 24);
                                this.nextBytes[i7 + 1] = (byte) (i9 >>> 16);
                                this.nextBytes[i7 + 2] = (byte) (i9 >>> 8);
                                this.nextBytes[i7 + 3] = (byte) i9;
                                i7 += 4;
                            }
                            this.nextBIndex = 0;
                            int length2 = 20 < bArr.length - i2 ? 20 : bArr.length - i2;
                            if (length2 > 0) {
                                System.arraycopy(this.nextBytes, 0, bArr, i2, length2);
                                i2 += length2;
                                this.nextBIndex += length2;
                            }
                            if (i2 < bArr.length) {
                                c = ' ';
                                i4 = 48;
                            } else {
                                return;
                            }
                        }
                    }
                }
            } else {
                throw new IllegalStateException("No seed supplied!");
            }
        } else {
            throw new IllegalArgumentException("bytes == null");
        }
    }

    private static void computeHash(int[] iArr) {
        int i;
        int i2;
        int i3;
        int i4 = iArr[82];
        int i5 = iArr[83];
        int i6 = iArr[84];
        int i7 = iArr[85];
        int i8 = iArr[86];
        for (int i9 = 16; i9 < 80; i9++) {
            int i10 = ((iArr[i9 - 3] ^ iArr[i9 - 8]) ^ iArr[i9 - 14]) ^ iArr[i9 - 16];
            iArr[i9] = (i10 >>> 31) | (i10 << 1);
        }
        int i11 = 0;
        int i12 = i4;
        int i13 = i5;
        int i14 = i6;
        int i15 = i8;
        int i16 = i7;
        while (true) {
            i = 20;
            if (i11 >= 20) {
                break;
            }
            int i17 = i15 + iArr[i11] + 1518500249 + ((i12 << 5) | (i12 >>> 27)) + ((i13 & i14) | ((~i13) & i16));
            i11++;
            i14 = (i13 >>> 2) | (i13 << 30);
            i13 = i12;
            i12 = i17;
            i15 = i16;
            i16 = i14;
        }
        int i18 = i13;
        int i19 = i12;
        int i20 = i15;
        int i21 = i16;
        int i22 = i14;
        while (true) {
            i2 = 40;
            if (i >= 40) {
                break;
            }
            int i23 = i20 + iArr[i] + 1859775393 + ((i19 << 5) | (i19 >>> 27)) + ((i18 ^ i22) ^ i21);
            int i24 = (i18 >>> 2) | (i18 << 30);
            i++;
            i18 = i19;
            i19 = i23;
            i20 = i21;
            i21 = i22;
            i22 = i24;
        }
        int i25 = i19;
        int i26 = i18;
        int i27 = i21;
        int i28 = i22;
        int i29 = i20;
        while (true) {
            i3 = 60;
            if (i2 >= 60) {
                break;
            }
            int i30 = ((i29 + iArr[i2]) - 1894007588) + ((i25 << 5) | (i25 >>> 27)) + ((i26 & i28) | (i26 & i27) | (i28 & i27));
            i2++;
            i28 = (i26 >>> 2) | (i26 << 30);
            i26 = i25;
            i25 = i30;
            i29 = i27;
            i27 = i28;
        }
        int i31 = i26;
        int i32 = i25;
        int i33 = i28;
        int i34 = i29;
        int i35 = i27;
        while (i3 < 80) {
            int i36 = ((i34 + iArr[i3]) - 899497514) + ((i32 << 5) | (i32 >>> 27)) + ((i31 ^ i33) ^ i35);
            int i37 = (i31 >>> 2) | (i31 << 30);
            i3++;
            i31 = i32;
            i32 = i36;
            i34 = i35;
            i35 = i33;
            i33 = i37;
        }
        iArr[82] = iArr[82] + i32;
        iArr[83] = iArr[83] + i31;
        iArr[84] = iArr[84] + i33;
        iArr[85] = iArr[85] + i35;
        iArr[86] = iArr[86] + i34;
    }

    private static void updateHash(int[] iArr, byte[] bArr, int i, int i2) {
        int i3 = iArr[81];
        int i4 = i3 >> 2;
        int i5 = i3 & 3;
        iArr[81] = (((i3 + i2) - i) + 1) & 63;
        if (i5 != 0) {
            while (i <= i2 && i5 < 4) {
                iArr[i4] = iArr[i4] | ((bArr[i] & 255) << ((3 - i5) << 3));
                i5++;
                i++;
            }
            if (i5 == 4 && (i4 = i4 + 1) == 16) {
                computeHash(iArr);
                i4 = 0;
            }
            if (i > i2) {
                return;
            }
        }
        int i6 = ((i2 - i) + 1) >> 2;
        int i7 = i4;
        int i8 = i;
        for (int i9 = 0; i9 < i6; i9++) {
            iArr[i7] = ((bArr[i8] & 255) << 24) | ((bArr[i8 + 1] & 255) << 16) | ((bArr[i8 + 2] & 255) << 8) | (bArr[i8 + 3] & 255);
            i8 += 4;
            i7++;
            if (i7 >= 16) {
                computeHash(iArr);
                i7 = 0;
            }
        }
        int i10 = (i2 - i8) + 1;
        if (i10 != 0) {
            int i11 = (bArr[i8] & 255) << 24;
            if (i10 != 1) {
                i11 |= (bArr[i8 + 1] & 255) << 16;
                if (i10 != 2) {
                    i11 |= (bArr[i8 + 2] & 255) << 8;
                }
            }
            iArr[i7] = i11;
        }
    }
}
