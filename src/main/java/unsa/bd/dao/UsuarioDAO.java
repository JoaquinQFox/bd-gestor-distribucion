package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void agregar(Usuario usuario) throws Exception {
        String sql = """
                INSERT INTO "USUARIO" ("UsuCod", "UsuRepCod", "UsuNomUsu", "UsuCon", "UsuRolUsu", "UsuEstReg")
                VALUES (?, ?, ?, ?, ?, 'A')""";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, usuario.getUsuCod());
            ps.setString(2, usuario.getUsuRepCod());
            ps.setString(3, usuario.getUsuNomUsu());
            ps.setString(4, usuario.getUsuCon());
            ps.setString(5, usuario.getUsuRolUsu());
            ps.executeUpdate();
        }
    }

    public void modificar(Usuario usuario) throws Exception {
        String sql = """
                UPDATE "USUARIO"\s
                SET "UsuRepCod" = ?, "UsuNomUsu" = ?, "UsuCon" = ?, "UsuRolUsu" = ?
                WHERE "UsuCod" = ?""";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, usuario.getUsuRepCod());
            ps.setString(2, usuario.getUsuNomUsu());
            ps.setString(3, usuario.getUsuCon());
            ps.setString(4, usuario.getUsuRolUsu());
            ps.setString(5, usuario.getUsuCod());

            ps.executeUpdate();
        }
    }

    public void eliminar(String usuCod) throws Exception {
        String sql = """
                UPDATE "USUARIO" SET "UsuEstReg" = '*' WHERE "UsuCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String usuCod) throws Exception {
        String sql = """
                UPDATE "USUARIO" SET "UsuEstReg" = 'I' WHERE "UsuCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String usuCod) throws Exception {
        String sql = """
                UPDATE "USUARIO" SET "UsuEstReg" = 'A' WHERE "UsuCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuCod);
            ps.executeUpdate();
        }
    }

    public List<Usuario> listarTodo() throws Exception {
        List<Usuario> lista = new ArrayList<>();
        String sql = """
                SELECT "UsuCod", "UsuRepCod", "UsuNomUsu", "UsuCon", "UsuRolUsu", "UsuEstReg"
                FROM "USUARIO" ORDER BY "UsuCod" ASC""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setUsuCod(rs.getString("UsuCod"));
                u.setUsuRepCod(rs.getString("UsuRepCod"));
                u.setUsuNomUsu(rs.getString("UsuNomUsu"));
                u.setUsuCon(rs.getString("UsuCon"));
                u.setUsuRolUsu(rs.getString("UsuRolUsu"));
                u.setUsuEstReg(rs.getString("UsuEstReg"));
                lista.add(u);
            }
        }
        return lista;
    }
}