package sdd.team1;

import sdd.team1.david.client.Client;
import sdd.team1.david.util.MyTask;

import java.io.IOException;

public interface BatchJobClient extends MyTask {
    Client c = new Client();
    String pwd = "";
    public void createDump();
    public String checkForOld();
    public void sendFile(String host,int port) throws IOException;
    public void receiveFile(String host, int port) throws IOException, ClassNotFoundException;
    public void mysqlImport();
}
