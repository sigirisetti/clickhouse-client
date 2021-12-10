package com.ssk.clickhouse.client.gui;

import com.ssk.clickhouse.client.model.ConnConfigPersistenceManager;
import com.ssk.clickhouse.com.ssk.clickhouse.dao.DbMetaDataDao;
import com.ssk.clickhouse.conn.ClickhouseDatasourceInitializer;
import com.ssk.clickhouse.db.model.ClientConnectionConfig;
import com.ssk.clickhouse.db.model.Database;
import ru.yandex.clickhouse.ClickHouseDataSource;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {

    private MainFrame mainFrame;
    private ConnectionDialog connectionDialog;
    private Map<ClientConnectionConfig, ClickHouseDataSource> connectionMap = new HashMap<>();
    private ConnConfigPersistenceManager connConfigPersistenceManager = new ConnConfigPersistenceManager();

    private void createAndShowGUI() {
        //Create and set up the window.
        mainFrame = new MainFrame(this, "Clickhouse Client");
        connectionDialog = new ConnectionDialog(this, mainFrame, "ssk");
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Application application = new Application();
                application.createAndShowGUI();
            }
        });
    }

    public void showConnectionDialog() {
        connectionDialog.setVisible(true);
    }

    public void connect(ClientConnectionConfig clientConnectionConfig) {
        if (!connectionMap.containsKey(clientConnectionConfig)) {
            connectionMap.put(clientConnectionConfig, ClickhouseDatasourceInitializer.createDataSource(clientConnectionConfig));
            List<Database> databases = new DbMetaDataDao(connectionMap.get(clientConnectionConfig)).getDatabases();

        }
    }

    public void saveConnConfig(List<ClientConnectionConfig> allConnConfigs) {
        connConfigPersistenceManager.saveConnConfig(allConnConfigs);
    }

    public List<ClientConnectionConfig> loadConnConfig() {
        return connConfigPersistenceManager.loadConnConfig();
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }
}
