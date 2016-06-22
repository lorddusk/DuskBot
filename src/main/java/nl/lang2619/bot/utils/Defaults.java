package nl.lang2619.bot.utils;

import com.rosaloves.bitlyj.Url;
import nl.lang2619.bot.Bot;
import nl.lang2619.bot.utils.json.Save;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import static com.rosaloves.bitlyj.Bitly.as;
import static com.rosaloves.bitlyj.Bitly.shorten;

/**
 * Created by Tim on 17-3-2015.
 */
public class Defaults {

    //Duskbot Stuff
    public static final String VERSION = "1.1 Beta";
    public static final String CHANGELOG = "Check the changelog here : http://www.duskbot.nl/changelog.php";
    //public static File serverConfig = new File("config/server.yml");
    public static File vipChecksum = new File("config/vip.dkt");

    //Toggles
    public static boolean debugToggled = false;
    public static boolean toggleStream = false;
    public static boolean isVip = false;
    public static boolean chatServer = true;

    public static boolean linkPurge = Boolean.getBoolean(Bot.extra.getProperty("linkToggle"));
    public static boolean capsPurge = Boolean.getBoolean(Bot.extra.getProperty("capsToggle"));
    public static boolean wotPurge = Boolean.getBoolean(Bot.extra.getProperty("wotToggle"));
    public static boolean schedule = Boolean.getBoolean(Bot.extra.getProperty("scheduleToggle"));
    public static boolean whisperToggle = Boolean.getBoolean(Bot.extra.getProperty("whisperToggle"));

    //Default values
    public static int lastStrawpoll = 0;
    public static int raffleCost = 100;
    public static int time = 5;
    public static Long totalPoints = (long) 0;

    //Sub messages
    public static String newSub = "";
    public static String oldSub = "";

    public static String cttText = Bot.extra.getProperty("ctt");
    public static boolean songRequestBoolean = true;
    public static int songRequestNumber = 1;

    public static void incSongRequestNumber() {
        songRequestNumber++;
    }

    public static int getSongRequestNumber() {
        return songRequestNumber;
    }

    public static String getBitlyLink() {
        String bitlyAPI = ""; //BitlyAPI here
        String bitlyUser = "duskbot";
        Url url = as(bitlyUser, bitlyAPI).call(shorten("https://twitter.com/intent/tweet?text=" + cttText + "&source=duskbot"));
        return url.getShortUrl();
    }

    public static int getRaffleCost() {
        return raffleCost;
    }

    public static void setRaffleCost(int set) {
        raffleCost = set;
    }

    public static String getPointName() {
        if (isVip) {
            return Bot.config.getProperty("points");
        } else {
            return "points";
        }
    }

    public static String getBotName() {
        if (isVip) {
            return Bot.config.getProperty("nick");
        } else {
            return "Duskb0t";
        }
    }

    public static String getOAuth() {
        if (isVip) {
            return Bot.config.getProperty("oauth");
        } else {
            return ""; //OAUTH HERE
        }
    }

    public static String getStreamer() {
        if (Bot.config.getProperty("autoJoinChannel") == null) {
            try {
                throw new Exception("Username can't be empty");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return Bot.config.getProperty("autoJoinChannel").toLowerCase();
        }
        return Bot.config.getProperty("autoJoinChannel").toLowerCase();
    }

    public static void setLastStrawpoll(int i) {
        lastStrawpoll = i;
    }

    public static String fuseArray(String[] array, int start) {
        String fused = "";
        for (int c = start; c < array.length; c++)
            fused += array[c] + " ";

        return fused.trim();

    }

    public static String latestVersion() throws Exception {
        JSONObject json = new JSONObject(JSONParser.readUrl("http://www.duskbot.nl/version.json"));
        String websiteVersion = json.getString("Version");
        return websiteVersion;
    }

    public static String getServer() {
        if (Bot.config.getProperty("chatServer") == null) {
            try {
                throw new Exception("Twitch Chat Server cannot be empty. You can use true for irc.chat.twitch.tv or false for irc.twitch.tv");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(Boolean.parseBoolean(Bot.config.getProperty("chatServer"))){
            return "irc.chat.twitch.tv";
        }else{
            return "irc.twitch.tv";
        }
    }

    public static int getPort() {
        if (Bot.config.getProperty("chatServer") == null) {
            try {
                throw new Exception("Twitch Chat Server cannot be empty. You can use true for irc.chat.twitch.tv or false for irc.twitch.tv");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(Boolean.parseBoolean(Bot.config.getProperty("chatServer"))){
            return 80;
        }else{
            return 6667;
        }
    }
}
