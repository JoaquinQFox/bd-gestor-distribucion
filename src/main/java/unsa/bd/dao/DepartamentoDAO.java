package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.Departamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO {

    public void agregar(Departamento departamento) throws SQLException {
        String sql = """
                INSERT INTO "DEPARTAMENTO" ("DepCod", "DepNom", "DepEstReg") VALUES (?, ?, 'A')""";
        try (Connection conn = ConexionDB.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, departamento.getDepCod());
            ps.setString(2, departamento.getDepNom());
            ps.executeUpdate();
        }
    }

    public void modificar(Departamento departamento) throws SQLException {
        String sql = """
                UPDATE "DEPARTAMENTO" SET "DepNom" = ? WHERE "DepCod" = ?""";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, departamento.getDepNom());
            ps.setString(2, departamento.getDepCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String depCod) throws SQLException {
        String sql = """
                UPDATE "DEPARTAMENTO" SET "DepEstReg" = '*' WHERE "DepCod" = ?""";
        try (Connection conn = ConexionDB.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, depCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String depCod) throws SQLException {
        String sql = "UPDATE \"DEPARTAMENTO\" SET \"DepEstReg\" = 'I' WHERE \"DepCod\" = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, depCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String depCod) throws SQLException {
        String sql = """
                UPDATE "DEPARTAMENTO" SET "DepEstReg" = 'A' WHERE "DepCod" = ?""";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, depCod);
            ps.executeUpdate();
        }
    }

    public List<Departamento> listarTodo() throws SQLException {
        List<Departamento> lista = new ArrayList<>();
        String sql = """
                SELECT "DepCod", "DepNom", "DepEstReg" FROM "DEPARTAMENTO" ORDER BY "DepCod" ASC""";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Departamento dep = new Departamento();
                dep.setDepCod(rs.getString("DepCod"));
                dep.setDepNom(rs.getString("DepNom"));
                dep.setDepEstReg(rs.getString("DepEstReg"));
                lista.add(dep);
            }
        }
        return lista;
    }
}