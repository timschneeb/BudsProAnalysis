package com.samsung.accessory.hearablemgr.module.aboutmenu;

import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.widget.TextView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import seccompat.android.util.Log;

class AboutEarbudsActivityUtil {
    private static final String TAG = "Attic_AboutEarbudsActivityUtil";

    AboutEarbudsActivityUtil() {
    }

    static void setDeviceName(TextView textView, String str) {
        Log.d(TAG, "setDeviceNameWithLogoImage() : " + str);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        setLogoImageSpan(textView, spannableStringBuilder, str, "Galaxy");
        setLogoImageSpan(textView, spannableStringBuilder, str, "Buds");
        setLogoImageSpan(textView, spannableStringBuilder, str, "Pro");
        textView.setText(spannableStringBuilder);
    }

    private static void setLogoImageSpan(TextView textView, SpannableStringBuilder spannableStringBuilder, String str, String str2) {
        int indexOf = str.indexOf("Galaxy Buds Pro");
        if (indexOf >= 0) {
            float textSize = textView.getTextSize();
            int indexOf2 = str.indexOf(str2, indexOf);
            if (indexOf2 >= 0) {
                char c = 65535;
                int hashCode = str2.hashCode();
                if (hashCode != 80525) {
                    if (hashCode != 2081858) {
                        if (hashCode == 2125565744 && str2.equals("Galaxy")) {
                            c = 0;
                        }
                    } else if (str2.equals("Buds")) {
                        c = 1;
                    }
                } else if (str2.equals("Pro")) {
                    c = 2;
                }
                int i = c != 0 ? c != 1 ? c != 2 ? 0 : R.drawable.logo_word_03_pro : R.drawable.logo_word_02_buds : R.drawable.logo_word_01_galaxy;
                if (i != 0) {
                    Drawable newDrawable = textView.getContext().getResources().getDrawable(i).getConstantState().newDrawable();
                    newDrawable.setBounds(0, 0, (int) ((((float) newDrawable.getIntrinsicWidth()) * textSize) / ((float) newDrawable.getIntrinsicHeight())), (int) textSize);
                    spannableStringBuilder.setSpan(new ImageSpan(newDrawable), indexOf2, str2.length() + indexOf2, 33);
                }
            }
        }
    }

    static void updateSWVersion(TextView textView) {
        String str = Application.getCoreService().getEarBudsInfo().deviceSWVer;
        if (str == null) {
            str = "";
        }
        if (Application.getCoreService().getEarBudsFotaInfo().isFotaDM != 0) {
            str = str + ".DM";
        }
        textView.setText(String.format("%s : %s", Application.getContext().getString(R.string.software_version), str));
    }
}
