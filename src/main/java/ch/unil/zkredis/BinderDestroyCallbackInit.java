package ch.unil.zkredis;

import org.zkoss.bind.callback.DestroyCallback;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.Configuration;
import org.zkoss.zk.ui.util.WebAppInit;

// Copied from https://potix.freshdesk.com/support/tickets/6742

public class BinderDestroyCallbackInit implements WebAppInit {
    @Override
    public void init(WebApp webApp) throws Exception {
        Configuration conf = webApp.getConfiguration();
        if (!conf.hasCallBack("destroy")) {
            conf.registerCallBack("destroy", new DestroyCallback());
        }
    }
}