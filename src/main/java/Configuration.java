import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class Configuration {
    static File configFile = new File("src/main/resources/Configuration.properties");

    public static String getConfig(String key) {
        String value = "";
        try {
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);

            value = props.getProperty(key);
            reader.close();
        } catch(Exception e) {
            System.out.println(e);
        }
        return value;
    }
}
