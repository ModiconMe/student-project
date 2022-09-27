package edu.javacourse.student_order.config;

import java.io.*;
import java.util.Properties;

public class Config {
    public static final String DB_URL = "db.url";
    public static final String DB_LOGIN = "db.login";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_LIMIT = "db.limit";
    private static final Properties properties = new Properties();

    public static String getProperty(String name) {
        if (properties.isEmpty()) {
            try (FileInputStream is = new FileInputStream(
//                    "C:\\Users\\USER\\Desktop\\student-project\\src\\main\\resources\\dao.properties")
            "F:\\1.Study\\Java\\1. Java_core\\projects\\student-project\\src\\main\\resources\\dao.properties")
//            "C:\\Users\\AVTOMATIX\\Desktop\\student-project\\src\\main\\resources\\dao.properties")
            ) {
                properties.load(is);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        return properties.getProperty(name);
    }

}
