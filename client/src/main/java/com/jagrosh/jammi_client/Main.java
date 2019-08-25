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
package com.jagrosh.jammi_client;

import java.net.*;
import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matthew Russell (mprussell836@gmail.com)
 */
public class Main
{
    public static Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.err.println("Usage: java EchoClient <host name>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = 3600; //TODO make configurable

        try
        {
            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = stdIn.readLine()) != null)
            {
                out.println(userInput);
            }
        }
        catch (UnknownHostException e)
        {
            LOG.error("Don't know host " + hostName + ": ", e);
            System.exit(1);
        }
        catch (IOException e)
        {
            LOG.error("Couldn't get I/O for the connection to " + hostName + ": ", e);
            System.exit(1);
        }
    }
}
