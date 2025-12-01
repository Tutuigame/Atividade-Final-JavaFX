
package com.example.financefx.dao;

import com.example.financefx.model.Expense;
import java.sql.*;
import java.util.*;

public class ExpenseDao {

    public List<Expense> listByUser(int userId) {
        List<Expense> list = new ArrayList<>();
        try (PreparedStatement ps = Database.get().prepareStatement(
            "SELECT * FROM expenses WHERE user_id=? ORDER BY date DESC")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
            return list;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public Map<String, Double> sumByCategory(int userId, String monthIso) {
        Map<String, Double> map = new LinkedHashMap<>();
        try (PreparedStatement ps = Database.get().prepareStatement("""
            SELECT category, SUM(amount) total
            FROM expenses
            WHERE user_id=? AND substr(date,1,7)=?
            GROUP BY category
            ORDER BY total DESC
        """)) {
            ps.setInt(1, userId); ps.setString(2, monthIso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) map.put(rs.getString("category"), rs.getDouble("total"));
            return map;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public int insert(Expense e) {
        try (PreparedStatement ps = Database.get().prepareStatement(
            "INSERT INTO expenses(description, category, amount, date, user_id) VALUES(?,?,?,?,?)",
            Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getDescription());
            ps.setString(2, e.getCategory());
            ps.setDouble(3, e.getAmount());
            ps.setString(4, e.getDate());
            ps.setInt(5, e.getUserId());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            return keys.next() ? keys.getInt(1) : 0;
        } catch (SQLException ex) { throw new RuntimeException(ex); }
    }

    public void update(Expense e) {
        try (PreparedStatement ps = Database.get().prepareStatement(
            "UPDATE expenses SET description=?, category=?, amount=?, date=? WHERE id=? AND user_id=?")) {
            ps.setString(1, e.getDescription());
            ps.setString(2, e.getCategory());
            ps.setDouble(3, e.getAmount());
            ps.setString(4, e.getDate());
            ps.setInt(5, e.getId());
            ps.setInt(6, e.getUserId());
            ps.executeUpdate();
        } catch (SQLException ex) { throw new RuntimeException(ex); }
    }

    public void delete(int id, int userId) {
        try (PreparedStatement ps = Database.get().prepareStatement(
            "DELETE FROM expenses WHERE id=? AND user_id=?")) {
            ps.setInt(1, id); ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException ex) { throw new RuntimeException(ex); }
    }

    private Expense map(ResultSet rs) throws SQLException {
        return new Expense(
            rs.getInt("id"),
            rs.getString("description"),
            rs.getString("category"),
            rs.getDouble("amount"),
            rs.getString("date"),
            rs.getInt("user_id")
        );
    }
}
