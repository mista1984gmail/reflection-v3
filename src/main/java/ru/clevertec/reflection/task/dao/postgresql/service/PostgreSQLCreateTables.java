package ru.clevertec.reflection.task.dao.postgresql.service;

import ru.clevertec.reflection.task.dao.postgresql.JDBCPostgreSQLCreateTable;
import ru.clevertec.reflection.task.dao.tables.JDBCCreateTableClient;

import java.util.HashMap;
import java.util.Map;

public class PostgreSQLCreateTables {

    private Map<String, String> createTables = new HashMap<>();

    public void createTablesInDataBase() {
        createTables.put(JDBCCreateTableClient.ACCOUNT_TABLE_NAME, JDBCCreateTableClient.TABLE_ACCOUNT_SQL_CREATE);
        JDBCPostgreSQLCreateTable jdbcPostgreSQLCreateTable = new JDBCPostgreSQLCreateTable();
        jdbcPostgreSQLCreateTable.createTables(createTables);
    }

}
