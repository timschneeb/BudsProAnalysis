package com.samsung.accessory.hearablemgr.core.bixbyroutine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.pm.PackageInfoCompat;
import com.accessorydm.eng.core.XDMWbxml;
import com.accessorydm.interfaces.XDMInterface;
import com.google.gson.JsonObject;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.util.SecurityUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.module.LaunchActivity;
import com.samsung.accessory.hearablemgr.module.mainmenu.AdvancedActivity;
import com.samsung.accessory.hearablemgr.module.noisecontrols.NoiseControlUtil;
import com.samsung.android.SDK.routine.Constants;
import com.samsung.android.sdk.mobileservice.social.buddy.provider.BuddyContract;
import java.util.ArrayList;
import java.util.HashMap;
import seccompat.SecCompatUtil;
import seccompat.android.util.Log;

public class RoutineUtils {
    private static final String TAG = (Application.TAG_ + RoutineUtils.class.getSimpleName());

    public static void showAOMDialog(final Activity activity) {
        Log.d(TAG, "showExtraDialog()");
        activity.requestWindowFeature(1);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View inflate = ((LayoutInflater) activity.getSystemService("layout_inflater")).inflate(R.layout.dialog_routine_vertical_button, (ViewGroup) null);
        inflate.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.core.bixbyroutine.RoutineUtils.AnonymousClass1 */

            public void onClick(View view) {
                activity.finish();
            }
        });
        inflate.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.core.bixbyroutine.RoutineUtils.AnonymousClass2 */

            public void onClick(View view) {
                if (Application.getCoreService().isConnected()) {
                    Intent intent = new Intent(activity, AdvancedActivity.class);
                    intent.addFlags(335577088);
                    activity.startActivity(intent);
                } else {
                    Intent intent2 = new Intent(activity, LaunchActivity.class);
                    intent2.putExtra("deviceid", UhmFwUtil.getLastLaunchDeviceId());
                    intent2.addFlags(268468224);
                    activity.startActivity(intent2);
                }
                activity.finish();
            }
        });
        builder.setView(inflate);
        builder.setMessage(R.string.routine_set_up_bixby_voice_wake_up);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            /* class com.samsung.accessory.hearablemgr.core.bixbyroutine.RoutineUtils.AnonymousClass3 */

            public void onDismiss(DialogInterface dialogInterface) {
                activity.finish();
            }
        });
        builder.show();
    }

    public static void showErrorDialog(final Activity activity, int i) {
        String str = TAG;
        Log.d(str, "showErrorDialog() :: activity : " + activity + ", validState : " + i);
        activity.requestWindowFeature(1);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        switch (i) {
            case -107:
            case -106:
            case -105:
            case -103:
            case -100:
                builder.setTitle(R.string.action_error_title);
                builder.setMessage(R.string.action_error_general_fail_because_not_set_up);
                break;
            case -104:
                builder.setTitle(R.string.action_error_title);
                builder.setMessage(R.string.action_error_general_not_supported);
                break;
            case -101:
                builder.setMessage(R.string.action_error_general_fail_because_another_operation);
                break;
        }
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.core.bixbyroutine.RoutineUtils.AnonymousClass4 */

            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finish();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            /* class com.samsung.accessory.hearablemgr.core.bixbyroutine.RoutineUtils.AnonymousClass5 */

            public void onDismiss(DialogInterface dialogInterface) {
                activity.finish();
            }
        });
        builder.show();
    }

    public static void save(Activity activity, String str, String str2) {
        String str3 = TAG;
        Log.d(str3, "save lebel : " + str + ", param : " + str2);
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_CONFIG_LABEL_PARAMS, str);
        intent.putExtra(Constants.EXTRA_CONFIG_PARAMS, str2);
        activity.setResult(-1, intent);
        activity.finish();
    }

    public static void setRTLConfigurationWithChildren(View view, int i) {
        view.setLayoutDirection(i);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                setRTLConfigurationWithChildren(viewGroup.getChildAt(i2), i);
            }
        }
    }

    public static void sendRecommendBroadcast(String str) {
        if (!Preferences.getBoolean("preference_routine.routine_recommend_broadcast_" + str, false, Preferences.MODE_MANAGER)) {
            String str2 = TAG;
            Log.d(str2, "sendRecommendBroadcast() : " + str);
            Preferences.putBoolean("preference_routine.routine_recommend_broadcast_" + str, true, Preferences.MODE_MANAGER);
            Bundle bundle = new Bundle();
            bundle.putString("recommend_tag", str);
            Intent intent = new Intent();
            intent.setPackage("com.samsung.android.app.routines");
            intent.setAction("com.samsung.android.app.routines.intent.ACTION_REQUEST_RECOMMEND_FORCED_REGISTRATION");
            intent.putExtras(bundle);
            Application.getContext().sendBroadcast(intent);
        }
    }

    public static void initialize(Context context) {
        Log.d(TAG, "initialize()");
        changeComponentEnableState(new ComponentName(context, RoutineActionProvider.class), IsSupportedRoutineVersion(context));
    }

    public static boolean isSupportedRecommendCard(Context context) {
        try {
            long longVersionCode = PackageInfoCompat.getLongVersionCode(context.getPackageManager().getPackageInfo("com.samsung.android.app.routines", 128));
            boolean z = true;
            if (Build.VERSION.SDK_INT == 29) {
                if (longVersionCode >= 262550000) {
                    Log.d(TAG, "isSupportedRecommendCard() VERSION.SDK_INT : " + Build.VERSION.SDK_INT + ", versionCode : " + longVersionCode + ", isSupported : " + z);
                    return z;
                }
            } else if (Build.VERSION.SDK_INT == 30) {
                if (longVersionCode >= 301100000) {
                    Log.d(TAG, "isSupportedRecommendCard() VERSION.SDK_INT : " + Build.VERSION.SDK_INT + ", versionCode : " + longVersionCode + ", isSupported : " + z);
                    return z;
                }
            } else if (Build.VERSION.SDK_INT > 30) {
                Log.d(TAG, "isSupportedRecommendCard() VERSION.SDK_INT : " + Build.VERSION.SDK_INT + ", versionCode : " + longVersionCode + ", isSupported : " + z);
                return z;
            }
            z = false;
            Log.d(TAG, "isSupportedRecommendCard() VERSION.SDK_INT : " + Build.VERSION.SDK_INT + ", versionCode : " + longVersionCode + ", isSupported : " + z);
            return z;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d(TAG, "routine package is null");
            return false;
        }
    }

    private static void changeComponentEnableState(ComponentName componentName, boolean z) {
        String str = TAG;
        Log.d(str, "changeComponentEnableState :: " + z);
        Application.getContext().getPackageManager().setComponentEnabledSetting(componentName, z ? 1 : 2, 1);
    }

    private static boolean IsSupportedRoutineVersion(Context context) {
        return SecCompatUtil.isSupportSEPVersion(context, 110100);
    }

    public static ArrayList getAppToAppList(Context context) {
        ArrayList arrayList = new ArrayList();
        for (ResolveInfo resolveInfo : context.getPackageManager().queryBroadcastReceivers(new Intent(Util.SEND_PUI_EVENT), XDMWbxml.WBXML_EXT_0)) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            Bundle bundle = activityInfo.metaData;
            if (bundle != null) {
                String str = activityInfo.packageName;
                String string = bundle.getString(RoutineConstants.APP_TO_APP_KEY_MENU_NAME);
                String string2 = bundle.getString("description");
                String string3 = bundle.getString("autho_key");
                if (!(string == null || string2 == null || !checkAuthorization(str, string3))) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("autho_key", str);
                    hashMap.put(RoutineConstants.APP_TO_APP_KEY_MENU_NAME, string);
                    hashMap.put("description", string2);
                    arrayList.add(hashMap);
                }
            }
        }
        return arrayList;
    }

    public static boolean checkAuthorization(String str, String str2) {
        boolean z;
        try {
            z = SecurityUtil.verify(str, str2);
        } catch (Exception e) {
            e.printStackTrace();
            z = false;
        }
        String str3 = TAG;
        Log.d(str3, "packageName : " + str);
        String str4 = TAG;
        Log.d(str4, "decryptValue : " + z);
        return z;
    }

    public static View findViewByTag(ViewGroup viewGroup, int i, Object obj) {
        for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (obj.equals(childAt.getTag(i))) {
                return childAt;
            }
        }
        return null;
    }

    public static JsonObject makeTouchpadOptionToJson(int i, int i2, String str, String str2) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("touch_option_left", Integer.valueOf(i));
        jsonObject.addProperty("touch_option_right", Integer.valueOf(i2));
        if (i == 5 && str != null) {
            jsonObject.addProperty("package_name_left", str);
        }
        if (i2 == 6 && str2 != null) {
            jsonObject.addProperty("package_name_right", str2);
        }
        return jsonObject;
    }

    public static void setActionResult(String str, String str2, int i) {
        Preferences.putInt(PreferenceKey.ROUTINE_UNKNOWN_RESULT_VALUE + str, Integer.valueOf(i));
    }

    public static int getActionResult(String str, String str2) {
        return Preferences.getInt(PreferenceKey.ROUTINE_UNKNOWN_RESULT_VALUE + str, 0);
    }

    public static String makeOnOffLabel(String str) {
        if (XDMInterface.XDM_DEVDETAIL_DEFAULT_LRGOBJ_SUPPORT.equals(str)) {
            return Application.getContext().getString(R.string.routine_off);
        }
        return Application.getContext().getString(R.string.routine_on);
    }

    public static String makeEqualizerLabel(String str) {
        if (str == null) {
            return Application.getContext().getString(R.string.eq_preset_normal);
        }
        char c = 65535;
        switch (str.hashCode()) {
            case 48:
                if (str.equals("0")) {
                    c = 0;
                    break;
                }
                break;
            case 49:
                if (str.equals("1")) {
                    c = 1;
                    break;
                }
                break;
            case 50:
                if (str.equals("2")) {
                    c = 2;
                    break;
                }
                break;
            case 51:
                if (str.equals("3")) {
                    c = 3;
                    break;
                }
                break;
            case 52:
                if (str.equals(BuddyContract.Email.Type.MOBILE)) {
                    c = 4;
                    break;
                }
                break;
            case 53:
                if (str.equals("5")) {
                    c = 5;
                    break;
                }
                break;
        }
        if (c == 0) {
            return Application.getContext().getString(R.string.eq_preset_normal);
        }
        if (c == 1) {
            return Application.getContext().getString(R.string.eq_preset_bass_boost);
        }
        if (c == 2) {
            return Application.getContext().getString(R.string.eq_preset_soft);
        }
        if (c == 3) {
            return Application.getContext().getString(R.string.eq_preset_dynamic);
        }
        if (c == 4) {
            return Application.getContext().getString(R.string.eq_preset_clear);
        }
        if (c != 5) {
            return Application.getContext().getString(R.string.eq_preset_normal);
        }
        return Application.getContext().getString(R.string.eq_preset_treble_boost);
    }

    public static String makeNoiseControlLabel(String str) {
        String string = Application.getContext().getString(R.string.noise_control_off);
        if (str == null) {
            return string;
        }
        try {
            return NoiseControlUtil.stateToText(Integer.valueOf(str).intValue());
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return string;
        }
    }
}
