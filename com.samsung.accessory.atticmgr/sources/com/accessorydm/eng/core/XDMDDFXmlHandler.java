package com.accessorydm.eng.core;

import android.text.TextUtils;
import com.accessorydm.agent.XDMAgent;
import com.accessorydm.db.file.XDB;
import com.accessorydm.eng.parser.XDMXmlParser;
import com.accessorydm.interfaces.XDBInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.samsung.accessory.hearablemgr.core.bixby.BixbyConstants;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.sdk.mobileservice.social.buddy.provider.BuddyContract;
import java.nio.charset.Charset;
import org.xmlpull.v1.XmlPullParser;

public class XDMDDFXmlHandler implements XDMInterface, XDMXml, XDMWbxml {
    public static XDM_DM_Tree CurXmlTree;
    public static XDM_DM_Tree XmlTree;
    public static boolean bNodeChangeMode;
    public static XDMTndsData gTndsData;
    public static char[] gTndsWbxmlHeaderInfo;
    public static XDMOmTree g_om;
    public static String[] g_szDmManagementObjectIdPath;
    public static String[] g_szDmManagementObjectIdType;
    public static String[] g_szDmXmlOmaTags = {"", "", "", "", "", "AccessType", "ACL", XDMInterface.CMD_ADD, "b64", "bin", "bool", "chr", "CaseSense", "CIS", XDMInterface.CMD_COPY, "CS", "data", "DDFName", "DefaultValue", XDMInterface.CMD_DELETE, XDBInterface.XDM_SQL_LAST_UPDATE_DESCRIPTION, "DFFormat", "DFProperties", "DFTitle", "DFType", BixbyConstants.Response.DYNAMIC, XDMInterface.CMD_EXEC, "float", "Format", XDMInterface.CMD_GET, "int", "Man", "MgmtTree", "MIME", "Mod", "Name", "Node", "node", "NodeName", "null", "Occurrence", "One", "OneOrMore", "OneOrN", "Path", "Permanent", XDMInterface.CMD_REPLACE, "RTProperties", "Scope", "Size", XDBInterface.XDM_SQL_DB_POLLING_TIME, "Title", "TStamp", "Type", "Value", "VerDTD", "VerNo", "xml", "ZeroOrMore", "ZeroOrN", "ZeroOrOne"};
    public static String[] g_szDmXmlTagString = {"<MgmtTree>", "</MgmtTree>\n", "<VerDTD>", "</VerDTD>\n", "<Node>", "</Node>\n", "<NodeName>", "</NodeName>\n", "<Path>", "</Path>\n", "<Value>", "</Value>\n", "<RTProperties>", "</RTProperties>\n", "<ACL>", "</ACL>\n", "<Format>", "</Format>\n", "<Type>", "</Type>\n", "<Add>", "</Add>", "<Get>", "</Get>", "<Replace>", "</Replace>", "<Delete>", "</Delete>", "<Exec>", "</Exec>", "<AccessType>", "</AccessType>\n", "<![CDATA[", "]]>", XDMXml.XML_SYNCML_OPEN_TAG, "</SyncML>\n", "<ResultCode>", "</ResultCode>", "<Identifier>", "</Identifier>", "", ""};
    public static String g_szNewAccPath;
    public static String[] g_szTndsTokenStr = {"AccessType", "ACL", XDMInterface.CMD_ADD, "b64", "bin", "bool", "chr", "CaseSense", "CIS", XDMInterface.CMD_COPY, "CS", BuddyContract.Event.DATE, "DDFName", "DefaultValue", XDMInterface.CMD_DELETE, XDBInterface.XDM_SQL_LAST_UPDATE_DESCRIPTION, "DFFormat", "DFProperties", "DFTitle", "DFType", BixbyConstants.Response.DYNAMIC, XDMInterface.CMD_EXEC, "float", "Format", XDMInterface.CMD_GET, "int", "Man", "MgmtTree", "MIME", "Mod", "Name", "Node", "node", "NodeName", "null", "Occurrence", "One", "OneOrMore", "OneOrN", "Path", "Permanent", XDMInterface.CMD_REPLACE, "RTProperties", "Scope", "Size", XDBInterface.XDM_SQL_DB_POLLING_TIME, "Title", "TStamp", "Type", "Value", "VerDTD", "VerNo", "xml", "ZerOrMore", "ZeroOrN", "ZeroOrOne"};
    public static XDMTndsTagManage gstTagManage;
    public int gTagCode = 0;

    public static boolean xdmDDFTNDSCheckMem(Object obj) {
        return obj != null;
    }

    public XDMDDFXmlHandler() {
        bNodeChangeMode = false;
        CurXmlTree = new XDM_DM_Tree();
        g_om = new XDMOmTree();
        g_szNewAccPath = null;
    }

    static {
        String[] strArr = new String[13];
        strArr[1] = "./SyncML/DMAcc";
        strArr[2] = "./SyncML/DMAcc";
        strArr[3] = "./SyncML/DMAcc";
        strArr[4] = "./SyncML/DMAcc";
        strArr[5] = "./SyncML/DMAcc";
        strArr[6] = "./SyncML/DMAcc";
        strArr[7] = "./SyncML/DMAcc";
        strArr[8] = XDMInterface.XDM_DEVINFO_PATH;
        strArr[9] = XDMInterface.XDM_DEVDETAIL_PATH;
        strArr[10] = "./Inbox";
        strArr[11] = XFOTAInterface.XFUMO_PATH;
        g_szDmManagementObjectIdPath = strArr;
        String[] strArr2 = new String[13];
        strArr2[1] = "org.openmobilealliance/1.0/w1";
        strArr2[2] = "org.openmobilealliance/1.0/w2";
        strArr2[3] = "org.openmobilealliance/1.0/w3";
        strArr2[4] = "org.openmobilealliance/1.0/w4";
        strArr2[5] = "org.openmobilealliance/1.0/w5";
        strArr2[6] = "org.openmobilealliance/1.0/w6";
        strArr2[7] = "org.openmobilealliance/1.0/w7";
        strArr2[8] = "org.openmobilealliance.dm/1.0/DevInfo";
        strArr2[9] = "org.openmobilealliance.dm/1.0/DevDetail";
        strArr2[10] = "org.openmobilealliance.dm/1.0/Inbox";
        strArr2[11] = "org.openmobilealliance/1.0/FirmwareUpdateManagementObject";
        g_szDmManagementObjectIdType = strArr2;
        char[] cArr = new char[31];
        cArr[0] = 2;
        cArr[3] = 'j';
        cArr[4] = 26;
        cArr[5] = '-';
        cArr[6] = XDMXml.XML_SLASH;
        cArr[7] = XDMXml.XML_SLASH;
        cArr[8] = 'O';
        cArr[9] = 'M';
        cArr[10] = 'A';
        cArr[11] = XDMXml.XML_SLASH;
        cArr[12] = XDMXml.XML_SLASH;
        cArr[13] = 'D';
        cArr[14] = 'T';
        cArr[15] = 'D';
        cArr[16] = '-';
        cArr[17] = 'D';
        cArr[18] = 'M';
        cArr[19] = '-';
        cArr[20] = 'D';
        cArr[21] = 'D';
        cArr[22] = 'F';
        cArr[23] = ' ';
        cArr[24] = '1';
        cArr[25] = '.';
        cArr[26] = '2';
        cArr[27] = XDMXml.XML_SLASH;
        cArr[28] = XDMXml.XML_SLASH;
        cArr[29] = 'E';
        cArr[30] = 'N';
        gTndsWbxmlHeaderInfo = cArr;
    }

    private static void OMSETPATH(XDMOmTree xDMOmTree, String str, int i, int i2) {
        if (XDMOmLib.xdmOmGetNodeProp(xDMOmTree, str) == null) {
            XDMOmLib.xdmOmWrite(xDMOmTree, str, 0, 0, "", 0);
            XDMAgent.xdmAgentMakeDefaultAcl(xDMOmTree, str, i, i2);
        }
    }

    public void startTag(String str) {
        int xdmDDFXmlTagCode = xdmDDFXmlTagCode(str);
        this.gTagCode = xdmDDFXmlTagCode;
        Log.H("start =          " + str);
        if (xdmDDFXmlTagCode != 22 && xdmDDFXmlTagCode == 36) {
            XDM_DDFXmlElement xDM_DDFXmlElement = new XDM_DDFXmlElement();
            if (CurXmlTree.object == null) {
                XDM_DM_Tree xDM_DM_Tree = CurXmlTree;
                xDM_DM_Tree.object = xDM_DDFXmlElement;
                XmlTree = xDM_DM_Tree;
            } else {
                if (CurXmlTree.childlist == null) {
                    CurXmlTree.childlist = XDMLinkedList.xdmListCreateLinkedList();
                }
                XDM_DM_Tree xDM_DM_Tree2 = new XDM_DM_Tree();
                XDM_DM_Tree xDM_DM_Tree3 = CurXmlTree;
                xDM_DM_Tree2.parent = xDM_DM_Tree3;
                xDM_DM_Tree2.object = xDM_DDFXmlElement;
                XDMLinkedList.xdmListAddObjAtLast(xDM_DM_Tree3.childlist, xDM_DM_Tree2);
                CurXmlTree = xDM_DM_Tree2;
            }
            xDM_DDFXmlElement.m_szTag = str;
        }
    }

    public void endTag(String str) {
        new XDM_DDFXmlElement();
        Log.H("end =            " + str);
        XDM_DM_Tree xDM_DM_Tree = CurXmlTree;
        if (xDM_DM_Tree != null) {
            XDM_DDFXmlElement xDM_DDFXmlElement = (XDM_DDFXmlElement) xDM_DM_Tree.object;
            this.gTagCode = xdmDDFXmlTagCode(str);
            if (xDM_DDFXmlElement != null) {
                int i = this.gTagCode;
                if (i == 16) {
                    xDM_DDFXmlElement.format = 10;
                } else if (i != 22) {
                    if (i != 50) {
                        if (i != 54) {
                            if (i == 57) {
                                xDM_DDFXmlElement.format = 8;
                            } else if (i != 18) {
                                if (i == 19) {
                                    xDM_DDFXmlElement.acl |= 2;
                                } else if (i == 29) {
                                    xDM_DDFXmlElement.acl |= 8;
                                } else if (i != 30) {
                                    switch (i) {
                                        case 7:
                                            xDM_DDFXmlElement.acl |= 1;
                                            break;
                                        case 8:
                                            xDM_DDFXmlElement.format = 1;
                                            break;
                                        case 9:
                                            xDM_DDFXmlElement.format = 2;
                                            break;
                                        case 10:
                                            xDM_DDFXmlElement.format = 3;
                                            break;
                                        case 11:
                                            xDM_DDFXmlElement.format = 4;
                                            break;
                                        default:
                                            switch (i) {
                                                case 25:
                                                    xDM_DDFXmlElement.scope = 2;
                                                    break;
                                                case 26:
                                                    xDM_DDFXmlElement.acl |= 4;
                                                    break;
                                                case 27:
                                                    xDM_DDFXmlElement.format = 9;
                                                    break;
                                                default:
                                                    switch (i) {
                                                        case 36:
                                                            CurXmlTree = CurXmlTree.parent;
                                                            break;
                                                        case 37:
                                                            xDM_DDFXmlElement.format = 6;
                                                            break;
                                                        case 38:
                                                            if (TextUtils.isEmpty(xDM_DDFXmlElement.m_szName)) {
                                                                xDM_DDFXmlElement.m_szName = "Node";
                                                                break;
                                                            }
                                                            break;
                                                        case 39:
                                                            xDM_DDFXmlElement.format = 7;
                                                            break;
                                                        default:
                                                            switch (i) {
                                                                case 45:
                                                                    xDM_DDFXmlElement.scope = 1;
                                                                    break;
                                                                case 46:
                                                                    xDM_DDFXmlElement.acl |= 16;
                                                                    break;
                                                            }
                                                    }
                                            }
                                    }
                                } else {
                                    xDM_DDFXmlElement.format = 5;
                                }
                            }
                        }
                        if (TextUtils.isEmpty(xDM_DDFXmlElement.m_szData)) {
                            xDM_DDFXmlElement.m_szData = "No Data";
                        }
                    } else {
                        xDM_DDFXmlElement.format = 11;
                    }
                }
                this.gTagCode = 0;
            }
        }
    }

    public void text(XmlPullParser xmlPullParser) {
        XDM_DDFXmlElement xDM_DDFXmlElement;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        new XDM_DDFXmlElement();
        Log.H("characters =     " + xmlPullParser.getText().trim());
        XDM_DM_Tree xDM_DM_Tree = CurXmlTree;
        if (xDM_DM_Tree != null && (xDM_DDFXmlElement = (XDM_DDFXmlElement) xDM_DM_Tree.object) != null) {
            int i = this.gTagCode;
            if (i != 17) {
                if (i != 18) {
                    if (i == 33) {
                        String str7 = xDM_DDFXmlElement.m_szMIME;
                        if (!TextUtils.isEmpty(str7)) {
                            str3 = str7.substring(0, str7.length()).concat(xmlPullParser.getText().trim());
                        } else {
                            str3 = xmlPullParser.getText().trim();
                        }
                        xDM_DDFXmlElement.m_szMIME = str3;
                        return;
                    } else if (i == 38) {
                        String str8 = xDM_DDFXmlElement.m_szName;
                        if (!TextUtils.isEmpty(str8)) {
                            str4 = str8.substring(0, str8.length()).concat(xmlPullParser.getText().trim());
                        } else {
                            str4 = xmlPullParser.getText().trim();
                        }
                        xDM_DDFXmlElement.m_szName = str4;
                        return;
                    } else if (i == 44) {
                        String str9 = xDM_DDFXmlElement.m_szPath;
                        if (!TextUtils.isEmpty(str9)) {
                            str5 = str9.substring(0, str9.length()).concat(xmlPullParser.getText().trim());
                        } else {
                            str5 = xmlPullParser.getText().trim();
                        }
                        xDM_DDFXmlElement.m_szPath = str5;
                        return;
                    } else if (i == 53) {
                        String str10 = xDM_DDFXmlElement.m_szType;
                        if (!TextUtils.isEmpty(str10)) {
                            str6 = str10.substring(0, str10.length()).concat(xmlPullParser.getText().trim());
                        } else {
                            str6 = xmlPullParser.getText().trim();
                        }
                        xDM_DDFXmlElement.m_szType = str6;
                        return;
                    } else if (i != 54) {
                        return;
                    }
                }
                String str11 = xDM_DDFXmlElement.m_szData;
                if (!TextUtils.isEmpty(str11)) {
                    str2 = str11.substring(0, str11.length()).concat(xmlPullParser.getText().trim());
                } else {
                    str2 = xmlPullParser.getText().trim();
                }
                xDM_DDFXmlElement.m_szData = str2;
                return;
            }
            String str12 = xDM_DDFXmlElement.m_szDDFName;
            if (!TextUtils.isEmpty(str12)) {
                str = str12.substring(0, str12.length()).concat(xmlPullParser.getText().trim());
            } else {
                str = xmlPullParser.getText().trim();
            }
            xDM_DDFXmlElement.m_szDDFName = str;
        }
    }

    public static int xdmDDFXmlTagCode(String str) {
        int i = 0;
        while (true) {
            String[] strArr = g_szDmXmlOmaTags;
            if (i >= strArr.length) {
                return 0;
            }
            if (strArr[i].equals(str)) {
                return i;
            }
            i++;
        }
    }

    public static int xdmDDFParsing(XDM_XMLStream xDM_XMLStream, XDM_DM_Tree xDM_DM_Tree) {
        if (xDM_XMLStream == null || TextUtils.isEmpty(xDM_XMLStream.m_szData) || xDM_DM_Tree == null) {
            return XDMXml.XML_ERR_INVALID_PARAM;
        }
        CurXmlTree = xDM_DM_Tree;
        new XDMXmlParser().xdmXmlParserDDF(xDM_XMLStream.m_szData);
        return 0;
    }

    public static boolean xdmDDFXmlTagParsing(XDM_DM_Tree xDM_DM_Tree) {
        if (xDM_DM_Tree == null) {
            return false;
        }
        XDMLinkedList xDMLinkedList = xDM_DM_Tree.childlist;
        boolean z = true;
        String str = "";
        while (xDM_DM_Tree != null) {
            XDM_DDFXmlElement xDM_DDFXmlElement = (XDM_DDFXmlElement) xDM_DM_Tree.object;
            if (!TextUtils.isEmpty(xDM_DDFXmlElement.m_szTag)) {
                Log.H("Tag:");
                Log.H(xDM_DDFXmlElement.m_szTag);
            }
            if (!TextUtils.isEmpty(xDM_DDFXmlElement.m_szName)) {
                Log.H("Name:");
                Log.H(xDM_DDFXmlElement.m_szName);
            }
            if (!TextUtils.isEmpty(xDM_DDFXmlElement.m_szPath)) {
                Log.H("Path:");
                Log.H(xDM_DDFXmlElement.m_szPath);
            }
            if (!TextUtils.isEmpty(xDM_DDFXmlElement.m_szData)) {
                Log.H("Data:");
                Log.H(xDM_DDFXmlElement.m_szData);
            }
            int xdmDDFXmlTagCode = xdmDDFXmlTagCode(xDM_DDFXmlElement.m_szTag);
            if (xDM_DM_Tree.parent == null) {
                str = null;
                if (!TextUtils.isEmpty(xDM_DDFXmlElement.m_szPath)) {
                    str = xDM_DDFXmlElement.m_szPath;
                } else {
                    Log.I("Path is NULL.");
                }
                if (!TextUtils.isEmpty(xDM_DDFXmlElement.m_szType)) {
                    str = xdmDDFCheckInbox(xDM_DDFXmlElement.m_szType);
                    if (!TextUtils.isEmpty(str)) {
                        xDM_DDFXmlElement.m_szPath = str;
                    }
                }
            }
            xdmAgentVerifyNewAccount(g_om, xDM_DDFXmlElement.m_szPath, xDM_DDFXmlElement.m_szName);
            if (!(xdmDDFXmlTagCode == 31 || xdmDDFXmlTagCode == 32 || xdmDDFXmlTagCode == 34)) {
                if (xdmDDFXmlTagCode == 36) {
                    xdmDDFPrintNodePropert(xDM_DDFXmlElement);
                    z = xdmDDFCreateNodeToOM(xDM_DDFXmlElement);
                    if (!z) {
                        return z;
                    }
                    if (!TextUtils.isEmpty(xDM_DDFXmlElement.m_szPath) && !xDM_DDFXmlElement.m_szPath.equals("/") && !xDM_DDFXmlElement.m_szPath.equals("./")) {
                        str = "/";
                    }
                    if (!TextUtils.isEmpty(str)) {
                        if (!TextUtils.isEmpty(xDM_DDFXmlElement.m_szName)) {
                            str = str.concat(xDM_DDFXmlElement.m_szName);
                        }
                        xdmDDFCreateNode(xDM_DM_Tree, str);
                        String xdmLibStrrchr = XDMMem.xdmLibStrrchr(str, XDMXml.XML_SLASH);
                        if (!TextUtils.isEmpty(xdmLibStrrchr)) {
                            str = xdmLibStrrchr;
                        }
                    }
                } else if (xdmDDFXmlTagCode != 55) {
                    Log.E(String.valueOf(xdmDDFXmlTagCode));
                    Log.E(g_szDmXmlOmaTags[xdmDDFXmlTagCode]);
                }
            }
            xDM_DM_Tree = (XDM_DM_Tree) XDMLinkedList.xdmListGetNextObj(xDMLinkedList);
        }
        return z;
    }

    public static String xdmDDFCheckInbox(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        for (int i = 1; i < 12; i++) {
            String xdmDDFGetMOType = xdmDDFGetMOType(i);
            if (!TextUtils.isEmpty(xdmDDFGetMOType) && str.equals(xdmDDFGetMOType)) {
                String xdmDDFGetMOPath = xdmDDFGetMOPath(i);
                if (!TextUtils.isEmpty(xdmDDFGetMOPath)) {
                    return xdmDDFGetMOPath;
                }
                return null;
            }
        }
        return null;
    }

    public static String xdmDDFGetMOPath(int i) {
        if (i > 0 && i < 12) {
            return g_szDmManagementObjectIdPath[i];
        }
        Log.E("wrong nId. [" + i + "]");
        return null;
    }

    public static String xdmDDFGetMOType(int i) {
        if (i > 0 && i < 12) {
            return g_szDmManagementObjectIdType[i];
        }
        Log.E("wrong nId. [" + i + "]");
        return null;
    }

    public static boolean xdmAgentVerifyNewAccount(XDMOmTree xDMOmTree, String str, String str2) {
        if (TextUtils.isEmpty(str) || !str.equals(XDMInterface.XDM_BASE_PATH)) {
            return false;
        }
        String concat = str.concat("/");
        if (!TextUtils.isEmpty(str2)) {
            concat = concat.concat(str2);
        }
        if (XDMOmLib.xdmOmGetNodeProp(xDMOmTree, concat) == null) {
            g_szNewAccPath = concat;
            return true;
        }
        g_szNewAccPath = null;
        return false;
    }

    public static void xdmDDFPrintNodePropert(XDM_DDFXmlElement xDM_DDFXmlElement) {
        String str = "";
        if (!TextUtils.isEmpty(xDM_DDFXmlElement.m_szPath)) {
            str = str.concat(xDM_DDFXmlElement.m_szPath);
        }
        if (!TextUtils.isEmpty(xDM_DDFXmlElement.m_szName)) {
            if (1024 <= ((long) (str.length() + xDM_DDFXmlElement.m_szName.length()))) {
                Log.I("Buffer Overflow. Increase the space. for element->name.");
                return;
            }
            str = str.concat("/").concat(xDM_DDFXmlElement.m_szName);
        }
        if (!TextUtils.isEmpty(xDM_DDFXmlElement.m_szData)) {
            if (1024 <= ((long) (str.length() + xDM_DDFXmlElement.m_szData.length()))) {
                Log.I("Buffer Overflow. Increase the space. for element->data.");
                return;
            }
            str = str.concat(" [").concat(xDM_DDFXmlElement.m_szData);
        }
        if (!TextUtils.isEmpty(xDM_DDFXmlElement.m_szType)) {
            if (1024 <= ((long) (str.length() + xDM_DDFXmlElement.m_szType.length()))) {
                Log.I("Buffer Overflow. Increase the space. element->type.");
                return;
            }
            str = str.concat("] [").concat(xDM_DDFXmlElement.m_szType);
        }
        if (!TextUtils.isEmpty(str)) {
            Log.H("[DDF][" + str + "] [" + xDM_DDFXmlElement.acl + "][" + xDM_DDFXmlElement.format + "][" + xDM_DDFXmlElement.scope + "].");
        }
    }

    public static boolean xdmDDFCreateNodeToOM(XDM_DDFXmlElement xDM_DDFXmlElement) {
        int length;
        int i = xDM_DDFXmlElement.scope;
        int i2 = xDM_DDFXmlElement.format;
        int i3 = xDM_DDFXmlElement.acl;
        String str = xDM_DDFXmlElement.m_szData;
        if (TextUtils.isEmpty(xDM_DDFXmlElement.m_szName)) {
            Log.E("Not exist nodename.");
            return false;
        }
        if (!(!TextUtils.isEmpty(XDMMem.xdmLibStrstr(xDM_DDFXmlElement.m_szPath, XDMInterface.XDM_BASE_PATH)))) {
            if (!TextUtils.isEmpty(xDM_DDFXmlElement.m_szPath)) {
                String concat = "".concat(XDMInterface.XDM_BASE_PATH).concat(xDM_DDFXmlElement.m_szPath);
                xDM_DDFXmlElement.m_szPath = null;
                xDM_DDFXmlElement.m_szPath = concat;
            } else {
                xDM_DDFXmlElement.m_szPath = XDMInterface.XDM_BASE_PATH;
            }
        }
        XDMAgent.xdmAgentSetXNodePath(xDM_DDFXmlElement.m_szPath, xDM_DDFXmlElement.m_szName, true);
        if (xDM_DDFXmlElement.m_szName.equals("AAuthData")) {
            i2 = 1;
        }
        String concat2 = xDM_DDFXmlElement.m_szPath.concat("/").concat(xDM_DDFXmlElement.m_szName);
        if (i3 == 0) {
            i3 = 27;
        }
        if (i == 0) {
            i = 2;
        }
        if (i2 == 12) {
            i2 = 6;
        }
        if (i2 == 6 || i2 == 7 || i2 == 12) {
            xdmDDFSetOMTree(g_om, concat2, str, 0, xDM_DDFXmlElement.m_szType, i3, i, i2);
            OMSETPATH(g_om, concat2, i3, i);
        } else {
            if (i2 == 2) {
                length = 0;
            } else {
                if (TextUtils.isEmpty(str)) {
                    str = "null";
                    xDM_DDFXmlElement.m_szData = str;
                }
                length = str.length();
            }
            if (concat2.length() < 0 || ((long) concat2.length()) >= 256) {
                Log.E("Size[" + concat2.length() + "]. Fatal ERROR.");
                return false;
            }
            xdmDDFSetOMTree(g_om, concat2, str, length, xDM_DDFXmlElement.m_szType, i3, i, i2);
        }
        return true;
    }

    public static void xdmDDFSetOMTree(XDMOmTree xDMOmTree, String str, String str2, int i, String str3, int i2, int i3, int i4) {
        XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, str);
        XDMAgent.xdmAgentSetSyncMode(3);
        if (xdmOmGetNodeProp == null || XDMAgent.xdmAgentGetSyncMode() == 3) {
            xdmDDFSetOMTreeProperty(xDMOmTree, str, str2, i, str3, i4);
            XDMAgent.xdmAgentMakeDefaultAcl(xDMOmTree, str, i2, i3);
        }
        if (bNodeChangeMode) {
            Log.I("bNodeChangeMode Change Node.");
            xdmDDFSetOMTreeProperty(xDMOmTree, str, str2, i, str3, i4);
            XDMAgent.xdmAgentMakeDefaultAcl(xDMOmTree, str, i2, i3);
        }
    }

    public static void xdmDDFSetOMTreeProperty(XDMOmTree xDMOmTree, String str, String str2, int i, String str3, int i2) {
        char[] cArr = new char[256];
        XDMOmLib.xdmOmMakeParentPath(str, cArr);
        String str4 = "";
        for (int i3 = 0; cArr[i3] != 0; i3++) {
            str4 = str4.concat(String.valueOf(cArr[i3]));
        }
        if (XDMOmLib.xdmOmGetNodeProp(xDMOmTree, str4) == null) {
            XDMOmLib.xdmOmProcessCmdImplicitAdd(xDMOmTree, str4, 27, 1);
        }
        if (i2 == 6 || i2 == 7 || i2 == 12) {
            XDMOmLib.xdmOmWrite(xDMOmTree, str, 0, 0, "", 0);
        } else {
            long xdmOmWrite = (long) XDMOmLib.xdmOmWrite(xDMOmTree, str, i, 0, str2, i);
            if (xdmOmWrite <= 0) {
                Log.I("Size[" + xdmOmWrite + "]");
            }
        }
        XDMVnode xdmOmGetNodeProp = XDMOmLib.xdmOmGetNodeProp(xDMOmTree, str);
        if (xdmOmGetNodeProp != null) {
            if (xdmOmGetNodeProp.type != null) {
                XDMOmLib.xdmOmVfsDeleteMimeList(xdmOmGetNodeProp.type);
            }
            XDMOmList xDMOmList = new XDMOmList();
            if (!TextUtils.isEmpty(str3)) {
                xDMOmList.data = str3;
            } else {
                xDMOmList.data = XDMInterface.MIMETYPE_TEXT_PLAIN;
            }
            xDMOmList.data = null;
            xdmOmGetNodeProp.type = xDMOmList;
            xdmOmGetNodeProp.format = i2;
        }
    }

    public static boolean xdmDDFCreateNode(XDM_DM_Tree xDM_DM_Tree, String str) {
        if (xDM_DM_Tree == null) {
            return false;
        }
        XDMLinkedList xDMLinkedList = xDM_DM_Tree.childlist;
        if (xDMLinkedList == null) {
            return true;
        }
        XDMLinkedList.xdmListSetCurrentObj(xDMLinkedList, 0);
        while (xDM_DM_Tree != null) {
            xDM_DM_Tree = (XDM_DM_Tree) XDMLinkedList.xdmListGetNextObj(xDMLinkedList);
            if (xDM_DM_Tree == null) {
                return true;
            }
            XDM_DDFXmlElement xDM_DDFXmlElement = (XDM_DDFXmlElement) xDM_DM_Tree.object;
            int xdmDDFXmlTagCode = xdmDDFXmlTagCode(xDM_DDFXmlElement.m_szTag);
            if (xdmDDFXmlTagCode != 36) {
                Log.E(String.valueOf(xdmDDFXmlTagCode));
                Log.E(g_szDmXmlOmaTags[xdmDDFXmlTagCode]);
            } else {
                xDM_DDFXmlElement.m_szPath = str;
                boolean xdmDDFCreateNodeToOM = xdmDDFCreateNodeToOM(xDM_DDFXmlElement);
                if (!xdmDDFCreateNodeToOM) {
                    return xdmDDFCreateNodeToOM;
                }
                String concat = str.concat("/").concat(xDM_DDFXmlElement.m_szName);
                xdmDDFCreateNode(xDM_DM_Tree, concat);
                str = XDMMem.xdmLibStrrchr(concat, XDMXml.XML_SLASH);
            }
        }
        return true;
    }

    public static int xdmDDFCreateTNDSNodeFromFile(int i, XDMOmTree xDMOmTree) {
        int xdbGetFileSize = (int) XDB.xdbGetFileSize(i);
        if (xdbGetFileSize <= 0) {
            return 0;
        }
        byte[] bArr = new byte[xdbGetFileSize];
        if (!XDB.xdbReadFile(i, 0, xdbGetFileSize, bArr)) {
            return 0;
        }
        String str = new String(bArr, Charset.defaultCharset());
        return xdmDDFCreateTNDSNode(str, str.length(), xDMOmTree);
    }

    public static int xdmDDFCreateTNDSNode(String str, int i, XDMOmTree xDMOmTree) {
        XDM_XMLStream xDM_XMLStream = new XDM_XMLStream();
        XDM_DM_Tree xDM_DM_Tree = new XDM_DM_Tree();
        if (!TextUtils.isEmpty(xdmDDFParseCDATA(str))) {
            xDM_XMLStream.m_szData = xdmDDFParseCDATA(str);
            xDM_XMLStream.size = 0;
        } else {
            xDM_XMLStream.m_szData = str;
            xDM_XMLStream.size = i;
        }
        if (xdmDDFParsing(xDM_XMLStream, xDM_DM_Tree) != 0) {
            Log.E("Parsing Fail.");
            g_om = null;
            return 0;
        } else if (xDMOmTree == null) {
            Log.E("OM is NULL.");
            g_om = null;
            return 0;
        } else {
            g_om = xDMOmTree;
            if (!xdmDDFXmlTagParsing(XmlTree)) {
                Log.E("Create Node Fail. Check the xml file.");
                g_om = null;
                return 0;
            }
            Log.I("Success.");
            return 1;
        }
    }

    public static String xdmDDFParseCDATA(String str) {
        String substring;
        int indexOf;
        String[] strArr = g_szDmXmlTagString;
        String str2 = strArr[33];
        String str3 = strArr[32];
        if (!TextUtils.isEmpty(str) && XDMMem.xdmLibStrncmp(str, str3, str3.length()) == 0 && !TextUtils.isEmpty(XDMMem.xdmLibStrstr(str, str2)) && (indexOf = (substring = str.substring(str3.length())).indexOf(str2)) > 0) {
            return substring.substring(0, indexOf);
        }
        return null;
    }

    public static String xdmTndsWbxmlParse(String str, int i) {
        xdmDDFTNDSInitParse(str, i);
        int xdmDDFTNDSGetWbxmlSize = xdmDDFTNDSGetWbxmlSize();
        String xdmDDFTNDSGetWbxmlData = xdmDDFTNDSGetWbxmlData();
        boolean z = false;
        while (xdmDDFTNDSGetWbxmlSize != 0 && !TextUtils.isEmpty(xdmDDFTNDSGetWbxmlData)) {
            byte[] bytes = xdmDDFTNDSGetWbxmlData.getBytes(Charset.defaultCharset());
            byte b = bytes[0];
            if (b == 0) {
                xdmDDFTNDSParsingCodePage();
            } else if (b == 2) {
                xdmDDFTNDSParsingWbxmlHeader();
            } else if (b == 96) {
                xdmDDFTNDSParsingMgmtTreeTag();
                xdmDDFTNDSUderMgmtTreeTagParse();
            } else if (b != 109) {
                Log.E(String.valueOf(xdmDDFTNDSGetWbxmlSize));
                Log.E(String.valueOf(bytes));
                return null;
            } else {
                xdmDDFTNDSParsingSyncMLTag();
                z = true;
            }
            xdmDDFTNDSGetWbxmlSize = xdmDDFTNDSGetWbxmlSize();
            xdmDDFTNDSGetWbxmlData = xdmDDFTNDSGetWbxmlSize != 0 ? xdmDDFTNDSGetWbxmlData() : null;
        }
        if (!z) {
            xdmDDFTNDSAppendSyncMLCloseTag();
        }
        return xdmDDFTNDSGetXMLData();
    }

    public static void xdmDDFTNDSInitParse(String str, int i) {
        gTndsData = new XDMTndsData();
        xdmDDFTNDSSetWbxmlSize(i);
        xdmDDFTNDSSetWbxmlData(str);
        xdmDDFTNDSAllocXMLData();
        xdmDDFTNDSAppendNameSpace();
        gstTagManage = new XDMTndsTagManage();
    }

    public static void xdmDDFTNDSSetWbxmlSize(int i) {
        gTndsData.nWbxmlDataSize = i;
    }

    public static void xdmDDFTNDSSetWbxmlData(String str) {
        gTndsData.m_szWbxmlData = str;
    }

    public static void xdmDDFTNDSSetXMLSize(int i) {
        gTndsData.nXMLDataSize = i;
    }

    public static void xdmDDFTNDSSetXMLData(String str) {
        gTndsData.m_szXMLData = str;
    }

    public static void xdmDDFTNDSSetXMLDataStart(String str) {
        gTndsData.m_szXMLDataStart = str;
    }

    public static void xdmDDFTNDSSetWbxmlDataStart(String str) {
        gTndsData.m_szWbxmlDataStart = str;
    }

    public static int xdmDDFTNDSGetWbxmlSize() {
        return gTndsData.nWbxmlDataSize;
    }

    public static String xdmDDFTNDSGetWbxmlData() {
        return gTndsData.m_szWbxmlData;
    }

    public static int xdmDDFTNDSGetXMLSize() {
        return gTndsData.nXMLDataSize;
    }

    public static String xdmDDFTNDSGetXMLData() {
        return gTndsData.m_szXMLData;
    }

    public static String xdmDDFTNDSGetXMLDataStart() {
        return gTndsData.m_szXMLDataStart;
    }

    public static String xdmDDFTNDSGetWbxmlDataStart() {
        return gTndsData.m_szWbxmlDataStart;
    }

    public static boolean xdmDDFTNDSAllocXMLData() {
        if (!xdmDDFTNDSCheckMem("")) {
            Log.E("# ERROR #  Alloc Error !!! ###");
            return false;
        }
        xdmDDFTNDSSetXMLData("");
        return true;
    }

    public static void xdmDDFTNDSAppendNameSpace() {
        String xdmDDFTNDSGetXMLData = xdmDDFTNDSGetXMLData();
        int xdmDDFTNDSGetXMLSize = xdmDDFTNDSGetXMLSize();
        String concat = xdmDDFTNDSGetXMLData.concat(XDMXml.XML_VERSION_STRING).concat(XDMXml.XML_NAME_SPACE_STRING);
        int length = concat.length();
        xdmDDFTNDSSetXMLData(concat);
        xdmDDFTNDSSetXMLSize(xdmDDFTNDSGetXMLSize + length);
    }

    public static boolean xdmDDFTNDSParsingCodePage() {
        String substring = xdmDDFTNDSGetWbxmlData().substring(1);
        if (substring.charAt(0) == 2 || substring.charAt(0) == 0) {
            xdmDDFTNDSSetWbxmlData(substring.substring(1));
            xdmDDFTNDSSetWbxmlSize(xdmDDFTNDSGetWbxmlSize() - 2);
            return true;
        }
        Log.E("### ERROR ### TNDS Tag Right ###");
        return false;
    }

    public static void xdmDDFTNDSParsingWbxmlHeader() {
        String substring = xdmDDFTNDSGetWbxmlData().substring(1).substring(2).substring(1);
        char charAt = substring.charAt(0);
        xdmDDFTNDSSetWbxmlData(substring.substring(1).substring(charAt));
        xdmDDFTNDSSetWbxmlSize(xdmDDFTNDSGetWbxmlSize() - (5 + charAt));
    }

    public static void xdmDDFTNDSParsingSyncMLTag() {
        int xdmDDFTNDSGetWbxmlSize = xdmDDFTNDSGetWbxmlSize();
        String xdmDDFTNDSGetWbxmlData = xdmDDFTNDSGetWbxmlData();
        xdmDDFTNDSManagePushTag(136);
        String substring = xdmDDFTNDSGetWbxmlData.substring(1);
        xdmDDFTNDSSetWbxmlSize(xdmDDFTNDSGetWbxmlSize - 1);
        xdmDDFTNDSSetWbxmlData(substring);
    }

    public static boolean xdmDDFTNDSManagePushTag(int i) {
        if (gstTagManage.nTagSP == 30) {
            Log.E("# ERROR # TagSP FULL !!! ###");
            return false;
        }
        gstTagManage.eTagID[gstTagManage.nTagSP] = i;
        gstTagManage.nTagSP++;
        return true;
    }

    public static void xdmDDFTNDSParsingMgmtTreeTag() {
        xdmDDFTNDSParsingOpenTag();
    }

    public static void xdmDDFTNDSParsingVerdtdTag() {
        xdmDDFTNDSParsingOpenTag();
    }

    public static void xdmDDFTNDSParsingNodeTag() {
        xdmDDFTNDSParsingOpenTag();
    }

    public static void xdmDDFTNDSParsingNodeNameTag() {
        xdmDDFTNDSParsingOpenTag();
    }

    public static void xdmDDFTNDSParsingRTPropertiesTag() {
        xdmDDFTNDSParsingOpenTag();
    }

    public static void xdmDDFTNDSParsingFormatTag() {
        xdmDDFTNDSParsingOpenTag();
        xdmDDFTNDSParsingFormatChildElement();
    }

    public static void xdmDDFTNDSParsingTypeTag() {
        xdmDDFTNDSParsingOpenTag();
    }

    public static void xdmDDFTNDSParsingDDFNameTag() {
        xdmDDFTNDSParsingOpenTag();
    }

    public static void xdmDDFTNDSParsingValueTag() {
        xdmDDFTNDSParsingOpenTag();
    }

    public static void xdmDDFTNDSParsingMIMETag() {
        xdmDDFTNDSParsingOpenTag();
    }

    public static void xdmDDFTNDSParsingAccessTypeTag() {
        xdmDDFTNDSParsingOpenTag();
        xdmDDFTNDSParsingAccessTypeChildElement();
    }

    public static void xdmDDFTNDSParsingPathTag() {
        xdmDDFTNDSParsingOpenTag();
    }

    public static void xdmDDFTNDSParsingACLTag() {
        xdmDDFTNDSParsingOpenTag();
    }

    public static void xdmDDFTNDSParsingOpenTag() {
        int xdmDDFTNDSGetWbxmlSize = xdmDDFTNDSGetWbxmlSize();
        String xdmDDFTNDSGetWbxmlData = xdmDDFTNDSGetWbxmlData();
        int i = 0;
        char charAt = xdmDDFTNDSGetWbxmlData.charAt(0);
        String xdmDDFTNDSGetXMLData = xdmDDFTNDSGetXMLData();
        int xdmDDFTNDSGetXMLSize = xdmDDFTNDSGetXMLSize();
        xdmDDFTNDSManagePushTag(charAt);
        String xdmDDFTNDSMakeOpenTagString = xdmDDFTNDSMakeOpenTagString(charAt);
        if (!TextUtils.isEmpty(xdmDDFTNDSMakeOpenTagString)) {
            i = xdmDDFTNDSMakeOpenTagString.length();
            xdmDDFTNDSGetXMLData = xdmDDFTNDSGetXMLData.concat(xdmDDFTNDSMakeOpenTagString);
        }
        xdmDDFTNDSSetXMLSize(xdmDDFTNDSGetXMLSize + i);
        xdmDDFTNDSSetXMLData(xdmDDFTNDSGetXMLData);
        String substring = xdmDDFTNDSGetWbxmlData.substring(1);
        xdmDDFTNDSSetWbxmlSize(xdmDDFTNDSGetWbxmlSize - 1);
        xdmDDFTNDSSetWbxmlData(substring);
    }

    public static String xdmDDFTNDSMakeOpenTagString(int i) {
        if (xdmDDFTNDSCheckMem("")) {
            return "<".concat(xdmDDFTNDSGetTagString(i)).concat(">");
        }
        Log.E("# ERROR # Alloc Error !!! ###");
        return null;
    }

    public static String xdmDDFTNDSGetTagString(int i) {
        if (i >= 125) {
            return i == 136 ? XDMXml.XML_SYNCML_STRING : "NULL";
        }
        return g_szTndsTokenStr[i - 69];
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x004e A[FALL_THROUGH] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void xdmDDFTNDSParsingFormatChildElement() {
        /*
        // Method dump skipped, instructions count: 160
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.eng.core.XDMDDFXmlHandler.xdmDDFTNDSParsingFormatChildElement():void");
    }

    public static void xdmDDFTNDSParsingAccessTypeChildElement() {
        int xdmDDFTNDSGetWbxmlSize = xdmDDFTNDSGetWbxmlSize();
        String xdmDDFTNDSGetWbxmlData = xdmDDFTNDSGetWbxmlData();
        char charAt = xdmDDFTNDSGetWbxmlData.charAt(0);
        String xdmDDFTNDSGetXMLData = xdmDDFTNDSGetXMLData();
        int xdmDDFTNDSGetXMLSize = xdmDDFTNDSGetXMLSize();
        int i = charAt - '@';
        if (i == 71 || i == 78 || i == 83 || i == 90 || i == 93 || i == 110) {
            xdmDDFTNDSSetXMLSize(xdmDDFTNDSGetXMLSize + "<".concat(xdmDDFTNDSGetTagString(i)).concat("/>").length());
            xdmDDFTNDSSetXMLData(xdmDDFTNDSGetXMLData);
            String substring = xdmDDFTNDSGetWbxmlData.substring(1);
            xdmDDFTNDSSetWbxmlSize(xdmDDFTNDSGetWbxmlSize - 1);
            xdmDDFTNDSSetWbxmlData(substring);
        }
    }

    public static boolean xdmDDFTNDSUderMgmtTreeTagParse() {
        int xdmDDFTNDSGetWbxmlSize = xdmDDFTNDSGetWbxmlSize();
        String xdmDDFTNDSGetWbxmlData = xdmDDFTNDSGetWbxmlData();
        while (xdmDDFTNDSGetWbxmlSize != 0 && !TextUtils.isEmpty(xdmDDFTNDSGetWbxmlData)) {
            byte b = xdmDDFTNDSGetWbxmlData.getBytes(Charset.defaultCharset())[0];
            if (b == 0) {
                xdmDDFTNDSParsingCodePage();
            } else if (b == 1) {
                xdmDDFTNDSParsingCloseTag();
            } else if (b == 3) {
                xdmDDFTNDSProcessStringData();
            } else if (b == 81) {
                xdmDDFTNDSParsingDDFNameTag();
            } else if (b == 92) {
                xdmDDFTNDSParsingFormatTag();
            } else if (b == 97) {
                xdmDDFTNDSParsingMIMETag();
            } else if (b == 100) {
                xdmDDFTNDSParsingNodeTag();
            } else if (b == 102) {
                xdmDDFTNDSParsingNodeNameTag();
            } else if (b == 108) {
                xdmDDFTNDSParsingPathTag();
            } else if (b == 111) {
                xdmDDFTNDSParsingRTPropertiesTag();
            } else if (b == 69) {
                xdmDDFTNDSParsingAccessTypeTag();
            } else if (b != 70) {
                switch (b) {
                    case 117:
                        xdmDDFTNDSParsingTypeTag();
                        break;
                    case 118:
                        xdmDDFTNDSParsingValueTag();
                        break;
                    case 119:
                        xdmDDFTNDSParsingVerdtdTag();
                        break;
                    default:
                        Log.E(String.valueOf(xdmDDFTNDSGetWbxmlSize));
                        Log.E(xdmDDFTNDSGetWbxmlData);
                        return false;
                }
            } else {
                xdmDDFTNDSParsingACLTag();
            }
            xdmDDFTNDSGetWbxmlSize = xdmDDFTNDSGetWbxmlSize();
            xdmDDFTNDSGetWbxmlData = xdmDDFTNDSGetWbxmlSize != 0 ? xdmDDFTNDSGetWbxmlData() : null;
        }
        return true;
    }

    public static void xdmDDFTNDSParsingCloseTag() {
        int i;
        int xdmDDFTNDSGetWbxmlSize = xdmDDFTNDSGetWbxmlSize();
        String xdmDDFTNDSGetWbxmlData = xdmDDFTNDSGetWbxmlData();
        String xdmDDFTNDSGetXMLData = xdmDDFTNDSGetXMLData();
        int xdmDDFTNDSGetXMLSize = xdmDDFTNDSGetXMLSize();
        String xdmDDFTNDSMakeCloseTagString = xdmDDFTNDSMakeCloseTagString();
        if (!TextUtils.isEmpty(xdmDDFTNDSMakeCloseTagString)) {
            i = xdmDDFTNDSMakeCloseTagString.length();
            xdmDDFTNDSGetXMLData = xdmDDFTNDSGetXMLData.concat(xdmDDFTNDSMakeCloseTagString);
        } else {
            i = 0;
        }
        xdmDDFTNDSSetXMLSize(xdmDDFTNDSGetXMLSize + i);
        xdmDDFTNDSSetXMLData(xdmDDFTNDSGetXMLData);
        String substring = xdmDDFTNDSGetWbxmlData.substring(1);
        xdmDDFTNDSSetWbxmlSize(xdmDDFTNDSGetWbxmlSize - 1);
        xdmDDFTNDSSetWbxmlData(substring);
    }

    public static String xdmDDFTNDSMakeCloseTagString() {
        if (xdmDDFTNDSCheckMem("")) {
            return "</".concat(xdmDDFTNDSGetTagString(xdmDDFTNDSManagePopTag())).concat(">");
        }
        Log.E("# ERROR # Alloc Error !!! ###");
        return null;
    }

    public static int xdmDDFTNDSManagePopTag() {
        if (gstTagManage.nTagSP == 0) {
            Log.E("# ERROR # TagSP EMPTY !!! ###");
            return 0;
        }
        XDMTndsTagManage xDMTndsTagManage = gstTagManage;
        xDMTndsTagManage.nTagSP--;
        int i = gstTagManage.eTagID[gstTagManage.nTagSP];
        gstTagManage.eTagID[gstTagManage.nTagSP] = 0;
        return i;
    }

    public static void xdmDDFTNDSProcessStringData() {
        StringBuilder sb = new StringBuilder();
        int xdmDDFTNDSGetWbxmlSize = xdmDDFTNDSGetWbxmlSize();
        String xdmDDFTNDSGetWbxmlData = xdmDDFTNDSGetWbxmlData();
        sb.append(xdmDDFTNDSGetXMLData());
        int xdmDDFTNDSGetXMLSize = xdmDDFTNDSGetXMLSize();
        String substring = xdmDDFTNDSGetWbxmlData.substring(1);
        char charAt = substring.charAt(0);
        String str = substring;
        int i = 1;
        while (charAt != 0) {
            if (charAt == '&') {
                sb.append("&amp;");
            } else {
                sb.append((char) charAt);
            }
            str = str.substring(1);
            i++;
            charAt = str.charAt(0);
        }
        xdmDDFTNDSSetXMLSize((xdmDDFTNDSGetXMLSize + i) - 1);
        xdmDDFTNDSSetXMLData(sb.toString());
        String substring2 = str.substring(1);
        xdmDDFTNDSSetWbxmlSize(xdmDDFTNDSGetWbxmlSize - (i + 1));
        xdmDDFTNDSSetWbxmlData(substring2);
    }

    public static void xdmDDFTNDSAppendSyncMLCloseTag() {
        String xdmDDFTNDSGetXMLData = xdmDDFTNDSGetXMLData();
        int xdmDDFTNDSGetXMLSize = xdmDDFTNDSGetXMLSize();
        xdmDDFTNDSSetXMLData(xdmDDFTNDSGetXMLData.concat(XDMXml.XML_SYNCML_END_STRING));
        xdmDDFTNDSSetXMLSize(xdmDDFTNDSGetXMLSize + 9);
    }

    public static char xdmDDFConvertString2WbxmlHex(String str) {
        int length = str.length();
        for (int i = 0; i != 56; i++) {
            if (str.regionMatches(0, xdmDDFConvertGetXMLTag(i), 0, length)) {
                return (char) (i + 5 + 64);
            }
        }
        if (str.regionMatches(0, XDMXml.XML_SYNCML_STRING, 0, 6)) {
            return 'm';
        }
        Log.E("# ERROR # Not Found String !!! ###");
        return 65535;
    }

    public static String xdmDDFConvertGetXMLTag(int i) {
        return g_szTndsTokenStr[i];
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static void xdmDDFConvertCheckElement(char c) {
        byte b = (byte) c;
        if (b != 69) {
            if (b != 70) {
                if (b != 76) {
                    if (b != 87) {
                        if (b != 92) {
                            if (b != 102) {
                                if (b != 104) {
                                    if (!(b == 81 || b == 82 || b == 84)) {
                                        if (b != 85) {
                                            if (b != 108) {
                                                if (b != 109) {
                                                    switch (b) {
                                                        case 95:
                                                        case 97:
                                                        case 98:
                                                        case 99:
                                                            break;
                                                        case 96:
                                                            xdmDDFConvertAddTndsCodePage();
                                                            return;
                                                        case 100:
                                                            return;
                                                        default:
                                                            switch (b) {
                                                                case 111:
                                                                    return;
                                                                case 112:
                                                                    break;
                                                                case 113:
                                                                    break;
                                                                default:
                                                                    switch (b) {
                                                                        case 115:
                                                                        case 116:
                                                                        case 118:
                                                                        case 119:
                                                                        case 120:
                                                                            break;
                                                                        case 117:
                                                                            return;
                                                                        default:
                                                                            Log.E("# ERROR # What? [value : " + c + "]!!! ###");
                                                                            return;
                                                                    }
                                                            }
                                                    }
                                                } else {
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            xdmDDFProcessConvertStringData();
            return;
        }
        xdmDDFProcessConvertHexData(c);
    }

    public static void xdmDDFProcessConvertStringData() {
        int xdmDDFTNDSGetWbxmlSize = xdmDDFTNDSGetWbxmlSize();
        String xdmDDFTNDSGetWbxmlData = xdmDDFTNDSGetWbxmlData();
        String xdmDDFTNDSGetXMLData = xdmDDFTNDSGetXMLData();
        char charAt = xdmDDFTNDSGetXMLData.charAt(0);
        int xdmDDFTNDSGetXMLSize = xdmDDFTNDSGetXMLSize();
        String concat = xdmDDFTNDSGetWbxmlData.concat(String.valueOf((char) 3));
        int i = xdmDDFTNDSGetWbxmlSize + 1;
        int i2 = 0;
        while (charAt != '<') {
            concat = concat.concat(String.valueOf(charAt));
            i++;
            xdmDDFTNDSGetXMLData = xdmDDFTNDSGetXMLData.substring(1);
            i2++;
            charAt = xdmDDFTNDSGetXMLData.charAt(0);
        }
        String concat2 = concat.concat(String.valueOf((char) 0));
        xdmDDFTNDSSetWbxmlSize(i + 1);
        xdmDDFTNDSSetWbxmlData(concat2);
        xdmDDFTNDSSetXMLSize(xdmDDFTNDSGetXMLSize - i2);
        xdmDDFTNDSSetXMLData(xdmDDFTNDSGetXMLData);
    }

    public static void xdmDDFProcessConvertHexData(char c) {
        if (c == 'E') {
            xdmDDFProcessConvertAccessTypeElement();
        } else if (c == 'U' || c == '\\') {
            xdmDDFProcessConvertFormatElement();
        }
    }

    public static void xdmDDFProcessConvertFormatElement() {
        int xdmDDFTNDSGetWbxmlSize = xdmDDFTNDSGetWbxmlSize();
        String xdmDDFTNDSGetWbxmlData = xdmDDFTNDSGetWbxmlData();
        String xdmDDFTNDSGetXMLData = xdmDDFTNDSGetXMLData();
        char charAt = xdmDDFTNDSGetXMLData.charAt(0);
        int xdmDDFTNDSGetXMLSize = xdmDDFTNDSGetXMLSize();
        String str = "";
        if (charAt == '<') {
            String substring = xdmDDFTNDSGetXMLData.substring(1);
            int i = xdmDDFTNDSGetXMLSize - 1;
            char charAt2 = substring.charAt(0);
            while (charAt2 != '/') {
                str = str.concat(String.valueOf(charAt2));
                substring = substring.substring(1);
                i--;
                charAt2 = substring.charAt(0);
                if (charAt2 == ' ') {
                    substring = substring.substring(1);
                    i--;
                    charAt2 = substring.charAt(0);
                }
            }
            xdmDDFTNDSGetXMLData = substring.substring(2);
            xdmDDFTNDSGetXMLSize = i - 2;
        } else if (charAt != '<') {
            while (charAt != '<') {
                str = str.concat(String.valueOf(charAt));
                xdmDDFTNDSGetXMLData = xdmDDFTNDSGetXMLData.substring(1);
                xdmDDFTNDSGetXMLSize--;
                charAt = xdmDDFTNDSGetXMLData.charAt(0);
            }
        }
        String concat = xdmDDFTNDSGetWbxmlData.concat(String.valueOf((char) (xdmDDFConvertString2WbxmlHex(str) - '@')));
        xdmDDFTNDSSetWbxmlSize(xdmDDFTNDSGetWbxmlSize + 1);
        xdmDDFTNDSSetWbxmlData(concat);
        xdmDDFTNDSSetXMLSize(xdmDDFTNDSGetXMLSize);
        xdmDDFTNDSSetXMLData(xdmDDFTNDSGetXMLData);
    }

    public static void xdmDDFProcessConvertAccessTypeElement() {
        int xdmDDFTNDSGetWbxmlSize = xdmDDFTNDSGetWbxmlSize();
        String xdmDDFTNDSGetWbxmlData = xdmDDFTNDSGetWbxmlData();
        String xdmDDFTNDSGetXMLData = xdmDDFTNDSGetXMLData();
        char charAt = xdmDDFTNDSGetXMLData.charAt(0);
        int xdmDDFTNDSGetXMLSize = xdmDDFTNDSGetXMLSize();
        String str = "";
        if (charAt == '<') {
            String substring = xdmDDFTNDSGetXMLData.substring(1);
            int i = xdmDDFTNDSGetXMLSize - 1;
            char charAt2 = substring.charAt(0);
            while (charAt2 != '/') {
                str = str.concat(String.valueOf(charAt2));
                substring = substring.substring(1);
                i--;
                charAt2 = substring.charAt(0);
            }
            xdmDDFTNDSGetXMLData = substring.substring(2);
            xdmDDFTNDSGetXMLSize = i - 2;
        }
        String concat = xdmDDFTNDSGetWbxmlData.concat(String.valueOf((char) (xdmDDFConvertString2WbxmlHex(str) - '@')));
        xdmDDFTNDSSetWbxmlSize(xdmDDFTNDSGetWbxmlSize + 1);
        xdmDDFTNDSSetWbxmlData(concat);
        xdmDDFTNDSSetXMLSize(xdmDDFTNDSGetXMLSize);
        xdmDDFTNDSSetXMLData(xdmDDFTNDSGetXMLData);
    }

    public static void xdmDDFConvertAddTndsCodePage() {
        int xdmDDFTNDSGetWbxmlSize = xdmDDFTNDSGetWbxmlSize();
        String xdmDDFTNDSGetWbxmlData = xdmDDFTNDSGetWbxmlData();
        int i = xdmDDFTNDSGetWbxmlSize - 1;
        String concat = xdmDDFTNDSGetWbxmlData.substring(0, i).concat(String.valueOf((char) 0)).concat(String.valueOf((char) 2)).concat(String.valueOf((char) xdmDDFTNDSGetWbxmlData.getBytes(Charset.defaultCharset())[i]));
        xdmDDFTNDSSetWbxmlSize(i + 1 + 1 + 1);
        xdmDDFTNDSSetWbxmlData(concat);
    }

    public static void xdmTndsParseFinish() {
        gTndsData = null;
        gstTagManage = null;
    }
}
