package ch.unil.zkredis;

import org.slf4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class HomeVm implements Serializable {
    private static final Logger logger = getLogger(HomeVm.class);
    private static final long serialVersionUID = 6551060335946014151L;

    private AtomicInteger count = new AtomicInteger(0);

    public int getCount() {
        return count.get();
    }

    public String getNode(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }

    @Init
    public void init() {
        logger.debug("[Init] Initializing view-model");

    }

    @Command("doOnClick")
    @NotifyChange("count")
    public void doOnClick(){
        logger.debug("[Command] Click... {}", count.getAndIncrement());
    }

}
