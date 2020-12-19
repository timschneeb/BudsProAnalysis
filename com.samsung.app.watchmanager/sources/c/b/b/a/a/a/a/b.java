package c.b.b.a.a.a.a;

public enum b {
    DEVICE_CONTROLLER_DIR("/v1/quotas"),
    DATA_DELETE("/app/delete"),
    DLS_DIR(""),
    DLS_DIR_BAT("");
    
    String f;

    private b(String str) {
        this.f = str;
    }

    public void a(String str) {
        this.f = str;
    }

    public String b() {
        return this.f;
    }
}
