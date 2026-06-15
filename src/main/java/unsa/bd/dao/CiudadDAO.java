package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.Ciudad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CiudadDAO {

    public void agregar(Ciudad ciudad) throws SQLException {
        String sql = "INSERT INTO \"CIUDAD\" (\"CiuNom\", \"CiuRegCod\", \"CiuEstReg\") VALUES (?, ?, 'A')";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ciudad.getCiuNom());
            ps.setInt(2, ciudad.getCiuRegCod());
            ps.executeUpdate();
        }
    }

    public void modificar(Ciudad ciudad) throws SQLException {
        String sql = "UPDATE \"CIUDAD\" SET \"CiuNom\" = ?, \"CiuRegCod\" = ? WHERE \"CiuCod\" = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ciudad.getCiuNom());
            ps.setInt(2, ciudad.getCiuRegCod());
            ps.setInt(3, ciudad.getCiuCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(int ciuCod) throws SQLException {
        String sql = "UPDATE \"CIUDAD\" SET \"CiuEstReg\" = '*' WHERE \"CiuCod\" = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ciuCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(int ciuCod) throws SQLException {
        String sql = "UPDATE \"CIUDAD\" SET \"CiuEstReg\" = 'I' WHERE \"CiuCod\" = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ciuCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(int ciuCod) throws SQLException {
        String sql = "UPDATE \"CIUDAD\" SET \"CiuEstReg\" = 'A' WHERE \"CiuCod\" = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ciuCod);
            ps.executeUpdate();
        }
    }

    public List<Ciudad> listarTodo() throws SQLException {
        List<Ciudad> lista = new ArrayList<>();
        String sql = "SELECT \"CiuCod\", \"CiuNom\", \"CiuRegCod\", \"CiuEstReg\" FROM \"CIUDAD\" ORDER BY \"CiuCod\" ASC";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Ciudad ciu = new Ciudad();
                ciu.setCiuCod(rs.getInt("CiuCod"));
                ciu.setCiuNom(rs.getString("CiuNom"));
                ciu.setCiuRegCod(rs.getInt("CiuRegCod"));
                ciu.setCiuEstReg(rs.getString("CiuEstReg"));
                lista.add(ciu);
            }
        }
        return lista;
    }
}