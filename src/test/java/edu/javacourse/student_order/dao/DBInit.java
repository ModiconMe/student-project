package edu.javacourse.student_order.dao;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class DBInit {
    public static void init() throws Exception {
        URL url1 = DictionaryDAOImplTest.class.getClassLoader().getResource("student_project.sql");
        URL url2 = DictionaryDAOImplTest.class.getClassLoader().getResource("student_data.sql");

        List<String> strings1 = Files.readAllLines(Paths.get(url1.toURI()));
        String sql1 = String.join("", strings1);

        List<String> strings2 = Files.readAllLines(Paths.get(url2.toURI()));
        String sql2 = String.join("", strings2);

        try (Connection connection = ConnectionBuilder.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql1);
            statement.executeUpdate(sql2);
        }
    }
}
