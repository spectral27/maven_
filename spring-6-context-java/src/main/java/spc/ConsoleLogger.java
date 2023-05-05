package spc;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class ConsoleLogger {

    public static Logger get(Class<?> clazz) {
        Logger logger = Logger.getLogger(clazz.getSimpleName());
        logger.addHandler(new ConsoleHandler() {

            {
                setOutputStream(System.out);
            }

        });
        return logger;
    }

}
