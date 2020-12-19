package com.accessorydm.db.file;

import android.text.TextUtils;
import com.accessorydm.adapter.XDMDevinfAdapter;
import com.accessorydm.agent.XDMDebug;
import com.accessorydm.eng.core.XDMBase64;
import com.accessorydm.interfaces.XDBInterface;
import com.accessorydm.interfaces.XDMDefInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.tp.XTPHttpUtil;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.NativeUtil;
import com.samsung.android.fotaprovider.util.OperatorUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class XDBFactoryBootstrap implements Serializable, XDMDefInterface, XDMInterface, XDBInterface {
    private static String[] OSP_ProfileName = {"mform25", "Fumo-Server"};
    private static String[] OSP_ServerID = {"mform", "x6g1q14r75"};
    private static String[] OSP_ServerUrl = {"http://mfiop25.mformation.com:80/oma/iop", "https://dms.ospserver.net/v1/device/magicsync/mdm"};
    private static final long serialVersionUID = 1;

    public static XDBProfileInfo xdbFBGetFactoryBootstrapData(int i) {
        String str;
        XDBProfileInfo xDBProfileInfo = new XDBProfileInfo();
        xDBProfileInfo.ProfileName = OSP_ProfileName[i];
        xDBProfileInfo.ServerID = OSP_ServerID[i];
        xDBProfileInfo.ServerUrl = OperatorUtil.getOspServerUrl(OSP_ServerUrl[i]);
        xDBProfileInfo.AuthType = 0;
        xDBProfileInfo.AppID = "w7";
        xDBProfileInfo.AuthLevel = "CLCRED";
        xDBProfileInfo.ServerAuthLevel = "SRVCRED";
        XDBUrlInfo xtpURLParser = XTPHttpUtil.xtpURLParser(xDBProfileInfo.ServerUrl);
        xDBProfileInfo.ServerUrl = xtpURLParser.pURL;
        xDBProfileInfo.ServerIP = xtpURLParser.pAddress;
        xDBProfileInfo.Path = xtpURLParser.pPath;
        xDBProfileInfo.ServerPort = xtpURLParser.nPort;
        xDBProfileInfo.Protocol = xtpURLParser.pProtocol;
        xDBProfileInfo.ServerUrl_Org = xDBProfileInfo.ServerUrl;
        XDBUrlInfo xtpURLParser2 = XTPHttpUtil.xtpURLParser(xDBProfileInfo.ServerUrl_Org);
        xDBProfileInfo.ServerUrl_Org = xtpURLParser2.pURL;
        xDBProfileInfo.ServerIP_Org = xtpURLParser2.pAddress;
        xDBProfileInfo.Path_Org = xtpURLParser2.pPath;
        xDBProfileInfo.ServerPort_Org = xtpURLParser2.nPort;
        xDBProfileInfo.Protocol_Org = xtpURLParser2.pProtocol;
        xDBProfileInfo.bChangedProtocol = false;
        if (i < 2) {
            xDBProfileInfo.AuthType = 1;
            xDBProfileInfo.nServerAuthType = 1;
            xDBProfileInfo.UserName = XDMDevinfAdapter.xdmDevAdpGetFullDeviceID();
            if (TextUtils.isEmpty(xDBProfileInfo.UserName)) {
                xDBProfileInfo.UserName = "IMEI:000000000000000";
            }
            xDBProfileInfo.Password = XDBFactoryBootstrapadapter.xdbFBAdpOspGenerateDevicePwd(xDBProfileInfo.UserName, xDBProfileInfo.ServerID);
            if (i == 0) {
                str = "mform";
            } else {
                str = NativeUtil.getOspServerValue();
            }
            xDBProfileInfo.ServerPwd = str;
            String xdbFBGenerateFactoryNonce = xdbFBGenerateFactoryNonce();
            if (!XDBAccXNodeAdp.xdbExistXNodeInfo(3)) {
                XDBAccXNodeInfo xDBAccXNodeInfo = new XDBAccXNodeInfo();
                xDBAccXNodeInfo.m_szAccount = "./DMAcc/OSP";
                xDBAccXNodeInfo.m_szAppAddr = "./DMAcc/OSP/AppAddr/AppAddrX";
                xDBAccXNodeInfo.m_szAppAddrPort = "./DMAcc/OSP/AppAddr/AppAddrX/Port/PortX";
                xDBAccXNodeInfo.m_szClientAppAuth = "./DMAcc/OSP/AppAuth/ClientSide";
                xDBAccXNodeInfo.m_szServerAppAuth = "./DMAcc/OSP/AppAuth/ServerSide";
                xDBAccXNodeInfo.m_szToConRef = "./DMAcc/OSP/ToConRef/Connectivity Reference Name";
                XDBAccXNodeAdp.xdbCreateXNodeInfo(xDBAccXNodeInfo);
            }
            if (i == 0) {
                xDBProfileInfo.ClientNonce = null;
                xDBProfileInfo.ServerNonce = null;
            } else {
                xDBProfileInfo.ClientNonce = xdbFBGenerateFactoryNonce;
                xDBProfileInfo.ServerNonce = xdbFBGenerateFactoryNonce;
            }
            xDBProfileInfo.ServerNonceFormat = 1;
            xDBProfileInfo.ClientNonceFormat = 1;
        } else {
            Log.E("Wrong Index : " + i);
        }
        XDMDebug.xdmSaveBootStrapLog(">> time :", new Date().toString());
        XDMDebug.xdmSaveBootStrapLog("Model :", XDMDevinfAdapter.xdmDevAdpGetModel());
        XDMDebug.xdmSaveBootStrapLog("DeviceID :", XDMDevinfAdapter.xdmDevAdpGetFullDeviceID());
        XDMDebug.xdmSaveBootStrapLog("Profile Index :", "" + i);
        XDMDebug.xdmSaveBootStrapLog("Server ID :", xDBProfileInfo.ServerID);
        XDMDebug.xdmSaveBootStrapLog("Server PS :", xDBProfileInfo.ServerPwd);
        XDMDebug.xdmSaveBootStrapLog("Server URL :", xDBProfileInfo.ServerUrl);
        XDMDebug.xdmSaveBootStrapLog("User ID :", xDBProfileInfo.UserName);
        XDMDebug.xdmSaveBootStrapLog("USer PS :", xDBProfileInfo.Password);
        return xDBProfileInfo;
    }

    private static String xdbFBGenerateFactoryNonce() {
        Random random = new Random(new Date().getTime());
        return XDMBase64.xdmBase64Encode(random.nextInt() + "SSNextNonce");
    }
}
