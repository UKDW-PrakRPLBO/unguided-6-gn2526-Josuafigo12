package org.rplbo.app.Manager;

import org.rplbo.app.Data.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private Connection connection;

    public UserManager(Connection connection) {
        this.connection = connection;
    }

    public List<User> getUsersByRole(String role) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM users WHERE role = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                userList.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error get users: " + e.getMessage());
        }
        return userList;
    }

    public boolean registerUser(String username, String password, String email, String role) {
        String query = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setString(4, role);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error registrasi: " + e.getMessage());
            return false;
        }
    }

    public boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // True jika ditemukan
        } catch (SQLException e) {
            System.err.println("Error login: " + e.getMessage());
            return false;
        }
    }

    public String getUserRole(String username) {
        String query = "SELECT role FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getString("role");
            }
        } catch (SQLException e) {
            System.err.println("Error ambil role: " + e.getMessage());
        }
        return "guest";
    }
}