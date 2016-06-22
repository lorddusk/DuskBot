package nl.lang2619.bot.commands;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.threads.ModCommon;
import nl.lang2619.bot.threads.StreamingCommon;
import nl.lang2619.bot.threads.ViewerCommon;
import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.MessageSending;
import nl.lang2619.bot.utils.Permissions;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.HashMap;

/**
 * Created by Tim on 10/13/2014.
 */
public class ChannelCommands extends ListenerAdapter<PircBotX> {
    private HashMap<String, Long> cooldowns = new HashMap<>();

    public ChannelCommands() {
        try {
            ModCommon.updateModerators();
            ViewerCommon.updateViewers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {

        String message = event.getMessage().split(" ")[0];
        ChannelMethods.preMessage(event);

        if(!commandWasRunRecently(message)) {
            if (message.equalsIgnoreCase("!viewers")) {
                ChannelMethods.Viewers(event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!chatters")) {
                ChannelMethods.Chatters(event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!blacklist")) {
                ChannelMethods.Blacklist(event);
                if(userCheck(event) && Permissions.getLevel(event.getUser().getNick().toLowerCase()) >= 2)
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!whitelist")) {
                ChannelMethods.Whitelist(event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!version")) {
                ChannelMethods.Version(event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!duskbot") || message.equalsIgnoreCase("!wduskbot")) {
                ChannelMethods.Duskbot(event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!changelog")) {
                MessageSending.sendNormalMessage(Defaults.CHANGELOG, event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!debug") && event.getUser().getNick().equalsIgnoreCase("lorddusk")) {
                ChannelMethods.Debug();
            }
            if (message.equalsIgnoreCase("!save")) {
                if (Permissions.getLevel(ChannelMethods.getNick(event)) >= 3) {
                    Bot.saveAllTheThings();
                    MessageSending.sendWhisper(Defaults.getStreamer(), "Saved files");
                }
            }
            if (message.equalsIgnoreCase("!exit") || message.equalsIgnoreCase("!close") || message.equalsIgnoreCase("!shutdown")) {
                if (Permissions.getLevel(ChannelMethods.getNick(event)) >= 3) {
                    ChannelMethods.ShutdownBot();
                    if(userCheck(event))
                        cooldowns.put(message, System.currentTimeMillis());
                }
            }
            if (message.equalsIgnoreCase("!updatemods")) {
                ModCommon.updateModerators();
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!togglestream")) {
                ChannelMethods.ToggleStream(event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!issues")) {
                MessageSending.sendNormalMessage("Any and all issues with the bot, or suggestions, please fill a ticket in here : https://github.com/lorddusk/DuskBotIssues/issues");
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!caster")) {
                ChannelMethods.Caster(event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!urban")) {
                ChannelMethods.UrbanDictionary(event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!status") || message.equalsIgnoreCase("!title")) {
                ChannelMethods.Title(event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!uptime")) {
                ChannelMethods.Uptime(event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!total")) {
                MessageSending.sendNormalMessage("This stream I gave away a total of " + Defaults.totalPoints + " " + Defaults.getPointName(), event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (Bot.commandList.containsKey(message)) {
                ChannelMethods.CustomCommandChecker(event, message);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!commands")) {
                if (!Defaults.whisperToggle) {
                    MessageSending.sendNormalMessage("You can check all the commands on this website : http://duskbot.nl/botpages/commands.php?streamer=" + Defaults.getStreamer(), event);
                    if(userCheck(event))
                        cooldowns.put(message, System.currentTimeMillis());
                } else {
                    MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "You can check all the commands on this website : http://duskbot.nl/botpages/commands.php?streamer=" + Defaults.getStreamer());
                    if(userCheck(event))
                        cooldowns.put(message, System.currentTimeMillis());
                }
            }
            if (message.equalsIgnoreCase("!game")) {
                ChannelMethods.SteamGame(event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!togglelinks")) {
                ChannelMethods.ToggleLinks(event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!togglecaps")) {
                ChannelMethods.ToggleCaps(event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!togglewot")) {
                ChannelMethods.ToggleWoT(event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!note")) {
                ChannelMethods.Note(event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!host")) {
                ChannelMethods.Host(event);
                if(userCheck(event))
                    cooldowns.put(message, System.currentTimeMillis());
            }
            if (message.equalsIgnoreCase("!unhost")) {
                if (Permissions.getLevel(ChannelMethods.getNick(event)) >= 3) {
                    MessageSending.sendNormalMessage("/unhost");
                    if(userCheck(event))
                        cooldowns.put(message, System.currentTimeMillis());
                }
            }
            if (message.equalsIgnoreCase("!togglewhisper")) {
                if (Permissions.getLevel(ChannelMethods.getNick(event)) >= 3) {
                    if (Defaults.whisperToggle) {
                        Defaults.whisperToggle = false;
                        MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "Toggled command whispers off.");
                        Bot.extra.setProperty("whisperToggle", "false");
                        if(userCheck(event))
                            cooldowns.put(message, System.currentTimeMillis());
                    } else {
                        Defaults.whisperToggle = true;
                        MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "Toggled command whispers on.");
                        Bot.extra.setProperty("whisperToggle", "true");
                        if(userCheck(event))
                            cooldowns.put(message, System.currentTimeMillis());
                    }
                }
            }
            //VIP Section of the commands
            if (Defaults.isVip) {
                if (message.equalsIgnoreCase("!newsub")) {
                    ChannelMethods.Newsub(event);
                }
                if (message.equalsIgnoreCase("!oldsub")) {
                    ChannelMethods.Oldsub(event);
                }
                if (event.getUser().getNick().equalsIgnoreCase("twitchnotify")) {
                    if (StreamingCommon.isOnline()) {
                        ChannelMethods.Subscriber(event);
                    }
                }
            }
        }
    }

    private boolean userCheck(MessageEvent<PircBotX> event) {
        return !Defaults.debugToggled && Permissions.getLevel(event.getUser().getNick().toLowerCase()) >= 2;
    }

    private boolean commandWasRunRecently(String command) {
        if(cooldowns.get(command) != null) {
            return System.currentTimeMillis() - cooldowns.get(command) < 10000L;
        }
        return false;
    }
}
