package nl.lang2619.bot.utils.jsonclasses;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;

/**
 * Created by Tim on 11/20/2014.
 */
public class Perm implements JSONStreamAware {

    private String name;
    private String permission;

    public Perm(String name, String permission) {
        this.name = name;
        this.permission = permission;
    }

    @Override
    public void writeJSONString(Writer writer) throws IOException {
        LinkedHashMap<String, String> obj = new LinkedHashMap<String, String>();
        obj.put("name", name);
        obj.put("permission", permission);
        JSONValue.writeJSONString(obj, writer);
    }
}
