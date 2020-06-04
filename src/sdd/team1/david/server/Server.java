package sdd.team1.david.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import sdd.team1.david.util.Database;
import sdd.team1.david.util.Message;

import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Server {
    private String pwd;

    public Server() {
        pwd = "/home/david/Development/java-test";
    }

    /**
     *
     */
    public void createDump() {
        try {
            File oldFile = new File(this.pwd + "/dump.sql");
            if (oldFile.exists()) {
                oldFile.delete();
            }
            ProcessBuilder pb = new ProcessBuilder();
            pb.command("bash", this.pwd + "/src/sdd/team1/david/util/dbdump.sh", this.pwd);
            pb.directory(new File("/bin"));
            pb.redirectOutput(new File("./log.txt"));
            pb.redirectErrorStream(true);
            pb.redirectError(new File("./errorFile"));
            Process p = pb.start();
            p.waitFor();
            if (p.exitValue() == 0) {
                System.out.println("Dumped sql");
            }
        } catch (IOException e) {

        } catch (InterruptedException ie) {
            //TODO
        }
    }

    /*
           1. check for transfer failure.
            a) if exists, send the string "transferError" followed by the file data
            b) if doesnt exist, send the string "null"
           2. check if dump file exists.
            a) if it exists, send contents of file as string
            b) if doesnt exist, send error log file
            */
    private String checkForOld() {
        String res;

        try {
            File errorLogFile = new File(this.pwd + "/transferError.sql");
            if (errorLogFile.exists()) {
                //if transferError file exists, send that file
                res = Files.readString(errorLogFile.toPath(), StandardCharsets.US_ASCII);
            } else {
                //if transferError file does not exist, create the dump
                createDump();
                File dumpFile = new File(this.pwd + "/dump.sql");
                if (dumpFile.exists()) {
                    System.out.println("dump file exists");
                    res = Files.readString(dumpFile.toPath(), StandardCharsets.US_ASCII);
                    System.out.println(res);
                } else {
                    System.out.println("NO dump file exists");
                    res = "null";
                }
            }

        } catch (IOException e) {
            res = "error";
        }

        return res;
    }

    private void dumpTransferFailed() {
        File oldFile = new File(this.pwd + "./dump.sql");
        File newFile = new File(this.pwd + "./transferError.sql");
        oldFile.renameTo(newFile);
    }

    private void mysqlImport() {
        try {
// mysqldump --databases SDD_PROJECT --user=sddproject --password=password < file.sql
            new Database().MysqlRestore("sddproject", "password", "SDD_PROJECT", this.pwd + "/transfer.sql");
        } catch (Exception e) {

        }
    }

    public void start() {
        try {
            while (true) {
                server();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void server() throws ClassNotFoundException {
        try {
            ServerSocket ss = new ServerSocket(7777);
            System.out.println("ServerSocket awaiting connections...");

            Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            String x = (String) objectInputStream.readObject();
            if (x.equals("send")) {
                //the client wants us to send the file

                String res = checkForOld();
                objectOutputStream.writeObject(res);
                String status = (String) objectInputStream.readObject();
                if (!status.equals("ok")) {
                    //TODO: log error
                    System.out.println("Some error");
                }
            } else if (x.equals("receive")) {
                //the client wants us to receive a file
                String res = (String) objectInputStream.readObject();
                Files.write(Paths.get(this.pwd + "/clientReceived.dump"), res.getBytes());
                System.out.println("data is: " + res);
                mysqlImport();
            }


//


            socket.close();
            ss.close();
        } catch (IOException e) {
            dumpTransferFailed();
        }
    }
}
