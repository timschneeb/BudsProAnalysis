package com.accessorydm.eng.core;

import com.samsung.android.fotaprovider.log.Log;

public class XDMLinkedList {
    public int count;
    public XDMNode cur;
    public XDMNode top;

    public static XDMLinkedList xdmListCreateLinkedList() {
        XDMLinkedList xDMLinkedList = new XDMLinkedList();
        XDMNode xdmListCreateNodeFromMemory = xdmListCreateNodeFromMemory();
        if (xdmListCreateNodeFromMemory == null) {
            Log.E("Create node memory alloc failed");
            return null;
        }
        xdmListCreateNodeFromMemory.next = xdmListCreateNodeFromMemory;
        xdmListCreateNodeFromMemory.previous = xdmListCreateNodeFromMemory;
        xDMLinkedList.top = xdmListCreateNodeFromMemory;
        xDMLinkedList.count = 0;
        return xDMLinkedList;
    }

    public static void xdmListFreeLinkedList(XDMLinkedList xDMLinkedList) {
        XDMNode xDMNode = xDMLinkedList.top;
        for (XDMNode xDMNode2 = xDMNode.next; xDMNode2 != xDMNode; xDMNode2 = xDMNode2.next) {
        }
        xdmListFreeNodeFromMemory(xDMNode);
    }

    public static void xdmListClearLinkedList(XDMLinkedList xDMLinkedList) {
        XDMNode xDMNode = xDMLinkedList.top;
        XDMNode xDMNode2 = xDMNode.next;
        while (xDMNode2 != xDMNode) {
            xDMNode2 = xDMNode2.next;
            xdmListFreeNodeFromMemory(xDMNode2.previous);
        }
        xDMNode.next = xDMNode;
        xDMNode.previous = xDMNode;
        xDMLinkedList.top = xDMNode;
        xDMLinkedList.count = 0;
    }

    public static XDMNode xdmListCreateNodeFromMemory() {
        return new XDMNode();
    }

    public static void xdmListAddObjAtFirst(XDMLinkedList xDMLinkedList, Object obj) {
        XDMNode xDMNode = xDMLinkedList.top;
        XDMNode xDMNode2 = new XDMNode();
        xdmListBindObjectToNode(xDMNode2, obj);
        xDMNode2.next = xDMNode.next;
        xDMNode2.previous = xDMNode;
        xDMNode.next = xDMNode2;
        xDMNode2.next.previous = xDMNode2;
        xDMLinkedList.count++;
    }

    public static void xdmListAddObjAtLast(XDMLinkedList xDMLinkedList, Object obj) {
        XDMNode xDMNode = xDMLinkedList.top;
        XDMNode xDMNode2 = new XDMNode();
        xdmListBindObjectToNode(xDMNode2, obj);
        xDMNode2.next = xDMNode;
        xDMNode2.previous = xDMNode.previous;
        xDMNode.previous.next = xDMNode2;
        xDMNode.previous = xDMNode2;
        xDMLinkedList.count++;
    }

    public static Object xdmListGetObj(XDMLinkedList xDMLinkedList, int i) {
        XDMNode xDMNode = xDMLinkedList.top;
        if (i >= xDMLinkedList.count || i < 0) {
            return null;
        }
        while (true) {
            int i2 = i - 1;
            if (i < 0) {
                return xDMNode.obj;
            }
            xDMNode = xDMNode.next;
            i = i2;
        }
    }

    public Object xdmListRemoveObj(XDMLinkedList xDMLinkedList, Object obj, int i) {
        XDMNode xDMNode = xDMLinkedList.top;
        for (XDMNode xDMNode2 = xDMNode.next; xDMNode2 != xDMNode; xDMNode2 = xDMNode2.next) {
            if (xDMNode2.obj.equals(obj)) {
                xDMNode2.previous.next = xDMNode2.next;
                xDMNode2.next.previous = xDMNode2.previous;
                xDMLinkedList.count--;
                return xdmListFreeNodeFromMemory(xDMNode2);
            }
        }
        return null;
    }

    public static Object xdmListRemoveObjAt(XDMLinkedList xDMLinkedList, int i) {
        XDMNode xDMNode = xDMLinkedList.top;
        if (i >= xDMLinkedList.count || i < 0) {
            return null;
        }
        while (true) {
            int i2 = i - 1;
            if (i < 0) {
                xDMNode.previous.next = xDMNode.next;
                xDMNode.next.previous = xDMNode.previous;
                xDMLinkedList.count--;
                return xdmListFreeNodeFromMemory(xDMNode);
            }
            xDMNode = xDMNode.next;
            i = i2;
        }
    }

    public static Object xdmListRemoveObjAtFirst(XDMLinkedList xDMLinkedList) {
        return xdmListRemoveObjAt(xDMLinkedList, 0);
    }

    public static Object xdmListBindObjectToNode(XDMNode xDMNode, Object obj) {
        if (xDMNode == null || obj == null) {
            return null;
        }
        Object obj2 = xDMNode.obj;
        xDMNode.obj = obj;
        return obj2;
    }

    public static Object xdmListFreeNodeFromMemory(XDMNode xDMNode) {
        if (xDMNode != null) {
            return xDMNode.obj;
        }
        return null;
    }

    public static void xdmListSetCurrentObj(XDMLinkedList xDMLinkedList, int i) {
        XDMNode xDMNode = xDMLinkedList.top;
        if (i >= 0 && i < xDMLinkedList.count) {
            while (true) {
                int i2 = i - 1;
                if (i < 0) {
                    break;
                }
                xDMNode = xDMNode.next;
                i = i2;
            }
        }
        xDMLinkedList.cur = xDMNode;
    }

    public static Object xdmListGetNextObj(XDMLinkedList xDMLinkedList) {
        XDMNode xDMNode = xDMLinkedList.cur;
        if (xDMNode == xDMLinkedList.top) {
            return null;
        }
        XDMNode xDMNode2 = xDMNode.next;
        xDMLinkedList.cur = xDMNode2;
        return xDMNode2.previous.obj;
    }

    public static Object xdmListRemovePreviousObj(XDMLinkedList xDMLinkedList) {
        XDMNode xDMNode = xDMLinkedList.cur;
        if (xDMNode.previous == xDMLinkedList.top) {
            return null;
        }
        XDMNode xDMNode2 = xDMNode.previous;
        xDMNode2.previous.next = xDMNode2.next;
        xDMNode2.next.previous = xDMNode2.previous;
        xDMLinkedList.count--;
        return xdmListFreeNodeFromMemory(xDMNode2);
    }
}
