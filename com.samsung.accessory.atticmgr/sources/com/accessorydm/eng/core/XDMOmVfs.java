package com.accessorydm.eng.core;

import android.text.TextUtils;
import com.accessorydm.db.file.XDB;
import com.accessorydm.interfaces.XDMInterface;
import com.samsung.android.fotaprovider.log.Log;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class XDMOmVfs implements XDMInterface {
    private static final int OM_MAX_LEN = 512;
    private static int index;
    public XDMVnode root = null;
    public byte[] stdobj_space = new byte[40960];

    private void xdmOmVfsResetStdobj() {
        index = 0;
        this.stdobj_space = null;
        this.stdobj_space = new byte[40960];
    }

    public void xdmOmVfsDeleteStdobj() {
        index = 0;
        this.stdobj_space = null;
    }

    public static int xdmOmVfsInit(XDMOmVfs xDMOmVfs) {
        if (xDMOmVfs.root == null) {
            xDMOmVfs.root = xdmOmVfsCreateNewNode("/", true);
        }
        return xdmOmVfsLoadFs(xDMOmVfs);
    }

    public static XDMVnode xdmOmVfsCreateNewNode(String str, boolean z) {
        XDMVnode xDMVnode = new XDMVnode();
        if (z) {
            XDMOmAcl xDMOmAcl = new XDMOmAcl();
            xDMOmAcl.m_szServerid = "*";
            xDMOmAcl.ac = 27;
            XDMOmList xDMOmList = new XDMOmList();
            xDMOmList.data = xDMOmAcl;
            xDMOmList.next = null;
            xDMVnode.acl = xDMOmList;
        }
        xDMVnode.m_szName = str;
        xDMVnode.format = 6;
        xDMVnode.verno = 0;
        xDMVnode.size = 0;
        xDMVnode.vaddr = -1;
        xDMVnode.scope = 2;
        return xDMVnode;
    }

    public static int xdmOmVfsLoadFs(XDMOmVfs xDMOmVfs) {
        DataInputStream dataInputStream;
        int xdbGetFileIdObjectTreeInfo = XDB.xdbGetFileIdObjectTreeInfo();
        String xdbFileGetNameFromCallerID = XDB.xdbFileGetNameFromCallerID(xdbGetFileIdObjectTreeInfo);
        int xdbGetFileSize = (int) XDB.xdbGetFileSize(xdbGetFileIdObjectTreeInfo);
        if (xdbGetFileSize <= 0) {
            return 0;
        }
        byte[] bArr = new byte[xdbGetFileSize];
        XDB.xdbReadFile(xdbGetFileIdObjectTreeInfo, 0, xdbGetFileSize, bArr);
        try {
            dataInputStream = new DataInputStream(new FileInputStream(xdbFileGetNameFromCallerID));
        } catch (FileNotFoundException e) {
            Log.E(e.toString());
            dataInputStream = null;
        }
        int i = bArr[index];
        while (i == 66) {
            try {
                i = xdmOmVfsUnpackFsNode(xDMOmVfs, dataInputStream, i, xDMOmVfs.root, bArr, xdbGetFileSize);
                if (i == 0) {
                    if (dataInputStream != null) {
                        try {
                            dataInputStream.close();
                        } catch (IOException e2) {
                            Log.E(e2.toString());
                        }
                    }
                    index = 0;
                    return -4;
                }
            } catch (Exception e3) {
                Log.E(e3.toString());
                xDMOmVfs.xdmOmVfsResetStdobj();
                xdmOmVfsDeleteOmFile();
                return 0;
            }
        }
        if (dataInputStream != null) {
            try {
                dataInputStream.close();
            } catch (IOException e4) {
                Log.E(e4.toString());
            }
        }
        index = 0;
        int xdbGetFileIdObjectData = XDB.xdbGetFileIdObjectData();
        XDB.xdbReadFile(xdbGetFileIdObjectData, 0, (int) XDB.xdbGetFileSize(xdbGetFileIdObjectData), xDMOmVfs.stdobj_space);
        return 0;
    }

    public static int xdmOmVfsSaveFs(XDMOmVfs xDMOmVfs) throws IOException {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(XDB.xdbFileGetNameFromCallerID(XDB.xdbGetFileIdObjectTreeInfo())));
            for (XDMVnode xDMVnode = xDMOmVfs.root.childlist; xDMVnode != null; xDMVnode = xDMVnode.next) {
                dataOutputStream = xdmOmVfsPackFsNode(dataOutputStream, xDMVnode);
            }
            dataOutputStream.close();
        } catch (Exception e) {
            Log.E(e.toString());
        }
        XDB.xdbWriteFile(XDB.xdbGetFileIdObjectData(), 40960, xDMOmVfs.stdobj_space);
        return 0;
    }

    public static DataOutputStream xdmOmVfsPackFsNode(DataOutputStream dataOutputStream, XDMVnode xDMVnode) throws IOException {
        if (xDMVnode == null) {
            return dataOutputStream;
        }
        DataOutputStream xdmOmVfsPackNode = xdmOmVfsPackNode(xdmOmVfsPackStart(dataOutputStream), xDMVnode);
        for (XDMVnode xDMVnode2 = xDMVnode.childlist; xDMVnode2 != null; xDMVnode2 = xDMVnode2.next) {
            xdmOmVfsPackNode = xdmOmVfsPackFsNode(xdmOmVfsPackNode, xDMVnode2);
        }
        return xdmOmVfsPackEnd(xdmOmVfsPackNode);
    }

    public static DataOutputStream xdmOmVfsPackNode(DataOutputStream dataOutputStream, XDMVnode xDMVnode) throws IOException {
        int i = 0;
        int i2 = 0;
        for (XDMOmList xDMOmList = xDMVnode.acl; xDMOmList != null; xDMOmList = xDMOmList.next) {
            i2++;
        }
        DataOutputStream xdmOmVfsPackByte = xdmOmVfsPackByte(dataOutputStream, i2);
        for (XDMOmList xDMOmList2 = xDMVnode.acl; xDMOmList2 != null; xDMOmList2 = xDMOmList2.next) {
            XDMOmAcl xDMOmAcl = (XDMOmAcl) xDMOmList2.data;
            xdmOmVfsPackByte = xdmOmVfsPackByte(xdmOmVfsPackStr(xdmOmVfsPackByte, xDMOmAcl.m_szServerid), xDMOmAcl.ac);
        }
        DataOutputStream xdmOmVfsPackInt32 = xdmOmVfsPackInt32(xdmOmVfsPackStr(xdmOmVfsPackStr(xdmOmVfsPackInt32(xdmOmVfsPackStr(xdmOmVfsPackInt32(xdmOmVfsPackByte, xDMVnode.format), xDMVnode.m_szName), xDMVnode.size), xDMVnode.title), xDMVnode.m_szTstamp), xDMVnode.scope);
        for (XDMOmList xDMOmList3 = xDMVnode.type; xDMOmList3 != null; xDMOmList3 = xDMOmList3.next) {
            i++;
        }
        DataOutputStream xdmOmVfsPackByte2 = xdmOmVfsPackByte(xdmOmVfsPackInt32, i);
        if (i > 0) {
            for (XDMOmList xDMOmList4 = xDMVnode.type; xDMOmList4 != null; xDMOmList4 = xDMOmList4.next) {
                xdmOmVfsPackByte2 = xdmOmVfsPackStr(xdmOmVfsPackByte2, (String) xDMOmList4.data);
            }
        }
        return xdmOmVfsPackInt32(xdmOmVfsPackStr(xdmOmVfsPackInt16(xdmOmVfsPackByte2, xDMVnode.verno), xDMVnode.m_szDdfName), xDMVnode.vaddr);
    }

    public static DataOutputStream xdmOmVfsPackStart(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(66);
        return dataOutputStream;
    }

    public static DataOutputStream xdmOmVfsPackEnd(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(68);
        return dataOutputStream;
    }

    public static DataOutputStream xdmOmVfsPackByte(DataOutputStream dataOutputStream, int i) throws IOException {
        dataOutputStream.writeInt(i);
        return dataOutputStream;
    }

    public static DataOutputStream xdmOmVfsPackStr(DataOutputStream dataOutputStream, String str) throws IOException {
        int i;
        if (TextUtils.isEmpty(str)) {
            i = 0;
        } else {
            i = str.length();
        }
        dataOutputStream.writeInt(i);
        if (!TextUtils.isEmpty(str)) {
            try {
                dataOutputStream.write(str.getBytes());
            } catch (IOException e) {
                Log.E(e.toString());
            }
        }
        return dataOutputStream;
    }

    public static DataOutputStream xdmOmVfsPackInt32(DataOutputStream dataOutputStream, int i) throws IOException {
        dataOutputStream.writeInt(i);
        return dataOutputStream;
    }

    public static DataOutputStream xdmOmVfsPackInt16(DataOutputStream dataOutputStream, int i) throws IOException {
        dataOutputStream.writeInt(i);
        return dataOutputStream;
    }

    public static int xdmOmVfsUnpackFsNode(XDMOmVfs xDMOmVfs, DataInputStream dataInputStream, int i, XDMVnode xDMVnode, byte[] bArr, int i2) throws Exception {
        if (dataInputStream == null) {
            return 0;
        }
        DataInputStream dataInputStream2 = dataInputStream;
        int i3 = i;
        while (i3 != 68) {
            if (i3 == 66) {
                dataInputStream2.readByte();
                index++;
                XDMVnode xDMVnode2 = new XDMVnode();
                DataInputStream xdmOmVfsUnpackNode = xdmOmVfsUnpackNode(dataInputStream2, xDMVnode2);
                xdmOmVfsAppendNode(xDMVnode, xDMVnode2);
                byte b = bArr[index];
                while (b != 68) {
                    if (b == 66) {
                        b = xdmOmVfsUnpackFsNode(xDMOmVfs, xdmOmVfsUnpackNode, b, xDMVnode2, bArr, i2);
                    } else if (b != 68) {
                        return 0;
                    }
                }
                i3 = b;
                dataInputStream2 = xdmOmVfsUnpackNode;
            } else if (i3 != 68) {
                return 0;
            }
        }
        dataInputStream2.readByte();
        index++;
        int i4 = index;
        if (i4 == i2) {
            return 68;
        }
        return bArr[i4];
    }

    public static DataInputStream xdmOmVfsUnpackNode(DataInputStream dataInputStream, XDMVnode xDMVnode) throws Exception {
        int readInt = dataInputStream.readInt();
        index += 4;
        for (int i = 0; i < readInt; i++) {
            String xdmOmVfsUnpackStr = xdmOmVfsUnpackStr(dataInputStream);
            int readInt2 = dataInputStream.readInt();
            index += 4;
            XDMOmAcl xDMOmAcl = new XDMOmAcl();
            xDMOmAcl.m_szServerid = xdmOmVfsUnpackStr;
            xDMOmAcl.ac = readInt2;
            XDMOmList xDMOmList = new XDMOmList();
            xDMOmList.data = xDMOmAcl;
            xDMVnode.acl = xdmOmVfsAppendList(xDMVnode.acl, xDMOmList);
        }
        xDMVnode.format = dataInputStream.readInt();
        index += 4;
        xDMVnode.m_szName = xdmOmVfsUnpackStrDup(dataInputStream);
        xDMVnode.size = dataInputStream.readInt();
        index += 4;
        xDMVnode.title = xdmOmVfsUnpackStrDup(dataInputStream);
        xDMVnode.m_szTstamp = xdmOmVfsUnpackStrDup(dataInputStream);
        xDMVnode.scope = dataInputStream.readInt();
        index += 4;
        int readInt3 = dataInputStream.readInt();
        index += 4;
        if (readInt3 > 0) {
            for (int i2 = 0; i2 < readInt3; i2++) {
                String xdmOmVfsUnpackStr2 = xdmOmVfsUnpackStr(dataInputStream);
                XDMOmList xDMOmList2 = new XDMOmList();
                xDMOmList2.data = xdmOmVfsUnpackStr2;
                xDMVnode.type = xdmOmVfsAppendList(xDMVnode.type, xDMOmList2);
            }
        }
        xDMVnode.verno = dataInputStream.readInt();
        index += 4;
        xDMVnode.m_szDdfName = xdmOmVfsUnpackStrDup(dataInputStream);
        xDMVnode.vaddr = dataInputStream.readInt();
        index += 4;
        return dataInputStream;
    }

    public static String xdmOmVfsUnpackStr(DataInputStream dataInputStream) throws Exception {
        int readInt = dataInputStream.readInt();
        index += 4;
        if (readInt == 0) {
            return null;
        }
        if (readInt <= 512) {
            byte[] bArr = new byte[readInt];
            dataInputStream.read(bArr, 0, readInt);
            index += readInt;
            return new String(bArr, Charset.defaultCharset());
        }
        throw new Exception("OM_MAX_LEN over");
    }

    public static String xdmOmVfsUnpackStrDup(DataInputStream dataInputStream) throws Exception {
        int readInt = dataInputStream.readInt();
        index += 4;
        if (readInt == 0) {
            return null;
        }
        if (readInt <= 512) {
            byte[] bArr = new byte[readInt];
            dataInputStream.read(bArr, 0, readInt);
            index += readInt;
            return new String(bArr, Charset.defaultCharset());
        }
        throw new Exception("OM_MAX_LEN over");
    }

    public static XDMOmList xdmOmVfsAppendList(XDMOmList xDMOmList, XDMOmList xDMOmList2) {
        if (xDMOmList == null) {
            xDMOmList2.next = null;
            return xDMOmList2;
        }
        XDMOmList xDMOmList3 = xDMOmList;
        while (xDMOmList3.next != null) {
            xDMOmList3 = xDMOmList3.next;
        }
        xDMOmList2.next = null;
        xDMOmList3.next = xDMOmList2;
        return xDMOmList;
    }

    public static int xdmOmVfsAppendNode(XDMVnode xDMVnode, XDMVnode xDMVnode2) {
        if (xdmOmVfsHaveThisChild(xDMVnode, xDMVnode2)) {
            return -2;
        }
        if (xDMVnode.childlist == null) {
            xDMVnode.childlist = xDMVnode2;
            xDMVnode2.ptParentNode = xDMVnode;
            return 0;
        }
        XDMVnode xDMVnode3 = xDMVnode.childlist;
        while (xDMVnode3.next != null) {
            xDMVnode3 = xDMVnode3.next;
        }
        xDMVnode3.next = xDMVnode2;
        xDMVnode2.ptParentNode = xDMVnode;
        return 0;
    }

    public static boolean xdmOmVfsHaveThisChild(XDMVnode xDMVnode, XDMVnode xDMVnode2) {
        for (XDMVnode xDMVnode3 = xDMVnode != null ? xDMVnode.childlist : null; xDMVnode3 != null; xDMVnode3 = xDMVnode3.next) {
            if (xDMVnode3.m_szName.compareTo(xDMVnode2.m_szName) == 0) {
                return true;
            }
        }
        return false;
    }

    public static XDMVnode xdmOmVfsPath2Node(XDMOmVfs xDMOmVfs, String str) {
        XDMVnode xDMVnode = xDMOmVfs.root;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.compareTo(XDMInterface.XDM_BASE_PATH) == 0 || str.compareTo("./") == 0) {
            return xDMOmVfs.root;
        }
        String[] split = str.split("/");
        int length = split.length;
        int i = 1;
        XDMVnode xDMVnode2 = null;
        while (i < length) {
            xDMVnode2 = xdmOmVfsGetNode(xDMOmVfs, split[i], xDMVnode);
            if (xDMVnode2 == null) {
                return null;
            }
            i++;
            xDMVnode = xDMVnode2;
        }
        return xDMVnode2;
    }

    public static XDMVnode xdmOmVfsGetNode(XDMOmVfs xDMOmVfs, String str, XDMVnode xDMVnode) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.compareTo("/") == 0 || str.charAt(0) == 0) {
            return xDMOmVfs.root;
        }
        for (XDMVnode xDMVnode2 = xDMVnode != null ? xDMVnode.childlist : null; xDMVnode2 != null; xDMVnode2 = xDMVnode2.next) {
            if (str.equals(xDMVnode2.m_szName)) {
                return xDMVnode2;
            }
        }
        return null;
    }

    public static int xdmOmVfsCreatePath(XDMOmVfs xDMOmVfs, String str) {
        XDMVnode xDMVnode = xDMOmVfs.root;
        String[] split = str.split("/");
        int length = split.length;
        int i = split[0].compareTo(XDMInterface.XDM_BASE_PATH) == 0 ? 1 : 0;
        while (i < length) {
            String str2 = split[i];
            i++;
            if (i != length) {
                xDMVnode = xdmOmVfsGetNode(xDMOmVfs, str2, xDMVnode);
                if (xDMVnode == null) {
                    return -3;
                }
            } else if (xdmOmVfsGetNode(xDMOmVfs, str2, xDMVnode) != null) {
                return -2;
            } else {
                xdmOmVfsAppendNode(xDMVnode, xdmOmVfsCreateNewNode(str2, true));
                return 0;
            }
        }
        return 0;
    }

    public static int xdmOmVfsWriteObj(XDMOmVfs xDMOmVfs, String str, int i, int i2, Object obj, int i3) {
        int i4;
        XDMVnode xdmOmVfsPath2Node = xdmOmVfsPath2Node(xDMOmVfs, str);
        if (xdmOmVfsPath2Node == null) {
            return -1;
        }
        if (i2 == 0) {
            i4 = xdmOmVfsGetFreeVaddr(xDMOmVfs, i);
            if (i4 < 0) {
                return i4;
            }
            xdmOmVfsPath2Node.vaddr = i4;
            xdmOmVfsPath2Node.size = i;
        } else {
            i4 = xdmOmVfsPath2Node.vaddr;
        }
        int i5 = i2 + i3;
        if (i5 > i) {
            i3 -= i5 - i;
        }
        if (xdmOmVfsSaveFsData(xDMOmVfs, i4 + i2, obj, i3) != 0) {
            return -4;
        }
        return i3;
    }

    public static int xdmOmVfsGetFreeVaddr(XDMOmVfs xDMOmVfs, int i) {
        XDMVfspace xDMVfspace = new XDMVfspace();
        xdmOmVfsFindVaddr(xDMOmVfs, xDMOmVfs.root, xDMVfspace);
        int i2 = 0;
        if (xDMVfspace.i == 0) {
            return 0;
        }
        for (int i3 = xDMVfspace.i - 1; i3 >= 1; i3--) {
            int i4 = 0;
            while (i4 <= i3 - 1) {
                int i5 = i4 + 1;
                if (xDMVfspace.start[i5] < xDMVfspace.start[i4]) {
                    int i6 = xDMVfspace.start[i5];
                    int i7 = xDMVfspace.end[i5];
                    xDMVfspace.start[i5] = xDMVfspace.start[i4];
                    xDMVfspace.end[i5] = xDMVfspace.end[i4];
                    xDMVfspace.start[i4] = i6;
                    xDMVfspace.end[i4] = i7;
                }
                i4 = i5;
            }
        }
        if (xDMVfspace.start[0] > 0 && xDMVfspace.start[0] + 1 >= i) {
            return 0;
        }
        while (i2 < xDMVfspace.i - 1) {
            int i8 = i2 + 1;
            if ((xDMVfspace.start[i8] - xDMVfspace.end[i2]) - 1 >= i) {
                return xDMVfspace.end[i2];
            }
            i2 = i8;
        }
        if ((XDMInterface.XDM_MAX_SPACE_SIZE - ((long) xDMVfspace.end[xDMVfspace.i - 1])) - 1 >= ((long) i)) {
            return xDMVfspace.end[xDMVfspace.i - 1];
        }
        return -5;
    }

    public static void xdmOmVfsFindVaddr(XDMOmVfs xDMOmVfs, XDMVnode xDMVnode, XDMVfspace xDMVfspace) {
        for (XDMVnode xDMVnode2 = xDMVnode.childlist; xDMVnode2 != null; xDMVnode2 = xDMVnode2.next) {
            xdmOmVfsFindVaddr(xDMOmVfs, xDMVnode2, xDMVfspace);
        }
        if (xDMVnode.vaddr >= 0 && xDMVnode.size > 0) {
            xDMVfspace.start[xDMVfspace.i] = xDMVnode.vaddr;
            xDMVfspace.end[xDMVfspace.i] = xDMVnode.vaddr + xDMVnode.size;
            xDMVfspace.i++;
        }
    }

    public static int xdmOmVfsSaveFsData(XDMOmVfs xDMOmVfs, int i, Object obj, int i2) {
        byte[] bytes = new String(obj.toString()).getBytes(Charset.defaultCharset());
        int i3 = 0;
        while (i3 < i2 && bytes.length > i3) {
            xDMOmVfs.stdobj_space[i + i3] = bytes[i3];
            i3++;
        }
        return 0;
    }

    public static int xdmOmVfsLoadFsData(XDMOmVfs xDMOmVfs, int i, char[] cArr, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            cArr[i3] = (char) xDMOmVfs.stdobj_space[i + i3];
        }
        return 0;
    }

    public static int xdmOmVfsRemoveNode(XDMOmVfs xDMOmVfs, XDMVnode xDMVnode, boolean z) {
        XDMVnode xDMVnode2 = xDMVnode.childlist;
        if (xDMVnode2 != null) {
            if (!z) {
                return -4;
            }
            while (xDMVnode2 != null) {
                int xdmOmVfsRemoveNode = xdmOmVfsRemoveNode(xDMOmVfs, xDMVnode2, true);
                if (xdmOmVfsRemoveNode != 0) {
                    return xdmOmVfsRemoveNode;
                }
                if (xDMVnode.childlist == null) {
                    xDMVnode2 = null;
                } else {
                    xDMVnode2 = xDMVnode.childlist;
                }
            }
        }
        XDMVnode xdmOmVfsGetParent = xdmOmVfsGetParent(xDMOmVfs, xDMOmVfs.root, xDMVnode);
        if (xdmOmVfsGetParent == null) {
            return -4;
        }
        if (xdmOmVfsGetParent.childlist != xDMVnode) {
            XDMVnode xDMVnode3 = xdmOmVfsGetParent.childlist;
            while (true) {
                if (xDMVnode3.next == null) {
                    break;
                } else if (xDMVnode3.next == xDMVnode) {
                    xDMVnode3.next = xDMVnode.next;
                    break;
                } else {
                    xDMVnode3 = xDMVnode3.next;
                }
            }
        } else {
            xdmOmVfsGetParent.childlist = xDMVnode.next;
        }
        if (xDMVnode.acl != null) {
            xdmOmVfsDeleteAclList(xDMVnode.acl);
        }
        if (xDMVnode.type != null) {
            xdmOmVfsDeleteMimeList(xDMVnode.type);
        }
        xDMVnode.m_szName = null;
        xDMVnode.title = null;
        xDMVnode.m_szTstamp = null;
        xDMVnode.m_szDdfName = null;
        xDMVnode.next = null;
        xDMVnode.ptParentNode = null;
        return 0;
    }

    public static XDMVnode xdmOmVfsGetParent(XDMOmVfs xDMOmVfs, XDMVnode xDMVnode, XDMVnode xDMVnode2) {
        for (XDMVnode xDMVnode3 = xDMVnode.childlist; xDMVnode3 != null; xDMVnode3 = xDMVnode3.next) {
            if (xDMVnode3 == xDMVnode2) {
                return xDMVnode;
            }
        }
        for (XDMVnode xDMVnode4 = xDMVnode.childlist; xDMVnode4 != null; xDMVnode4 = xDMVnode4.next) {
            XDMVnode xdmOmVfsGetParent = xdmOmVfsGetParent(xDMOmVfs, xDMVnode4, xDMVnode2);
            if (xdmOmVfsGetParent != null) {
                return xdmOmVfsGetParent;
            }
        }
        return null;
    }

    public static void xdmOmVfsDeleteAclList(XDMOmList xDMOmList) {
        while (xDMOmList != null) {
            xDMOmList = xDMOmList.next;
        }
    }

    public static void xdmOmVfsDeleteMimeList(XDMOmList xDMOmList) {
        while (xDMOmList != null) {
            XDMOmList xDMOmList2 = xDMOmList.next;
            xDMOmList.data = null;
            xDMOmList = xDMOmList2;
        }
    }

    public static int xdmOmVfsGetData(XDMOmVfs xDMOmVfs, XDMVnode xDMVnode, char[] cArr) {
        if (xDMVnode.size <= 0 || xDMVnode.vaddr < 0 || xdmOmVfsLoadFsData(xDMOmVfs, xDMVnode.vaddr, cArr, xDMVnode.size) != 0) {
            return -4;
        }
        return 0;
    }

    public static int xdmOmVfsSetData(XDMOmVfs xDMOmVfs, XDMVnode xDMVnode, Object obj, int i) {
        int xdmOmVfsGetFreeVaddr = xdmOmVfsGetFreeVaddr(xDMOmVfs, i);
        if (xdmOmVfsGetFreeVaddr < 0) {
            return xdmOmVfsGetFreeVaddr;
        }
        xDMVnode.vaddr = xdmOmVfsGetFreeVaddr;
        xDMVnode.size = i;
        return xdmOmVfsSaveFsData(xDMOmVfs, xdmOmVfsGetFreeVaddr, obj, i) != 0 ? -4 : 0;
    }

    public static void xdmOmVfsEnd(XDMOmVfs xDMOmVfs) {
        xdmOmVfsDeleteVfs(xDMOmVfs.root);
    }

    public static void xdmOmVfsDeleteStdobj(XDMOmVfs xDMOmVfs) {
        xDMOmVfs.xdmOmVfsDeleteStdobj();
    }

    public static void xdmOmVfsDeleteVfs(XDMVnode xDMVnode) {
        XDMVnode xDMVnode2 = xDMVnode.childlist;
        while (xDMVnode2 != null) {
            XDMVnode xDMVnode3 = xDMVnode2.next;
            xdmOmVfsDeleteVfs(xDMVnode2);
            xDMVnode2 = xDMVnode3;
        }
        if (xDMVnode.acl != null) {
            xdmOmVfsDeleteAclList(xDMVnode.acl);
        }
        if (xDMVnode.type != null) {
            xdmOmVfsDeleteMimeList(xDMVnode.type);
        }
        xDMVnode.m_szName = null;
        xDMVnode.title = null;
        xDMVnode.m_szTstamp = null;
        xDMVnode.m_szDdfName = null;
        xDMVnode.childlist = null;
    }

    public static void xdmOmVfsDeleteOmFile() {
        XDB.xdbDeleteFile(XDB.xdbGetFileIdObjectTreeInfo());
        XDB.xdbDeleteFile(XDB.xdbGetFileIdObjectData());
    }
}
