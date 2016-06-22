package nl.lang2619.bot.threads;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.utils.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tim on 10/13/2014.
 */
public class ModCommon {
    public static ArrayList<String> moderators = new ArrayList<String>();

    public static Thread updateMods = new Thread("ModCommon") {
        @Override
        public void run() {
            while (true) {
                try {
                    updateModerators();
                    sleep(300000);
                } catch (Exception e) {
                    e.printStackTrace();
                    Bot.log.error(e.getMessage());
                }
            }
        }
    };

    public static void updateModerators() throws Exception {
        try {
            moderators.clear();
            JSONObject json = new JSONObject(JSONParser.readUrl("http://tmi.twitch.tv/group/user/" + Bot.config.getProperty("autoJoinChannel") + "/chatters"));
            for (int i = 0; i < json.length(); i++) {
                JSONArray mods = json.getJSONObject("chatters").getJSONArray("moderators");
                for (int j = 0; j < mods.length(); j++) {
                    moderators.add(mods.getString(j));
                    if (!Bot.permList.containsKey(mods.getString(j))) {
                        Bot.permList.put(mods.getString(j).toLowerCase(), "mod");
                        Bot.saveAllTheThings();
                    }
                }
            }
        }catch(Exception e){
            Bot.log.error("Can't reach Twitch.");
            Bot.log.error(e.getMessage());
        }
    }
}
