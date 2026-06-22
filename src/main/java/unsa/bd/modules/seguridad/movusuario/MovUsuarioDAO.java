package unsa.bd.modules.seguridad.movusuario;

import unsa.bd.config.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MovUsuarioDAO {

    public void agregar(MovUsuario mov) throws Exception {
        String sql = """
                INSERT INTO "MOV_USUARIO" ("MovUsuCod", "MovUsuDes", "MovUsuDia", "MovUsuMes", "MovUsuYea", "MovUsuHor", "MovUsuAccCod", "MovUsuEstReg", "MovUsuUsuCod") VALUES (?, ?, ?, ?, ?, ?, ?, 'A', ?)""";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, mov.getMovUsuCod());
            ps.setString(2, mov.getMovUsuDes());
            ps.setInt(3, mov.getMovUsuDia());
            ps.setInt(4, mov.getMovUsuMes());
            ps.setInt(5, mov.getMovUsuYea());
            ps.setObject(6, mov.getMovUsuHor()); // Mapea LocalTime a TIME automáticamente
            ps.setString(7, mov.getMovUsuAccCod());
            ps.setString(8, mov.getMovUsuUsuCod());
            ps.executeUpdate();
        }
    }

    public void modificar(MovUsuario mov) throws Exception {
        String sql = """
                UPDATE "MOV_USUARIO" 
                SET "MovUsuDes" = ?, "MovUsuDia" = ?, "MovUsuMes" = ?, "MovUsuYea" = ?, "MovUsuHor" = ?, "MovUsuAccCod" = ?, "MovUsuUsuCod" = ? 
                WHERE "MovUsuCod" = ?""";

        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, mov.getMovUsuDes());
            ps.setInt(2, mov.getMovUsuDia());
            ps.setInt(3, mov.getMovUsuMes());
            ps.setInt(4, mov.getMovUsuYea());
            ps.setObject(5, mov.getMovUsuHor());
            ps.setString(6, mov.getMovUsuAccCod());
            ps.setString(7, mov.getMovUsuUsuCod());
            ps.setString(8, mov.getMovUsuCod());

            ps.executeUpdate();
        }
    }

    public void eliminar(String movUsuCod) throws Exception {
        String sql = """
                UPDATE "MOV_USUARIO" SET "MovUsuEstReg" = '*' WHERE "MovUsuCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, movUsuCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String movUsuCod) throws Exception {
        String sql = """
                UPDATE "MOV_USUARIO" SET "MovUsuEstReg" = 'I' WHERE "MovUsuCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, movUsuCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String movUsuCod) throws Exception {
        String sql = """
                UPDATE "MOV_USUARIO" SET "MovUsuEstReg" = 'A' WHERE "MovUsuCod" = ?""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, movUsuCod);
            ps.executeUpdate();
        }
    }

    public List<MovUsuario> listarTodo() throws Exception {
        List<MovUsuario> lista = new ArrayList<>();
        String sql = """
                SELECT "MovUsuCod", "MovUsuDes", "MovUsuDia", "MovUsuMes", "MovUsuYea", "MovUsuHor", "MovUsuAccCod", "MovUsuEstReg", "MovUsuUsuCod" 
                FROM "MOV_USUARIO" ORDER BY "MovUsuCod" ASC""";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MovUsuario m = new MovUsuario();
                m.setMovUsuCod(rs.getString("MovUsuCod"));
                m.setMovUsuDes(rs.getString("MovUsuDes"));
                m.setMovUsuDia(rs.getInt("MovUsuDia"));
                m.setMovUsuMes(rs.getInt("MovUsuMes"));
                m.setMovUsuYea(rs.getInt("MovUsuYea"));
                m.setMovUsuHor(rs.getObject("MovUsuHor", LocalTime.class)); // Recupera el tipo TIME como LocalTime
                m.setMovUsuAccCod(rs.getString("MovUsuAccCod"));
                m.setMovUsuEstReg(rs.getString("MovUsuEstReg"));
                m.setMovUsuUsuCod(rs.getString("MovUsuUsuCod"));
                lista.add(m);
            }
        }
        return lista;
    }
}