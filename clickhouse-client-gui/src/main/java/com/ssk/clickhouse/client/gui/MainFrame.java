package com.ssk.clickhouse.client.gui;

import com.ssk.clickhouse.db.model.Table;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private final Application app;

    private AppMenuBar menuBar;
    private AppToolBar toolBar;
    private AppLeftNavTree navTree;
    private AppContentTabbedPane tabbedPane;

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

    private JComponent buildContentPanel() {
        tabbedPane = new AppContentTabbedPane();
        return tabbedPane.buildUI();
    }

    public void displayTableData(Table tableConfig, List<List<Object>> data) {
        tabbedPane.displayTableData(tableConfig, data);
    }
}
