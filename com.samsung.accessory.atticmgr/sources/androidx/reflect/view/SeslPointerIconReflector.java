package androidx.reflect.view;

import android.os.Build;
import androidx.reflect.SeslBaseReflector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SeslPointerIconReflector {
    protected static String mClassName = "android.view.PointerIcon";

    private SeslPointerIconReflector() {
    }

    public static int getField_SEM_TYPE_STYLUS_DEFAULT() {
        Object obj = null;
        if (Build.VERSION.SDK_INT >= 29) {
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClassName, "hidden_SEM_TYPE_STYLUS_DEFAULT", new Class[0]);
            if (declaredMethod != null) {
                obj = SeslBaseReflector.invoke(null, declaredMethod, new Object[0]);
            }
        } else {
            Field field = SeslBaseReflector.getField(mClassName, Build.VERSION.SDK_INT >= 24 ? "SEM_TYPE_STYLUS_DEFAULT" : "HOVERING_SPENICON_DEFAULT");
            if (field != null) {
                obj = SeslBaseReflector.get(null, field);
            }
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        return 1;
    }

    public static int getField_SEM_TYPE_STYLUS_SCROLL_UP() {
        Object obj = null;
        if (Build.VERSION.SDK_INT >= 29) {
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClassName, "hidden_SEM_TYPE_STYLUS_SCROLL_UP", new Class[0]);
            if (declaredMethod != null) {
                obj = SeslBaseReflector.invoke(null, declaredMethod, new Object[0]);
            }
        } else {
            Field field = SeslBaseReflector.getField(mClassName, Build.VERSION.SDK_INT >= 24 ? "SEM_TYPE_STYLUS_SCROLL_UP" : "HOVERING_SCROLLICON_POINTER_01");
            if (field != null) {
                obj = SeslBaseReflector.get(null, field);
            }
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        return 11;
    }

    public static int getField_SEM_TYPE_STYLUS_SCROLL_DOWN() {
        Object obj = null;
        if (Build.VERSION.SDK_INT >= 29) {
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClassName, "hidden_SEM_TYPE_STYLUS_SCROLL_DOWN", new Class[0]);
            if (declaredMethod != null) {
                obj = SeslBaseReflector.invoke(null, declaredMethod, new Object[0]);
            }
        } else {
            Field field = SeslBaseReflector.getField(mClassName, Build.VERSION.SDK_INT >= 24 ? "SEM_TYPE_STYLUS_SCROLL_DOWN" : "HOVERING_SCROLLICON_POINTER_05");
            if (field != null) {
                obj = SeslBaseReflector.get(null, field);
            }
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        return 15;
    }

    public static int getField_SEM_TYPE_STYLUS_SCROLL_LEFT() {
        Object obj = null;
        if (Build.VERSION.SDK_INT >= 29) {
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClassName, "hidden_SEM_TYPE_STYLUS_SCROLL_LEFT", new Class[0]);
            if (declaredMethod != null) {
                obj = SeslBaseReflector.invoke(null, declaredMethod, new Object[0]);
            }
        } else {
            Field field = SeslBaseReflector.getField(mClassName, Build.VERSION.SDK_INT >= 24 ? "SEM_TYPE_STYLUS_SCROLL_LEFT" : "HOVERING_SCROLLICON_POINTER_07");
            if (field != null) {
                obj = SeslBaseReflector.get(null, field);
            }
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        return 17;
    }

    public static int getField_SEM_TYPE_STYLUS_SCROLL_RIGHT() {
        Object obj = null;
        if (Build.VERSION.SDK_INT >= 29) {
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClassName, "hidden_SEM_TYPE_STYLUS_SCROLL_RIGHT", new Class[0]);
            if (declaredMethod != null) {
                obj = SeslBaseReflector.invoke(null, declaredMethod, new Object[0]);
            }
        } else {
            Field field = SeslBaseReflector.getField(mClassName, Build.VERSION.SDK_INT >= 24 ? "SEM_TYPE_STYLUS_SCROLL_RIGHT" : "HOVERING_SCROLLICON_POINTER_03");
            if (field != null) {
                obj = SeslBaseReflector.get(null, field);
            }
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        return 13;
    }

    public static int getField_SEM_TYPE_STYLUS_PEN_SELECT() {
        Object obj = null;
        if (Build.VERSION.SDK_INT >= 29) {
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClassName, "hidden_SEM_TYPE_STYLUS_PEN_SELECT", new Class[0]);
            if (declaredMethod != null) {
                obj = SeslBaseReflector.invoke(null, declaredMethod, new Object[0]);
            }
        } else {
            Field field = SeslBaseReflector.getField(mClassName, Build.VERSION.SDK_INT >= 24 ? "SEM_TYPE_STYLUS_PEN_SELECT" : "HOVERING_PENSELECT_POINTER_01");
            if (field != null) {
                obj = SeslBaseReflector.get(null, field);
            }
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        return 21;
    }

    public static int getField_SEM_TYPE_STYLUS_MORE() {
        Object obj = null;
        if (Build.VERSION.SDK_INT >= 29) {
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(mClassName, "hidden_SEM_TYPE_STYLUS_MORE", new Class[0]);
            if (declaredMethod != null) {
                obj = SeslBaseReflector.invoke(null, declaredMethod, new Object[0]);
            }
        } else {
            Field field = SeslBaseReflector.getField(mClassName, Build.VERSION.SDK_INT >= 24 ? "SEM_TYPE_STYLUS_MORE" : "HOVERING_SPENICON_MORE");
            if (field != null) {
                obj = SeslBaseReflector.get(null, field);
            }
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        return 20010;
    }
}
