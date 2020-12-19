package com.samsung.sht.sensor;

public class VectorUtil {
    public static float[] addVec(float[] fArr, float[] fArr2) {
        if (fArr.length != fArr2.length) {
            return null;
        }
        float[] fArr3 = new float[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            fArr3[i] = fArr[i] + fArr2[i];
        }
        return fArr3;
    }

    public static float[] subVec(float[] fArr, float[] fArr2) {
        if (fArr.length != fArr2.length) {
            return null;
        }
        float[] fArr3 = new float[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            fArr3[i] = fArr[i] - fArr2[i];
        }
        return fArr3;
    }

    public static float normOf(float[] fArr) {
        float f = 0.0f;
        for (int i = 0; i < fArr.length; i++) {
            f += fArr[i] * fArr[i];
        }
        return (float) Math.sqrt((double) f);
    }

    public static float[] normalizeVec(float[] fArr) {
        float normOf = normOf(fArr);
        if (normOf <= 0.0f) {
            return null;
        }
        float[] fArr2 = new float[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            fArr2[i] = fArr[i] / normOf;
        }
        return fArr2;
    }

    public static float[] avgOf(float[] fArr, float[] fArr2) {
        if (fArr.length != fArr2.length) {
            return null;
        }
        float[] fArr3 = new float[fArr.length];
        for (int i = 0; i < fArr3.length; i++) {
            fArr3[i] = (fArr[i] + fArr2[i]) / 2.0f;
        }
        return fArr3;
    }

    public static float dotProduct(float[] fArr, float[] fArr2) {
        if (fArr.length != fArr2.length) {
            return Float.NaN;
        }
        float f = 0.0f;
        for (int i = 0; i < fArr.length; i++) {
            f += fArr[i] * fArr2[i];
        }
        return f;
    }

    public static float cosBetween(float[] fArr, float[] fArr2) {
        float normOf = normOf(fArr);
        float normOf2 = normOf(fArr2);
        if (normOf <= 0.0f || normOf2 <= 0.0f) {
            return Float.NaN;
        }
        return (dotProduct(fArr, fArr2) / normOf) / normOf2;
    }
}
