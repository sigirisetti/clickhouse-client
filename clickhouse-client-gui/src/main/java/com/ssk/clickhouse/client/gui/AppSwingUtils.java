package com.ssk.clickhouse.client.gui;

import javax.swing.*;

public class AppSwingUtils {

    static ImageIcon createImageIcon(int h, String path,
                                        String description) {
        java.net.URL imgURL = Thread.currentThread().getContextClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(new ImageIcon(imgURL, description).getImage().getScaledInstance( h, h,  java.awt.Image.SCALE_SMOOTH));
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }

    }
}
