package androidx.core.content.a;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.Base64;
import android.util.TypedValue;
import android.util.Xml;
import com.samsung.android.app.twatchmanager.smartswitch.SmartSwitchConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class c {

    public interface a {
    }

    public static final class b implements a {

        /* renamed from: a  reason: collision with root package name */
        private final C0011c[] f602a;

        public b(C0011c[] cVarArr) {
            this.f602a = cVarArr;
        }

        public C0011c[] a() {
            return this.f602a;
        }
    }

    /* renamed from: androidx.core.content.a.c$c  reason: collision with other inner class name */
    public static final class C0011c {

        /* renamed from: a  reason: collision with root package name */
        private final String f603a;

        /* renamed from: b  reason: collision with root package name */
        private int f604b;

        /* renamed from: c  reason: collision with root package name */
        private boolean f605c;

        /* renamed from: d  reason: collision with root package name */
        private String f606d;
        private int e;
        private int f;

        public C0011c(String str, int i, boolean z, String str2, int i2, int i3) {
            this.f603a = str;
            this.f604b = i;
            this.f605c = z;
            this.f606d = str2;
            this.e = i2;
            this.f = i3;
        }

        public String a() {
            return this.f603a;
        }

        public int b() {
            return this.f;
        }

        public int c() {
            return this.e;
        }

        public String d() {
            return this.f606d;
        }

        public int e() {
            return this.f604b;
        }

        public boolean f() {
            return this.f605c;
        }
    }

    public static final class d implements a {

        /* renamed from: a  reason: collision with root package name */
        private final b.e.d.a f607a;

        /* renamed from: b  reason: collision with root package name */
        private final int f608b;

        /* renamed from: c  reason: collision with root package name */
        private final int f609c;

        public d(b.e.d.a aVar, int i, int i2) {
            this.f607a = aVar;
            this.f609c = i;
            this.f608b = i2;
        }

        public int a() {
            return this.f609c;
        }

        public b.e.d.a b() {
            return this.f607a;
        }

        public int c() {
            return this.f608b;
        }
    }

    private static int a(TypedArray typedArray, int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            return typedArray.getType(i);
        }
        TypedValue typedValue = new TypedValue();
        typedArray.getValue(i, typedValue);
        return typedValue.type;
    }

    public static a a(XmlPullParser xmlPullParser, Resources resources) {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next == 2) {
            return b(xmlPullParser, resources);
        }
        throw new XmlPullParserException("No start tag found");
    }

    public static List<List<byte[]>> a(Resources resources, int i) {
        if (i == 0) {
            return Collections.emptyList();
        }
        TypedArray obtainTypedArray = resources.obtainTypedArray(i);
        try {
            if (obtainTypedArray.length() == 0) {
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            if (a(obtainTypedArray, 0) == 1) {
                for (int i2 = 0; i2 < obtainTypedArray.length(); i2++) {
                    int resourceId = obtainTypedArray.getResourceId(i2, 0);
                    if (resourceId != 0) {
                        arrayList.add(a(resources.getStringArray(resourceId)));
                    }
                }
            } else {
                arrayList.add(a(resources.getStringArray(i)));
            }
            obtainTypedArray.recycle();
            return arrayList;
        } finally {
            obtainTypedArray.recycle();
        }
    }

    private static List<byte[]> a(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            arrayList.add(Base64.decode(str, 0));
        }
        return arrayList;
    }

    private static void a(XmlPullParser xmlPullParser) {
        int i = 1;
        while (i > 0) {
            int next = xmlPullParser.next();
            if (next == 2) {
                i++;
            } else if (next == 3) {
                i--;
            }
        }
    }

    private static a b(XmlPullParser xmlPullParser, Resources resources) {
        xmlPullParser.require(2, null, "font-family");
        if (xmlPullParser.getName().equals("font-family")) {
            return c(xmlPullParser, resources);
        }
        a(xmlPullParser);
        return null;
    }

    private static a c(XmlPullParser xmlPullParser, Resources resources) {
        TypedArray obtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlPullParser), b.e.c.FontFamily);
        String string = obtainAttributes.getString(b.e.c.FontFamily_fontProviderAuthority);
        String string2 = obtainAttributes.getString(b.e.c.FontFamily_fontProviderPackage);
        String string3 = obtainAttributes.getString(b.e.c.FontFamily_fontProviderQuery);
        int resourceId = obtainAttributes.getResourceId(b.e.c.FontFamily_fontProviderCerts, 0);
        int integer = obtainAttributes.getInteger(b.e.c.FontFamily_fontProviderFetchStrategy, 1);
        int integer2 = obtainAttributes.getInteger(b.e.c.FontFamily_fontProviderFetchTimeout, SmartSwitchConstants.SLEEP);
        obtainAttributes.recycle();
        if (string == null || string2 == null || string3 == null) {
            ArrayList arrayList = new ArrayList();
            while (xmlPullParser.next() != 3) {
                if (xmlPullParser.getEventType() == 2) {
                    if (xmlPullParser.getName().equals("font")) {
                        arrayList.add(d(xmlPullParser, resources));
                    } else {
                        a(xmlPullParser);
                    }
                }
            }
            if (arrayList.isEmpty()) {
                return null;
            }
            return new b((C0011c[]) arrayList.toArray(new C0011c[arrayList.size()]));
        }
        while (xmlPullParser.next() != 3) {
            a(xmlPullParser);
        }
        return new d(new b.e.d.a(string, string2, string3, a(resources, resourceId)), integer, integer2);
    }

    private static C0011c d(XmlPullParser xmlPullParser, Resources resources) {
        TypedArray obtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlPullParser), b.e.c.FontFamilyFont);
        int i = obtainAttributes.getInt(obtainAttributes.hasValue(b.e.c.FontFamilyFont_fontWeight) ? b.e.c.FontFamilyFont_fontWeight : b.e.c.FontFamilyFont_android_fontWeight, 400);
        boolean z = 1 == obtainAttributes.getInt(obtainAttributes.hasValue(b.e.c.FontFamilyFont_fontStyle) ? b.e.c.FontFamilyFont_fontStyle : b.e.c.FontFamilyFont_android_fontStyle, 0);
        int i2 = obtainAttributes.hasValue(b.e.c.FontFamilyFont_ttcIndex) ? b.e.c.FontFamilyFont_ttcIndex : b.e.c.FontFamilyFont_android_ttcIndex;
        String string = obtainAttributes.getString(obtainAttributes.hasValue(b.e.c.FontFamilyFont_fontVariationSettings) ? b.e.c.FontFamilyFont_fontVariationSettings : b.e.c.FontFamilyFont_android_fontVariationSettings);
        int i3 = obtainAttributes.getInt(i2, 0);
        int i4 = obtainAttributes.hasValue(b.e.c.FontFamilyFont_font) ? b.e.c.FontFamilyFont_font : b.e.c.FontFamilyFont_android_font;
        int resourceId = obtainAttributes.getResourceId(i4, 0);
        String string2 = obtainAttributes.getString(i4);
        obtainAttributes.recycle();
        while (xmlPullParser.next() != 3) {
            a(xmlPullParser);
        }
        return new C0011c(string2, i, z, string, i3, resourceId);
    }
}
