package spc;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        JacksonTaskAssigner jacksonTaskAssigner = context.getBean("assigner", JacksonTaskAssigner.class);

        Version java = new Version(1, System.getProperty("java.vendor"), System.getProperty("java.version"));
        Version spring = new Version(2, "Spring Framework", "6.0.8");
        Version jackson = new Version(3, "Jackson Databind", "2.15.0");

        try {
            jacksonTaskAssigner.print(java);
            jacksonTaskAssigner.print(spring);
            jacksonTaskAssigner.print(jackson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
