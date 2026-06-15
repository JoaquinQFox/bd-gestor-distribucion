package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.EscalaCredito;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EscalaCreditoDAO {

    public void agregar(EscalaCredito escCre) throws Exception {
        String sql = """
                INSERT INTO "ESCALA_CREDITO" ("EscCreNom", "EscCreLimCre", "EscCreEstReg") VALUES (?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, escCre.getEscCreNom());
            ps.setBigDecimal(2, escCre.getEscCreLimCre());
            ps.executeUpdate();
        }
    }

    public void modificar(EscalaCredito escCre) throws Exception {
        String sql = """
                UPDATE "ESCALA_CREDITO" SET "EscCreNom" = ?, "EscCreLimCre" = ? WHERE "EscCreCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, escCre.getEscCreNom());
            ps.setBigDecimal(2, escCre.getEscCreLimCre());
            ps.setInt(3, escCre.getEscCreCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(int escCreCod) throws Exception {
        String sql = """
                UPDATE "ESCALA_CREDITO" SET "EscCreEstReg" = '*' WHERE "EscCreCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, escCreCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(int escCreCod) throws Exception {
        String sql = """
                UPDATE "ESCALA_CREDITO" SET "EscCreEstReg" = 'I' WHERE "EscCreCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, escCreCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(int escCreCod) throws Exception {
        String sql = """
                UPDATE "ESCALA_CREDITO" SET "EscCreEstReg" = 'A' WHERE "EscCreCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, escCreCod);
            ps.executeUpdate();
        }
    }

    public List<EscalaCredito> listarTodo() throws Exception {
        List<EscalaCredito> lista = new ArrayList<>();
        String sql = """
                SELECT "EscCreCod", "EscCreNom", "EscCreLimCre", "EscCreEstReg" FROM "ESCALA_CREDITO" ORDER BY "EscCreCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EscalaCredito e = new EscalaCredito();
                e.setEscCreCod(rs.getInt("EscCreCod"));
                e.setEscCreNom(rs.getString("EscCreNom"));
                e.setEscCreLimCre(rs.getBigDecimal("EscCreLimCre"));
                e.setEscCreEstReg(rs.getString("EscCreEstReg"));
                lista.add(e);
            }
        }
        return lista;
    }
}