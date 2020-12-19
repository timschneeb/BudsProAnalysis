package c.b.b.a.a.a.f.b;

import android.net.Uri;
import android.text.TextUtils;
import c.b.b.a.a.a.c.b;
import c.b.b.a.a.a.d.d;
import c.b.b.a.a.a.f.c;
import c.b.b.a.a.a.f.e;
import com.samsung.android.app.twatchmanager.model.GroupInfo;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Queue;
import java.util.zip.GZIPOutputStream;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;

public class a implements b {

    /* renamed from: a  reason: collision with root package name */
    private static final c.b.b.a.a.a.a.a f1758a = c.b.b.a.a.a.a.a.SEND_LOG;

    /* renamed from: b  reason: collision with root package name */
    private static final c.b.b.a.a.a.a.a f1759b = c.b.b.a.a.a.a.a.SEND_BUFFERED_LOG;

    /* renamed from: c  reason: collision with root package name */
    private Queue<e> f1760c;

    /* renamed from: d  reason: collision with root package name */
    private e f1761d;
    private c e;
    private String f;
    private HttpsURLConnection g = null;
    private c.b.b.a.a.a.c.a h;
    private Boolean i = false;

    public a(c cVar, Queue<e> queue, String str, c.b.b.a.a.a.c.a aVar) {
        this.f1760c = queue;
        this.f = str;
        this.h = aVar;
        this.i = true;
        this.e = cVar;
    }

    public a(e eVar, String str, c.b.b.a.a.a.c.a aVar) {
        this.f1761d = eVar;
        this.f = str;
        this.h = aVar;
        this.e = eVar.d();
    }

    private String a() {
        if (!this.i.booleanValue()) {
            return this.f1761d.a();
        }
        Iterator<e> it = this.f1760c.iterator();
        String a2 = it.next().a();
        while (it.hasNext()) {
            a2 = a2 + "\u000e" + it.next().a();
        }
        return a2;
    }

    private void a(int i2, String str) {
        if (this.h != null) {
            if (i2 == 200 && str.equalsIgnoreCase("1000")) {
                return;
            }
            if (this.i.booleanValue()) {
                while (!this.f1760c.isEmpty()) {
                    e poll = this.f1760c.poll();
                    c.b.b.a.a.a.c.a aVar = this.h;
                    aVar.a(i2, poll.c() + "", poll.a(), poll.d().b());
                }
                return;
            }
            c.b.b.a.a.a.c.a aVar2 = this.h;
            aVar2.a(i2, this.f1761d.c() + "", this.f1761d.a(), this.f1761d.d().b());
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
        if (this.g != null) {
            this.g.disconnect();
        }
    }

    @Override // c.b.b.a.a.a.c.b
    public int onFinish() {
        BufferedReader bufferedReader;
        Throwable th;
        int i2;
        Exception e2;
        String str;
        try {
            int responseCode = this.g.getResponseCode();
            bufferedReader = new BufferedReader(new InputStreamReader(this.g.getInputStream()));
            try {
                String string = new JSONObject(bufferedReader.readLine()).getString("rc");
                if (responseCode != 200 || !string.equalsIgnoreCase("1000")) {
                    i2 = -7;
                    str = "[DLS Sender] send result fail : " + responseCode + " " + string;
                } else {
                    i2 = 1;
                    str = "[DLS Sender] send result success : " + responseCode + " " + string;
                }
                c.b.b.a.a.a.i.a.a(str);
                a(responseCode, string);
            } catch (Exception e3) {
                e2 = e3;
                try {
                    c.b.b.a.a.a.i.a.b("[DLS Client] Send fail.");
                    c.b.b.a.a.a.i.a.c("[DLS Client] " + e2.getMessage());
                    i2 = -41;
                    a(0, "");
                    a(bufferedReader);
                    return i2;
                } catch (Throwable th2) {
                    th = th2;
                    a(bufferedReader);
                    throw th;
                }
            }
        } catch (Exception e4) {
            bufferedReader = null;
            e2 = e4;
            c.b.b.a.a.a.i.a.b("[DLS Client] Send fail.");
            c.b.b.a.a.a.i.a.c("[DLS Client] " + e2.getMessage());
            i2 = -41;
            a(0, "");
            a(bufferedReader);
            return i2;
        } catch (Throwable th3) {
            bufferedReader = null;
            th = th3;
            a(bufferedReader);
            throw th;
        }
        a(bufferedReader);
        return i2;
    }

    @Override // c.b.b.a.a.a.c.b
    public void run() {
        try {
            c.b.b.a.a.a.a.a aVar = this.i.booleanValue() ? f1759b : f1758a;
            Uri.Builder buildUpon = Uri.parse(aVar.c()).buildUpon();
            String format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(new Date());
            Uri.Builder appendQueryParameter = buildUpon.appendQueryParameter("ts", format).appendQueryParameter(GroupInfo.ImageInfo.ATTR_TYPE, this.e.b()).appendQueryParameter("tid", this.f);
            appendQueryParameter.appendQueryParameter("hc", d.a(this.f + format + d.f1738a));
            this.g = (HttpsURLConnection) new URL(buildUpon.build().toString()).openConnection();
            this.g.setSSLSocketFactory(c.b.b.a.a.a.e.b.a().b().getSocketFactory());
            this.g.setRequestMethod(aVar.b());
            this.g.addRequestProperty("Content-Encoding", this.i.booleanValue() ? "gzip" : "text");
            this.g.setConnectTimeout(3000);
            String a2 = a();
            if (!TextUtils.isEmpty(a2)) {
                this.g.setDoOutput(true);
                BufferedOutputStream bufferedOutputStream = this.i.booleanValue() ? new BufferedOutputStream(new GZIPOutputStream(this.g.getOutputStream())) : new BufferedOutputStream(this.g.getOutputStream());
                bufferedOutputStream.write(a2.getBytes());
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
            c.b.b.a.a.a.i.a.c("[DLS Client] Send to DLS : " + a2);
        } catch (Exception e2) {
            c.b.b.a.a.a.i.a.b("[DLS Client] Send fail.");
            c.b.b.a.a.a.i.a.c("[DLS Client] " + e2.getMessage());
        }
    }
}
