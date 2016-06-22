package nl.lang2619.bot.commands.extra;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.MainController;
import nl.lang2619.bot.commands.CommandBase;
import nl.lang2619.bot.utils.MessageSending;
import nl.lang2619.bot.utils.Permissions;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import java.io.IOException;

/**
 * Created by Tim on 11/20/2014.
 */
public class CommandPermissions extends CommandBase {

    @Override
    public void channelCommand(MessageEvent<PircBotX> event) throws Exception {
        super.channelCommand(event);
        if (args.length == 1) {
            System.out.println(Bot.permList.get(user.toLowerCase()));
            if (Bot.permList.get(user.toLowerCase()) != null) {
                MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(),"You have the following level of permissions : " + getPermission(user));
            } else {
                MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(),"You have no permissions");
            }
        } else if (args.length == 4) {
            if(args[3].equalsIgnoreCase("reg") && Permissions.getLevel(user) >= 2) {
                addPermission(args[2], args[3], event);
            }else if(Permissions.getLevel(user) == 4){
                addPermission(args[2], args[3], event);
            }else{
                MessageSending.sendNormalMessage("You do not have permission to give a user a higher permission level than regular",event);
            }
        } else if (args.length == 3 && Permissions.getLevel(user) == 4) {
            removePermission(args[2], event);
        } else {
            MessageSending.sendMessageWithPrefix("Correct Args: !permission &lt;add|remove&gt; &lt;name&gt; [&lt;mod|reg|smod&gt;]", user, event);
        }
    }

    private void removePermission(String user, MessageEvent<PircBotX> event) throws IOException {
        if (Bot.permList.get(user.toLowerCase()) != null) {
            Bot.permList.remove(user.toLowerCase());
            MessageSending.sendNormalMessage(user + " has had their permissions revoked.", event);
            Bot.log.info(user + " has had their permissions revoked.");
            Bot.saveAllTheThings();
            MainController.getInstance().permissionFiller();
        } else {
            MessageSending.sendNormalMessage(user + " doesn't have permissions.", event);
        }
    }

    private void addPermission(String user, String level, MessageEvent<PircBotX> event) throws IOException {
        if (level.equalsIgnoreCase("mod") || level.equalsIgnoreCase("reg") || level.equalsIgnoreCase("smod")) {
            if (Bot.permList.containsKey(user)) {
                Bot.permList.replace(user.toLowerCase(), user.toLowerCase(), level.toLowerCase());
                MessageSending.sendNormalMessage(user + " has been given " + level + " permissions.", event);
                Bot.log.info(user + " has been given " + level + " permissions.");
                Bot.saveAllTheThings();
                MainController.getInstance().permissionFiller();
            } else {
                Bot.permList.put(user.toLowerCase(), level.toLowerCase());
                MessageSending.sendNormalMessage(user + " has been given " + level + " permissions.", event);
                Bot.log.info(user + " has been given " + level + " permissions.");
                Bot.saveAllTheThings();
                MainController.getInstance().permissionFiller();
            }
        } else {
            MessageSending.sendNormalMessage("Only permission levels are MOD and REG", event);
        }
    }

    private String getPermission(String user) {
        return Bot.permList.get(user);
    }

}
