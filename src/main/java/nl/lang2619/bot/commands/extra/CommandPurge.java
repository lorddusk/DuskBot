package nl.lang2619.bot.commands.extra;

import nl.lang2619.bot.commands.CommandBase;
import nl.lang2619.bot.utils.Permissions;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Tim on 2-12-2014.
 */
public class CommandPurge extends CommandBase {

    @Override
    public void channelCommand(MessageEvent<PircBotX> event) throws Exception {
        super.channelCommand(event);
        if(Permissions.getLevel(user) >= 2){
            if(args.length == 2){
                CommandPermit.Purge(event, args[1]);
            }
        }
    }
}
