package com.ssk.clickhouse.client.gui;

import com.ssk.clickhouse.db.model.ClientConnectionConfig;
import com.ssk.clickhouse.db.model.Column;
import com.ssk.clickhouse.db.model.Database;
import com.ssk.clickhouse.db.model.Table;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AppLeftNavTree {

    private Application app;
    private JTree tree;
    private DefaultTreeModel model;

    private JPopupMenu popupMenu;
    private JMenuItem connectMenuItem;
    private JMenuItem tableDataMenuItem;

    AppLeftNavTree(Application app) {
        this.app = app;
        buildUI();
        PopupMenuActionHandler popupMenuActionHandler = new PopupMenuActionHandler();
        popupMenu = new JPopupMenu();
        connectMenuItem = new JMenuItem("Connect");
        connectMenuItem.addActionListener(popupMenuActionHandler);
        popupMenu.add(connectMenuItem);

        tableDataMenuItem = new JMenuItem("Show Table Data");
        tableDataMenuItem.addActionListener(popupMenuActionHandler);
        popupMenu.add(tableDataMenuItem);
    }

    private void addDatabases(DefaultMutableTreeNode connNode, List<Database> databases) {
        for (Database db : databases) {
            DefaultMutableTreeNode dbNode = new DefaultMutableTreeNode(db);
            connNode.add(dbNode);
            for (Table t : db.getTables()) {
                DefaultMutableTreeNode table = new DefaultMutableTreeNode(t);
                dbNode.add(table);
                for (Column c : t.getColumn()) {
                    DefaultMutableTreeNode col = new DefaultMutableTreeNode(c);
                    table.add(col);
                }
            }
        }
    }

    JComponent buildUI() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Connections");
        List<ClientConnectionConfig> connections = app.loadConnConfig();
        populateConnectionNodes(top, connections);
        model = new DefaultTreeModel(top);
        tree = new JTree(model);
        addMouseListener();
        addTreeSelectionListener();
        return new JScrollPane(tree);
    }

    private void addTreeSelectionListener() {
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getNewLeadSelectionPath().getLastPathComponent();
            Object userObject = node.getUserObject();
            if (userObject instanceof ClientConnectionConfig) {
                connectMenuItem.setEnabled(true);
                tableDataMenuItem.setEnabled(false);
            } else if (userObject instanceof Table) {
                connectMenuItem.setEnabled(false);
                tableDataMenuItem.setEnabled(true);
            } else {
                connectMenuItem.setEnabled(false);
                tableDataMenuItem.setEnabled(false);
            }
        });
    }

    private void addMouseListener() {
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = tree.getClosestRowForLocation(e.getX(), e.getY());
                    tree.setSelectionRow(row);
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private void populateConnectionNodes(DefaultMutableTreeNode top, List<ClientConnectionConfig> connections) {
        for (ClientConnectionConfig connectionConfig : connections) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(connectionConfig);
            top.add(node);
        }
    }

    class PopupMenuActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TreePath treePath = tree.getSelectionPath();
            DefaultMutableTreeNode connNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
            Object o = connNode.getUserObject();
            if (o instanceof ClientConnectionConfig) {
                populateDbMetaDataNodes(connNode, (ClientConnectionConfig) o);
            } else if (o instanceof Table) {
                displayTableData(getClientConnConfig(treePath), (Table) o);
            }
        }
    }

    private ClientConnectionConfig getClientConnConfig(TreePath treePath) {
        Object[] path = treePath.getPath();
        for(int i = path.length-1;i>=0;i--) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) path[i];
            if(n.getUserObject() instanceof ClientConnectionConfig) {
                return (ClientConnectionConfig) n.getUserObject();
            }
        }
        return null;
    }

    private void displayTableData(ClientConnectionConfig connectionConfig, Table tableConfig) {
        app.displayTableData(connectionConfig, tableConfig);
    }

    private void populateDbMetaDataNodes(DefaultMutableTreeNode connNode, ClientConnectionConfig o) {
        List<Database> databases = app.connect(o);
        addDatabases(connNode, databases);
        tree.expandPath(tree.getSelectionPath());
    }
}
