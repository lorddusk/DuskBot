package nl.lang2619.bot.commands.extra;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.MainController;
import nl.lang2619.bot.commands.CommandBase;
import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.JSONParser;
import nl.lang2619.bot.utils.MessageSending;
import nl.lang2619.bot.utils.Permissions;
import org.json.JSONObject;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Tim on 10/14/2014.
 */
public class CommandQuote extends CommandBase {

    private boolean quoteSend = false;
    private boolean quoteAdded = false;

    @Override
    public void channelCommand(MessageEvent<PircBotX> event) throws Exception {
        super.channelCommand(event);
        if (args.length == 1) {
            Random rand = new Random();

            long quote = rand.nextInt(Bot.quoteList.size()) + 1;
            while (!quoteSend) {
                if (Bot.quoteList.containsKey(quote)) {
                    if (Bot.quoteList.get(quote) != null) {
                        MessageSending.sendNormalMessage("Quote #" + quote + " : " + Bot.quoteList.get(quote), event);
                        quoteSend = true;
                    }
                } else {
                    quote += 1;
                    quoteSend = false;
                }
            }
            quoteSend = false;
        } else if (args.length == 2) {
            if (args[1].equalsIgnoreCase("list")) {
                MessageSending.sendNormalMessage("Want to know all the quotes? Go here : http://duskbot.nl/botpages/quotes.php?streamer="+ Defaults.getStreamer(),event);
            }
            else if (Bot.quoteList.get(Long.parseLong(args[1])) != null) {
                MessageSending.sendNormalMessage("Quote #" + Long.parseLong(args[1]) + " : " + Bot.quoteList.get(Long.parseLong(args[1])), event);
            } else {
                MessageSending.sendNormalMessage("Quote #" + Long.parseLong(args[1]) + " Doesn't exist.", event);
            }

        } else if (args.length >= 2) {
            if (Permissions.getLevel(user) >= 2) {
                if (args[1].equals("add") && !args[2].isEmpty()) {
                    try {
                        List<String> quote = new ArrayList<>();
                        quote.addAll(Arrays.asList(args).subList(2, args.length));
                        StringBuilder result = new StringBuilder();
                        for (int i = 0; i < quote.size(); i++) {
                            result.append(quote.get(i));
                            if (i != quote.size() - 1) {
                                result.append(" ");
                            }
                        }
                        JSONObject Json = new JSONObject(JSONParser.readUrl("https://api.twitch.tv/kraken/channels/"
                        										+ Bot.config.getProperty("autoJoinChannel")));
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                 	   //get current date time with Date()
                 	   Date date = new Date();
                        String completeQuote = result.toString() + " ~" + Json.get("game") + " " + dateFormat.format(date) ;
                        long quoted = (long) Bot.quoteList.size() + 1;
                        
                        while (!quoteAdded) {
                            if (!Bot.quoteList.containsKey(quoted)) {
                                Bot.quoteList.put(quoted, completeQuote);
                                quoteAdded = true;
                                MessageSending.sendNormalMessage("Quote has been added as #" + quoted + ".", event);
                                Bot.log.info("Quote "+quoted+" has been added");
                            } else {
                                quoted += 1;
                                quoteAdded = false;
                            }
                        }
                        quoteAdded = false;
                        MainController.getInstance().quoteFiller();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    } finally {
                        Bot.saveAllTheThings();
                    }
                } else if (args[1].equals("remove")) {
                    try {
                        Bot.quoteList.remove(Long.parseLong(args[2]));
                        MessageSending.sendNormalMessage("Quote " + args[2] + " removed.", event);
                        Bot.log.info("Quote " + args[2] + " has been removed");
                        MainController.getInstance().quoteFiller();
                    } catch (Exception e) {
                        MessageSending.sendNormalMessage("Quote wasn't found", event);
                    } finally {
                        Bot.saveAllTheThings();
                    }
                }
            }
        } else {
            MessageSending.sendNormalMessage("Correct Args: !quote #/add/remove [args-for-operation]", event);
        }
    }

    public static String getQuoteList() {
        String rankListing = "";
        Set<Map.Entry<Long, String>> mapSet = Bot.quoteList.entrySet();
        Iterator<Map.Entry<Long, String>> mapIterator = mapSet.iterator();
        while (mapIterator.hasNext()) {
            Map.Entry<Long, String> mapEntry = mapIterator.next();
            Long key = mapEntry.getKey();
            String value = mapEntry.getValue();
            rankListing += ("Quote # : " + key + " | Quote : " + value) + "\n";
        }
        return rankListing;
    }


}

