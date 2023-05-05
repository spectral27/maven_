package spc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonPrettyPrinter {

    private final ObjectMapper jackson = new ObjectMapper();
    private final DefaultPrettyPrinter printer = new DefaultPrettyPrinter();

    public JacksonPrettyPrinter() {
        DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter("  ", "\n");
        printer.indentObjectsWith(indenter);
        printer.indentArraysWith(indenter);
    }

    public void print(Object object) throws JsonProcessingException {
        String json = jackson.writer(printer).writeValueAsString(object);
        System.out.println(json);
    }

}
