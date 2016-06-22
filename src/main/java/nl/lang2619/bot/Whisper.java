/**
 * Copyright (C) 2010-2013 Leon Blakey <lord.quackstar at gmail.com>
 * <p/>
 * This file is part of PircBotX.
 * <p/>
 * PircBotX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * PircBotX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with PircBotX. If not, see <http://www.gnu.org/licenses/>.
 */
package nl.lang2619.bot;

import nl.lang2619.bot.module.HooksWhisperer;
import nl.lang2619.bot.utils.Defaults;
import org.pircbotx.Configuration;

import java.nio.charset.Charset;

/**
 * Created by Tim on 10/12/2014.
 */
public class Whisper {
    public static Configuration whisper = new Configuration.Builder()
            .setEncoding(Charset.forName("UTF8"))
            .setName(Defaults.getBotName())
            .setAutoNickChange(true)
            .setServerHostname(Defaults.getServer())
            .setServerPassword(Defaults.getOAuth())
            .setServerPort(6667)
            .addAutoJoinChannel("#_lorddusk_1434036475740")
            .setMessageDelay(1875)
            .addListener(new HooksWhisperer())
            .buildConfiguration();
}
