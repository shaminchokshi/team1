package sdd.team1;

import sdd.team1.david.server.Server;
import sdd.team1.david.util.MyTask;

public interface BatchJobServer extends MyTask {
    Server s = null;
    String pwd=null;
    public void start();
    public String checkForOld();
    public void createDump();
    public void dumpTransferFailed();
    public void mysqlInport();
    public void server();

}
