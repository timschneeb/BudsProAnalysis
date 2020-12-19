package com.accessorydm.tp.urlconnect;

public interface HttpNetworkInterface {
    public static final int APPEND_SAVED_BUFFER_SIZE = 65536;
    public static final int RECEIVE_BUFFER_SIZE = 5120;
    public static final int RECEIVE_DOWNLOAD_BUFFER_SIZE = 33792;
    public static final int XTP_CONNECT_TIME_OUT = 60000;
    public static final String XTP_CRLF_STRING = "\r\n";
    public static final String XTP_HTTP_ACCEPT = "Accept";
    public static final String XTP_HTTP_ACCEPT_CHARSET = "Accept-Charset";
    public static final String XTP_HTTP_ACCEPT_LANGUAGE = "Accept-Language";
    public static final String XTP_HTTP_CACHE_CONTROL = "Cache-Control";
    public static final String XTP_HTTP_CACHE_CONTROL_MODE = "no-store, private";
    public static final String XTP_HTTP_CLOSE = "Close";
    public static final String XTP_HTTP_CONNECTION = "Connection";
    public static final String XTP_HTTP_CONTENT_LENGTH = "Content-Length";
    public static final String XTP_HTTP_CONTENT_RANGE = "Content-Range";
    public static final String XTP_HTTP_CONTENT_TYPE = "Content-Type";
    public static final String XTP_HTTP_DM_USER_AGENT = "SyncML_DM Client";
    public static final String XTP_HTTP_HEADER_DL_ACCEPT = "application/vnd.oma.dd+xml";
    public static final String XTP_HTTP_HEADER_DL_CONTENT_TYPE = "application/vnd.oma.dd+xml";
    public static final String XTP_HTTP_HEADER_DM_ACCEPT = "application/vnd.syncml.dm+wbxml";
    public static final String XTP_HTTP_HOST = "Host";
    public static final String XTP_HTTP_KEEPALIVE = "Keep-Alive";
    public static final String XTP_HTTP_LANGUAGE = "en";
    public static final String XTP_HTTP_METHOD_GET = "GET";
    public static final String XTP_HTTP_METHOD_POST = "POST";
    public static final String XTP_HTTP_MIME_DM_WBXML = "application/vnd.syncml.dm+wbxml";
    public static final String XTP_HTTP_RANGE = "Range";
    public static final String XTP_HTTP_TRANSFER_ENCODING = "Transfer-Encoding";
    public static final String XTP_HTTP_USER_AGENT = "User-Agent";
    public static final String XTP_HTTP_UTF8 = "UTF-8";
    public static final String XTP_HTTP_X_SYNCML_HMAC = "x-syncml-hmac";
    public static final String XTP_NETWORK_TYPE_HTTP = "http";
    public static final String XTP_NETWORK_TYPE_HTTPS = "https";
    public static final int XTP_RECEIVE_TIME_OUT = 60000;
    public static final int XTP_SEND_TIME_OUT = 60000;
    public static final int XTP_TIMER_INTERVAL = 1000;
}
