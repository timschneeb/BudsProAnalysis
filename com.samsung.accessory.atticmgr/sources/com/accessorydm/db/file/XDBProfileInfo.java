package com.accessorydm.db.file;

import java.io.Serializable;

public class XDBProfileInfo implements Serializable {
    private static final long serialVersionUID = 1;
    public String AppID = "";
    public String AuthLevel = "";
    public int AuthType = 0;
    public String ClientNonce = "";
    public int ClientNonceFormat = 0;
    public int MagicNumber;
    public String NetworkConnName = "";
    public int ObexType = 2;
    public String Password = "";
    public String Path = "";
    public String Path_Org = "";
    public String PrefConRef = "";
    public String ProfileName = "";
    public String Protocol = "";
    public String Protocol_Org = "";
    public String ServerAuthLevel = "";
    public String ServerID = "";
    public String ServerIP = "";
    public String ServerIP_Org = "";
    public String ServerNonce = "";
    public int ServerNonceFormat = 0;
    public int ServerPort = 80;
    public int ServerPort_Org;
    public String ServerPwd = "";
    public String ServerUrl = "";
    public String ServerUrl_Org = "";
    public String UserName = "";
    public boolean bChangedProtocol;
    public int nNetworkConnIndex = 1;
    public int nServerAuthType = 0;
}
