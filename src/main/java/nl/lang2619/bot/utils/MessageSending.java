package nl.lang2619.bot.utils;

import nl.lang2619.bot.Bot;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Tim on 10/13/2014.
 */
public class MessageSending {

    public static void sendMessageWithPrefix(String message, String user, MessageEvent event){
        if(Bot.rankUserList.get(user.toLowerCase()) == null){
            event.getChannel().send().message(message);
        }else{
            String prefix = "[" + Bot.rankUserList.get(user.toLowerCase()) + "] ";
            event.getChannel().send().message(prefix + message);
        }
    }

    public static void sendNormalMessage(String message, MessageEvent event){
        event.getChannel().send().message(message);
    }

    public static void sendNormalMessage(String message){
        Bot.mbm.getBotById(0).sendIRC().message("#"+Bot.config.getProperty("autoJoinChannel"),message);
    }

    public static void sendWhisper(String user, String message){
        Bot.mbm.getBotById(1).sendIRC().message("#jtv","/w "+user+" "+message);
    }

}
