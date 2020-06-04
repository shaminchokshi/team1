package sdd.team1.tester;
import sdd.team1.david.client.Client;
import sdd.team1.david.server.Server;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class T extends Thread {
    Server server = new Server();
    @Override
    public void run() {
        server.start();
    }
}

public class Tester {
    private Client c = new Client();
    private Server s = new Server();
    private T t1 = new T();

    public Tester() {
        t1.start();


    }

    public void testServer(){
        System.out.println("Starting Client Testing");
        try{

        }catch (NullPointerException e){

        }
    }

    public void testClient() throws InterruptedException {

        System.out.println("Starting Client Testing");
//        try {
//            c.sendFile("localhost",7777);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        try {
            assert (c.socketIsBound());
            System.out.println("Socket: Bind Successful");

            assertEquals(false, c.socketIsBound(), "Error: Bind Failed. ALready in use");

            assert (c.socketIsClosed());
        } catch (NullPointerException e) {
            System.out.println("Exception: [" + e.getClass().toString().substring(6) + "] Socket Not Initialised");
        } finally {
            new File(c.pwd + "/dump.sql").delete();
            c.createDump();

        }

        File f = new File(c.pwd + "/dump.sql");
        assertTrue(f.exists(), "Error: Could not dump database");
        System.out.println("Dump: Successful");

        t1.join();

        assertEquals(false, c.socketIsBound(), "Error: Socket release Failed. ALready in use");
    }

}
