package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.Cargo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CargoDAO {

    public void agregar(Cargo cargo) throws SQLException {
        String sql = "INSERT INTO \"CARGO\" (\"CarNom\", \"CarEstReg\") VALUES (?, 'A')";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cargo.getCarNom());
            ps.executeUpdate();
        }
    }

    public void modificar(Cargo cargo) throws SQLException {
        String sql = "UPDATE \"CARGO\" SET \"CarNom\" = ? WHERE \"CarCod\" = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cargo.getCarNom());
            ps.setInt(2, cargo.getCarCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(int carCod) throws SQLException {
        String sql = "UPDATE \"CARGO\" SET \"CarEstReg\" = '*' WHERE \"CarCod\" = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(int carCod) throws SQLException {
        String sql = "UPDATE \"CARGO\" SET \"CarEstReg\" = 'I' WHERE \"CarCod\" = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(int carCod) throws SQLException {
        String sql = "UPDATE \"CARGO\" SET \"CarEstReg\" = 'A' WHERE \"CarCod\" = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carCod);
            ps.executeUpdate();
        }
    }

    public List<Cargo> listarTodo() throws SQLException {
        List<Cargo> lista = new ArrayList<>();
        String sql = "SELECT \"CarCod\", \"CarNom\", \"CarEstReg\" FROM \"CARGO\" ORDER BY \"CarCod\" ASC";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cargo cargo = new Cargo();
                cargo.setCarCod(rs.getInt("CarCod"));
                cargo.setCarNom(rs.getString("CarNom"));
                cargo.setCarEstReg(rs.getString("CarEstReg"));
                lista.add(cargo);
            }
        }
        return lista;
    }
}