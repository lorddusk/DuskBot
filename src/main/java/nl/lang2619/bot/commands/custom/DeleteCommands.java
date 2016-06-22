package nl.lang2619.bot.commands.custom;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.MainController;
import nl.lang2619.bot.commands.CommandBase;
import nl.lang2619.bot.utils.MessageSending;
import nl.lang2619.bot.utils.Permissions;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import java.io.IOException;

/**
 * Created by Tim on 25-11-2014.
 */
public class DeleteCommands extends CommandBase {

    @Override
    public void channelCommand(MessageEvent<PircBotX> event) throws Exception {
        super.channelCommand(event);
        if (Permissions.getLevel(user) >= 2) {
            if (Bot.commandList.containsKey(args[1])) {
                removeCommand(args[1], event);
            }
        } else {
            MessageSending.sendNormalMessage("You don't have permission to delete a command", event);
        }
    }

    private void removeCommand(String command, MessageEvent<PircBotX> event) throws IOException {
        if (Bot.commandList.containsKey(command)) {
            Bot.commandList.remove(command);
            if (Bot.commandpermList.containsKey(command)) {
                Bot.commandpermList.remove(command);
            }
            MessageSending.sendWhisper(event.getUser().getNick(), "Command has been removed");
            Bot.log.info(command + " removed");
            MainController.getInstance().commandFiller();
            Bot.saveAllTheThings();
        } else {
            MessageSending.sendWhisper(event.getUser().getNick(), "Command doesn't exist");
        }
    }
}
