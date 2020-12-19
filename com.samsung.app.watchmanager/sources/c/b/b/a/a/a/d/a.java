package c.b.b.a.a.a.d;

import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import c.b.b.a.a.a.a.c;
import c.b.b.a.a.a.c.b;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public class a implements b {

    /* renamed from: a  reason: collision with root package name */
    private Map<String, String> f1731a;

    /* renamed from: b  reason: collision with root package name */
    private c.b.b.a.a.a.a.a f1732b;

    /* renamed from: c  reason: collision with root package name */
    private HttpsURLConnection f1733c = null;

    /* renamed from: d  reason: collision with root package name */
    private SharedPreferences f1734d;
    private c.b.b.a.a.a.a<Void, Boolean> e;

    public a(c.b.b.a.a.a.a.a aVar, Map<String, String> map, SharedPreferences sharedPreferences, c.b.b.a.a.a.a<Void, Boolean> aVar2) {
        this.f1732b = aVar;
        this.f1731a = map;
        this.f1734d = sharedPreferences;
        this.e = aVar2;
    }

    private void a(BufferedReader bufferedReader) {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException unused) {
                return;
            }
        }
        if (this.f1733c != null) {
            this.f1733c.disconnect();
        }
    }

    public void a(JSONObject jSONObject) {
        try {
            SharedPreferences.Editor putInt = this.f1734d.edit().putInt("oq-3g", jSONObject.getInt("oq-3g") * 1024).putInt("dq-3g", jSONObject.getInt("dq-3g") * 1024).putInt("oq-w", jSONObject.getInt("oq-w") * 1024).putInt("dq-w", jSONObject.getInt("dq-w") * 1024);
            putInt.putString("dom", "https://" + jSONObject.getString("dom")).putString("uri", jSONObject.getString("uri")).putString("bat-uri", jSONObject.getString("bat-uri")).putString("lgt", jSONObject.getString("lgt")).putInt("rint", jSONObject.getInt("rint")).putLong("policy_received_date", System.currentTimeMillis()).apply();
            c cVar = c.f1710c;
            cVar.a("https://" + jSONObject.getString("dom"));
            c.b.b.a.a.a.a.b.DLS_DIR.a(jSONObject.getString("uri"));
            c.b.b.a.a.a.a.b.DLS_DIR_BAT.a(jSONObject.getString("bat-uri"));
            c.b.b.a.a.a.i.a.c("dq-3g: " + (jSONObject.getInt("dq-3g") * 1024) + ", dq-w: " + (jSONObject.getInt("dq-w") * 1024) + ", oq-3g: " + (jSONObject.getInt("oq-3g") * 1024) + ", oq-w: " + (jSONObject.getInt("oq-w") * 1024));
        } catch (JSONException e2) {
            c.b.b.a.a.a.i.a.b("Fail to get Policy");
            c.b.b.a.a.a.i.a.c("[GetPolicyClient] " + e2.getMessage());
        }
    }

    @Override // c.b.b.a.a.a.c.b
    public int onFinish() {
        Throwable th;
        int i;
        String string;
        BufferedReader bufferedReader = null;
        try {
            if (this.f1733c.getResponseCode() != 200) {
                c.b.b.a.a.a.i.a.b("Fail to get Policy. Response code : " + this.f1733c.getResponseCode());
                i = -61;
            } else {
                i = 0;
            }
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(this.f1733c.getInputStream()));
            try {
                String readLine = bufferedReader2.readLine();
                c.b.b.a.a.a.i.a.c(readLine);
                JSONObject jSONObject = new JSONObject(readLine);
                int i2 = jSONObject.getInt("rc");
                if (i2 != 1000) {
                    c.b.b.a.a.a.i.a.b("Fail to get Policy; Invalid Message. Result code : " + i2);
                    i = -61;
                } else {
                    c.b.b.a.a.a.i.a.a("GetPolicyClient", "Get Policy Success");
                    if (TextUtils.isEmpty(this.f1734d.getString("lgt", "")) && this.e != null && (string = jSONObject.getString("lgt")) != null && string.equals("rtb")) {
                        this.e.a(true);
                    }
                    a(jSONObject);
                }
                if (this.f1733c != null) {
                    this.f1733c.disconnect();
                }
                a(bufferedReader2);
            } catch (Exception unused) {
                bufferedReader = bufferedReader2;
                try {
                    c.b.b.a.a.a.i.a.b("Fail to get Policy");
                    a(bufferedReader);
                    i = -61;
                    boolean isEmpty = TextUtils.isEmpty(this.f1734d.getString("dom", ""));
                    this.f1734d.edit().putLong("policy_received_date", System.currentTimeMillis()).apply();
                    return i;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader2 = bufferedReader;
                    a(bufferedReader2);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                a(bufferedReader2);
                throw th;
            }
        } catch (Exception unused2) {
            c.b.b.a.a.a.i.a.b("Fail to get Policy");
            a(bufferedReader);
            i = -61;
            boolean isEmpty2 = TextUtils.isEmpty(this.f1734d.getString("dom", ""));
            this.f1734d.edit().putLong("policy_received_date", System.currentTimeMillis()).apply();
            return i;
        }
        boolean isEmpty22 = TextUtils.isEmpty(this.f1734d.getString("dom", ""));
        if (i == -61 && !isEmpty22) {
            this.f1734d.edit().putLong("policy_received_date", System.currentTimeMillis()).apply();
        }
        return i;
    }

    @Override // c.b.b.a.a.a.c.b
    public void run() {
        try {
            Uri.Builder buildUpon = Uri.parse(this.f1732b.c()).buildUpon();
            for (String str : this.f1731a.keySet()) {
                buildUpon.appendQueryParameter(str, this.f1731a.get(str));
            }
            this.f1733c = (HttpsURLConnection) new URL(buildUpon.build().toString()).openConnection();
            this.f1733c.setSSLSocketFactory(c.b.b.a.a.a.e.b.a().b().getSocketFactory());
            this.f1733c.setRequestMethod(this.f1732b.b());
            this.f1733c.setConnectTimeout(3000);
        } catch (Exception unused) {
            c.b.b.a.a.a.i.a.b("Fail to get Policy");
        }
    }
}
