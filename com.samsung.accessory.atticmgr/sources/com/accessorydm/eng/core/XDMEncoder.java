package com.accessorydm.eng.core;

import android.text.TextUtils;
import com.accessorydm.adapter.XDMDevinfAdapter;
import com.accessorydm.eng.parser.XDMParserAdd;
import com.accessorydm.eng.parser.XDMParserAlert;
import com.accessorydm.eng.parser.XDMParserAnchor;
import com.accessorydm.eng.parser.XDMParserAtomic;
import com.accessorydm.eng.parser.XDMParserCopy;
import com.accessorydm.eng.parser.XDMParserCred;
import com.accessorydm.eng.parser.XDMParserDelete;
import com.accessorydm.eng.parser.XDMParserExec;
import com.accessorydm.eng.parser.XDMParserGet;
import com.accessorydm.eng.parser.XDMParserItem;
import com.accessorydm.eng.parser.XDMParserMap;
import com.accessorydm.eng.parser.XDMParserMapItem;
import com.accessorydm.eng.parser.XDMParserMem;
import com.accessorydm.eng.parser.XDMParserMeta;
import com.accessorydm.eng.parser.XDMParserPcdata;
import com.accessorydm.eng.parser.XDMParserPut;
import com.accessorydm.eng.parser.XDMParserReplace;
import com.accessorydm.eng.parser.XDMParserResults;
import com.accessorydm.eng.parser.XDMParserStatus;
import com.accessorydm.eng.parser.XDMParserSync;
import com.accessorydm.eng.parser.XDMParserSyncheader;
import com.accessorydm.interfaces.XDMInterface;
import com.samsung.android.fotaprovider.log.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class XDMEncoder extends XDMWbxmlEncoder implements XDMInterface {
    ByteArrayOutputStream out;

    public XDMEncoder() {
    }

    public XDMEncoder(ByteArrayOutputStream byteArrayOutputStream) {
        Log.I("XDMEncoder init");
        this.out = byteArrayOutputStream;
    }

    private int _START_E(int i) {
        return !xdmWbxEncStartElement(i, true) ? 5 : 0;
    }

    private int _ADD_C(String str) {
        return !xdmWbxEncAddContent(str) ? 5 : 0;
    }

    private int _END_E() {
        return !xdmWbxEncEndElement() ? 5 : 0;
    }

    private void _ADD_E(int i, String str) {
        _START_E(i);
        _ADD_C(str);
        _END_E();
    }

    public int _ADD_BE(int i) {
        return !xdmWbxEncStartElement(i, false) ? 5 : 0;
    }

    public void xdmEncInit(ByteArrayOutputStream byteArrayOutputStream) {
        xdmWbxEncInit(byteArrayOutputStream);
    }

    public int xdmEncStartSyncml(int i, int i2, String str, int i3) {
        if (!xdmWbxEncStartDocument(i, i2, str, i3)) {
            return 5;
        }
        _START_E(45);
        return 0;
    }

    public int xdmEncEndSyncml() {
        if (xdmWbxEncEndElement() && xdmWbxEncEndDocument()) {
            return 0;
        }
        return 5;
    }

    public int xdmEncStartAtomic(XDMParserAtomic xDMParserAtomic) {
        int xdmEncAddMeta;
        if (xDMParserAtomic == null) {
            return 6;
        }
        _START_E(8);
        if (xDMParserAtomic.cmdid >= 0) {
            _ADD_E(11, String.valueOf(xDMParserAtomic.cmdid));
        }
        if (xDMParserAtomic.is_noresp >= 0) {
            _ADD_BE(29);
        }
        if (xDMParserAtomic.meta == null || (xdmEncAddMeta = xdmEncAddMeta(xDMParserAtomic.meta)) == 0) {
            return 0;
        }
        return xdmEncAddMeta;
    }

    public int xdmEncEndAtomic() {
        _END_E();
        return 0;
    }

    public int xdmEncAddMeta(XDMParserMeta xDMParserMeta) {
        int xdmEncAddMetinfMem;
        int xdmEncAddMetinfEmi;
        int xdmEncAddMetinfAnchor;
        if (xDMParserMeta == null) {
            return 6;
        }
        _START_E(26);
        if (!xdmWbxEncAddSwitchpage(1)) {
            return 5;
        }
        if (!TextUtils.isEmpty(xDMParserMeta.m_szFormat)) {
            _ADD_E(7, xDMParserMeta.m_szFormat);
        }
        if (!TextUtils.isEmpty(xDMParserMeta.m_szType)) {
            _ADD_E(19, xDMParserMeta.m_szType);
        }
        if (!TextUtils.isEmpty(xDMParserMeta.m_szMark)) {
            _ADD_E(11, xDMParserMeta.m_szMark);
        }
        if (xDMParserMeta.size > 0) {
            _ADD_E(18, String.valueOf(xDMParserMeta.size));
        }
        if (!TextUtils.isEmpty(xDMParserMeta.m_szVersion)) {
            _ADD_E(20, xDMParserMeta.m_szVersion);
        }
        if (!TextUtils.isEmpty(xDMParserMeta.m_szNextNonce)) {
            _ADD_E(16, new String(xDMParserMeta.m_szNextNonce));
        }
        if (xDMParserMeta.maxmsgsize > 0) {
            _ADD_E(12, String.valueOf(xDMParserMeta.maxmsgsize));
        }
        if (xDMParserMeta.maxobjsize > 0) {
            _ADD_E(21, String.valueOf(xDMParserMeta.maxobjsize));
        }
        if (xDMParserMeta.anchor != null && !TextUtils.isEmpty(xDMParserMeta.anchor.m_szLast) && !TextUtils.isEmpty(xDMParserMeta.anchor.m_szNext) && (xdmEncAddMetinfAnchor = xdmEncAddMetinfAnchor(xDMParserMeta.anchor)) != 0) {
            return xdmEncAddMetinfAnchor;
        }
        if (!TextUtils.isEmpty(xDMParserMeta.m_szEmi) && (xdmEncAddMetinfEmi = xdmEncAddMetinfEmi(xDMParserMeta.m_szEmi)) != 0) {
            return xdmEncAddMetinfEmi;
        }
        if (xDMParserMeta.mem != null && (xdmEncAddMetinfMem = xdmEncAddMetinfMem(xDMParserMeta.mem)) != 0) {
            return xdmEncAddMetinfMem;
        }
        if (!xdmWbxEncAddSwitchpage(0)) {
            return 5;
        }
        _END_E();
        return 0;
    }

    public int xdmEncAddItem(XDMParserItem xDMParserItem) {
        int xdmEncAddMeta;
        int xdmEncAddSource;
        int xdmEncAddTarget;
        if (xDMParserItem == null) {
            return 6;
        }
        _START_E(20);
        if (!TextUtils.isEmpty(xDMParserItem.m_szTarget) && (xdmEncAddTarget = xdmEncAddTarget(xDMParserItem.m_szTarget)) != 0) {
            return xdmEncAddTarget;
        }
        if (!TextUtils.isEmpty(xDMParserItem.m_szSource) && (xdmEncAddSource = xdmEncAddSource(xDMParserItem.m_szSource)) != 0) {
            return xdmEncAddSource;
        }
        if (xDMParserItem.meta != null && (xdmEncAddMeta = xdmEncAddMeta(xDMParserItem.meta)) != 0) {
            return xdmEncAddMeta;
        }
        if (xDMParserItem.data != null) {
            if (xDMParserItem.data.type == 0) {
                _ADD_E(15, xdmEncPcdataGetString(xDMParserItem.data));
            } else if (xDMParserItem.data.type == 1) {
                _START_E(15);
                try {
                    if (!xdmWbxEncAddOpaque(xDMParserItem.data.data, xDMParserItem.data.size)) {
                        return 5;
                    }
                } catch (IOException e) {
                    Log.E(e.toString());
                }
                _END_E();
            } else if (xDMParserItem.data.type == 2) {
                _START_E(15);
                if (!xdmWbxEncAddSwitchpage(1)) {
                    return 5;
                }
                int xdmEncAddMetinfAnchor = xdmEncAddMetinfAnchor(xDMParserItem.data.anchor);
                if (xdmEncAddMetinfAnchor != 0) {
                    return xdmEncAddMetinfAnchor;
                }
                if (!xdmWbxEncAddSwitchpage(0)) {
                    return 5;
                }
                _END_E();
            }
        }
        _END_E();
        return 0;
    }

    public int xdmEncAddMapItem(XDMParserMapItem xDMParserMapItem) {
        int xdmEncAddSource;
        int xdmEncAddTarget;
        if (xDMParserMapItem == null) {
            return 6;
        }
        _START_E(25);
        if (!TextUtils.isEmpty(xDMParserMapItem.m_szTarget) && (xdmEncAddTarget = xdmEncAddTarget(xDMParserMapItem.m_szTarget)) != 0) {
            return xdmEncAddTarget;
        }
        if (!TextUtils.isEmpty(xDMParserMapItem.m_szSource) && (xdmEncAddSource = xdmEncAddSource(xDMParserMapItem.m_szSource)) != 0) {
            return xdmEncAddSource;
        }
        _END_E();
        return 0;
    }

    public int xdmEncAddCred(XDMParserCred xDMParserCred) {
        int xdmEncAddMeta;
        _START_E(14);
        if (xDMParserCred.meta != null && (xdmEncAddMeta = xdmEncAddMeta(xDMParserCred.meta)) != 0) {
            return xdmEncAddMeta;
        }
        if (!TextUtils.isEmpty(xDMParserCred.m_szData)) {
            _ADD_E(15, xDMParserCred.m_szData);
        }
        _END_E();
        return 0;
    }

    public int xdmEncAddSyncHeader(XDMParserSyncheader xDMParserSyncheader) {
        int xdmEncAddMeta;
        int xdmEncAddCred;
        int xdmEncAddTarget;
        _START_E(44);
        if (!TextUtils.isEmpty(xDMParserSyncheader.m_szVerdtd)) {
            _ADD_E(49, xDMParserSyncheader.m_szVerdtd);
        }
        if (!TextUtils.isEmpty(xDMParserSyncheader.m_szVerproto)) {
            _ADD_E(50, xDMParserSyncheader.m_szVerproto);
        }
        if (!TextUtils.isEmpty(xDMParserSyncheader.m_szSessionId)) {
            _ADD_E(37, xDMParserSyncheader.m_szSessionId);
        }
        if (xDMParserSyncheader.msgid > 0) {
            _ADD_E(27, String.valueOf(xDMParserSyncheader.msgid));
        }
        if (!TextUtils.isEmpty(xDMParserSyncheader.m_szRespUri)) {
            _ADD_E(33, xDMParserSyncheader.m_szRespUri);
        }
        if (xDMParserSyncheader.is_noresp > 0) {
            _ADD_BE(29);
        }
        if (!TextUtils.isEmpty(xDMParserSyncheader.m_szTarget) && (xdmEncAddTarget = xdmEncAddTarget(xDMParserSyncheader.m_szTarget)) != 0) {
            return xdmEncAddTarget;
        }
        if (!TextUtils.isEmpty(xDMParserSyncheader.m_szSource)) {
            if (TextUtils.isEmpty(xDMParserSyncheader.m_szLocname)) {
                int xdmEncAddSource = xdmEncAddSource(xDMParserSyncheader.m_szSource);
                if (xdmEncAddSource != 0) {
                    return xdmEncAddSource;
                }
            } else {
                int xdmEncAddSourceWithLocname = xdmEncAddSourceWithLocname(xDMParserSyncheader.m_szSource, xDMParserSyncheader.m_szLocname);
                if (xdmEncAddSourceWithLocname != 0) {
                    return xdmEncAddSourceWithLocname;
                }
            }
        }
        if (xDMParserSyncheader.cred != null && ((xDMParserSyncheader.cred.meta != null || !TextUtils.isEmpty(xDMParserSyncheader.cred.m_szData)) && (xdmEncAddCred = xdmEncAddCred(xDMParserSyncheader.cred)) != 0)) {
            return xdmEncAddCred;
        }
        if (xDMParserSyncheader.meta != null && (xdmEncAddMeta = xdmEncAddMeta(xDMParserSyncheader.meta)) != 0) {
            return xdmEncAddMeta;
        }
        _END_E();
        return 0;
    }

    public int xdmEncAddTarget(String str) {
        if (TextUtils.isEmpty(str)) {
            return 6;
        }
        _START_E(46);
        _ADD_E(23, str);
        _END_E();
        return 0;
    }

    public int xdmEncAddTargetparent(String str) {
        if (TextUtils.isEmpty(str)) {
            return 6;
        }
        _START_E(58);
        _ADD_E(23, str);
        _END_E();
        return 0;
    }

    public int xdmEncAddSource(String str) {
        if (TextUtils.isEmpty(str)) {
            return 6;
        }
        _START_E(39);
        _ADD_E(23, str);
        _END_E();
        return 0;
    }

    public int xdmEncAddSourceWithLocname(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return 6;
        }
        _START_E(39);
        _ADD_E(23, str);
        _ADD_E(22, str2);
        _END_E();
        return 0;
    }

    public int xdmEncStartSyncbody() {
        _START_E(43);
        return 0;
    }

    public int xdmEncEndSyncbody(boolean z) {
        if (z) {
            _ADD_BE(18);
        }
        _END_E();
        return 0;
    }

    public int xdmEncStartSync(XDMParserSync xDMParserSync) {
        int xdmEncAddSource;
        int xdmEncAddTarget;
        int xdmEncAddMeta;
        int xdmEncAddCred;
        if (xDMParserSync == null) {
            return 6;
        }
        _START_E(42);
        if (xDMParserSync.cmdid > 0) {
            _ADD_E(11, String.valueOf(xDMParserSync.cmdid));
        }
        if (xDMParserSync.cred != null && (xdmEncAddCred = xdmEncAddCred(xDMParserSync.cred)) != 0) {
            return xdmEncAddCred;
        }
        if (xDMParserSync.is_noresp) {
            _ADD_BE(29);
        }
        if (xDMParserSync.is_noresults) {
            _ADD_BE(30);
        }
        if (xDMParserSync.meta != null && (xdmEncAddMeta = xdmEncAddMeta(xDMParserSync.meta)) != 0) {
            return xdmEncAddMeta;
        }
        if (!TextUtils.isEmpty(xDMParserSync.m_szTarget) && (xdmEncAddTarget = xdmEncAddTarget(xDMParserSync.m_szTarget)) != 0) {
            return xdmEncAddTarget;
        }
        if (!TextUtils.isEmpty(xDMParserSync.m_szSource) && (xdmEncAddSource = xdmEncAddSource(xDMParserSync.m_szSource)) != 0) {
            return xdmEncAddSource;
        }
        if (xDMParserSync.numofchanges < 0) {
            return 0;
        }
        _ADD_E(51, String.valueOf(xDMParserSync.numofchanges));
        return 0;
    }

    public int xdmEncEndSync() {
        _END_E();
        return 0;
    }

    public int xdmEncAddAlert(XDMParserAlert xDMParserAlert) {
        int xdmEncAddItemlist;
        int xdmEncAddCred;
        if (xDMParserAlert == null || xDMParserAlert.cmdid < 0 || TextUtils.isEmpty(xDMParserAlert.m_szData)) {
            return 6;
        }
        _START_E(6);
        if (xDMParserAlert.cmdid > 0) {
            _ADD_E(11, String.valueOf(xDMParserAlert.cmdid));
        }
        if (!TextUtils.isEmpty(xDMParserAlert.m_szCorrelator)) {
            _ADD_E(60, xDMParserAlert.m_szCorrelator);
        }
        if (!TextUtils.isEmpty(xDMParserAlert.m_szData)) {
            _ADD_E(15, xDMParserAlert.m_szData);
        }
        if (xDMParserAlert.cred != null && (xdmEncAddCred = xdmEncAddCred(xDMParserAlert.cred)) != 0) {
            return xdmEncAddCred;
        }
        if (xDMParserAlert.is_noresp > 0) {
            _ADD_BE(29);
        }
        if (xDMParserAlert.itemlist != null && (xdmEncAddItemlist = xdmEncAddItemlist(xDMParserAlert.itemlist)) != 0) {
            return xdmEncAddItemlist;
        }
        _END_E();
        return 0;
    }

    public int xdmEncAddElelist(XDMList xDMList, int i) {
        if (xDMList == null) {
            return 6;
        }
        String str = (String) XDMList.xdmListGetItem(xDMList);
        XDMList xdmListGetItemPtr = XDMList.xdmListGetItemPtr(xDMList);
        while (!TextUtils.isEmpty(str)) {
            if (!TextUtils.isEmpty(str)) {
                _ADD_E(i, str);
            }
            str = (String) XDMList.xdmListGetItem(xdmListGetItemPtr);
            xdmListGetItemPtr = XDMList.xdmListGetItemPtr(xdmListGetItemPtr);
        }
        return 0;
    }

    public int xdmEncAddStatus(XDMParserStatus xDMParserStatus) {
        int xdmEncAddItemlist;
        int xdmEncAddCred;
        if (xDMParserStatus == null) {
            return 6;
        }
        Log.I("xdmEncAddStatus cmd.cmdid = " + xDMParserStatus.cmdid);
        Log.I("xdmEncAddStatus cmd.msgref = " + xDMParserStatus.m_szMsgRef);
        Log.I("xdmEncAddStatus cmd.cmd = " + xDMParserStatus.m_szCmd);
        Log.H("xdmEncAddStatus cmd.data = " + xDMParserStatus.m_szData);
        if (xDMParserStatus.cmdid < 0 || TextUtils.isEmpty(xDMParserStatus.m_szMsgRef) || TextUtils.isEmpty(xDMParserStatus.m_szData)) {
            return 6;
        }
        _START_E(41);
        if (xDMParserStatus.cmdid > 0) {
            _ADD_E(11, String.valueOf(xDMParserStatus.cmdid));
        }
        if (!TextUtils.isEmpty(xDMParserStatus.m_szMsgRef)) {
            _ADD_E(28, xDMParserStatus.m_szMsgRef);
        }
        if (!TextUtils.isEmpty(xDMParserStatus.m_szCmdRef)) {
            _ADD_E(12, xDMParserStatus.m_szCmdRef);
        }
        if (!TextUtils.isEmpty(xDMParserStatus.m_szCmd)) {
            _ADD_E(10, xDMParserStatus.m_szCmd);
        }
        if (xDMParserStatus.targetref != null) {
            xdmEncAddElelist(xDMParserStatus.targetref, 47);
        }
        if (xDMParserStatus.sourceref != null) {
            xdmEncAddElelist(xDMParserStatus.sourceref, 40);
        }
        if (xDMParserStatus.cred != null && (xdmEncAddCred = xdmEncAddCred(xDMParserStatus.cred)) != 0) {
            return xdmEncAddCred;
        }
        if (xDMParserStatus.chal != null) {
            _START_E(9);
            int xdmEncAddMeta = xdmEncAddMeta(xDMParserStatus.chal);
            if (xdmEncAddMeta != 0) {
                return xdmEncAddMeta;
            }
            _END_E();
        }
        if (!TextUtils.isEmpty(xDMParserStatus.m_szData)) {
            _ADD_E(15, xDMParserStatus.m_szData);
        }
        if (xDMParserStatus.itemlist != null && (xdmEncAddItemlist = xdmEncAddItemlist(xDMParserStatus.itemlist)) != 0) {
            return xdmEncAddItemlist;
        }
        _END_E();
        return 0;
    }

    public int xdmEncAddAdd(XDMParserAdd xDMParserAdd) {
        int xdmEncAddItemlist;
        int xdmEncAddMeta;
        int xdmEncAddCred;
        if (xDMParserAdd == null) {
            return 6;
        }
        _START_E(5);
        if (xDMParserAdd.cmdid > 0) {
            _ADD_E(11, String.valueOf(xDMParserAdd.cmdid));
        }
        if (xDMParserAdd.is_noresp > 0) {
            _ADD_BE(29);
        }
        if (xDMParserAdd.cred != null && (xdmEncAddCred = xdmEncAddCred(xDMParserAdd.cred)) != 0) {
            return xdmEncAddCred;
        }
        if (xDMParserAdd.meta != null && (xdmEncAddMeta = xdmEncAddMeta(xDMParserAdd.meta)) != 0) {
            return xdmEncAddMeta;
        }
        if (xDMParserAdd.itemlist != null && (xdmEncAddItemlist = xdmEncAddItemlist(xDMParserAdd.itemlist)) != 0) {
            return xdmEncAddItemlist;
        }
        _END_E();
        return 0;
    }

    public int xdmEncStartAdd(XDMParserAdd xDMParserAdd) {
        int xdmEncAddMeta;
        int xdmEncAddCred;
        if (xDMParserAdd == null) {
            return 6;
        }
        _START_E(5);
        if (xDMParserAdd.cmdid > 0) {
            _ADD_E(11, String.valueOf(xDMParserAdd.cmdid));
        }
        if (xDMParserAdd.is_noresp > 0) {
            _ADD_BE(29);
        }
        if (xDMParserAdd.cred != null && (xdmEncAddCred = xdmEncAddCred(xDMParserAdd.cred)) != 0) {
            return xdmEncAddCred;
        }
        if (xDMParserAdd.meta == null || (xdmEncAddMeta = xdmEncAddMeta(xDMParserAdd.meta)) == 0) {
            return 0;
        }
        return xdmEncAddMeta;
    }

    public int xdmEncEndAdd() {
        _END_E();
        return 0;
    }

    public int xdmEncAddReplace(XDMParserReplace xDMParserReplace) {
        int xdmEncAddItemlist;
        int xdmEncAddMeta;
        int xdmEncAddCred;
        if (xDMParserReplace == null) {
            return 6;
        }
        _START_E(32);
        if (xDMParserReplace.cmdid > 0) {
            _ADD_E(11, String.valueOf(xDMParserReplace.cmdid));
        }
        if (xDMParserReplace.is_noresp > 0) {
            _ADD_BE(29);
        }
        if (xDMParserReplace.cred != null && (xdmEncAddCred = xdmEncAddCred(xDMParserReplace.cred)) != 0) {
            return xdmEncAddCred;
        }
        if (xDMParserReplace.meta != null && (xdmEncAddMeta = xdmEncAddMeta(xDMParserReplace.meta)) != 0) {
            return xdmEncAddMeta;
        }
        if (xDMParserReplace.itemlist != null && (xdmEncAddItemlist = xdmEncAddItemlist(xDMParserReplace.itemlist)) != 0) {
            return xdmEncAddItemlist;
        }
        _END_E();
        return 0;
    }

    public int xdmEncStartReplace(XDMParserReplace xDMParserReplace) {
        int xdmEncAddMeta;
        int xdmEncAddCred;
        if (xDMParserReplace == null) {
            return 6;
        }
        _START_E(32);
        if (xDMParserReplace.cmdid > 0) {
            _ADD_E(11, String.valueOf(xDMParserReplace.cmdid));
        }
        if (xDMParserReplace.is_noresp > 0) {
            _ADD_BE(29);
        }
        if (xDMParserReplace.cred != null && (xdmEncAddCred = xdmEncAddCred(xDMParserReplace.cred)) != 0) {
            return xdmEncAddCred;
        }
        if (xDMParserReplace.meta == null || (xdmEncAddMeta = xdmEncAddMeta(xDMParserReplace.meta)) == 0) {
            return 0;
        }
        return xdmEncAddMeta;
    }

    public int xdmEncEndReplace() {
        _END_E();
        return 0;
    }

    public int xdmEncAddDelete(XDMParserDelete xDMParserDelete) {
        int xdmEncAddItemlist;
        int xdmEncAddMeta;
        int xdmEncAddCred;
        if (xDMParserDelete == null) {
            return 6;
        }
        _START_E(16);
        if (xDMParserDelete.cmdid > 0) {
            _ADD_E(11, String.valueOf(xDMParserDelete.cmdid));
        }
        if (xDMParserDelete.is_noresp > 0) {
            _ADD_BE(29);
        }
        if (xDMParserDelete.is_archive > 0) {
            _ADD_BE(7);
        }
        if (xDMParserDelete.is_sftdel > 0) {
            _ADD_BE(38);
        }
        if (xDMParserDelete.cred != null && (xdmEncAddCred = xdmEncAddCred(xDMParserDelete.cred)) != 0) {
            return xdmEncAddCred;
        }
        if (xDMParserDelete.meta != null && (xdmEncAddMeta = xdmEncAddMeta(xDMParserDelete.meta)) != 0) {
            return xdmEncAddMeta;
        }
        if (xDMParserDelete.itemlist != null && (xdmEncAddItemlist = xdmEncAddItemlist(xDMParserDelete.itemlist)) != 0) {
            return xdmEncAddItemlist;
        }
        _END_E();
        return 0;
    }

    public int xdmEncStartDelete(XDMParserDelete xDMParserDelete) {
        int xdmEncAddMeta;
        int xdmEncAddCred;
        if (xDMParserDelete == null) {
            return 6;
        }
        _START_E(16);
        if (xDMParserDelete.cmdid > 0) {
            _ADD_E(11, String.valueOf(xDMParserDelete.cmdid));
        }
        if (xDMParserDelete.is_noresp > 0) {
            _ADD_BE(29);
        }
        if (xDMParserDelete.is_archive > 0) {
            _ADD_BE(7);
        }
        if (xDMParserDelete.is_sftdel > 0) {
            _ADD_BE(38);
        }
        if (xDMParserDelete.cred != null && (xdmEncAddCred = xdmEncAddCred(xDMParserDelete.cred)) != 0) {
            return xdmEncAddCred;
        }
        if (xDMParserDelete.meta == null || (xdmEncAddMeta = xdmEncAddMeta(xDMParserDelete.meta)) == 0) {
            return 0;
        }
        return xdmEncAddMeta;
    }

    public int xdmEncEndDelete() {
        _END_E();
        return 0;
    }

    public int xdmEncAddMap(XDMParserMap xDMParserMap) {
        int xdmEncAddSource;
        int xdmEncAddTarget;
        int xdmEncAddMeta;
        int xdmEncAddCred;
        if (xDMParserMap == null) {
            return 6;
        }
        XDMList xDMList = xDMParserMap.itemlist;
        _START_E(24);
        if (xDMParserMap.cmdid > 0) {
            _ADD_E(11, String.valueOf(xDMParserMap.cmdid));
        }
        if (xDMParserMap.cred != null && (xdmEncAddCred = xdmEncAddCred(xDMParserMap.cred)) != 0) {
            return xdmEncAddCred;
        }
        if (xDMParserMap.meta != null && (xdmEncAddMeta = xdmEncAddMeta(xDMParserMap.meta)) != 0) {
            return xdmEncAddMeta;
        }
        if (!TextUtils.isEmpty(xDMParserMap.m_szTarget) && (xdmEncAddTarget = xdmEncAddTarget(xDMParserMap.m_szTarget)) != 0) {
            return xdmEncAddTarget;
        }
        if (!TextUtils.isEmpty(xDMParserMap.m_szSource) && (xdmEncAddSource = xdmEncAddSource(xDMParserMap.m_szSource)) != 0) {
            return xdmEncAddSource;
        }
        XDMParserMapItem xDMParserMapItem = (XDMParserMapItem) XDMList.xdmListGetItem(xDMList);
        XDMList xdmListGetItemPtr = XDMList.xdmListGetItemPtr(xDMList);
        while (xDMParserMapItem != null) {
            int xdmEncAddMapItem = xdmEncAddMapItem(xDMParserMapItem);
            if (xdmEncAddMapItem != 0) {
                _END_E();
                return xdmEncAddMapItem;
            }
            xDMParserMapItem = (XDMParserMapItem) XDMList.xdmListGetItem(xdmListGetItemPtr);
            xdmListGetItemPtr = XDMList.xdmListGetItemPtr(xdmListGetItemPtr);
        }
        _END_E();
        return 0;
    }

    public int xdmEncStartMap(XDMParserMap xDMParserMap) {
        int xdmEncAddSource;
        int xdmEncAddTarget;
        int xdmEncAddMeta;
        int xdmEncAddCred;
        if (xDMParserMap == null) {
            return 6;
        }
        _START_E(24);
        if (xDMParserMap.cmdid >= 0) {
            _ADD_E(11, String.valueOf(xDMParserMap.cmdid));
        }
        if (xDMParserMap.cred != null && (xdmEncAddCred = xdmEncAddCred(xDMParserMap.cred)) != 0) {
            return xdmEncAddCred;
        }
        if (xDMParserMap.meta != null && (xdmEncAddMeta = xdmEncAddMeta(xDMParserMap.meta)) != 0) {
            return xdmEncAddMeta;
        }
        if (!TextUtils.isEmpty(xDMParserMap.m_szTarget) && (xdmEncAddTarget = xdmEncAddTarget(xDMParserMap.m_szTarget)) != 0) {
            return xdmEncAddTarget;
        }
        if (TextUtils.isEmpty(xDMParserMap.m_szSource) || (xdmEncAddSource = xdmEncAddSource(xDMParserMap.m_szSource)) == 0) {
            return 0;
        }
        return xdmEncAddSource;
    }

    public int xdmEncEndMap() {
        _END_E();
        return 0;
    }

    public int xdmEncAddGet(XDMParserGet xDMParserGet) {
        int xdmEncAddMeta;
        int xdmEncAddCred;
        if (xDMParserGet == null) {
            return 6;
        }
        XDMList xDMList = xDMParserGet.itemlist;
        _START_E(19);
        if (xDMParserGet.cmdid > 0) {
            _ADD_E(11, String.valueOf(xDMParserGet.cmdid));
        }
        if (xDMParserGet.cred != null && (xdmEncAddCred = xdmEncAddCred(xDMParserGet.cred)) != 0) {
            return xdmEncAddCred;
        }
        if (xDMParserGet.is_noresp > 0) {
            _ADD_BE(29);
        }
        if (xDMParserGet.lang > 0) {
            _ADD_E(21, String.valueOf(xDMParserGet.lang));
        }
        if (xDMParserGet.meta != null && (xdmEncAddMeta = xdmEncAddMeta(xDMParserGet.meta)) != 0) {
            return xdmEncAddMeta;
        }
        XDMParserItem xDMParserItem = (XDMParserItem) XDMList.xdmListGetItem(xDMList);
        XDMList xdmListGetItemPtr = XDMList.xdmListGetItemPtr(xDMList);
        while (xDMParserItem != null) {
            int xdmEncAddItem = xdmEncAddItem(xDMParserItem);
            if (xdmEncAddItem != 0) {
                _END_E();
                return xdmEncAddItem;
            }
            xDMParserItem = (XDMParserItem) XDMList.xdmListGetItem(xdmListGetItemPtr);
            xdmListGetItemPtr = XDMList.xdmListGetItemPtr(xdmListGetItemPtr);
        }
        _END_E();
        return 0;
    }

    public int xdmEncAddPut(XDMParserPut xDMParserPut) {
        int xdmEncAddMeta;
        int xdmEncAddCred;
        Log.I("xdmEncAddPut");
        if (xDMParserPut == null) {
            return 6;
        }
        XDMList xDMList = xDMParserPut.itemlist;
        _START_E(31);
        if (xDMParserPut.cmdid > 0) {
            _ADD_E(11, String.valueOf(xDMParserPut.cmdid));
        }
        if (xDMParserPut.cred != null && (xdmEncAddCred = xdmEncAddCred(xDMParserPut.cred)) != 0) {
            return xdmEncAddCred;
        }
        if (xDMParserPut.is_noresp > 0) {
            _ADD_BE(29);
        }
        if (xDMParserPut.lang > 0) {
            _ADD_E(21, String.valueOf(xDMParserPut.lang));
        }
        if (xDMParserPut.meta != null && (xdmEncAddMeta = xdmEncAddMeta(xDMParserPut.meta)) != 0) {
            return xdmEncAddMeta;
        }
        XDMParserItem xDMParserItem = (XDMParserItem) XDMList.xdmListGetItem(xDMList);
        XDMList xdmListGetItemPtr = XDMList.xdmListGetItemPtr(xDMList);
        while (xDMParserItem != null) {
            int xdmEncAddItem = xdmEncAddItem(xDMParserItem);
            if (xdmEncAddItem != 0) {
                _END_E();
                return xdmEncAddItem;
            }
            xDMParserItem = (XDMParserItem) XDMList.xdmListGetItem(xdmListGetItemPtr);
            xdmListGetItemPtr = XDMList.xdmListGetItemPtr(xdmListGetItemPtr);
        }
        _END_E();
        return 0;
    }

    public int xdmEncAddItemlist(XDMList xDMList) {
        if (xDMList == null) {
            return 6;
        }
        XDMParserItem xDMParserItem = (XDMParserItem) XDMList.xdmListGetItem(xDMList);
        XDMList xdmListGetItemPtr = XDMList.xdmListGetItemPtr(xDMList);
        while (xDMParserItem != null) {
            int xdmEncAddItem = xdmEncAddItem(xDMParserItem);
            if (xdmEncAddItem != 0) {
                return xdmEncAddItem;
            }
            xDMParserItem = (XDMParserItem) XDMList.xdmListGetItem(xdmListGetItemPtr);
            xdmListGetItemPtr = XDMList.xdmListGetItemPtr(xdmListGetItemPtr);
        }
        return 0;
    }

    public int xdmEncAddMetinfAnchor(XDMParserAnchor xDMParserAnchor) {
        if (xDMParserAnchor == null) {
            return 6;
        }
        if (TextUtils.isEmpty(xDMParserAnchor.m_szLast) && TextUtils.isEmpty(xDMParserAnchor.m_szNext)) {
            return 6;
        }
        _START_E(5);
        if (!TextUtils.isEmpty(xDMParserAnchor.m_szLast)) {
            _ADD_E(10, xDMParserAnchor.m_szLast);
        }
        if (!TextUtils.isEmpty(xDMParserAnchor.m_szNext)) {
            _ADD_E(15, xDMParserAnchor.m_szNext);
        }
        _END_E();
        return 0;
    }

    public int xdmEncAddMetinfMem(XDMParserMem xDMParserMem) {
        if (xDMParserMem == null) {
            return 6;
        }
        if (xDMParserMem.free <= 0 && xDMParserMem.freeid <= 0 && TextUtils.isEmpty(xDMParserMem.m_szShared)) {
            return 6;
        }
        _START_E(13);
        if (xDMParserMem.free >= 0) {
            _ADD_E(9, String.valueOf(xDMParserMem.free));
        }
        if (xDMParserMem.freeid >= 0) {
            _ADD_E(8, String.valueOf(xDMParserMem.freeid));
        }
        if (!TextUtils.isEmpty(xDMParserMem.m_szShared)) {
            _ADD_E(17, xDMParserMem.m_szShared);
        }
        _END_E();
        return 0;
    }

    public int xdmEncAddMetinfEmi(String str) {
        if (TextUtils.isEmpty(str)) {
            return 6;
        }
        _ADD_E(6, str);
        return 0;
    }

    public static int xdmEncGetBufferSize(XDMEncoder xDMEncoder) {
        return xdmWbxEncGetBufferSize();
    }

    public int xdmEncAddResults(XDMParserResults xDMParserResults) {
        int xdmEncAddItemlist;
        int xdmEncAddMeta;
        if (xDMParserResults == null) {
            return 6;
        }
        _START_E(34);
        if (xDMParserResults.cmdid > 0) {
            _ADD_E(11, String.valueOf(xDMParserResults.cmdid));
        }
        if (!TextUtils.isEmpty(xDMParserResults.m_szMsgRef)) {
            _ADD_E(28, xDMParserResults.m_szMsgRef);
        }
        if (!TextUtils.isEmpty(xDMParserResults.m_szCmdRef)) {
            _ADD_E(12, xDMParserResults.m_szCmdRef);
        }
        if (xDMParserResults.meta == null || (xdmEncAddMeta = xdmEncAddMeta(xDMParserResults.meta)) == 0) {
            if (!TextUtils.isEmpty(xDMParserResults.m_szTargetRef)) {
                _ADD_E(47, xDMParserResults.m_szTargetRef);
            }
            if (!TextUtils.isEmpty(xDMParserResults.m_szSourceRef)) {
                _ADD_E(40, xDMParserResults.m_szSourceRef);
            }
            if (xDMParserResults.itemlist != null && (xdmEncAddItemlist = xdmEncAddItemlist(xDMParserResults.itemlist)) != 0) {
                return xdmEncAddItemlist;
            }
            _END_E();
            return 0;
        }
        _END_E();
        return xdmEncAddMeta;
    }

    public int xdmEncAddCopy(XDMParserCopy xDMParserCopy) {
        int xdmEncAddItemlist;
        int xdmEncAddMeta;
        int xdmEncAddCred;
        if (xDMParserCopy == null) {
            return 6;
        }
        _START_E(13);
        if (xDMParserCopy.cmdid > 0) {
            _ADD_E(11, String.valueOf(xDMParserCopy.cmdid));
        }
        if (xDMParserCopy.is_noresp > 0) {
            _ADD_BE(29);
        }
        if (xDMParserCopy.cred != null && (xdmEncAddCred = xdmEncAddCred(xDMParserCopy.cred)) != 0) {
            return xdmEncAddCred;
        }
        if (xDMParserCopy.meta != null && (xdmEncAddMeta = xdmEncAddMeta(xDMParserCopy.meta)) != 0) {
            return xdmEncAddMeta;
        }
        if (xDMParserCopy.itemlist != null && (xdmEncAddItemlist = xdmEncAddItemlist(xDMParserCopy.itemlist)) != 0) {
            return xdmEncAddItemlist;
        }
        _END_E();
        return 0;
    }

    public int xdmEncAddExec(XDMParserExec xDMParserExec) {
        int xdmEncAddItemlist;
        int xdmEncAddMeta;
        if (xDMParserExec == null) {
            return 6;
        }
        _START_E(13);
        if (xDMParserExec.cmdid > 0) {
            _ADD_E(11, String.valueOf(xDMParserExec.cmdid));
        }
        if (!TextUtils.isEmpty(xDMParserExec.m_szCorrelator)) {
            _ADD_E(60, xDMParserExec.m_szCorrelator);
        }
        if (xDMParserExec.is_noresp > 0) {
            _ADD_BE(29);
        }
        if (xDMParserExec.meta != null && (xdmEncAddMeta = xdmEncAddMeta(xDMParserExec.meta)) != 0) {
            return xdmEncAddMeta;
        }
        if (xDMParserExec.itemlist != null && (xdmEncAddItemlist = xdmEncAddItemlist(xDMParserExec.itemlist)) != 0) {
            return xdmEncAddItemlist;
        }
        _END_E();
        return 0;
    }

    public byte[] xdmEncDevinf2Opaque(ByteArrayOutputStream byteArrayOutputStream, XDMDevinfAdapter xDMDevinfAdapter, int[] iArr) {
        if (xDMDevinfAdapter == null) {
            return null;
        }
        xdmWbxEncInit(byteArrayOutputStream);
        if (!xdmWbxEncStartElement(10, true)) {
            return null;
        }
        byte[] bArr = new byte[byteArrayOutputStream.size()];
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        iArr[0] = byteArray.length;
        return byteArray;
    }

    public String xdmEncPcdataGetString(XDMParserPcdata xDMParserPcdata) {
        if (xDMParserPcdata != null && xDMParserPcdata.type == 0) {
            return String.valueOf(xDMParserPcdata.data);
        }
        return null;
    }
}
