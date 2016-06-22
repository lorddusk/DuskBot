package nl.lang2619.bot.utils.jsonclasses;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;

/**
 * Created by Tim on 10/14/2014.
 */
public class Ranks implements JSONStreamAware {

    private String name;
    private Long amount;

    public Ranks(String name, Long amount){
        this.name = name;
        this.amount = amount;
    }

    @Override
    public void writeJSONString(Writer writer) throws IOException {
        LinkedHashMap<String, java.io.Serializable> obj = new LinkedHashMap<String, java.io.Serializable>();
        obj.put("name",name);
        obj.put("amount",amount);
        JSONValue.writeJSONString(obj, writer);
    }
}
