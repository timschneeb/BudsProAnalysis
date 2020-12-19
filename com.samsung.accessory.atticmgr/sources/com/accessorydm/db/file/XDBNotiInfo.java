package com.accessorydm.db.file;

import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XNOTIInterface;
import java.io.Serializable;

public class XDBNotiInfo implements Serializable, XDMInterface, XNOTIInterface {
    private static final long serialVersionUID = 1;
    public int appId = 0;
    public int jobId = 0;
    public String m_szServerId = "";
    public String m_szSessionId = "";
    public int opMode = 0;
    public int rowId = 0;
    public int uiMode = 1;
}
