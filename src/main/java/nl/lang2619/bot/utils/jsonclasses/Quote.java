package nl.lang2619.bot.utils.jsonclasses;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;

/**
 * Created by Tim on 18-11-2014.
 */
public class Quote  implements JSONStreamAware {

    private Long number;
    private String quote;

    public Quote(Long number, String quote){
        this.number = number;
        this.quote = quote;
    }

    @Override
    public void writeJSONString(Writer writer) throws IOException {
        LinkedHashMap<String, java.io.Serializable> obj = new LinkedHashMap<String, java.io.Serializable>();
        obj.put("number",number);
        obj.put("quote", quote);
        JSONValue.writeJSONString(obj, writer);
    }
}