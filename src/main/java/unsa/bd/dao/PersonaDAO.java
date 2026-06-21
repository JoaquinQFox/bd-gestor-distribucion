package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    public void agregar(Persona persona) throws Exception {
        String sql = """
                INSERT INTO "PERSONA" ("PerCliCod", "PerNom", "PerTipDocCod", "PerNumDoc", "PerEstReg")
                VALUES (?, ?, ?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, persona.getPerCliCod());
            ps.setString(2, persona.getPerNom());
            ps.setString(3, persona.getPerTipDocCod());
            ps.setString(4, persona.getPerNumDoc());
            ps.executeUpdate();
        }
    }

    public void modificar(Persona persona) throws Exception {
        String sql = """
                UPDATE "PERSONA" SET "PerNom" = ?, "PerTipDocCod" = ?, "PerNumDoc" = ?
                WHERE "PerCliCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, persona.getPerNom());
            ps.setString(2, persona.getPerTipDocCod());
            ps.setString(3, persona.getPerNumDoc());
            ps.setString(4, persona.getPerCliCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String perCliCod) throws Exception {
        String sql = """
                UPDATE "PERSONA" SET "PerEstReg" = '*' WHERE "PerCliCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, perCliCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String perCliCod) throws Exception {
        String sql = """
                UPDATE "PERSONA" SET "PerEstReg" = 'I' WHERE "PerCliCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, perCliCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String perCliCod) throws Exception {
        String sql = """
                UPDATE "PERSONA" SET "PerEstReg" = 'A' WHERE "PerCliCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, perCliCod);
            ps.executeUpdate();
        }
    }

    public List<Persona> listarTodo() throws Exception {
        List<Persona> lista = new ArrayList<>();
        String sql = """
                SELECT "PerCliCod", "PerNom", "PerTipDocCod", "PerNumDoc", "PerEstReg"
                FROM "PERSONA" ORDER BY "PerCliCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Persona p = new Persona();
                p.setPerCliCod(rs.getString("PerCliCod"));
                p.setPerNom(rs.getString("PerNom"));
                p.setPerTipDocCod(rs.getString("PerTipDocCod"));
                p.setPerNumDoc(rs.getString("PerNumDoc"));
                p.setPerEstReg(rs.getString("PerEstReg"));
                lista.add(p);
            }
        }
        return lista;
    }
}
