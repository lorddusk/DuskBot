/**
 * Copyright (C) 2010-2013 Leon Blakey <lord.quackstar at gmail.com>
 * <p/>
 * This file is part of PircBotX.
 * <p/>
 * PircBotX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * PircBotX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with PircBotX. If not, see <http://www.gnu.org/licenses/>.
 */
package nl.lang2619.bot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.lang2619.bot.commands.ChannelCommands;
import nl.lang2619.bot.module.Commands;
import nl.lang2619.bot.module.Hooks;
import nl.lang2619.bot.utils.Config;
import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.json.Load;
import nl.lang2619.bot.utils.json.Save;
import nl.lang2619.bot.utils.json.Upload;
import nl.lang2619.bot.utils.jsonclasses.Schedule;
import nl.lang2619.bot.utils.jsonclasses.YoutubeVideo;
import org.pircbotx.Configuration;
import org.pircbotx.MultiBotManager;
import org.pircbotx.PircBotX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Tim on 10/12/2014.
 */
public class Bot extends Application {

    public static final Logger log = LoggerFactory.getLogger(Bot.class.getName());
    public static Yaml yaml = new Yaml();

    public static Properties extra = new Properties();
    public static Properties config = new Properties();
    public static HashMap<String, Long> userList = new HashMap<String, Long>();
    public static HashMap<String, String> rankUserList = new HashMap<String, String>();
    public static HashMap<String, Long> rankList = new HashMap<String, Long>();
    public static HashMap<Long, String> quoteList = new HashMap<Long, String>();
    public static HashMap<Long, String> noteList = new HashMap<Long, String>();
    public static HashMap<String, String> permList = new HashMap<String, String>();
    public static HashMap<String, String> commandList = new HashMap<String, String>();
    public static HashMap<String, String> commandpermList = new HashMap<String, String>();
    public static HashMap<Long, String> raffleList = new HashMap<Long, String>();
    public static ArrayList<String> blackList = new ArrayList<>();
    public static ArrayList<String> permitted = new ArrayList<>();
    public static HashMap<String, Integer> steamList = new HashMap<String, Integer>();
    public static HashMap<Long, Schedule> scheduledList = new HashMap<Long, Schedule>();
    public static HashMap<String, Long> strikeList = new HashMap<String, Long>();
    public static HashMap<YoutubeVideo, String> songRequestList = new HashMap<YoutubeVideo, String>();
    public static PircBotX myBot;
    public static MultiBotManager<PircBotX> mbm = new MultiBotManager<>();


    public static void main(String[] args) throws Exception {
        setupProperties();
        setupConfigs();

        Application.launch(args);
    }

    private static void setupProperties() {
        File file = new File("config/extraConfigs.properties");
        try {
            if (file.exists()) {
                FileInputStream fileIn = new FileInputStream(file);
                extra.load(fileIn);
                fileIn.close();
            } else {
                FileOutputStream fileOut = new FileOutputStream(file);
                extra.setProperty("linkToggle", "true");
                extra.setProperty("capsToggle", "true");
                extra.setProperty("wotToggle", "true");
                extra.setProperty("whisperToggle", "false");
                extra.setProperty("scheduleToggle", "false");
                extra.setProperty("ctt", "");
                extra.store(fileOut, "Extra Configs");
                fileOut.close();
                setupProperties();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bots() {

        Configuration server = new Configuration.Builder()
                .setEncoding(Charset.forName("UTF8"))
                .setName(Defaults.getBotName())
                .setAutoNickChange(true)
                .setServerHostname(Defaults.getServer())
                .setServerPassword(Defaults.getOAuth())
                .setServerPort(Defaults.getPort())
                .addAutoJoinChannel("#" + Defaults.getStreamer())
                .setMessageDelay(1875)
                .addListener(new Hooks())
                .addListener(new Commands())
                .addListener(new ChannelCommands())
                .buildConfiguration();

        try {
            mbm.addBot(new PircBotX(server));
            mbm.addBot(new PircBotX(Whisper.whisper));
            mbm.start();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    private static void setupConfigs() throws Exception {
        File file = new File("config/serverConfig.properties");
        try {
            if (file.exists()) {
                FileInputStream fileIn = new FileInputStream(file);
                config.load(fileIn);
                fileIn.close();
            } else {
                FileOutputStream fileOut = new FileOutputStream(file);
                config.setProperty("points", "IFVIPPUTPOINTNAMEHERE");
                config.setProperty("nick", "BOTNAMEHERE");
                config.setProperty("oauth", "OAUTHHERE");
                config.setProperty("autoJoinChannel", "YOURSTREAMCHANNELNAMEHERE");
                config.setProperty("chatServer", "true");
                config.store(fileOut, "Server Configs");
                fileOut.close();
                System.out.print("Please fill in the serverConfig.properties before pressing enter.");
                System.in.read();
                System.out.println("Reading serverConfig.properties.");
                setupConfigs();
                setupProperties();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadAllTheThings();
        Save.dataList();
    }

    public static void saveAllTheThings() throws IOException {
        Save.properties();
        Save.userList();
        Save.quoteList();
        Save.rankList();
        Save.ranksList();
        Save.permsList();
        Save.commandList();
        Save.commandPermList();
        Save.raffleList();
        Save.dataList();
        Save.subMessages();
        Save.noteList();
        Save.blackList();
        Save.scheduleList();
        Save.songRequestList();
        Upload.uploadFiles();
    }

    private static void loadAllTheThings() throws IOException {
        Load.ranksList();
        Load.rankList();
        Load.quoteList();
        Load.userList();
        Load.permList();
        Load.commandList();
        Load.commandPermList();
        Load.raffleList();
        Load.noteList();
        Load.blackList();
        Load.steamList();
        Load.scheduleList();
        Load.subMessages();
        Load.songRequestList();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Bot.class.getResource("/fxml/main.fxml"));

        AnchorPane pane = loader.load();

        MainController controller = loader.getController();

        controller.setPrevStage(primaryStage);

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);

        primaryStage.show();

        Config.checkVIPStatus();

        bots();

        primaryStage.setTitle(Defaults.getBotName());

    }
}
