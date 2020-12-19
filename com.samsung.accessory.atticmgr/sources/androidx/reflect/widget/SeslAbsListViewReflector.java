package androidx.reflect.widget;

import android.os.Build;
import android.widget.AbsListView;
import android.widget.EdgeEffect;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SeslAbsListViewReflector {
    private static final Class<?> mClass = AbsListView.class;

    private SeslAbsListViewReflector() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0032 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.widget.EdgeEffect getField_mEdgeGlowTop(android.widget.AbsListView r5) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 0
            r2 = 29
            if (r0 < r2) goto L_0x001b
            java.lang.Class<?> r0 = androidx.reflect.widget.SeslAbsListViewReflector.mClass
            r2 = 0
            java.lang.Class[] r3 = new java.lang.Class[r2]
            java.lang.String r4 = "hidden_mEdgeGlowTop"
            java.lang.reflect.Method r0 = androidx.reflect.SeslBaseReflector.getDeclaredMethod(r0, r4, r3)
            if (r0 == 0) goto L_0x002a
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.Object r5 = androidx.reflect.SeslBaseReflector.invoke(r5, r0, r2)
            goto L_0x002b
        L_0x001b:
            java.lang.Class<?> r0 = androidx.reflect.widget.SeslAbsListViewReflector.mClass
            java.lang.String r2 = "mEdgeGlowTop"
            java.lang.reflect.Field r0 = androidx.reflect.SeslBaseReflector.getDeclaredField(r0, r2)
            if (r0 == 0) goto L_0x002a
            java.lang.Object r5 = androidx.reflect.SeslBaseReflector.get(r5, r0)
            goto L_0x002b
        L_0x002a:
            r5 = r1
        L_0x002b:
            boolean r0 = r5 instanceof android.widget.EdgeEffect
            if (r0 == 0) goto L_0x0032
            android.widget.EdgeEffect r5 = (android.widget.EdgeEffect) r5
            return r5
        L_0x0032:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.reflect.widget.SeslAbsListViewReflector.getField_mEdgeGlowTop(android.widget.AbsListView):android.widget.EdgeEffect");
    }

    public static void setField_mEdgeGlowTop(AbsListView absListView, EdgeEffect edgeEffect) {
        if (Build.VERSION.SDK_INT >= 29) {
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_mEdgeGlowTop", EdgeEffect.class);
            if (declaredMethod != null) {
                SeslBaseReflector.invoke(absListView, declaredMethod, edgeEffect);
                return;
            }
            return;
        }
        Field declaredField = SeslBaseReflector.getDeclaredField(mClass, "mEdgeGlowTop");
        if (declaredField != null) {
            SeslBaseReflector.set(absListView, declaredField, edgeEffect);
        }
    }

    public static void setField_mEdgeGlowBottom(AbsListView absListView, EdgeEffect edgeEffect) {
        if (Build.VERSION.SDK_INT >= 29) {
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClass, "hidden_mEdgeGlowBottom", EdgeEffect.class);
            if (declaredMethod != null) {
                SeslBaseReflector.invoke(absListView, declaredMethod, edgeEffect);
                return;
            }
            return;
        }
        Field declaredField = SeslBaseReflector.getDeclaredField(mClass, "mEdgeGlowBottom");
        if (declaredField != null) {
            SeslBaseReflector.set(absListView, declaredField, edgeEffect);
        }
    }
}
