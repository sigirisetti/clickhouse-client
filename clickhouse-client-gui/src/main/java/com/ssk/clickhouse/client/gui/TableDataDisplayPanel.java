package com.ssk.clickhouse.client.gui;

import com.ssk.clickhouse.db.model.Table;
import ru.yandex.clickhouse.ClickHouseArray;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class TableDataDisplayPanel extends JPanel {

    private JTable table;
    private JScrollPane scrollPane;
    private JTextArea infoText;

    TableDataDisplayPanel() {
        setLayout(new BorderLayout());
        buildUI();
    }

    private void buildUI() {
        infoText = new JTextArea();
        infoText.setEditable(false);
        infoText.setPreferredSize(new Dimension(2000, 50));
        infoText.setText("No Content to display");
        add(infoText, BorderLayout.NORTH);

        scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                table.setColumnSelectionAllowed(false);
                table.setRowSelectionAllowed(true);
            }
        });
    }

    void displayTableData(Table tableConfig, java.util.List<List<Object>> data) {
        table = new JTable(new TableDataModel(tableConfig, data));
        resizeColumnWidth(table);
        final JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.addMouseListener(tableHeaderMouseAdaptor(header));

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        DoubleArrayRenderer doubleArrayRenderer = new DoubleArrayRenderer();
        table.setDefaultRenderer(ClickHouseArray.class, doubleArrayRenderer);
        scrollPane.setViewportView(table);
    }

    private MouseAdapter tableHeaderMouseAdaptor(JTableHeader header) {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int col = header.columnAtPoint(e.getPoint());
                if (header.getCursor().getType() == Cursor.E_RESIZE_CURSOR) {
                    e.consume();
                } else {
                    //System.out.printf("sorting column %d%n", col);
                    table.setColumnSelectionAllowed(true);
                    table.setRowSelectionAllowed(false);
                    table.clearSelection();
                    table.setColumnSelectionInterval(col, col);
                    //tableModel[selectedTab].sortArrayList(col);
                }
            }
        };
    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300)
                width = 300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    class TableDataModel extends AbstractTableModel {

        private Table tableConfig;
        private java.util.List<List<Object>> data;

        public TableDataModel(Table table, java.util.List<List<Object>> data) {
            this.tableConfig = table;
            this.data = data;
        }

        public int getColumnCount() {
            return tableConfig.getColumn().size();
        }

        public int getRowCount() {
            return data.size();
        }

        public String getColumnName(int col) {
            return tableConfig.getColumn().get(col).getName();
        }

        public Object getValueAt(int row, int col) {
            if (row >= data.size()) {
                return null;
            }
            return data.get(row).get(col);
        }

        public Class getColumnClass(int c) {
            return data.get(0).get(c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
            return false;
        }

        public List<Object> getSelectedRow(int selectedRow) {
            return data.get(selectedRow);
        }

        public List<List<Object>> getData() {
            return data;
        }
    }

    class DoubleArrayRenderer extends DefaultTableCellRenderer {
        private JLabel EMPTY_LABEL = new JLabel("");

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof ClickHouseArray) {
                try {
                    Object arr = ((ClickHouseArray) value).getArray();
                    if (arr instanceof double[]) {
                        return new JLabel(Arrays.toString((double[]) arr));
                    } else if (arr instanceof long[]) {
                        return new JLabel(Arrays.toString((long[]) arr));
                    } else if (arr instanceof int[]) {
                        return new JLabel(Arrays.toString((int[]) arr));
                    } else if (arr instanceof float[]) {
                        return new JLabel(Arrays.toString((float[]) arr));
                    } else if (arr instanceof short[]) {
                        return new JLabel(Arrays.toString((short[]) arr));
                    } else if (arr instanceof byte[]) {
                        return new JLabel(Arrays.toString((byte[]) arr));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return EMPTY_LABEL;
        }
    }
}
