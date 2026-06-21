package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.CompraDet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CompraDetDAO {

    public void agregar(CompraDet compraDet) throws Exception {
        String sql = """
                INSERT INTO "COMPRA_DET" ("ComDetCabNum", "ComDetProCod", "ComDetCan", "ComDetPre", "ComDetTot", "ComDetEstReg")
                VALUES (?, ?, ?, ?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, compraDet.getComDetCabNum());
            ps.setString(2, compraDet.getComDetProCod());
            ps.setInt(3, compraDet.getComDetCan());
            ps.setBigDecimal(4, compraDet.getComDetPre());
            ps.setBigDecimal(5, compraDet.getComDetTot());
            ps.executeUpdate();
        }
    }

    public void modificar(CompraDet compraDet) throws Exception {
        String sql = """
                UPDATE "COMPRA_DET" SET "ComDetCan" = ?, "ComDetPre" = ?, "ComDetTot" = ?
                WHERE "ComDetCabNum" = ? AND "ComDetProCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, compraDet.getComDetCan());
            ps.setBigDecimal(2, compraDet.getComDetPre());
            ps.setBigDecimal(3, compraDet.getComDetTot());
            ps.setString(4, compraDet.getComDetCabNum());
            ps.setString(5, compraDet.getComDetProCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String comDetCabNum, String comDetProCod) throws Exception {
        String sql = """
                UPDATE "COMPRA_DET" SET "ComDetEstReg" = '*' WHERE "ComDetCabNum" = ? AND "ComDetProCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, comDetCabNum);
            ps.setString(2, comDetProCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String comDetCabNum, String comDetProCod) throws Exception {
        String sql = """
                UPDATE "COMPRA_DET" SET "ComDetEstReg" = 'I' WHERE "ComDetCabNum" = ? AND "ComDetProCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, comDetCabNum);
            ps.setString(2, comDetProCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String comDetCabNum, String comDetProCod) throws Exception {
        String sql = """
                UPDATE "COMPRA_DET" SET "ComDetEstReg" = 'A' WHERE "ComDetCabNum" = ? AND "ComDetProCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, comDetCabNum);
            ps.setString(2, comDetProCod);
            ps.executeUpdate();
        }
    }

    public List<CompraDet> listarTodo() throws Exception {
        List<CompraDet> lista = new ArrayList<>();
        String sql = """
                SELECT "ComDetCabNum", "ComDetProCod", "ComDetCan", "ComDetPre", "ComDetTot", "ComDetEstReg"
                FROM "COMPRA_DET" ORDER BY "ComDetCabNum" ASC, "ComDetProCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CompraDet c = new CompraDet();
                c.setComDetCabNum(rs.getString("ComDetCabNum"));
                c.setComDetProCod(rs.getString("ComDetProCod"));
                c.setComDetCan(rs.getInt("ComDetCan"));
                c.setComDetPre(rs.getBigDecimal("ComDetPre"));
                c.setComDetTot(rs.getBigDecimal("ComDetTot"));
                c.setComDetEstReg(rs.getString("ComDetEstReg"));
                lista.add(c);
            }
        }
        return lista;
    }
}