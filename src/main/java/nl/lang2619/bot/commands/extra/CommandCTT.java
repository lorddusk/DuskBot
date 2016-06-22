package nl.lang2619.bot.commands.extra;

import nl.lang2619.bot.commands.ChannelMethods;
import nl.lang2619.bot.commands.CommandBase;
import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.MessageSending;
import nl.lang2619.bot.utils.Permissions;
import nl.lang2619.bot.utils.json.Save;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Tim on 5/25/2015.
 */
public class CommandCTT extends CommandBase{

    @Override
    public void channelCommand(MessageEvent<PircBotX> event) throws Exception {
        super.channelCommand(event);
        if(args.length == 1){
            MessageSending.sendNormalMessage("Please support the stream by clicking on this link and tweet it around! : " + Defaults.getBitlyLink());
        }
        if(args.length >= 2){
            if(Permissions.getLevel(user.toLowerCase())>=2){
                ChannelMethods.changeCTT(args);
                MessageSending.sendNormalMessage("Changed the CTT text. Also renewed the link.");
                Save.dataList();
            }
        }
    }
}
