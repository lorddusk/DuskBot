package nl.lang2619.bot.commands.extra;

import nl.lang2619.bot.commands.ChannelMethods;
import nl.lang2619.bot.commands.CommandBase;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Tim on 1-6-2015.
 */
public class CommandNowPlaying extends CommandBase {
    @Override
    public void channelCommand(MessageEvent<PircBotX> event) throws Exception {
        super.channelCommand(event);
        if(args.length == 1){
            ChannelMethods.NowPlaying();
        }
    }
}
