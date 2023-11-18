package ru.clevertec.reflection.task.dao.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class JDBCPostgreSQLCreateTable {

    public void createTables(Map<String, String> sqls) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = JDBCPostgreSQLConnection.getConnection();
            stmt = conn.createStatement();
            for (Map.Entry entry : sqls.entrySet()) {
                stmt.executeUpdate(entry.getValue()
                                        .toString());
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }

}