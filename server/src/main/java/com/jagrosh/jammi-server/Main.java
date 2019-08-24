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
package com.jagrosh.jammi;

import com.jagrosh.jammi.audio.AudioManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class Main
{
    public static Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)
    {
        try
        {
            // set up audio player
            var audio = new AudioManager();
            audio.search("ytsearch:idkhow do it all the time lyrics");
            audio.search("ytsearch:chaos chaos do you feel it");
        }
        catch(Exception ex)
        {
            LOG.error("An error occurred during startup: ", ex);
        }
    }
}
