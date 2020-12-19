package com.samsung.android.fotaagent.network.action;

import com.accessorydm.db.file.XDBPollingAdp;
import com.samsung.android.fotaagent.network.rest.RestResponse;
import com.samsung.android.fotaagent.polling.PollingInfo;
import com.samsung.android.fotaprovider.log.Log;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class PollingResult extends NetworkResult {
    private static final String TAG_HEART_BEAT_PERIOD = "CycleOfDeviceHeartbeat";
    private static final String TAG_HEART_BEAT_URL = "ServiceURL";
    private static final String TAG_PERIOD = "period";
    private static final String TAG_PERIOD_UNIT = "periodUnit";
    private static final String TAG_RANGE = "range";
    private static final String TAG_TIME = "time";
    private static final String TAG_URL = "url";
    private ArrayList<String> mVersionList = null;

    PollingResult(RestResponse restResponse) {
        super(restResponse);
        parseResponseIfPossible();
    }

    @Override // com.samsung.android.fotaagent.network.action.NetworkResult
    public boolean needToRetry() {
        return getErrorType() == 0 || getErrorType() == 510 || getErrorType() == 500;
    }

    @Override // com.samsung.android.fotaagent.network.action.NetworkResult
    public void parse(Element element) {
        updateVersionList(element);
        updatePollingInfo(element);
    }

    private void updateVersionList(Element element) {
        NodeList elementsByTagName = element.getElementsByTagName("value");
        if (existsNode(elementsByTagName)) {
            this.mVersionList = new ArrayList<>();
            for (int i = 0; i < elementsByTagName.getLength(); i++) {
                this.mVersionList.add(elementsByTagName.item(i).getTextContent());
            }
        }
    }

    private void updatePollingInfo(Element element) {
        NodeList elementsByTagName = element.getElementsByTagName("url");
        NodeList elementsByTagName2 = element.getElementsByTagName("period");
        NodeList elementsByTagName3 = element.getElementsByTagName("time");
        NodeList elementsByTagName4 = element.getElementsByTagName("range");
        if (existsNode(elementsByTagName) && existsNode(elementsByTagName2) && existsNode(elementsByTagName3) && existsNode(elementsByTagName4)) {
            PollingInfo xdbGetPollingInfo = XDBPollingAdp.xdbGetPollingInfo();
            xdbGetPollingInfo.setPreUrl(elementsByTagName.item(0).getTextContent());
            xdbGetPollingInfo.setPeriod(Integer.parseInt(elementsByTagName2.item(0).getTextContent()));
            xdbGetPollingInfo.setTime(Integer.parseInt(elementsByTagName3.item(0).getTextContent()));
            xdbGetPollingInfo.setRange(Integer.parseInt(elementsByTagName4.item(0).getTextContent()));
            NodeList elementsByTagName5 = element.getElementsByTagName(TAG_PERIOD_UNIT);
            if (existsNode(elementsByTagName5)) {
                xdbGetPollingInfo.setPeriodUnit(elementsByTagName5.item(0).getTextContent());
            }
            NodeList elementsByTagName6 = element.getElementsByTagName(TAG_HEART_BEAT_URL);
            NodeList elementsByTagName7 = element.getElementsByTagName(TAG_HEART_BEAT_PERIOD);
            if (existsNode(elementsByTagName6) && existsNode(elementsByTagName7)) {
                xdbGetPollingInfo.setHeartBeatUrl(elementsByTagName6.item(0).getTextContent());
                xdbGetPollingInfo.setHeartBeatPeriod(Integer.parseInt(elementsByTagName7.item(0).getTextContent()));
            }
            Log.I("update pollingInfo");
            XDBPollingAdp.xdbSetPollingInfo(xdbGetPollingInfo);
        }
    }

    private boolean existsNode(NodeList nodeList) {
        return nodeList != null && nodeList.getLength() > 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x002f  */
    @Override // com.samsung.android.fotaagent.network.action.NetworkResult
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isUpdateAvailable() {
        /*
            r6 = this;
            com.accessorydm.db.file.AccessoryInfoAdapter r0 = new com.accessorydm.db.file.AccessoryInfoAdapter
            r0.<init>()
            java.lang.String r0 = r0.getFirmwareVersion()
            java.util.ArrayList<java.lang.String> r1 = r6.mVersionList
            r2 = 0
            if (r1 == 0) goto L_0x004e
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x0015
            goto L_0x004e
        L_0x0015:
            byte[] r1 = hexToBytes(r0)
            java.lang.String r3 = "MD5"
            byte[] r1 = generateHash(r1, r3)
            java.lang.String r1 = bytesToHex(r1)
            java.util.ArrayList<java.lang.String> r3 = r6.mVersionList
            java.util.Iterator r3 = r3.iterator()
        L_0x0029:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0048
            java.lang.Object r4 = r3.next()
            java.lang.String r4 = (java.lang.String) r4
            boolean r5 = r0.equals(r4)
            if (r5 != 0) goto L_0x0041
            boolean r4 = r1.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x0029
        L_0x0041:
            java.lang.String r0 = "Find Firmware version in Version List"
            com.samsung.android.fotaprovider.log.Log.I(r0)
            r0 = 1
            return r0
        L_0x0048:
            java.lang.String r0 = "Can not find Firmware version in Version List"
            com.samsung.android.fotaprovider.log.Log.I(r0)
            return r2
        L_0x004e:
            java.lang.String r0 = "Can not check update"
            com.samsung.android.fotaprovider.log.Log.I(r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.fotaagent.network.action.PollingResult.isUpdateAvailable():boolean");
    }

    private static byte[] generateHash(byte[] bArr, String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            instance.reset();
            return instance.digest(bArr);
        } catch (Exception e) {
            Log.W(e.toString());
            return bArr;
        }
    }

    private static byte[] hexToBytes(String str) {
        return str.getBytes(Charset.defaultCharset());
    }

    private static String bytesToHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(Integer.toString((b & 255) + 256, 16).substring(1));
        }
        return sb.toString();
    }
}
