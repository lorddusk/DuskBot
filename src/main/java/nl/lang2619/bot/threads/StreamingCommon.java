package nl.lang2619.bot.threads;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.utils.JSONParser;
import org.json.JSONObject;

/**
 * Created by Tim on 10/12/2014.
 */
public class StreamingCommon {

    public static Thread checkIfOnline = new Thread() {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(240000);
                    isOnline();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public static boolean isOnline() throws Exception {
        if (new JSONObject(JSONParser.readUrl("https://api.twitch.tv/kraken/streams/" + Bot.config.getProperty("autoJoinChannel"))).getString("stream").contains("game")) {
            return true;
        } else{
            return false;
        }
    }

}
