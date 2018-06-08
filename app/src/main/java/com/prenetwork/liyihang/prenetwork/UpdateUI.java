package com.prenetwork.liyihang.prenetwork;

import android.widget.Toast;

import com.prenetwork.liyihang.lib_pre_network.PNBaseActivity;
import com.prenetwork.liyihang.lib_pre_network.PNBaseObserver;
import com.prenetwork.liyihang.lib_pre_network.PNObserver;
import com.prenetwork.liyihang.lib_pre_network.PNRequestObservable;
import com.prenetwork.liyihang.lib_pre_network.PreNetworkHelper;

import java.lang.ref.WeakReference;

public class UpdateUI extends PNBaseObserver {

    private WeakReference<OtherActivity> activity;

    public UpdateUI(OtherActivity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @Override
    public void pre() {
        // del request observable
        PreNetworkHelper.getInstance().removeRequestObservable(MainActivity.url_id_only);
    }

    @Override
    public void result(String res) {
        if (activity.get()==null)
            return;
        activity.get().sendStateSelf(PNBaseActivity.getMsgObj(10, res));
    }

    @Override
    public void error(String err) {
        Toast.makeText(activity.get(), "网络错误", Toast.LENGTH_LONG).show();
    }

    @Override
    public void end() {

    }

    @Override
    public String getId() {
        return MainActivity.url_id_only;
    }
}
