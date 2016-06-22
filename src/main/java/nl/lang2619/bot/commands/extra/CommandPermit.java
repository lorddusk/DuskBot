package nl.lang2619.bot.commands.extra;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.commands.ChannelMethods;
import nl.lang2619.bot.commands.CommandBase;
import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.MessageSending;
import nl.lang2619.bot.utils.Permissions;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tim on 2-12-2014.
 */
public class CommandPermit extends CommandBase {

    private static final Pattern urlPattern = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|(www\\.))"
                    + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    public static boolean containsLink(String s, MessageEvent<PircBotX> event) throws InterruptedException {
        Matcher matcher = urlPattern.matcher(s);
        if (matcher.find()) {
            if (!Permissions.isPermitted(getNick(event).toLowerCase())) {
                Thread.sleep(1000);
                Timeout(event, "link");

                return true;
            } else {
                ChannelMethods.youtubeDescription(s, Defaults.songRequestBoolean);
                Bot.permitted.remove(event.getUser().getNick().toLowerCase());
                return false;
            }
        } else {
            return false;
        }
    }

    private static String getNick(MessageEvent<PircBotX> event) {
        return event.getUser().getNick();
    }

    public static void Timeout(MessageEvent<PircBotX> event, String type) {
        if (Bot.strikeList.get(event.getUser().getNick().toLowerCase()) == null) {
            Bot.strikeList.put(event.getUser().getNick().toLowerCase(), (long) 1);
        } else {
            Bot.strikeList.put(event.getUser().getNick().toLowerCase(), Bot.strikeList.get(event.getUser().getNick().toLowerCase()) + 1);
        }

        if (type.equals("link")) {
            MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "You are not permitted to post links. (Strike : #" + Bot.strikeList.get(event.getUser().getNick().toLowerCase()) + ")");
            MessageSending.sendNormalMessage("You are not permitted to post links.");
        } else if (type.equals("caps")) {
            MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "Please refrain from using big amounts of caps. (Strike : #" + Bot.strikeList.get(event.getUser().getNick().toLowerCase()) + ")");
            MessageSending.sendNormalMessage("Please refrain from using big amounts of caps.");
        } else if (type.equals("wot")) {
            MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "Please refrain from using walls of text. (Strike : #" + Bot.strikeList.get(event.getUser().getNick().toLowerCase()) + ")");
            MessageSending.sendNormalMessage("Please refrain from using walls of text.");
        } else {
            MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "You were purged. (Strike : #" + Bot.strikeList.get(event.getUser().getNick().toLowerCase()) + ")");
        }

        if (Bot.strikeList.get(event.getUser().getNick().toLowerCase()) <= 3) {
            event.getBot().sendRaw().rawLine("PRIVMSG " + event.getChannel().getName() + " :.timeout " + event.getUser().getNick() + " 1");
        } else {
            event.getBot().sendRaw().rawLine("PRIVMSG " + event.getChannel().getName() + " :.timeout " + event.getUser().getNick() + " 600");
        }
    }

    public static void Purge(MessageEvent<PircBotX> event, String user) {
        if (Bot.strikeList.get(user) == null) {
            Bot.strikeList.put(user, (long) 1);
        } else {
            Bot.strikeList.put(user, Bot.strikeList.get(user) + 1);
        }

        MessageSending.sendWhisper(user, "You were purged. (Strike : #" + Bot.strikeList.get(user) + ")");

        if (Bot.strikeList.get(user) <= 3) {
            event.getBot().sendRaw().rawLine("PRIVMSG " + event.getChannel().getName() + " :.timeout " + user + " 1");
        } else {
            event.getBot().sendRaw().rawLine("PRIVMSG " + event.getChannel().getName() + " :.timeout " + user + " 600");
        }
    }

    public static void containsCaps(String message, MessageEvent<PircBotX> event) throws InterruptedException {
        if (message.length() > 10) {
            double threshold = Math.floor(message.length() / 2);
            String isUp = "";
            int z = message.length();
            for (int y = 0; y < z; y++) {
                if (Character.isUpperCase(message.charAt(y))) {
                    char w = message.charAt(y);
                    isUp = isUp + w;
                }
            }
            if (isUp.length() > threshold) {
                Thread.sleep(1000);
                if (!Permissions.isPermitted(getNick(event).toLowerCase())) {
                    Timeout(event, "caps");

                }
            }
        }
    }

    public static void containsWOT(String message, MessageEvent<PircBotX> event) throws InterruptedException {
        if (message.length() > 250) {
            Thread.sleep(1000);
            if (!Permissions.isPermitted(getNick(event).toLowerCase())) {
                Timeout(event, "wot");

            }
        }
    }

    @Override
    public void channelCommand(MessageEvent<PircBotX> event) throws Exception {
        super.channelCommand(event);
        if (Permissions.getLevel(user.toLowerCase()) >= 2) {
            Bot.permitted.add(args[1].toLowerCase());
            MessageSending.sendNormalMessage("You are allowed to post one link " + args[1].toLowerCase(), event);
            Bot.log.info(args[1] + " was permitted to post a link.");
        }
    }
}