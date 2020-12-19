package com.samsung.context.sdk.samsunganalytics.internal.sender;

import java.util.Map;

public interface LogSender {
    int send(Map<String, String> map);
}
