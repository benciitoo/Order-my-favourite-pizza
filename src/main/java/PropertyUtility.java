import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class PropertyUtility {

    public static void getProperties(HashMap<String, String> dictionary) {
        Properties prop = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream("./src/main/properties/config.properties");
            prop.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String key : prop.stringPropertyNames()) {
            dictionary.put(key, prop.get(key).toString());
        }
    }
}
