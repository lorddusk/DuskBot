package nl.lang2619.bot.commands;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Tim on 10/13/2014.
 */
public abstract class CommandBase {
    public CommandBase(){}

    public boolean isActive;

    public String message;
    public String command;
    public String user;

    public Channel channel;

    public String[] args;

    public void channelCommand(MessageEvent<PircBotX> event) throws Exception {
        message = event.getMessage();
        command = message.split(" ")[0];
        user = event.getUser().getNick();
        args = event.getMessage().split(" ");
        channel = event.getChannel();
    }
}