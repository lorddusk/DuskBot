package nl.lang2619.bot.utils.jsonclasses;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;

/**
 * Created by Tim on 25-11-2014.
 */
public class Schedule implements JSONStreamAware {

    private Long number;
    private String message;
    private boolean toggle;

    public Schedule(Long number, String message, boolean toggle) {
        this.number = number;
        this.message = message;
        this.toggle = toggle;
    }

    @Override
    public void writeJSONString(Writer writer) throws IOException {
        LinkedHashMap<String, java.io.Serializable> obj = new LinkedHashMap<String, java.io.Serializable>();
        obj.put("number", number);
        obj.put("message", message);
        obj.put("toggle", toggle);
        JSONValue.writeJSONString(obj, writer);
    }

    public Long getNumber() {
        return number;
    }

    public String getMessage() {
        return message;
    }

    public boolean getToggle(){
        return toggle;
    }

    public void setToggle(boolean toggle1){
        this.toggle = toggle1;
    }
}
