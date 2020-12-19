package androidx.work.impl.model;

public interface SystemIdInfoDao {
    SystemIdInfo getSystemIdInfo(String str);

    void insertSystemIdInfo(SystemIdInfo systemIdInfo);

    void removeSystemIdInfo(String str);
}
