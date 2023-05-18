package spec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MainTest {

    private static ClassPathXmlApplicationContext context;

    @BeforeAll
    public static void init() {
        H2DatabaseInit.init();
        context = new ClassPathXmlApplicationContext("beans1.xml");
    }

    @Test
    public void mainTest() {
        JavaObjectService service = context.getBean(JavaObjectService.class);
        JavaObject junit = new JavaObject(1, "JUnit Jupiter", "5.9.2");
        service.insert(junit);
        List<JavaObject> javaObjects = service.selectAll();
        Assertions.assertNotNull(javaObjects);
        Assertions.assertEquals(1, javaObjects.size());
        Assertions.assertEquals(1, javaObjects.get(0).getId());
        Assertions.assertEquals("JUnit Jupiter", javaObjects.get(0).getVendor());
        Assertions.assertEquals("5.9.2", javaObjects.get(0).getVersion());
        System.out.println(javaObjects.get(0));
    }

}
