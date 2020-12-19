package androidx.core.widget;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import b.e.g.C0113c;
import b.e.g.t;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class k {

    /* renamed from: a  reason: collision with root package name */
    private static Method f655a;

    /* renamed from: b  reason: collision with root package name */
    private static boolean f656b;

    /* renamed from: c  reason: collision with root package name */
    private static Field f657c;

    /* renamed from: d  reason: collision with root package name */
    private static boolean f658d;

    public static void a(PopupWindow popupWindow, int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            popupWindow.setWindowLayoutType(i);
            return;
        }
        if (!f656b) {
            try {
                f655a = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", Integer.TYPE);
                f655a.setAccessible(true);
            } catch (Exception unused) {
            }
            f656b = true;
        }
        Method method = f655a;
        if (method != null) {
            try {
                method.invoke(popupWindow, Integer.valueOf(i));
            } catch (Exception unused2) {
            }
        }
    }

    public static void a(PopupWindow popupWindow, View view, int i, int i2, int i3) {
        if (Build.VERSION.SDK_INT >= 19) {
            popupWindow.showAsDropDown(view, i, i2, i3);
            return;
        }
        if ((C0113c.a(i3, t.i(view)) & 7) == 5) {
            i -= popupWindow.getWidth() - view.getWidth();
        }
        popupWindow.showAsDropDown(view, i, i2);
    }

    public static void a(PopupWindow popupWindow, boolean z) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 23) {
            popupWindow.setOverlapAnchor(z);
        } else if (i >= 21) {
            if (!f658d) {
                try {
                    f657c = PopupWindow.class.getDeclaredField("mOverlapAnchor");
                    f657c.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    Log.i("PopupWindowCompatApi21", "Could not fetch mOverlapAnchor field from PopupWindow", e);
                }
                f658d = true;
            }
            Field field = f657c;
            if (field != null) {
                try {
                    field.set(popupWindow, Boolean.valueOf(z));
                } catch (IllegalAccessException e2) {
                    Log.i("PopupWindowCompatApi21", "Could not set overlap anchor field in PopupWindow", e2);
                }
            }
        }
    }
}
