package com.accessorydm.db.file;

import java.io.Serializable;

public class XDBAccXNodeInfo implements Serializable {
    private static final long serialVersionUID = 1;
    public String m_szAccount = "";
    public String m_szAppAddr = "";
    public String m_szAppAddrPort = "";
    public String m_szClientAppAuth = "";
    public String m_szServerAppAuth = "";
    public String m_szToConRef = "";
}
