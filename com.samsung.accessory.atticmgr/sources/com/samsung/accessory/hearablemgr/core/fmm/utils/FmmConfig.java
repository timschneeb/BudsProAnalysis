package com.samsung.accessory.hearablemgr.core.fmm.utils;

import android.content.Intent;
import com.samsung.accessory.hearablemgr.core.fmm.utils.FmmConstants;
import org.json.JSONException;
import org.json.JSONObject;
import seccompat.android.util.Log;

public class FmmConfig {
    private static final String TAG = "Attic_FmmConfig";
    public static String left_e2e;
    public static String left_findingSupport;
    public static String left_fmmToken;
    public static String left_iv;
    public static int left_maxN;
    public static int left_region;
    public static String left_secretKey;
    public static String left_sn;
    public static int revision;
    public static String right_e2e;
    public static String right_findingSupport;
    public static String right_fmmToken;
    public static String right_iv;
    public static int right_maxN;
    public static int right_region;
    public static String right_secretKey;
    public static String right_sn;

    public static void setFmmConfig(Intent intent) {
        String string = intent.getExtras().getString("data");
        Log.d(TAG, "jsonString : " + string);
        clearFmmConfig();
        try {
            JSONObject jSONObject = new JSONObject(string).getJSONObject("detail");
            JSONObject jSONObject2 = jSONObject.getJSONObject("left").getJSONObject("fmmConfig");
            left_sn = jSONObject2.getString(FmmConstants.fmmConfig.sn);
            left_fmmToken = jSONObject2.getString(FmmConstants.fmmConfig.fmmToken);
            left_findingSupport = jSONObject2.getString(FmmConstants.fmmConfig.findingSupport);
            left_e2e = jSONObject2.getString(FmmConstants.fmmConfig.e2e);
            left_secretKey = jSONObject2.getString(FmmConstants.fmmConfig.secretKey);
            left_iv = jSONObject2.getString(FmmConstants.fmmConfig.iv);
            left_maxN = jSONObject2.getInt(FmmConstants.fmmConfig.maxN);
            left_region = jSONObject2.getInt("region");
            JSONObject jSONObject3 = jSONObject.getJSONObject("right").getJSONObject("fmmConfig");
            right_sn = jSONObject3.getString(FmmConstants.fmmConfig.sn);
            right_fmmToken = jSONObject3.getString(FmmConstants.fmmConfig.fmmToken);
            right_findingSupport = jSONObject3.getString(FmmConstants.fmmConfig.findingSupport);
            right_e2e = jSONObject3.getString(FmmConstants.fmmConfig.e2e);
            right_secretKey = jSONObject3.getString(FmmConstants.fmmConfig.secretKey);
            right_iv = jSONObject3.getString(FmmConstants.fmmConfig.iv);
            right_maxN = jSONObject3.getInt(FmmConstants.fmmConfig.maxN);
            right_region = jSONObject3.getInt("region");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        }
        printFmmConfing();
    }

    public static void printFmmConfing() {
        Log.d(TAG, " : left_serial_number : " + left_sn);
        Log.d(TAG, " : left_fmmToken : " + left_fmmToken);
        Log.d(TAG, " : left_findingSupport : " + left_findingSupport);
        Log.d(TAG, " : left_e2e : " + left_e2e);
        Log.d(TAG, " : left_secretKey : " + left_secretKey);
        Log.d(TAG, " : left_iv : " + left_iv);
        Log.d(TAG, " : left_maxN : " + left_maxN);
        Log.d(TAG, " : left_region : " + left_region);
        Log.d(TAG, " : right_serial_number : " + right_sn);
        Log.d(TAG, " : right_fmmToken : " + right_fmmToken);
        Log.d(TAG, " : right_findingSupport : " + right_findingSupport);
        Log.d(TAG, " : right_e2e : " + right_e2e);
        Log.d(TAG, " : right_secretKey : " + right_secretKey);
        Log.d(TAG, " : right_iv : " + right_iv);
        Log.d(TAG, " : right_maxN : " + right_maxN);
        Log.d(TAG, " : right_region : " + right_region);
    }

    public static void clearFmmConfig() {
        Log.d(TAG, "clearFmmConfig()");
        left_sn = "";
        left_fmmToken = "";
        left_findingSupport = "";
        left_e2e = "";
        left_secretKey = "";
        left_iv = "";
        left_maxN = -1;
        left_region = -1;
        right_sn = "";
        right_fmmToken = "";
        right_findingSupport = "";
        right_e2e = "";
        right_secretKey = "";
        right_iv = "";
        right_maxN = -1;
        right_region = -1;
        printFmmConfing();
    }
}
