package com.samsung.android.app.twatchmanager.util;

import com.samsung.android.app.twatchmanager.model.CommonInfo;
import com.samsung.android.app.twatchmanager.model.ModuleInfo;
import java.io.InputStream;
import java.util.List;

public interface IRulesParser {
    List<ModuleInfo> getAllModuleInfo();

    CommonInfo getCommonInfo();

    String getRulesXMLVersion(InputStream inputStream);
}
