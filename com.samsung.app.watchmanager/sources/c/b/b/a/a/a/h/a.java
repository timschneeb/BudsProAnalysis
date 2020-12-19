package c.b.b.a.a.a.h;

import android.net.Uri;
import android.text.TextUtils;
import c.b.b.a.a.a.c.b;
import c.b.b.a.a.a.d.d;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public class a implements b {

    /* renamed from: a  reason: collision with root package name */
    private final c.b.b.a.a.a.a.a f1801a = c.b.b.a.a.a.a.a.DATA_DELETE;

    /* renamed from: b  reason: collision with root package name */
    private HttpsURLConnection f1802b = null;

    /* renamed from: c  reason: collision with root package name */
    private String f1803c = "";

    /* renamed from: d  reason: collision with root package name */
    private String f1804d = "";
    private long e;
    private c.b.b.a.a.a.c.a f;

    public a(String str, String str2, long j, c.b.b.a.a.a.c.a aVar) {
        this.f1803c = str;
        this.f1804d = str2;
        this.e = j;
        this.f = aVar;
    }

    private String a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("tid", this.f1803c);
            jSONObject.put("lid", this.f1804d);
            jSONObject.put("ts", this.e);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }

    private void a(int i, String str) {
        if (this.f != null) {
            if (i != 200 || !str.equalsIgnoreCase("1000")) {
                this.f.a(i, str, "", "");
            } else {
                this.f.b(0, "", "", "");
            }
        }
    }

    private void a(BufferedReader bufferedReader) {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException unused) {
                return;
            }
        }
        if (this.f1802b != null) {
            this.f1802b.disconnect();
        }
    }

    @Override // c.b.b.a.a.a.c.b
    public int onFinish() {
        String str;
        BufferedReader bufferedReader = null;
        try {
            int responseCode = this.f1802b.getResponseCode();
            bufferedReader = responseCode >= 400 ? new BufferedReader(new InputStreamReader(this.f1802b.getErrorStream())) : new BufferedReader(new InputStreamReader(this.f1802b.getInputStream()));
            String string = new JSONObject(bufferedReader.readLine()).getString("rc");
            if (responseCode != 200 || !string.equalsIgnoreCase("1000")) {
                str = "Fail : " + responseCode + " " + string;
            } else {
                str = "Success : " + responseCode + " " + string;
            }
            c.b.b.a.a.a.i.a.c(str);
            a(responseCode, string);
        } catch (Exception unused) {
            a(0, "");
        } catch (Throwable th) {
            a(null);
            throw th;
        }
        a(bufferedReader);
        return 0;
    }

    @Override // c.b.b.a.a.a.c.b
    public void run() {
        try {
            Uri.Builder buildUpon = Uri.parse(this.f1801a.c()).buildUpon();
            String format = SimpleDateFormat.getTimeInstance(2).format(new Date());
            Uri.Builder appendQueryParameter = buildUpon.appendQueryParameter("ts", format);
            appendQueryParameter.appendQueryParameter("hc", d.a(format + d.f1738a));
            this.f1802b = (HttpsURLConnection) new URL(buildUpon.build().toString()).openConnection();
            this.f1802b.setSSLSocketFactory(c.b.b.a.a.a.e.b.a().b().getSocketFactory());
            this.f1802b.setRequestMethod(this.f1801a.b());
            this.f1802b.setConnectTimeout(3000);
            this.f1802b.setRequestProperty("Content-Type", "application/json");
            String a2 = a();
            if (!TextUtils.isEmpty(a2)) {
                this.f1802b.setDoOutput(true);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.f1802b.getOutputStream());
                bufferedOutputStream.write(a2.getBytes());
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
        } catch (Exception unused) {
        }
    }
}
