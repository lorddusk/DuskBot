package nl.lang2619.bot.utils.jsonclasses;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;

/**
 * Created by Tim on 10/14/2014.
 */
public class RankUser implements JSONStreamAware {

    private String name;
    private String rank;

    public RankUser(String name, String rank){
        this.name = name;
        this.rank = rank;
    }

    @Override
    public void writeJSONString(Writer writer) throws IOException {
        LinkedHashMap<String, String> obj = new LinkedHashMap<String, String>();
        obj.put("user",name);
        obj.put("rank",rank);
        JSONValue.writeJSONString(obj, writer);
    }
}
