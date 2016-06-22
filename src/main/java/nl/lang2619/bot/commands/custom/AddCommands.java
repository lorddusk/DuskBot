package nl.lang2619.bot.commands.custom;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.MainController;
import nl.lang2619.bot.commands.CommandBase;
import nl.lang2619.bot.utils.MessageSending;
import nl.lang2619.bot.utils.Permissions;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tim on 25-11-2014.
 */
public class AddCommands extends CommandBase {

    @Override
    public void channelCommand(MessageEvent<PircBotX> event) throws Exception {
        super.channelCommand(event);
        if (Permissions.getLevel(user) >= 2) {
            if (args[1].contains("-ul=")) {
                List<String> response = new ArrayList<>();
                response.addAll(Arrays.asList(args).subList(3, args.length));
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < response.size(); i++) {
                    result.append(response.get(i));
                    if (i != response.size() - 1) {
                        result.append(" ");
                    }
                }
                String responseComplete = result.toString();
                if (args[1].equalsIgnoreCase("-ul=mod")) {

                    addCommand(args[2], responseComplete, "mod", event);
                } else if (args[1].equalsIgnoreCase("-ul=reg")) {
                    addCommand(args[2], responseComplete, "reg", event);
                } else {
                    addCommand(args[2], responseComplete, event);
                }
            } else {
                List<String> response = new ArrayList<>();
                response.addAll(Arrays.asList(args).subList(2, args.length));
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < response.size(); i++) {
                    result.append(response.get(i));
                    if (i != response.size() - 1) {
                        result.append(" ");
                    }
                }
                String responseComplete = result.toString();
                addCommand(args[1], responseComplete, event);
            }
        } else {
            MessageSending.sendNormalMessage("You don't have permission to add a command", event);
        }
    }

    private void addCommand(String command, String response, MessageEvent<PircBotX> event) throws IOException {
        if (!Bot.commandList.containsKey(command)) {
            Bot.commandList.put(command, response);
            MessageSending.sendWhisper(event.getUser().getNick(), "Command added");
            Bot.log.info(command.toString() + " added");
            MainController.getInstance().commandFiller();
            Bot.saveAllTheThings();
        } else {
            MessageSending.sendWhisper(event.getUser().getNick(), "Command already exists");
        }
    }

    private void addCommand(String command, String response, String permission, MessageEvent<PircBotX> event) throws IOException {
        if (!Bot.commandList.containsKey(command)) {
            Bot.commandList.put(command, response);
            Bot.commandpermList.put(command, permission);
            MessageSending.sendWhisper(event.getUser().getNick(), "Command added");
            Bot.log.info(command.toString() + " added");
            MainController.getInstance().commandFiller();
            Bot.saveAllTheThings();
        } else {
            MessageSending.sendWhisper(event.getUser().getNick(), "Command already exists");
        }
    }

}
