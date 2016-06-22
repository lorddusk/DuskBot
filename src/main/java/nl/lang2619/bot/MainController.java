package nl.lang2619.bot;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import nl.lang2619.bot.utils.ConsoleOutput;
import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.MessageSending;
import nl.lang2619.bot.utils.json.Save;
import nl.lang2619.bot.utils.Dialog;

import java.io.*;
import java.util.*;

import static javafx.collections.FXCollections.*;

/**
 * Created by Tim on 8/21/2015.
 */
public class MainController {

    public static MainController instance;

    //Config
    public TabPane tabPane;
    public Button btnProperties;
    public TextField botText;
    public TextField oauthText;
    public TextField streamerText;
    public TextField pointText;
    public TextField cttText;
    public Tooltip toolLink = new Tooltip();
    public CheckBox linkToggle;
    public Tooltip toolWot = new Tooltip();
    public CheckBox wotToggle;
    public Tooltip toolCaps = new Tooltip();
    public CheckBox capsToggle;
    public Tooltip toolSchedule = new Tooltip();
    public CheckBox scheduleToggle;
    public Tooltip toolWhisper = new Tooltip();
    public CheckBox whisperToggle;
    public Button btnExtras;
    public TextField newSubText;
    public TextField oldSubText;
    public Button btnUpdateSub;
    public Button toggleBtn;
    public CheckBox chatToggle;
    public Tooltip toolChat = new Tooltip();

    //Commands
    @FXML
    public ListView<String> commandList;
    public TextField commandText;
    public TextField responseText;
    public Button saveEditBtn;
    public ChoiceBox permBox;
    public Button deleteCommandBtn;
    public Button editCommandBtn;
    public Button addCommandBtn;
    public Button clearCommandBtn;

    //Quotes
    @FXML
    public ListView<String> qouteList;
    public TextField quoteText;
    public TextField quoteResponseText;
    public Button deleteQuoteBtn;
    public Button addQuoteBtn;
    public Button editQuoteBtn;
    public Button clearQuoteBtn;

    //Permissions
    @FXML
    public ListView<String> permissionList;
    public TextField userText;
    public ChoiceBox permsBox;
    public Button editPermBtn;
    public Button addPermBtn;
    public Button clearPermBtn;

    //Ranks
    @FXML
    public ListView<String> rankList;
    public TextField rankText;
    public TextField costText;
    public Button deleteRankBtn;
    public Button editRankBtn;
    public Button addRankBtn;
    public Button clearRankBtn;

    //Points
    @FXML
    public ListView<String> pointList;
    public TextField userPointsText;
    public TextField pointsText;
    public Button deletePointsBtn;
    public Button editPointsBtn;
    public Button clearPointsBtn;
    public TextField searchText;
    public Button searchBtn;

    //User Ranks
    @FXML
    public ListView<String> userRankList;
    public TextField userRankText;
    public TextField currentRankText;
    public Button editUserRankBtn;
    public Button deleteUserRankBtn;
    public Button clearUserRankBtn;
    public ChoiceBox rankBox;
    public MenuItem helpVersion;

    Stage prevStage;

    ObservableList<String> items = observableArrayList();

    @FXML
    private TextArea area;
    public CheckMenuItem debugCheck;
    public Tab DebugTab;

    @FXML
    void initialize() throws IOException {
        instance = this;
        fillInStuff();
        //ConsoleOutput co = new ConsoleOutput();
        //co.startReadingOutput(area);
        area.setEditable(false);
        helpVersion.setText("Version : " + Defaults.VERSION);
        tooltips();
        linkToggle.setTooltip(toolLink);
        capsToggle.setTooltip(toolCaps);
        scheduleToggle.setTooltip(toolSchedule);
        wotToggle.setTooltip(toolWot);
        whisperToggle.setTooltip(toolWhisper);
        chatToggle.setTooltip(toolChat);
    }

    private void tooltips() {
        toolLink.setText("Toggles between purging of links.");
        toolCaps.setText("Toggles between purging of too much caps.");
        toolSchedule.setText("Toggles between showing scheduled messages.");
        toolWot.setText("Toggles between purging of walls of text.");
        toolWhisper.setText("Toggles between whispering certain commands or just put them in chat.");
        toolChat.setText("True for 'irc.chat.twitch.tv' or False for 'irc.twitch.tv'");
    }

    private void fillInStuff() {
        configFiller();
        commandFiller();
        quoteFiller();
        permissionFiller();
        rankFiller();
        pointsFiller();
        userRankFiller();
        commandList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String old, String newValue) {
                commandText.setText(newValue);
                responseText.setText(Bot.commandList.get(newValue));
                String permission = Bot.commandpermList.get(newValue);
                permission = getPerm(permission);
                permBox.setValue(permission);
            }

            private String getPerm(String s) {
                if (s == null) {
                    return "None";
                }
                switch (s) {
                    case "mod":
                        return "Moderator";
                    case "reg":
                        return "Regular";
                    default:
                        return "None";
                }
            }
        });
        qouteList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                quoteText.setText(newValue);
                quoteResponseText.setText(Bot.quoteList.get(Long.parseLong(newValue)));
            }
        });
        permissionList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                userText.setText(newValue);
                String permission = Bot.permList.get(newValue);
                permission = getPerm(permission);
                permsBox.setValue(permission);
            }

            private String getPerm(String s) {
                if (s == null) {
                    return "None";
                }
                switch (s) {
                    case "mod":
                        return "Moderator";
                    case "reg":
                        return "Regular";
                    case "smod":
                        return "Super Moderator";
                    default:
                        return "None";
                }
            }
        });
        rankList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                rankText.setText(newValue);
                costText.setText(String.valueOf(Bot.rankList.get(newValue)));
            }
        });
        pointList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                userPointsText.setText(newValue);
                pointsText.setText(String.valueOf(Bot.userList.get(newValue)));
            }
        });
        userRankList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                userRankText.setText(newValue);
                currentRankText.setText(Bot.rankUserList.get(newValue));
                if (Bot.rankList.containsKey(currentRankText.getText())) {
                    rankBox.setValue(Bot.rankUserList.get(newValue));
                } else {
                    rankBox.setValue("None");
                }
            }
        });
        permBox.getItems().addAll("None", "Regular", "Moderator");
        permBox.setValue("None");
        permsBox.getItems().addAll("None", "Regular", "Moderator", "Super Moderator");
        permsBox.setValue("None");
        rankBox.getItems().addAll("None", items);
        rankBox.setValue("None");
    }

    public void commandFiller() {
        fillCommands(commandList);
    }

    public void quoteFiller() {
        fillQuotes(qouteList);
    }

    public void pointsFiller() {
        fillPoints(pointList);
    }

    private static void fillPoints(ListView<String> pointList) {
        ObservableList<String> items = observableArrayList();
        Set<Map.Entry<String, Long>> mapSet = Bot.userList.entrySet();
        for (Map.Entry<String, Long> mapEntry : mapSet) {
            items.add(mapEntry.getKey());
        }
        pointList.setItems(items);
    }

    private static void fillQuotes(ListView<String> qouteList) {
        ObservableList<String> items = observableArrayList();
        Set<Map.Entry<Long, String>> mapSet = Bot.quoteList.entrySet();
        for (Map.Entry<Long, String> mapEntry : mapSet) {
            items.add(mapEntry.getKey().toString());
        }
        qouteList.setItems(items);
    }

    public void permissionFiller() {
        fillPermissions(permissionList);
    }

    private static void fillPermissions(ListView<String> permissionList) {
        ObservableList<String> items = observableArrayList();
        Set<Map.Entry<String, String>> mapSet = Bot.permList.entrySet();
        for (Map.Entry<String, String> mapEntry : mapSet) {
            items.add(mapEntry.getKey().toString());
        }
        permissionList.setItems(items);
    }

    public void userRankFiller() {
        fillUserRanks(userRankList);
    }

    private void fillUserRanks(ListView<String> userRankList) {
        ObservableList<String> items = observableArrayList();
        Set<Map.Entry<String, String>> mapSet = Bot.rankUserList.entrySet();
        for (Map.Entry<String, String> mapEntry : mapSet) {
            items.add(mapEntry.getKey());
        }
        userRankList.setItems(items);
    }

    public void rankFiller() {
        fillRanks(rankList);
    }

    private void fillRanks(ListView<String> rankList) {
        Set<Map.Entry<String, Long>> mapSet = Bot.rankList.entrySet();
        for (Map.Entry<String, Long> mapEntry : mapSet) {
            items.add(mapEntry.getKey());
        }
        rankList.setItems(items);
    }

    public static void fillCommands(ListView<String> commandList) {
        ObservableList<String> items = observableArrayList();
        Set<Map.Entry<String, String>> mapSet = Bot.commandList.entrySet();
        for (Map.Entry<String, String> mapEntry : mapSet) {
            items.add(mapEntry.getKey());
        }
        commandList.setItems(items);
    }

    private void configFiller() {
        pointText.setText(Bot.config.getProperty("points"));
        botText.setText(Bot.config.getProperty("nick"));
        oauthText.setText(Bot.config.getProperty("oauth"));
        streamerText.setText(Bot.config.getProperty("autoJoinChannel"));
        chatToggle.setSelected(Boolean.parseBoolean(Bot.config.getProperty("chatServer")));

        linkToggle.setSelected((Bot.extra.getProperty("linkToggle").equals("true")));
        capsToggle.setSelected((Bot.extra.getProperty("capsToggle").equals("true")));
        wotToggle.setSelected((Bot.extra.getProperty("wotToggle").equals("true")));
        whisperToggle.setSelected((Bot.extra.getProperty("whisperToggle").equals("true")));
        scheduleToggle.setSelected((Bot.extra.getProperty("scheduleToggle").equals("true")));
        cttText.setText(Bot.extra.getProperty("ctt"));

        newSubText.setText(Defaults.newSub);
        oldSubText.setText(Defaults.oldSub);
    }

    public void setPrevStage(Stage stage) {
        this.prevStage = stage;
    }

    public void debugTab(ActionEvent actionEvent) {
        if (debugCheck.isSelected()) {
            DebugTab.setDisable(false);
        } else {
            DebugTab.setDisable(true);
        }
    }

    public void changeProperties(ActionEvent actionEvent) {
        File file = new File("config/serverConfig.properties");
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            Bot.config.setProperty("points", pointText.getText());
            Bot.config.setProperty("nick", botText.getText());
            Bot.config.setProperty("oauth", oauthText.getText());
            Bot.config.setProperty("autoJoinChannel", streamerText.getText());
            Bot.config.setProperty("chatServer", chatToggle.isSelected() ? "true" : "false");
            Bot.config.store(fileOut, "Server Configs");
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void extraProperties() {
        File file = new File("config/extraConfigs.properties");
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            Bot.extra.setProperty("linkToggle", linkToggle.isSelected() ? "true" : "false");
            Bot.extra.setProperty("capsToggle", capsToggle.isSelected() ? "true" : "false");
            Bot.extra.setProperty("wotToggle", wotToggle.isSelected() ? "true" : "false");
            Bot.extra.setProperty("whisperToggle", whisperToggle.isSelected() ? "true" : "false");
            Bot.extra.setProperty("scheduleToggle", scheduleToggle.isSelected() ? "true" : "false");
            Bot.extra.setProperty("ctt", cttText.getText());
            Bot.extra.store(fileOut, "Extra Configs");
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSub(ActionEvent actionEvent) throws IOException {
        Defaults.oldSub = oldSubText.getText();
        Defaults.newSub = newSubText.getText();
        Save.subMessages();
    }

    public void toggle(ActionEvent actionEvent) {
        if (Defaults.toggleStream) {
            toggleBtn.setTextFill(Color.web("red"));
            toggleBtn.setText("Stream is toggled OFF");
            MessageSending.sendWhisper(Defaults.getStreamer(), "Auto-giving away " + Defaults.getPointName() + " has been toggled off!");
            MessageSending.sendNormalMessage("This time I gave away " + Defaults.totalPoints + " " + Defaults.getPointName() + " in total!");
            Defaults.toggleStream = false;
        } else {
            toggleBtn.setTextFill(Color.web("green"));
            toggleBtn.setText("Stream is toggled ON");
            MessageSending.sendWhisper(Defaults.getStreamer(), "Auto-giving away " + Defaults.getPointName() + " has been toggled on!");
            Defaults.totalPoints = (long) 0;
            Defaults.toggleStream = true;
        }
    }

    public void exitProgram(ActionEvent actionEvent) {
        Dialog.showDialog(prevStage, "Shutting down", "Shutting down program. Please wait...");
        System.exit(1);
    }

    public static MainController getInstance() {
        return instance;
    }

    public void deleteCommand(ActionEvent actionEvent) throws IOException {
        String command = commandText.getText();
        if (Bot.commandList.containsKey(command)) {
            Bot.commandList.remove(command);
            if (Bot.commandpermList.containsKey(command)) {
                Bot.commandpermList.remove(command);
            }
            Bot.log.info(command + " removed");
            commandFiller();
            Bot.saveAllTheThings();
            Dialog.showDialog(prevStage, "Success", "Command was successfully deleted!");
        } else {
            Dialog.showDialog(prevStage, "Problem", "Command " + command + "doesn't exists.");
        }
    }

    public void editCommand(ActionEvent actionEvent) throws IOException {
        String command = commandText.getText();
        String response = responseText.getText();
        String permission = permBox.getValue().toString();
        if (Bot.commandList.containsKey(command)) {
            Bot.commandList.remove(command);
            if (Bot.commandpermList.containsKey(command)) {
                Bot.commandpermList.remove(command);
            }
            if (!command.equals("") || !response.equals("")) {
                if (!Bot.commandList.containsKey(command)) {
                    Bot.commandList.put(command, response);
                    Bot.log.info(command.toString() + " added");
                    if (!permission.equals("") || !permission.equals("None")) {
                        Bot.commandpermList.put(command, permission);
                    }
                    commandFiller();
                    Bot.saveAllTheThings();
                    Dialog.showDialog(prevStage, "Success", "Command was successfully edited!");
                }
            } else {
                Dialog.showDialog(prevStage, "Problem", "Command and response text can't be empty.");
            }
        } else {
            Dialog.showDialog(prevStage, "Problem", "Command " + command + "doesn't exists.");
        }
    }

    public void addCommand(ActionEvent actionEvent) throws IOException {
        String command = commandText.getText();
        String response = responseText.getText();
        String permission = permBox.getValue().toString();
        if (!command.equals("") || !response.equals("")) {
            if (!Bot.commandList.containsKey(command)) {
                Bot.commandList.put(command, response);
                Bot.log.info(command.toString() + " added");
                if (!permission.equals("") || !permission.equals("None")) {
                    Bot.commandpermList.put(command, permission);
                }
                commandFiller();
                Bot.saveAllTheThings();
                Dialog.showDialog(prevStage, "Success", "Command was successfully added!");
            } else {
                Dialog.showDialog(prevStage, "Problem", "Command " + command + "already exists.");
            }
        } else {
            Dialog.showDialog(prevStage, "Problem", "Command and response text can't be empty.");
        }
    }

    public void clearCommand(ActionEvent actionEvent) {
        commandText.clear();
        responseText.clear();
        permBox.setValue("None");
        commandList.getSelectionModel().clearSelection();
    }

    public void deleteQoute(ActionEvent actionEvent) throws IOException {
        if (Bot.quoteList.containsKey(Long.parseLong(quoteText.getText()))) {
            Bot.quoteList.remove(Long.parseLong(quoteText.getText()));
            Dialog.showDialog(prevStage, "Removed", "Quote " + quoteText.getText() + " has been removed.");
            quoteResponseText.clear();
            quoteText.clear();
            qouteList.getSelectionModel().clearSelection();
            quoteFiller();
            Bot.saveAllTheThings();
        } else {
            Dialog.showDialog(prevStage, "Problem", "Quote " + quoteText.getText() + " doesn't exist.");
        }
    }

    public void editQoute(ActionEvent actionEvent) throws IOException {
        Bot.quoteList.remove(Long.parseLong(quoteText.getText()));
        Bot.quoteList.put(Long.parseLong(quoteText.getText()), quoteResponseText.getText());
        Dialog.showDialog(prevStage, "Edited", "Quote " + quoteText.getText() + " has been edited.");
        quoteFiller();
        Bot.saveAllTheThings();
    }

    public void addQoute(ActionEvent actionEvent) throws IOException {
        if (!Bot.quoteList.containsKey(Long.parseLong(quoteText.getText()))) {
            Bot.quoteList.put(Long.parseLong(quoteText.getText()), quoteResponseText.getText());
            Dialog.showDialog(prevStage, "Added", "Quote " + quoteText.getText() + " has been added.");
            quoteFiller();
            Bot.saveAllTheThings();
        } else {
            Dialog.showDialog(prevStage, "Problem", "Quote " + quoteText.getText() + " already exists.");
        }
    }

    public void clearQoute(ActionEvent actionEvent) {
        quoteResponseText.clear();
        quoteText.clear();
        qouteList.getSelectionModel().clearSelection();
    }

    public void editPerm(ActionEvent actionEvent) throws IOException {
        String permission = getPermission(permsBox.getValue().toString());
        if (permission.equals("")) {
            if (Bot.permList.containsKey(userText.getText())) {
                Bot.permList.remove(userText.getText());
                permissionFiller();
                Bot.saveAllTheThings();
            } else {
                Dialog.showDialog(prevStage, "Problem", "User " + userText.getText() + " doesn't have any permissions.");
            }
        } else {
            if (Bot.permList.containsKey(userText.getText())) {
                Bot.permList.remove(userText.getText());
                Bot.permList.put(userText.getText(), permission);
                Dialog.showDialog(prevStage, "Success", "User was given " + permsBox.getValue().toString() + " permission.");
                permissionFiller();
                Bot.saveAllTheThings();
            }
        }
    }

    public void addPerm(ActionEvent actionEvent) throws IOException {
        String permission = getPermission(permsBox.getValue().toString());
        if (!Bot.permList.containsKey(userText.getText())) {
            Bot.permList.remove(userText.getText());
            Bot.permList.put(userText.getText(), permission);
            Dialog.showDialog(prevStage, "Success", "User was given " + permsBox.getValue().toString() + " permission.");
            permissionFiller();
            Bot.saveAllTheThings();
        } else {
            Bot.permList.remove(userText.getText());
            Bot.permList.put(userText.getText(), permission);
            Dialog.showDialog(prevStage, "Success", "User already had an permission but was given " + permsBox.getValue().toString() + " permission.");
            permissionFiller();
            Bot.saveAllTheThings();
        }
    }

    public String getPermission(String asked) {
        switch (asked) {
            case "None":
                return "";
            case "Regular":
                return "reg";
            case "Moderator":
                return "mod";
            case "Super Moderator":
                return "smod";
            default:
                return "";
        }
    }

    public void clearPerm(ActionEvent actionEvent) {
        permissionList.getSelectionModel().clearSelection();
        permsBox.setValue("None");
        userText.clear();
    }

    public void deleteRank(ActionEvent actionEvent) throws IOException {
        if (Bot.rankList.containsKey(rankText.getText())) {
            Bot.rankList.remove(rankText.getText());
            Dialog.showDialog(prevStage, "Deleted", "Rank " + rankText.getText() + " has been removed.");
            rankFiller();
            Bot.saveAllTheThings();
        } else {
            Dialog.showDialog(prevStage, "Problem", "Rank " + rankText.getText() + " doesn't exist.");
        }
    }

    public void editRank(ActionEvent actionEvent) throws IOException {
        if (Bot.rankList.containsKey(rankText.getText())) {
            Bot.rankList.remove(rankText.getText());
            Bot.rankList.put(rankText.getText(), Long.valueOf(costText.getText()));
            Dialog.showDialog(prevStage, "Edited", "Rank " + rankText.getText() + " has been edited.");
            rankFiller();
            Bot.saveAllTheThings();
        } else {
            Dialog.showDialog(prevStage, "Problem", "Rank " + rankText.getText() + " doesn't exist.");
        }
    }

    public void addRank(ActionEvent actionEvent) throws IOException {
        if (!Bot.rankList.containsKey(rankText.getText())) {
            Bot.rankList.put(rankText.getText(), Long.valueOf(costText.getText()));
            Dialog.showDialog(prevStage, "Added", "Rank " + rankText.getText() + " has been added.");
            rankFiller();
            Bot.saveAllTheThings();
            rankBox.getItems().clear();
            rankBox.getItems().addAll("None", items);
        } else {
            Dialog.showDialog(prevStage, "Problem", "Rank " + rankText.getText() + " already exists.");
        }
    }

    public void clearRank(ActionEvent actionEvent) {
        rankList.getSelectionModel().clearSelection();
        rankText.clear();
        costText.clear();
    }

    public void editPoints(ActionEvent actionEvent) throws IOException {
        if (Bot.userList.containsKey(userPointsText.getText())) {
            Bot.userList.put(userPointsText.getText(), Long.valueOf(pointText.getText()));
            Dialog.showDialog(prevStage, "Edited", "User " + userPointsText.getText() + " got his points edited.");
            pointsFiller();
            Bot.saveAllTheThings();
        } else {
            Bot.userList.put(userPointsText.getText(), Long.valueOf(pointText.getText()));
            Dialog.showDialog(prevStage, "Added", "User " + userPointsText.getText() + " got added and given points.");
            pointsFiller();
            Bot.saveAllTheThings();
        }
    }

    public void deletePoints(ActionEvent actionEvent) throws IOException {
        if (Bot.userList.containsKey(userPointsText.getText())) {
            Bot.userList.remove(userPointsText.getText());
            Bot.blackList.add(userPointsText.getText());
            Dialog.showDialog(prevStage, "Deleted", "User " + userPointsText.getText() + " got all his points revoked and put on the blacklist.");
            pointsFiller();
            Bot.saveAllTheThings();
        } else {
            Dialog.showDialog(prevStage, "Problem", "User " + userPointsText.getText() + " doesn't have any points.");
        }
    }

    public void clearPoints(ActionEvent actionEvent) {
        searchText.clear();
        userPointsText.clear();
        pointsText.clear();
    }

    public void searchUser(ActionEvent actionEvent) {
        searchText.setText(searchText.getText().toLowerCase());
        if (Bot.userList.containsKey(searchText.getText())) {
            pointsText.setText(String.valueOf(Bot.userList.get(searchText.getText())));
            userPointsText.setText(searchText.getText());
            searchText.clear();
        } else {
            Dialog.showDialog(prevStage, "Problem", "User " + searchText.getText() + " doesn't have any points.");
        }
    }

    public void editUserRank(ActionEvent actionEvent) throws IOException {
        if (Bot.rankUserList.containsKey(userRankText.getText())) {
            Bot.rankUserList.remove(userRankText.getText());
            if (!currentRankText.getText().equals("")) {
                Bot.rankUserList.put(userRankText.getText(), currentRankText.getText());
                Dialog.showDialog(prevStage, "Edited", "User " + userRankText.getText() + " has been given " + currentRankText.getText() + ".");
            } else {
                Bot.rankUserList.put(userRankText.getText(), rankBox.getValue().toString());
                Dialog.showDialog(prevStage, "Edited", "User " + userRankText.getText() + " has been given " + rankBox.getValue().toString() + ".");
            }
            Bot.saveAllTheThings();
            userRankFiller();
        } else {
            if (!currentRankText.getText().equals("")) {
                Bot.rankUserList.put(userRankText.getText(), currentRankText.getText());
                Dialog.showDialog(prevStage, "Edited", "User " + userRankText.getText() + " has been given " + currentRankText.getText() + ".");
            } else {
                Bot.rankUserList.put(userRankText.getText(), rankBox.getValue().toString());
                Dialog.showDialog(prevStage, "Edited", "User " + userRankText.getText() + " has been given " + rankBox.getValue().toString() + ".");
            }
            Bot.saveAllTheThings();
            userRankFiller();
        }
    }

    public void deleteUserRank(ActionEvent actionEvent) throws IOException {
        if (Bot.rankUserList.containsKey(userRankText.getText())) {
            Bot.rankUserList.remove(userRankText.getText());
            Dialog.showDialog(prevStage, "Deleted", "User " + userRankText.getText() + " has their rank revoked.");
            Bot.saveAllTheThings();
            userRankFiller();
        } else {
            Dialog.showDialog(prevStage, "Problem", "User " + userRankText.getText() + " has no rank to be revoked.");
        }
    }

    public void clearUserRank(ActionEvent actionEvent) {
        userRankText.clear();
        currentRankText.clear();
        rankBox.setValue("None");
    }
}
