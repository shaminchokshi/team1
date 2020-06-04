package sdd.team1;

import sdd.team1.david.server.Server;
import sdd.team1.david.client.Client;
import sdd.team1.david.util.MyTask;
import sdd.team1.david.util.MyTaskExecutor;
import sdd.team1.tester.Tester;

import java.io.File;

class Test implements MyTask {
    public void execute() {
        System.out.println("Hello workd");
    }
}

public class Main {


    public static void main(String[] args) {
        try {
//            Runtime.getRuntime().exec("/usr/bin/mysqldump --databases SDD_PROJECT --user=sddproject --password=password > ~/Development/java-test/lol.sql")
//            Runtime.getRuntime().exec("mysqldump --databases SDD_PROJECT --user=sddproject --password=password > ~/Development/java-test/dump.sql");

            Tester t= new Tester();
            t.testClient();

// receive file is working. Work on Client::sendFile
//            Client c = new Client();
//            c.createDump();
//            c.receiveFile("localhost", 7777);
//            c.sendFile("localhost", 7777);
//            Server s = new Server();
//            s.createDump();
//            s.start();

//            Test t = new Test();
//            MyTaskExecutor te = new MyTaskExecutor(t);
//            te.startExecutionAt(21, 52, 15);
//            te.stop();
//            Calendar rightNow = Calendar.getInstance();
//            int hour = rightNow.get(Calendar.HOUR_OF_DAY);
//            System.out.println(hour);
//
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection con=DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/SDD_PROJECT","sddproject","password    ");
////here sonoo is database name, root is username and password
//            Statement stmt=con.createStatement();
//            ResultSet rs=stmt.executeQuery("SELECT * FROM TEST");
//            while(rs.next())
//                System.out.println(rs.getString(1)+"  "+rs.getInt(2));
//            con.close();
            // Runtime.getRuntime().exec("mysqldump -u sddproject -p password SDD_PROJECT > dump.sql");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}

