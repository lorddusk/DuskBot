package nl.lang2619.bot.commands.extra;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.commands.CommandBase;
import nl.lang2619.bot.threads.ScheduleCommon;
import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.MessageSending;
import nl.lang2619.bot.utils.Permissions;
import nl.lang2619.bot.utils.jsonclasses.Schedule;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by Tim on 19-5-2015.
 */
public class CommandSchedule extends CommandBase {

    // x !schedule add <message>
    //!schedule remove <number>
    // x !schedule toggle
    // x !schedule on <number>
    // x !schedule off <number>

    //Number - Message

    private boolean messageAdded = false;

    @Override
    public void channelCommand(MessageEvent<PircBotX> event) throws Exception {
        super.channelCommand(event);
        if (Permissions.getLevel(user.toLowerCase()) >= 2) {
            if (args.length == 2) {
                if (args[1].equals("toggle")) {
                    if (Defaults.schedule) {
                        Defaults.schedule = false;
                        ScheduleCommon.toggled = false;
                    } else {
                        Defaults.schedule = true;
                        ScheduleCommon.toggled = true;
                    }
                }
            } else if (args.length >= 2) {
                if (args[1].equals("add") && !args[2].isEmpty()) {
                    try {
                        List<String> message = new ArrayList<>();
                        message.addAll(Arrays.asList(args).subList(2, args.length));
                        StringBuilder result = new StringBuilder();
                        for (int i = 0; i < message.size(); i++) {
                            result.append(message.get(i));
                            if (i != message.size() - 1) {
                                result.append(" ");
                            }
                        }
                        long messages = (long) Bot.quoteList.size() + 1;
                        while (!messageAdded) {
                            if (Bot.scheduledList.get(messages) == null) {
                                Bot.scheduledList.put(messages, new Schedule(messages, result.toString(), true));
                                MessageSending.sendWhisper(user, "Scheduled message has been added as number " + messages);
                                messageAdded = true;
                            } else {
                                messages += 1;
                                messageAdded = false;
                            }
                        }
                        messageAdded = false;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    } finally {
                        Bot.saveAllTheThings();
                    }
                }

            } else if (args.length == 3) {
                if (args[1].equalsIgnoreCase("on") && !args[2].isEmpty()) {
                    for (long i = 0; i < Bot.scheduledList.size(); i++) {
                        if (Objects.equals(Bot.scheduledList.get(i).getNumber(), Long.valueOf(args[2]))) {
                            Schedule schedule = Bot.scheduledList.get(i);
                            schedule.setToggle(true);
                            Bot.scheduledList.replace(i, schedule);
                            Bot.extra.setProperty("scheduleToggle","true");
                        }
                    }
                }
                if (args[1].equalsIgnoreCase("off") && !args[2].isEmpty()) {
                    for (long i = 0; i < Bot.scheduledList.size(); i++) {
                        if (Objects.equals(Bot.scheduledList.get(i).getNumber(), Long.valueOf(args[2]))) {
                            Schedule schedule = Bot.scheduledList.get(i);
                            schedule.setToggle(false);
                            Bot.scheduledList.replace(i, schedule);
                            Bot.extra.setProperty("scheduleToggle","false");
                        }
                    }
                }
                if (args[1].equalsIgnoreCase("remove") && !args[2].isEmpty()) {
                    for (long i = 0; i < Bot.scheduledList.size(); i++) {
                        if (Objects.equals(Bot.scheduledList.get(i).getNumber(), Long.valueOf(args[2]))) {
                            Bot.scheduledList.remove(i);
                        }
                    }
                }
            }
        }
    }
}
