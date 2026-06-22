package unsa.bd.modules.inventario.unidadmedida;

import unsa.bd.config.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UnidadMedidaDAO {

    public void agregar(UnidadMedida uniMed) throws Exception {
        String sql = """
                INSERT INTO "UNIDAD_MEDIDA" ("UniMedCod", "UniMedNom", "UniMedAbr", "UniMedEstReg") VALUES (?, ?, ?, 'A')""";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, uniMed.getUniMedCod());
            ps.setString(2, uniMed.getUniMedNom());
            ps.setString(3, uniMed.getUniMedAbr());
            ps.executeUpdate();
        }
    }

    public void modificar(UnidadMedida uniMed) throws Exception {
        String sql = """
                UPDATE "UNIDAD_MEDIDA" SET "UniMedNom" = ?, "UniMedAbr" = ? WHERE "UniMedCod" = ?""";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, uniMed.getUniMedNom());
            ps.setString(2, uniMed.getUniMedAbr());
            ps.setString(3, uniMed.getUniMedCod());

            ps.executeUpdate();
        }
    }
    public void eliminar(String uniMedCod) throws Exception {
        String sql = """
                UPDATE "UNIDAD_MEDIDA" SET "UniMedEstReg" = '*' WHERE "UniMedCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, uniMedCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String uniMedCod) throws Exception {
        String sql = """
                UPDATE "UNIDAD_MEDIDA" SET "UniMedEstReg" = 'I' WHERE "UniMedCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, uniMedCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String uniMedCod) throws Exception {
        String sql = """
                UPDATE "UNIDAD_MEDIDA" SET "UniMedEstReg" = 'A' WHERE "UniMedCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, uniMedCod);
            ps.executeUpdate();
        }
    }

    public List<UnidadMedida> listarTodo() throws Exception {
        List<UnidadMedida> lista = new ArrayList<>();
        String sql = """
                SELECT "UniMedCod", "UniMedNom", "UniMedAbr", "UniMedEstReg" FROM "UNIDAD_MEDIDA" ORDER BY "UniMedCod" ASC""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UnidadMedida u = new UnidadMedida();
                u.setUniMedCod(rs.getString("UniMedCod"));
                u.setUniMedNom(rs.getString("UniMedNom"));
                u.setUniMedAbr(rs.getString("UniMedAbr"));
                u.setUniEstReg(rs.getString("UniMedEstReg"));
                lista.add(u);
            }
        }
        return lista;
    }
}
