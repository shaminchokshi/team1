package sdd.team1.david.util;

import java.util.*;
import java.io.*;

public class ConfigReader {

    public Properties p;
    public FileInputStream ip;

    public ConfigReader(String filePath) throws IOException {
        p = new Properties();
        /*
          Note: We are using a relative path here. If the debug and deployment machines
          are *nix, we can use `$(pwd)/config.properties` instead. However this will
          not work on windows
         */
        try {
            ip = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            System.out.println("Config File Not Found");
        }
        //this makes sure that the FileInputStream object ip is never null
        //if it is null, then the assertion fails and the program stops executing
        assert ip != null;

        p.load(ip);
    }

    public String getProperty(final String prop) {
        return p.getProperty(prop);
    }

}