package c.b.b.a.a.a.i;

import java.util.Map;

public class b<K, V> {

    public enum a {
        ONE_DEPTH("\u0002", "\u0003"),
        TWO_DEPTH("\u0004", "\u0005"),
        THREE_DEPTH("\u0006", "\u0007");
        
        private String e;
        private String f;

        private a(String str, String str2) {
            this.e = str;
            this.f = str2;
        }

        public String b() {
            return this.e;
        }

        public String c() {
            return this.f;
        }
    }

    public String a(Map<K, V> map, a aVar) {
        String str = null;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            str = ((str == null ? entry.getKey().toString() : (str + aVar.b()) + ((Object) entry.getKey())) + aVar.c()) + ((Object) entry.getValue());
        }
        return str;
    }
}
