
package com.example.financefx.dao;

import com.example.financefx.model.User;
import java.sql.*;

public class UserDao {
    public User auth(String username, String password) {
        try (PreparedStatement ps = Database.get().prepareStatement(
            "SELECT id, username FROM users WHERE username=? AND password=?")) {
            ps.setString(1, username); ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new User(rs.getInt("id"), rs.getString("username"));
            return null;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}
