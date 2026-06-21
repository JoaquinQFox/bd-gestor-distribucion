package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.Oficina;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OficinaDAO {

    public void agregar(Oficina oficina) throws Exception {
        String sql = """
                INSERT INTO "OFICINA" ("OfiCod", "OfiRegCod", "OfiCiuCod", "OfiObjPro", "OfiVenRea", "OfiEstReg")
                VALUES (?, ?, ?, ?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, oficina.getOfiCod());
            ps.setString(2, oficina.getOfiRegCod());
            ps.setString(3, oficina.getOfiCiuCod());
            ps.setBigDecimal(4, oficina.getOfiObjPro());
            ps.setBigDecimal(5, oficina.getOfiVenRea());
            ps.executeUpdate();
        }
    }

    public void modificar(Oficina oficina) throws Exception {
        String sql = """
                UPDATE "OFICINA" SET "OfiRegCod" = ?, "OfiCiuCod" = ?, "OfiObjPro" = ?, "OfiVenRea" = ?
                WHERE "OfiCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, oficina.getOfiRegCod());
            ps.setString(2, oficina.getOfiCiuCod());
            ps.setBigDecimal(3, oficina.getOfiObjPro());
            ps.setBigDecimal(4, oficina.getOfiVenRea());
            ps.setString(5, oficina.getOfiCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String ofiCod) throws Exception {
        String sql = """
                UPDATE "OFICINA" SET "OfiEstReg" = '*' WHERE "OfiCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ofiCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String ofiCod) throws Exception {
        String sql = """
                UPDATE "OFICINA" SET "OfiEstReg" = 'I' WHERE "OfiCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ofiCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String ofiCod) throws Exception {
        String sql = """
                UPDATE "OFICINA" SET "OfiEstReg" = 'A' WHERE "OfiCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ofiCod);
            ps.executeUpdate();
        }
    }

    public List<Oficina> listarTodo() throws Exception {
        List<Oficina> lista = new ArrayList<>();
        String sql = """
                SELECT "OfiCod", "OfiRegCod", "OfiCiuCod", "OfiObjPro", "OfiVenRea", "OfiEstReg"
                FROM "OFICINA" ORDER BY "OfiCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Oficina o = new Oficina();
                o.setOfiCod(rs.getString("OfiCod"));
                o.setOfiRegCod(rs.getString("OfiRegCod"));
                o.setOfiCiuCod(rs.getString("OfiCiuCod"));
                o.setOfiObjPro(rs.getBigDecimal("OfiObjPro"));
                o.setOfiVenRea(rs.getBigDecimal("OfiVenRea"));
                o.setOfiEstReg(rs.getString("OfiEstReg"));
                lista.add(o);
            }
        }
        return lista;
    }
}
