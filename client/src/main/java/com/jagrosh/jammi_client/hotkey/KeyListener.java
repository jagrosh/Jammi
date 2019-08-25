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
package com.jagrosh.jammi_client.hotkey;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
@FunctionalInterface
public interface KeyListener extends NativeKeyListener
{
    public static void registerHook() throws NativeHookException
    {
        // Get the logger for "org.jnativehook" and set the level to warning.
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(java.util.logging.Level.WARNING);

        // disable the parent handlers.
        logger.setUseParentHandlers(false);

        GlobalScreen.registerNativeHook();
    }

    public static void registerKeyListener(KeyListener listener)
    {
        GlobalScreen.addNativeKeyListener(listener);
    }

    @Override
    public default void nativeKeyTyped(NativeKeyEvent event) {}

    @Override
    public default void nativeKeyPressed(NativeKeyEvent event) {}

    @Override
    public default void nativeKeyReleased(NativeKeyEvent event) { onKey(event.getKeyCode()); }

    public void onKey(int keyCode);
}
