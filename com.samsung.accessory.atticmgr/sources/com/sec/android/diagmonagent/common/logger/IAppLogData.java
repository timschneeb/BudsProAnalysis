package com.sec.android.diagmonagent.common.logger;

import android.os.Bundle;

public interface IAppLogData {
    int d(String str, String str2);

    int e(String str, String str2);

    int i(String str, String str2);

    int printBundle(String str, Bundle bundle);

    int v(String str, String str2);

    int w(String str, String str2);
}
