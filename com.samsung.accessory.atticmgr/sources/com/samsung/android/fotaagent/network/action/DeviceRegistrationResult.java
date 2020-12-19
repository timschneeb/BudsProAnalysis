package com.samsung.android.fotaagent.network.action;

import android.text.TextUtils;
import com.accessorydm.db.file.XDBPollingAdp;
import com.samsung.android.fotaagent.network.rest.RestResponse;
import com.samsung.android.fotaagent.polling.PollingInfo;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DeviceRegistrationResult extends NetworkResult {
    private static final String TAG_URL = "url";
    private static final String TAG_VERSION_FILE_NAME = "filename";

    DeviceRegistrationResult(RestResponse restResponse) {
        super(restResponse);
        parseResponseIfPossible();
    }

    @Override // com.samsung.android.fotaagent.network.action.NetworkResult
    public boolean needToRetry() {
        return getErrorType() == 500;
    }

    @Override // com.samsung.android.fotaagent.network.action.NetworkResult
    public void parse(Element element) {
        NodeList elementsByTagName = element.getElementsByTagName("url");
        String str = "";
        String textContent = existsNode(elementsByTagName) ? elementsByTagName.item(0).getTextContent() : str;
        NodeList elementsByTagName2 = element.getElementsByTagName(TAG_VERSION_FILE_NAME);
        if (existsNode(elementsByTagName2)) {
            str = elementsByTagName2.item(0).getTextContent();
        }
        PollingInfo xdbGetPollingInfo = XDBPollingAdp.xdbGetPollingInfo();
        if (!TextUtils.isEmpty(textContent)) {
            xdbGetPollingInfo.setPreUrl(textContent);
        }
        if (!TextUtils.isEmpty(str)) {
            xdbGetPollingInfo.setVersionFileName(str);
        }
        XDBPollingAdp.xdbSetPollingInfo(xdbGetPollingInfo);
    }

    private boolean existsNode(NodeList nodeList) {
        return nodeList != null && nodeList.getLength() > 0;
    }
}
