package com.ssk.clickhouse.client.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final Application app;

    private AppMenuBar menuBar;
    private AppToolBar toolBar;
    private AppLeftNavTree navTree;

    MainFrame(Application app, String title) {

        setTitle(title);
        this.app = app;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

        toolBar = new AppToolBar(app);
        getContentPane().add(toolBar, BorderLayout.NORTH);

        menuBar = new AppMenuBar();
        setJMenuBar(menuBar);
        //Display the window.

        getContentPane().add(buildMainUi());

        pack();
        setVisible(true);
    }

    JComponent buildMainUi() {
        navTree = new AppLeftNavTree(app);
        JSplitPane splitPane = new JSplitPane(SwingConstants.VERTICAL, navTree.buildUI(), buildContentPanel());
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.2);
        add(splitPane);
        return splitPane;
    }

    private JPanel buildContentPanel() {
        JPanel contentPanel = new JPanel();
        return contentPanel;
    }
}
