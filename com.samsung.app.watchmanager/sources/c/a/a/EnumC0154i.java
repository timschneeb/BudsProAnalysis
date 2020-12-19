package c.a.a;

/* renamed from: c.a.a.i  reason: case insensitive filesystem */
public enum EnumC0154i implements AbstractC0155j {
    IDENTITY {
        public java.lang.String a(
/*
[5] Method generation error in method: c.a.a.d.a(java.lang.reflect.Field):java.lang.String, file: classes.dex
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: c.a.a.d.a(java.lang.reflect.Field):java.lang.String, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:266)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:132)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:337)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:295)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:264)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        
*/
    },
    UPPER_CAMEL_CASE {
        public java.lang.String a(
/*
[9] Method generation error in method: c.a.a.e.a(java.lang.reflect.Field):java.lang.String, file: classes.dex
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: c.a.a.e.a(java.lang.reflect.Field):java.lang.String, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:266)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:132)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:337)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:295)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:264)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        
*/
    },
    UPPER_CAMEL_CASE_WITH_SPACES {
        public java.lang.String a(
/*
[15] Method generation error in method: c.a.a.f.a(java.lang.reflect.Field):java.lang.String, file: classes.dex
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: c.a.a.f.a(java.lang.reflect.Field):java.lang.String, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:266)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:132)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:337)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:295)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:264)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        
*/
    },
    LOWER_CASE_WITH_UNDERSCORES {
        public java.lang.String a(
/*
[15] Method generation error in method: c.a.a.g.a(java.lang.reflect.Field):java.lang.String, file: classes.dex
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: c.a.a.g.a(java.lang.reflect.Field):java.lang.String, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:266)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:132)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:337)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:295)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:264)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        
*/
    },
    LOWER_CASE_WITH_DASHES {
        public java.lang.String a(
/*
[15] Method generation error in method: c.a.a.h.a(java.lang.reflect.Field):java.lang.String, file: classes.dex
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: c.a.a.h.a(java.lang.reflect.Field):java.lang.String, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:266)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:132)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:337)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:295)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:264)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        
*/
    };

    private static String a(char c2, String str, int i) {
        if (i >= str.length()) {
            return String.valueOf(c2);
        }
        return c2 + str.substring(i);
    }

    /* access modifiers changed from: private */
    public static String b(String str) {
        char charAt;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            charAt = str.charAt(i);
            if (i < str.length() - 1 && !Character.isLetter(charAt)) {
                sb.append(charAt);
                i++;
            }
        }
        if (i == str.length()) {
            return sb.toString();
        }
        if (Character.isUpperCase(charAt)) {
            return str;
        }
        sb.append(a(Character.toUpperCase(charAt), str, i + 1));
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public static String b(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (Character.isUpperCase(charAt) && sb.length() != 0) {
                sb.append(str2);
            }
            sb.append(charAt);
        }
        return sb.toString();
    }
}
