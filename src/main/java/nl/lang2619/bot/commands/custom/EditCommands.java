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

public class EditCommands extends CommandBase {

    @Override
    public void channelCommand(MessageEvent<PircBotX> event) throws Exception {
        super.channelCommand(event);

        if(Permissions.getLevel(user) >= 2) {
            if(Bot.commandList.containsKey(args[1])) {
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
                editCommand(args[1], responseComplete, event);
            }
        } else {
            MessageSending.sendNormalMessage("You don't have permission to edit a command", event);
        }
    }

    private void editCommand(String command, String responseComplete, MessageEvent<PircBotX> event) throws IOException {
        if(Bot.commandList.containsKey(command)) {
            Bot.commandList.put(command, responseComplete);
            if (Bot.commandpermList.containsKey(command)) {
                Bot.commandpermList.put(command, responseComplete);
            }
            MessageSending.sendWhisper(event.getUser().getNick(), "Command has been edited");
            Bot.log.info(command + " edited");
            MainController.getInstance().commandFiller();
            Bot.saveAllTheThings();
        } else {
            MessageSending.sendWhisper(event.getUser().getNick(), "Command doesn't exist");
        }
    }
}
