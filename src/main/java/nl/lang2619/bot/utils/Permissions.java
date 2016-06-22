package nl.lang2619.bot.utils;

import nl.lang2619.bot.Bot;

/**
 * Created by Tim on 10/13/2014.
 */
public class Permissions {
    /**
     * @param user = username of permission to check
     * @return 4 = Owner, 3 = Super Mod, 2 = Mod, 1 = Regular, 0 = Normal
     */
    public static int getLevel(String user) {
        if (user.equals(Bot.config.getProperty("autoJoinChannel")) || (Defaults.debugToggled && user.equals("lorddusk"))) {
            return 4;
        } else if (Bot.permList.containsKey(user.toLowerCase()) && Bot.permList.get(user.toLowerCase()).equalsIgnoreCase("smod")) {
            return 3;
        } else if (Bot.permList.containsKey(user.toLowerCase()) && Bot.permList.get(user.toLowerCase()).equalsIgnoreCase("mod")) {
            return 2;
        } else if (Bot.permList.containsKey(user.toLowerCase()) && Bot.permList.get(user.toLowerCase()).equalsIgnoreCase("reg")) {
            return 1;
        } else {
            return 0;
        }
    }

    public static boolean isPermitted(String user){
        if(Bot.permitted.contains(user.toLowerCase())){
            return true;
        }
        else if(getLevel(user) >= 1){
            return true;
        }
        else{
            return false;
        }
    }
}
