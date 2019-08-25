/*
 * Copyright 2019 John Grosh (john.a.grosh@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jagrosh.jammi_server;

import java.net.*;
import java.io.*;

import com.jagrosh.jammi_server.audio.AudioManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 * @author Matthew Russell (mprussell836@gmail.com)
 */
public class Main
{
    public static Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)
    {
        int portNumber = 3600; //TODO make configurable

        try
        {
            LOG.debug("Setting up audio manager");
            var audio = new AudioManager();

            LOG.info("Setting up server");
            ServerSocket serverSocket = new ServerSocket(portNumber);

            LOG.info("Listening for connections");
            Socket clientSocket = serverSocket.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                audio.search("ytsearch:" + inputLine);
                LOG.info("adding " + inputLine + " to queue");
            }
        }
        catch (IOException ex)
        {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            LOG.error("Error occured listening on port " + portNumber + ": ", ex);
        }
        catch(Exception ex)
        {
            LOG.error("An error occurred during startup: ", ex);
        }
    }
}
