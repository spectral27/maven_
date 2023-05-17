package spec;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
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

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans1.xml");

        JavaObjectService service = context.getBean(JavaObjectService.class);

        JavaObject java = new JavaObject();
        java.setId(1);
        java.setVendor(System.getProperty("java.vendor"));
        java.setVersion(System.getProperty("java.version"));

        service.insert(java);

        JavaObject another = new JavaObject();
        another.setId(1);
        another.setVendor(System.getProperty("java.vendor"));
        another.setVersion(System.getProperty("java.version"));

        // Transaction rollback (duplicate primary key)
        try {
            service.insert(another);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        service.select().forEach(j -> System.out.println(j.getVendor() + " " + j.getVersion()));
    }

}
