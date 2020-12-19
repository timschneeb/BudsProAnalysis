package com.accessorydm.tp.urlconnect;

import android.text.TextUtils;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.exception.http.ExceptionHttpContent;
import com.accessorydm.exception.http.ExceptionHttpHeader;
import com.accessorydm.exception.http.ExceptionHttpNetwork;
import com.accessorydm.exception.http.ExceptionHttpReceive;
import com.accessorydm.exception.http.ExceptionHttpSend;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XTPInterface;
import com.accessorydm.tp.XTPHttpObj;
import com.accessorydm.tp.XTPNetworkTimer;
import com.samsung.android.fotaprovider.log.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class HttpURLConnectionAdapter implements HttpNetworkInterface, XTPInterface {
    static /* synthetic */ boolean lambda$createHttpUrlConnection$0(String str, SSLSession sSLSession) {
        return true;
    }

    public HttpURLConnection createHttpUrlConnection(int i) {
        HttpsURLConnection httpsURLConnection;
        HttpURLConnection httpURLConnection = null;
        try {
            String xdbGetServerUrl = XDB.xdbGetServerUrl(i);
            URL url = new URL(xdbGetServerUrl);
            if (xdbGetServerUrl.contains(HttpNetworkInterface.XTP_NETWORK_TYPE_HTTPS)) {
                Log.I("createHttpUrlConnection https");
                HttpsURLConnection httpsURLConnection2 = (HttpsURLConnection) url.openConnection();
                httpsURLConnection2.setHostnameVerifier($$Lambda$HttpURLConnectionAdapter$hWffn_OuVX7HfMg2LwVHCnB9ugk.INSTANCE);
                httpsURLConnection2.setSSLSocketFactory(getNetworkTrustInfo());
                httpsURLConnection = httpsURLConnection2;
            } else {
                Log.I("createHttpUrlConnection http");
                httpsURLConnection = (HttpURLConnection) url.openConnection();
            }
            httpsURLConnection.setConnectTimeout(60000);
            httpsURLConnection.setReadTimeout(60000);
            return httpsURLConnection;
        } catch (Exception unused) {
            if (0 != 0) {
                httpURLConnection.disconnect();
            }
            throw new ExceptionHttpNetwork("HttpUrlConnection Network Error!!!");
        }
    }

    public void httpUrlConnect(HttpURLConnection httpURLConnection, XTPNetworkTimer xTPNetworkTimer) {
        try {
            httpURLConnection.connect();
            xTPNetworkTimer.networkEndTimer();
        } catch (IOException unused) {
            throw new ExceptionHttpSend("connectHttpUrlConnection Connect Fail");
        }
    }

    public void disconnectHttpUrlConnection(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001c, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001d, code lost:
        if (r2 != null) goto L_0x001f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0023, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0024, code lost:
        r3.addSuppressed(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0027, code lost:
        throw r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void httpUrlSendData(java.net.HttpURLConnection r2, byte[] r3, com.accessorydm.tp.XTPNetworkTimer r4) {
        /*
            r1 = this;
            r0 = 1
            r2.setDoInput(r0)
            r2.setDoOutput(r0)
            java.io.OutputStream r2 = r2.getOutputStream()     // Catch:{ IOException -> 0x0028 }
            r2.write(r3)     // Catch:{ all -> 0x001a }
            r2.flush()     // Catch:{ all -> 0x001a }
            if (r2 == 0) goto L_0x0016
            r2.close()
        L_0x0016:
            r4.networkEndTimer()
            return
        L_0x001a:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x001c }
        L_0x001c:
            r4 = move-exception
            if (r2 == 0) goto L_0x0027
            r2.close()     // Catch:{ all -> 0x0023 }
            goto L_0x0027
        L_0x0023:
            r2 = move-exception
            r3.addSuppressed(r2)
        L_0x0027:
            throw r4
        L_0x0028:
            com.accessorydm.exception.http.ExceptionHttpSend r2 = new com.accessorydm.exception.http.ExceptionHttpSend
            java.lang.String r3 = "httpUrlSendData Fail"
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.tp.urlconnect.HttpURLConnectionAdapter.httpUrlSendData(java.net.HttpURLConnection, byte[], com.accessorydm.tp.XTPNetworkTimer):void");
    }

    public void httpUrlReceiveData(HttpURLConnection httpURLConnection, XTPHttpObj xTPHttpObj, XTPNetworkTimer xTPNetworkTimer) {
        try {
            xTPHttpObj.nHttpReturnStatusValue = httpURLConnection.getResponseCode();
            xTPNetworkTimer.networkEndTimer();
            if (200 > xTPHttpObj.nHttpReturnStatusValue || xTPHttpObj.nHttpReturnStatusValue >= 300) {
                throw new ExceptionHttpHeader("httpUrlReceiveData Header Error");
            }
            int receivedHeaderData = receivedHeaderData(xTPHttpObj, httpURLConnection);
            httpHeaderResponseLog(httpURLConnection);
            if (receivedHeaderData != 0) {
                throw new ExceptionHttpContent("httpUrlReceiveData Content Type Error");
            }
        } catch (IOException unused) {
            throw new ExceptionHttpReceive("httpUrlReceiveData IO Error");
        } catch (ExceptionHttpReceive unused2) {
            throw new ExceptionHttpReceive("httpUrlReceiveData Error");
        }
    }

    public byte[] httpUrlReceiveDeltaBodyData(InputStream inputStream, int i) {
        try {
            return getDeltaFileStream(inputStream, i);
        } catch (ExceptionHttpReceive unused) {
            throw new ExceptionHttpReceive("httpUrlReceiveDeltaBodyData Fail");
        }
    }

    private byte[] getDeltaFileStream(InputStream inputStream, int i) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[i];
        try {
            int read = inputStream.read(bArr);
            if (read > 0) {
                byteArrayOutputStream.write(bArr, 0, read);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException unused) {
            throw new ExceptionHttpReceive("getDeltaFileStream Fail");
        }
    }

    public byte[] httpUrlReceiveBodyData(InputStream inputStream, int i) {
        try {
            return getDataStream(inputStream, i);
        } catch (ExceptionHttpReceive unused) {
            throw new ExceptionHttpReceive("httpUrlReceiveBodyData Fail");
        }
    }

    private byte[] getDataStream(InputStream inputStream, int i) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[i];
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read <= 0) {
                    return byteArrayOutputStream.toByteArray();
                }
                byteArrayOutputStream.write(bArr, 0, read);
            } catch (IOException unused) {
                throw new ExceptionHttpReceive("getDataStream Fail");
            }
        }
    }

    public int sendMakeHeader(XTPHttpObj xTPHttpObj, HttpURLConnection httpURLConnection, int i) {
        Log.I("");
        if (TextUtils.isEmpty(xTPHttpObj.m_szHttpOpenMode)) {
            return -3;
        }
        try {
            httpURLConnection.setRequestMethod(xTPHttpObj.m_szHttpOpenMode);
            httpURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_CACHE_CONTROL, HttpNetworkInterface.XTP_HTTP_CACHE_CONTROL_MODE);
            if (!TextUtils.isEmpty(xTPHttpObj.m_szHttpConnection)) {
                httpURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_CONNECTION, xTPHttpObj.m_szHttpConnection);
            }
            if (!TextUtils.isEmpty(xTPHttpObj.m_szHttpUserAgent)) {
                httpURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_USER_AGENT, xTPHttpObj.m_szHttpUserAgent);
            }
            if (!TextUtils.isEmpty(xTPHttpObj.m_szHttpAccept)) {
                httpURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_ACCEPT, xTPHttpObj.m_szHttpAccept);
            }
            httpURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_ACCEPT_LANGUAGE, HttpNetworkInterface.XTP_HTTP_LANGUAGE);
            httpURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_ACCEPT_CHARSET, HttpNetworkInterface.XTP_HTTP_UTF8);
            if (!TextUtils.isEmpty(xTPHttpObj.m_szServerAddr)) {
                httpURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_HOST, xTPHttpObj.m_szServerAddr + ":" + xTPHttpObj.nServerPort);
            }
            if (!TextUtils.isEmpty(xTPHttpObj.m_szHttpMimeType)) {
                httpURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_CONTENT_TYPE, xTPHttpObj.m_szHttpMimeType);
            }
            if (!TextUtils.isEmpty(xTPHttpObj.m_szContentRange)) {
                httpURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_RANGE, "bytes=" + xTPHttpObj.m_szContentRange + "-");
            }
            httpURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_CONTENT_LENGTH, String.valueOf(i));
            if (TextUtils.isEmpty(xTPHttpObj.pHmacData)) {
                return 0;
            }
            httpURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_X_SYNCML_HMAC, xTPHttpObj.pHmacData);
            return 0;
        } catch (Exception e) {
            Log.E(e.toString());
            return -3;
        }
    }

    private int receivedHeaderData(XTPHttpObj xTPHttpObj, HttpURLConnection httpURLConnection) {
        Log.I("");
        try {
            URL url = httpURLConnection.getURL();
            Log.H("URL: " + url.toString());
            xTPHttpObj.nHttpReturnStatusValue = httpURLConnection.getResponseCode();
            Log.H("Response Result Code = " + xTPHttpObj.nHttpReturnStatusValue);
            if (httpURLConnection.getHeaderField(HttpNetworkInterface.XTP_HTTP_CONTENT_LENGTH) != null) {
                xTPHttpObj.nContentLength = Long.parseLong(httpURLConnection.getHeaderField(HttpNetworkInterface.XTP_HTTP_CONTENT_LENGTH));
            }
            xTPHttpObj.m_szHttpConnection = httpURLConnection.getHeaderField(HttpNetworkInterface.XTP_HTTP_CONNECTION);
            if (!TextUtils.isEmpty(xTPHttpObj.m_szHttpConnection) && xTPHttpObj.m_szHttpConnection.equalsIgnoreCase(HttpNetworkInterface.XTP_HTTP_CLOSE)) {
                Log.I("_______HTTP_CONNECTION_TYPE_CLOSE MODE_______");
                xTPHttpObj.nHttpConnection = 1;
            } else if (TextUtils.isEmpty(xTPHttpObj.m_szHttpConnection) || !xTPHttpObj.m_szHttpConnection.equalsIgnoreCase(HttpNetworkInterface.XTP_HTTP_KEEPALIVE)) {
                Log.I("_______HTTP_CONNECTION_TYPE_NONE MODE_______");
                xTPHttpObj.nHttpConnection = 2;
                xTPHttpObj.m_szHttpConnection = HttpNetworkInterface.XTP_HTTP_KEEPALIVE;
            } else {
                xTPHttpObj.nHttpConnection = 2;
            }
            String headerField = httpURLConnection.getHeaderField(HttpNetworkInterface.XTP_HTTP_X_SYNCML_HMAC);
            if (!TextUtils.isEmpty(headerField)) {
                xTPHttpObj.pHmacData = headerField;
            } else {
                xTPHttpObj.pHmacData = null;
                Log.H("szHmac null");
            }
            String headerField2 = httpURLConnection.getHeaderField(HttpNetworkInterface.XTP_HTTP_TRANSFER_ENCODING);
            if (TextUtils.isEmpty(headerField2)) {
                xTPHttpObj.nTransferCoding = 0;
            } else if (headerField2.equalsIgnoreCase("chunked")) {
                xTPHttpObj.nTransferCoding = 1;
            } else {
                xTPHttpObj.nTransferCoding = 0;
            }
            String headerField3 = httpURLConnection.getHeaderField(HttpNetworkInterface.XTP_HTTP_CONTENT_RANGE);
            if (!TextUtils.isEmpty(headerField3)) {
                xTPHttpObj.m_szContentRange = headerField3;
                if (xTPHttpObj.nContentLength == 0 && xTPHttpObj.nTransferCoding != 1) {
                    Log.I("Content-length 0, Content-Range Use");
                    xTPHttpObj.nContentLength = (long) xtpAdpHttpPsrGetContentLengthByRange(headerField3);
                }
            } else {
                xTPHttpObj.m_szContentRange = null;
            }
            String headerField4 = httpURLConnection.getHeaderField(HttpNetworkInterface.XTP_HTTP_CONTENT_TYPE);
            Log.H("chunked = " + headerField2);
            if (!XDMDmUtils.getInstance().XDM_VALIDATION_CHECK) {
                Log.I("content type check skip");
            } else if (!TextUtils.isEmpty(headerField4) && XDBFumoAdp.xdbGetFUMOStatus() == 30 && !XDMInterface.SYNCML_MIME_TYPE_DOWNLOAD_TYPE.equals(headerField4.toLowerCase(Locale.US)) && xTPHttpObj.nHttpReturnStatusValue >= 200 && xTPHttpObj.nHttpReturnStatusValue < 300) {
                Log.E("content type miss match");
                return -10;
            }
            return 0;
        } catch (Exception e) {
            Log.E(e.toString());
            throw new ExceptionHttpHeader("receivedHeaderData URL Error");
        }
    }

    public void httpHeaderRequestLog(HttpURLConnection httpURLConnection) {
        StringBuilder sb = new StringBuilder();
        sb.append(httpURLConnection.getRequestMethod());
        sb.append(HttpNetworkInterface.XTP_CRLF_STRING);
        for (Map.Entry<String, List<String>> entry : httpURLConnection.getRequestProperties().entrySet()) {
            sb.append(entry.getKey());
            sb.append(": ");
            for (String str : entry.getValue()) {
                sb.append(str);
                sb.append(HttpNetworkInterface.XTP_CRLF_STRING);
            }
        }
        sb.append(HttpNetworkInterface.XTP_CRLF_STRING);
        Log.H("\r\n [_____Make Header_____]\r\n" + ((Object) sb));
    }

    private void httpHeaderResponseLog(HttpURLConnection httpURLConnection) {
        StringBuilder sb = new StringBuilder("");
        for (Map.Entry<String, List<String>> entry : httpURLConnection.getHeaderFields().entrySet()) {
            sb.append(entry.getKey());
            sb.append(": ");
            for (String str : entry.getValue()) {
                sb.append(str);
                sb.append(HttpNetworkInterface.XTP_CRLF_STRING);
            }
        }
        sb.append(HttpNetworkInterface.XTP_CRLF_STRING);
        Log.H("\r\n [_____Receive Header_____]\r\n" + sb.toString());
    }

    private SSLSocketFactory getNetworkTrustInfo() {
        try {
            SSLContext instance = SSLContext.getInstance("TLS");
            instance.init(null, new TrustManager[]{new XTPTrustManager(null)}, new SecureRandom());
            instance.getServerSessionContext().setSessionTimeout(60000);
            return instance.getSocketFactory();
        } catch (GeneralSecurityException e) {
            Log.printStackTrace(e);
            return null;
        }
    }

    public static class XTPTrustManager implements X509TrustManager {
        private X509TrustManager trustManager;

        XTPTrustManager(KeyStore keyStore) {
            try {
                TrustManagerFactory instance = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                instance.init(keyStore);
                this.trustManager = getX509TrustManager(instance);
                if (this.trustManager == null) {
                    Log.E("X509TrustManager is null");
                    throw new IllegalStateException("X509TrustManager is null");
                }
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            if (HttpNetworkInfo.getInstance().isSSLCheck()) {
                this.trustManager.checkClientTrusted(x509CertificateArr, str);
            }
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            if (HttpNetworkInfo.getInstance().isSSLCheck()) {
                this.trustManager.checkServerTrusted(x509CertificateArr, str);
            }
        }

        public X509Certificate[] getAcceptedIssuers() {
            return this.trustManager.getAcceptedIssuers();
        }

        private X509TrustManager getX509TrustManager(TrustManagerFactory trustManagerFactory) {
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            for (TrustManager trustManager2 : trustManagers) {
                if (trustManager2 instanceof X509TrustManager) {
                    return (X509TrustManager) trustManager2;
                }
            }
            return null;
        }
    }

    private int xtpAdpHttpPsrGetContentLengthByRange(String str) {
        int length;
        int intValue;
        int indexOf = str.toLowerCase(Locale.US).indexOf("bytes ");
        if (indexOf < 0) {
            return 0;
        }
        String trim = str.substring(indexOf + 6).trim();
        if (TextUtils.isEmpty(trim) || (length = trim.length()) == 0) {
            return 0;
        }
        int i = 0;
        while (true) {
            if (i >= length) {
                i = 0;
                break;
            } else if (trim.charAt(i) == '/') {
                break;
            } else {
                i++;
            }
        }
        if (i != 0) {
            trim = trim.substring(0, i);
        }
        String[] split = trim.split("-");
        if (TextUtils.isEmpty(split[0]) || TextUtils.isEmpty(split[1]) || (intValue = Integer.valueOf(split[1]).intValue() - Integer.valueOf(split[0]).intValue()) <= 0) {
            return 0;
        }
        return intValue + 1;
    }
}
