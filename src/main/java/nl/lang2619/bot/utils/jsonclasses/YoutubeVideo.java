package nl.lang2619.bot.utils.jsonclasses;

import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.JSONParser;
import nl.lang2619.bot.utils.YoutubeParser;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Tim on 6/10/2015.
 */
public class YoutubeVideo {

    private String link; //https://www.youtube.com/watch?v=id
    private String title;
    private String uploader;
    private String duration;
    private int number;

    public YoutubeVideo(String link) {
        String id;
        if(YoutubeParser.getVideoId(link) == null){
            id = link;
        }else{
            id = YoutubeParser.getVideoId(link);
        }

        this.link = "https://www.youtube.com/watch?v=" + id;
        try {
            JSONObject json = new JSONObject(JSONParser.readUrl("https://www.googleapis.com/youtube/v3/videos?id=" + id + "&key=<KEYHERE>&part=snippet,contentDetails&fields=items(snippet/title,snippet/channelTitle,contentDetails/duration)"));
            JSONArray array = json.getJSONArray("items");
            JSONObject snippet = array.getJSONObject(0).getJSONObject("snippet");
            JSONObject content = array.getJSONObject(0).getJSONObject("contentDetails");
            String title = String.valueOf(snippet.get("title"));
            String channelTitle = String.valueOf(snippet.get("channelTitle"));
            String duration = String.valueOf(content.get("duration"));

            this.title = title;
            this.uploader = channelTitle;
            this.duration = youtubeString(duration);
            this.number = Defaults.getSongRequestNumber();

        } catch (Exception e) {
        }
    }


    public String getDuration() {
        return duration;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getUploader() {
        return uploader;
    }

    public int getNumber() {
        return number;
    }

    private static String youtubeString(String duration) {
        String minutes = "0";
        String seconds = "";
        String hours = "";

        duration = duration.replace("PT", "");

        if (duration.contains("H")) {
            hours = duration.split("H")[0];
            duration = duration.substring(duration.indexOf("H") + 1);
        }
        if (duration.contains("M")) {
            minutes = duration.split("M")[0];
            duration = duration.substring(duration.indexOf("M") + 1);
        }
        if (duration.contains("S")) {
            seconds = duration.split("S")[0];
        }
        if (!hours.equals("")) {
            if (minutes.equals(""))
                minutes = "00";
            if (minutes.length() == 1)
                minutes = "0" + minutes;
        }
        if (!minutes.equals("0")) {
            if (seconds.equals(""))
                seconds = "00";
            if (seconds.length() == 1)
                seconds = "0" + seconds;
        }
        if (hours.equals(""))
            return minutes + ":" + seconds;
        else
            return hours + ":" + minutes + ":" + seconds;

    }
}
