package nl.lang2619.bot.threads;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.utils.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tim on 10/13/2014.
 */
public class ViewerCommon {
    public static ArrayList<String> viewers = new ArrayList<String>();

    public static Thread updateViewers = new Thread("ModCommon") {
        @Override
        public void run() {
            while (true) {
                try {
                    updateViewers();
                    sleep(180000);
                } catch (Exception e) {
                    e.printStackTrace();
                    Bot.log.error(e.getMessage());
                }
            }
        }
    };

    public static void updateViewers() throws Exception {
        try {
            JSONObject json = new JSONObject(JSONParser.readUrl("http://tmi.twitch.tv/group/user/" + Bot.config.getProperty("autoJoinChannel") + "/chatters"));
            JSONArray view = json.getJSONObject("chatters").getJSONArray("viewers");
            viewers.clear();
            for (int j = 0; j < view.length(); j++) {
                viewers.add(view.getString(j));
            }
            JSONArray mods = json.getJSONObject("chatters").getJSONArray("moderators");
            for (int j = 0; j < mods.length(); j++) {
                viewers.add(mods.getString(j));
            }
        } catch (Exception e) {
            Bot.log.error("Can't reach Twitch.");
            Bot.log.error(e.getMessage());
        }
    }
}
