package spc;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class LoggerSystemOut {

    public LoggerSystemOut() {
        Handler handler = new ConsoleHandler() {

            {
                setOutputStream(System.out);
            }

        };

        Logger.getLogger("").addHandler(handler);
    }

}
