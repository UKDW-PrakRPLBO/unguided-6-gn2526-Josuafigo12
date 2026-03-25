package org.rplbo.app.Manager;

import org.rplbo.app.Data.RekamMedis;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RekamMedisManager {
    private Connection connection;

    public RekamMedisManager(Connection connection) {
        this.connection = connection;
    }

    public boolean tambahRekamMedis(String namaDokter, String namaPasien, String diagnosis, String tanggal) {
        String query = "INSERT INTO rekam_medis (nama_dokter, nama_pasien, diagnosis, tanggal) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, namaDokter);
            stmt.setString(2, namaPasien);
            stmt.setString(3, diagnosis);
            stmt.setString(4, tanggal);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insert RM: " + e.getMessage());
            return false;
        }
    }

    public List<RekamMedis> getAllRekamMedis() {
        List<RekamMedis> rekamMedisList = new ArrayList<>();
        String query = "SELECT * FROM rekam_medis";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                RekamMedis rm = mapResultSetToRekamMedis(rs);
                rekamMedisList.add(rm);
            }
        } catch (SQLException e) {
            System.err.println("Error read all: " + e.getMessage());
        }
        return rekamMedisList;
    }

    public boolean editRekamMedis(int idRekamMedis, String diagnosisBaru) {
        String query = "UPDATE rekam_medis SET diagnosis = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, diagnosisBaru);
            stmt.setInt(2, idRekamMedis);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update: " + e.getMessage());
            return false;
        }
    }

    public boolean hapusRekamMedis(int idRekamMedis) {
        String query = "DELETE FROM rekam_medis WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idRekamMedis);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error delete: " + e.getMessage());
            return false;
        }
    }

    public List<RekamMedis> cariRekamMedisPasien(String nama) {
        List<RekamMedis> resultList = new ArrayList<>();
        String query = "SELECT * FROM rekam_medis WHERE nama_pasien LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + nama + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultList.add(mapResultSetToRekamMedis(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error search: " + e.getMessage());
        }
        return resultList;
    }

    // Helper method untuk memetakan ResultSet ke Objek
    private RekamMedis mapResultSetToRekamMedis(ResultSet rs) throws SQLException {
        RekamMedis rm = new RekamMedis();
        rm.setId(rs.getInt("id"));
        rm.setNamaPasien(rs.getString("nama_pasien"));
        rm.setNamaDokter(rs.getString("nama_dokter"));
        rm.setDiagnosis(rs.getString("diagnosis"));
        rm.setTanggal(rs.getString("tanggal"));
        return rm;
    }
}