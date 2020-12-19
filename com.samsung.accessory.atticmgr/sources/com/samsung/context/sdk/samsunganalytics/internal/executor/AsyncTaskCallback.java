package com.samsung.context.sdk.samsunganalytics.internal.executor;

public abstract class AsyncTaskCallback {
    public abstract void onFail(int i, String str, String str2, String str3);

    public abstract void onSuccess(int i, String str, String str2, String str3);
}
