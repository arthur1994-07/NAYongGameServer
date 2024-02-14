package com.common.nayong.utils;

import javax.servlet.http.HttpServletRequest;

public class URLHelper {

    public static String getBaseUrl(HttpServletRequest request) {
        var scheme = request.getScheme();
        var serverName = request.getServerName();
        var serverPort = request.getServerPort();
        var contextPath = request.getContextPath();
        StringBuffer url = new StringBuffer();
        url.append(scheme).append("://").append(serverName);

        if ((serverPort != 6666) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath);
        if (url.toString().endsWith("/")) {
            url.append("/");
        }
        return url.toString();
    }
}
