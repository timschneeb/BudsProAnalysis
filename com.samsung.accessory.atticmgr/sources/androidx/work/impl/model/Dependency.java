package androidx.work.impl.model;

public class Dependency {
    public final String prerequisiteId;
    public final String workSpecId;

    public Dependency(String str, String str2) {
        this.workSpecId = str;
        this.prerequisiteId = str2;
    }
}
