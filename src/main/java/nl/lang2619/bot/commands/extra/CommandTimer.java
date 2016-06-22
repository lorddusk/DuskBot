package nl.lang2619.bot.commands.extra;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.commands.CommandBase;
import nl.lang2619.bot.utils.MessageSending;
import nl.lang2619.bot.utils.Permissions;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tim on 2-12-2014.
 */
public class CommandTimer extends CommandBase {


    @Override
    public void channelCommand(final MessageEvent<PircBotX> event) throws Exception {
        super.channelCommand(event);
        if (Permissions.getLevel(user.toLowerCase()) >= 2) {
            if (args.length != 4) {
                MessageSending.sendNormalMessage("Wrong Syntax user : !timer &lt;command1&gt; &lt;time in minutes&gt; &lt;command2&gt;", event);
            }
            if (args.length == 4) {
                if (Bot.commandList.containsKey(args[1]) && Bot.commandList.containsKey(args[3])) {
                    MessageSending.sendNormalMessage(Bot.commandList.get(args[1]), event);
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            MessageSending.sendNormalMessage(Bot.commandList.get(args[3]), event);
                        }
                    }, Integer.parseInt(args[2]) * 60 * 1000);

                }
            }
        }
    }
}