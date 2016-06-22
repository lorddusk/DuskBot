package nl.lang2619.bot.module;

import nl.lang2619.bot.commands.ChannelMethods;
import nl.lang2619.bot.commands.extra.CommandPermit;
import nl.lang2619.bot.commands.extra.CommandPoints;
import nl.lang2619.bot.threads.ModCommon;
import nl.lang2619.bot.threads.ScheduleCommon;
import nl.lang2619.bot.threads.StreamingCommon;
import nl.lang2619.bot.threads.ViewerCommon;
import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.MessageSending;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.Objects;

/**
 * Created by Tim on 10/12/2014.
 */
public class Hooks extends ListenerAdapter<PircBotX> {

    @Override
    public void onConnect(ConnectEvent<PircBotX> event) throws Exception {
        if (event.getBot().getBotId() == 1) {
            event.getBot().sendCAP().request("twitch.tv/commands");
        } else {
            System.out.println("Bot started and joined " + Defaults.getStreamer() + "'s channel.\nPlease enjoy DuskBot");
            MessageSending.sendNormalMessage("Bot started. Please enjoy Chirp/DuskBot");
            if (!Objects.equals(Defaults.latestVersion(), Defaults.VERSION)) {
                MessageSending.sendWhisper(Defaults.getStreamer(), "You are running " + Defaults.VERSION + ". But the latest version is " + Defaults.latestVersion() + ". Please consider updating.");
            }
        }
    }

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        if (ViewerCommon.updateViewers.getState().equals(Thread.State.NEW)) {
            ViewerCommon.updateViewers.start();
        }
        if (CommandPoints.points.getState().equals(Thread.State.NEW)) {
            CommandPoints.points.start();
        }
        if (ModCommon.updateMods.getState().equals(Thread.State.NEW)) {
            ModCommon.updateMods.start();
        }
        if (StreamingCommon.checkIfOnline.getState().equals(Thread.State.NEW)) {
            StreamingCommon.checkIfOnline.start();
        }
        if (Defaults.linkPurge) {
            CommandPermit.containsLink(event.getMessage(), event);
        } else {
            if (Defaults.songRequestBoolean) {
                ChannelMethods.youtubeDescription(event.getMessage(), Defaults.songRequestBoolean);
            }
        }
        if (Defaults.capsPurge) {
            CommandPermit.containsCaps(event.getMessage(), event);
        }
        if (Defaults.wotPurge) {
            CommandPermit.containsWOT(event.getMessage(), event);
        }
        if (ScheduleCommon.scheduleMessages.getState().equals(Thread.State.NEW)) {
            ScheduleCommon.scheduleMessages.start();
        }
    }
}
