package com.samsung.android.app.watchmanager.setupwizard;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import com.samsung.android.app.twatchmanager.factory.SystemPropertyFactory;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.update.SAGUIDHelper;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import java.util.Locale;

public class ContactUs {
    private static final String APPLICATION_FEEDBACK_CONTACT_US = "/faq/searchFaq.do";
    private static final String BROWSER_FEEDBACK_CONTACT_US = "/ticket/createQuestionTicket.do";
    private static final String TAG = ("tUHM:" + ContactUs.class.getSimpleName());
    private Context mContext;

    public ContactUs(Context context) {
        this.mContext = context;
    }

    private String getCountryISO() {
        try {
            String systemProperty = SystemPropertyFactory.getAndroidSystemProperty().getSystemProperty("ro.csc.countryiso_code");
            return (systemProperty == null || systemProperty.length() != 2) ? "" : systemProperty;
        } catch (Exception unused) {
            return "";
        }
    }

    private String getLanguage() {
        Locale locale = this.mContext.getResources().getConfiguration().locale;
        String locale2 = locale.toString();
        return locale2.startsWith("zh") ? HostManagerUtils.handleUnsupportableLocaleUpdate(locale).toString().toLowerCase(Locale.ENGLISH) : (locale2.startsWith("en") || locale2.startsWith("fr") || locale2.startsWith("pt") || locale2.startsWith("es") || locale2.startsWith("ar")) ? locale2.toLowerCase(Locale.ENGLISH) : locale.getLanguage();
    }

    private String getModelName() {
        String str = Build.MODEL;
        return str.contains("SAMSUNG") ? str.substring(8, str.length() - 1) : str;
    }

    private String getOsVersion() {
        return "Android " + Build.VERSION.RELEASE;
    }

    private Account getSamsungAccountName() {
        AccountManager accountManager = AccountManager.get(this.mContext);
        if (accountManager == null) {
            return null;
        }
        Account[] accounts = accountManager.getAccounts();
        for (Account account : accounts) {
            Log.d(TAG, "account [" + account + "]");
            if (SAGUIDHelper.SAMSUNG_ACCOUNT_TYPE.equals(account.type)) {
                return account;
            }
        }
        return null;
    }

    private String makeMUSEUri(String str) {
        Uri.Builder builder = new Uri.Builder();
        Account samsungAccountName = getSamsungAccountName();
        Uri.Builder appendQueryParameter = builder.scheme("https").encodedAuthority("help.content.samsung.com").appendPath("csweb").appendPath("auth").appendPath("gosupport.do").appendQueryParameter("serviceCd", "sgear").appendQueryParameter("_common_country", getCountryISO()).appendQueryParameter("_common_lang", getLanguage()).appendQueryParameter("targetUrl", str).appendQueryParameter("chnlCd", "ODC").appendQueryParameter("saccountID", samsungAccountName != null ? samsungAccountName.name : "").appendQueryParameter("dvcModelCd", getModelName());
        Context context = this.mContext;
        appendQueryParameter.appendQueryParameter("odcVersion", HostManagerUtils.getVersionName(context, context.getPackageName())).appendQueryParameter("dvcOSVersion", getOsVersion());
        Log.d(TAG, builder.build().toString());
        return builder.build().toString();
    }

    public String getApplicationURI() {
        return makeMUSEUri(APPLICATION_FEEDBACK_CONTACT_US);
    }

    public String getBrowserURI() {
        return makeMUSEUri(BROWSER_FEEDBACK_CONTACT_US);
    }
}
