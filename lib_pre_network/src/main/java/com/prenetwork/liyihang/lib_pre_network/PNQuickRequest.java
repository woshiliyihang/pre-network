package com.prenetwork.liyihang.lib_pre_network;

import java.util.Map;

/**
 * Created by liyihang on 18-1-16.
 */

public class PNQuickRequest extends PNRequestObservable {

    private String id;
    private String url;
    private String parms;
    private Map<String, String> header;
    private String method;

    public PNQuickRequest(String id, String url, String parms, Map<String, String> header, String method) {
        this.id = id;
        this.url = url;
        this.parms = parms;
        this.header = header;
        this.method = method;
    }

    public PNQuickRequest(String id, String url) {
        this.id = id;
        this.url = url;
        parms=null;
        header=null;
    }

    public PNQuickRequest(String id, String url, String parms) {
        this.id = id;
        this.url = url;
        this.parms = parms;
        header=null;
    }

    public PNQuickRequest(String id, String url, String parms, Map<String, String> header) {
        this.id = id;
        this.url = url;
        this.parms = parms;
        this.header = header;
    }

    @Override
    public String getRequestMethod() {
        return method!=null ? method : super.getRequestMethod();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Map<String, String> getRequestHeader() {
        return header;
    }

    @Override
    public String getRequestParms() {
        return parms;
    }

    @Override
    public String getRequestUrl() {
        return url;
    }

}
