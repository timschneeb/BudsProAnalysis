package c.b.b.a.a.a.a;

public enum a {
    DATA_DELETE(c.f1708a, b.DATA_DELETE, d.POST),
    GET_POLICY(c.f1709b, b.DEVICE_CONTROLLER_DIR, d.GET),
    SEND_LOG(c.f1710c, b.DLS_DIR, d.POST),
    SEND_BUFFERED_LOG(c.f1710c, b.DLS_DIR_BAT, d.POST);
    
    c f;
    b g;
    d h;

    private a(c cVar, b bVar, d dVar) {
        this.f = cVar;
        this.g = bVar;
        this.h = dVar;
    }

    public String b() {
        return this.h.b();
    }

    public String c() {
        return this.f.b() + this.g.b();
    }
}
