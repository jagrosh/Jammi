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

/**
 *
 * This is primarily a command-line tool to play multiple tracks and then exit
 * 
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class OneTimePlayer
{
    public static void main(String[] args) throws Exception
    {
        // set up audio player
        var audio = new AudioManager();

        // play command line arguments
        for(String arg: args)
            audio.search(arg);
        
        // TODO: exit when last song completes
    }
}
