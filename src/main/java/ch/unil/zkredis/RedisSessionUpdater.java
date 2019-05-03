package ch.unil.zkredis;

import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.sys.Attributes;
import org.zkoss.zk.ui.util.ExecutionCleanup;

import java.util.List;

/*
    Work around for serialized session update, for more details see
    http://forum.zkoss.org/question/109524/zk-spring-boot-spring-session/
    The bug is still there even if we do not use this.
    Question: has his been already fixed in the later versions of ZK (since the time of the post on the forum) ?
 */

public class RedisSessionUpdater implements ExecutionCleanup {
    public void cleanup(Execution exec, Execution parent, List<Throwable> errs) throws Exception {
        Session session = exec.getSession();
        session.setAttribute(Attributes.ZK_SESSION, session.getAttribute(Attributes.ZK_SESSION));
    }
}
