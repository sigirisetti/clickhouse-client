package com.ssk.clickhouse.client.gui;

import com.ssk.clickhouse.db.model.ClientConnectionConfig;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionTable extends JPanel {

    private boolean DEBUG = false;

    private JTable table;
    private ConnectionTableModel connectionTableModel;

    private Application app;

    public ConnectionTable(Application app) {
        super(new GridLayout(1, 0));
        this.app = app;

        connectionTableModel = new ConnectionTableModel();
        connectionTableModel.setData(app.loadConnConfig());
        table = new JTable(connectionTableModel);
        table.setPreferredScrollableViewportSize(new Dimension(400, 100));
        table.setFillsViewportHeight(true);
        PasswordCellRenderer passwordCellRenderer = new PasswordCellRenderer();
        table.getColumnModel().getColumn(3).setCellRenderer(passwordCellRenderer);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);
    }

    ClientConnectionConfig getSelectedRow() {
        int n = table.getSelectedRow();
        if (n == -1) {
            JOptionPane.showMessageDialog(app.getMainFrame(), "Please select a connection row", "No Connection Selected", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return connectionTableModel.getSelectedRow(n);
    }

    List<ClientConnectionConfig> getAllConnConfigs() {
        return connectionTableModel.getData();
    }

    public void addNewConnection() {
        connectionTableModel.addNewConnection();
    }

    class ConnectionTableModel extends AbstractTableModel {

        private String[] columnNames = {"Hostname", "Port", "User", "Password"};

        private List<ClientConnectionConfig> data;

        public ConnectionTableModel() {
            data = new ArrayList<>();
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            if (row >= data.size()) {
                return null;
            }
            ClientConnectionConfig con = data.get(row);
            switch (col) {
                case 0:
                    return con.getHostname();
                case 1:
                    return con.getPort();
                case 2:
                    return con.getUsername();
                case 3:
                    return con.getPassword();
                default:
                    throw new RuntimeException();
            }
        }

        public Class getColumnClass(int c) {
            switch (c) {
                case 0:
                    return String.class;
                case 1:
                    return Integer.class;
                case 2:
                    return String.class;
                case 3:
                    return String.class;
            }
            return String.class;
        }

        public boolean isCellEditable(int row, int col) {
            return true;
        }

        public void setValueAt(Object value, int row, int col) {
            ClientConnectionConfig con = data.get(row);
            switch (col) {
                case 0:
                    con.setHostname((String) value);
                    break;
                case 1:
                    con.setPort(((Number) value).intValue());
                    break;
                case 2:
                    con.setUsername((String) value);
                    break;
                case 3:
                    con.setPassword((String) value);
            }

            fireTableCellUpdated(row, col);
        }

        public ClientConnectionConfig getSelectedRow(int selectedRow) {
            return data.get(selectedRow);
        }

        public List<ClientConnectionConfig> getData() {
            return data;
        }

        public void setData(List<ClientConnectionConfig> connConfig) {
            if (connConfig != null) {
                this.data.addAll(connConfig);
                fireTableDataChanged();
            }
        }

        public void addNewConnection() {
            data.add(new ClientConnectionConfig());
            fireTableDataChanged();
        }
    }
}