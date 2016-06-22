package nl.lang2619.bot.commands.extra;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.commands.CommandBase;
import nl.lang2619.bot.threads.ViewerCommon;
import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.JSONParser;
import nl.lang2619.bot.utils.MessageSending;
import nl.lang2619.bot.utils.Permissions;
import org.json.JSONException;
import org.json.JSONObject;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.Random;

/**
 * Created by Tim on 12-1-2015.
 */
public class CommandWinner extends CommandBase {

    private Random rand = new Random();

    @Override
    public void channelCommand(MessageEvent<PircBotX> event) throws Exception {
        super.channelCommand(event);
        if (Permissions.getLevel(user) >= 3) {
            if (args.length <= 1) {
                MessageSending.sendNormalMessage("And the winner is..... " + getWinner(false) + "!!!! Congratulations!!!!", event);
            }
            if (args.length == 2) {
                if (args[1].toLowerCase().equals("-f")) {
                    MessageSending.sendNormalMessage("And the winner is..... " + getWinner(true) + "!!!! Congratulations!!!!", event);
                }
            }
        }
    }

    private String getWinner(boolean follows) throws Exception {
        String winner = "";
        int win = 0;
        ViewerCommon.updateViewers();
        if (!follows) {
            boolean breaker = true;
            while (breaker) {
                win = rand.nextInt(ViewerCommon.viewers.size());
                winner = ViewerCommon.viewers.get(win);
                if (winner.equals(Bot.config.getProperty("autoJoinChannel").toString().toLowerCase()) || winner.equals(Defaults.getBotName().toString().toLowerCase())) {
                    breaker = true;
                } else {
                    breaker = false;
                }
            }
            return winner;
        } else {
            boolean breaker = true;
            while (breaker) {
                win = rand.nextInt(ViewerCommon.viewers.size());
                winner = ViewerCommon.viewers.get(win);
                if (winner.equals(Bot.config.getProperty("autoJoinChannel").toString().toLowerCase()) || winner.equals(Defaults.getBotName().toString().toLowerCase())) {
                    breaker = true;
                } else {
                    JSONObject json = new JSONObject(JSONParser.readUrl("https://api.twitch.tv/kraken/users/" + winner + "/follows/channels/" + Bot.config.getProperty("autoJoinChannel")));
                    try {
                        if (json.get("status").toString().contains("404")) {
                            breaker = false;
                        }
                    } catch (JSONException e) {
                        breaker = true;
                        return winner;
                    }
                }

            }
            return winner;
        }
    }
}
