package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.Region;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RegionDAO {

    public void agregar(Region region) throws Exception {
        String sql = """
                INSERT INTO "REGION" ("RegNom", "RegEstReg") VALUES (?, 'A')""";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, region.getRegNom());
            ps.executeUpdate();
        }
    }

    public void modificar(Region region) throws Exception {
        String sql = """
                UPDATE "REGION" SET "RegNom" = ? WHERE "RegCod" = ?""";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, region.getRegNom());
            ps.setInt(2, region.getRegCod());

            ps.executeUpdate();
        }
    }
    public void eliminar(int regCod) throws Exception {
        String sql = """
                UPDATE "REGION" SET "RegEstReg" = '*' WHERE "RegCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, regCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(int regCod) throws Exception {
        String sql = """
                UPDATE "REGION" SET "RegEstReg" = 'I' WHERE "RegCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, regCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(int regCod) throws Exception {
        String sql = """
                UPDATE "REGION" SET "RegEstReg" = 'A' WHERE "RegCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, regCod);
            ps.executeUpdate();
        }
    }

    public List<Region> listarTodo() throws Exception {
        List<Region> lista = new ArrayList<>();
        String sql = """
                SELECT "RegCod", "RegNom", "RegEstReg" FROM "REGION" ORDER BY "RegCod" ASC""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Region r = new Region();
                r.setRegCod(rs.getInt("RegCod"));
                r.setRegNom(rs.getString("RegNom"));
                r.setRegEstReg(rs.getString("RegEstReg"));
                lista.add(r);
            }
        }
        return lista;
    }

    public static void main(String[] args) throws Exception {
        RegionDAO regionDAO = new RegionDAO();

        List<Region> regiones = regionDAO.listarTodo();
        Region regionModificada = regiones.getFirst();

        regionDAO.reactivar(regionModificada.getRegCod());
    }
}
