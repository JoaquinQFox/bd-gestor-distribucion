package unsa.bd.modules.inventario.stock;

import unsa.bd.config.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {

    public void agregar(Stock stock) throws Exception {
        String sql = """
                INSERT INTO "STOCK" ("StoProCod", "StoAlmCod", "StoCanDis", "StoCanMin", "StoEstReg")
                VALUES (?, ?, ?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, stock.getStoProCod());
            ps.setString(2, stock.getStoAlmCod());
            ps.setInt(3, stock.getStoCanDis());
            ps.setInt(4, stock.getStoCanMin());
            ps.executeUpdate();
        }
    }

    public void modificar(Stock stock) throws Exception {
        String sql = """
                UPDATE "STOCK" SET "StoCanDis" = ?, "StoCanMin" = ?
                WHERE "StoProCod" = ? AND "StoAlmCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, stock.getStoCanDis());
            ps.setInt(2, stock.getStoCanMin());
            ps.setString(3, stock.getStoProCod());
            ps.setString(4, stock.getStoAlmCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String stoProCod, String stoAlmCod) throws Exception {
        String sql = """
                UPDATE "STOCK" SET "StoEstReg" = '*' WHERE "StoProCod" = ? AND "StoAlmCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, stoProCod);
            ps.setString(2, stoAlmCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String stoProCod, String stoAlmCod) throws Exception {
        String sql = """
                UPDATE "STOCK" SET "StoEstReg" = 'I' WHERE "StoProCod" = ? AND "StoAlmCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, stoProCod);
            ps.setString(2, stoAlmCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String stoProCod, String stoAlmCod) throws Exception {
        String sql = """
                UPDATE "STOCK" SET "StoEstReg" = 'A' WHERE "StoProCod" = ? AND "StoAlmCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, stoProCod);
            ps.setString(2, stoAlmCod);
            ps.executeUpdate();
        }
    }

    public List<Stock> listarTodo() throws Exception {
        List<Stock> lista = new ArrayList<>();
        String sql = """
                SELECT "StoProCod", "StoAlmCod", "StoCanDis", "StoCanMin", "StoEstReg"
                FROM "STOCK" ORDER BY "StoProCod", "StoAlmCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Stock s = new Stock();
                s.setStoProCod(rs.getString("StoProCod"));
                s.setStoAlmCod(rs.getString("StoAlmCod"));
                s.setStoCanDis(rs.getInt("StoCanDis"));
                s.setStoCanMin(rs.getInt("StoCanMin"));
                s.setStoEstReg(rs.getString("StoEstReg"));
                lista.add(s);
            }
        }
        return lista;
    }
}