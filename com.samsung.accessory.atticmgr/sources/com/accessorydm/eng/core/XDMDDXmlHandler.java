package com.accessorydm.eng.core;

import com.accessorydm.interfaces.XFOTADDInterface;
import org.xmlpull.v1.XmlPullParser;

public class XDMDDXmlHandler implements XFOTADDInterface {
    private XDMDDXmlDataSet DownloadDescriptorDataSet = new XDMDDXmlDataSet();
    private boolean in_ddversion = false;
    private boolean in_description = false;
    private boolean in_iconURI = false;
    private boolean in_infoURL = false;
    private boolean in_installparam = false;
    private boolean in_name = false;
    private boolean in_nextURL = false;
    private boolean in_notifyuri = false;
    private boolean in_objecturi = false;
    private boolean in_size = false;
    private boolean in_type = false;
    private boolean in_vendor = false;

    public void startTag(String str) {
        if (XFOTADDInterface.XFOTA_DD_VERSION.equals(str)) {
            this.in_ddversion = true;
        } else if ("name".equals(str)) {
            this.in_name = true;
        } else if ("type".equals(str)) {
            this.in_type = true;
        } else if ("description".equals(str)) {
            this.in_description = true;
        } else if (XFOTADDInterface.XFOTA_DD_OBJECTURI.equals(str)) {
            this.in_objecturi = true;
        } else if ("size".equals(str)) {
            this.in_size = true;
        } else if (XFOTADDInterface.XFOTA_DD_NOTIFYURI.equals(str)) {
            this.in_notifyuri = true;
        } else if (XFOTADDInterface.XFOTA_DD_INSTALLPARAM.equals(str)) {
            this.in_installparam = true;
        } else if (XFOTADDInterface.XFOTA_DD_VENDOR.equals(str)) {
            this.in_vendor = true;
        } else if (XFOTADDInterface.XFOTA_DD_NEXTURL.equals(str)) {
            this.in_nextURL = true;
        } else if (XFOTADDInterface.XFOTA_DD_INFOURL.equals(str)) {
            this.in_infoURL = true;
        } else if (XFOTADDInterface.XFOTA_DD_ICONURI.equals(str)) {
            this.in_iconURI = true;
        }
    }

    public void endTag(String str) {
        if (XFOTADDInterface.XFOTA_DD_VERSION.equals(str)) {
            this.in_ddversion = false;
        } else if ("name".equals(str)) {
            this.in_name = false;
        } else if ("type".equals(str)) {
            this.in_type = false;
        } else if ("description".equals(str)) {
            this.in_description = false;
        } else if (XFOTADDInterface.XFOTA_DD_OBJECTURI.equals(str)) {
            this.in_objecturi = false;
        } else if ("size".equals(str)) {
            this.in_size = false;
        } else if (XFOTADDInterface.XFOTA_DD_NOTIFYURI.equals(str)) {
            this.in_notifyuri = false;
        } else if (XFOTADDInterface.XFOTA_DD_INSTALLPARAM.equals(str)) {
            this.in_installparam = false;
        } else if (XFOTADDInterface.XFOTA_DD_VENDOR.equals(str)) {
            this.in_vendor = false;
        } else if (XFOTADDInterface.XFOTA_DD_NEXTURL.equals(str)) {
            this.in_nextURL = false;
        } else if (XFOTADDInterface.XFOTA_DD_INFOURL.equals(str)) {
            this.in_infoURL = false;
        } else if (XFOTADDInterface.XFOTA_DD_ICONURI.equals(str)) {
            this.in_iconURI = false;
        }
    }

    public void text(XmlPullParser xmlPullParser) {
        if (this.in_ddversion) {
            XDMDDXmlDataSet xDMDDXmlDataSet = this.DownloadDescriptorDataSet;
            xDMDDXmlDataSet.m_szDDVersion = String.valueOf(xDMDDXmlDataSet.m_szDDVersion) + xmlPullParser.getText().trim();
        } else if (this.in_name) {
            XDMDDXmlDataSet xDMDDXmlDataSet2 = this.DownloadDescriptorDataSet;
            xDMDDXmlDataSet2.m_szName = String.valueOf(xDMDDXmlDataSet2.m_szName) + xmlPullParser.getText().trim();
        } else if (this.in_type) {
            XDMDDXmlDataSet xDMDDXmlDataSet3 = this.DownloadDescriptorDataSet;
            xDMDDXmlDataSet3.m_szType = String.valueOf(xDMDDXmlDataSet3.m_szType) + xmlPullParser.getText().trim();
        } else if (this.in_description) {
            XDMDDXmlDataSet xDMDDXmlDataSet4 = this.DownloadDescriptorDataSet;
            xDMDDXmlDataSet4.m_szDescription = String.valueOf(xDMDDXmlDataSet4.m_szDescription) + xmlPullParser.getText().trim();
        } else if (this.in_objecturi) {
            XDMDDXmlDataSet xDMDDXmlDataSet5 = this.DownloadDescriptorDataSet;
            xDMDDXmlDataSet5.m_szObjectURI = String.valueOf(xDMDDXmlDataSet5.m_szObjectURI) + xmlPullParser.getText().trim();
        } else if (this.in_size) {
            XDMDDXmlDataSet xDMDDXmlDataSet6 = this.DownloadDescriptorDataSet;
            xDMDDXmlDataSet6.m_szSize = String.valueOf(xDMDDXmlDataSet6.m_szSize) + xmlPullParser.getText().trim();
        } else if (this.in_notifyuri) {
            XDMDDXmlDataSet xDMDDXmlDataSet7 = this.DownloadDescriptorDataSet;
            xDMDDXmlDataSet7.m_szInstallNotifyURI = String.valueOf(xDMDDXmlDataSet7.m_szInstallNotifyURI) + xmlPullParser.getText().trim();
        } else if (this.in_installparam) {
            XDMDDXmlDataSet xDMDDXmlDataSet8 = this.DownloadDescriptorDataSet;
            xDMDDXmlDataSet8.m_szInstallParam = String.valueOf(xDMDDXmlDataSet8.m_szInstallParam) + xmlPullParser.getText().trim();
        } else if (this.in_vendor) {
            XDMDDXmlDataSet xDMDDXmlDataSet9 = this.DownloadDescriptorDataSet;
            xDMDDXmlDataSet9.m_szVendor = String.valueOf(xDMDDXmlDataSet9.m_szVendor) + xmlPullParser.getText().trim();
        } else if (this.in_nextURL) {
            XDMDDXmlDataSet xDMDDXmlDataSet10 = this.DownloadDescriptorDataSet;
            xDMDDXmlDataSet10.m_szNextURL = String.valueOf(xDMDDXmlDataSet10.m_szNextURL) + xmlPullParser.getText().trim();
        } else if (this.in_infoURL) {
            XDMDDXmlDataSet xDMDDXmlDataSet11 = this.DownloadDescriptorDataSet;
            xDMDDXmlDataSet11.m_szInfoURL = String.valueOf(xDMDDXmlDataSet11.m_szInfoURL) + xmlPullParser.getText().trim();
        } else if (this.in_iconURI) {
            XDMDDXmlDataSet xDMDDXmlDataSet12 = this.DownloadDescriptorDataSet;
            xDMDDXmlDataSet12.m_szIconURI = String.valueOf(xDMDDXmlDataSet12.m_szIconURI) + xmlPullParser.getText().trim();
        }
    }

    public XDMDDXmlDataSet xdmDDXmlGetParsedData() {
        return this.DownloadDescriptorDataSet;
    }
}
