package sdd.team1.david.util;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Logger {
    private FileWriter fw;
    private DateFormat dateFormat;

    public Logger(String fileName) throws IOException {
        fw = new FileWriter(fileName);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }
    private String getDateTime(){
        return dateFormat.format(new Date());
    }
    public void Log(String topic, String message) throws IOException {
        fw.write(getDateTime()+ "topic: "+topic+ " message: "+message);
    }

    @Override
    protected void finalize() throws Throwable {
        fw.close();
    }
}