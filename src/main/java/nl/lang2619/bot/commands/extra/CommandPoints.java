package nl.lang2619.bot.commands.extra;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.MainController;
import nl.lang2619.bot.commands.CommandBase;
import nl.lang2619.bot.threads.StreamingCommon;
import nl.lang2619.bot.threads.ViewerCommon;
import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.MessageSending;
import nl.lang2619.bot.utils.Permissions;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.*;

/**
 * Created by Tim on 10/13/2014.
 */
public class CommandPoints extends CommandBase {

    static String point = Defaults.getPointName().toString();

    public static Thread points = new Thread() {
        public void run() {
            while (true) {
                try {
                    if (Defaults.toggleStream) {
                        System.out.println("Auto start giving out " + point);
                        try {
                            CommandPoints.class.newInstance().autoTickPoints();
                            Bot.saveAllTheThings();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (!StreamingCommon.isOnline() && Defaults.toggleStream) {
                        System.out.println("Stream isn't online.");
                    } else if (!Defaults.toggleStream && StreamingCommon.isOnline()) {
                        System.out.println("Bot isn't toggled.");
                    }
                    sleep((Defaults.time * 1000 * 60));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public CommandPoints() {
        try {
            ViewerCommon.updateViewers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void autoTickPoints() {
        try {
            ViewerCommon.updateViewers();
            addAll(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPoints(String user, Long amount, boolean auto) {
        if (!Bot.blackList.contains(user)) {
            if (Bot.userList.get(user) != null) {
                Bot.userList.put(user, (Bot.userList.get(user) + amount));
                Defaults.totalPoints += amount;
            } else {
                Bot.userList.put(user, amount);
                Defaults.totalPoints += amount;
            }
        } else {
            if (!auto) {
                MessageSending.sendNormalMessage(user + " is on the blacklist. They can't gain " + Defaults.getPointName());
            }
        }
    }

    @Override
    public void channelCommand(MessageEvent<PircBotX> event) throws Exception {
        super.channelCommand(event);
        if (args.length <= 1) {
            if (Bot.blackList.contains(user)) {
                MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), "Sorry, " + user + " you are blacklisted and are not eligible to gain any " + Defaults.getPointName() + ". Please contact a mod if you think this is not correct");
            } else {
                MessageSending.sendWhisper(user, "You have a total of " + getPoints(user) + " " + Defaults.getPointName() + "!");
            }
        } else {
            if (args[1].equalsIgnoreCase("give")) {
                if (Permissions.getLevel(user) >= 3) {
                    if (args.length >= 3) {
                        addPoints(args[2].toLowerCase(), Long.parseLong(args[3]), false);
                        if (!Bot.blackList.contains(args[2].toLowerCase())) {
                            MessageSending.sendNormalMessage(args[3] + " " + Defaults.getPointName() + " have been added to " + args[2] + " total.", event);
                            Bot.log.info("Gave away " + args[3] + " points to " + args[2]);
                            MainController.getInstance().pointsFiller();
                        }
                    } else {
                        MessageSending.sendNormalMessage("Incorrect syntax. Use : !" + Defaults.getPointName() + " give &lt;user&gt; &lt;amount&gt;", event);
                    }
                }
            }
            if (args[1].equalsIgnoreCase("clear")) {
                if (Permissions.getLevel(user) >= 2) {
                    if (args.length >= 2) {
                        if (Bot.userList.get(args[2].toLowerCase()) != null) {
                            Bot.userList.remove(args[2].toLowerCase());
                            MessageSending.sendNormalMessage(args[2] + " has been removed from the points list.");
                            MainController.getInstance().pointsFiller();
                        }
                    }
                }
            }
            if (args[1].equalsIgnoreCase("check")) {
                if (Permissions.getLevel(user) >= 2) {
                    if (args.length >= 2) {
                        MessageSending.sendNormalMessage(args[2] + " has a total of " + getPoints(args[2]) + " " + Defaults.getPointName(), event);
                    } else {
                        MessageSending.sendNormalMessage("Incorrect syntax. Use : !" + Defaults.getPointName() + " check &lt;name&gt;", event);
                    }
                }
            }
            if (args[1].equalsIgnoreCase("giveall")) {
                if (Permissions.getLevel(user) >= 3) {
                    if (args.length >= 2) {
                        addAll(Long.parseLong(args[2]));
                        MessageSending.sendNormalMessage("Everyone got " + args[2] + " " + Defaults.getPointName() + "!", event);
                        Bot.log.info("Gave away " + args[2] + " points to everyone");
                        MainController.getInstance().pointsFiller();
                        Bot.saveAllTheThings();
                    } else {
                        MessageSending.sendNormalMessage("Incorrect syntax. Use : !" + Defaults.getPointName() + " giveall &lt;amount&gt;", event);
                    }
                }
            }
            if (args[1].equalsIgnoreCase("top")) {
                if (Permissions.getLevel(user) >= 1) {
                    getTopList();
                }
            }
        }
    }

    private void getTopList() {
        Set<Map.Entry<String, Long>> set = Bot.userList.entrySet();
        List<Map.Entry<String, Long>> list = new ArrayList<>(set);
        Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> a,
                               Map.Entry<String, Long> b) {
                return b.getValue().compareTo(a.getValue());
            }
        });


        String[] first = String.valueOf(list.get(0)).split("=");
        String[] second = String.valueOf(list.get(1)).split("=");
        String[] third = String.valueOf(list.get(2)).split("=");
        String[] fourth = String.valueOf(list.get(3)).split("=");
        String[] fifth = String.valueOf(list.get(4)).split("=");


        MessageSending.sendNormalMessage("1st. " + first[0] + " with " + first[1] + " " + Defaults.getPointName());
        MessageSending.sendNormalMessage("2nd. " + second[0] + " with " + second[1] + " " + Defaults.getPointName());
        MessageSending.sendNormalMessage("3th. " + third[0] + " with " + third[1] + " " + Defaults.getPointName());
        MessageSending.sendNormalMessage("4th. " + fourth[0] + " with " + fourth[1] + " " + Defaults.getPointName());
        MessageSending.sendNormalMessage("5th. " + fifth[0] + " with " + fifth[1] + " " + Defaults.getPointName());
    }

    private void addAll(long amount) throws Exception {
        ViewerCommon.updateViewers();
        for (int i = 0; i < ViewerCommon.viewers.size(); i++) {
            if (!Bot.blackList.contains(ViewerCommon.viewers.get(i))) {
                addPoints(ViewerCommon.viewers.get(i), amount, false);
            }
        }
        MainController.getInstance().pointsFiller();
    }

    private String getPoints(String user) {
        if (Bot.userList.get(user) == null) {
            return "0";
        } else {
            return Bot.userList.get(user).toString();
        }
    }

}

