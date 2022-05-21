/*
 * Copyright (c) 2007, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package org.graalvm.visualvm.core.explorer;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

import javax.swing.*;

/**
 *
 * @author Jiri Sedlacek
 */
// Top component for DataSources explorer
final class ExplorerTopComponent extends TopComponent {

    private static final String PREFERRED_ID = "ExplorerTopComponent";  // NOI18N
    private static final Logger LOGGER = Logger.getLogger(ExplorerTopComponent.class.getName());

    static final String ICON_PATH = "org/graalvm/visualvm/core/ui/resources/explorer.png";    // NOI18N

    private static ExplorerTopComponent instance;


    private ExplorerTopComponent() {
        replaceFonts();
        initComponents();
        setName(NbBundle.getMessage(ExplorerTopComponent.class, "LBL_Applications"));   // NOI18N
        setToolTipText(NbBundle.getMessage(ExplorerTopComponent.class, "LBL_Applications"));    // NOI18N
        setIcon(ImageUtilities.loadImage(ICON_PATH, true));

        setFocusable(true);
        setRequestFocusEnabled(true);
        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                ExplorerComponent.instance().requestFocusInWindow();
            }
        });
    }

    private void replaceFonts() {
        Font font = new Font("Tahoma", Font.PLAIN, 11);
        replaceFonts(UIManager.getDefaults(), font);
        replaceFonts(UIManager.getLookAndFeel().getDefaults(), font);
    }

    private void replaceFonts(UIDefaults defaults, Font newFont) {
        defaults.put("Label.font", newFont);

//        for( Enumeration<Object> e = defaults.keys(); e.hasMoreElements(); ){
//            Object key = e.nextElement();
//            Object font = defaults.get(key);
//            if( !(font instanceof Font) )
//                continue;
//
//            String msg = key + ": " + font;
//            System.out.println(msg);
//
//            defaults.put(key, newFont);
//        }
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        add(ExplorerComponent.instance(), BorderLayout.CENTER);
    }


    /**
    * Gets default instance. Do not use directly: reserved for *.settings files only,
    * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
    * To obtain the singleton instance, use {@link findInstance}.
    */
    public static synchronized ExplorerTopComponent getInstance() {
        if (instance == null) instance = new ExplorerTopComponent();
        return instance;
    }

    /**
    * Obtain the ExplorerTopComponent instance. Never call {@link #getDefault} directly!
    */
    public static synchronized ExplorerTopComponent findInstance() {
        TopComponent explorerTopComponent = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (explorerTopComponent == null) return getInstance();
        if (explorerTopComponent instanceof ExplorerTopComponent) return (ExplorerTopComponent)explorerTopComponent;

        if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.warning("There seem to be multiple components with the '" + PREFERRED_ID + "' ID. That is a potential source of errors and unexpected behavior.");   // NOI18N
    }
        return getInstance();
    }

    private boolean needsDocking() {
        return WindowManager.getDefault().findMode(this) == null;
    }

    public void open() {
        if (needsDocking()) {
            Mode mode = WindowManager.getDefault().findMode("explorer"); // NOI18N
            if (mode != null) mode.dockInto(this);
        }
        super.open();
    }

    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    protected String preferredID() {
        return PREFERRED_ID;
    }

}
