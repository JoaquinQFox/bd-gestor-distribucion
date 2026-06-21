package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.Empresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    public void agregar(Empresa empresa) throws Exception {
        String sql = """
                INSERT INTO "EMPRESA" ("EmpCliCod", "EmpNom", "EmpRUC", "EmpRazSoc", "EmpEstReg")
                VALUES (?, ?, ?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, empresa.getEmpCliCod());
            ps.setString(2, empresa.getEmpNom());
            ps.setString(3, empresa.getEmpRUC());
            ps.setString(4, empresa.getEmpRazSoc());
            ps.executeUpdate();
        }
    }

    public void modificar(Empresa empresa) throws Exception {
        String sql = """
                UPDATE "EMPRESA" SET "EmpNom" = ?, "EmpRUC" = ?, "EmpRazSoc" = ?
                WHERE "EmpCliCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, empresa.getEmpNom());
            ps.setString(2, empresa.getEmpRUC());
            ps.setString(3, empresa.getEmpRazSoc());
            ps.setString(4, empresa.getEmpCliCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String empCliCod) throws Exception {
        String sql = """
                UPDATE "EMPRESA" SET "EmpEstReg" = '*' WHERE "EmpCliCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, empCliCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String empCliCod) throws Exception {
        String sql = """
                UPDATE "EMPRESA" SET "EmpEstReg" = 'I' WHERE "EmpCliCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, empCliCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String empCliCod) throws Exception {
        String sql = """
                UPDATE "EMPRESA" SET "EmpEstReg" = 'A' WHERE "EmpCliCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, empCliCod);
            ps.executeUpdate();
        }
    }

    public List<Empresa> listarTodo() throws Exception {
        List<Empresa> lista = new ArrayList<>();
        String sql = """
                SELECT "EmpCliCod", "EmpNom", "EmpRUC", "EmpRazSoc", "EmpEstReg"
                FROM "EMPRESA" ORDER BY "EmpCliCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Empresa e = new Empresa();
                e.setEmpCliCod(rs.getString("EmpCliCod"));
                e.setEmpNom(rs.getString("EmpNom"));
                e.setEmpRUC(rs.getString("EmpRUC"));
                e.setEmpRazSoc(rs.getString("EmpRazSoc"));
                e.setEmpEstReg(rs.getString("EmpEstReg"));
                lista.add(e);
            }
        }
        return lista;
    }
}