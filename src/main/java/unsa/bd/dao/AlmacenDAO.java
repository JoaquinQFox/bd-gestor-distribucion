package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.Almacen;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AlmacenDAO {

    public void agregar(Almacen almacen) throws Exception {
        String sql = """
                INSERT INTO "ALMACEN" ("AlmCod", "AlmNom", "AlmDir", "AlmCiuCod", "AlmRegCod", "AlmEstReg")
                VALUES (?, ?, ?, ?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, almacen.getAlmCod());
            ps.setString(2, almacen.getAlmNom());
            ps.setString(3, almacen.getAlmDir());
            ps.setString(4, almacen.getAlmCiuCod());
            ps.setString(5, almacen.getAlmRegCod());
            ps.executeUpdate();
        }
    }

    public void modificar(Almacen almacen) throws Exception {
        String sql = """
                UPDATE "ALMACEN" SET "AlmNom" = ?, "AlmDir" = ?, "AlmCiuCod" = ?, "AlmRegCod" = ?
                WHERE "AlmCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, almacen.getAlmNom());
            ps.setString(2, almacen.getAlmDir());
            ps.setString(3, almacen.getAlmCiuCod());
            ps.setString(4, almacen.getAlmRegCod());
            ps.setString(5, almacen.getAlmCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String almCod) throws Exception {
        String sql = """
                UPDATE "ALMACEN" SET "AlmEstReg" = '*' WHERE "AlmCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, almCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String almCod) throws Exception {
        String sql = """
                UPDATE "ALMACEN" SET "AlmEstReg" = 'I' WHERE "AlmCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, almCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String almCod) throws Exception {
        String sql = """
                UPDATE "ALMACEN" SET "AlmEstReg" = 'A' WHERE "AlmCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, almCod);
            ps.executeUpdate();
        }
    }

    public List<Almacen> listarTodo() throws Exception {
        List<Almacen> lista = new ArrayList<>();
        String sql = """
                SELECT "AlmCod", "AlmNom", "AlmDir", "AlmCiuCod", "AlmRegCod", "AlmEstReg"
                FROM "ALMACEN" ORDER BY "AlmCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Almacen a = new Almacen();
                a.setAlmCod(rs.getString("AlmCod"));
                a.setAlmNom(rs.getString("AlmNom"));
                a.setAlmDir(rs.getString("AlmDir"));
                a.setAlmCiuCod(rs.getString("AlmCiuCod"));
                a.setAlmRegCod(rs.getString("AlmRegCod"));
                a.setAlmEstReg(rs.getString("AlmEstReg"));
                lista.add(a);
            }
        }
        return lista;
    }
}