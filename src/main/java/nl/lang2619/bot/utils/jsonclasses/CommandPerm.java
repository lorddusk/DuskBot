package nl.lang2619.bot.utils.jsonclasses;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;

/**
 * Created by Tim on 25-11-2014.
 */
public class CommandPerm implements JSONStreamAware {

    private String command;
    private String permission;

    public CommandPerm(String command, String permission) {
        this.command = command;
        this.permission = permission;
    }

    @Override
    public void writeJSONString(Writer writer) throws IOException {
        LinkedHashMap<String, String> obj = new LinkedHashMap<String, String>();
        obj.put("command", command);
        obj.put("permission", permission);
        JSONValue.writeJSONString(obj, writer);
    }
}
