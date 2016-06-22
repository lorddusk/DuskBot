package nl.lang2619.bot.utils.json;

import nl.lang2619.bot.Bot;
import nl.lang2619.bot.utils.Defaults;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Tim on 2/3/2015.
 */
public class Upload {

    static String host = ""; //FTP HOST
    static String user = ""; //FTP USER
    static String pass = ""; //FTP PASSWORD
    static String uploadPath = "/public_html/api/channel/";

    static String[] files = new String[]{"songRequestList", "data", "subMessages", "scheduleList", "rankList", "commandpermissions", "commands", "permissions", "ranks", "points", "note", "quotes", "raffleTickets"};
    static String[] raffleFiles = new String[]{"data", "subMessages", "raffleTickets"};

    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                Bot.log.info("SERVER: " + aReply);
            }
        }
    }

    public static void uploadFiles() {
        Bot.log.info("Uploading files to FTP.");
        try {
            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect(host);
                boolean login = ftpClient.login(user, pass);
                if (login) {
                    ftpClient.enterLocalPassiveMode();
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    ftpClient.makeDirectory(uploadPath + "" + Bot.config.getProperty("autoJoinChannel"));
                    showServerReply(ftpClient);

                    for (String file : files) {
                        try {
                            File upload = new File("config/" + file + ".json");

                            InputStream inputStream = new FileInputStream(upload);
                            OutputStream outputStream = ftpClient.storeFileStream(uploadPath + Bot.config.getProperty("autoJoinChannel") + "/" + file + ".json");
                            byte[] bytesIn = new byte[4096];
                            int read;

                            while ((read = inputStream.read(bytesIn)) != -1) {
                                outputStream.write(bytesIn, 0, read);
                            }
                            inputStream.close();
                            outputStream.close();

                            boolean completed = ftpClient.completePendingCommand();
                            if (completed) {
                                inputStream.close();
                            }
                        } catch (Exception e) {
                        }
                    }
                    ftpClient.logout();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Bot.log.info("Finished uploading files to FTP.");
        }
    }

    public static void uploadDataFiles() {
        Bot.log.info("Uploading raffle files to FTP.");
        try {
            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect(host);
                boolean login = ftpClient.login(user, pass);
                if (login) {
                    ftpClient.enterLocalPassiveMode();
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                    for (String file : raffleFiles) {
                        File upload = new File("config/" + file + ".json");

                        InputStream inputStream = new FileInputStream(upload);
                        OutputStream outputStream = ftpClient.storeFileStream(uploadPath + Defaults.getStreamer() + "/" + file + ".json");
                        byte[] bytesIn = new byte[4096];
                        int read;

                        while ((read = inputStream.read(bytesIn)) != -1) {
                            outputStream.write(bytesIn, 0, read);
                        }
                        inputStream.close();
                        outputStream.close();

                        boolean completed = ftpClient.completePendingCommand();
                        if (completed) {
                            inputStream.close();
                        }
                    }
                    ftpClient.logout();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Bot.log.info("Finished uploading raffle files to FTP.");
        }
    }
}
