package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;

public class DataBufferRef {
    protected final DataHolder mDataHolder;
    protected int mDataRow;
    private int zaln;

    public DataBufferRef(DataHolder dataHolder, int i) {
        this.mDataHolder = (DataHolder) Preconditions.checkNotNull(dataHolder);
        zag(i);
    }

    /* access modifiers changed from: protected */
    public int getDataRow() {
        return this.mDataRow;
    }

    /* access modifiers changed from: protected */
    public final void zag(int i) {
        Preconditions.checkState(i >= 0 && i < this.mDataHolder.getCount());
        this.mDataRow = i;
        this.zaln = this.mDataHolder.getWindowIndex(this.mDataRow);
    }

    public boolean isDataValid() {
        return !this.mDataHolder.isClosed();
    }

    public boolean hasColumn(String str) {
        return this.mDataHolder.hasColumn(str);
    }

    /* access modifiers changed from: protected */
    public long getLong(String str) {
        return this.mDataHolder.getLong(str, this.mDataRow, this.zaln);
    }

    /* access modifiers changed from: protected */
    public int getInteger(String str) {
        return this.mDataHolder.getInteger(str, this.mDataRow, this.zaln);
    }

    /* access modifiers changed from: protected */
    public boolean getBoolean(String str) {
        return this.mDataHolder.getBoolean(str, this.mDataRow, this.zaln);
    }

    /* access modifiers changed from: protected */
    public String getString(String str) {
        return this.mDataHolder.getString(str, this.mDataRow, this.zaln);
    }

    /* access modifiers changed from: protected */
    public float getFloat(String str) {
        return this.mDataHolder.zaa(str, this.mDataRow, this.zaln);
    }

    /* access modifiers changed from: protected */
    public double getDouble(String str) {
        return this.mDataHolder.zab(str, this.mDataRow, this.zaln);
    }

    /* access modifiers changed from: protected */
    public byte[] getByteArray(String str) {
        return this.mDataHolder.getByteArray(str, this.mDataRow, this.zaln);
    }

    /* access modifiers changed from: protected */
    public Uri parseUri(String str) {
        String string = this.mDataHolder.getString(str, this.mDataRow, this.zaln);
        if (string == null) {
            return null;
        }
        return Uri.parse(string);
    }

    /* access modifiers changed from: protected */
    public void copyToBuffer(String str, CharArrayBuffer charArrayBuffer) {
        this.mDataHolder.zaa(str, this.mDataRow, this.zaln, charArrayBuffer);
    }

    /* access modifiers changed from: protected */
    public boolean hasNull(String str) {
        return this.mDataHolder.hasNull(str, this.mDataRow, this.zaln);
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.mDataRow), Integer.valueOf(this.zaln), this.mDataHolder);
    }

    public boolean equals(Object obj) {
        if (obj instanceof DataBufferRef) {
            DataBufferRef dataBufferRef = (DataBufferRef) obj;
            if (!Objects.equal(Integer.valueOf(dataBufferRef.mDataRow), Integer.valueOf(this.mDataRow)) || !Objects.equal(Integer.valueOf(dataBufferRef.zaln), Integer.valueOf(this.zaln)) || dataBufferRef.mDataHolder != this.mDataHolder) {
                return false;
            }
            return true;
        }
        return false;
    }
}
