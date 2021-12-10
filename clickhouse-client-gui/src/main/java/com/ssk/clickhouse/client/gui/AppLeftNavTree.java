package com.ssk.clickhouse.client.gui;

import com.ssk.clickhouse.db.model.ClientConnectionConfig;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

public class AppLeftNavTree {

    private Application app;
    private JTree tree;
    private DefaultTreeModel model;

    AppLeftNavTree(Application app) {
        this.app = app;
        buildUI();
    }

    JTree buildUI() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Connections");
        List<ClientConnectionConfig> connections = app.loadConnConfig();
        populateConnectionNodes(top, connections);
        model = new DefaultTreeModel(top);
        tree = new JTree(model);
        return tree;
    }

    private void populateConnectionNodes(DefaultMutableTreeNode top, List<ClientConnectionConfig> connections) {
        for(ClientConnectionConfig connectionConfig:connections) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(connectionConfig);
            top.add(node);
        }
    }
}
