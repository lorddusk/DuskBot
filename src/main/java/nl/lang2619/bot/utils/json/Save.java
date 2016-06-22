package nl.lang2619.bot.utils.json;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.jsonclasses.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.rmi.server.ExportException;
import java.util.Map;
import java.util.Set;

/**
 * Created by Tim on 11/20/2014.
 */
public class Save {

    public static void rankList() throws IOException {
        JSONObject object = new JSONObject();
        JSONArray list = new JSONArray();
        Set<Map.Entry<String, Long>> mapSet = Bot.rankList.entrySet();
        for (Map.Entry<String, Long> mapEntry : mapSet) {
            String keyValue = mapEntry.getKey();
            Long value = mapEntry.getValue();
            list.add(new Ranks(keyValue, value));
        }

        object.put("Ranks", list);

        StringWriter out = new StringWriter();

        object.writeJSONString(out);
        try {
            File file = new File("config/rankList.json");
            if (file.delete()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            Bot.log.error(e.getMessage());
        }

        PrintWriter printWriter = new PrintWriter("config/rankList.json", "UTF-8");

        try {
            printWriter.write(out.toString());
        } finally {
            printWriter.flush();
            printWriter.close();
        }
    }

    public static void scheduleList() throws IOException {
        JSONObject object = new JSONObject();
        JSONArray list = new JSONArray();
        Set<Map.Entry<Long, Schedule>> mapSet = Bot.scheduledList.entrySet();
        for (Map.Entry<Long, Schedule> mapEntry : mapSet) {
            Schedule value = mapEntry.getValue();
            list.add(new Schedule(value.getNumber(), value.getMessage(), value.getToggle()));
        }

        object.put("Schedule", list);

        StringWriter out = new StringWriter();

        object.writeJSONString(out);
        try {
            File file = new File("config/scheduleList.json");
            if (file.delete()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            Bot.log.error(e.getMessage());
        }

        PrintWriter printWriter = new PrintWriter("config/scheduleList.json", "UTF-8");

        try {
            printWriter.write(out.toString());
        } finally {
            printWriter.flush();
            printWriter.close();
        }
    }

    public static void songRequestList() throws IOException {
        JSONObject object = new JSONObject();
        JSONArray list = new JSONArray();
        Set<Map.Entry<YoutubeVideo, String>> mapSet = Bot.songRequestList.entrySet();
        for (Map.Entry<YoutubeVideo, String> mapEntry : mapSet) {
            YoutubeVideo keyValue = mapEntry.getKey();
            String value = mapEntry.getValue();
            list.add(new SongRequest(value, keyValue.getNumber(), keyValue.getLink(), keyValue.getTitle(), keyValue.getUploader(), keyValue.getDuration()));
        }

        object.put("Request", list);
        StringWriter out = new StringWriter();

        object.writeJSONString(out);
        try {
            File file = new File("config/songRequestList.json");
            if (file.delete()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            Bot.log.error(e.getMessage());
        }

        PrintWriter printWriter = new PrintWriter("config/songRequestList.json", "UTF-8");

        try {
            printWriter.write(out.toString());
        } finally {
            printWriter.flush();
            printWriter.close();
        }
    }

    public static void quoteList() throws IOException {
        JSONObject object = new JSONObject();
        JSONArray list = new JSONArray();
        Set<Map.Entry<Long, String>> mapSet = Bot.quoteList.entrySet();
        for (Map.Entry<Long, String> mapEntry : mapSet) {
            Long keyValue = mapEntry.getKey();
            String value = mapEntry.getValue();
            list.add(new Quote(keyValue, value));
        }

        object.put("Quotes", list);

        StringWriter out = new StringWriter();

        object.writeJSONString(out);
        try {
            File file = new File("config/quotes.json");
            if (file.delete()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            Bot.log.error(e.getMessage());
        }

        PrintWriter printWriter = new PrintWriter("config/quotes.json", "UTF-8");

        try {
            printWriter.write(out.toString());
        } finally {
            printWriter.flush();
            printWriter.close();

        }
    }

    public static void userList() throws IOException {
        JSONObject object = new JSONObject();
        JSONArray list = new JSONArray();
        Set<Map.Entry<String, Long>> mapSet = Bot.userList.entrySet();
        for (Map.Entry<String, Long> mapEntry : mapSet) {
            String keyValue = mapEntry.getKey();
            Long value = mapEntry.getValue();
            list.add(new User(keyValue, value));
        }

        object.put("Users", list);

        StringWriter out = new StringWriter();

        object.writeJSONString(out);
        try {
            File file = new File("config/points.json");
            if (file.delete()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            Bot.log.error(e.getMessage());
        }

        PrintWriter printWriter = new PrintWriter("config/points.json", "UTF-8");

        try {
            printWriter.write(out.toString());
        } finally {
            printWriter.flush();
            printWriter.close();
        }
    }

    public static void ranksList() throws IOException {
        JSONObject object = new JSONObject();
        JSONArray list = new JSONArray();
        Set<Map.Entry<String, String>> mapSet = Bot.rankUserList.entrySet();
        for (Map.Entry<String, String> mapEntry : mapSet) {
            String key = mapEntry.getKey();
            String value = mapEntry.getValue();
            list.add(new RankUser(key, value));
        }
        object.put("Ranks", list);
        StringWriter out = new StringWriter();
        object.writeJSONString(out);

        try {
            File file = new File("config/ranks.json");
            if (file.delete()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            Bot.log.error(e.getMessage());
        }

        PrintWriter printWriter = new PrintWriter("config/ranks.json", "UTF-8");

        try {
            printWriter.write(out.toString());
        } finally {
            printWriter.flush();
            printWriter.close();
        }
    }

    public static void permsList() throws IOException {
        JSONObject object = new JSONObject();
        JSONArray list = new JSONArray();
        Set<Map.Entry<String, String>> mapSet = Bot.permList.entrySet();
        for (Map.Entry<String, String> mapEntry : mapSet) {
            String key = mapEntry.getKey();
            String value = mapEntry.getValue();
            list.add(new Perm(key, value));
        }
        object.put("Permissions", list);
        StringWriter out = new StringWriter();
        object.writeJSONString(out);

        try {
            File file = new File("config/permissions.json");
            if (file.delete()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            Bot.log.error(e.getMessage());
        }

        PrintWriter printWriter = new PrintWriter("config/permissions.json", "UTF-8");

        try {
            printWriter.write(out.toString());
        } finally {
            printWriter.flush();
            printWriter.close();
        }
    }

    public static void commandList() throws IOException {
        JSONObject object = new JSONObject();
        JSONArray list = new JSONArray();
        Set<Map.Entry<String, String>> mapSet = Bot.commandList.entrySet();
        for (Map.Entry<String, String> mapEntry : mapSet) {
            String key = mapEntry.getKey();
            String value = mapEntry.getValue();
            list.add(new Command(key, value));
        }
        object.put("Commands", list);
        StringWriter out = new StringWriter();
        object.writeJSONString(out);

        try {
            File file = new File("config/commands.json");
            if (file.delete()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            Bot.log.error(e.getMessage());
        }

        PrintWriter printWriter = new PrintWriter("config/commands.json", "UTF-8");

        try {
            printWriter.write(out.toString());
        } finally {
            printWriter.flush();
            printWriter.close();

        }
    }


    public static void commandPermList() throws IOException {
        JSONObject object = new JSONObject();
        JSONArray list = new JSONArray();
        Set<Map.Entry<String, String>> mapSet = Bot.commandpermList.entrySet();
        for (Map.Entry<String, String> mapEntry : mapSet) {
            String key = mapEntry.getKey();
            String value = mapEntry.getValue();
            list.add(new CommandPerm(key, value));
        }
        object.put("Commands", list);
        StringWriter out = new StringWriter();
        object.writeJSONString(out);

        try {
            File file = new File("config/commandpermissions.json");
            if (file.delete()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            Bot.log.error(e.getMessage());
        }

        PrintWriter printWriter = new PrintWriter("config/commandpermissions.json", "UTF-8");

        try {
            printWriter.write(out.toString());
        } finally {
            printWriter.flush();
            printWriter.close();
        }
    }

    public static void dataList() throws IOException {
        JSONObject object = new JSONObject();

        object.put("Nick", Defaults.getBotName());
        object.put("Points", Defaults.getPointName());
        object.put("Raffle cost", Defaults.getRaffleCost());
        object.put("VIP", Defaults.isVip);
        object.put("Link", Defaults.linkPurge);
        object.put("WoT", Defaults.wotPurge);
        object.put("Caps", Defaults.capsPurge);
        object.put("CTT", Defaults.cttText);
        object.put("Whisper", Defaults.whisperToggle);

        StringWriter out = new StringWriter();
        object.writeJSONString(out);
        try {
            File file = new File("config/data.json");
            if (file.delete()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            Bot.log.error(e.getMessage());
        }

        PrintWriter printWriter = new PrintWriter("config/data.json", "UTF-8");

        try {
            printWriter.write(out.toString());
        } finally {
            printWriter.flush();
            printWriter.close();
        }
    }

    public static void blackList() throws IOException {
        JSONObject object = new JSONObject();
        JSONArray list = new JSONArray();

        for (int i = 0; i < Bot.blackList.size(); i++) {
            list.add(new Blacklist(Bot.blackList.get(i)));
        }
        object.put("Blacklist", list);


        StringWriter out = new StringWriter();
        object.writeJSONString(out);
        try {
            File file = new File("config/blacklist.json");
            if (file.delete()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            Bot.log.error(e.getMessage());
        }

        PrintWriter printWriter = new PrintWriter("config/blacklist.json", "UTF-8");

        try {
            printWriter.write(out.toString());
        } finally {
            printWriter.flush();
            printWriter.close();
        }
    }

    public static void noteList() throws IOException {
        JSONObject object = new JSONObject();
        JSONArray list = new JSONArray();
        Set<Map.Entry<Long, String>> mapSet = Bot.noteList.entrySet();
        for (Map.Entry<Long, String> mapEntry : mapSet) {
            Long keyValue = mapEntry.getKey();
            String value = mapEntry.getValue();
            list.add(new Note(keyValue, value));
        }

        object.put("Notes", list);

        StringWriter out = new StringWriter();

        object.writeJSONString(out);
        try {
            File file = new File("config/note.json");
            if (file.delete()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            Bot.log.error(e.getMessage());
        }

        PrintWriter printWriter = new PrintWriter("config/note.json", "UTF-8");

        try {
            printWriter.write(out.toString());
        } finally {
            printWriter.flush();
            printWriter.close();

        }
    }


    public static void raffleList() throws IOException {
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();
        Set<Map.Entry<Long, String>> mapSet = Bot.raffleList.entrySet();
        for (Map.Entry<Long, String> mapEntry : mapSet) {
            Long key = mapEntry.getKey();
            String value = mapEntry.getValue();
            array.add(new Raffle(key, value));
        }
        obj.put("Raffle", array);
        StringWriter out = new StringWriter();
        obj.writeJSONString(out);

        try {
            File file = new File("config/raffleTickets.json");
            if (file.delete()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            Bot.log.error(e.getMessage());
        }

        PrintWriter printWriter = new PrintWriter("config/raffleTickets.json", "UTF-8");

        try {
            printWriter.write(out.toString());
        } finally {
            printWriter.flush();
            printWriter.close();
        }

    }

    public static void subMessages() throws IOException {
        JSONObject object = new JSONObject();

        object.put("New", Defaults.newSub);
        object.put("Old", Defaults.oldSub);

        StringWriter out = new StringWriter();
        object.writeJSONString(out);
        try {
            File file = new File("config/subMessages.json");
            if (file.delete()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            Bot.log.error(e.getMessage());
        }

        PrintWriter printWriter = new PrintWriter("config/subMessages.json", "UTF-8");

        try {
            printWriter.write(out.toString());
        } finally {
            printWriter.flush();
            printWriter.close();
        }
    }

    public static void properties() {
        try {
            File file = new File("extraConfigs.properties");
            FileOutputStream fileOut = new FileOutputStream(file);
            Bot.extra.store(fileOut, "Extra Configs");
            fileOut.close();
        }catch (Exception e){
            System.out.println("Couldn't save the extra config properties");
        }
    }
}
