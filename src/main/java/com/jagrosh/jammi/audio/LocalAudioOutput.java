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

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class LocalAudioOutput
{
    private final Logger log = LoggerFactory.getLogger(LocalAudioOutput.class);
    private final ScheduledExecutorService thread = Executors.newSingleThreadScheduledExecutor();
    private final int frameBufferDuration = 20;
    private final AudioPlayer audioPlayer;
    private final SourceDataLine line;
    
    public LocalAudioOutput(AudioPlayer player, AudioFormat format)
    {
        audioPlayer = player;
        audioPlayer.setFrameBufferDuration(frameBufferDuration);
        SourceDataLine tempLine = null;
        try
        {
            tempLine = AudioSystem.getSourceDataLine(format);
            tempLine.open(format);
            tempLine.start();
            log.info("Successfully opened source data line: " + tempLine.toString());
        }
        catch(LineUnavailableException ex)
        {
            log.error("Error opening target data line", ex);
        }
        line = tempLine;
        thread.scheduleWithFixedDelay(() -> playFrame(), 0, frameBufferDuration, TimeUnit.MILLISECONDS);
    }

    private void playFrame()
    {
        var frame = audioPlayer.provide();
        if(frame == null || line == null)
            return;
        line.write(frame.getData(), 0, frame.getDataLength());
    }
    
    public void shutdown()
    {
        thread.shutdown();
    }
}
