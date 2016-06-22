package nl.lang2619.bot.utils.jsonclasses;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;

/**
 * Created by Tim on 5/5/2015.
 */
public class Blacklist implements JSONStreamAware {

    private String user;

    public Blacklist(String s) {
        this.user = s;
    }

    @Override
    public void writeJSONString(Writer writer) throws IOException {
        LinkedHashMap<String, String> obj = new LinkedHashMap<String, String>();
        obj.put("user",user);
        JSONValue.writeJSONString(obj,writer);
    }
}
