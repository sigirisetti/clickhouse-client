package com.ssk.clickhouse.client.gui;

import javax.swing.*;

public class AppMenuBar extends JMenuBar {
    AppMenuBar() {
        JMenu fileMenu = new JMenu("File");
        add(fileMenu);
        fileMenu.add(new JMenuItem("New Connection"));
        fileMenu.add(new JMenuItem("Disconnect"));
        fileMenu.add(new JMenuItem("Exit"));

        JMenu help = new JMenu("Help");
        add(help);
        help.add(new JMenuItem("About"));
    }
}
