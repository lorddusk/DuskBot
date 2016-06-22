package nl.lang2619.bot.commands;

import javafx.application.Application;
import nl.lang2619.bot.Bot;
import nl.lang2619.bot.utils.*;
import nl.lang2619.bot.utils.json.Save;
import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Tim on 5/20/2015.
 */
public class ChannelMethods {


    public static String arg = "";
    public static ArrayList<String> argList = new ArrayList<>();


    public static void preMessage(MessageEvent<PircBotX> event) {
        arg = "";
        argList.clear();

        if (event.getMessage().split(" ").length >= 2) {
            for (int i = 1; i < event.getMessage().split(" ").length; i++) {
                if (i == event.getMessage().split(" ").length) {
                    arg += (event.getMessage().split(" ")[i]);
                    argList.add(event.getMessage().split(" ")[i]);
                } else {
                    arg += (event.getMessage().split(" ")[i] + " ");
                    argList.add(event.getMessage().split(" ")[i]);
                }
            }
        }
    }


    public static void Subscriber(MessageEvent<PircBotX> event) {
        String[] messenger = event.getMessage().split(" ");
        char[] username = messenger[0].trim().toCharArray();
        username[0] = Character.toUpperCase(username[0]);
        String user = new String(username);

        if (messenger[1].equals("just") && messenger[2].equals("subscribed!")) {
            String sentMessage = Defaults.newSub.replace("#user", user);
            MessageSending.sendNormalMessage(sentMessage);
        }
        if (messenger[1].equals("subscribed") && messenger[2].equals("for")) {
            String sentMessage = Defaults.oldSub.replace("#months", messenger[3]).replace("#user", user);
            MessageSending.sendNormalMessage(sentMessage);
        }
    }

    public static void Oldsub(MessageEvent<PircBotX> event) throws IOException {
        if (Permissions.getLevel(getNick(event)) >= 3) {
            Defaults.oldSub = arg;
            Save.subMessages();
        }
    }

    public static void Newsub(MessageEvent<PircBotX> event) throws IOException {
        if (Permissions.getLevel(getNick(event)) >= 3) {
            Defaults.newSub = arg;
            Save.subMessages();
        }
    }

    public static void Host(MessageEvent<PircBotX> event) throws Exception {
        if (Permissions.getLevel(getNick(event)) >= 3) {
            MessageSending.sendNormalMessage("/unhost");
            hostingMode(arg, event);
        }
    }

    public static void Note(MessageEvent<PircBotX> event) throws IOException {
        if (Permissions.getLevel(getNick(event)) >= 2) {
            boolean noteAdded = false;
            try {
                String completeQuote = arg;
                long note = (long) Bot.noteList.size() + 1;
                while (!noteAdded) {
                    if (!Bot.noteList.containsKey(note)) {
                        Bot.noteList.put(note, completeQuote);
                        noteAdded = true;
                        MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), " Note has been added as #" + note + ".");
                        Bot.log.info("Note " + note + " has been added");
                    } else {
                        note += 1;
                        noteAdded = false;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                Bot.saveAllTheThings();
            }
        }
    }

    public static void ToggleWoT(MessageEvent<PircBotX> event) {
        if (Permissions.getLevel(getNick(event)) >= 2) {
            if (getWotPurge().equals("on")) {
                Defaults.wotPurge = false;
                MessageSending.sendWhisper(event.getUser().getNick(), "Purging of walls of text has been toggled off.");
                Bot.extra.setProperty("wotToggle", "false");
            } else {
                Defaults.wotPurge = true;
                MessageSending.sendWhisper(event.getUser().getNick(), "Purging of walls of text has been toggled on.");
                Bot.extra.setProperty("wotToggle", "true");
            }
        }
    }

    public static void ToggleCaps(MessageEvent<PircBotX> event) {
        if (Permissions.getLevel(getNick(event)) >= 2) {
            if (getCapsPurge().equals("on")) {
                Defaults.capsPurge = false;
                MessageSending.sendWhisper(event.getUser().getNick(), "Purging of caps has been toggled off.");
                Bot.extra.setProperty("capsToggle", "false");
            } else {
                Defaults.capsPurge = true;
                MessageSending.sendWhisper(event.getUser().getNick(), "Purging of caps has been toggled on.");
                Bot.extra.setProperty("capsToggle", "true");
            }
        }
    }

    public static void ToggleLinks(MessageEvent<PircBotX> event) {
        if (Permissions.getLevel(getNick(event)) >= 2) {
            if (getLinkPurge().equals("on")) {
                Defaults.linkPurge = false;
                MessageSending.sendWhisper(event.getUser().getNick(), "Purging of links has been toggled off.");
                Bot.extra.setProperty("linkToggle", "false");
            } else {
                Defaults.linkPurge = true;
                MessageSending.sendWhisper(event.getUser().getNick(), "Purging of links has been toggled on.");
                Bot.extra.setProperty("linkToggle", "true");
            }
        }
    }

    public static void SteamGame(MessageEvent<PircBotX> event) throws Exception {
        JSONObject json = new JSONObject(JSONParser.readUrl("https://api.twitch.tv/kraken/channels/" + Bot.config.getProperty("autoJoinChannel")));
        if (json != null) {
            String game = json.get("game").toString();
            if (Bot.steamList.containsKey(game)) {
                Integer appid = Bot.steamList.get(game);
                String url = "http://store.steampowered.com/app/" + appid + "/";
                if (Defaults.whisperToggle) {
                    MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "I'm currently playing " + game + ". You can find more info about it right here : " + url);
                } else {
                    MessageSending.sendNormalMessage("I'm currently playing " + game + ". You can find more info about it right here : " + url);
                }
            } else {
                if (Defaults.whisperToggle) {
                    MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "I'm currently playing " + game + ".");
                } else {
                    MessageSending.sendNormalMessage("I'm currently playing " + game + ".");
                }
            }
        }
    }

    public static void Uptime(MessageEvent<PircBotX> event) throws Exception {
        //String uptime = JSONParser.readUrl("https://nightdev.com/hosted/uptime.php?channel=" + Bot.config.getProperty("autoJoinChannel"));
        JSONObject json = new JSONObject(JSONParser.readUrl("https://api.twitch.tv/kraken/streams?channel=" + Bot.config.getProperty("autoJoinChannel")));
        //JSONObject json = new JSONObject(JSONParser.readUrl("https://api.twitch.tv/kraken/streams?channel=MissCoookiez"));
        JSONArray streams = json.getJSONArray("streams");
        String uptime = "";
        try {
            uptime = streams.getJSONObject(0).getString("created_at");
        } catch (Exception e) {
            uptime = null;
        }
        if (uptime != null) {
            long ts = System.currentTimeMillis();
            Date localTime = new Date(ts);
            String format = "yyyy/MM/dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date gmtTime = new Date(sdf.format(localTime));
            System.out.println("Twitch " + uptime);
            String uptimeUTC = gmtTime.getYear() + 1900 + " " + gmtTime.getMonth() + 1 + " " + gmtTime.getDate() + " " + gmtTime.getHours() + " " + gmtTime.getMinutes() + " " + gmtTime.getSeconds();
            System.out.println("UTC " + uptimeUTC);

            uptime = uptime.replace("-", " ").replace("T", " ").replace(":", " ").replace("Z", "");
            String twitch[] = uptime.split(" ");
            String year = twitch[0];
            String month = twitch[1];
            String day = twitch[2];
            String hour = twitch[3];
            String minute = twitch[4];
            String second = twitch[5];
            String local[] = uptimeUTC.split(" ");
            String yearL = local[0];
            String monthL = local[1];
            String dayL = local[2];
            String hourL = local[3];
            String minuteL = local[4];
            String secondL = local[5];

            String twitchDate = month + "/" + day + "/" + year + " " + hour + ":" + minute + ":" + second;
            String localDate = monthL + "/" + dayL + "/" + yearL + " " + hourL + ":" + minuteL + ":" + secondL;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            Date d1 = null;
            Date d2 = null;

            try {
                d1 = simpleDateFormat.parse(twitchDate);
                d2 = simpleDateFormat.parse(localDate);

                long diff = d2.getTime() - d1.getTime();

                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffMinutes = diff / (60 * 1000) % 60;

                if (!Defaults.whisperToggle) {
                    if (uptime.equals("") || uptime == null) {
                        MessageSending.sendNormalMessage(Bot.config.getProperty("autoJoinChannel") + " is currently offline.", event);
                    } else {
                        if (diffHours > 0) {
                            MessageSending.sendNormalMessage(Bot.config.getProperty("autoJoinChannel") + " is currently live for " + diffHours + " hours and " + diffMinutes + " minutes.", event);
                        } else {
                            MessageSending.sendNormalMessage(Bot.config.getProperty("autoJoinChannel") + " is currently live for " + diffMinutes + " minutes.", event);
                        }
                    }

                } else {
                    if (uptime.equals("") || uptime == null) {
                        MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), Bot.config.getProperty("autoJoinChannel") + " is currently offline.");
                    } else {
                        if (diffHours > 0) {
                            MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), Bot.config.getProperty("autoJoinChannel") + " is currently live for " + diffHours + " hours and " + diffMinutes + " minutes.");
                        } else {
                            MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), Bot.config.getProperty("autoJoinChannel") + " is currently live for " + diffMinutes + " minutes.");
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if(!Defaults.whisperToggle){
                MessageSending.sendNormalMessage(Bot.config.getProperty("autoJoinChannel") + " is currently offline.", event);
            }else{
                MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), Bot.config.getProperty("autoJoinChannel") + " is currently offline.");
            }
        }
    }

    public static void Title(MessageEvent<PircBotX> event) throws Exception {
        JSONObject json = new JSONObject(JSONParser.readUrl("https://api.twitch.tv/kraken/channels/" + Bot.config.getProperty("autoJoinChannel")));
        MessageSending.sendNormalMessage("playing " + json.get("game") + ": " + json.get("status"), event);
    }

    public static void UrbanDictionary(MessageEvent<PircBotX> event) throws Exception {
        if (Permissions.getLevel(getNick(event)) >= 2) {
            JSONObject json = new JSONObject(JSONParser.readUrl("http://api.urbandictionary.com/v0/define?term=" + arg));
            try {

                JSONArray jsonArray = json.getJSONArray("list");
                JSONObject test = jsonArray.getJSONObject(0);
                MessageSending.sendNormalMessage("Definition of " + arg + " :", event);
                MessageSending.sendNormalMessage("" + test.getString("definition").replaceAll("\n", ""), event);
            } catch (JSONException e) {
                MessageSending.sendNormalMessage("Definition not found, please try again.", event);
            }
        }
    }

    public static void Caster(MessageEvent<PircBotX> event) throws Exception {
        if (Permissions.getLevel(getNick(event)) >= 2) {
            JSONObject json = new JSONObject(JSONParser.readUrl("https://api.twitch.tv/kraken/channels/" + arg.toLowerCase()));
            if (json != null) {

                String game = json.get("game").toString();
                String Status = json.get("status").toString();

                System.out.println(Status);

                MessageSending.sendNormalMessage("Go give http://twitch.tv/" + arg + "a follow, they last played " + Status + " (Playing " + game + ")", event);
            }
        }
    }

    public static void Duskbot(MessageEvent<PircBotX> event) throws Exception {
        if (!Defaults.whisperToggle) {
            if (!Defaults.isVip) {
                MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "This is DuskBot. A Twitch bot specifically designed for YOU! You want your own copy? Get it here http://www.duskbot.nl");
            } else {
                MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "This is the VIP version of DuskBot. A Twitch bot specifically designed for YOU! You want your own copy? Get it here http://www.duskbot.nl");
            }
        } else {
            if (!Defaults.isVip) {
                MessageSending.sendNormalMessage("This is DuskBot. A Twitch bot specifically designed for YOU! You want your own copy? Get it here http://www.duskbot.nl");
            } else {
                MessageSending.sendNormalMessage("This is the VIP version of DuskBot. A Twitch bot specifically designed for YOU! You want your own copy? Get it here http://www.duskbot.nl");
            }
        }
    }

    public static void Version(MessageEvent<PircBotX> event) {
        if (!Defaults.whisperToggle) {
            if (Defaults.isVip) {
                MessageSending.sendNormalMessage("Current Version: " + Defaults.VERSION + " VIP", event);
            } else {
                MessageSending.sendNormalMessage("Current Version: " + Defaults.VERSION + " Free", event);
            }
        } else {
            if (Defaults.isVip) {
                MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "Current Version: " + Defaults.VERSION + " VIP");
            } else {
                MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "Current Version: " + Defaults.VERSION + " Free");
            }
        }
    }

    public static void Whitelist(MessageEvent<PircBotX> event) throws IOException {
        if (Permissions.getLevel(getNick(event)) >= 2) {
            addToWhitelist(arg.toLowerCase());
        }
    }

    public static void Blacklist(MessageEvent<PircBotX> event) throws IOException {
        if (Permissions.getLevel(getNick(event)) >= 2) {
            addToBlacklist(arg.toLowerCase());
        }
    }

    public static void Chatters(MessageEvent<PircBotX> event) throws Exception {
        JSONObject json = new JSONObject(JSONParser.readUrl("http://tmi.twitch.tv/group/user/" + Defaults.getStreamer() + "/chatters"));
        if (Defaults.whisperToggle) {
            MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), json.get("chatter_count") + " people are currently chatting!");
        } else {
            MessageSending.sendNormalMessage(json.get("chatter_count") + " people are currently chatting!", event);

        }

    }

    public static void Viewers(MessageEvent<PircBotX> event) {
        try {
            JSONObject json = new JSONObject(JSONParser.readUrl("https://api.twitch.tv/kraken/streams?channel=" + Defaults.getStreamer()));
            JSONArray array = json.getJSONArray("streams");
            String viewers = String.valueOf(array.getJSONObject(0).get("viewers"));
            if (Defaults.whisperToggle) {
                MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), viewers + " people are currently watching!");
            } else {
                MessageSending.sendNormalMessage(viewers + " people are currently watching!", event);
            }
        } catch (Exception e) {
            MessageSending.sendNormalMessage("This stream is currently offline.");
        }
    }


    private static void addToWhitelist(String user) throws IOException {
        user = user.replace(" ", "");
        if (Bot.blackList.contains(user)) {
            Bot.blackList.remove(user);
            MessageSending.sendNormalMessage(user + " has been removed from the blacklist.");
            Save.blackList();
        } else {
            MessageSending.sendNormalMessage(user + " is not on the blacklist.");
        }
    }

    private static void addToBlacklist(String user) throws IOException {
        user = user.replace(" ", "");
        if (!Bot.blackList.contains(user)) {
            Bot.blackList.add(user);
            MessageSending.sendNormalMessage(user + " has been added to the blacklist.");
            Save.blackList();
        } else {
            MessageSending.sendNormalMessage(user + " is already on the blacklist.");
        }
    }

    private static void hostingMode(String arg, MessageEvent<PircBotX> event) throws Exception {
        String hoster = (String) Bot.config.getProperty("autoJoinChannel");

        MessageSending.sendNormalMessage("/host " + arg);
        Bot.myBot.sendIRC().joinChannel("#" + arg);
        Bot.myBot.sendIRC().message("#" + arg, hoster + " is hosting you for " + getViewercount(hoster) + " viewers. Please enjoy your stream.");
        Bot.myBot.getUserChannelDao().getChannel("#" + arg).send().part();
    }


    private static int getViewercount(String hoster) throws Exception {
        JSONObject json = new JSONObject(JSONParser.readUrl("http://tmi.twitch.tv/group/user/" + hoster + "/chatters"));
        return (int) json.get("chatter_count");
    }

    private static String getLinkPurge() {
        if (Defaults.linkPurge) {
            return "on";
        } else {
            return "off";
        }
    }

    private static String getCapsPurge() {
        if (Defaults.capsPurge) {
            return "on";
        } else {
            return "off";
        }
    }

    private static String getWotPurge() {
        if (Defaults.wotPurge) {
            return "on";
        } else {
            return "off";
        }
    }

    public static void Debug() {
        if (Defaults.debugToggled) {
            Defaults.debugToggled = false;
            MessageSending.sendWhisper("lorddusk", "Debug toggled off");
        } else {
            Defaults.debugToggled = true;
            MessageSending.sendWhisper("lorddusk", "Debug toggled on");
        }
    }

    public static void ToggleStream(MessageEvent<PircBotX> event) {
        if (Permissions.getLevel(getNick(event)) >= 3) {
            if (!Defaults.toggleStream) {
                MessageSending.sendWhisper(event.getUser().getNick(), "Auto-giving away " + Defaults.getPointName() + " has been toggled on!");
                Defaults.totalPoints = (long) 0;
                Defaults.toggleStream = true;
            } else if (Defaults.toggleStream) {
                MessageSending.sendWhisper(event.getUser().getNick(), "Auto-giving away " + Defaults.getPointName() + " has been toggled off!");
                MessageSending.sendNormalMessage("This time I gave away " + Defaults.totalPoints + " " + Defaults.getPointName() + " in total!", event);
                Defaults.toggleStream = false;
            }
        }
    }

    public static void CustomCommandChecker(MessageEvent<PircBotX> event, String message) {
        if (Bot.commandpermList.containsKey(message)) {
            if (arg == null) {
                String user = event.getUser().getNick().toLowerCase();
                String perm = Bot.permList.get(user.toLowerCase());
                if (perm != null) {
                    if (Bot.commandpermList.get(message).equals(perm) || Permissions.getLevel(user) >= 2 || Defaults.debugToggled && user.equals("cricketnu")) {
                        MessageSending.sendNormalMessage(Bot.commandList.get(message), event);
                    } else {
                        MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "You do not have enough permission to use this command.");
                    }
                } else {
                    MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "You do not have enough permission to use this command.");
                }
            } else {
                String user = event.getUser().getNick().toLowerCase();
                String perm = Bot.permList.get(user.toLowerCase());
                if (perm != null) {
                    if (Bot.commandpermList.get(message).equals(perm) || Permissions.getLevel(user) >= 2 || Defaults.debugToggled && user.equals("cricketnu")) {
                        String command = Bot.commandList.get(message);
                        command = command.replaceAll("%+[a-z]+%", arg);
                        MessageSending.sendNormalMessage(command, event);
                        arg = "";
                    } else {
                        MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "You do not have enough permission to use this command.");
                    }
                } else {
                    MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "You do not have enough permission to use this command.");
                }
            }
        } else {
            if (arg == null) {
                MessageSending.sendNormalMessage(Bot.commandList.get(message), event);
            } else {
                String command = Bot.commandList.get(message);
                command = command.replaceAll("%+[a-z]+%", arg);
                MessageSending.sendNormalMessage(command, event);
                arg = "";
            }
        }
    }

    public static void youtubeDescription(String s, boolean songRequestBoolean) {
        String id = YoutubeParser.getVideoId(s);
        try {
            JSONObject json = new JSONObject(JSONParser.readUrl("https://www.googleapis.com/youtube/v3/videos?id=" + id + "&key=<KEYHERE>&part=snippet,contentDetails&fields=items(snippet/title,snippet/channelTitle,contentDetails/duration)"));
            JSONArray array = json.getJSONArray("items");
            JSONObject snippet = array.getJSONObject(0).getJSONObject("snippet");
            JSONObject content = array.getJSONObject(0).getJSONObject("contentDetails");
            String title = String.valueOf(snippet.get("title"));
            String channelTitle = String.valueOf(snippet.get("channelTitle"));
            String duration = String.valueOf(content.get("duration"));
            if (songRequestBoolean) {
                MessageSending.sendNormalMessage(youtubeString(title, channelTitle, duration));
            }
        } catch (Exception e) {
        }

    }

    private static String youtubeString(String title, String uploader, String duration) {
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
            return "Linked YouTube video: \"" + title + "\" by " + uploader + ". [" + minutes + ":" + seconds + "]";
        else
            return "Linked YouTube video: \"" + title + "\" by " + uploader + ". [" + hours + ":" + minutes + ":" + seconds + "]";

    }

    public static String getNick(MessageEvent<PircBotX> event) {
        return event.getUser().getNick();
    }

    public static void ShutdownBot() throws InterruptedException {
        MessageSending.sendNormalMessage("Shutting down the bot.");
        System.out.println("Shutting down.");
        Bot.mbm.stop();
        System.out.println("Shut down!");
        exit(0);
    }

    public static void changeCTT(String[] args) {

        StringBuilder sb = new StringBuilder();
        int i = 1;
        while (i <= args.length - 1) {
            if (i == 1) {
                sb.append(WordUtils.capitalize(args[i]));
            } else {
                sb.append(args[i]);
            }
            sb.append("+");
            i++;
        }

        Defaults.cttText = sb.toString();
        Bot.extra.setProperty("ctt", sb.toString());
        Save.properties();
    }

    public static void exit(final int status) {
        new Thread("App-exit") {
            @Override
            public void run() {
                System.exit(status);
            }
        }.start();
    }

    public static void NowPlaying() {
        String song;
        try {
            File file = new File("config/nowplaying.txt");
            InputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            while ((song = br.readLine()) != null) {
                MessageSending.sendNormalMessage(song);
            }
            br.close();
        } catch (IOException e) {
            MessageSending.sendNormalMessage("nowplaying.txt file not found in config folder.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
