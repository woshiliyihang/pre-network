package com.prenetwork.liyihang.prenetwork;

import java.util.Map;

/**
 * Created by liyihang on 18-1-16.
 */

public interface PNRequestInterface extends PNIDInterface {
    Map<String, String> getRequestHeader();
    String getRequestParms();
    String getRequestUrl();
    void handlerRequest();
}
