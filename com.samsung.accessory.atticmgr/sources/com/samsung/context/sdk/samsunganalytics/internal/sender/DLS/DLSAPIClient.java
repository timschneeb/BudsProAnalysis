package com.samsung.context.sdk.samsunganalytics.internal.sender.DLS;

import android.net.Uri;
import android.text.TextUtils;
import com.accessorydm.interfaces.XDBInterface;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.context.sdk.samsunganalytics.internal.connection.API;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.security.CertificateManager;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
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

public class DLSAPIClient implements AsyncTaskClient {
    private static final int DEFAULT_TIMEOUT_IN_MILLISECONDS = 3000;
    private static final API REALTIME_API = API.SEND_LOG;
    private static final API RTB_API = API.SEND_BUFFERED_LOG;
    private AsyncTaskCallback asyncTaskCallback;
    private HttpsURLConnection conn = null;
    private Boolean isBatch = false;
    private LogType logType;
    private Queue<SimpleLog> logs;
    private SimpleLog simpleLog;
    private String trid;

    public DLSAPIClient(SimpleLog simpleLog2, String str, AsyncTaskCallback asyncTaskCallback2) {
        this.simpleLog = simpleLog2;
        this.trid = str;
        this.asyncTaskCallback = asyncTaskCallback2;
        this.logType = simpleLog2.getType();
    }

    public DLSAPIClient(LogType logType2, Queue<SimpleLog> queue, String str, AsyncTaskCallback asyncTaskCallback2) {
        this.logs = queue;
        this.trid = str;
        this.asyncTaskCallback = asyncTaskCallback2;
        this.isBatch = true;
        this.logType = logType2;
    }

    private String getBody() {
        if (!this.isBatch.booleanValue()) {
            return this.simpleLog.getData();
        }
        Iterator<SimpleLog> it = this.logs.iterator();
        String data = it.next().getData();
        while (it.hasNext()) {
            data = data + Utils.LOG_DELIMITER + it.next().getData();
        }
        return data;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public void run() {
        BufferedOutputStream bufferedOutputStream;
        try {
            API api = this.isBatch.booleanValue() ? RTB_API : REALTIME_API;
            Uri.Builder buildUpon = Uri.parse(api.getUrl()).buildUpon();
            String format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(new Date());
            Uri.Builder appendQueryParameter = buildUpon.appendQueryParameter("ts", format).appendQueryParameter("type", this.logType.getAbbrev()).appendQueryParameter("tid", this.trid);
            appendQueryParameter.appendQueryParameter("hc", Validation.sha256(this.trid + format + Validation.SALT));
            this.conn = (HttpsURLConnection) new URL(buildUpon.build().toString()).openConnection();
            this.conn.setSSLSocketFactory(CertificateManager.getInstance().getSSLContext().getSocketFactory());
            this.conn.setRequestMethod(api.getMethod());
            this.conn.addRequestProperty("Content-Encoding", this.isBatch.booleanValue() ? "gzip" : XDBInterface.XDM_SQL_DB_PROFILELIST_UICRESULTKEEP_TEXT);
            this.conn.setConnectTimeout(3000);
            String body = getBody();
            if (!TextUtils.isEmpty(body)) {
                this.conn.setDoOutput(true);
                if (this.isBatch.booleanValue()) {
                    bufferedOutputStream = new BufferedOutputStream(new GZIPOutputStream(this.conn.getOutputStream()));
                } else {
                    bufferedOutputStream = new BufferedOutputStream(this.conn.getOutputStream());
                }
                bufferedOutputStream.write(body.getBytes());
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            }
            Debug.LogENG("[DLS Client] Send to DLS : " + body);
        } catch (Exception e) {
            Debug.LogE("[DLS Client] Send fail.");
            Debug.LogENG("[DLS Client] " + e.getMessage());
        }
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public int onFinish() {
        BufferedReader bufferedReader;
        Throwable th;
        int i;
        Exception e;
        try {
            int responseCode = this.conn.getResponseCode();
            bufferedReader = new BufferedReader(new InputStreamReader(this.conn.getInputStream()));
            try {
                String string = new JSONObject(bufferedReader.readLine()).getString("rc");
                if (responseCode != 200 || !string.equalsIgnoreCase(SA.Event.UP_BUTTON)) {
                    i = -7;
                    Debug.LogD("[DLS Sender] send result fail : " + responseCode + " " + string);
                } else {
                    i = 1;
                    Debug.LogD("[DLS Sender] send result success : " + responseCode + " " + string);
                }
                callback(responseCode, string);
            } catch (Exception e2) {
                e = e2;
                try {
                    Debug.LogE("[DLS Client] Send fail.");
                    Debug.LogENG("[DLS Client] " + e.getMessage());
                    i = -41;
                    callback(0, "");
                    cleanUp(bufferedReader);
                    return i;
                } catch (Throwable th2) {
                    th = th2;
                    cleanUp(bufferedReader);
                    throw th;
                }
            }
        } catch (Exception e3) {
            bufferedReader = null;
            e = e3;
            Debug.LogE("[DLS Client] Send fail.");
            Debug.LogENG("[DLS Client] " + e.getMessage());
            i = -41;
            callback(0, "");
            cleanUp(bufferedReader);
            return i;
        } catch (Throwable th3) {
            bufferedReader = null;
            th = th3;
            cleanUp(bufferedReader);
            throw th;
        }
        cleanUp(bufferedReader);
        return i;
    }

    private void callback(int i, String str) {
        if (this.asyncTaskCallback != null) {
            if (i == 200 && str.equalsIgnoreCase(SA.Event.UP_BUTTON)) {
                return;
            }
            if (this.isBatch.booleanValue()) {
                while (!this.logs.isEmpty()) {
                    SimpleLog poll = this.logs.poll();
                    AsyncTaskCallback asyncTaskCallback2 = this.asyncTaskCallback;
                    asyncTaskCallback2.onFail(i, poll.getTimestamp() + "", poll.getData(), poll.getType().getAbbrev());
                }
                return;
            }
            AsyncTaskCallback asyncTaskCallback3 = this.asyncTaskCallback;
            asyncTaskCallback3.onFail(i, this.simpleLog.getTimestamp() + "", this.simpleLog.getData(), this.simpleLog.getType().getAbbrev());
        }
    }

    private void cleanUp(BufferedReader bufferedReader) {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException unused) {
                return;
            }
        }
        if (this.conn != null) {
            this.conn.disconnect();
        }
    }
}
