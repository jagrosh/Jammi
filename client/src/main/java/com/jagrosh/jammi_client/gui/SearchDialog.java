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
package com.jagrosh.jammi_client.gui;

import com.jagrosh.jammi_client.hotkey.KeyListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class SearchDialog extends JDialog
{
    static
    {
        KeyListener.registerKeyListener(keyCode -> 
        {
            if(keyCode == NativeKeyEvent.VC_F12)
            {
                System.out.println("f12");
                SwingUtilities.invokeLater(() -> 
                {
                    var dialog = new SearchDialog();
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                    dialog.toFront();
                    dialog.requestFocus();
                });
            }
        });
    }
    
    public SearchDialog()
    {
        setTitle("Search");
        setUndecorated(true);
        setFocusable(true);
        setAlwaysOnTop(true);
        
        JTextField field = new JTextField();
        field.setColumns(40);
        add(field);
        pack();
        
        setLocationRelativeTo(null);
        setVisible(true);
        toFront();
        requestFocus();
        
        field.requestFocusInWindow();
        
        field.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent event) 
            {
                System.out.println("gained");
            }

            @Override
            public void focusLost(FocusEvent arg0)
            {
                System.out.println("lost");
                dispose();
            }
        });
    }
}
