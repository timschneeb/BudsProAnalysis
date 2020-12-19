package com.accessorydm.ui.fullscreen.content;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.LeadingMarginSpan;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.samsung.android.fotaprovider.log.Log;
import com.sec.android.fotaprovider.R;

public interface MiddleContentView {

    public static class WithCaution extends BaseSoftwareUpdateInformationView {
        private final TextView tvCautionText;

        public WithCaution(Activity activity, ViewStub viewStub) {
            super(activity, viewStub);
            this.layoutCaution.setVisibility(0);
            this.tvCautionText = (TextView) activity.findViewById(R.id.textview_middle_content_caution_text);
        }

        public void setCautionText(String str) {
            TextView textView = this.tvCautionText;
            textView.setText(insertBulletIfNeeded(textView, str));
        }
    }

    public static class WithoutCaution extends BaseSoftwareUpdateInformationView {
        public WithoutCaution(Activity activity, ViewStub viewStub) {
            super(activity, viewStub);
            this.layoutCaution.setVisibility(8);
        }
    }

    public static abstract class BaseMiddleContentView implements MiddleContentView {
        Activity parentActivity;
        ViewStub viewStub;

        BaseMiddleContentView(Activity activity, ViewStub viewStub2, int i) {
            this.parentActivity = activity;
            this.viewStub = viewStub2;
            viewStub2.setLayoutResource(i);
            viewStub2.inflate();
        }
    }

    public static abstract class BaseSoftwareUpdateInformationView extends BaseMiddleContentView {
        final LinearLayout layoutCaution;
        final LinearLayout layoutWhatsNew;
        final TextView tvSoftwareUpdateInformationSize;
        final TextView tvSoftwareUpdateInformationVersion;
        final TextView tvWhatsNewText;

        BaseSoftwareUpdateInformationView(Activity activity, ViewStub viewStub) {
            super(activity, viewStub, R.layout.middle_content_full);
            this.tvSoftwareUpdateInformationVersion = (TextView) activity.findViewById(R.id.textview_middle_content_software_update_information_version);
            this.tvSoftwareUpdateInformationSize = (TextView) activity.findViewById(R.id.textview_middle_content_software_update_information_size);
            this.tvWhatsNewText = (TextView) activity.findViewById(R.id.textview_middle_content_whatsnew_description);
            this.layoutWhatsNew = (LinearLayout) activity.findViewById(R.id.layout_middle_content_whatsnew);
            this.layoutCaution = (LinearLayout) activity.findViewById(R.id.layout_middle_content_caution);
        }

        public void setSoftwareUpdateInformation(String str, String str2) {
            setSoftwareUpdateInformationVersion(str);
            setSoftwareUpdateInformationSize(str2);
        }

        private void setSoftwareUpdateInformationVersion(String str) {
            if (TextUtils.isEmpty(str)) {
                Log.W("version is empty");
                this.tvSoftwareUpdateInformationVersion.setVisibility(8);
                return;
            }
            TextView textView = this.tvSoftwareUpdateInformationVersion;
            textView.setText(insertBullet(textView, str));
        }

        private void setSoftwareUpdateInformationSize(String str) {
            if (TextUtils.isEmpty(str)) {
                Log.W("size is empty");
                this.tvSoftwareUpdateInformationSize.setVisibility(8);
                return;
            }
            TextView textView = this.tvSoftwareUpdateInformationSize;
            textView.setText(insertBullet(textView, str));
        }

        public void setWhatsNewText(String str) {
            if (TextUtils.isEmpty(str)) {
                Log.W("what's new text is empty");
                this.layoutWhatsNew.setVisibility(8);
                return;
            }
            this.tvWhatsNewText.setText(str);
        }

        private CharSequence insertBullet(TextView textView, String str) {
            return CharBulletSpan.applyTo(textView, str);
        }

        /* access modifiers changed from: package-private */
        public CharSequence insertBulletIfNeeded(TextView textView, String str) {
            return isNeedToInsertBullet(str) ? insertBullet(textView, str) : str;
        }

        private boolean isNeedToInsertBullet(String str) {
            return str.contains("\n");
        }

        /* access modifiers changed from: private */
        public static class CharBulletSpan implements LeadingMarginSpan {
            static final char DEFAULT_BULLET = 8226;
            static final int LEADING_MARGIN_BULLET_AND_ONE_SPACE = -1;
            private final char bullet;
            int leadingMargin;
            private final Paint paint;

            CharBulletSpan(TextView textView, char c, int i) {
                this.paint = textView.getPaint();
                this.bullet = c;
                this.leadingMargin = i;
            }

            public int getLeadingMargin(boolean z) {
                if (this.leadingMargin == -1) {
                    Paint paint2 = this.paint;
                    if (paint2 == null) {
                        Log.W("paint should not be null");
                    } else {
                        this.leadingMargin = (int) (paint2.measureText(String.valueOf(this.bullet + " ")) + 0.5f);
                    }
                }
                return this.leadingMargin;
            }

            public void drawLeadingMargin(Canvas canvas, Paint paint2, int i, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6, int i7, boolean z, Layout layout) {
                if (z) {
                    Paint.Align textAlign = paint2.getTextAlign();
                    paint2.setTextAlign(i2 < 0 ? Paint.Align.RIGHT : Paint.Align.LEFT);
                    canvas.drawText(String.valueOf(this.bullet), (float) i, (float) i4, paint2);
                    paint2.setTextAlign(textAlign);
                }
            }

            /* access modifiers changed from: private */
            public static CharSequence applyTo(TextView textView, CharSequence charSequence) {
                return applyTo(textView, charSequence, DEFAULT_BULLET, -1);
            }

            private static CharSequence applyTo(TextView textView, CharSequence charSequence, char c, int i) {
                SpannableString spannableString = new SpannableString(charSequence);
                String charSequence2 = charSequence.toString();
                int i2 = 0;
                while (true) {
                    int indexOf = charSequence2.indexOf(10, i2) + 1;
                    if (indexOf == 0) {
                        spannableString.setSpan(new CharBulletSpan(textView, c, i), i2, charSequence.length(), 33);
                        return spannableString;
                    }
                    spannableString.setSpan(new CharBulletSpan(textView, c, i), i2, indexOf, 33);
                    i2 = indexOf;
                }
            }
        }
    }
}
