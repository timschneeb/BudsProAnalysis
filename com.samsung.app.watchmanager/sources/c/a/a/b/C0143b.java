package c.a.a.b;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

/* renamed from: c.a.a.b.b  reason: case insensitive filesystem */
public final class C0143b {

    /* renamed from: a  reason: collision with root package name */
    static final Type[] f1599a = new Type[0];

    /* access modifiers changed from: private */
    /* renamed from: c.a.a.b.b$a */
    public static final class a implements GenericArrayType, Serializable {

        /* renamed from: a  reason: collision with root package name */
        private final Type f1600a;

        public a(Type type) {
            this.f1600a = C0143b.c(type);
        }

        public boolean equals(Object obj) {
            return (obj instanceof GenericArrayType) && C0143b.a(this, (GenericArrayType) obj);
        }

        public Type getGenericComponentType() {
            return this.f1600a;
        }

        public int hashCode() {
            return this.f1600a.hashCode();
        }

        public String toString() {
            return C0143b.h(this.f1600a) + "[]";
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c.a.a.b.b$b  reason: collision with other inner class name */
    public static final class C0032b implements ParameterizedType, Serializable {

        /* renamed from: a  reason: collision with root package name */
        private final Type f1601a;

        /* renamed from: b  reason: collision with root package name */
        private final Type f1602b;

        /* renamed from: c  reason: collision with root package name */
        private final Type[] f1603c;

        public C0032b(Type type, Type type2, Type... typeArr) {
            int i = 0;
            if (type2 instanceof Class) {
                Class cls = (Class) type2;
                boolean z = true;
                boolean z2 = Modifier.isStatic(cls.getModifiers()) || cls.getEnclosingClass() == null;
                if (type == null && !z2) {
                    z = false;
                }
                C0117a.a(z);
            }
            this.f1601a = type == null ? null : C0143b.c(type);
            this.f1602b = C0143b.c(type2);
            this.f1603c = (Type[]) typeArr.clone();
            while (true) {
                Type[] typeArr2 = this.f1603c;
                if (i < typeArr2.length) {
                    C0117a.a(typeArr2[i]);
                    C0143b.i(this.f1603c[i]);
                    Type[] typeArr3 = this.f1603c;
                    typeArr3[i] = C0143b.c(typeArr3[i]);
                    i++;
                } else {
                    return;
                }
            }
        }

        public boolean equals(Object obj) {
            return (obj instanceof ParameterizedType) && C0143b.a(this, (ParameterizedType) obj);
        }

        public Type[] getActualTypeArguments() {
            return (Type[]) this.f1603c.clone();
        }

        public Type getOwnerType() {
            return this.f1601a;
        }

        public Type getRawType() {
            return this.f1602b;
        }

        public int hashCode() {
            return (Arrays.hashCode(this.f1603c) ^ this.f1602b.hashCode()) ^ C0143b.b((Object) this.f1601a);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder((this.f1603c.length + 1) * 30);
            sb.append(C0143b.h(this.f1602b));
            if (this.f1603c.length == 0) {
                return sb.toString();
            }
            sb.append("<");
            sb.append(C0143b.h(this.f1603c[0]));
            for (int i = 1; i < this.f1603c.length; i++) {
                sb.append(", ");
                sb.append(C0143b.h(this.f1603c[i]));
            }
            sb.append(">");
            return sb.toString();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c.a.a.b.b$c */
    public static final class c implements WildcardType, Serializable {

        /* renamed from: a  reason: collision with root package name */
        private final Type f1604a;

        /* renamed from: b  reason: collision with root package name */
        private final Type f1605b;

        public c(Type[] typeArr, Type[] typeArr2) {
            Type type;
            boolean z = true;
            C0117a.a(typeArr2.length <= 1);
            C0117a.a(typeArr.length == 1);
            if (typeArr2.length == 1) {
                C0117a.a(typeArr2[0]);
                C0143b.i(typeArr2[0]);
                C0117a.a(typeArr[0] != Object.class ? false : z);
                this.f1605b = C0143b.c(typeArr2[0]);
                type = Object.class;
            } else {
                C0117a.a(typeArr[0]);
                C0143b.i(typeArr[0]);
                this.f1605b = null;
                type = C0143b.c(typeArr[0]);
            }
            this.f1604a = type;
        }

        public boolean equals(Object obj) {
            return (obj instanceof WildcardType) && C0143b.a(this, (WildcardType) obj);
        }

        public Type[] getLowerBounds() {
            Type type = this.f1605b;
            if (type == null) {
                return C0143b.f1599a;
            }
            return new Type[]{type};
        }

        public Type[] getUpperBounds() {
            return new Type[]{this.f1604a};
        }

        public int hashCode() {
            Type type = this.f1605b;
            return (type != null ? type.hashCode() + 31 : 1) ^ (this.f1604a.hashCode() + 31);
        }

        public String toString() {
            StringBuilder sb;
            Type type;
            if (this.f1605b != null) {
                sb = new StringBuilder();
                sb.append("? super ");
                type = this.f1605b;
            } else if (this.f1604a == Object.class) {
                return "?";
            } else {
                sb = new StringBuilder();
                sb.append("? extends ");
                type = this.f1604a;
            }
            sb.append(C0143b.h(type));
            return sb.toString();
        }
    }

    private static int a(Object[] objArr, Object obj) {
        for (int i = 0; i < objArr.length; i++) {
            if (obj.equals(objArr[i])) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    private static Class<?> a(TypeVariable<?> typeVariable) {
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
            return (Class) genericDeclaration;
        }
        return null;
    }

    public static ParameterizedType a(Type type, Type type2, Type... typeArr) {
        return new C0032b(type, type2, typeArr);
    }

    public static Type a(Type type, Class<?> cls) {
        Type b2 = b(type, cls, Collection.class);
        if (b2 instanceof WildcardType) {
            b2 = ((WildcardType) b2).getUpperBounds()[0];
        }
        return b2 instanceof ParameterizedType ? ((ParameterizedType) b2).getActualTypeArguments()[0] : Object.class;
    }

    static Type a(Type type, Class<?> cls, Class<?> cls2) {
        if (cls2 == cls) {
            return type;
        }
        if (cls2.isInterface()) {
            Class<?>[] interfaces = cls.getInterfaces();
            int length = interfaces.length;
            for (int i = 0; i < length; i++) {
                if (interfaces[i] == cls2) {
                    return cls.getGenericInterfaces()[i];
                }
                if (cls2.isAssignableFrom(interfaces[i])) {
                    return a(cls.getGenericInterfaces()[i], interfaces[i], cls2);
                }
            }
        }
        if (!cls.isInterface()) {
            while (cls != Object.class) {
                Class<? super Object> superclass = cls.getSuperclass();
                if (superclass == cls2) {
                    return cls.getGenericSuperclass();
                }
                if (cls2.isAssignableFrom(superclass)) {
                    return a(cls.getGenericSuperclass(), (Class<?>) superclass, cls2);
                }
                cls = superclass;
            }
        }
        return cls2;
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:56:? */
    public static Type a(Type type, Class<?> cls, Type type2) {
        while (type2 instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) type2;
            Type a2 = a(type, cls, (TypeVariable<?>) typeVariable);
            if (a2 == typeVariable) {
                return a2;
            }
            type2 = a2;
        }
        if (type2 instanceof Class) {
            Class cls2 = (Class) type2;
            if (cls2.isArray()) {
                Class<?> componentType = cls2.getComponentType();
                Type a3 = a(type, cls, (Type) componentType);
                return componentType == a3 ? cls2 : b(a3);
            }
        }
        if (type2 instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) type2;
            Type genericComponentType = genericArrayType.getGenericComponentType();
            Type a4 = a(type, cls, genericComponentType);
            return genericComponentType == a4 ? genericArrayType : b(a4);
        }
        if (type2 instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type2;
            Type ownerType = parameterizedType.getOwnerType();
            Type a5 = a(type, cls, ownerType);
            boolean z = a5 != ownerType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            int length = actualTypeArguments.length;
            for (int i = 0; i < length; i++) {
                Type a6 = a(type, cls, actualTypeArguments[i]);
                if (a6 != actualTypeArguments[i]) {
                    if (!z) {
                        actualTypeArguments = (Type[]) actualTypeArguments.clone();
                        z = true;
                    }
                    actualTypeArguments[i] = a6;
                }
            }
            return z ? a(a5, parameterizedType.getRawType(), actualTypeArguments) : parameterizedType;
        }
        boolean z2 = type2 instanceof WildcardType;
        WildcardType wildcardType = type2;
        if (z2) {
            WildcardType wildcardType2 = (WildcardType) type2;
            Type[] lowerBounds = wildcardType2.getLowerBounds();
            Type[] upperBounds = wildcardType2.getUpperBounds();
            if (lowerBounds.length == 1) {
                Type a7 = a(type, cls, lowerBounds[0]);
                wildcardType = wildcardType2;
                if (a7 != lowerBounds[0]) {
                    return g(a7);
                }
            } else {
                wildcardType = wildcardType2;
                if (upperBounds.length == 1) {
                    Type a8 = a(type, cls, upperBounds[0]);
                    wildcardType = wildcardType2;
                    if (a8 != upperBounds[0]) {
                        return f(a8);
                    }
                }
            }
        }
        return wildcardType;
    }

    static Type a(Type type, Class<?> cls, TypeVariable<?> typeVariable) {
        Class<?> a2 = a(typeVariable);
        if (a2 == null) {
            return typeVariable;
        }
        Type a3 = a(type, cls, a2);
        if (!(a3 instanceof ParameterizedType)) {
            return typeVariable;
        }
        return ((ParameterizedType) a3).getActualTypeArguments()[a((Object[]) a2.getTypeParameters(), (Object) typeVariable)];
    }

    static boolean a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static boolean a(Type type, Type type2) {
        if (type == type2) {
            return true;
        }
        if (type instanceof Class) {
            return type.equals(type2);
        }
        if (type instanceof ParameterizedType) {
            if (!(type2 instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType parameterizedType = (ParameterizedType) type;
            ParameterizedType parameterizedType2 = (ParameterizedType) type2;
            return a(parameterizedType.getOwnerType(), parameterizedType2.getOwnerType()) && parameterizedType.getRawType().equals(parameterizedType2.getRawType()) && Arrays.equals(parameterizedType.getActualTypeArguments(), parameterizedType2.getActualTypeArguments());
        } else if (type instanceof GenericArrayType) {
            if (!(type2 instanceof GenericArrayType)) {
                return false;
            }
            return a(((GenericArrayType) type).getGenericComponentType(), ((GenericArrayType) type2).getGenericComponentType());
        } else if (type instanceof WildcardType) {
            if (!(type2 instanceof WildcardType)) {
                return false;
            }
            WildcardType wildcardType = (WildcardType) type;
            WildcardType wildcardType2 = (WildcardType) type2;
            return Arrays.equals(wildcardType.getUpperBounds(), wildcardType2.getUpperBounds()) && Arrays.equals(wildcardType.getLowerBounds(), wildcardType2.getLowerBounds());
        } else if (!(type instanceof TypeVariable) || !(type2 instanceof TypeVariable)) {
            return false;
        } else {
            TypeVariable typeVariable = (TypeVariable) type;
            TypeVariable typeVariable2 = (TypeVariable) type2;
            return typeVariable.getGenericDeclaration() == typeVariable2.getGenericDeclaration() && typeVariable.getName().equals(typeVariable2.getName());
        }
    }

    /* access modifiers changed from: private */
    public static int b(Object obj) {
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    public static GenericArrayType b(Type type) {
        return new a(type);
    }

    static Type b(Type type, Class<?> cls, Class<?> cls2) {
        C0117a.a(cls2.isAssignableFrom(cls));
        return a(type, cls, a(type, cls, cls2));
    }

    public static Type[] b(Type type, Class<?> cls) {
        if (type == Properties.class) {
            return new Type[]{String.class, String.class};
        }
        Type b2 = b(type, cls, Map.class);
        if (b2 instanceof ParameterizedType) {
            return ((ParameterizedType) b2).getActualTypeArguments();
        }
        return new Type[]{Object.class, Object.class};
    }

    public static Type c(Type type) {
        if (type instanceof Class) {
            Class cls = (Class) type;
            return cls.isArray() ? new a(c(cls.getComponentType())) : cls;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return new C0032b(parameterizedType.getOwnerType(), parameterizedType.getRawType(), parameterizedType.getActualTypeArguments());
        } else if (type instanceof GenericArrayType) {
            return new a(((GenericArrayType) type).getGenericComponentType());
        } else {
            if (!(type instanceof WildcardType)) {
                return type;
            }
            WildcardType wildcardType = (WildcardType) type;
            return new c(wildcardType.getUpperBounds(), wildcardType.getLowerBounds());
        }
    }

    public static Type d(Type type) {
        return type instanceof GenericArrayType ? ((GenericArrayType) type).getGenericComponentType() : ((Class) type).getComponentType();
    }

    public static Class<?> e(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            C0117a.a(rawType instanceof Class);
            return (Class) rawType;
        } else if (type instanceof GenericArrayType) {
            return Array.newInstance(e(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
        } else {
            if (type instanceof TypeVariable) {
                return Object.class;
            }
            if (type instanceof WildcardType) {
                return e(((WildcardType) type).getUpperBounds()[0]);
            }
            String name = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + name);
        }
    }

    public static WildcardType f(Type type) {
        return new c(new Type[]{type}, f1599a);
    }

    public static WildcardType g(Type type) {
        return new c(new Type[]{Object.class}, new Type[]{type});
    }

    public static String h(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    /* access modifiers changed from: private */
    public static void i(Type type) {
        C0117a.a(!(type instanceof Class) || !((Class) type).isPrimitive());
    }
}
