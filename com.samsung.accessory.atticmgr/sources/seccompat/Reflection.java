package seccompat;

import com.accessorydm.interfaces.XDMInterface;
import com.samsung.accessory.hearablemgr.Application;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import seccompat.android.os.SystemProperties;
import seccompat.android.util.Log;

public class Reflection {
    private static final boolean DEBUG_MODE;
    private static final String TAG = "Attic_Reflection";

    static {
        boolean z = false;
        if (SystemProperties.getInt("ro.debuggable", 0) == 1) {
            z = true;
        }
        DEBUG_MODE = z;
    }

    private static Class<?>[] createParameterTypes(Object... objArr) {
        Class<?>[] clsArr = new Class[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            clsArr[i] = objArr[i].getClass();
            if (clsArr[i] == Boolean.class) {
                clsArr[i] = Boolean.TYPE;
            } else if (clsArr[i] == Byte.class) {
                clsArr[i] = Byte.TYPE;
            } else if (clsArr[i] == Short.class) {
                clsArr[i] = Short.TYPE;
            } else if (clsArr[i] == Integer.class) {
                clsArr[i] = Integer.TYPE;
            } else if (clsArr[i] == Long.class) {
                clsArr[i] = Long.TYPE;
            } else if (clsArr[i] == Float.class) {
                clsArr[i] = Float.TYPE;
            } else if (clsArr[i] == Double.class) {
                clsArr[i] = Double.TYPE;
            } else if (clsArr[i] == Character.class) {
                clsArr[i] = Character.TYPE;
            }
        }
        return clsArr;
    }

    private static Object _callMethod(Object obj, Class<?> cls, String str, Object... objArr) {
        if (cls == null) {
            return null;
        }
        try {
            if (DEBUG_MODE) {
                StringBuilder sb = new StringBuilder();
                sb.append("callMethod() : ");
                sb.append(obj == null ? cls.getName() : obj);
                sb.append(XDMInterface.XDM_BASE_PATH);
                sb.append(str);
                sb.append("()");
                Log.v(TAG, sb.toString());
            }
            return cls.getMethod(str, createParameterTypes(objArr)).invoke(obj, objArr);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            throw new NoSuchMethodError(cls.getSimpleName() + XDMInterface.XDM_BASE_PATH + str + "()");
        }
    }

    public static Object callStaticMethod(String str, String str2, Object... objArr) {
        try {
            return _callMethod(null, Class.forName(str), str2, objArr);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new NoClassDefFoundError(str);
        }
    }

    public static Object callMethod(Object obj, String str, Object... objArr) {
        return _callMethod(obj, obj.getClass(), str, objArr);
    }

    private static Object _getField(Object obj, Class<?> cls, String str) {
        if (cls == null) {
            return null;
        }
        try {
            if (DEBUG_MODE) {
                StringBuilder sb = new StringBuilder();
                sb.append("getField() : ");
                sb.append(obj == null ? cls.getName() : obj);
                sb.append(XDMInterface.XDM_BASE_PATH);
                sb.append(str);
                Log.v(TAG, sb.toString());
            }
            return cls.getField(str).get(obj);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            throw new NoSuchFieldError(cls.getSimpleName() + XDMInterface.XDM_BASE_PATH + str);
        }
    }

    public static Object getStaticField(String str, String str2) {
        try {
            return _getField(null, Class.forName(str), str2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new NoClassDefFoundError(str);
        }
    }

    public static Object getStaticField(String str) {
        int lastIndexOf = str.lastIndexOf(XDMInterface.XDM_BASE_PATH);
        if (lastIndexOf != -1) {
            return getStaticField(str.substring(0, lastIndexOf), str.substring(lastIndexOf + 1));
        }
        throw new NoSuchFieldError(str);
    }

    public static Object getField(Object obj, String str) {
        return _getField(obj, obj.getClass(), str);
    }

    public static ReflectionConstructor getConstructor(String str, Class<?>... clsArr) {
        try {
            return new ReflectionConstructor(Class.forName(str).getConstructor(clsArr));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new NoClassDefFoundError(str);
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            throw new NoSuchMethodError(str);
        }
    }

    public static class ReflectionConstructor<T> {
        private Constructor<T> mConstructor;

        ReflectionConstructor(Constructor<T> constructor) {
            this.mConstructor = constructor;
        }

        public T newInstance(Object... objArr) {
            try {
                return this.mConstructor.newInstance(objArr);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
                throw new InstantiationError(this.mConstructor.getClass().getSimpleName());
            }
        }
    }

    public static void printDeclaredMethods(Class cls) {
        Method[] declaredMethods = cls.getDeclaredMethods();
        Log.d(TAG, "printDeclaredMethods() : " + cls);
        for (Method method : declaredMethods) {
            Log.v(TAG, " - " + method);
        }
    }

    public static boolean containsMethod(Class cls, String str, Class<?>... clsArr) {
        boolean z = false;
        try {
            if (cls.getMethod(str, clsArr) != null) {
                z = true;
            }
        } catch (Throwable th) {
            Log.d(TAG, "containsMethod() : Exception : " + th);
        }
        Log.d(TAG, "containsMethod() : " + str + "() : " + z);
        if (Application.DEBUG_MODE && !z) {
            printDeclaredMethods(cls);
        }
        return z;
    }
}
