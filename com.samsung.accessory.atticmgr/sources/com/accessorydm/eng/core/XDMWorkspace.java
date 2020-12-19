package com.accessorydm.eng.core;

import com.accessorydm.eng.parser.XDMParser;
import com.accessorydm.eng.parser.XDMParserAlert;
import com.accessorydm.eng.parser.XDMParserAtomic;
import com.accessorydm.eng.parser.XDMParserItem;
import com.accessorydm.eng.parser.XDMParserResults;
import com.accessorydm.eng.parser.XDMParserSequence;
import com.accessorydm.eng.parser.XDMParserSyncheader;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XUICInterface;
import java.io.ByteArrayOutputStream;

public class XDMWorkspace implements XDMInterface, XUICInterface {
    public boolean IsSequenceProcessing;
    public int appId;
    public XDMParserAtomic atomic;
    public boolean atomicFlag;
    public XDMLinkedList atomicList;
    public XDMInterface.XDMAtomicStep atomicStep;
    public int authCount;
    public int authState;
    public ByteArrayOutputStream buf;
    public int bufsize;
    public int cmdID;
    public int credType;
    public boolean dataBuffered;
    public int dataTotalSize;
    public XDMInterface.XDMSyncMLState dmState;
    public XDMEncoder e;
    public boolean endOfMsg;
    public boolean inAtomicCmd;
    public boolean inSequenceCmd;
    public boolean isFinal;
    public XDMLinkedList list;
    public String m_szClientPW;
    public String m_szDownloadURI;
    public String m_szHostname;
    public String m_szMsgRef;
    public String m_szProtocol;
    public String m_szServerID;
    public String m_szServerPW;
    public String m_szSessionID;
    public String m_szSourceURI;
    public String m_szStatusReturnCode;
    public String m_szSvcState;
    public String m_szTargetURI;
    public String m_szUserName;
    public int maxMsgSize;
    public int maxObjSize;
    public int msgID;
    public boolean nTNDSFlag;
    public char nUpdateMechanism;
    public boolean nextMsg;
    public byte[] nextNonce;
    public int numAction;
    public XDMOmTree om;
    public XDMParser p;
    public int port;
    public int prevBufPos;
    public XDMInterface.XDMProcessingState procState;
    public int procStep;
    public XDMHmacData recvHmacData;
    public XDMParserResults results;
    public XDMLinkedList resultsList;
    public boolean sendChal;
    public int sendPos;
    public boolean sendRemain;
    public XDMParserSequence sequence;
    public XDMLinkedList sequenceList;
    public int serverAuthState;
    public int serverCredType;
    public int serverMaxMsgSize;
    public int serverMaxObjSize;
    public byte[] serverNextNonce;
    public int sessionAbort;
    public XDMLinkedList sourceRefList;
    public XDMInterface.XDMSyncMLState state;
    public XDMLinkedList statusList;
    public XDMParserSyncheader syncHeader;
    public XDMLinkedList targetRefList;
    public XDMParserResults tempResults;
    public XDMParserItem tmpItem;
    public XDMParserAlert uicAlert;
    public XDMList uicData;
    public XUICInterface.XUICFlag uicFlag;
    public XDMUicOption uicOption;
    public Object userData;
    public XDMWorkspace ws;

    public XDMWorkspace() {
        this.m_szDownloadURI = "";
        this.m_szSvcState = "";
        this.m_szUserName = "";
        this.m_szStatusReturnCode = "";
        this.m_szServerID = "";
        this.m_szServerPW = "";
        this.m_szClientPW = "";
        this.nextNonce = new byte[128];
        this.serverNextNonce = new byte[128];
        this.m_szProtocol = "";
        this.m_szHostname = "";
        this.m_szSourceURI = "";
        this.m_szTargetURI = "";
        this.m_szSessionID = "";
        this.m_szMsgRef = "";
        this.uicData = null;
        this.authState = -8;
        this.serverAuthState = -8;
        this.credType = -1;
        this.serverCredType = -1;
        this.sendChal = false;
        this.inAtomicCmd = false;
        this.atomicList = XDMLinkedList.xdmListCreateLinkedList();
        this.inSequenceCmd = false;
        this.sequenceList = null;
        this.om = new XDMOmTree();
        this.e = new XDMEncoder();
        this.targetRefList = XDMLinkedList.xdmListCreateLinkedList();
        this.sourceRefList = XDMLinkedList.xdmListCreateLinkedList();
        this.list = XDMLinkedList.xdmListCreateLinkedList();
        this.statusList = XDMLinkedList.xdmListCreateLinkedList();
        this.resultsList = XDMLinkedList.xdmListCreateLinkedList();
        this.results = null;
        this.buf = new ByteArrayOutputStream();
        this.bufsize = XDMInterface.XDM_WBXML_ENCODING_BUF_SIZE;
        this.maxMsgSize = 5120;
        this.maxObjSize = 1048576;
        this.serverMaxMsgSize = 5120;
        this.serverMaxObjSize = 1048576;
        this.endOfMsg = false;
        this.syncHeader = null;
        this.sessionAbort = 0;
        this.dmState = XDMInterface.XDMSyncMLState.XDM_STATE_INIT;
        this.cmdID = 1;
        this.appId = 0;
        this.msgID = 1;
        this.authCount = 0;
        this.dataBuffered = false;
        this.IsSequenceProcessing = false;
        this.nUpdateMechanism = 0;
        this.recvHmacData = new XDMHmacData();
        this.uicFlag = XUICInterface.XUICFlag.UIC_NONE;
        this.uicData = null;
    }

    public void xdmFreeWorkSpace() {
        if (this.targetRefList != null) {
            this.targetRefList = null;
        }
        if (this.sourceRefList != null) {
            this.sourceRefList = null;
        }
        if (this.list != null) {
            this.list = null;
        }
        if (this.statusList != null) {
            this.statusList = null;
        }
        if (this.resultsList != null) {
            this.resultsList = null;
        }
        if (this.atomicList != null) {
            this.atomicList = null;
        }
    }
}
