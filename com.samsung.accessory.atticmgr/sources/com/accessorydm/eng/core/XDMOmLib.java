package com.accessorydm.eng.core;

import android.text.TextUtils;
import com.accessorydm.interfaces.XDMInterface;
import com.samsung.android.fotaprovider.log.Log;
import java.io.IOException;

public class XDMOmLib extends XDMOmVfs {
    public static int xdmOmInit(XDMOmTree xDMOmTree) {
        return xdmOmVfsInit(xDMOmTree.vfs) == 0 ? 0 : -3;
    }

    public static void xdmOmEnd(XDMOmTree xDMOmTree) {
        if (xDMOmTree != null) {
            try {
                xdmOmVfsSaveFs(xDMOmTree.vfs);
            } catch (IOException e) {
                Log.E(e.toString());
            }
        }
    }

    public static void xdmOmSetServerId(XDMOmTree xDMOmTree, String str) {
        if (!TextUtils.isEmpty(str) && str.charAt(0) != 0 && str.length() <= 19) {
            xDMOmTree.m_szServerId = str;
        }
    }

    public static XDMVnode xdmOmGetNodeProp(XDMOmTree xDMOmTree, String str) {
        return xdmOmVfsPath2Node(xDMOmTree.vfs, str);
    }

    public static int xdmOmWrite(XDMOmTree xDMOmTree, String str, int i, int i2, Object obj, int i3) {
        xdmOmVfsCreatePath(xDMOmTree.vfs, str);
        if (obj == null || i3 <= 0 || xdmOmVfsWriteObj(xDMOmTree.vfs, str, i, i2, obj, i3) >= 0) {
            return i3;
        }
        return -3;
    }

    public static int xdmOmRead(XDMOmTree xDMOmTree, String str, int i, char[] cArr, int i2) {
        if (!xdmOmvfsCheckPath(xDMOmTree.vfs, str)) {
            return -1;
        }
        int xdmOmvfsReadObj = xdmOmvfsReadObj(xDMOmTree.vfs, str, i, cArr, i2);
        if (xdmOmvfsReadObj < 0) {
            return -3;
        }
        return xdmOmvfsReadObj;
    }

    public static boolean xdmOmvfsCheckPath(XDMOmVfs xDMOmVfs, String str) {
        return xdmOmVfsPath2Node(xDMOmVfs, str) != null;
    }

    public static int xdmOmvfsReadObj(XDMOmVfs xDMOmVfs, String str, int i, char[] cArr, int i2) {
        XDMVnode xdmOmVfsPath2Node = xdmOmVfsPath2Node(xDMOmVfs, str);
        if (xdmOmVfsPath2Node == null || xdmOmVfsPath2Node.size <= 0 || xdmOmVfsPath2Node.vaddr < 0) {
            return -4;
        }
        int i3 = i2 + i;
        if (i3 > xdmOmVfsPath2Node.size) {
            i2 -= i3 - xdmOmVfsPath2Node.size;
        }
        if (xdmOmVfsLoadFsData(xDMOmVfs, xdmOmVfsPath2Node.vaddr + i, cArr, i2) != 0) {
            return -4;
        }
        return i2;
    }

    public static int xdmOmDelete(XDMOmTree xDMOmTree, String str, boolean z) {
        XDMVnode xdmOmVfsPath2Node = xdmOmVfsPath2Node(xDMOmTree.vfs, str);
        if (xdmOmVfsPath2Node == null) {
            return -3;
        }
        if (!xdmOmCheckAcl(xDMOmTree, xdmOmVfsPath2Node, 2)) {
            return -5;
        }
        if (xdmOmVfsRemoveNode(xDMOmTree.vfs, xdmOmVfsPath2Node, z) != 0) {
            return -3;
        }
        return 0;
    }

    public static boolean xdmOmCheckAcl(XDMOmTree xDMOmTree, XDMVnode xDMVnode, int i) {
        if (xDMOmTree == null || xDMVnode == null || !xdmOmCheckNodeAcl(xDMVnode, i, xDMOmTree.m_szServerId)) {
            return false;
        }
        return true;
    }

    public static boolean xdmOmCheckNodeAcl(XDMVnode xDMVnode, int i, String str) {
        XDMOmList xDMOmList = xDMVnode.acl;
        if (xDMOmList == null) {
            return false;
        }
        while (xDMOmList != null) {
            XDMOmAcl xDMOmAcl = (XDMOmAcl) xDMOmList.data;
            if (xDMOmAcl.m_szServerid.compareTo(str) == 0 || xDMOmAcl.m_szServerid.compareTo("*") == 0) {
                if (xDMOmAcl.ac == 0) {
                    return xdmOmCheckNodeAcl(xDMVnode.ptParentNode, i, str);
                }
                if ((xDMOmAcl.ac & i) == i) {
                    return true;
                }
            }
            xDMOmList = xDMOmList.next;
        }
        return false;
    }

    public static int xdmOmGetChild(XDMOmTree xDMOmTree, String str, String[] strArr, int i) {
        XDMVnode xdmOmVfsPath2Node = xdmOmVfsPath2Node(xDMOmTree.vfs, str);
        if (xdmOmVfsPath2Node == null) {
            return -6;
        }
        XDMVnode xDMVnode = xdmOmVfsPath2Node.childlist;
        int i2 = 0;
        while (xDMVnode != null) {
            if (i2 >= i) {
                return i;
            }
            if (TextUtils.isEmpty(xDMVnode.m_szName)) {
                return -3;
            }
            strArr[i2] = xDMVnode.m_szName;
            xDMVnode = xDMVnode.next;
            i2++;
        }
        return i2;
    }

    public static boolean xdmOmCheckAclCurrentNode(XDMOmTree xDMOmTree, String str, int i) {
        String str2;
        XDMVnode xDMVnode = xDMOmTree.vfs.root;
        String str3 = xDMOmTree.m_szServerId;
        int indexOf = str.indexOf("/");
        String str4 = null;
        if (indexOf >= 0) {
            str4 = str.substring(0, indexOf);
            str2 = str.substring(indexOf + 1);
        } else {
            str2 = null;
        }
        Log.I("strnodename :" + str4 + ", ptr :" + str2);
        XDMVnode xDMVnode2 = xDMVnode;
        boolean z = false;
        while (!TextUtils.isEmpty(str2)) {
            if (TextUtils.isEmpty(str4)) {
                if (z || !(str2.charAt(0) == '/' || str2.charAt(0) == '.')) {
                    int indexOf2 = str2.indexOf("/");
                    if (indexOf2 >= 0) {
                        str4 = str2.substring(0, indexOf2);
                        str2 = str2.substring(indexOf2 + 1);
                        Log.I("strnodename :" + str4 + ", ptr :" + str2);
                    }
                } else {
                    XDMVnode xDMVnode3 = xDMOmTree.vfs.root;
                    z = true;
                }
            }
            if (TextUtils.isEmpty(str2) && i == 1) {
                return true;
            }
            if (str4.equals(XDMInterface.XDM_BASE_PATH)) {
                xDMVnode2 = xDMOmTree.vfs.root;
            } else {
                xDMVnode2 = XDMOmVfs.xdmOmVfsGetNode(xDMOmTree.vfs, str4, xDMVnode2);
                if (xDMVnode2 == null) {
                    return false;
                }
            }
            if (TextUtils.isEmpty(str2) && !xdmOmCheckNodeAcl(xDMVnode2, i, str3)) {
                return false;
            }
            int indexOf3 = str2.indexOf("/");
            if (indexOf3 < 0) {
                return true;
            }
            String substring = str2.substring(0, indexOf3);
            str2 = str2.substring(indexOf3 + 1);
            Log.I("strnodename :" + substring + ", ptr :" + str2);
            str4 = substring;
        }
        return true;
    }

    public static void xdmOmMakeParentPath(String str, char[] cArr) {
        if (!TextUtils.isEmpty(str)) {
            int length = str.length() - 1;
            while (true) {
                if (length < 0) {
                    length = -1;
                    break;
                } else if (str.charAt(length) == '/') {
                    break;
                } else {
                    length--;
                }
            }
            if (length < 0) {
                cArr[0] = 0;
                return;
            }
            int i = 0;
            while (i < length) {
                cArr[i] = str.charAt(i);
                i++;
            }
            cArr[i] = 0;
        }
    }

    public static boolean xdmOmProcessCmdImplicitAdd(Object obj, String str, int i, int i2) {
        XDMOmTree xDMOmTree = (XDMOmTree) obj;
        if (!str.contains(XDMInterface.XDM_BASE_PATH)) {
            Log.E("ROOT NODE not found");
            return false;
        } else if (i2 > 0 && !xdmOmCheckNodePathDepth(str)) {
            return false;
        } else {
            char[] cArr = new char[256];
            xdmOmMakeParentPath(str, cArr);
            String xdmLibCharToString = XDMMem.xdmLibCharToString(cArr);
            if (xdmOmGetNodeProp(xDMOmTree, xdmLibCharToString) == null) {
                xdmOmProcessCmdImplicitAdd(xDMOmTree, xdmLibCharToString, i, 0);
            }
            xdmOmWrite(xDMOmTree, str, 0, 0, "", 0);
            xdmOmDefaultACL(xDMOmTree, str, i, 2);
            return true;
        }
    }

    public static boolean xdmOmCheckNodePathDepth(String str) {
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str.charAt(i2) == '/') {
                i++;
            }
        }
        return i <= 15;
    }

    public static void xdmOmDefaultACL(Object obj, String str, int i, int i2) {
        XDMVnode xdmOmGetNodeProp = xdmOmGetNodeProp((XDMOmTree) obj, str);
        if (xdmOmGetNodeProp != null) {
            ((XDMOmAcl) xdmOmGetNodeProp.acl.data).ac = i;
            xdmOmGetNodeProp.scope = i2;
            return;
        }
        Log.E("Not Exist");
    }

    public static int xdmOmDeleteImplicit(XDMOmTree xDMOmTree, String str, boolean z) {
        XDMVnode xdmOmVfsPath2Node = xdmOmVfsPath2Node(xDMOmTree.vfs, str);
        if (xdmOmVfsPath2Node != null && xdmOmVfsRemoveNode(xDMOmTree.vfs, xdmOmVfsPath2Node, z) == 0) {
            return 0;
        }
        return -3;
    }
}
