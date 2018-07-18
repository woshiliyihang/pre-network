package com.chengxing.liyihang;

public class CXDataCacheBean extends CXBean {

    public int cid;
    public String keyid;
    public String jsonstr;
    public long mtime;
    public int nums;

    public CXDataCacheBean(int newsid, String keyid, String jsonstr, long mtime, int nums) {
        this.cid = newsid;
        this.keyid = keyid;
        this.jsonstr = jsonstr;
        this.mtime = mtime;
        this.nums = nums;
    }

    public CXDataCacheBean(String keyid, String jsonstr, long mtime) {
        this.keyid = keyid;
        this.jsonstr = jsonstr;
        this.mtime = mtime;
    }

    public CXDataCacheBean(int newsid, String keyid, String jsonstr, long mtime) {
        this.cid = newsid;
        this.keyid = keyid;
        this.jsonstr = jsonstr;
        this.mtime = mtime;
    }

    @Override
    public String toString() {
        return "CXDataCacheBean{" +
                "cid=" + cid +
                ", keyid='" + keyid + '\'' +
                ", jsonstr='" + jsonstr + '\'' +
                ", mtime=" + mtime +
                ", nums=" + nums +
                '}';
    }
}
