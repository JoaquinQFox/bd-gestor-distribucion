package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.Accion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccionDAO {

    public void agregar(Accion accion) throws SQLException {
        String sql = "INSERT INTO \"ACCION\" (\"AccNom\", \"AccEstReg\") VALUES (?, 'A')";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accion.getAccNom());
            ps.executeUpdate();
        }
    }

    public void modificar(Accion accion) throws SQLException {
        String sql = "UPDATE \"ACCION\" SET \"AccNom\" = ? WHERE \"AccCod\" = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accion.getAccNom());
            ps.setInt(2, accion.getAccCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(int accCod) throws SQLException {
        String sql = "UPDATE \"ACCION\" SET \"AccEstReg\" = '*' WHERE \"AccCod\" = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(int accCod) throws SQLException {
        String sql = "UPDATE \"ACCION\" SET \"AccEstReg\" = 'I' WHERE \"AccCod\" = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(int accCod) throws SQLException {
        String sql = "UPDATE \"ACCION\" SET \"AccEstReg\" = 'A' WHERE \"AccCod\" = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accCod);
            ps.executeUpdate();
        }
    }

    public List<Accion> listarTodo() throws SQLException {
        List<Accion> lista = new ArrayList<>();
        String sql = "SELECT \"AccCod\", \"AccNom\", \"AccEstReg\" FROM \"ACCION\" ORDER BY \"AccCod\" ASC";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Accion accion = new Accion();
                accion.setAccCod(rs.getInt("AccCod"));
                accion.setAccNom(rs.getString("AccNom"));
                accion.setAccEstReg(rs.getString("AccEstReg"));
                lista.add(accion);
            }
        }
        return lista;
    }
}