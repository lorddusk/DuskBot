package nl.lang2619.bot.utils.jsonclasses;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;

/**
 * Created by Tim on 25-11-2014.
 */
public class Command implements JSONStreamAware {

    private String command;
    private String response;

    public Command(String command, String response) {
        this.command = command;
        this.response = response;
    }

    @Override
    public void writeJSONString(Writer writer) throws IOException {
        LinkedHashMap<String, String> obj = new LinkedHashMap<String, String>();
        obj.put("command", command);
        obj.put("response", response);
        JSONValue.writeJSONString(obj, writer);
    }
}
