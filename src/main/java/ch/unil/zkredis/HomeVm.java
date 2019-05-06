package ch.unil.zkredis;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Sessions;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Date;

public class HomeVm implements Serializable {

    private static final long serialVersionUID = 6551060335946014151L;

    // will be stored in session
    private String storeInSession;

    @Init
    public void onInit() {
        String now = new Date().toString();
        this.storeInSession = now;
        HttpSession nativeSession = (HttpSession) Sessions.getCurrent().getNativeSession();
        System.out.println("Initializing viewModel, now: " +
                now +
                ", session ID: " + nativeSession.getId());
    }

    @Command
    public void checkSession() {
        System.out.println("Got from session: " + storeInSession);
    }
}
