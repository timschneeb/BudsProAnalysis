package com.accessorydm.agent;

import android.text.TextUtils;
import com.accessorydm.eng.core.XDMLinkedList;
import com.accessorydm.eng.core.XDMList;
import com.accessorydm.eng.core.XDMNode;
import com.accessorydm.eng.core.XDMWorkspace;
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
import com.accessorydm.eng.parser.XDMParserMem;
import com.accessorydm.eng.parser.XDMParserMeta;
import com.accessorydm.eng.parser.XDMParserPcdata;
import com.accessorydm.eng.parser.XDMParserReplace;
import com.accessorydm.eng.parser.XDMParserResults;
import com.accessorydm.eng.parser.XDMParserSequence;
import com.accessorydm.eng.parser.XDMParserStatus;
import com.accessorydm.eng.parser.XDMParserSyncheader;
import com.accessorydm.interfaces.XDMInterface;

public class XDMHandleCmd implements XDMInterface {
    public void xdmAgentHdlCmdSyncHdr(Object obj, XDMParserSyncheader xDMParserSyncheader) {
        XDMWorkspace xDMWorkspace = (XDMWorkspace) obj;
        XDMAgent xDMAgent = new XDMAgent();
        xDMAgent.m_Header = new XDMParserSyncheader();
        xdmAgentDataStDuplSyncHeader(xDMAgent.m_Header, xDMParserSyncheader);
        xDMAgent.m_szCmd = XDMInterface.CMD_SYNCHDR;
        if (xDMWorkspace.inAtomicCmd || xDMWorkspace.inSequenceCmd) {
            xdmAgentHdlCmdAddSelectedAgent(xDMAgent, xDMWorkspace.list);
        } else {
            xdmAgentListAddObjAtLast(xDMWorkspace.list, xDMAgent);
        }
    }

    public void xdmAgentHdlCmdStatus(Object obj, XDMParserStatus xDMParserStatus) {
        XDMWorkspace xDMWorkspace = (XDMWorkspace) obj;
        XDMAgent xDMAgent = new XDMAgent();
        xDMAgent.m_Status = new XDMParserStatus();
        xdmAgentDataStDuplStatus(xDMAgent.m_Status, xDMParserStatus);
        xDMAgent.m_szCmd = XDMInterface.CMD_STATUS;
        if (xDMWorkspace.inAtomicCmd || xDMWorkspace.inSequenceCmd) {
            xdmAgentHdlCmdAddSelectedAgent(xDMAgent, xDMWorkspace.list);
        } else {
            xdmAgentListAddObjAtLast(xDMWorkspace.list, xDMAgent);
        }
    }

    public void xdmAgentHdlCmdGet(Object obj, XDMParserGet xDMParserGet) {
        XDMWorkspace xDMWorkspace = (XDMWorkspace) obj;
        XDMAgent xDMAgent = new XDMAgent();
        xDMAgent.m_Get = new XDMParserGet();
        xdmAgentDataStDuplGet(xDMAgent.m_Get, xDMParserGet);
        xDMAgent.m_szCmd = XDMInterface.CMD_GET;
        if (xDMWorkspace.inAtomicCmd || xDMWorkspace.inSequenceCmd) {
            xdmAgentHdlCmdAddSelectedAgent(xDMAgent, xDMWorkspace.list);
        } else {
            xdmAgentListAddObjAtLast(xDMWorkspace.list, xDMAgent);
        }
    }

    public void xdmAgentHdlCmdExec(Object obj, XDMParserExec xDMParserExec) {
        XDMWorkspace xDMWorkspace = (XDMWorkspace) obj;
        XDMAgent xDMAgent = new XDMAgent();
        xDMAgent.m_Exec = new XDMParserExec();
        xdmAgentDataStDuplExec(xDMAgent.m_Exec, xDMParserExec);
        xDMAgent.m_szCmd = XDMInterface.CMD_EXEC;
        if (xDMWorkspace.inAtomicCmd || xDMWorkspace.inSequenceCmd) {
            xdmAgentHdlCmdAddSelectedAgent(xDMAgent, xDMWorkspace.list);
        } else {
            xdmAgentListAddObjAtLast(xDMWorkspace.list, xDMAgent);
        }
    }

    public void xdmAgentHdlCmdAlert(Object obj, XDMParserAlert xDMParserAlert) {
        XDMWorkspace xDMWorkspace = (XDMWorkspace) obj;
        XDMAgent xDMAgent = new XDMAgent();
        xDMAgent.m_Alert = new XDMParserAlert();
        xdmAgentDataStDuplAlert(xDMAgent.m_Alert, xDMParserAlert);
        xDMAgent.m_szCmd = XDMInterface.CMD_ALERT;
        if (xDMWorkspace.inAtomicCmd || xDMWorkspace.inSequenceCmd) {
            xdmAgentHdlCmdAddSelectedAgent(xDMAgent, xDMWorkspace.list);
        } else {
            xdmAgentListAddObjAtLast(xDMWorkspace.list, xDMAgent);
        }
    }

    public void xdmAgentHdlCmdAdd(Object obj, XDMParserAdd xDMParserAdd) {
        XDMWorkspace xDMWorkspace = (XDMWorkspace) obj;
        XDMAgent xDMAgent = new XDMAgent();
        xDMAgent.m_AddCmd = new XDMParserAdd();
        xdmAgentDataStDuplAdd(xDMAgent.m_AddCmd, xDMParserAdd);
        xDMAgent.m_szCmd = XDMInterface.CMD_ADD;
        if (xDMWorkspace.inAtomicCmd || xDMWorkspace.inSequenceCmd) {
            xdmAgentHdlCmdAddSelectedAgent(xDMAgent, xDMWorkspace.list);
        } else {
            xdmAgentListAddObjAtLast(xDMWorkspace.list, xDMAgent);
        }
    }

    public void xdmAgentHdlCmdReplace(Object obj, XDMParserReplace xDMParserReplace) {
        XDMWorkspace xDMWorkspace = (XDMWorkspace) obj;
        XDMAgent xDMAgent = new XDMAgent();
        xDMAgent.m_ReplaceCmd = new XDMParserReplace();
        xdmAgentDataStDuplReplace(xDMAgent.m_ReplaceCmd, xDMParserReplace);
        xDMAgent.m_szCmd = XDMInterface.CMD_REPLACE;
        if (xDMWorkspace.inAtomicCmd || xDMWorkspace.inSequenceCmd) {
            xdmAgentHdlCmdAddSelectedAgent(xDMAgent, xDMWorkspace.list);
        } else {
            xdmAgentListAddObjAtLast(xDMWorkspace.list, xDMAgent);
        }
    }

    public void xdmAgentHdlCmdCopy(Object obj, XDMParserCopy xDMParserCopy) {
        XDMWorkspace xDMWorkspace = (XDMWorkspace) obj;
        XDMAgent xDMAgent = new XDMAgent();
        xDMAgent.m_CopyCmd = new XDMParserCopy();
        xdmAgentDataStDuplCopy(xDMAgent.m_CopyCmd, xDMParserCopy);
        xDMAgent.m_szCmd = XDMInterface.CMD_COPY;
        if (xDMWorkspace.inAtomicCmd || xDMWorkspace.inSequenceCmd) {
            xdmAgentHdlCmdAddSelectedAgent(xDMAgent, xDMWorkspace.list);
        } else {
            xdmAgentListAddObjAtLast(xDMWorkspace.list, xDMAgent);
        }
    }

    public void xdmAgentHdlCmdDelete(Object obj, XDMParserDelete xDMParserDelete) {
        XDMWorkspace xDMWorkspace = (XDMWorkspace) obj;
        XDMAgent xDMAgent = new XDMAgent();
        xDMAgent.m_DeleteCmd = new XDMParserDelete();
        xdmAgentDataStDuplDelete(xDMAgent.m_DeleteCmd, xDMParserDelete);
        xDMAgent.m_szCmd = XDMInterface.CMD_DELETE;
        if (xDMWorkspace.inAtomicCmd || xDMWorkspace.inSequenceCmd) {
            xdmAgentHdlCmdAddSelectedAgent(xDMAgent, xDMWorkspace.list);
        } else {
            xdmAgentListAddObjAtLast(xDMWorkspace.list, xDMAgent);
        }
    }

    public void xdmAgentHdlCmdAtomicStart(Object obj, XDMParserAtomic xDMParserAtomic) {
        XDMWorkspace xDMWorkspace = (XDMWorkspace) obj;
        XDMAgent xDMAgent = new XDMAgent();
        xDMAgent.m_Atomic = new XDMParserAtomic();
        xdmAgentDataStDuplAtomic(xDMAgent.m_Atomic, xDMParserAtomic);
        xDMWorkspace.atomic = new XDMParserAtomic();
        xdmAgentDataStDuplAtomic(xDMWorkspace.atomic, xDMParserAtomic);
        xDMAgent.m_szCmd = "Atomic_Start";
        if (xDMWorkspace.inAtomicCmd || xDMWorkspace.inSequenceCmd) {
            xdmAgentHdlCmdAddSelectedAgent(xDMAgent, xDMWorkspace.list);
            return;
        }
        xDMWorkspace.inAtomicCmd = true;
        xdmAgentListAddObjAtLast(xDMWorkspace.list, xDMAgent);
    }

    public void xdmAgentHdlCmdAtomicEnd(Object obj) {
        XDMWorkspace xDMWorkspace = (XDMWorkspace) obj;
        if (xDMWorkspace.inAtomicCmd || xDMWorkspace.inSequenceCmd) {
            xdmAgentHdlCmdLocateSelectedAgent(null, xDMWorkspace.list);
            xDMWorkspace.inAtomicCmd = false;
        }
    }

    public void xdmAgentHdlCmdSequenceStart(Object obj, XDMParserSequence xDMParserSequence) {
        XDMWorkspace xDMWorkspace = (XDMWorkspace) obj;
        XDMAgent xDMAgent = new XDMAgent();
        xDMAgent.m_Sequence = new XDMParserSequence();
        xdmAgentDataStDuplSequence(xDMAgent.m_Sequence, xDMParserSequence);
        if (xDMWorkspace.sequence != null) {
            xdmAgentDataStDeleteSequence(xDMWorkspace.sequence);
        }
        xDMWorkspace.sequence = new XDMParserSequence();
        xdmAgentDataStDuplSequence(xDMWorkspace.sequence, xDMParserSequence);
        xDMAgent.m_szCmd = "Sequence_Start";
        if (xDMWorkspace.inAtomicCmd || xDMWorkspace.inSequenceCmd) {
            xdmAgentHdlCmdAddSelectedAgent(xDMAgent, xDMWorkspace.list);
            return;
        }
        xDMWorkspace.inSequenceCmd = false;
        xdmAgentListAddObjAtLast(xDMWorkspace.list, xDMAgent);
    }

    private void xdmAgentHdlCmdAddSelectedAgent(XDMAgent xDMAgent, XDMLinkedList xDMLinkedList) {
        XDMAgent xDMAgent2 = (XDMAgent) XDMLinkedList.xdmListGetObj(xDMLinkedList, xDMLinkedList.count - 1);
        if (xDMAgent2 == null) {
            xdmAgentListAddObjAtLast(XDMLinkedList.xdmListCreateLinkedList(), xDMAgent);
        } else if (!xDMAgent2.m_bInProgresscmd) {
            if ("Atomic_Start".compareTo(xDMAgent.m_szCmd) == 0 || "Sequence_Start".compareTo(xDMAgent.m_szCmd) == 0) {
                xDMAgent2.m_bInProgresscmd = true;
            }
            if (xDMAgent2.m_Atomic != null) {
                xDMLinkedList = xDMAgent2.m_Atomic.itemlist;
            } else if (xDMAgent2.m_Sequence != null) {
                xDMLinkedList = xDMAgent2.m_Sequence.itemlist;
            }
            if (xDMLinkedList != null) {
                xdmAgentListAddObjAtLast(xDMLinkedList, xDMAgent);
            } else {
                xdmAgentListAddObjAtLast(XDMLinkedList.xdmListCreateLinkedList(), xDMAgent);
            }
        } else if (xDMAgent2.m_Atomic != null) {
            if (xDMAgent2.m_Atomic.itemlist != null) {
                xdmAgentHdlCmdAddSelectedAgent(xDMAgent, xDMAgent2.m_Atomic.itemlist);
                return;
            }
            xDMAgent2.m_Atomic.itemlist = XDMLinkedList.xdmListCreateLinkedList();
            xdmAgentHdlCmdAddSelectedAgent(xDMAgent, xDMAgent2.m_Atomic.itemlist);
        } else if (xDMAgent2.m_Sequence == null) {
            xdmAgentListAddObjAtLast(xDMLinkedList, xDMAgent);
        } else if (xDMAgent2.m_Sequence.itemlist != null) {
            xdmAgentHdlCmdAddSelectedAgent(xDMAgent, xDMAgent2.m_Sequence.itemlist);
        } else {
            xDMAgent2.m_Sequence.itemlist = XDMLinkedList.xdmListCreateLinkedList();
            xdmAgentHdlCmdAddSelectedAgent(xDMAgent, xDMAgent2.m_Sequence.itemlist);
        }
    }

    private void xdmAgentHdlCmdLocateSelectedAgent(XDMAgent xDMAgent, XDMLinkedList xDMLinkedList) {
        XDMAgent xDMAgent2 = (XDMAgent) XDMLinkedList.xdmListGetObj(xDMLinkedList, xDMLinkedList.count - 1);
        if (xDMAgent2 == null) {
            return;
        }
        if (xDMAgent2.m_bInProgresscmd) {
            if (xDMAgent2.m_Atomic != null) {
                xdmAgentHdlCmdLocateSelectedAgent(xDMAgent2, xDMAgent2.m_Atomic.itemlist);
            } else if (xDMAgent2.m_Sequence != null) {
                xdmAgentHdlCmdLocateSelectedAgent(xDMAgent2, xDMAgent2.m_Sequence.itemlist);
            }
        } else if (xDMAgent != null) {
            xDMAgent.m_bInProgresscmd = false;
        }
    }

    public void xdmAgentHdlCmdSequenceEnd(Object obj) {
        XDMWorkspace xDMWorkspace = (XDMWorkspace) obj;
        if (xDMWorkspace.inSequenceCmd || xDMWorkspace.inAtomicCmd) {
            xdmAgentHdlCmdLocateSelectedAgent(null, xDMWorkspace.list);
            xDMWorkspace.inSequenceCmd = false;
        }
    }

    private void xdmAgentListAddObjAtLast(XDMLinkedList xDMLinkedList, Object obj) {
        XDMNode xDMNode = xDMLinkedList.top;
        XDMNode xDMNode2 = new XDMNode();
        XDMLinkedList.xdmListBindObjectToNode(xDMNode2, obj);
        xDMNode2.next = xDMNode;
        xDMNode2.previous = xDMNode.previous;
        xDMNode.previous.next = xDMNode2;
        xDMNode.previous = xDMNode2;
        xDMLinkedList.count++;
    }

    private void xdmAgentDataStDuplSyncHeader(XDMParserSyncheader xDMParserSyncheader, XDMParserSyncheader xDMParserSyncheader2) {
        if (!TextUtils.isEmpty(xDMParserSyncheader2.m_szVerdtd)) {
            xDMParserSyncheader.m_szVerdtd = xDMParserSyncheader2.m_szVerdtd;
        }
        if (!TextUtils.isEmpty(xDMParserSyncheader2.m_szVerproto)) {
            xDMParserSyncheader.m_szVerproto = xDMParserSyncheader2.m_szVerproto;
        }
        if (!TextUtils.isEmpty(xDMParserSyncheader2.m_szSessionId)) {
            xDMParserSyncheader.m_szSessionId = xDMParserSyncheader2.m_szSessionId;
        }
        if (xDMParserSyncheader2.msgid > 0) {
            xDMParserSyncheader.msgid = xDMParserSyncheader2.msgid;
        }
        if (!TextUtils.isEmpty(xDMParserSyncheader2.m_szTarget)) {
            xDMParserSyncheader.m_szTarget = xDMParserSyncheader2.m_szTarget;
        }
        if (!TextUtils.isEmpty(xDMParserSyncheader2.m_szSource)) {
            xDMParserSyncheader.m_szSource = xDMParserSyncheader2.m_szSource;
        }
        if (!TextUtils.isEmpty(xDMParserSyncheader2.m_szLocname)) {
            xDMParserSyncheader.m_szLocname = xDMParserSyncheader2.m_szLocname;
        }
        if (!TextUtils.isEmpty(xDMParserSyncheader2.m_szRespUri)) {
            xDMParserSyncheader.m_szRespUri = xDMParserSyncheader2.m_szRespUri;
        }
        if (xDMParserSyncheader2.cred != null) {
            xDMParserSyncheader.cred = new XDMParserCred();
            xdmAgentDataStDuplCred(xDMParserSyncheader.cred, xDMParserSyncheader2.cred);
        }
        if (xDMParserSyncheader2.meta != null) {
            xDMParserSyncheader.meta = new XDMParserMeta();
            xdmAgentDataStDuplMeta(xDMParserSyncheader.meta, xDMParserSyncheader2.meta);
        }
    }

    private void xdmAgentDataStDuplStatus(XDMParserStatus xDMParserStatus, XDMParserStatus xDMParserStatus2) {
        if (xDMParserStatus2 != null) {
            if (xDMParserStatus2.cmdid > 0) {
                xDMParserStatus.cmdid = xDMParserStatus2.cmdid;
            }
            if (!TextUtils.isEmpty(xDMParserStatus2.m_szMsgRef)) {
                xDMParserStatus.m_szMsgRef = xDMParserStatus2.m_szMsgRef;
            }
            if (!TextUtils.isEmpty(xDMParserStatus2.m_szCmdRef)) {
                xDMParserStatus.m_szCmdRef = xDMParserStatus2.m_szCmdRef;
            }
            if (!TextUtils.isEmpty(xDMParserStatus2.m_szCmd)) {
                xDMParserStatus.m_szCmd = xDMParserStatus2.m_szCmd;
            }
            if (xDMParserStatus2.targetref != null) {
                xDMParserStatus.targetref = xDMParserStatus2.targetref;
            }
            if (xDMParserStatus2.sourceref != null) {
                xDMParserStatus.sourceref = xDMParserStatus2.sourceref;
            }
            if (xDMParserStatus2.cred != null) {
                xDMParserStatus.cred = new XDMParserCred();
                xdmAgentDataStDuplCred(xDMParserStatus.cred, xDMParserStatus2.cred);
            }
            if (xDMParserStatus2.chal != null) {
                xDMParserStatus.chal = new XDMParserMeta();
                xdmAgentDataStDuplMeta(xDMParserStatus.chal, xDMParserStatus2.chal);
            }
            if (!TextUtils.isEmpty(xDMParserStatus2.m_szData)) {
                xDMParserStatus.m_szData = xDMParserStatus2.m_szData;
            }
            if (xDMParserStatus2.itemlist != null) {
                xDMParserStatus.itemlist = xdmAgentDataStDuplItemlist(xDMParserStatus2.itemlist);
            }
        }
    }

    private void xdmAgentDataStDuplGet(XDMParserGet xDMParserGet, XDMParserGet xDMParserGet2) {
        if (xDMParserGet2 != null) {
            if (xDMParserGet2.cmdid > 0) {
                xDMParserGet.cmdid = xDMParserGet2.cmdid;
            }
            if (xDMParserGet2.lang > 0) {
                xDMParserGet.lang = xDMParserGet2.lang;
            }
            if (xDMParserGet2.cred != null) {
                xDMParserGet.cred = new XDMParserCred();
                xdmAgentDataStDuplCred(xDMParserGet.cred, xDMParserGet2.cred);
            }
            if (xDMParserGet2.meta != null) {
                xDMParserGet.meta = new XDMParserMeta();
                xdmAgentDataStDuplMeta(xDMParserGet.meta, xDMParserGet2.meta);
            }
            if (xDMParserGet2.itemlist != null) {
                xDMParserGet.itemlist = xdmAgentDataStDuplItemlist(xDMParserGet2.itemlist);
            }
        }
    }

    private void xdmAgentDataStDuplExec(XDMParserExec xDMParserExec, XDMParserExec xDMParserExec2) {
        if (xDMParserExec2 != null) {
            if (xDMParserExec2.cmdid > 0) {
                xDMParserExec.cmdid = xDMParserExec2.cmdid;
            }
            if (!TextUtils.isEmpty(xDMParserExec2.m_szCorrelator)) {
                xDMParserExec.m_szCorrelator = xDMParserExec2.m_szCorrelator;
            }
            if (xDMParserExec2.meta != null) {
                xDMParserExec.meta = new XDMParserMeta();
                xdmAgentDataStDuplMeta(xDMParserExec.meta, xDMParserExec2.meta);
            }
            if (xDMParserExec2.itemlist != null) {
                xDMParserExec.itemlist = xdmAgentDataStDuplItemlist(xDMParserExec2.itemlist);
            }
        }
    }

    public static void xdmAgentDataStDuplAlert(XDMParserAlert xDMParserAlert, XDMParserAlert xDMParserAlert2) {
        if (xDMParserAlert2 != null) {
            if (xDMParserAlert2.cmdid > 0) {
                xDMParserAlert.cmdid = xDMParserAlert2.cmdid;
            }
            if (!TextUtils.isEmpty(xDMParserAlert2.m_szCorrelator)) {
                xDMParserAlert.m_szCorrelator = xDMParserAlert2.m_szCorrelator;
            }
            if (xDMParserAlert2.cred != null) {
                xDMParserAlert.cred = new XDMParserCred();
                xdmAgentDataStDuplCred(xDMParserAlert.cred, xDMParserAlert2.cred);
            }
            if (!TextUtils.isEmpty(xDMParserAlert2.m_szData)) {
                xDMParserAlert.m_szData = xDMParserAlert2.m_szData;
            }
            if (xDMParserAlert2.itemlist != null) {
                xDMParserAlert.itemlist = xdmAgentDataStDuplItemlist(xDMParserAlert2.itemlist);
            }
        }
    }

    private void xdmAgentDataStDuplAdd(XDMParserAdd xDMParserAdd, XDMParserAdd xDMParserAdd2) {
        if (xDMParserAdd2 != null) {
            if (xDMParserAdd2.cmdid > 0) {
                xDMParserAdd.cmdid = xDMParserAdd2.cmdid;
            }
            if (xDMParserAdd2.cred != null) {
                xDMParserAdd.cred = new XDMParserCred();
                xdmAgentDataStDuplCred(xDMParserAdd.cred, xDMParserAdd2.cred);
            }
            if (xDMParserAdd2.meta != null) {
                xDMParserAdd.meta = new XDMParserMeta();
                xdmAgentDataStDuplMeta(xDMParserAdd.meta, xDMParserAdd2.meta);
            }
            if (xDMParserAdd2.itemlist != null) {
                xDMParserAdd.itemlist = xdmAgentDataStDuplItemlist(xDMParserAdd2.itemlist);
            }
        }
    }

    private void xdmAgentDataStDuplReplace(XDMParserReplace xDMParserReplace, XDMParserReplace xDMParserReplace2) {
        if (xDMParserReplace2 != null) {
            if (xDMParserReplace2.cmdid > 0) {
                xDMParserReplace.cmdid = xDMParserReplace2.cmdid;
            }
            if (xDMParserReplace2.cred != null) {
                xDMParserReplace.cred = new XDMParserCred();
                xdmAgentDataStDuplCred(xDMParserReplace.cred, xDMParserReplace2.cred);
            }
            if (xDMParserReplace2.meta != null) {
                xDMParserReplace.meta = new XDMParserMeta();
                xdmAgentDataStDuplMeta(xDMParserReplace.meta, xDMParserReplace2.meta);
            }
            if (xDMParserReplace2.itemlist != null) {
                xDMParserReplace.itemlist = xdmAgentDataStDuplItemlist(xDMParserReplace2.itemlist);
            }
        }
    }

    private void xdmAgentDataStDuplCopy(XDMParserCopy xDMParserCopy, XDMParserCopy xDMParserCopy2) {
        if (xDMParserCopy2 != null) {
            if (xDMParserCopy2.cmdid > 0) {
                xDMParserCopy.cmdid = xDMParserCopy2.cmdid;
            }
            if (xDMParserCopy2.cred != null) {
                xDMParserCopy.cred = new XDMParserCred();
                xdmAgentDataStDuplCred(xDMParserCopy.cred, xDMParserCopy2.cred);
            }
            if (xDMParserCopy2.meta != null) {
                xDMParserCopy.meta = new XDMParserMeta();
                xdmAgentDataStDuplMeta(xDMParserCopy.meta, xDMParserCopy2.meta);
            }
            if (xDMParserCopy2.itemlist != null) {
                xDMParserCopy.itemlist = xdmAgentDataStDuplItemlist(xDMParserCopy2.itemlist);
            }
        }
    }

    private void xdmAgentDataStDuplDelete(XDMParserDelete xDMParserDelete, XDMParserDelete xDMParserDelete2) {
        if (xDMParserDelete2 != null) {
            if (xDMParserDelete2.cmdid > 0) {
                xDMParserDelete.cmdid = xDMParserDelete2.cmdid;
            }
            if (xDMParserDelete2.cred != null) {
                xDMParserDelete.cred = new XDMParserCred();
                xdmAgentDataStDuplCred(xDMParserDelete.cred, xDMParserDelete2.cred);
            }
            if (xDMParserDelete2.meta != null) {
                xDMParserDelete.meta = new XDMParserMeta();
                xdmAgentDataStDuplMeta(xDMParserDelete.meta, xDMParserDelete2.meta);
            }
            if (xDMParserDelete2.itemlist != null) {
                xDMParserDelete.itemlist = xdmAgentDataStDuplItemlist(xDMParserDelete2.itemlist);
            }
        }
    }

    private static void xdmAgentDataStDuplCred(XDMParserCred xDMParserCred, XDMParserCred xDMParserCred2) {
        if (xDMParserCred2 != null) {
            if (!TextUtils.isEmpty(xDMParserCred2.m_szData)) {
                xDMParserCred.m_szData = xDMParserCred2.m_szData;
            }
            if (xDMParserCred2.meta != null) {
                xDMParserCred.meta = new XDMParserMeta();
                xdmAgentDataStDuplMeta(xDMParserCred.meta, xDMParserCred2.meta);
            }
        }
    }

    private static void xdmAgentDataStDuplMeta(XDMParserMeta xDMParserMeta, XDMParserMeta xDMParserMeta2) {
        if (xDMParserMeta2 != null) {
            if (!TextUtils.isEmpty(xDMParserMeta2.m_szType)) {
                xDMParserMeta.m_szType = xDMParserMeta2.m_szType;
            }
            if (!TextUtils.isEmpty(xDMParserMeta2.m_szFormat)) {
                xDMParserMeta.m_szFormat = xDMParserMeta2.m_szFormat;
            }
            if (!TextUtils.isEmpty(xDMParserMeta2.m_szMark)) {
                xDMParserMeta.m_szMark = xDMParserMeta2.m_szMark;
            }
            if (xDMParserMeta2.size > 0) {
                xDMParserMeta.size = xDMParserMeta2.size;
            }
            if (!TextUtils.isEmpty(xDMParserMeta2.m_szNextNonce)) {
                xDMParserMeta.m_szNextNonce = xDMParserMeta2.m_szNextNonce;
            }
            if (!TextUtils.isEmpty(xDMParserMeta2.m_szVersion)) {
                xDMParserMeta.m_szVersion = xDMParserMeta2.m_szVersion;
            }
            if (xDMParserMeta2.maxmsgsize > 0) {
                xDMParserMeta.maxmsgsize = xDMParserMeta2.maxmsgsize;
            }
            if (xDMParserMeta2.maxobjsize > 0) {
                xDMParserMeta.maxobjsize = xDMParserMeta2.maxobjsize;
            }
            if (xDMParserMeta2.mem != null) {
                xDMParserMeta.mem = new XDMParserMem();
                xdmAgentDataStDuplMetinfMem(xDMParserMeta.mem, xDMParserMeta2.mem);
            }
            if (!TextUtils.isEmpty(xDMParserMeta2.m_szEmi)) {
                xDMParserMeta.m_szEmi = xDMParserMeta2.m_szEmi;
            }
            if (xDMParserMeta2.anchor != null) {
                xDMParserMeta.anchor = new XDMParserAnchor();
                xdmAgentDataStDuplMetinfAnchor(xDMParserMeta.anchor, xDMParserMeta2.anchor);
            }
        }
    }

    private static XDMList xdmAgentDataStDuplItemlist(XDMList xDMList) {
        XDMList xDMList2 = null;
        while (xDMList != null) {
            XDMList xDMList3 = xDMList.next;
            XDMParserItem xDMParserItem = new XDMParserItem();
            xdmAgentDataStDuplItem(xDMParserItem, (XDMParserItem) xDMList.item);
            if (xDMList2 == null) {
                xDMList2 = XDMList.xdmListAppend(xDMList2, null, xDMParserItem);
            } else {
                XDMList.xdmListAppend(xDMList2, null, xDMParserItem);
            }
            xDMList = xDMList3;
        }
        return xDMList2;
    }

    public static void xdmAgentDataStDuplItem(XDMParserItem xDMParserItem, XDMParserItem xDMParserItem2) {
        if (xDMParserItem2 != null) {
            if (!TextUtils.isEmpty(xDMParserItem2.m_szTarget)) {
                xDMParserItem.m_szTarget = xDMParserItem2.m_szTarget;
            }
            if (!TextUtils.isEmpty(xDMParserItem2.m_szSource)) {
                xDMParserItem.m_szSource = xDMParserItem2.m_szSource;
            }
            if (xDMParserItem2.meta != null) {
                xDMParserItem.meta = new XDMParserMeta();
                xdmAgentDataStDuplMeta(xDMParserItem.meta, xDMParserItem2.meta);
            }
            if (xDMParserItem2.data != null) {
                xDMParserItem.data = new XDMParserPcdata();
                xdmAgentDataStDuplPcdata(xDMParserItem.data, xDMParserItem2.data);
            }
            xDMParserItem.moredata = xDMParserItem2.moredata;
        }
    }

    private static void xdmAgentDataStDuplPcdata(XDMParserPcdata xDMParserPcdata, XDMParserPcdata xDMParserPcdata2) {
        if (xDMParserPcdata2 != null) {
            xDMParserPcdata.type = xDMParserPcdata2.type;
            if (xDMParserPcdata.type == 0) {
                xDMParserPcdata.data = xDMParserPcdata2.data;
                xDMParserPcdata.size = xDMParserPcdata2.size;
            } else if (xDMParserPcdata2.data != null) {
                xDMParserPcdata.data = new char[xDMParserPcdata2.size];
                System.arraycopy(Integer.valueOf(xDMParserPcdata2.size), 0, xDMParserPcdata.data, 0, xDMParserPcdata2.size);
                xDMParserPcdata.size = xDMParserPcdata2.size;
            }
            if (xDMParserPcdata2.anchor != null) {
                xDMParserPcdata.anchor = new XDMParserAnchor();
                xdmAgentDataStDuplMetinfAnchor(xDMParserPcdata.anchor, xDMParserPcdata2.anchor);
            }
        }
    }

    private static void xdmAgentDataStDuplMetinfMem(XDMParserMem xDMParserMem, XDMParserMem xDMParserMem2) {
        if (xDMParserMem2 != null) {
            if (xDMParserMem2.free > 0) {
                xDMParserMem.free = xDMParserMem2.free;
            }
            if (xDMParserMem2.freeid > 0) {
                xDMParserMem.freeid = xDMParserMem2.freeid;
            }
            if (!TextUtils.isEmpty(xDMParserMem2.m_szShared)) {
                xDMParserMem.m_szShared = xDMParserMem2.m_szShared;
            }
        }
    }

    private static void xdmAgentDataStDuplMetinfAnchor(XDMParserAnchor xDMParserAnchor, XDMParserAnchor xDMParserAnchor2) {
        if (xDMParserAnchor2 != null) {
            if (!TextUtils.isEmpty(xDMParserAnchor2.m_szLast)) {
                xDMParserAnchor.m_szLast = xDMParserAnchor2.m_szLast;
            }
            if (!TextUtils.isEmpty(xDMParserAnchor2.m_szNext)) {
                xDMParserAnchor.m_szNext = xDMParserAnchor2.m_szNext;
            }
        }
    }

    private void xdmAgentDataStDuplAtomic(XDMParserAtomic xDMParserAtomic, XDMParserAtomic xDMParserAtomic2) {
        if (xDMParserAtomic2 != null) {
            if (xDMParserAtomic2.cmdid > 0) {
                xDMParserAtomic.cmdid = xDMParserAtomic2.cmdid;
            }
            if (xDMParserAtomic2.meta != null) {
                xDMParserAtomic.meta = new XDMParserMeta();
                xdmAgentDataStDuplMeta(xDMParserAtomic.meta, xDMParserAtomic2.meta);
            }
            if (xDMParserAtomic2.itemlist != null) {
                XDMLinkedList.xdmListSetCurrentObj(xDMParserAtomic2.itemlist, 0);
                XDMAgent xDMAgent = (XDMAgent) XDMLinkedList.xdmListGetNextObj(xDMParserAtomic2.itemlist);
                while (xDMAgent != null) {
                    xdmAgentListAddObjAtLast(xDMParserAtomic.itemlist, xDMAgent);
                    xDMAgent = (XDMAgent) XDMLinkedList.xdmListGetNextObj(xDMParserAtomic2.itemlist);
                }
                return;
            }
            xDMParserAtomic.itemlist = XDMLinkedList.xdmListCreateLinkedList();
        }
    }

    private void xdmAgentDataStDuplSequence(XDMParserSequence xDMParserSequence, XDMParserSequence xDMParserSequence2) {
        if (xDMParserSequence2 != null) {
            if (xDMParserSequence2.cmdid > 0) {
                xDMParserSequence.cmdid = xDMParserSequence2.cmdid;
            }
            if (xDMParserSequence2.meta != null) {
                xDMParserSequence.meta = new XDMParserMeta();
                xdmAgentDataStDuplMeta(xDMParserSequence.meta, xDMParserSequence2.meta);
            }
            if (xDMParserSequence2.itemlist != null) {
                XDMLinkedList.xdmListSetCurrentObj(xDMParserSequence2.itemlist, 0);
                XDMAgent xDMAgent = (XDMAgent) XDMLinkedList.xdmListGetNextObj(xDMParserSequence2.itemlist);
                while (xDMAgent != null) {
                    xdmAgentListAddObjAtLast(xDMParserSequence.itemlist, xDMAgent);
                    xDMAgent = (XDMAgent) XDMLinkedList.xdmListGetNextObj(xDMParserSequence2.itemlist);
                }
                return;
            }
            xDMParserSequence.itemlist = XDMLinkedList.xdmListCreateLinkedList();
        }
    }

    private void xdmAgentDataStDeleteSequence(Object obj) {
        XDMParserSequence xDMParserSequence = (XDMParserSequence) obj;
        if (xDMParserSequence != null) {
            xDMParserSequence.cmdid = 0;
            if (xDMParserSequence.meta != null) {
                xDMParserSequence.meta.anchor = null;
                xDMParserSequence.meta.m_szEmi = null;
                xDMParserSequence.meta.m_szFormat = null;
                xDMParserSequence.meta.m_szMark = null;
                xDMParserSequence.meta.maxmsgsize = 0;
                xDMParserSequence.meta.maxobjsize = 0;
                xDMParserSequence.meta.mem = null;
                xDMParserSequence.meta.m_szNextNonce = null;
                xDMParserSequence.meta.size = 0;
                xDMParserSequence.meta.m_szType = null;
                xDMParserSequence.meta.m_szVersion = null;
                xDMParserSequence.meta = null;
            }
            if (xDMParserSequence.itemlist != null) {
                XDMLinkedList.xdmListSetCurrentObj(xDMParserSequence.itemlist, 0);
                for (XDMAgent xDMAgent = (XDMAgent) XDMLinkedList.xdmListGetNextObj(xDMParserSequence.itemlist); xDMAgent != null; xDMAgent = (XDMAgent) XDMLinkedList.xdmListGetNextObj(xDMParserSequence.itemlist)) {
                    XDMLinkedList.xdmListRemoveObjAtFirst(xDMParserSequence.itemlist);
                }
            }
        }
    }

    public static void xdmAgentDataStDeleteStatus(Object obj) {
        XDMParserStatus xDMParserStatus = (XDMParserStatus) obj;
        if (xDMParserStatus != null) {
            if (xDMParserStatus.chal != null) {
                xdmAgentDataStDeleteMeta(xDMParserStatus.chal);
            }
            if (xDMParserStatus.itemlist != null) {
                xdmAgentDataStDeleteItemlist(xDMParserStatus.itemlist);
            }
            if (xDMParserStatus.cred != null) {
                xdmAgentDataStDeleteCred(xDMParserStatus.cred);
            }
            xDMParserStatus.m_szCmd = null;
            xDMParserStatus.cmdid = 0;
            xDMParserStatus.m_szCmdRef = null;
            xDMParserStatus.m_szData = null;
            xDMParserStatus.m_szMsgRef = null;
            if (xDMParserStatus.sourceref != null) {
                xdmAgentDataStDeleteElelist(xDMParserStatus.sourceref);
            }
            if (xDMParserStatus.targetref != null) {
                xdmAgentDataStDeleteElelist(xDMParserStatus.targetref);
            }
        }
    }

    public static void xdmAgentDataStDeleteMeta(Object obj) {
        XDMParserMeta xDMParserMeta = (XDMParserMeta) obj;
        if (xDMParserMeta != null) {
            if (xDMParserMeta.anchor != null) {
                xdmAgentDataStDeleteMetinfAnchor(xDMParserMeta.anchor);
            }
            if (xDMParserMeta.mem != null) {
                xdmAgentDataStDeleteMetinfMem(xDMParserMeta.mem);
            }
            xDMParserMeta.m_szEmi = null;
            xDMParserMeta.m_szFormat = null;
            xDMParserMeta.m_szMark = null;
            xDMParserMeta.maxmsgsize = 0;
            xDMParserMeta.maxobjsize = 0;
            xDMParserMeta.m_szNextNonce = null;
            xDMParserMeta.size = 0;
            xDMParserMeta.m_szType = null;
            xDMParserMeta.m_szVersion = null;
        }
    }

    private static void xdmAgentDataStDeleteMetinfAnchor(Object obj) {
        XDMParserAnchor xDMParserAnchor = (XDMParserAnchor) obj;
        if (xDMParserAnchor != null) {
            xDMParserAnchor.m_szLast = null;
            xDMParserAnchor.m_szNext = null;
        }
    }

    private static void xdmAgentDataStDeleteMetinfMem(Object obj) {
        XDMParserMem xDMParserMem = (XDMParserMem) obj;
        if (xDMParserMem != null) {
            xDMParserMem.free = 0;
            xDMParserMem.freeid = 0;
            xDMParserMem.m_szShared = null;
        }
    }

    private static void xdmAgentDataStDeleteItemlist(Object obj) {
        XDMList xDMList = (XDMList) obj;
        while (xDMList != null) {
            XDMList xDMList2 = xDMList.next;
            xdmAgentDataStDeleteItem(xDMList.item);
            xDMList = xDMList2;
        }
    }

    private static void xdmAgentDataStDeleteItem(Object obj) {
        XDMParserItem xDMParserItem = (XDMParserItem) obj;
        if (xDMParserItem != null) {
            if (xDMParserItem.data != null) {
                xdmAgentDataStDeletePcdata(xDMParserItem.data);
            }
            xDMParserItem.m_szSource = null;
            xDMParserItem.m_szTarget = null;
            if (xDMParserItem.meta != null) {
                xdmAgentDataStDeleteMeta(xDMParserItem.meta);
            }
        }
    }

    private static void xdmAgentDataStDeletePcdata(Object obj) {
        XDMParserPcdata xDMParserPcdata = (XDMParserPcdata) obj;
        if (xDMParserPcdata != null) {
            xDMParserPcdata.data = null;
            if (xDMParserPcdata.anchor != null) {
                xdmAgentDataStDeleteMetinfAnchor(xDMParserPcdata.anchor);
            }
        }
    }

    private static void xdmAgentDataStDeleteCred(Object obj) {
        XDMParserCred xDMParserCred = (XDMParserCred) obj;
        if (xDMParserCred != null) {
            xDMParserCred.m_szData = null;
            if (xDMParserCred.meta != null) {
                xdmAgentDataStDeleteMeta(xDMParserCred.meta);
            }
        }
    }

    private static void xdmAgentDataStDeleteElelist(Object obj) {
        XDMList xDMList = (XDMList) obj;
        while (xDMList != null) {
            XDMList xDMList2 = xDMList.next;
            xDMList.item = null;
            xDMList = xDMList2;
        }
    }

    public static void xdmAgentDataStDeleteAlert(Object obj) {
        XDMParserAlert xDMParserAlert = (XDMParserAlert) obj;
        if (xDMParserAlert != null) {
            xDMParserAlert.cmdid = 0;
            xDMParserAlert.m_szCorrelator = null;
            if (xDMParserAlert.cred != null) {
                xdmAgentDataStDeleteCred(xDMParserAlert.cred);
            }
            xDMParserAlert.m_szData = null;
            if (xDMParserAlert.itemlist != null) {
                xdmAgentDataStDeleteItemlist(xDMParserAlert.itemlist);
            }
        }
    }

    public static void xdmAgentDataStDeleteReplace(Object obj) {
        XDMParserReplace xDMParserReplace = (XDMParserReplace) obj;
        if (xDMParserReplace != null) {
            if (xDMParserReplace.cred != null) {
                xdmAgentDataStDeleteCred(xDMParserReplace.cred);
            }
            if (xDMParserReplace.itemlist != null) {
                xdmAgentDataStDeleteItemlist(xDMParserReplace.itemlist);
            }
            if (xDMParserReplace.meta != null) {
                xdmAgentDataStDeleteMeta(xDMParserReplace.meta);
            }
            xDMParserReplace.cmdid = 0;
        }
    }

    public static XDMParserPcdata xdmAgentDataStString2Pcdata(char[] cArr) {
        XDMParserPcdata xDMParserPcdata = new XDMParserPcdata();
        xDMParserPcdata.type = 0;
        xDMParserPcdata.size = cArr.length;
        xDMParserPcdata.data = new char[cArr.length];
        System.arraycopy(cArr, 0, xDMParserPcdata.data, 0, cArr.length);
        return xDMParserPcdata;
    }

    public static void xdmAgentDataStDeleteResults(Object obj) {
        XDMParserResults xDMParserResults = (XDMParserResults) obj;
        if (xDMParserResults != null) {
            xDMParserResults.cmdid = 0;
            xDMParserResults.m_szMsgRef = null;
            xDMParserResults.m_szCmdRef = null;
            if (xDMParserResults.meta != null) {
                xdmAgentDataStDeleteMeta(xDMParserResults.meta);
            }
            xDMParserResults.m_szTargetRef = null;
            xDMParserResults.m_szSourceRef = null;
            if (xDMParserResults.itemlist != null) {
                xdmAgentDataStDeleteItemlist(xDMParserResults.itemlist);
            }
        }
    }

    public static String xdmAgentDataStGetString(XDMParserPcdata xDMParserPcdata) {
        if (xDMParserPcdata == null || xDMParserPcdata.type != 0 || xDMParserPcdata.data == null) {
            return null;
        }
        return String.valueOf(xDMParserPcdata.data);
    }

    public static void xdmAgentDataStDuplResults(XDMParserResults xDMParserResults, XDMParserResults xDMParserResults2) {
        if (xDMParserResults2 != null) {
            if (xDMParserResults2.cmdid > 0) {
                xDMParserResults.cmdid = xDMParserResults2.cmdid;
            }
            if (!TextUtils.isEmpty(xDMParserResults2.m_szMsgRef)) {
                xDMParserResults.m_szMsgRef = xDMParserResults2.m_szMsgRef;
            }
            if (!TextUtils.isEmpty(xDMParserResults2.m_szCmdRef)) {
                xDMParserResults.m_szCmdRef = xDMParserResults2.m_szCmdRef;
            }
            if (xDMParserResults2.meta != null) {
                xDMParserResults.meta = new XDMParserMeta();
                xdmAgentDataStDuplMeta(xDMParserResults.meta, xDMParserResults2.meta);
            }
            if (!TextUtils.isEmpty(xDMParserResults2.m_szTargetRef)) {
                xDMParserResults.m_szTargetRef = xDMParserResults2.m_szTargetRef;
            }
            if (!TextUtils.isEmpty(xDMParserResults2.m_szSourceRef)) {
                xDMParserResults.m_szSourceRef = xDMParserResults2.m_szSourceRef;
            }
            if (xDMParserResults2.itemlist != null) {
                xDMParserResults.itemlist = xdmAgentDataStDuplItemlist(xDMParserResults2.itemlist);
            }
        }
    }
}
