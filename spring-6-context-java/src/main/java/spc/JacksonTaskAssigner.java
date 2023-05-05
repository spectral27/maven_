package spc;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JacksonTaskAssigner {

    private final JacksonPrettyPrinter jacksonPrettyPrinter;

    public JacksonTaskAssigner(JacksonPrettyPrinter jacksonPrettyPrinter) {
        this.jacksonPrettyPrinter = jacksonPrettyPrinter;
    }

    public void print(Version version) throws JsonProcessingException {
        jacksonPrettyPrinter.print(version);
    }

}
