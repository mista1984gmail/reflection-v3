package ru.clevertec.reflection.task.dao.tables;

public class JDBCCreateTableClient {

    public final static String ACCOUNT_TABLE_NAME = "clients";
    public final static String TABLE_ACCOUNT_SQL_CREATE = "CREATE TABLE if not exists clients " +
            "(id SERIAL not NULL, " +
            " first_name VARCHAR(255), " +
            " last_name VARCHAR(255), " +
            " email VARCHAR(255), " +
            " telephone VARCHAR(255), " +
            " birthday date, " +
            " registration_date TIMESTAMP, " +
            " PRIMARY KEY ( id ))";

}
