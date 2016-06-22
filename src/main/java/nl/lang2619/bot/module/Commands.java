package nl.lang2619.bot.module;

import nl.lang2619.bot.commands.*;
import nl.lang2619.bot.commands.custom.AddCommands;
import nl.lang2619.bot.commands.custom.DeleteCommands;
import nl.lang2619.bot.commands.custom.EditCommands;
import nl.lang2619.bot.commands.extra.*;
import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.MessageSending;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.HashMap;

/**
 * Created by Tim on 10/13/2014.
 */
public class Commands extends ListenerAdapter<PircBotX> {
    public HashMap<String, CommandBase> commands = new HashMap<String, CommandBase>();

    String points = Defaults.getPointName().toString();

    public Commands() {
        commands.clear();
        commands.put(points, new CommandPoints());
        commands.put("rank", new CommandRanks());
        commands.put("quote", new CommandQuote());
        commands.put("permission", new CommandPermissions());
        commands.put("addcommand", new AddCommands());
        commands.put("delcommand", new DeleteCommands());
        commands.put("editcommand", new EditCommands());
        commands.put("purge", new CommandPurge());
        commands.put("permit", new CommandPermit());
        commands.put("timer", new CommandTimer());
        commands.put("winner", new CommandWinner());
        commands.put("raffle", new CommandRaffle());
        commands.put("strawpoll", new CommandStrawpoll());
        commands.put("ctt", new CommandCTT());
        commands.put("songrequest", new CommandSongRequest());
        if(Defaults.isVip) {
            commands.put("schedule", new CommandSchedule());
            commands.put("song", new CommandNowPlaying());
        }
    }

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        if (event.getMessage().startsWith("!")) {
            if (commands.containsKey(event.getMessage().substring(1).split(" ")[0])) {
                commands.get(event.getMessage().substring(1).split(" ")[0]).channelCommand(event);
            }
        }
    }
}
