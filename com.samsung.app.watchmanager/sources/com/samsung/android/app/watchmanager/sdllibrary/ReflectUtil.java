package com.samsung.android.app.watchmanager.sdllibrary;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;

public class ReflectUtil {
    private static final String TAG = "ReflectUtil";

    public static Object callMethod(Class cls, String str, Object... objArr) {
        if (cls == null) {
            return null;
        }
        try {
            Log.v(TAG, "callMethod() Class : " + cls.getName() + " Method : " + str);
            Class<?>[] clsArr = new Class[objArr.length];
            for (int i = 0; i < objArr.length; i++) {
                clsArr[i] = objArr[i].getClass();
            }
            return cls.getMethod(str, clsArr).invoke(null, objArr);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public static Object callMethod(Object obj, String str, Class<?>[] clsArr, Object... objArr) {
        if (obj != null) {
            try {
                Log.v(TAG, "callMethod() Class : " + obj.getClass().getName() + " Method : " + str);
                return obj.getClass().getMethod(str, clsArr).invoke(obj, objArr);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
            }
        }
        return null;
    }

    public static Object callMethod(Object obj, String str, Object... objArr) {
        if (obj != null) {
            Log.v(TAG, "callMethod() Class : " + obj.getClass().getName() + " Method : " + str);
            try {
                Class<?> cls = obj.getClass();
                Class<?>[] clsArr = new Class[objArr.length];
                for (int i = 0; i < objArr.length; i++) {
                    Log.d(TAG, "args[" + i + "].getClass()" + objArr[i].getClass());
                    if (objArr[i].getClass() == Integer.class) {
                        clsArr[i] = Integer.TYPE;
                    } else if (objArr[i].getClass() == Boolean.class) {
                        clsArr[i] = Boolean.TYPE;
                    } else if (objArr[i].getClass() == Long.class) {
                        clsArr[i] = Long.TYPE;
                    } else {
                        clsArr[i] = objArr[i].getClass();
                    }
                }
                return cls.getMethod(str, clsArr).invoke(obj, objArr);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
            }
        }
        return null;
    }

    public static Object callMethodThrowsException(Object obj, String str, Class<?>[] clsArr, Object... objArr) {
        if (obj != null) {
            Log.v(TAG, "callMethodThrowsException() Class : " + obj.getClass().getName() + " Method : " + str);
            try {
                return obj.getClass().getMethod(str, clsArr).invoke(obj, objArr);
            } catch (NoSuchMethodException e) {
                throw e;
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
            }
        }
        return null;
    }

    public static Object callMethodThrowsException(Object obj, String str, Object... objArr) {
        if (obj != null) {
            try {
                Log.v(TAG, "callMethodThrowsException() Class : " + obj.getClass().getName() + " Method : " + str);
                Class<?> cls = obj.getClass();
                Class<?>[] clsArr = new Class[objArr.length];
                for (int i = 0; i < objArr.length; i++) {
                    Log.d(TAG, "args[" + i + "].getClass()" + objArr[i].getClass());
                    if (objArr[i].getClass() == Integer.class) {
                        clsArr[i] = Integer.TYPE;
                    } else if (objArr[i].getClass() == Boolean.class) {
                        clsArr[i] = Boolean.TYPE;
                    } else if (objArr[i].getClass() == Long.class) {
                        clsArr[i] = Long.TYPE;
                    } else {
                        clsArr[i] = objArr[i].getClass();
                    }
                }
                return cls.getMethod(str, clsArr).invoke(obj, objArr);
            } catch (NoSuchMethodException e) {
                throw e;
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
            }
        }
        return null;
    }
}
