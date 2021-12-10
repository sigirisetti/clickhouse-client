package com.ssk.clickhouse.client.model;

import com.ssk.clickhouse.db.model.ClientConnectionConfig;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Collections;
import java.util.List;

public class ConnConfigPersistenceManager {

    public static final String CLICKHOUSE_CLIENT_CONN_CONFIG_XML = "clickhouse-client-conn-config.xml";

    private JFrame parent;

    public void saveConnConfig(List<ClientConnectionConfig> connConfigList) {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(AllConnections.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            AllConnections allConnections = new AllConnections();
            allConnections.setAllConnConfigs(connConfigList);

            jaxbMarshaller.marshal(allConnections, System.out);

            jaxbMarshaller.marshal(allConnections, getConnConfigFilePath());
        } catch (JAXBException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Failed to save connection config: " + e.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<ClientConnectionConfig> loadConnConfig() {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(AllConnections.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            File f = getConnConfigFilePath();
            if (!f.exists()) {
                return Collections.emptyList();
            }
            //We had written this file in marshalling example
            AllConnections emps = (AllConnections) jaxbUnmarshaller.unmarshal(getConnConfigFilePath());
            return emps.getAllConnConfigs();
        } catch (JAXBException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private File getConnConfigFilePath() {
        String homeDir = System.getProperty("user.home");
        return new File(homeDir, CLICKHOUSE_CLIENT_CONN_CONFIG_XML);
    }
}
