package unsa.bd.modules.seguridad.rolusuario;

import unsa.bd.config.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RolUsuarioDAO {

    public void agregar(RolUsuario rolUsuario) throws Exception {
        String sql = """
                INSERT INTO "ROL_USUARIO" ("RolUsuCod", "RolUsuNom", "RolUsuEstReg") VALUES (?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, rolUsuario.getRolUsuCod());
            ps.setString(2, rolUsuario.getRolUsuNom());
            ps.executeUpdate();
        }
    }

    public void modificar(RolUsuario rolUsuario) throws Exception {
        String sql = """
                UPDATE "ROL_USUARIO" SET "RolUsuNom" = ? WHERE "RolUsuCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, rolUsuario.getRolUsuNom());
            ps.setString(2, rolUsuario.getRolUsuCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String rolUsuCod) throws Exception {
        String sql = """
                UPDATE "ROL_USUARIO" SET "RolUsuEstReg" = '*' WHERE "RolUsuCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rolUsuCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String rolUsuCod) throws Exception {
        String sql = """
                UPDATE "ROL_USUARIO" SET "RolUsuEstReg" = 'I' WHERE "RolUsuCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rolUsuCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String rolUsuCod) throws Exception {
        String sql = """
                UPDATE "ROL_USUARIO" SET "RolUsuEstReg" = 'A' WHERE "RolUsuCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rolUsuCod);
            ps.executeUpdate();
        }
    }

    public List<RolUsuario> listarTodo() throws Exception {
        List<RolUsuario> lista = new ArrayList<>();
        String sql = """
                SELECT "RolUsuCod", "RolUsuNom", "RolUsuEstReg" FROM "ROL_USUARIO" ORDER BY "RolUsuCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RolUsuario r = new RolUsuario();
                r.setRolUsuCod(rs.getString("RolUsuCod"));
                r.setRolUsuNom(rs.getString("RolUsuNom"));
                r.setRolUsuEstReg(rs.getString("RolUsuEstReg"));
                lista.add(r);
            }
        }
        return lista;
    }
}