package unsa.bd.modules.cliente.cliente;

import unsa.bd.config.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void agregar(Cliente cliente) throws Exception {
        String sql = """
                INSERT INTO "CLIENTE" ("CliCod", "CliDir", "CliCor", "CliRepCod", "CliDep", "CliTipCliCod", "CliEscCreCod", "CliEstReg")
                VALUES (?, ?, ?, ?, ?, ?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cliente.getCliCod());
            ps.setString(2, cliente.getCliDir());
            ps.setString(3, cliente.getCliCor());
            ps.setString(4, cliente.getCliRepCod());
            ps.setString(5, cliente.getCliDep());
            ps.setString(6, cliente.getCliTipCliCod());
            ps.setString(7, cliente.getCliEscCreCod());
            ps.executeUpdate();
        }
    }

    public void modificar(Cliente cliente) throws Exception {
        String sql = """
                UPDATE "CLIENTE" SET "CliDir" = ?, "CliCor" = ?, "CliRepCod" = ?, "CliDep" = ?, "CliTipCliCod" = ?, "CliEscCreCod" = ?
                WHERE "CliCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cliente.getCliDir());
            ps.setString(2, cliente.getCliCor());
            ps.setString(3, cliente.getCliRepCod());
            ps.setString(4, cliente.getCliDep());
            ps.setString(5, cliente.getCliTipCliCod());
            ps.setString(6, cliente.getCliEscCreCod());
            ps.setString(7, cliente.getCliCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String cliCod) throws Exception {
        String sql = """
                UPDATE "CLIENTE" SET "CliEstReg" = '*' WHERE "CliCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String cliCod) throws Exception {
        String sql = """
                UPDATE "CLIENTE" SET "CliEstReg" = 'I' WHERE "CliCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String cliCod) throws Exception {
        String sql = """
                UPDATE "CLIENTE" SET "CliEstReg" = 'A' WHERE "CliCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliCod);
            ps.executeUpdate();
        }
    }

    public List<Cliente> listarTodo() throws Exception {
        List<Cliente> lista = new ArrayList<>();
        String sql = """
                SELECT "CliCod", "CliDir", "CliCor", "CliRepCod", "CliDep", "CliTipCliCod", "CliEscCreCod", "CliEstReg"
                FROM "CLIENTE" ORDER BY "CliCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setCliCod(rs.getString("CliCod"));
                c.setCliDir(rs.getString("CliDir"));
                c.setCliCor(rs.getString("CliCor"));
                c.setCliRepCod(rs.getString("CliRepCod"));
                c.setCliDep(rs.getString("CliDep"));
                c.setCliTipCliCod(rs.getString("CliTipCliCod"));
                c.setCliEscCreCod(rs.getString("CliEscCreCod"));
                c.setCliEstReg(rs.getString("CliEstReg"));
                lista.add(c);
            }
        }
        return lista;
    }
}
