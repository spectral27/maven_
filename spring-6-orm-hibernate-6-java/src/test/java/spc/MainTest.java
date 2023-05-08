package spc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MainTest {

    private static ApplicationContext applicationContext;

    @BeforeAll
    public static void createContext() {
        try {
            Class.forName("org.h2.Driver");
            DriverManager.getConnection(
                    "jdbc:h2:mem:database01;INIT=runscript from 'classpath:init.sql'",
                    "username",
                    "password"
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        applicationContext = new AnnotationConfigApplicationContext(SpringBeans.class);
    }

    @Test
    public void mainTest() {
        JavaObjectRepository repository = applicationContext.getBean(JavaObjectRepository.class);
        JavaObject javaObject = new JavaObject();
        javaObject.setVendor("JUnit");
        javaObject.setVersion("5.9.2");
        repository.insertJavaObject(javaObject);
        List<JavaObject> javaObjects = repository.selectJavaObjects();
        Assertions.assertNotNull(javaObjects);
        Assertions.assertEquals(1, javaObjects.size());
        Assertions.assertNotNull(javaObjects.get(0).getVendor());
        Assertions.assertNotNull(javaObjects.get(0).getVersion());
        System.out.printf("%s %s\n", javaObjects.get(0).getVendor(), javaObjects.get(0).getVersion());
    }

}
