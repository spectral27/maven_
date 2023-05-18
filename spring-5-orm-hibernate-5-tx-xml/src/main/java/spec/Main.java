package spec;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        H2DatabaseInit.init();

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans1.xml");
        JavaObjectService service = context.getBean(JavaObjectService.class);

        JavaObject object = new JavaObject(1, System.getProperty("java.vendor"), System.getProperty("java.version"));
        service.insert(object);

        // Transaction rollback (duplicate primary key)
        JavaObject another = new JavaObject(1, "Vendor", "Version");

        try {
            service.insert(another);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //

        List<JavaObject> objects = service.selectAll();
        objects.forEach(System.out::println);
    }

}
