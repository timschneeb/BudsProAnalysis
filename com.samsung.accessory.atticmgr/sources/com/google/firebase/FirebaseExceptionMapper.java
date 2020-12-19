package com.google.firebase;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;

public class FirebaseExceptionMapper implements StatusExceptionMapper {
    @Override // com.google.android.gms.common.api.internal.StatusExceptionMapper
    public Exception getException(Status status) {
        if (status.getStatusCode() == 8) {
            return new FirebaseException(status.zzg());
        }
        return new FirebaseApiNotAvailableException(status.zzg());
    }
}
