package nl.lang2619.bot.module;

import nl.lang2619.bot.utils.MessageSending;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.NoticeEvent;

/**
 * Created by Tim on 10/12/2014.
 */
public class HooksWhisperer extends ListenerAdapter<PircBotX> {

    @Override
    public void onConnect(ConnectEvent<PircBotX> event) throws Exception {
        if(event.getBot().getBotId() == 1) {
            event.getBot().sendCAP().request("twitch.tv/membership");
            event.getBot().sendCAP().request("twitch.tv/tags");
        }
    }

    @Override
    public void onNotice(NoticeEvent<PircBotX> event) throws Exception {
        if (event.getMessage().contains("That user")){
            MessageSending.sendNormalMessage("If you're not getting a response from the bot.");
            MessageSending.sendNormalMessage("You will either need to follow the bot or change you're settings here http://www.twitch.tv/settings/security .");
        }
    }
}
