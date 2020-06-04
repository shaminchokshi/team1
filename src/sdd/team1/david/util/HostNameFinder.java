package sdd.team1.david.util;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class HostNameFinder {

    private String ip;

    public String getHostName(){
        return this.ip;
    }

    public HostNameFinder(){

        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            this.ip = socket.getLocalAddress().getHostAddress();
        }catch(UnknownHostException u){
            System.out.println("Could not find your computer's ip address");
        }
        catch(SocketException s){
            System.out.println("Error while binding the socket");
        }

    }

}
