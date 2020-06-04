package sdd.team1.david.client;

import sdd.team1.david.util.*;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Client {

    private Socket socket;
    public String pwd;
    InputStream inputStream;
    OutputStream outputStream;

    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;


    public Client() {
        pwd = "/home/david/Development/java-test";
    }
    public Boolean socketIsBound(){
        return socket.isBound();
    }
    public Boolean socketIsConnected(){
        return socket.isConnected();
    }
    public Boolean socketIsClosed(){
        return socket.isClosed();
    }
    public void createDump() {
        try {
            File oldFile = new File(this.pwd + "/clientDumpError.sql");
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


    private String checkForOld() {
        String res;

        try {
            File errorLogFile = new File(this.pwd + "/clientTransferError.sql");
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

    public void sendFile(String host, int port) throws IOException, ClassNotFoundException {
        //need host and port, we want to connect to the ServerSocket at port 7777
        socket = new Socket("localhost", 7777);
        System.out.println("Connected!");

        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();

        objectOutputStream = new ObjectOutputStream(outputStream);
        objectInputStream = new ObjectInputStream(inputStream);

        /*
        1. tell the server to receive a file
        1.5. Create the dump file
        2. check if the file exists:
         a) if it exists, read it in to a string
         b) if it does not exist, throw an exception and give string default value
        3. Send the string over the socket
        4. Wait for a response
        5. check the response

         */

        String response = "";
        //1.we want to send a file to the server so we tell it to receive a file
        objectOutputStream.writeObject("receive");
        try {
            //1.5 creating dump file
            createDump();
            response = checkForOld();
        } catch (Exception e) {
            System.out.println("Exception thrown");
            response = "exception";
        } finally {
            System.out.println("Finally");
        }
        //2. checking if the file exists


        //fill this with the db dump
        objectOutputStream.writeObject(response);

        System.out.println("Closing socket and terminating program.");
        socket.close();
    }

    public void receiveFile(String host, int port) throws IOException, ClassNotFoundException {
        //need host and port, we want to connect to the ServerSocket at port 7777
        socket = new Socket(host, port);
        System.out.println("Connected!");

        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();

        objectOutputStream = new ObjectOutputStream(outputStream);
        objectInputStream = new ObjectInputStream(inputStream);

        //we want to receive a file so we tell the server to send us an object
        objectOutputStream.writeObject("send");
        String x = "";
        try {
            x = (String) objectInputStream.readObject();
        } catch (Exception e) {
            objectOutputStream.writeObject("error");
        } finally {
            objectOutputStream.writeObject("ok");
        }
        //System.out.println("data is "+x);
        System.out.println("Writing file");
        Files.write(Paths.get(this.pwd + "/transfer.sql"), x.getBytes());
        System.out.println("Closing socket and terminating program.");
        socket.close();

        mysqlImport();
    }

    private void mysqlImport() {
        new Database().MysqlRestore("sddproject","password","SDD_PROJECT",this.pwd+"/transfer.sql");
    }
}
