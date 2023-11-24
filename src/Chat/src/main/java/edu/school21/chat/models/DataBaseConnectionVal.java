package edu.school21.chat.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataBaseConnectionVal {
    private static final Properties PROPERTIES = new Properties();

    public DataBaseConnectionVal() {
        try {
            uploadProperty();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void uploadProperty() throws IOException {
        String cur = new File(".").getCanonicalPath();
        System.out.println(cur);
        try (var input = new FileInputStream("src/main/resources/properties.properties")) {
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getAnyVal(String key) {
        return PROPERTIES.getProperty(key);
    }

}
