package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.RepVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RepVentaDAO {

    public void agregar(RepVenta repVenta) throws Exception {
        String sql = """
                INSERT INTO "REP_VENTA" ("RepVenCod", "RepVenNom", "RepVenEda", "RepVenOfiCod", "RepVenCarCod", "RepVenObjVen", "RepVenEstReg")
                VALUES (?, ?, ?, ?, ?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, repVenta.getRepVenCod());
            ps.setString(2, repVenta.getRepVenNom());
            ps.setInt(3, repVenta.getRepVenEda());
            ps.setString(4, repVenta.getRepVenOfiCod());
            ps.setString(5, repVenta.getRepVenCarCod());
            ps.setBigDecimal(6, repVenta.getRepVenObjVen());
            ps.executeUpdate();
        }
    }

    public void modificar(RepVenta repVenta) throws Exception {
        String sql = """
                UPDATE "REP_VENTA" SET "RepVenNom" = ?, "RepVenEda" = ?, "RepVenOfiCod" = ?, "RepVenCarCod" = ?, "RepVenObjVen" = ?
                WHERE "RepVenCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, repVenta.getRepVenNom());
            ps.setInt(2, repVenta.getRepVenEda());
            ps.setString(3, repVenta.getRepVenOfiCod());
            ps.setString(4, repVenta.getRepVenCarCod());
            ps.setBigDecimal(5, repVenta.getRepVenObjVen());
            ps.setString(6, repVenta.getRepVenCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String repVenCod) throws Exception {
        String sql = """
                UPDATE "REP_VENTA" SET "RepVenEstReg" = '*' WHERE "RepVenCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, repVenCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String repVenCod) throws Exception {
        String sql = """
                UPDATE "REP_VENTA" SET "RepVenEstReg" = 'I' WHERE "RepVenCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, repVenCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String repVenCod) throws Exception {
        String sql = """
                UPDATE "REP_VENTA" SET "RepVenEstReg" = 'A' WHERE "RepVenCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, repVenCod);
            ps.executeUpdate();
        }
    }

    public List<RepVenta> listarTodo() throws Exception {
        List<RepVenta> lista = new ArrayList<>();
        String sql = """
                SELECT "RepVenCod", "RepVenNom", "RepVenEda", "RepVenOfiCod", "RepVenCarCod", "RepVenObjVen", "RepVenEstReg"
                FROM "REP_VENTA" ORDER BY "RepVenCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RepVenta r = new RepVenta();
                r.setRepVenCod(rs.getString("RepVenCod"));
                r.setRepVenNom(rs.getString("RepVenNom"));
                r.setRepVenEda(rs.getInt("RepVenEda"));
                r.setRepVenOfiCod(rs.getString("RepVenOfiCod"));
                r.setRepVenCarCod(rs.getString("RepVenCarCod"));
                r.setRepVenObjVen(rs.getBigDecimal("RepVenObjVen"));
                r.setRepVenEstReg(rs.getString("RepVenEstReg"));
                lista.add(r);
            }
        }
        return lista;
    }
}
