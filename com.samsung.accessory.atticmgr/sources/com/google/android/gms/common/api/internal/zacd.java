package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.TimeUnit;

public final class zacd<R extends Result> extends PendingResult<R> {
    private final Status mStatus;

    public zacd(Status status) {
        Preconditions.checkNotNull(status, "Status must not be null");
        Preconditions.checkArgument(!status.isSuccess(), "Status must not be success");
        this.mStatus = status;
    }

    /* access modifiers changed from: package-private */
    public final Status getStatus() {
        return this.mStatus;
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final R await() {
        throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final R await(long j, TimeUnit timeUnit) {
        throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final void cancel() {
        throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final boolean isCanceled() {
        throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final void setResultCallback(ResultCallback<? super R> resultCallback) {
        throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final void setResultCallback(ResultCallback<? super R> resultCallback, long j, TimeUnit timeUnit) {
        throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final void addStatusListener(PendingResult.StatusListener statusListener) {
        throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> resultTransform) {
        throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
    }

    @Override // com.google.android.gms.common.api.PendingResult
    public final Integer zam() {
        throw new UnsupportedOperationException("Operation not supported on PendingResults generated by ResultTransform.createFailedResult()");
    }
}
