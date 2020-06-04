import sdd.team1.david.util.ConfigReader;

public class Main {

    public static void main(String[] args) {
        // write your code here
        //System.out.println("Hello word");
        ConfigReader c = null;

        try {
            c = new ConfigReader("config.properties");
        }catch(Exception e){
            System.out.println("Error");;
        }
        assert c != null;
        System.out.println(c.getProperty("SCRIPT_START_TIME"));
    }
}
