package nl.lang2619.bot.utils.jsonclasses;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;

/**
 * Created by Tim on 6/10/2015.
 */
public class SongRequest implements JSONStreamAware{

    private String link; //https://www.youtube.com/watch?v=id
    private String title;
    private String uploader;
    private String duration;
    private String requester;
    private int number;

    public SongRequest(String request,int number,String link,String title,String uploader,String duration){
        this.requester = request;
        this.number = number;
        this.link = link;
        this.title = title;
        this.uploader = uploader;
        this.duration = duration;
    }

    @Override
    public void writeJSONString(Writer out) throws IOException {
        LinkedHashMap<String, java.io.Serializable> obj = new LinkedHashMap<String, java.io.Serializable>();
        obj.put("request",requester);
        obj.put("number", number);
        obj.put("link",link);
        obj.put("title",title);
        obj.put("uploader",uploader);
        obj.put("duration",duration);
        JSONValue.writeJSONString(obj,out);
    }
}
