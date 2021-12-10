package com.ssk.clickhouse.client.model;

import com.ssk.clickhouse.db.model.ClientConnectionConfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "connections")
@XmlAccessorType(XmlAccessType.FIELD)
public class AllConnections {

    @XmlElement(name = "client-conn-config")
    private List<ClientConnectionConfig> allConnConfigs;

    public void setAllConnConfigs(List<ClientConnectionConfig> allConnConfigs) {
        this.allConnConfigs = allConnConfigs;
    }

    public List<ClientConnectionConfig> getAllConnConfigs() {
        return allConnConfigs;
    }
}
