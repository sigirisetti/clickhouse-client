package com.ssk.clickhouse.com.ssk.clickhouse.dao;

import com.ssk.clickhouse.db.model.Column;
import com.ssk.clickhouse.db.model.Table;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableDao {

    private final String SELECT_TABLE_DATA_SQL = "SELECT * FROM %s.%s LIMIT 1000";

    private final ClickHouseDataSource dataSource;

    public TableDao(ClickHouseDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<List<Object>> getTableData(Table table) {
        List<List<Object>> tableData = new ArrayList<>(1000);
        try (ClickHouseConnection connection = dataSource.getConnection()) {
            String sql = String.format(SELECT_TABLE_DATA_SQL, table.getDatabase(), table.getName());
            System.out.println("Firing sql : " + sql);
            PreparedStatement ps = connection.prepareStatement(sql);
            int n = table.getColumn().size();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    List<Object> row = new ArrayList<>(n);
                    tableData.add(row);
                    for(Column c : table.getColumn()) {
                        row.add(rs.getObject(c.getName()));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableData;
    }
}
