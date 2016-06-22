package nl.lang2619.bot.utils;

import nl.lang2619.bot.Bot;
import org.json.JSONArray;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

/**
 * Created by Tim on 19-3-2015.
 */
public class Config {

//    public static void CheckServerConfig() {
//        if (!Defaults.vipChecksum.exists()) {
//            if (!Defaults.serverConfig.exists()) {
//                Bot.log.info("First time set-up. Please edit config after it has been written");
//                try {
//                    FileOutputStream fos = new FileOutputStream(Defaults.serverConfig);
//                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
//                    bw.write("##Default Server Config\n");
//                    bw.write("##For what streamer is this bot?\n");
//                    bw.write("autoJoinChannel: \n");
//                    bw.newLine();
//                    bw.close();
//                    Bot.log.info("Finished writing default config");
//                    System.exit(0);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Bot.log.error(e.getMessage());
//                }
//            }
//        }
//        if (Defaults.vipChecksum.exists()) {
//            if (!Defaults.serverConfig.exists()) {
//                Bot.log.info("First time set-up. Please edit config after it has been written");
//                try {
//                    FileOutputStream fos = new FileOutputStream(Defaults.serverConfig);
//                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
//                    bw.write("##Default Server Config\n");
//                    bw.write("##For what streamer is this bot?\n");
//                    bw.write("autoJoinChannel: \n");
//                    bw.newLine();
//                    bw.write("##VIP FEATURES\n");
//                    bw.write("##What is your bot called?\n");
//                    bw.write("nick: \n");
//                    bw.write("oauth: \n");
//                    bw.newLine();
//                    bw.write("##What are your points called?\n");
//                    bw.write("points: \n");
//                    bw.newLine();
//                    bw.close();
//                    Bot.log.info("Finished writing default config");
//                    System.exit(0);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Bot.log.error(e.getMessage());
//                }
//            }
//        }
//    }

    public static void checkVIPStatus() throws Exception {
        JSONArray array = new JSONArray(JSONParser.readUrl("http://www.duskbot.nl/api/viplist.php"));
        HashMap<String, String> vipList = new HashMap<String, String>();
        for (int i = 0; i < array.length(); i++) {
            String user = array.getJSONObject(i).getString("username").toLowerCase();
            String role = array.getJSONObject(i).getString("role").toLowerCase();
            vipList.put(user, role);
        }
        Defaults.isVip = vipList.get(Defaults.getStreamer()) != null && (vipList.get(Defaults.getStreamer()).equalsIgnoreCase("vip") || vipList.get(Defaults.getStreamer()).equalsIgnoreCase("admin"));
    }
}
