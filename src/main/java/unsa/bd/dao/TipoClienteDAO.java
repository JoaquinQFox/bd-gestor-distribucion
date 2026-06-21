package unsa.bd.dao;

import unsa.bd.database.ConexionDB;
import unsa.bd.model.TipoCliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TipoClienteDAO {

    public void agregar(TipoCliente tipoCliente) throws Exception {
        String sql = """
                INSERT INTO "TIPO_CLIENTE" ("TipCliCod", "TipCliNom", "TipCliEstReg") VALUES (?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, tipoCliente.getTipCliCod());
            ps.setString(2, tipoCliente.getTipCliNom());
            ps.executeUpdate();
        }
    }

    public void modificar(TipoCliente tipoCliente) throws Exception {
        String sql = """
                UPDATE "TIPO_CLIENTE" SET "TipCliNom" = ? WHERE "TipCliCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, tipoCliente.getTipCliNom());
            ps.setString(2, tipoCliente.getTipCliCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String tipCliCod) throws Exception {
        String sql = """
                UPDATE "TIPO_CLIENTE" SET "TipCliEstReg" = '*' WHERE "TipCliCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipCliCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String tipCliCod) throws Exception {
        String sql = """
                UPDATE "TIPO_CLIENTE" SET "TipCliEstReg" = 'I' WHERE "TipCliCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipCliCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String tipCliCod) throws Exception {
        String sql = """
                UPDATE "TIPO_CLIENTE" SET "TipCliEstReg" = 'A' WHERE "TipCliCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipCliCod);
            ps.executeUpdate();
        }
    }

    public List<TipoCliente> listarTodo() throws Exception {
        List<TipoCliente> lista = new ArrayList<>();
        String sql = """
                SELECT "TipCliCod", "TipCliNom", "TipCliEstReg" FROM "TIPO_CLIENTE" ORDER BY "TipCliCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TipoCliente t = new TipoCliente();
                t.setTipCliCod(rs.getString("TipCliCod"));
                t.setTipCliNom(rs.getString("TipCliNom"));
                t.setTipCliEstReg(rs.getString("TipCliEstReg"));
                lista.add(t);
            }
        }
        return lista;
    }
}