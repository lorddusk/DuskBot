package nl.lang2619.bot.commands.extra;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.commands.CommandBase;
import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.MessageSending;
import nl.lang2619.bot.utils.Permissions;
import nl.lang2619.bot.utils.json.Save;
import nl.lang2619.bot.utils.json.Upload;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by Tim on 16-3-2015.
 */
public class CommandRaffle extends CommandBase {

    Random rand = new Random();

    @Override
    public void channelCommand(MessageEvent<PircBotX> event) throws Exception {
        super.channelCommand(event);
        if (args.length == 1) {
            if (Permissions.getLevel(user) >= 3) {
                drawRaffle();
            }
        } else if (args.length == 2) {
            if (args[1].equalsIgnoreCase("buy")) {
                buyTickets(user, (long) 1);
            }
            if (args[1].equalsIgnoreCase("clear")) {
                if (Permissions.getLevel(user) >= 3) {
                    Bot.raffleList.clear();
                    Save.raffleList();
                    Upload.uploadDataFiles();
                }
            }
            if (args[1].equalsIgnoreCase("check")) {
                MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "You have " + getRaffleTickets(user) + " tickets");
            }
            if (args[1].equalsIgnoreCase("price")) {
                MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), " The tickets cost " + Defaults.raffleCost + " " + Defaults.getPointName() + " each.");
            }
            if (args[1].equalsIgnoreCase("total")) {
                MessageSending.sendNormalMessage("Currently there have been " + Bot.raffleList.size() + " ticket(s) sold.");
            }
            if (args[1].equalsIgnoreCase("list")) {
                MessageSending.sendNormalMessage("You can check the amount of tickets sold, and how many tickets someone has on this website : http://duskbot.nl/botpages/raffle.php?streamer=" + Defaults.getStreamer(), event);
            }
        } else if (args.length == 3) {
            if (args[1].equalsIgnoreCase("buy")) {
                if (Long.valueOf(args[2]) > 0) {
                    buyTickets(user, Long.valueOf(args[2]));
                } else {
                    MessageSending.sendWhisper(user, "Buying negative tickets won't work...");
                }

            }
            if (args[1].equalsIgnoreCase("set")) {
                if (Permissions.getLevel(user) >= 3) {
                    Defaults.setRaffleCost(Integer.valueOf(args[2]));
                    MessageSending.sendNormalMessage("Raffle tickets now costs " + Defaults.raffleCost + " " + Defaults.getPointName());
                    Save.raffleList();
                    Save.dataList();
                    Upload.uploadDataFiles();
                }
            }
        }
    }

    private void drawRaffle() throws IOException {
        int size = Bot.raffleList.size();
        int randomnumber = rand.nextInt((size - 1) + 1) + 1;
        String user = Bot.raffleList.get(Long.valueOf(randomnumber));

        MessageSending.sendNormalMessage(user + " you have won the raffle, the draw ID was " + randomnumber);

        Bot.raffleList.clear();
        Save.raffleList();
        Save.dataList();
        Upload.uploadDataFiles();
    }

    private void buyTickets(String user, Long amount) throws IOException {
        Long totalCost = amount * Defaults.raffleCost;
        Long points = Bot.userList.get(user);
        if (points >= totalCost) {
            Long size = (long) Bot.raffleList.size();
            for (int i = 1; i <= amount; i++) {
                Bot.raffleList.put(size + i, user);
            }
            Bot.userList.put(user, Bot.userList.get(user) - totalCost);
            MessageSending.sendNormalMessage(user + " just bought " + amount + " tickets.");
            Save.raffleList();
            Upload.uploadDataFiles();
        } else {
            MessageSending.sendWhisper(user, "You don't have enough " + Defaults.getPointName() + " to buy " + amount + " tickets. The most you can buy is " + Math.round(points / Defaults.raffleCost));
        }
    }

    private int getRaffleTickets(String user) {
        int tickets = 0;

        Set<Map.Entry<Long, String>> mapSet = Bot.raffleList.entrySet();
        for (Map.Entry<Long, String> mapEntry : mapSet) {
            if (mapEntry.getValue().equals(user)) {
                tickets++;
            }
        }

        return tickets;
    }
}
