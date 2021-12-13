package com.ssk.clickhouse.client.gui;

import com.ssk.clickhouse.db.model.Table;

import javax.swing.*;
import java.util.List;

public class AppContentTabbedPane {

    private JPanel contentPanel;
    private JTabbedPane tabbedPane;
    private TableDataDisplayPanel tableDataDisplayPanel;

    public AppContentTabbedPane() {
        buildUI();
    }

    JTabbedPane buildUI() {
        tabbedPane = new JTabbedPane();

        tableDataDisplayPanel = new TableDataDisplayPanel();
        tabbedPane.addTab("Table Data", tableDataDisplayPanel);
        return tabbedPane;
    }

    public void displayTableData(Table tableConfig, List<List<Object>> data) {
        tableDataDisplayPanel.displayTableData(tableConfig, data);
    }
}
