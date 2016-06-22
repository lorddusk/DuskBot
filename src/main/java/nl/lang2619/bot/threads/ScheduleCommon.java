package nl.lang2619.bot.threads;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.MessageSending;
import nl.lang2619.bot.utils.jsonclasses.Schedule;

/**
 * Created by Tim on 19-5-2015.
 */
public class ScheduleCommon {


    static int interval = 5 * 60;
    static int ms = 1000;

    static int time = interval * ms;

    static long message = 0;
    public static boolean toggled = true;


    public static Thread scheduleMessages = new Thread("ScheduleCommon") {
        @Override
        public void run() {
            while (true) {
                if (Defaults.schedule) {
                    try {
                        Thread.sleep(time);
                        if (toggled) {
                            Schedule scheduleMessage = Bot.scheduledList.get(getMessageNumber());
                            if(scheduleMessage.getToggle()) {
                                MessageSending.sendNormalMessage(scheduleMessage.getMessage());
                            }else{

                            }
                            message++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };


    private static Long getMessageNumber() {
        Long messageCount = Long.valueOf(Bot.scheduledList.size());
        if (messageCount == 0) { //2
            Defaults.schedule = false;
            MessageSending.sendNormalMessage("There are no messages scheduled. Please add these messages");
        }

        while (Bot.scheduledList.get(message) == null || !Bot.scheduledList.get(message).getToggle()) {
            message++;
            if (message >= messageCount) {
                message = 0;
            }
        }
        return message;
    }
}
