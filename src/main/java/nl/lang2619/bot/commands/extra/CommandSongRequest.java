package nl.lang2619.bot.commands.extra;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.commands.CommandBase;
import nl.lang2619.bot.utils.Defaults;
import nl.lang2619.bot.utils.MessageSending;
import nl.lang2619.bot.utils.Permissions;
import nl.lang2619.bot.utils.json.Save;
import nl.lang2619.bot.utils.json.Upload;
import nl.lang2619.bot.utils.jsonclasses.YoutubeVideo;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Tim on 6/10/2015.
 */
public class CommandSongRequest extends CommandBase {

    @Override
    public void channelCommand(MessageEvent<PircBotX> event) throws Exception {
        super.channelCommand(event);
        Defaults.songRequestBoolean = false;
        if (Permissions.isPermitted(event.getUser().getNick().toLowerCase()) || !Defaults.linkPurge) {
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("clear")) {
                    Bot.songRequestList.clear();
                    MessageSending.sendNormalMessage("Song requests cleared.");
                    Save.songRequestList();
                    Upload.uploadFiles();
                    Defaults.songRequestNumber = 0;
                } else if (args[1].equalsIgnoreCase("list")) {
                    MessageSending.sendNormalMessage("Requested songs can be found here : http://duskbot.nl/botpages/songRequest.php?streamer=" + Defaults.getStreamer());
                } else {
                    YoutubeVideo youtube = new YoutubeVideo(args[1]);
                    if (youtube.getTitle().equals("null")) {
                        MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), " Song requested isn't a valid link, try again.");
                    } else {
                        Defaults.incSongRequestNumber();
                        Bot.songRequestList.put(youtube, event.getUser().getNick().toLowerCase());
                        MessageSending.sendWhisper(event.getUser().getNick().toLowerCase(), " Song requested and added to the list.");
                        Save.songRequestList();
                        Upload.uploadFiles();
                    }

                }
            }
        }
        Defaults.songRequestBoolean = true;
    }
}
