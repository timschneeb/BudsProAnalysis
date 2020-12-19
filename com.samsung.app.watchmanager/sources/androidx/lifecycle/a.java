package androidx.lifecycle;

import androidx.lifecycle.f;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class a {

    /* renamed from: a  reason: collision with root package name */
    static a f832a = new a();

    /* renamed from: b  reason: collision with root package name */
    private final Map<Class, C0014a> f833b = new HashMap();

    /* renamed from: c  reason: collision with root package name */
    private final Map<Class, Boolean> f834c = new HashMap();

    /* access modifiers changed from: package-private */
    /* renamed from: androidx.lifecycle.a$a  reason: collision with other inner class name */
    public static class C0014a {

        /* renamed from: a  reason: collision with root package name */
        final Map<f.a, List<b>> f835a = new HashMap();

        /* renamed from: b  reason: collision with root package name */
        final Map<b, f.a> f836b;

        C0014a(Map<b, f.a> map) {
            this.f836b = map;
            for (Map.Entry<b, f.a> entry : map.entrySet()) {
                f.a value = entry.getValue();
                List<b> list = this.f835a.get(value);
                if (list == null) {
                    list = new ArrayList<>();
                    this.f835a.put(value, list);
                }
                list.add(entry.getKey());
            }
        }

        private static void a(List<b> list, h hVar, f.a aVar, Object obj) {
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    list.get(size).a(hVar, aVar, obj);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(h hVar, f.a aVar, Object obj) {
            a(this.f835a.get(aVar), hVar, aVar, obj);
            a(this.f835a.get(f.a.ON_ANY), hVar, aVar, obj);
        }
    }

    /* access modifiers changed from: package-private */
    public static class b {

        /* renamed from: a  reason: collision with root package name */
        final int f837a;

        /* renamed from: b  reason: collision with root package name */
        final Method f838b;

        b(int i, Method method) {
            this.f837a = i;
            this.f838b = method;
            this.f838b.setAccessible(true);
        }

        /* access modifiers changed from: package-private */
        public void a(h hVar, f.a aVar, Object obj) {
            try {
                int i = this.f837a;
                if (i == 0) {
                    this.f838b.invoke(obj, new Object[0]);
                } else if (i == 1) {
                    this.f838b.invoke(obj, hVar);
                } else if (i == 2) {
                    this.f838b.invoke(obj, hVar, aVar);
                }
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Failed to call observer method", e.getCause());
            } catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || b.class != obj.getClass()) {
                return false;
            }
            b bVar = (b) obj;
            return this.f837a == bVar.f837a && this.f838b.getName().equals(bVar.f838b.getName());
        }

        public int hashCode() {
            return (this.f837a * 31) + this.f838b.getName().hashCode();
        }
    }

    a() {
    }

    private C0014a a(Class cls, Method[] methodArr) {
        int i;
        C0014a a2;
        Class superclass = cls.getSuperclass();
        HashMap hashMap = new HashMap();
        if (!(superclass == null || (a2 = a(superclass)) == null)) {
            hashMap.putAll(a2.f836b);
        }
        for (Class<?> cls2 : cls.getInterfaces()) {
            for (Map.Entry<b, f.a> entry : a(cls2).f836b.entrySet()) {
                a(hashMap, entry.getKey(), entry.getValue(), cls);
            }
        }
        if (methodArr == null) {
            methodArr = c(cls);
        }
        boolean z = false;
        for (Method method : methodArr) {
            q qVar = (q) method.getAnnotation(q.class);
            if (qVar != null) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length <= 0) {
                    i = 0;
                } else if (parameterTypes[0].isAssignableFrom(h.class)) {
                    i = 1;
                } else {
                    throw new IllegalArgumentException("invalid parameter type. Must be one and instanceof LifecycleOwner");
                }
                f.a value = qVar.value();
                if (parameterTypes.length > 1) {
                    if (!parameterTypes[1].isAssignableFrom(f.a.class)) {
                        throw new IllegalArgumentException("invalid parameter type. second arg must be an event");
                    } else if (value == f.a.ON_ANY) {
                        i = 2;
                    } else {
                        throw new IllegalArgumentException("Second arg is supported only for ON_ANY value");
                    }
                }
                if (parameterTypes.length <= 2) {
                    a(hashMap, new b(i, method), value, cls);
                    z = true;
                } else {
                    throw new IllegalArgumentException("cannot have more than 2 params");
                }
            }
        }
        C0014a aVar = new C0014a(hashMap);
        this.f833b.put(cls, aVar);
        this.f834c.put(cls, Boolean.valueOf(z));
        return aVar;
    }

    private void a(Map<b, f.a> map, b bVar, f.a aVar, Class cls) {
        f.a aVar2 = map.get(bVar);
        if (aVar2 != null && aVar != aVar2) {
            Method method = bVar.f838b;
            throw new IllegalArgumentException("Method " + method.getName() + " in " + cls.getName() + " already declared with different @OnLifecycleEvent value: previous" + " value " + aVar2 + ", new value " + aVar);
        } else if (aVar2 == null) {
            map.put(bVar, aVar);
        }
    }

    private Method[] c(Class cls) {
        try {
            return cls.getDeclaredMethods();
        } catch (NoClassDefFoundError e) {
            throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", e);
        }
    }

    /* access modifiers changed from: package-private */
    public C0014a a(Class cls) {
        C0014a aVar = this.f833b.get(cls);
        return aVar != null ? aVar : a(cls, null);
    }

    /* access modifiers changed from: package-private */
    public boolean b(Class cls) {
        if (this.f834c.containsKey(cls)) {
            return this.f834c.get(cls).booleanValue();
        }
        Method[] c2 = c(cls);
        for (Method method : c2) {
            if (((q) method.getAnnotation(q.class)) != null) {
                a(cls, c2);
                return true;
            }
        }
        this.f834c.put(cls, false);
        return false;
    }
}
