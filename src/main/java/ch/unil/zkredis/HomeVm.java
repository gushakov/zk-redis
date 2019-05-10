package ch.unil.zkredis;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.event.UploadEvent;

import java.io.Serializable;

public class HomeVm implements Serializable {

    private static final long serialVersionUID = 6551060335946014151L;

    @Command
    public void doOnUpload(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent uploadEvent) {
        System.out.println(">>>>>>>>>>>>>" + uploadEvent);
        System.out.println(">>>>>>>>>>>>>" + uploadEvent.getMedia());
    }
}
