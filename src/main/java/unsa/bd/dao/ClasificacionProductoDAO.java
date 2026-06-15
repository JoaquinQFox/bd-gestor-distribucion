package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.ClasificacionProducto;
import unsa.bd.model.Region;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClasificacionProductoDAO {
    public void agregar(ClasificacionProducto claPro) throws Exception {
        String sql = """
                INSERT INTO "CLASIFICACION_PRODUCTO" ("ClaProNom", "ClaProEstReg") VALUES (?, 'A')""";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, claPro.getClaProNom());
            ps.executeUpdate();
        }
    }

    public void modificar(ClasificacionProducto claPro) throws Exception {
        String sql = """
                UPDATE "CLASIFICACION_PRODUCTO" SET "ClaProNom" = ? WHERE "ClaProCod" = ?""";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, claPro.getClaProNom());
            ps.setInt(2, claPro.getClaProCod());

            ps.executeUpdate();
        }
    }
    public void eliminar(int claProCod) throws Exception {
        String sql = """
                UPDATE "CLASIFICACION_PRODUCTO" SET "ClaProEstReg" = '*' WHERE "ClaProCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, claProCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(int claProCod) throws Exception {
        String sql = """
                UPDATE "CLASIFICACION_PRODUCTO" SET "ClaProEstReg" = 'I' WHERE "ClaProCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, claProCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(int claProCod) throws Exception {
        String sql = """
                UPDATE "CLASIFICACION_PRODUCTO" SET "ClaProEstReg" = 'A' WHERE "ClaProCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, claProCod);
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
                c.setClaProCod(rs.getInt("ClaProCod"));
                c.setClaProNom(rs.getString("ClaProNom"));
                c.setClaProEstReg(rs.getString("ClaProEstReg"));
                lista.add(c);
            }
        }
        return lista;
    }
}
