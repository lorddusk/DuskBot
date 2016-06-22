package nl.lang2619.bot.utils.json;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.jsonclasses.Schedule;
import nl.lang2619.bot.utils.jsonclasses.YoutubeVideo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Tim on 11/20/2014.
 */
public class Load {
    public static void quoteList() {
        try {
            FileReader reader = new FileReader("config/quotes.json");
            JSONParser parser = new JSONParser();
            JSONObject object1 = (JSONObject) parser.parse(reader);

            JSONArray quotes = (JSONArray) object1.get("Quotes");

            for (Object quote : quotes) {
                JSONObject inner = (JSONObject) quote;
                Bot.quoteList.put((Long) inner.get("number"), (String) inner.get("quote"));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Quote list couldn't be found.");
            Bot.log.error(e.getMessage());
        } catch (ParseException | IOException e) {
            Bot.log.error(e.getMessage());

        }
    }

    public static void blackList() {
        try {
            FileReader reader = new FileReader("config/blacklist.json");
            JSONParser parser = new JSONParser();
            JSONObject object1 = (JSONObject) parser.parse(reader);

            JSONArray quotes = (JSONArray) object1.get("Blacklist");

            for (Object quote : quotes) {
                JSONObject inner = (JSONObject) quote;
                Bot.blackList.add((String) inner.get("user"));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Blacklist couldn't be found.");
            Bot.log.error(e.getMessage());
        } catch (ParseException | IOException e) {
            Bot.log.error(e.getMessage());

        }
    }

    public static void noteList() {
        try {
            FileReader reader = new FileReader("config/note.json");
            JSONParser parser = new JSONParser();
            JSONObject object1 = (JSONObject) parser.parse(reader);

            JSONArray quotes = (JSONArray) object1.get("Notes");

            for (Object quote : quotes) {
                JSONObject inner = (JSONObject) quote;
                Bot.noteList.put((Long) inner.get("number"), (String) inner.get("note"));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Note list couldn't be found.");
            Bot.log.error(e.getMessage());
        } catch (ParseException | IOException e) {
            Bot.log.error(e.getMessage());

        }
    }

    public static void ranksList() {
        try {
            FileReader reader = new FileReader("config/ranklist.json");
            JSONParser parser = new JSONParser();
            JSONObject object1 = (JSONObject) parser.parse(reader);

            JSONArray users = (JSONArray) object1.get("Ranks");

            for (Object user : users) {
                JSONObject inner = (JSONObject) user;
                Bot.rankList.put((String) inner.get("name"), (Long) inner.get("amount"));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Rank list couldn't be found.");
            Bot.log.error(e.getMessage());
        } catch (ParseException | IOException e) {
            Bot.log.error(e.getMessage());
        }
    }

    public static void rankList() {
        try {
            FileReader reader = new FileReader("config/ranks.json");
            JSONParser parser = new JSONParser();
            JSONObject object1 = (JSONObject) parser.parse(reader);
            JSONArray ranks = (JSONArray) object1.get("Ranks");

            for (Object rank : ranks) {
                JSONObject inner = (JSONObject) rank;
                Bot.rankUserList.put((String) inner.get("user"), (String) inner.get("rank"));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ranks couldn't be found.");
            Bot.log.error(e.getMessage());
        } catch (ParseException | IOException e) {
            Bot.log.error(e.getMessage());
        }
    }

    public static void scheduleList() {
        try {
            FileReader reader = new FileReader("config/scheduleList.json");
            JSONParser parser = new JSONParser();
            JSONObject object1 = (JSONObject) parser.parse(reader);
            JSONArray messages = (JSONArray) object1.get("Schedule");

            for (Object schedule : messages) {
                JSONObject inner = (JSONObject) schedule;
                Bot.scheduledList.put((Long) inner.get("number"), new Schedule((Long) inner.get("number"), inner.get("message").toString(), (boolean) inner.get("toggle")));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Schedule couldn't be found.");
            Bot.log.error(e.getMessage());
        } catch (ParseException | IOException e) {
            Bot.log.error(e.getMessage());
        }
    }

    public static void userList() {
        try {
            FileReader reader = new FileReader("config/points.json");
            JSONParser parser = new JSONParser();
            JSONObject object1 = (JSONObject) parser.parse(reader);

            JSONArray users = (JSONArray) object1.get("Users");

            for (Object user : users) {
                JSONObject inner = (JSONObject) user;
                Bot.userList.put((String) inner.get("user"), (Long) inner.get("amount"));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Points couldn't be found.");
            Bot.log.error(e.getMessage());
        } catch (ParseException | IOException e) {
            Bot.log.error(e.getMessage());
        }
    }

    public static void permList() {
        try {
            FileReader reader = new FileReader("config/permissions.json");
            JSONParser parser = new JSONParser();
            JSONObject object1 = (JSONObject) parser.parse(reader);
            JSONArray ranks = (JSONArray) object1.get("Permissions");

            for (Object rank : ranks) {
                JSONObject inner = (JSONObject) rank;
                Bot.permList.put((String) inner.get("name"), (String) inner.get("permission"));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Permissions couldn't be found.");
            Bot.log.error(e.getMessage());
        } catch (ParseException | IOException e) {
            Bot.log.error(e.getMessage());
        }
    }

    public static void commandList() {
        try {
            FileReader reader = new FileReader("config/commands.json");
            JSONParser parser = new JSONParser();
            JSONObject object1 = (JSONObject) parser.parse(reader);
            JSONArray ranks = (JSONArray) object1.get("Commands");

            for (Object rank : ranks) {
                JSONObject inner = (JSONObject) rank;
                String command = (String) inner.get("command");
                if (!command.equalsIgnoreCase("!ctt")) {
                    if(command.startsWith("!"))
                        Bot.commandList.put(command, (String) inner.get("response"));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Commands couldn't be found.");
            Bot.log.error(e.getMessage());
        } catch (ParseException | IOException e) {
            Bot.log.error(e.getMessage());
        }
    }

    public static void commandPermList() {
        try {
            FileReader reader = new FileReader("config/commandpermissions.json");
            JSONParser parser = new JSONParser();
            JSONObject object1 = (JSONObject) parser.parse(reader);
            JSONArray ranks = (JSONArray) object1.get("Commands");

            for (Object rank : ranks) {
                JSONObject inner = (JSONObject) rank;
                Bot.commandpermList.put((String) inner.get("command"), (String) inner.get("permission"));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Command Permissions couldn't be found.");
            Bot.log.error(e.getMessage());
        } catch (ParseException | IOException e) {
            Bot.log.error(e.getMessage());
        }
    }

    public static void raffleList() {
        try {
            FileReader reader = new FileReader("config/raffleTickets.json");
            JSONParser parser = new JSONParser();
            JSONObject object1 = (JSONObject) parser.parse(reader);
            JSONArray ranks = (JSONArray) object1.get("Raffle");

            for (Object rank : ranks) {
                JSONObject inner = (JSONObject) rank;
                Bot.raffleList.put((Long) inner.get("id"), (String) inner.get("user"));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Raffle Tickets couldn't be found.");
            Bot.log.error(e.getMessage());
        } catch (ParseException | IOException e) {
            Bot.log.error(e.getMessage());
        }
    }

    public static void songRequestList() {
        try {
            FileReader reader = new FileReader("config/songRequestList.json");
            JSONParser parser = new JSONParser();
            JSONObject object1 = (JSONObject) parser.parse(reader);
            JSONArray ranks = (JSONArray) object1.get("Request");

            for (Object rank : ranks) {
                JSONObject inner = (JSONObject) rank;
                Bot.songRequestList.put(new YoutubeVideo((String) inner.get("link")), (String) inner.get("request"));
                Defaults.incSongRequestNumber();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Song Requests couldn't be found.");
            Bot.log.error(e.getMessage());
        } catch (ParseException | IOException e) {
            Bot.log.error(e.getMessage());
        }
    }

    public static void steamList() {
        try {
            org.json.JSONObject json = new org.json.JSONObject(nl.lang2619.bot.utils.JSONParser.readUrl("http://api.steampowered.com/ISteamApps/GetAppList/v0001/"));
            org.json.JSONObject applist = json.getJSONObject("applist");
            org.json.JSONObject apps = applist.getJSONObject("apps");
            org.json.JSONArray app = apps.getJSONArray("app");
            for (int i = 0; i < app.length(); i++) {
                String name = String.valueOf(app.getJSONObject(i).get("name"));
                Integer appid = (Integer) app.getJSONObject(i).get("appid");
                Bot.steamList.put(name, appid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done loading steam game list, amount of games loaded " + Bot.steamList.size());
    }

    public static void subMessages() {
        try {
            FileReader reader = new FileReader("config/subMessages.json");
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(reader);
            Defaults.newSub = String.valueOf(object.get("New"));
            Defaults.oldSub = String.valueOf(object.get("Old"));
        } catch (FileNotFoundException e) {
            System.out.println("Sub messages couldn't be found.");
            Bot.log.error(e.getMessage());
        } catch (ParseException | IOException e) {
            Bot.log.error(e.getMessage());
        }
    }
}
