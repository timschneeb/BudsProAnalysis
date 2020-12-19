package com.samsung.android.app.twatchmanager.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.ShowButtonBackgroundSettingObserver;
import com.samsung.android.app.watchmanager.R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class PermissionUtils {
    public static final String[] INITIAL_PERMISSION = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE", "android.permission.GET_ACCOUNTS"};
    public static final String[] INITIAL_PERMISSION_FOR_CONTACT_US = {"android.permission.GET_ACCOUNTS"};
    public static final String[][] PERMISSION_DRAWABLE_MAP = {new String[]{"android.permission.ACCESS_FINE_LOCATION", "2131165405"}, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "2131165408"}, new String[]{"android.permission.READ_PHONE_STATE", "2131165407"}, new String[]{"android.permission.GET_ACCOUNTS", "2131165404"}, new String[]{"android.permission.INSTALL_PACKAGES", "2131165403"}, new String[]{"android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS", "2131165406"}};
    private static final String[][] PERMISSION_GROUP = {new String[]{"android.permission.ACCESS_FINE_LOCATION", "2131624116"}, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "2131624120"}, new String[]{"android.permission.READ_PHONE_STATE", "2131624101"}, new String[]{"android.permission.GET_ACCOUNTS", "2131624102"}};
    private static final String[][] PERMISSION_REQUEST_CODE_MAP = {new String[]{"android.permission.ACCESS_FINE_LOCATION", "5003"}, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "5001"}, new String[]{"android.permission.READ_PHONE_STATE", "5005"}, new String[]{"android.permission.GET_ACCOUNTS", "5006"}};
    private static final String PREF_PERMISSION_NEVER_SHOW = "pref_permission_never_show";
    public static final int REQUEST_CODE_PERMISSION_ACCESS_FINE_LOCATION = 5003;
    public static final int REQUEST_CODE_PERMISSION_EXTERNAL_STORAGE = 5001;
    public static final int REQUEST_CODE_PERMISSION_GET_ACCOUNTS = 5006;
    public static final int REQUEST_CODE_PERMISSION_READ_PHONE_STATE = 5005;
    private static final String TAG = ("tUHM:" + PermissionUtils.class.getSimpleName());
    static TextView cancelTextView;
    private static final ShowButtonBackgroundSettingObserver.OnSettingValueChangeListener mOnSettingValueChangeListener = new ShowButtonBackgroundSettingObserver.OnSettingValueChangeListener() {
        /* class com.samsung.android.app.twatchmanager.util.PermissionUtils.AnonymousClass1 */

        @Override // com.samsung.android.app.twatchmanager.util.ShowButtonBackgroundSettingObserver.OnSettingValueChangeListener
        public void onChange(boolean z) {
            PermissionUtils.initShowButtonBackground(z);
        }
    };
    private static ShowButtonBackgroundSettingObserver mShowButtonBackgroundSettingObserver;
    static TextView settingsTextView;

    private static void getDrawableLabelForPermission(String str, Activity activity, HashSet<PermissionItem> hashSet) {
        String str2;
        Drawable drawable;
        String str3;
        if (str.equalsIgnoreCase("android.permission.ACCESS_FINE_LOCATION")) {
            drawable = activity.getResources().getDrawable(Integer.parseInt(PERMISSION_DRAWABLE_MAP[0][1]));
            str3 = PERMISSION_GROUP[0][1];
        } else if (str.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE")) {
            drawable = activity.getResources().getDrawable(Integer.parseInt(PERMISSION_DRAWABLE_MAP[1][1]));
            str3 = PERMISSION_GROUP[1][1];
        } else if (str.equalsIgnoreCase("android.permission.READ_PHONE_STATE")) {
            drawable = activity.getResources().getDrawable(Integer.parseInt(PERMISSION_DRAWABLE_MAP[2][1]));
            str3 = PERMISSION_GROUP[2][1];
        } else if (str.equalsIgnoreCase("android.permission.GET_ACCOUNTS")) {
            drawable = activity.getResources().getDrawable(Integer.parseInt(PERMISSION_DRAWABLE_MAP[3][1]));
            str3 = PERMISSION_GROUP[3][1];
        } else {
            drawable = null;
            str2 = "";
            String str4 = TAG;
            Log.d(str4, "drawable : " + drawable + " group : " + str2);
            hashSet.add(new PermissionItem(drawable, str2));
        }
        str2 = activity.getResources().getString(Integer.parseInt(str3));
        String str42 = TAG;
        Log.d(str42, "drawable : " + drawable + " group : " + str2);
        hashSet.add(new PermissionItem(drawable, str2));
    }

    public static final int getRequestCode(String str) {
        String str2 = TAG;
        Log.d(str2, "getRequestCode() permission:" + str);
        int length = PERMISSION_REQUEST_CODE_MAP.length;
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            } else if (str.equals(PERMISSION_REQUEST_CODE_MAP[i2][0])) {
                i = Integer.parseInt(PERMISSION_REQUEST_CODE_MAP[i2][1]);
                break;
            } else {
                i2++;
            }
        }
        String str3 = TAG;
        Log.d(str3, "getRequestCode() permission:" + str + " return:" + i);
        return i;
    }

    private static SpannableString getSpannableMessage(String str, String str2) {
        SpannableString spannableString = new SpannableString(str);
        int indexOf = spannableString.toString().indexOf(str2, 0);
        spannableString.setSpan(new StyleSpan(1), indexOf, str2.length() + indexOf, 0);
        return spannableString;
    }

    /* access modifiers changed from: private */
    @TargetApi(21)
    public static void initShowButtonBackground(boolean z) {
        String str = TAG;
        Log.d(str, "initShowButtonBackground() showButtonShape:" + z);
        int i = z ? R.drawable.button_background_vision_show_shape : R.drawable.button_background;
        TextView textView = cancelTextView;
        if (textView != null) {
            textView.setBackgroundResource(i);
        }
        TextView textView2 = settingsTextView;
        if (textView2 != null) {
            textView2.setBackgroundResource(i);
        }
    }

    public static boolean isNeverShowEnabled(int i) {
        SharedPreferences sharedPreferences = TWatchManagerApplication.getAppContext().getSharedPreferences(PREF_PERMISSION_NEVER_SHOW, 0);
        boolean z = sharedPreferences.getBoolean("#" + i, false);
        String str = TAG;
        Log.d(str, "isNeverShowEnabled()  requestCodeUniqueIdentifier:" + i + " result:" + z);
        return z;
    }

    public static void setNeverShow(int i, boolean z) {
        String str = TAG;
        Log.d(str, "setNeverShow() requestCodeUniqueIdentifier:" + i + " value:" + z);
        SharedPreferences.Editor edit = TWatchManagerApplication.getAppContext().getSharedPreferences(PREF_PERMISSION_NEVER_SHOW, 0).edit();
        StringBuilder sb = new StringBuilder();
        sb.append("#");
        sb.append(i);
        edit.putBoolean(sb.toString(), z);
        edit.apply();
    }

    public static void showPermissionSettingsDialog(final Activity activity, String str, ArrayList<String> arrayList, final boolean z) {
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("showPermissionSettingsDialog() activity!=null:");
        sb.append(activity != null);
        Log.d(str2, sb.toString());
        if (activity == null) {
            Log.e(TAG, "showPermissionSettingsDialog() has null instance activity");
        } else if (arrayList != null) {
            HashSet hashSet = new HashSet(arrayList.size());
            PackageManager packageManager = activity.getPackageManager();
            if (Build.VERSION.SDK_INT > 28) {
                Iterator<String> it = arrayList.iterator();
                while (it.hasNext()) {
                    getDrawableLabelForPermission(it.next(), activity, hashSet);
                }
            } else if (packageManager != null) {
                Iterator<String> it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    try {
                        PermissionGroupInfo permissionGroupInfo = packageManager.getPermissionGroupInfo(packageManager.getPermissionInfo(it2.next(), 128).group, 128);
                        hashSet.add(new PermissionItem(permissionGroupInfo.loadIcon(packageManager), permissionGroupInfo.loadLabel(packageManager).toString()));
                    } catch (Exception e) {
                        Log.w(TAG, "Permission label fetch", e);
                    }
                }
            } else {
                return;
            }
            if (hashSet.size() > 0) {
                PermissionListAdapter permissionListAdapter = new PermissionListAdapter(activity, new ArrayList(hashSet));
                View inflate = ((LayoutInflater) activity.getSystemService("layout_inflater")).inflate(R.layout.item_list_permission, (ViewGroup) null);
                ListView listView = (ListView) inflate.findViewById(R.id.permission_list);
                listView.setAdapter((ListAdapter) permissionListAdapter);
                UIUtils.setListViewHeightBasedOnItems(listView, 2);
                ((TextView) inflate.findViewById(R.id.popup_message_textview)).setText(getSpannableMessage(activity.getString(R.string.permission_settings_dialog_message, new Object[]{str}), str));
                final AlertDialog create = new AlertDialog.Builder(activity).setView(inflate).setCancelable(false).create();
                create.getWindow().setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.winset_dialog_bg));
                create.getWindow().setGravity(80);
                cancelTextView = (TextView) inflate.findViewById(R.id.cancel_btn);
                cancelTextView.setOnClickListener(new View.OnClickListener() {
                    /* class com.samsung.android.app.twatchmanager.util.PermissionUtils.AnonymousClass2 */

                    public void onClick(View view) {
                        Log.d(PermissionUtils.TAG, "Clicked cancel on permission dialog");
                        create.dismiss();
                        activity.finish();
                    }
                });
                settingsTextView = (TextView) inflate.findViewById(R.id.settings);
                settingsTextView.setOnClickListener(new View.OnClickListener() {
                    /* class com.samsung.android.app.twatchmanager.util.PermissionUtils.AnonymousClass3 */

                    public void onClick(View view) {
                        create.dismiss();
                        PermissionUtils.startManagePermissionsActivity(activity);
                        if (z) {
                            if (HostManagerUtils.DEBUGGABLE()) {
                                Toast.makeText(activity, "Gear manger closed .Relaunch again!", 1).show();
                            }
                            activity.finish();
                        }
                    }
                });
                if (HostManagerUtils.isSupportButtonShapes()) {
                    mShowButtonBackgroundSettingObserver = new ShowButtonBackgroundSettingObserver(activity.getContentResolver());
                    mShowButtonBackgroundSettingObserver.setOnContentChangeListener(mOnSettingValueChangeListener);
                }
                create.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    /* class com.samsung.android.app.twatchmanager.util.PermissionUtils.AnonymousClass4 */

                    public void onDismiss(DialogInterface dialogInterface) {
                        if (PermissionUtils.mShowButtonBackgroundSettingObserver != null) {
                            PermissionUtils.mShowButtonBackgroundSettingObserver.setOnContentChangeListener(null);
                        }
                        PermissionUtils.settingsTextView = null;
                        PermissionUtils.cancelTextView = null;
                    }
                });
                create.show();
            }
        }
    }

    /* access modifiers changed from: private */
    public static void startManagePermissionsActivity(Activity activity) {
        Log.d(TAG, "startManagePermissionsActivity");
        Intent intent = new Intent();
        intent.setFlags(268435456);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Log.w(TAG, "No app can handle Settings.ACTION_APPLICATION_DETAILS_SETTINGS");
        }
    }
}
