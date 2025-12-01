
package com.example.financefx.dao;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:sqlite:finance.db";
    private static Connection conn;

    public static Connection get() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL);
                init();
            }
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao abrir conexão com SQLite", e);
        }
    }

    private static void init() throws SQLException {
        try (Statement st = get().createStatement()) {
            st.execute("""
                CREATE TABLE IF NOT EXISTS users(
                  id INTEGER PRIMARY KEY AUTOINCREMENT,
                  username TEXT UNIQUE NOT NULL,
                  password TEXT NOT NULL
                )
            """);
            st.execute("""
                CREATE TABLE IF NOT EXISTS expenses(
                  id INTEGER PRIMARY KEY AUTOINCREMENT,
                  description TEXT NOT NULL,
                  category TEXT NOT NULL,
                  amount REAL NOT NULL,
                  date TEXT NOT NULL,
                  user_id INTEGER NOT NULL,
                  FOREIGN KEY(user_id) REFERENCES users(id)
                )
            """);
            st.execute("""
                INSERT OR IGNORE INTO users(username, password) VALUES('admin','admin')
            """);
        }
    }
}
