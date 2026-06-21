package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.CompraCab;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CompraCabDAO {

    public void agregar(CompraCab compraCab) throws Exception {
        String sql = """
                INSERT INTO "COMPRA_CAB" (
                    "ComCabNum", "ComCabPrvCod", "ComCabAlmCod", 
                    "ComCabDia", "ComCabMes", "ComCabYea", 
                    "ComCabImp", "ComCabEstReg"
                ) VALUES (?, ?, ?, ?, ?, ?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, compraCab.getComCabNum());
            ps.setString(2, compraCab.getComCabPrvCod());
            ps.setString(3, compraCab.getComCabAlmCod());
            ps.setInt(4, compraCab.getComCabDia());
            ps.setInt(5, compraCab.getComCabMes());
            ps.setInt(6, compraCab.getComCabYea());
            ps.setBigDecimal(7, compraCab.getComCabImp());
            ps.executeUpdate();
        }
    }

    public void modificar(CompraCab compraCab) throws Exception {
        String sql = """
                UPDATE "COMPRA_CAB" SET 
                    "ComCabPrvCod" = ?, "ComCabAlmCod" = ?, 
                    "ComCabDia" = ?, "ComCabMes" = ?, "ComCabYea" = ?, 
                    "ComCabImp" = ?
                WHERE "ComCabNum" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, compraCab.getComCabPrvCod());
            ps.setString(2, compraCab.getComCabAlmCod());
            ps.setInt(3, compraCab.getComCabDia());
            ps.setInt(4, compraCab.getComCabMes());
            ps.setInt(5, compraCab.getComCabYea());
            ps.setBigDecimal(6, compraCab.getComCabImp());
            ps.setString(7, compraCab.getComCabNum());
            ps.executeUpdate();
        }
    }

    public void eliminar(String comCabNum) throws Exception {
        String sql = """
                UPDATE "COMPRA_CAB" SET "ComCabEstReg" = '*' WHERE "ComCabNum" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, comCabNum);
            ps.executeUpdate();
        }
    }

    public void inactivar(String comCabNum) throws Exception {
        String sql = """
                UPDATE "COMPRA_CAB" SET "ComCabEstReg" = 'I' WHERE "ComCabNum" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, comCabNum);
            ps.executeUpdate();
        }
    }

    public void reactivar(String comCabNum) throws Exception {
        String sql = """
                UPDATE "COMPRA_CAB" SET "ComCabEstReg" = 'A' WHERE "ComCabNum" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, comCabNum);
            ps.executeUpdate();
        }
    }

    public List<CompraCab> listarTodo() throws Exception {
        List<CompraCab> lista = new ArrayList<>();
        String sql = """
                SELECT "ComCabNum", "ComCabPrvCod", "ComCabAlmCod", 
                       "ComCabDia", "ComCabMes", "ComCabYea", 
                       "ComCabImp", "ComCabEstReg"
                FROM "COMPRA_CAB" ORDER BY "ComCabNum" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CompraCab c = new CompraCab();
                c.setComCabNum(rs.getString("ComCabNum"));
                c.setComCabPrvCod(rs.getString("ComCabPrvCod"));
                c.setComCabAlmCod(rs.getString("ComCabAlmCod"));
                c.setComCabDia(rs.getInt("ComCabDia"));
                c.setComCabMes(rs.getInt("ComCabMes"));
                c.setComCabYea(rs.getInt("ComCabYea"));
                c.setComCabImp(rs.getBigDecimal("ComCabImp"));
                c.setComCabEstReg(rs.getString("ComCabEstReg"));
                lista.add(c);
            }
        }
        return lista;
    }
}