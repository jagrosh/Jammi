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
package com.jagrosh.jammi_server.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.*;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class TrackHandler implements AudioEventListener
{
    private final List<AudioTrack> queue = new ArrayList<>();
    private final AudioPlayer player;

    public TrackHandler(AudioPlayer player)
    {
        this.player = player;
    }

    public void addTrack(AudioPlayer player, AudioTrack track)
    {
        if(player.getPlayingTrack() == null)
            player.playTrack(track);
        else
            queue.add(track);
    }

    public void pause()
    {
        player.setPaused(!player.isPaused());
    }

    public void restart()
    {
        if(player.getPlayingTrack() == null || !player.getPlayingTrack().isSeekable())
            return;
        player.getPlayingTrack().setPosition(0);
    }

    public void next()
    {
        if(player.getPlayingTrack() != null)
            player.stopTrack();
    }

    public void stop()
    {
        queue.clear();
        next();
    }

    @Override
    public void onEvent(AudioEvent event)
    {
        if(event instanceof TrackEndEvent)
        {
            if(!queue.isEmpty())
                event.player.playTrack(queue.remove(0));
        }
        //        || event instanceof TrackStuckEvent
        //        || event instanceof TrackExceptionEvent
    }
}
