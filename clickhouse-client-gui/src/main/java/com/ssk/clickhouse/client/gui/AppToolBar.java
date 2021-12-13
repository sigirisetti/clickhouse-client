package com.ssk.clickhouse.client.gui;

import javax.swing.*;

import static com.ssk.clickhouse.client.utils.AppSwingUtils.createImageIcon;

public class AppToolBar extends JToolBar {

    private final int BUTTON_DIMENSION = 32;

    private Application app;

    AppToolBar(Application app) {
        JButton newConnButton =getToolbarButton("images/connect.png", "New Connection");
        newConnButton.addActionListener(e -> app.showConnectionDialog());
        add(newConnButton);
    }

    private JButton getToolbarButton(String image, String desc) {
        JButton button = new JButton(createImageIcon(BUTTON_DIMENSION, image, desc));
        button.setToolTipText(desc);
        return button;
    }

}
