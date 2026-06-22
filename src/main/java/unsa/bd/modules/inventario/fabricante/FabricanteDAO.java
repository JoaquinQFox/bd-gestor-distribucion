package unsa.bd.modules.inventario.fabricante;

import unsa.bd.config.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FabricanteDAO {

    public void agregar(Fabricante fabricante) throws Exception {
        String sql = """
                INSERT INTO "FABRICANTE" ("FabCod", "FabNom", "FabEstReg") VALUES (?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, fabricante.getFabCod());
            ps.setString(2, fabricante.getFabNom());
            ps.executeUpdate();
        }
    }

    public void modificar(Fabricante fabricante) throws Exception {
        String sql = """
                UPDATE "FABRICANTE" SET "FabNom" = ? WHERE "FabCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, fabricante.getFabNom());
            ps.setString(2, fabricante.getFabCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String fabCod) throws Exception {
        String sql = """
                UPDATE "FABRICANTE" SET "FabEstReg" = '*' WHERE "FabCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, fabCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String fabCod) throws Exception {
        String sql = """
                UPDATE "FABRICANTE" SET "FabEstReg" = 'I' WHERE "FabCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, fabCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String fabCod) throws Exception {
        String sql = """
                UPDATE "FABRICANTE" SET "FabEstReg" = 'A' WHERE "FabCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, fabCod);
            ps.executeUpdate();
        }
    }

    public List<Fabricante> listarTodo() throws Exception {
        List<Fabricante> lista = new ArrayList<>();
        String sql = """
                SELECT "FabCod", "FabNom", "FabEstReg" FROM "FABRICANTE" ORDER BY "FabCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Fabricante f = new Fabricante();
                f.setFabCod(rs.getString("FabCod"));
                f.setFabNom(rs.getString("FabNom"));
                f.setFabEstReg(rs.getString("FabEstReg"));
                lista.add(f);
            }
        }
        return lista;
    }
}