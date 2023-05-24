package spc;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class JavaObjectRepositoryTest {

    private final JavaObjectRepository repository = new JavaObjectRepository();

    @BeforeAll
    public static void beforeAll() {
        try {
            Class.forName("org.h2.Driver");
            DriverManager.getConnection(
                    "jdbc:h2:mem:database01;INIT=runscript from 'classpath:test_repository.sql'",
                    "username",
                    "password"
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Test
    public void getIdsTest() {
        List<Integer> ids = repository.getIds();
        Assertions.assertNotNull(ids);
        Assertions.assertEquals(List.of(1), ids);
    }

    @Test
    public void getIdsCriteriaBuilderTest() {
        List<Integer> ids = repository.getIdsCriteriaBuilder();
        Assertions.assertNotNull(ids);
        Assertions.assertEquals(List.of(1), ids);
    }

    @AfterAll
    public static void afterAll() {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:database01", "username", "password");
            connection.createStatement().execute("drop table JavaObject");
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
