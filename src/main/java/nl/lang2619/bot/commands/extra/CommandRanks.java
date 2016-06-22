package nl.lang2619.bot.commands.extra;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.MainController;
import nl.lang2619.bot.commands.CommandBase;
import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.MessageSending;
import nl.lang2619.bot.utils.Permissions;
import org.apache.commons.lang3.text.WordUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Tim on 10/14/2014.
 */
public class CommandRanks extends CommandBase {

    @Override
    public void channelCommand(MessageEvent<PircBotX> event) throws Exception {
        super.channelCommand(event);
        if (args.length == 1) {
            if (Bot.rankUserList.get(user.toLowerCase()) != null) {
                MessageSending.sendMessageWithPrefix(user + " is " + getRank(user) + "!", user, event);
            } else {
                MessageSending.sendMessageWithPrefix(user + " has no rank.", user, event);
            }
        }
        else if (args.length == 2) {
            if (args[1].equalsIgnoreCase("sell")) {
                sellRank(user, event);
                Bot.log.info(user + "sold his rank.");
                Bot.saveAllTheThings();
            }
            if(args[1].equalsIgnoreCase("list") ){
                MessageSending.sendNormalMessage("The ranks you can buy, check them here : http://duskbot.nl/botpages/rankList.php?streamer="+ Defaults.getStreamer(),event);
            }
        }
        else if(args.length == 3) {
            if (args[1].equalsIgnoreCase("buy")) {
                buyRank(user, removeUnderscores(args[2]), event);
                Bot.log.info(user + "bought " + args[2]);
                Bot.saveAllTheThings();
            }
            if (args[1].equalsIgnoreCase("remove")) {
                if (Permissions.getLevel(user) >= 3) {
                    removeRank(removeUnderscores(args[2]), event);
                    Bot.log.info(args[2] + " has got their rank removed.");
                    Bot.saveAllTheThings();
                }
            }
        }
        else if (args.length > 3) {
            if (args[1].equalsIgnoreCase("give")) {
                if (Permissions.getLevel(user) == 4) {
                    giveRank(args[2].toLowerCase(), removeUnderscores(args[3]), event);
                    Bot.log.info(args[2] + " received a special rank " + args[3]);
                    Bot.saveAllTheThings();
                }
            }
            if (args[1].equalsIgnoreCase("add")) {
                if (Permissions.getLevel(user) >= 3) {
                    addRank(removeUnderscores(args[2]), Long.parseLong(args[3]), event);
                    Bot.log.info("A new rank was added: "+args[2]);
                    MainController.getInstance().rankFiller();
                    Bot.saveAllTheThings();
                }
            }

        } else {
            MessageSending.sendMessageWithPrefix("Correct Args: !rank &lt;operation&gt; [args-for-operation]", user, event);
        }

    }

    private String removeUnderscores(String arg) {
        String newName;
        newName = arg.replace("_", " ");
        WordUtils.capitalize(newName);
        return newName;
    }

    private void giveRank(String user, String rank, MessageEvent<PircBotX> event) {
        Bot.rankUserList.put(user, rank);
    }

    private void addRank(String rank, Long amount, MessageEvent<PircBotX> event) throws IOException {
        if (Bot.rankList.get(rank) == null) {
            Bot.rankList.put(rank, amount);
            MessageSending.sendMessageWithPrefix(user + " added " + rank, user, event);
            Bot.saveAllTheThings();
        } else {
            MessageSending.sendMessageWithPrefix(user + " this rank already exists.", user, event);
        }
    }

    private void removeRank(String rank, MessageEvent<PircBotX> event) throws IOException {
        if (Bot.rankList.get(rank) == null) {
            MessageSending.sendMessageWithPrefix(user + " this rank doesn't exist", user, event);
        } else {
            Bot.rankList.remove(rank);
            MessageSending.sendMessageWithPrefix(user +" "+ rank +" removed", user, event);
            Bot.saveAllTheThings();
        }
    }

    private void buyRank(String user, String rank, MessageEvent<PircBotX> event) {
        try {
            Long points = Bot.userList.get(user);
            Long rankCost = Bot.rankList.get(rank);
            if (Bot.rankList.get(rank) != null) {
                if (points >= rankCost) {
                    Bot.userList.put(user, Bot.userList.get(user) - rankCost);
                    Bot.rankUserList.put(user, rank);
                    MessageSending.sendMessageWithPrefix(user + " successfully bought " + rank, user, event);
                    Bot.saveAllTheThings();
                } else {
                    MessageSending.sendMessageWithPrefix(user + " you do not have enough " + Defaults.getPointName(), user, event);
                }
            } else {
                System.out.println(rank);
                System.out.println(Bot.rankList.get(rank));
                MessageSending.sendMessageWithPrefix(user + " this rank doesn't exist.", user, event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sellRank(String user, MessageEvent<PircBotX> event) {
        try {
            Long rankCost = Bot.rankList.get(getRank(user));
            Long points = Bot.userList.get(user);
            if (Bot.rankUserList.get(user) != null) {
                Bot.rankUserList.remove(user);
                Bot.userList.put(user, (points + (rankCost / 2)));
                MessageSending.sendMessageWithPrefix(user + " you have successfully sold your rank. But you only received half of your " + Defaults.getPointName() + " back, because inflation.", user, event);
                Bot.saveAllTheThings();
            } else {
                MessageSending.sendMessageWithPrefix(user + " you either have no rank, or you have a unsellable rank.", user, event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRank(String user) {
        return Bot.rankUserList.get(user);
    }


    public static String getRankList(){
        String rankListing = "";
        Set<Map.Entry<String,Long>> mapSet = Bot.rankList.entrySet();
        Iterator<Map.Entry<String,Long>> mapIterator = mapSet.iterator();
        while(mapIterator.hasNext()){
            Map.Entry<String,Long> mapEntry = mapIterator.next();
            String key = mapEntry.getKey();
            Long value = mapEntry.getValue();
            rankListing += ("Rank : " + key + " | Costs : " + value) + "\n";
        }
        return rankListing;
    }
}
