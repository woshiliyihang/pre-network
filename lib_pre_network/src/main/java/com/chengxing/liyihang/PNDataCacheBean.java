package com.chengxing.liyihang;

public class PNDataCacheBean extends PNBean {

    public int cid;
    public String keyid;
    public String jsonstr;
    public long mtime;
    public int nums;

    public PNDataCacheBean(int newsid, String keyid, String jsonstr, long mtime, int nums) {
        this.cid = newsid;
        this.keyid = keyid;
        this.jsonstr = jsonstr;
        this.mtime = mtime;
        this.nums = nums;
    }

    public PNDataCacheBean(String keyid, String jsonstr, long mtime) {
        this.keyid = keyid;
        this.jsonstr = jsonstr;
        this.mtime = mtime;
    }

    public PNDataCacheBean(int newsid, String keyid, String jsonstr, long mtime) {
        this.cid = newsid;
        this.keyid = keyid;
        this.jsonstr = jsonstr;
        this.mtime = mtime;
    }

    @Override
    public String toString() {
        return "PNDataCacheBean{" +
                "cid=" + cid +
                ", keyid='" + keyid + '\'' +
                ", jsonstr='" + jsonstr + '\'' +
                ", mtime=" + mtime +
                ", nums=" + nums +
                '}';
    }
}
