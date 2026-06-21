package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.ClasificacionProducto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClasificacionProductoDAO {
    public void agregar(ClasificacionProducto claPro) throws Exception {
        String sql = """
                INSERT INTO "CLASIFICACION_PRODUCTO" ("ClaProCod", "ClaProNom", "ClaProEstReg") VALUES (?, ?, 'A')""";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, claPro.getClaProCod());
            ps.setString(2, claPro.getClaProNom());
            ps.executeUpdate();
        }
    }

    public void modificar(ClasificacionProducto claPro) throws Exception {
        String sql = """
                UPDATE "CLASIFICACION_PRODUCTO" SET "ClaProNom" = ? WHERE "ClaProCod" = ?""";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, claPro.getClaProNom());
            ps.setString(2, claPro.getClaProCod());

            ps.executeUpdate();
        }
    }
    public void eliminar(String claProCod) throws Exception {
        String sql = """
                UPDATE "CLASIFICACION_PRODUCTO" SET "ClaProEstReg" = '*' WHERE "ClaProCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, claProCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String claProCod) throws Exception {
        String sql = """
                UPDATE "CLASIFICACION_PRODUCTO" SET "ClaProEstReg" = 'I' WHERE "ClaProCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, claProCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String claProCod) throws Exception {
        String sql = """
                UPDATE "CLASIFICACION_PRODUCTO" SET "ClaProEstReg" = 'A' WHERE "ClaProCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, claProCod);
            ps.executeUpdate();
        }
    }

    public List<ClasificacionProducto> listarTodo() throws Exception {
        List<ClasificacionProducto> lista = new ArrayList<>();
        String sql = """
                SELECT "ClaProCod", "ClaProNom", "ClaProEstReg" FROM "CLASIFICACION_PRODUCTO" ORDER BY "ClaProCod" ASC""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ClasificacionProducto c = new ClasificacionProducto();
                c.setClaProCod(rs.getString("ClaProCod"));
                c.setClaProNom(rs.getString("ClaProNom"));
                c.setClaProEstReg(rs.getString("ClaProEstReg"));
                lista.add(c);
            }
        }
        return lista;
    }
}
