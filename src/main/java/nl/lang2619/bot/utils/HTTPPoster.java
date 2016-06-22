package nl.lang2619.bot.utils;

import java.io.*;
import java.net.*;

/**
 * Created by Tim on 5/21/2015.
 */
public class HTTPPoster {

    public static String postRemoteDataStrawpoll(String urlString) {

        String line = null;
        try {
            HttpURLConnection c = (HttpURLConnection) (new URL(
                    "http://strawpoll.me/api/v2/polls").openConnection());

            c.setRequestMethod("POST");
            c.setRequestProperty("Content-Type",
                    "application/json; charset=UTF-8");
            c.setRequestProperty("User-Agent", "CB1");

            c.setDoOutput(true);
            c.setDoInput(true);
            c.setUseCaches(false);

            String queryString = urlString;

            c.setRequestProperty("Content-Length",
                    Integer.toString(queryString.length()));

            DataOutputStream wr = new DataOutputStream(c.getOutputStream());
            wr.writeBytes(queryString);

            wr.flush();
            wr.close();

            InputStream data = null;
            try {
                data = c.getInputStream();
            } catch (Exception e) {
                e.printStackTrace();
                data = c.getErrorStream();
            }

            BufferedReader b = new BufferedReader(new InputStreamReader(data));

            if ((line = b.readLine()) != null) {
                line = line.substring(line.indexOf(":") + 1, line.indexOf("}"));
                //System.out.println(line);
            }

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return line;
    }

    public static String getRemoteContent(String urlString) {

        String dataIn = "";
        try {
            URL url = new URL(urlString);
            // System.out.println("DEBUG: Getting data from " + url.toString());
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "DuskBot");
//			conn.setConnectTimeout(5 * 1000);
//			conn.setReadTimeout(5 * 1000);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                dataIn += inputLine;
            in.close();

        } catch (Exception ex) {
            if (ex instanceof SocketTimeoutException) {
                return "API took too long to respond.";
            }
            ex.printStackTrace();
        }

        return dataIn;
    }
}
