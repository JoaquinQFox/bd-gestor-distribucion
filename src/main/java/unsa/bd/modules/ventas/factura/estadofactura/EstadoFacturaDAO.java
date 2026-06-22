package unsa.bd.modules.ventas.factura.estadofactura;

import unsa.bd.config.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstadoFacturaDAO {

    public void agregar(EstadoFactura estFac) throws Exception {
        String sql = """
                INSERT INTO "ESTADO_FACTURA" ("EstFacCod", "EstFacNom", "EstFacEstReg") VALUES (?, ?, 'A')""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estFac.getEstFacCod());
            ps.setString(2, estFac.getEstFacNom());
            ps.executeUpdate();
        }
    }

    public void modificar(EstadoFactura estFac) throws Exception {
        String sql = """
                UPDATE "ESTADO_FACTURA" SET "EstFacNom" = ? WHERE "EstFacCod" = ?""";
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estFac.getEstFacNom());
            ps.setString(2, estFac.getEstFacCod());
            ps.executeUpdate();
        }
    }

    public void eliminar(String estFacCod) throws Exception {
        String sql = """
                UPDATE "ESTADO_FACTURA" SET "EstFacEstReg" = '*' WHERE "EstFacCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estFacCod);
            ps.executeUpdate();
        }
    }

    public void inactivar(String estFacCod) throws Exception {
        String sql = """
                UPDATE "ESTADO_FACTURA" SET "EstFacEstReg" = 'I' WHERE "EstFacCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estFacCod);
            ps.executeUpdate();
        }
    }

    public void reactivar(String estFacCod) throws Exception {
        String sql = """
                UPDATE "ESTADO_FACTURA" SET "EstFacEstReg" = 'A' WHERE "EstFacCod" = ?""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estFacCod);
            ps.executeUpdate();
        }
    }

    public List<EstadoFactura> listarTodo() throws Exception {
        List<EstadoFactura> lista = new ArrayList<>();
        String sql = """
                SELECT "EstFacCod", "EstFacNom", "EstFacEstReg" FROM "ESTADO_FACTURA" ORDER BY "EstFacCod" ASC""";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EstadoFactura e = new EstadoFactura();
                e.setEstFacCod(rs.getString("EstFacCod"));
                e.setEstFacNom(rs.getString("EstFacNom"));
                e.setEstFacEstReg(rs.getString("EstFacEstReg"));
                lista.add(e);
            }
        }
        return lista;
    }
}
