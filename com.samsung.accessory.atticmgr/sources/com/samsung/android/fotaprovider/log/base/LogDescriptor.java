package com.samsung.android.fotaprovider.log.base;

import com.samsung.android.fotaprovider.log.Log;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public abstract class LogDescriptor {
    public static final LogDescriptor NULL = new LogDescriptor() {
        /* class com.samsung.android.fotaprovider.log.base.LogDescriptor.AnonymousClass1 */

        @Override // com.samsung.android.fotaprovider.log.base.LogDescriptor
        public void println(String str) {
        }

        @Override // com.samsung.android.fotaprovider.log.base.LogDescriptor
        public void shift() {
        }
    };

    public void onBefore() {
    }

    public abstract void println(String str);

    public abstract void shift();

    protected LogDescriptor() {
    }

    public static abstract class Stream extends LogDescriptor {
        public abstract OutputStream getOutputStream() throws FileNotFoundException;

        /* access modifiers changed from: protected */
        public abstract long size();

        public boolean isLogExceeds(long j) {
            return size() > j;
        }

        @Override // com.samsung.android.fotaprovider.log.base.LogDescriptor
        public void println(String str) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(getOutputStream(), Charset.defaultCharset()));
                try {
                    bufferedWriter.write(str);
                    try {
                        bufferedWriter.close();
                        return;
                    } catch (IOException e) {
                        e = e;
                    }
                    Log.printStackTrace(e);
                } catch (IOException e2) {
                    Log.printStackTrace(e2);
                    try {
                        bufferedWriter.close();
                    } catch (IOException e3) {
                        e = e3;
                    }
                } catch (Throwable th) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e4) {
                        Log.printStackTrace(e4);
                    }
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                Log.printStackTrace(e5);
            } catch (RuntimeException e6) {
                Log.printStackTrace(e6);
            }
        }
    }

    public static class Limit extends LogDescriptor {
        private long limit;
        private Stream logSteramDescriptor;

        public Limit(Stream stream, long j) {
            this.logSteramDescriptor = stream;
            this.limit = j;
        }

        @Override // com.samsung.android.fotaprovider.log.base.LogDescriptor
        public void shift() {
            this.logSteramDescriptor.shift();
        }

        @Override // com.samsung.android.fotaprovider.log.base.LogDescriptor
        public void println(String str) {
            this.logSteramDescriptor.println(str);
        }

        @Override // com.samsung.android.fotaprovider.log.base.LogDescriptor
        public void onBefore() {
            if (this.logSteramDescriptor.isLogExceeds(this.limit)) {
                shift();
            }
        }
    }
}
