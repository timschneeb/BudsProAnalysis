package com.samsung.sht.sensor;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Arrays;

public class OrientationUtil {
    public static float[] convertDcm2Euler(float[][] fArr) {
        if (fArr.length != 3 || fArr[0].length != 3 || fArr[1].length != 3 || fArr[2].length != 3) {
            return null;
        }
        double[] dArr = new double[3];
        double[] dArr2 = new double[3];
        dArr[2] = Math.atan2((double) fArr[1][0], (double) fArr[0][0]);
        if (Math.abs(fArr[2][1]) > 1.0f) {
            fArr[2][1] = 1.0f;
        }
        dArr2[0] = Math.asin((double) fArr[2][1]);
        dArr2[1] = Math.atan2((double) (-fArr[2][0]), (double) fArr[2][2]);
        dArr2[2] = Math.atan2((double) (-fArr[0][1]), (double) fArr[1][1]);
        double abs = Math.abs(dArr2[0] / 1.5707963267948966d);
        double d = abs * abs;
        if (d == 0.0d) {
            d = 0.0010000000474974513d;
        }
        double d2 = 1.0d;
        double abs2 = (Math.abs(dArr2[1]) / 1.5707963267948966d) - 1.0d;
        if (abs2 < 0.0d) {
            abs2 = 0.0d;
        }
        double d3 = (((1.0d - d) / d) * abs2) + 1.0d;
        if (d3 > 20.0d) {
            d3 = 20.0d;
        }
        if (dArr2[0] * dArr2[1] > 0.0d) {
            if (dArr2[2] > 0.0d && dArr[2] < 0.0d) {
                dArr[2] = dArr[2] + 6.283185307179586d;
            }
        } else if (dArr2[2] < 0.0d && dArr[2] > 0.0d) {
            dArr[2] = dArr[2] - 6.283185307179586d;
        }
        double[] dArr3 = new double[3];
        double d4 = d * d3;
        dArr3[0] = ((1.0d - d4) * dArr2[2]) + (d4 * dArr[2]);
        double d5 = (double) fArr[2][1];
        if (d5 <= 1.0d) {
            d2 = d5 < -1.0d ? -1.0d : d5;
        }
        dArr3[1] = -Math.asin(d2);
        if (((double) fArr[2][2]) < 0.0d) {
            dArr3[1] = 3.141592653589793d - dArr3[1];
        }
        if (dArr3[1] > 3.141592653589793d) {
            dArr3[1] = dArr3[1] - 6.283185307179586d;
        }
        if (((double) Math.abs(fArr[2][1])) > 0.7853981633974483d) {
            dArr3[2] = Math.atan2((double) fArr[2][0], (double) fArr[2][1]);
        } else {
            dArr3[2] = Math.atan2((double) fArr[2][0], (double) fArr[2][2]);
        }
        if (dArr3[2] > 1.5707963267948966d) {
            dArr3[2] = 3.141592653589793d - dArr3[2];
        } else if (dArr3[2] < -1.5707963267948966d) {
            dArr3[2] = -3.141592653589793d - dArr3[2];
        }
        dArr3[0] = dArr3[0] * 57.29577951308232d;
        dArr3[1] = dArr3[1] * 57.29577951308232d;
        dArr3[2] = dArr3[2] * 57.29577951308232d;
        if (dArr3[0] < -180.0d) {
            dArr3[0] = dArr3[0] + 360.0d;
        } else if (dArr3[0] > 180.0d) {
            dArr3[0] = dArr3[0] - 360.0d;
        }
        return new float[]{(float) dArr3[2], (float) dArr3[1], (float) dArr3[0]};
    }

    public static float[][] convertQuat2Dcm(float[] fArr) {
        if (fArr.length != 4) {
            return null;
        }
        float[][] fArr2 = (float[][]) Array.newInstance(float.class, 3, 3);
        fArr2[0][0] = (((fArr[0] * fArr[0]) + (fArr[1] * fArr[1])) - (fArr[2] * fArr[2])) - (fArr[3] * fArr[3]);
        fArr2[0][1] = ((fArr[1] * fArr[2]) - (fArr[0] * fArr[3])) * 2.0f;
        fArr2[0][2] = ((fArr[0] * fArr[2]) + (fArr[1] * fArr[3])) * 2.0f;
        fArr2[1][0] = ((fArr[1] * fArr[2]) + (fArr[0] * fArr[3])) * 2.0f;
        fArr2[1][1] = (((fArr[0] * fArr[0]) - (fArr[1] * fArr[1])) + (fArr[2] * fArr[2])) - (fArr[3] * fArr[3]);
        fArr2[1][2] = ((fArr[2] * fArr[3]) - (fArr[0] * fArr[1])) * 2.0f;
        fArr2[2][0] = ((fArr[1] * fArr[3]) - (fArr[0] * fArr[2])) * 2.0f;
        fArr2[2][1] = ((fArr[0] * fArr[1]) + (fArr[2] * fArr[3])) * 2.0f;
        fArr2[2][2] = (((fArr[0] * fArr[0]) - (fArr[1] * fArr[1])) - (fArr[2] * fArr[2])) + (fArr[3] * fArr[3]);
        return fArr2;
    }

    public static float[] convertQuat2Euler(float[] fArr) {
        if (fArr.length != 4) {
            return null;
        }
        return convertDcm2Euler(convertQuat2Dcm(fArr));
    }

    public static float[] convertAOSPQuat2Euler(float[] fArr) {
        if (fArr.length != 4) {
            return null;
        }
        return convertQuat2Euler(new float[]{fArr[3], fArr[0], fArr[1], fArr[2]});
    }

    public static float[] mulQuat(float[] fArr, float[] fArr2) {
        if (fArr.length != 4 || fArr2.length != 4) {
            return null;
        }
        return new float[]{(((fArr[0] * fArr2[0]) - (fArr[1] * fArr2[1])) - (fArr[2] * fArr2[2])) - (fArr[3] * fArr2[3]), (((fArr[1] * fArr2[0]) + (fArr[0] * fArr2[1])) - (fArr[3] * fArr2[2])) + (fArr[2] * fArr2[3]), (((fArr[2] * fArr2[0]) + (fArr[3] * fArr2[1])) + (fArr[0] * fArr2[2])) - (fArr[1] * fArr2[3]), ((fArr[3] * fArr2[0]) - (fArr[2] * fArr2[1])) + (fArr[1] * fArr2[2]) + (fArr[0] * fArr2[3])};
    }

    public static float[] normalizeQuat(float[] fArr) {
        if (fArr.length != 4) {
            return null;
        }
        float[] normalizeVec = VectorUtil.normalizeVec(fArr);
        if (normalizeVec != null && normalizeVec[0] < 0.0f) {
            normalizeVec[0] = normalizeVec[0] * -1.0f;
            normalizeVec[1] = normalizeVec[1] * -1.0f;
            normalizeVec[2] = normalizeVec[2] * -1.0f;
            normalizeVec[3] = normalizeVec[3] * -1.0f;
        }
        return normalizeVec;
    }

    public static float[] conjugateQuat(float[] fArr) {
        if (fArr.length != 4) {
            return null;
        }
        return new float[]{fArr[0], -fArr[1], -fArr[2], -fArr[3]};
    }

    public static float[] rotateVec3WithQuat(float[] fArr, float[] fArr2) {
        if (fArr.length != 3 || fArr2.length != 4) {
            return null;
        }
        return Arrays.copyOfRange(mulQuat(fArr2, mulQuat(new float[]{0.0f, fArr[0], fArr[1], fArr[2]}, conjugateQuat(fArr2))), 1, 4);
    }

    public static void main(String[] strArr) {
        float[] mulQuat = mulQuat(new float[]{0.0392f, 0.2082f, -0.5598f, -0.8011f}, new float[]{0.6152f, -0.541f, 0.4341f, 0.3747f});
        PrintStream printStream = System.out;
        printStream.println(mulQuat[0] + "," + mulQuat[1] + "," + mulQuat[2] + "," + mulQuat[3]);
    }
}
