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
package com.jagrosh.jammi.audio;

import com.sedmelluq.discord.lavaplayer.format.AudioDataFormatTools;
import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class AudioManager
{
    private final AudioPlayerManager playerManager;
    private final AudioPlayer audioPlayer;
    private final TrackHandler listener;
    private final LocalAudioOutput output;

    public AudioManager()
    {
        playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
        playerManager.getConfiguration().setOutputFormat(StandardAudioDataFormats.COMMON_PCM_S16_BE);
        audioPlayer = playerManager.createPlayer();
        listener = new TrackHandler(audioPlayer);
        audioPlayer.addListener(listener);
        output = new LocalAudioOutput(audioPlayer, AudioDataFormatTools.toAudioFormat(playerManager.getConfiguration().getOutputFormat()));
    }

    public void search(String input)
    {
        playerManager.loadItemOrdered(listener, input, new AudioLoadResultHandler()
        {
            @Override
            public void trackLoaded(AudioTrack track)
            {
                listener.addTrack(audioPlayer, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist tracklist)
            {
                if(tracklist.getSelectedTrack() != null)
                    trackLoaded(tracklist.getSelectedTrack());
                else if(tracklist.isSearchResult())
                    trackLoaded(tracklist.getTracks().get(0));
                else
                    tracklist.getTracks().forEach(track -> trackLoaded(track));
            }

            @Override
            public void noMatches()
            {
            }

            @Override
            public void loadFailed(FriendlyException ex)
            {
            }
        });
    }

    public void shutdown()
    {
        output.shutdown();
    }
}
