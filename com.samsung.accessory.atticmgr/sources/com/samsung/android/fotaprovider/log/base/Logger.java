package com.samsung.android.fotaprovider.log.base;

import java.io.PrintWriter;
import java.io.StringWriter;

public interface Logger {

    public interface Core {
        public static final int D = 2;
        public static final int E = 5;
        public static final int F = 6;
        public static final int H = 0;
        public static final int I = 3;
        public static final int V = 1;
        public static final int W = 4;

        void println(int i, String str);
    }

    void D(String str);

    void E(String str);

    void H(String str);

    void I(String str);

    void V(String str);

    void W(String str);

    void printStackTrace(Throwable th);

    public static abstract class Impl implements Core, Logger {
        static {
            LogLineInfo.excludeClass(Impl.class);
        }

        @Override // com.samsung.android.fotaprovider.log.base.Logger
        public void H(String str) {
            println(0, str);
        }

        @Override // com.samsung.android.fotaprovider.log.base.Logger
        public void V(String str) {
            println(1, str);
        }

        @Override // com.samsung.android.fotaprovider.log.base.Logger
        public void D(String str) {
            println(2, str);
        }

        @Override // com.samsung.android.fotaprovider.log.base.Logger
        public void I(String str) {
            println(3, str);
        }

        @Override // com.samsung.android.fotaprovider.log.base.Logger
        public void W(String str) {
            println(4, str);
        }

        @Override // com.samsung.android.fotaprovider.log.base.Logger
        public void E(String str) {
            println(5, str);
        }

        @Override // com.samsung.android.fotaprovider.log.base.Logger
        public void printStackTrace(Throwable th) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            try {
                th.printStackTrace(printWriter);
                H(stringWriter.toString());
            } finally {
                printWriter.close();
            }
        }
    }
}
