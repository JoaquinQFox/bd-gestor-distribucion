package unsa.bd.modules.ventas.factura.tipofactura;

import unsa.bd.config.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TipoFacturaDAO {

    public void agregar(TipoFactura tipoFactura) throws Exception {
        String sql = """
                INSERT INTO "TIPO_FACTURA" ("TipFacCod", "TipFacNom", "TipFacEstReg") VALUES (?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, tipoFactura.getTipFacCod());
            ps.setString(2, tipoFactura.getTipFacNom());
            ps.executeUpdate();
        }
    }

    public void modificar(TipoFactura tipoFactura) throws Exception {
        String sql = """
                UPDATE "TIPO_FACTURA" SET "TipFacNom" = ? WHERE "TipFacCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, tipoFactura.getTipFacNom());
            ps.setString(2, tipoFactura.getTipFacCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String tipFacCod) throws Exception {
        String sql = """
                UPDATE "TIPO_FACTURA" SET "TipFacEstReg" = '*' WHERE "TipFacCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipFacCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String tipFacCod) throws Exception {
        String sql = """
                UPDATE "TIPO_FACTURA" SET "TipFacEstReg" = 'I' WHERE "TipFacCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipFacCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String tipFacCod) throws Exception {
        String sql = """
                UPDATE "TIPO_FACTURA" SET "TipFacEstReg" = 'A' WHERE "TipFacCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipFacCod);
            ps.executeUpdate();
        }
    }

    public List<TipoFactura> listarTodo() throws Exception {
        List<TipoFactura> lista = new ArrayList<>();
        String sql = """
                SELECT "TipFacCod", "TipFacNom", "TipFacEstReg" FROM "TIPO_FACTURA" ORDER BY "TipFacCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TipoFactura t = new TipoFactura();
                t.setTipFacCod(rs.getString("TipFacCod"));
                t.setTipFacNom(rs.getString("TipFacNom"));
                t.setTipFacEstReg(rs.getString("TipFacEstReg"));
                lista.add(t);
            }
        }
        return lista;
    }
}