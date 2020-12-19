package b.e.e;

import android.os.Build;
import android.text.PrecomputedText;
import android.text.Spannable;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.MetricAffectingSpan;
import b.e.f.c;
import java.util.concurrent.Executor;

public class a implements Spannable {

    /* renamed from: a  reason: collision with root package name */
    private static final Object f1379a = new Object();

    /* renamed from: b  reason: collision with root package name */
    private static Executor f1380b = null;

    /* renamed from: c  reason: collision with root package name */
    private final Spannable f1381c;

    /* renamed from: d  reason: collision with root package name */
    private final C0022a f1382d;
    private final PrecomputedText e;

    /* renamed from: b.e.e.a$a  reason: collision with other inner class name */
    public static final class C0022a {

        /* renamed from: a  reason: collision with root package name */
        private final TextPaint f1383a;

        /* renamed from: b  reason: collision with root package name */
        private final TextDirectionHeuristic f1384b;

        /* renamed from: c  reason: collision with root package name */
        private final int f1385c;

        /* renamed from: d  reason: collision with root package name */
        private final int f1386d;
        final PrecomputedText.Params e;

        /* renamed from: b.e.e.a$a$a  reason: collision with other inner class name */
        public static class C0023a {

            /* renamed from: a  reason: collision with root package name */
            private final TextPaint f1387a;

            /* renamed from: b  reason: collision with root package name */
            private TextDirectionHeuristic f1388b;

            /* renamed from: c  reason: collision with root package name */
            private int f1389c;

            /* renamed from: d  reason: collision with root package name */
            private int f1390d;

            public C0023a(TextPaint textPaint) {
                this.f1387a = textPaint;
                if (Build.VERSION.SDK_INT >= 23) {
                    this.f1389c = 1;
                    this.f1390d = 1;
                } else {
                    this.f1390d = 0;
                    this.f1389c = 0;
                }
                this.f1388b = Build.VERSION.SDK_INT >= 18 ? TextDirectionHeuristics.FIRSTSTRONG_LTR : null;
            }

            public C0023a a(int i) {
                this.f1389c = i;
                return this;
            }

            public C0023a a(TextDirectionHeuristic textDirectionHeuristic) {
                this.f1388b = textDirectionHeuristic;
                return this;
            }

            public C0022a a() {
                return new C0022a(this.f1387a, this.f1388b, this.f1389c, this.f1390d);
            }

            public C0023a b(int i) {
                this.f1390d = i;
                return this;
            }
        }

        public C0022a(PrecomputedText.Params params) {
            this.f1383a = params.getTextPaint();
            this.f1384b = params.getTextDirection();
            this.f1385c = params.getBreakStrategy();
            this.f1386d = params.getHyphenationFrequency();
            this.e = params;
        }

        C0022a(TextPaint textPaint, TextDirectionHeuristic textDirectionHeuristic, int i, int i2) {
            this.e = Build.VERSION.SDK_INT >= 28 ? new PrecomputedText.Params.Builder(textPaint).setBreakStrategy(i).setHyphenationFrequency(i2).setTextDirection(textDirectionHeuristic).build() : null;
            this.f1383a = textPaint;
            this.f1384b = textDirectionHeuristic;
            this.f1385c = i;
            this.f1386d = i2;
        }

        public int a() {
            return this.f1385c;
        }

        public int b() {
            return this.f1386d;
        }

        public TextDirectionHeuristic c() {
            return this.f1384b;
        }

        public TextPaint d() {
            return this.f1383a;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || !(obj instanceof C0022a)) {
                return false;
            }
            C0022a aVar = (C0022a) obj;
            PrecomputedText.Params params = this.e;
            if (params != null) {
                return params.equals(aVar.e);
            }
            if (Build.VERSION.SDK_INT >= 23 && (this.f1385c != aVar.a() || this.f1386d != aVar.b())) {
                return false;
            }
            if ((Build.VERSION.SDK_INT >= 18 && this.f1384b != aVar.c()) || this.f1383a.getTextSize() != aVar.d().getTextSize() || this.f1383a.getTextScaleX() != aVar.d().getTextScaleX() || this.f1383a.getTextSkewX() != aVar.d().getTextSkewX()) {
                return false;
            }
            if ((Build.VERSION.SDK_INT >= 21 && (this.f1383a.getLetterSpacing() != aVar.d().getLetterSpacing() || !TextUtils.equals(this.f1383a.getFontFeatureSettings(), aVar.d().getFontFeatureSettings()))) || this.f1383a.getFlags() != aVar.d().getFlags()) {
                return false;
            }
            int i = Build.VERSION.SDK_INT;
            if (i >= 24) {
                if (!this.f1383a.getTextLocales().equals(aVar.d().getTextLocales())) {
                    return false;
                }
            } else if (i >= 17 && !this.f1383a.getTextLocale().equals(aVar.d().getTextLocale())) {
                return false;
            }
            if (this.f1383a.getTypeface() == null) {
                if (aVar.d().getTypeface() != null) {
                    return false;
                }
            } else if (!this.f1383a.getTypeface().equals(aVar.d().getTypeface())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int i = Build.VERSION.SDK_INT;
            if (i >= 24) {
                return c.a(Float.valueOf(this.f1383a.getTextSize()), Float.valueOf(this.f1383a.getTextScaleX()), Float.valueOf(this.f1383a.getTextSkewX()), Float.valueOf(this.f1383a.getLetterSpacing()), Integer.valueOf(this.f1383a.getFlags()), this.f1383a.getTextLocales(), this.f1383a.getTypeface(), Boolean.valueOf(this.f1383a.isElegantTextHeight()), this.f1384b, Integer.valueOf(this.f1385c), Integer.valueOf(this.f1386d));
            } else if (i >= 21) {
                return c.a(Float.valueOf(this.f1383a.getTextSize()), Float.valueOf(this.f1383a.getTextScaleX()), Float.valueOf(this.f1383a.getTextSkewX()), Float.valueOf(this.f1383a.getLetterSpacing()), Integer.valueOf(this.f1383a.getFlags()), this.f1383a.getTextLocale(), this.f1383a.getTypeface(), Boolean.valueOf(this.f1383a.isElegantTextHeight()), this.f1384b, Integer.valueOf(this.f1385c), Integer.valueOf(this.f1386d));
            } else if (i >= 18) {
                return c.a(Float.valueOf(this.f1383a.getTextSize()), Float.valueOf(this.f1383a.getTextScaleX()), Float.valueOf(this.f1383a.getTextSkewX()), Integer.valueOf(this.f1383a.getFlags()), this.f1383a.getTextLocale(), this.f1383a.getTypeface(), this.f1384b, Integer.valueOf(this.f1385c), Integer.valueOf(this.f1386d));
            } else if (i >= 17) {
                return c.a(Float.valueOf(this.f1383a.getTextSize()), Float.valueOf(this.f1383a.getTextScaleX()), Float.valueOf(this.f1383a.getTextSkewX()), Integer.valueOf(this.f1383a.getFlags()), this.f1383a.getTextLocale(), this.f1383a.getTypeface(), this.f1384b, Integer.valueOf(this.f1385c), Integer.valueOf(this.f1386d));
            } else {
                return c.a(Float.valueOf(this.f1383a.getTextSize()), Float.valueOf(this.f1383a.getTextScaleX()), Float.valueOf(this.f1383a.getTextSkewX()), Integer.valueOf(this.f1383a.getFlags()), this.f1383a.getTypeface(), this.f1384b, Integer.valueOf(this.f1385c), Integer.valueOf(this.f1386d));
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:12:0x00e3  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.String toString() {
            /*
            // Method dump skipped, instructions count: 329
            */
            throw new UnsupportedOperationException("Method not decompiled: b.e.e.a.C0022a.toString():java.lang.String");
        }
    }

    public C0022a a() {
        return this.f1382d;
    }

    public PrecomputedText b() {
        Spannable spannable = this.f1381c;
        if (spannable instanceof PrecomputedText) {
            return (PrecomputedText) spannable;
        }
        return null;
    }

    public char charAt(int i) {
        return this.f1381c.charAt(i);
    }

    public int getSpanEnd(Object obj) {
        return this.f1381c.getSpanEnd(obj);
    }

    public int getSpanFlags(Object obj) {
        return this.f1381c.getSpanFlags(obj);
    }

    public int getSpanStart(Object obj) {
        return this.f1381c.getSpanStart(obj);
    }

    @Override // android.text.Spanned
    public <T> T[] getSpans(int i, int i2, Class<T> cls) {
        return Build.VERSION.SDK_INT >= 28 ? (T[]) this.e.getSpans(i, i2, cls) : (T[]) this.f1381c.getSpans(i, i2, cls);
    }

    public int length() {
        return this.f1381c.length();
    }

    public int nextSpanTransition(int i, int i2, Class cls) {
        return this.f1381c.nextSpanTransition(i, i2, cls);
    }

    public void removeSpan(Object obj) {
        if (obj instanceof MetricAffectingSpan) {
            throw new IllegalArgumentException("MetricAffectingSpan can not be removed from PrecomputedText.");
        } else if (Build.VERSION.SDK_INT >= 28) {
            this.e.removeSpan(obj);
        } else {
            this.f1381c.removeSpan(obj);
        }
    }

    public void setSpan(Object obj, int i, int i2, int i3) {
        if (obj instanceof MetricAffectingSpan) {
            throw new IllegalArgumentException("MetricAffectingSpan can not be set to PrecomputedText.");
        } else if (Build.VERSION.SDK_INT >= 28) {
            this.e.setSpan(obj, i, i2, i3);
        } else {
            this.f1381c.setSpan(obj, i, i2, i3);
        }
    }

    public CharSequence subSequence(int i, int i2) {
        return this.f1381c.subSequence(i, i2);
    }

    public String toString() {
        return this.f1381c.toString();
    }
}
