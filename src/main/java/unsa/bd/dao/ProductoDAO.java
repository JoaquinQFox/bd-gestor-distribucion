package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public void agregar(Producto producto) throws Exception {
        String sql = """
                INSERT INTO "PRODUCTO" ("ProCod", "ProFabCod", "ProDes", "ProPre", "ProClaCod", "ProUniMed", "ProEstReg")
                VALUES (?, ?, ?, ?, ?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, producto.getProCod());
            ps.setString(2, producto.getProFabCod());
            ps.setString(3, producto.getProDes());
            ps.setBigDecimal(4, producto.getProPre());
            ps.setString(5, producto.getProClaCod());
            ps.setString(6, producto.getProUniMed());
            ps.executeUpdate();
        }
    }

    public void modificar(Producto producto) throws Exception {
        String sql = """
                UPDATE "PRODUCTO" SET "ProFabCod" = ?, "ProDes" = ?, "ProPre" = ?, "ProClaCod" = ?, "ProUniMed" = ?
                WHERE "ProCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, producto.getProFabCod());
            ps.setString(2, producto.getProDes());
            ps.setBigDecimal(3, producto.getProPre());
            ps.setString(4, producto.getProClaCod());
            ps.setString(5, producto.getProUniMed());
            ps.setString(6, producto.getProCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String proCod) throws Exception {
        String sql = """
                UPDATE "PRODUCTO" SET "ProEstReg" = '*' WHERE "ProCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, proCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String proCod) throws Exception {
        String sql = """
                UPDATE "PRODUCTO" SET "ProEstReg" = 'I' WHERE "ProCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, proCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String proCod) throws Exception {
        String sql = """
                UPDATE "PRODUCTO" SET "ProEstReg" = 'A' WHERE "ProCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, proCod);
            ps.executeUpdate();
        }
    }

    public List<Producto> listarTodo() throws Exception {
        List<Producto> lista = new ArrayList<>();
        String sql = """
                SELECT "ProCod", "ProFabCod", "ProDes", "ProPre", "ProClaCod", "ProUniMed", "ProEstReg"
                FROM "PRODUCTO" ORDER BY "ProCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setProCod(rs.getString("ProCod"));
                p.setProFabCod(rs.getString("ProFabCod"));
                p.setProDes(rs.getString("ProDes"));
                p.setProPre(rs.getBigDecimal("ProPre"));
                p.setProClaCod(rs.getString("ProClaCod"));
                p.setProUniMed(rs.getString("ProUniMed"));
                p.setProEstReg(rs.getString("ProEstReg"));
                lista.add(p);
            }
        }
        return lista;
    }
}