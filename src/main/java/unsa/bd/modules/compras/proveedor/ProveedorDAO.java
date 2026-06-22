package unsa.bd.modules.compras.proveedor;

import unsa.bd.config.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    public void agregar(Proveedor proveedor) throws Exception {
        String sql = """
                INSERT INTO "PROVEEDOR" ("PrvCod", "PrvNom", "PrvRUC", "PrvCor", "PrvEstReg")
                VALUES (?, ?, ?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, proveedor.getPrvCod());
            ps.setString(2, proveedor.getPrvNom());
            ps.setString(3, proveedor.getPrvRUC());
            ps.setString(4, proveedor.getPrvCor());
            ps.executeUpdate();
        }
    }

    public void modificar(Proveedor proveedor) throws Exception {
        String sql = """
                UPDATE "PROVEEDOR" SET "PrvNom" = ?, "PrvRUC" = ?, "PrvCor" = ?
                WHERE "PrvCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, proveedor.getPrvNom());
            ps.setString(2, proveedor.getPrvRUC());
            ps.setString(3, proveedor.getPrvCor());
            ps.setString(4, proveedor.getPrvCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String prvCod) throws Exception {
        String sql = """
                UPDATE "PROVEEDOR" SET "PrvEstReg" = '*' WHERE "PrvCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, prvCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String prvCod) throws Exception {
        String sql = """
                UPDATE "PROVEEDOR" SET "PrvEstReg" = 'I' WHERE "PrvCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, prvCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String prvCod) throws Exception {
        String sql = """
                UPDATE "PROVEEDOR" SET "PrvEstReg" = 'A' WHERE "PrvCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, prvCod);
            ps.executeUpdate();
        }
    }

    public List<Proveedor> listarTodo() throws Exception {
        List<Proveedor> lista = new ArrayList<>();
        String sql = """
                SELECT "PrvCod", "PrvNom", "PrvRUC", "PrvCor", "PrvEstReg"
                FROM "PROVEEDOR" ORDER BY "PrvCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Proveedor p = new Proveedor();
                p.setPrvCod(rs.getString("PrvCod"));
                p.setPrvNom(rs.getString("PrvNom"));
                p.setPrvRUC(rs.getString("PrvRUC"));
                p.setPrvCor(rs.getString("PrvCor"));
                p.setPrvEstReg(rs.getString("PrvEstReg"));
                lista.add(p);
            }
        }
        return lista;
    }
}