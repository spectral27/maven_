package spc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class SpringBeans {

    private final Logger logger = ConsoleLogger.get(SpringBeans.class);

    @Bean
    public JacksonTaskAssigner jacksonTaskAssigner(JacksonPrettyPrinter jacksonPrettyPrinter) {
        logger.info("Creating JacksonTaskAssigner instance");
        logger.info("Injecting JacksonPrettyPrinter dependency");
        return new JacksonTaskAssigner(jacksonPrettyPrinter);
    }

    @Bean
    public JacksonPrettyPrinter jacksonPrettyPrinter() {
        logger.info("Creating JacksonPrettyPrinter instance");
        return new JacksonPrettyPrinter();
    }

}
